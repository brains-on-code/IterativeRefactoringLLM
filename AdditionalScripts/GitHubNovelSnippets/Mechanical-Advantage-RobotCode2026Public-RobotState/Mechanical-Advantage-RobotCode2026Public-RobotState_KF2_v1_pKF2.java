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
import java.util.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.ExtensionMethod;
import org.littletonrobotics.frc2026.subsystems.drive.DriveConstants;
import org.littletonrobotics.frc2026.util.geometry.GeomUtil;
import org.littletonrobotics.junction.AutoLogOutput;

@ExtensionMethod({GeomUtil.class})
public class RobotState {
  private static final double POSE_BUFFER_DURATION_SECONDS = 2.0;

  /** Standard deviations for odometry state (x, y, theta). */
  private static final Matrix<N3, N1> ODOMETRY_STATE_STD_DEVS =
      new Matrix<>(VecBuilder.fill(0.003, 0.003, 0.002));

  @Getter
  @AutoLogOutput
  private Pose2d odometryPose = Pose2d.kZero;

  @Getter
  @AutoLogOutput
  private Pose2d estimatedPose = Pose2d.kZero;

  private final TimeInterpolatableBuffer<Pose2d> poseBuffer =
      TimeInterpolatableBuffer.createBuffer(POSE_BUFFER_DURATION_SECONDS);

  /** Process noise covariance (Q) diagonal entries for x, y, theta. */
  private final Matrix<N3, N1> qStdDevs = new Matrix<>(Nat.N3(), Nat.N1());

  private final SwerveDriveKinematics kinematics;

  private SwerveModulePosition[] lastWheelPositions =
      new SwerveModulePosition[] {
        new SwerveModulePosition(),
        new SwerveModulePosition(),
        new SwerveModulePosition(),
        new SwerveModulePosition()
      };

  /** Offset between gyro reading and field-relative rotation. */
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
    for (int i = 0; i < 3; ++i) {
      double stdDev = ODOMETRY_STATE_STD_DEVS.get(i, 0);
      qStdDevs.set(i, 0, stdDev * stdDev);
    }
    kinematics = new SwerveDriveKinematics(DriveConstants.moduleTranslations);
  }

  public void resetPose(Pose2d pose) {
    gyroOffset = pose.getRotation().minus(odometryPose.getRotation().minus(gyroOffset));
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

    observation
        .gyroAngle()
        .ifPresent(
            gyroAngle -> {
              Rotation2d correctedAngle = gyroAngle.plus(gyroOffset);
              odometryPose = new Pose2d(odometryPose.getTranslation(), correctedAngle);
            });

    poseBuffer.addSample(observation.timestamp(), odometryPose);

    Twist2d odometryDelta = previousOdometryPose.log(odometryPose);
    estimatedPose = estimatedPose.exp(odometryDelta);
  }

  public void addVisionObservation(VisionObservation observation) {
    try {
      double oldestAllowedTimestamp =
          poseBuffer.getInternalBuffer().lastKey() - POSE_BUFFER_DURATION_SECONDS;
      if (oldestAllowedTimestamp > observation.timestamp()) {
        return;
      }
    } catch (NoSuchElementException ex) {
      return;
    }

    var sample = poseBuffer.getSample(observation.timestamp());
    if (sample.isEmpty()) {
      return;
    }

    Pose2d samplePose = sample.get();

    Transform2d sampleToOdometryTransform = new Transform2d(samplePose, odometryPose);
    Transform2d odometryToSampleTransform = new Transform2d(odometryPose, samplePose);

    Pose2d estimateAtTime = estimatedPose.plus(odometryToSampleTransform);

    double[] r = new double[3];
    for (int i = 0; i < 3; ++i) {
      double stdDev = observation.stdDevs().get(i, 0);
      r[i] = stdDev * stdDev;
    }

    Matrix<N3, N3> visionK = new Matrix<>(Nat.N3(), Nat.N3());
    for (int row = 0; row < 3; ++row) {
      double q = qStdDevs.get(row, 0);
      if (q == 0.0) {
        visionK.set(row, row, 0.0);
      } else {
        visionK.set(row, row, q / (q + Math.sqrt(q * r[row])));
      }
    }

    Transform2d visionError =
        new Transform2d(estimateAtTime, observation.visionPose().toPose2d());

    var kTimesError =
        visionK.times(
            VecBuilder.fill(
                visionError.getX(),
                visionError.getY(),
                visionError.getRotation().getRadians()));

    Transform2d scaledCorrection =
        new Transform2d(
            kTimesError.get(0, 0),
            kTimesError.get(1, 0),
            Rotation2d.fromRadians(kTimesError.get(2, 0)));

    estimatedPose = estimateAtTime.plus(scaledCorrection).plus(sampleToOdometryTransform);
  }

  public record OdometryObservation(
      double timestamp, SwerveModulePosition[] wheelPositions, Optional<Rotation2d> gyroAngle) {}

  public record VisionObservation(double timestamp, Pose3d visionPose, Matrix<N3, N1> stdDevs) {}
}