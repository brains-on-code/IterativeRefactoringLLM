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
  private static final int STATE_DIMENSION = 3;

  private static final Matrix<N3, N1> ODOMETRY_STATE_STD_DEVS =
      new Matrix<>(VecBuilder.fill(0.003, 0.003, 0.002));

  @Getter
  @AutoLogOutput
  private Pose2d odometryPose = Pose2d.kZero;

  @Getter
  @AutoLogOutput
  private Pose2d estimatedPose = Pose2d.kZero;

  private final TimeInterpolatableBuffer<Pose2d> poseBuffer =
      TimeInterpolatableBuffer.createBuffer(POSE_BUFFER_SIZE_SEC);

  private final Matrix<N3, N1> processVariance = new Matrix<>(Nat.N3(), Nat.N1());
  private final SwerveDriveKinematics kinematics;

  private SwerveModulePosition[] lastWheelPositions =
      new SwerveModulePosition[] {
        new SwerveModulePosition(),
        new SwerveModulePosition(),
        new SwerveModulePosition(),
        new SwerveModulePosition()
      };

  private Rotation2d gyroOffset = Rotation2d.kZero;

  @Getter
  @Setter
  private ChassisSpeeds robotVelocity = new ChassisSpeeds();

  private static RobotState instance;

  public static RobotState getInstance() {
    if (instance == null) {
      instance = new RobotState();
    }
    return instance;
  }

  private RobotState() {
    kinematics = new SwerveDriveKinematics(DriveConstants.moduleTranslations);
    initializeProcessVariance();
  }

  private void initializeProcessVariance() {
    for (int i = 0; i < STATE_DIMENSION; ++i) {
      double stdDev = ODOMETRY_STATE_STD_DEVS.get(i, 0);
      processVariance.set(i, 0, stdDev * stdDev);
    }
  }

  public void resetPose(Pose2d pose) {
    Rotation2d poseRotation = pose.getRotation();
    Rotation2d odometryRotation = odometryPose.getRotation();
    gyroOffset = poseRotation.minus(odometryRotation.minus(gyroOffset));

    estimatedPose = pose;
    odometryPose = pose;
    poseBuffer.clear();
  }

  public Rotation2d getRotation() {
    return estimatedPose.getRotation();
  }

  public ChassisSpeeds getFieldVelocity() {
    return ChassisSpeeds.fromRobotRelativeSpeeds(robotVelocity, getRotation());
  }

  public void addOdometryObservation(OdometryObservation observation) {
    Twist2d twist = kinematics.toTwist2d(lastWheelPositions, observation.wheelPositions());
    lastWheelPositions = observation.wheelPositions();

    Pose2d previousOdometryPose = odometryPose;
    odometryPose = odometryPose.exp(twist);

    applyGyroToOdometryPose(observation);

    poseBuffer.addSample(observation.timestamp(), odometryPose);

    Twist2d finalTwist = previousOdometryPose.log(odometryPose);
    estimatedPose = estimatedPose.exp(finalTwist);
  }

  private void applyGyroToOdometryPose(OdometryObservation observation) {
    observation
        .gyroAngle()
        .ifPresent(
            gyroAngle -> {
              Rotation2d angle = gyroAngle.plus(gyroOffset);
              odometryPose = new Pose2d(odometryPose.getTranslation(), angle);
            });
  }

  public void addVisionObservation(VisionObservation observation) {
    double timestamp = observation.timestamp();
    if (!isVisionObservationRecent(timestamp)) {
      return;
    }

    Optional<Pose2d> sample = poseBuffer.getSample(timestamp);
    if (sample.isEmpty()) {
      return;
    }

    Pose2d samplePose = sample.get();
    Transform2d sampleToOdometryTransform = new Transform2d(samplePose, odometryPose);
    Transform2d odometryToSampleTransform = new Transform2d(odometryPose, samplePose);

    Pose2d estimateAtTime = estimatedPose.plus(odometryToSampleTransform);

    double[] measurementVariance = computeVisionVariance(observation.stdDevs());
    Matrix<N3, N3> visionKalmanGain = computeVisionKalmanGain(measurementVariance);

    Transform2d measurementTransform =
        new Transform2d(estimateAtTime, observation.visionPose().toPose2d());

    Matrix<N3, N1> scaledMeasurement =
        visionKalmanGain.times(
            VecBuilder.fill(
                measurementTransform.getX(),
                measurementTransform.getY(),
                measurementTransform.getRotation().getRadians()));

    Transform2d scaledTransform =
        new Transform2d(
            scaledMeasurement.get(0, 0),
            scaledMeasurement.get(1, 0),
            Rotation2d.fromRadians(scaledMeasurement.get(2, 0)));

    estimatedPose = estimateAtTime.plus(scaledTransform).plus(sampleToOdometryTransform);
  }

  private boolean isVisionObservationRecent(double timestamp) {
    try {
      double newestTimestamp = poseBuffer.getInternalBuffer().lastKey();
      double oldestAllowedTimestamp = newestTimestamp - POSE_BUFFER_SIZE_SEC;
      return timestamp >= oldestAllowedTimestamp;
    } catch (NoSuchElementException ex) {
      return false;
    }
  }

  private double[] computeVisionVariance(Matrix<N3, N1> stdDevs) {
    double[] variance = new double[STATE_DIMENSION];
    for (int i = 0; i < STATE_DIMENSION; ++i) {
      double stdDev = stdDevs.get(i, 0);
      variance[i] = stdDev * stdDev;
    }
    return variance;
  }

  private Matrix<N3, N3> computeVisionKalmanGain(double[] measurementVariance) {
    Matrix<N3, N3> visionKalmanGain = new Matrix<>(Nat.N3(), Nat.N3());

    for (int i = 0; i < STATE_DIMENSION; ++i) {
      double processVar = processVariance.get(i, 0);
      if (processVar == 0.0) {
        visionKalmanGain.set(i, i, 0.0);
        continue;
      }

      double measVar = measurementVariance[i];
      double denominator = processVar + Math.sqrt(processVar * measVar);
      visionKalmanGain.set(i, i, processVar / denominator);
    }

    return visionKalmanGain;
  }

  public record OdometryObservation(
      double timestamp, SwerveModulePosition[] wheelPositions, Optional<Rotation2d> gyroAngle) {}

  public record VisionObservation(double timestamp, Pose3d visionPose, Matrix<N3, N1> stdDevs) {}
}