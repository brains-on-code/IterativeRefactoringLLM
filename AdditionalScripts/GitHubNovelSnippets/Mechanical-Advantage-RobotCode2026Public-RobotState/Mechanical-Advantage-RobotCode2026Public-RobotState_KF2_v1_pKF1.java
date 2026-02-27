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
  private static final Matrix<N3, N1> ODOMETRY_STATE_STD_DEVS =
      new Matrix<>(VecBuilder.fill(0.003, 0.003, 0.002));

  @Getter @AutoLogOutput private Pose2d odometryPose = Pose2d.kZero;
  @Getter @AutoLogOutput private Pose2d estimatedPose = Pose2d.kZero;

  private final TimeInterpolatableBuffer<Pose2d> poseBuffer =
      TimeInterpolatableBuffer.createBuffer(POSE_BUFFER_DURATION_SECONDS);

  /** Process noise covariance (Q) diagonal entries (squared std devs). */
  private final Matrix<N3, N1> processNoiseVarianceDiagonal = new Matrix<>(Nat.N3(), Nat.N1());

  private final SwerveDriveKinematics swerveKinematics;

  private SwerveModulePosition[] previousModulePositions =
      new SwerveModulePosition[] {
        new SwerveModulePosition(),
        new SwerveModulePosition(),
        new SwerveModulePosition(),
        new SwerveModulePosition()
      };

  private Rotation2d gyroOffset = Rotation2d.kZero;

  @Getter @Setter private ChassisSpeeds robotRelativeVelocity = new ChassisSpeeds();

  private static RobotState instance;

  public static RobotState getInstance() {
    if (instance == null) {
      instance = new RobotState();
    }
    return instance;
  }

  private RobotState() {
    for (int i = 0; i < 3; ++i) {
      processNoiseVarianceDiagonal.set(
          i, 0, Math.pow(ODOMETRY_STATE_STD_DEVS.get(i, 0), 2));
    }
    swerveKinematics = new SwerveDriveKinematics(DriveConstants.moduleTranslations);
  }

  public void resetPose(Pose2d newPose) {
    gyroOffset =
        newPose.getRotation().minus(odometryPose.getRotation().minus(gyroOffset));
    estimatedPose = newPose;
    odometryPose = newPose;
    poseBuffer.clear();
  }

  public Rotation2d getRotation() {
    return estimatedPose.getRotation();
  }

  public ChassisSpeeds getFieldRelativeVelocity() {
    return ChassisSpeeds.fromRobotRelativeSpeeds(robotRelativeVelocity, getRotation());
  }

  public void addOdometryObservation(OdometryObservation odometryObservation) {
    Twist2d measuredTwist =
        swerveKinematics.toTwist2d(
            previousModulePositions, odometryObservation.modulePositions());
    previousModulePositions = odometryObservation.modulePositions();

    Pose2d previousOdometryPose = odometryPose;
    odometryPose = odometryPose.exp(measuredTwist);

    odometryObservation.gyroAngle()
        .ifPresent(
            measuredGyroAngle -> {
              Rotation2d fusedAngle = measuredGyroAngle.plus(gyroOffset);
              odometryPose = new Pose2d(odometryPose.getTranslation(), fusedAngle);
            });

    poseBuffer.addSample(odometryObservation.timestamp(), odometryPose);

    Twist2d odometryDeltaTwist = previousOdometryPose.log(odometryPose);
    estimatedPose = estimatedPose.exp(odometryDeltaTwist);
  }

  public void addVisionObservation(VisionObservation visionObservation) {
    try {
      if (poseBuffer.getInternalBuffer().lastKey() - POSE_BUFFER_DURATION_SECONDS
          > visionObservation.timestamp()) {
        return;
      }
    } catch (NoSuchElementException ex) {
      return;
    }

    var bufferedPoseAtVisionTime = poseBuffer.getSample(visionObservation.timestamp());
    if (bufferedPoseAtVisionTime.isEmpty()) {
      return;
    }

    Pose2d odometryPoseAtVisionTime = bufferedPoseAtVisionTime.get();

    Transform2d sampleToCurrentOdometryTransform =
        new Transform2d(odometryPoseAtVisionTime, odometryPose);
    Transform2d currentOdometryToSampleTransform =
        new Transform2d(odometryPose, odometryPoseAtVisionTime);

    Pose2d estimatedPoseAtVisionTime =
        estimatedPose.plus(currentOdometryToSampleTransform);

    double[] visionMeasurementVariance = new double[3];
    for (int i = 0; i < 3; ++i) {
      double stdDev = visionObservation.standardDeviations().get(i, 0);
      visionMeasurementVariance[i] = stdDev * stdDev;
    }

    Matrix<N3, N3> kalmanGainMatrix = new Matrix<>(Nat.N3(), Nat.N3());
    for (int row = 0; row < 3; ++row) {
      double processVariance = processNoiseVarianceDiagonal.get(row, 0);
      if (processVariance == 0.0) {
        kalmanGainMatrix.set(row, row, 0.0);
      } else {
        kalmanGainMatrix.set(
            row,
            row,
            processVariance
                / (processVariance + Math.sqrt(processVariance * visionMeasurementVariance[row])));
      }
    }

    Transform2d visionMeasurementTransform =
        new Transform2d(
            estimatedPoseAtVisionTime, visionObservation.visionPose().toPose2d());

    var kalmanGainTimesError =
        kalmanGainMatrix.times(
            VecBuilder.fill(
                visionMeasurementTransform.getX(),
                visionMeasurementTransform.getY(),
                visionMeasurementTransform.getRotation().getRadians()));

    Transform2d scaledCorrectionTransform =
        new Transform2d(
            kalmanGainTimesError.get(0, 0),
            kalmanGainTimesError.get(1, 0),
            Rotation2d.fromRadians(kalmanGainTimesError.get(2, 0)));

    estimatedPose =
        estimatedPoseAtVisionTime
            .plus(scaledCorrectionTransform)
            .plus(sampleToCurrentOdometryTransform);
  }

  public record OdometryObservation(
      double timestamp,
      SwerveModulePosition[] modulePositions,
      Optional<Rotation2d> gyroAngle) {}

  public record VisionObservation(
      double timestamp, Pose3d visionPose, Matrix<N3, N1> standardDeviations) {}
}