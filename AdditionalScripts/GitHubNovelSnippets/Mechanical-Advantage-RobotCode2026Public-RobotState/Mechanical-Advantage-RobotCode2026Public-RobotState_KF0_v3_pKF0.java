// Copyright (c) 2025-2026 Littleton Robotics
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package org.littletonrobotics.frc2026;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.math.interpolation.TimeInterpolatableBuffer;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.ExtensionMethod;
import org.littletonrobotics.frc2026.subsystems.drive.DriveConstants;
import org.littletonrobotics.frc2026.util.geometry.GeomUtil;
import org.littletonrobotics.junction.AutoLogOutput;

@ExtensionMethod({GeomUtil.class})
public class RobotState {
  private static final double POSE_BUFFER_SIZE_SEC = 2.0;
  private static final Matrix<N3, N1> ODOMETRY_STATE_STD_DEVS =
      new Matrix<>(VecBuilder.fill(0.003, 0.003, 0.002));

  private static RobotState instance;

  @Getter @AutoLogOutput private Pose2d odometryPose = Pose2d.kZero;
  @Getter @AutoLogOutput private Pose2d estimatedPose = Pose2d.kZero;

  private final TimeInterpolatableBuffer<Pose2d> poseBuffer =
      TimeInterpolatableBuffer.createBuffer(POSE_BUFFER_SIZE_SEC);
  private final Matrix<N3, N1> qStdDevs = new Matrix<>(Nat.N3(), Nat.N1());
  private final SwerveDriveKinematics kinematics =
      new SwerveDriveKinematics(DriveConstants.moduleTranslations);

  private SwerveModulePosition[] lastWheelPositions =
      new SwerveModulePosition[] {
        new SwerveModulePosition(),
        new SwerveModulePosition(),
        new SwerveModulePosition(),
        new SwerveModulePosition()
      };

  private Rotation2d gyroOffset = Rotation2d.kZero;

  @Getter @Setter private ChassisSpeeds robotVelocity = new ChassisSpeeds();

  public static RobotState getInstance() {
    if (instance == null) {
      instance = new RobotState();
    }
    return instance;
  }

  private RobotState() {
    initializeProcessNoise();
  }

  private void initializeProcessNoise() {
    for (int i = 0; i < 3; i++) {
      double stdDev = ODOMETRY_STATE_STD_DEVS.get(i, 0);
      qStdDevs.set(i, 0, stdDev * stdDev);
    }
  }

  /** Reset the pose estimate and odometry pose to the given pose. */
  public void resetPose(Pose2d pose) {
    Rotation2d currentGyroFrame = odometryPose.getRotation().minus(gyroOffset);
    gyroOffset = pose.getRotation().minus(currentGyroFrame);

    estimatedPose = pose;
    odometryPose = pose;
    poseBuffer.clear();
  }

  /** Get the rotation of the estimated pose. */
  public Rotation2d getRotation() {
    return estimatedPose.getRotation();
  }

  public ChassisSpeeds getFieldVelocity() {
    return ChassisSpeeds.fromRobotRelativeSpeeds(robotVelocity, getRotation());
  }

  /** Adds a new odometry sample from the drive subsystem. */
  public void addOdometryObservation(OdometryObservation observation) {
    Twist2d twist = kinematics.toTwist2d(lastWheelPositions, observation.wheelPositions());
    lastWheelPositions = observation.wheelPositions();

    Pose2d previousOdometryPose = odometryPose;
    odometryPose = odometryPose.exp(twist);

    applyGyroCorrection(observation);
    poseBuffer.addSample(observation.timestamp(), odometryPose);

    Twist2d odometryDelta = previousOdometryPose.log(odometryPose);
    estimatedPose = estimatedPose.exp(odometryDelta);
  }

  private void applyGyroCorrection(OdometryObservation observation) {
    observation
        .gyroAngle()
        .ifPresent(
            gyroAngle -> {
              Rotation2d correctedAngle = gyroAngle.plus(gyroOffset);
              odometryPose = new Pose2d(odometryPose.getTranslation(), correctedAngle);
            });
  }

  /** Adds a new vision pose observation from the vision subsystem. */
  public void addVisionObservation(VisionObservation observation) {
    double timestamp = observation.timestamp();
    if (isObservationTooOld(timestamp)) {
      return;
    }

    Optional<Pose2d> sample = poseBuffer.getSample(timestamp);
    if (sample.isEmpty()) {
      return;
    }

    Pose2d samplePose = sample.get();
    Pose2d estimateAtTime = getEstimateAtSampleTime(samplePose);

    double[] visionVariance = computeVisionVariance(observation.stdDevs());
    Matrix<N3, N3> visionKalmanGain = computeVisionKalmanGain(visionVariance);

    Transform2d measurementTransform =
        new Transform2d(estimateAtTime, observation.visionPose().toPose2d());
    Transform2d scaledTransform = applyKalmanGain(visionKalmanGain, measurementTransform);

    Transform2d sampleToOdometryTransform = new Transform2d(samplePose, odometryPose);
    estimatedPose = estimateAtTime.plus(scaledTransform).plus(sampleToOdometryTransform);
  }

  private Pose2d getEstimateAtSampleTime(Pose2d samplePose) {
    Transform2d odometryToSampleTransform = new Transform2d(odometryPose, samplePose);
    return estimatedPose.plus(odometryToSampleTransform);
  }

  private boolean isObservationTooOld(double timestamp) {
    try {
      double newestTimestamp = poseBuffer.getInternalBuffer().lastKey();
      return newestTimestamp - POSE_BUFFER_SIZE_SEC > timestamp;
    } catch (NoSuchElementException ex) {
      return true;
    }
  }

  private double[] computeVisionVariance(Matrix<N3, N1> stdDevs) {
    double[] variance = new double[3];
    for (int i = 0; i < 3; i++) {
      double stdDev = stdDevs.get(i, 0);
      variance[i] = stdDev * stdDev;
    }
    return variance;
  }

  private Matrix<N3, N3> computeVisionKalmanGain(double[] measurementVariance) {
    Matrix<N3, N3> visionK = new Matrix<>(Nat.N3(), Nat.N3());

    for (int row = 0; row < 3; row++) {
      double processVar = qStdDevs.get(row, 0);
      if (processVar == 0.0) {
        visionK.set(row, row, 0.0);
        continue;
      }

      double measVar = measurementVariance[row];
      double gain = processVar / (processVar + Math.sqrt(processVar * measVar));
      visionK.set(row, row, gain);
    }

    return visionK;
  }

  private Transform2d applyKalmanGain(Matrix<N3, N3> visionK, Transform2d transform) {
    Matrix<N3, N1> stateVector =
        VecBuilder.fill(
            transform.getX(), transform.getY(), transform.getRotation().getRadians());

    Matrix<N3, N1> kTimesTransform = visionK.times(stateVector);

    return new Transform2d(
        kTimesTransform.get(0, 0),
        kTimesTransform.get(1, 0),
        Rotation2d.fromRadians(kTimesTransform.get(2, 0)));
  }

  public record OdometryObservation(
      double timestamp, SwerveModulePosition[] wheelPositions, Optional<Rotation2d> gyroAngle) {}

  public record VisionObservation(double timestamp, Pose3d visionPose, Matrix<N3, N1> stdDevs) {}
}