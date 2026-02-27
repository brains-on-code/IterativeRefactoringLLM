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

  @Getter
  @AutoLogOutput
  private Pose2d odometryPose = Pose2d.kZero;

  @Getter
  @AutoLogOutput
  private Pose2d estimatedPose = Pose2d.kZero;

  private final TimeInterpolatableBuffer<Pose2d> poseBuffer =
      TimeInterpolatableBuffer.createBuffer(POSE_BUFFER_SIZE_SEC);

  private final Matrix<N3, N1> qStdDevs = new Matrix<>(Nat.N3(), Nat.N1());
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
    for (int i = 0; i < 3; ++i) {
      double stdDev = ODOMETRY_STATE_STD_DEVS.get(i, 0);
      qStdDevs.set(i, 0, stdDev * stdDev);
    }
    kinematics = new SwerveDriveKinematics(DriveConstants.moduleTranslations);
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

    observation
        .gyroAngle()
        .ifPresent(
            gyroAngle -> {
              Rotation2d angle = gyroAngle.plus(gyroOffset);
              odometryPose = new Pose2d(odometryPose.getTranslation(), angle);
            });

    poseBuffer.addSample(observation.timestamp(), odometryPose);

    Twist2d finalTwist = previousOdometryPose.log(odometryPose);
    estimatedPose = estimatedPose.exp(finalTwist);
  }

  public void addVisionObservation(VisionObservation observation) {
    if (!isVisionObservationRecent(observation.timestamp())) {
      return;
    }

    Optional<Pose2d> sample = poseBuffer.getSample(observation.timestamp());
    if (sample.isEmpty()) {
      return;
    }

    Pose2d samplePose = sample.get();
    Transform2d sampleToOdometryTransform = new Transform2d(samplePose, odometryPose);
    Transform2d odometryToSampleTransform = new Transform2d(odometryPose, samplePose);

    Pose2d estimateAtTime = estimatedPose.plus(odometryToSampleTransform);

    double[] r = computeVisionVariance(observation.stdDevs());
    Matrix<N3, N3> visionK = computeVisionKalmanGain(r);

    Transform2d transform =
        new Transform2d(estimateAtTime, observation.visionPose().toPose2d());

    Matrix<N3, N1> kTimesTransform =
        visionK.times(
            VecBuilder.fill(
                transform.getX(),
                transform.getY(),
                transform.getRotation().getRadians()));

    Transform2d scaledTransform =
        new Transform2d(
            kTimesTransform.get(0, 0),
            kTimesTransform.get(1, 0),
            Rotation2d.fromRadians(kTimesTransform.get(2, 0)));

    estimatedPose = estimateAtTime.plus(scaledTransform).plus(sampleToOdometryTransform);
  }

  private boolean isVisionObservationRecent(double timestamp) {
    try {
      double oldestAllowedTimestamp = poseBuffer.getInternalBuffer().lastKey() - POSE_BUFFER_SIZE_SEC;
      return timestamp >= oldestAllowedTimestamp;
    } catch (NoSuchElementException ex) {
      return false;
    }
  }

  private double[] computeVisionVariance(Matrix<N3, N1> stdDevs) {
    double[] r = new double[3];
    for (int i = 0; i < 3; ++i) {
      double stdDev = stdDevs.get(i, 0);
      r[i] = stdDev * stdDev;
    }
    return r;
  }

  private Matrix<N3, N3> computeVisionKalmanGain(double[] r) {
    Matrix<N3, N3> visionK = new Matrix<>(Nat.N3(), Nat.N3());
    for (int row = 0; row < 3; ++row) {
      double processVar = qStdDevs.get(row, 0);
      if (processVar == 0.0) {
        visionK.set(row, row, 0.0);
      } else {
        double measurementVar = r[row];
        double denominator = processVar + Math.sqrt(processVar * measurementVar);
        visionK.set(row, row, processVar / denominator);
      }
    }
    return visionK;
  }

  public record OdometryObservation(
      double timestamp, SwerveModulePosition[] wheelPositions, Optional<Rotation2d> gyroAngle) {}

  public record VisionObservation(double timestamp, Pose3d visionPose, Matrix<N3, N1> stdDevs) {}
}