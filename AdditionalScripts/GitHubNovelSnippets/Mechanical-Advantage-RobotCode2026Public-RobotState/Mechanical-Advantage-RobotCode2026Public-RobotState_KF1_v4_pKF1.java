package org.littletonrobotics.frc2026;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.math.interpolation.TimeInterpolatableBuffer;
import edu.wpi.first.math.var14.ChassisSpeeds;
import edu.wpi.first.math.var14.SwerveDriveKinematics;
import edu.wpi.first.math.var14.SwerveModulePosition;
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
public class PoseEstimator {
  private static final double POSE_BUFFER_DURATION_SECONDS = 2.0;
  private static final Matrix<N3, N1> VISION_MEASUREMENT_STD_DEVIATIONS =
      new Matrix<>(VecBuilder.fill(0.003, 0.003, 0.002));

  @Getter @AutoLogOutput private Pose2d estimatedPose = Pose2d.kZero;
  @Getter @AutoLogOutput private Pose2d filteredPose = Pose2d.kZero;

  private final TimeInterpolatableBuffer<Pose2d> poseHistoryBuffer =
      TimeInterpolatableBuffer.createBuffer(POSE_BUFFER_DURATION_SECONDS);
  private final Matrix<N3, N1> visionProcessVariance = new Matrix<>(Nat.N3(), Nat.N1());

  private final SwerveDriveKinematics swerveKinematics;
  private SwerveModulePosition[] previousModulePositions =
      new SwerveModulePosition[] {
        new SwerveModulePosition(),
        new SwerveModulePosition(),
        new SwerveModulePosition(),
        new SwerveModulePosition()
      };
  private Rotation2d gyroOffset = Rotation2d.kZero;

  @Getter @Setter private ChassisSpeeds measuredChassisSpeeds = new ChassisSpeeds();

  private static PoseEstimator instance;

  public static PoseEstimator getInstance() {
    if (instance == null) {
      instance = new PoseEstimator();
    }
    return instance;
  }

  private PoseEstimator() {
    for (int row = 0; row < 3; ++row) {
      double stdDev = VISION_MEASUREMENT_STD_DEVIATIONS.get(row, 0);
      visionProcessVariance.set(row, 0, Math.pow(stdDev, 2));
    }
    swerveKinematics = new SwerveDriveKinematics(DriveConstants.moduleTranslations);
  }

  public void resetPose(Pose2d newPose) {
    gyroOffset = newPose.getRotation().minus(estimatedPose.getRotation().minus(gyroOffset));
    filteredPose = newPose;
    estimatedPose = newPose;
    poseHistoryBuffer.clear();
  }

  public Rotation2d getRotation() {
    return filteredPose.getRotation();
  }

  public ChassisSpeeds getFieldRelativeSpeeds() {
    return ChassisSpeeds.fromRobotRelativeSpeeds(measuredChassisSpeeds, getRotation());
  }

  public void addOdometryMeasurement(OdometryMeasurement odometryMeasurement) {
    Twist2d odometryTwist =
        swerveKinematics.toTwist2d(previousModulePositions, odometryMeasurement.modulePositions());
    previousModulePositions = odometryMeasurement.modulePositions();

    Pose2d previousEstimatedPose = estimatedPose;
    estimatedPose = estimatedPose.exp(odometryTwist);

    odometryMeasurement
        .gyroRotation()
        .ifPresent(
            measuredGyroRotation -> {
              Rotation2d correctedRotation = measuredGyroRotation.plus(gyroOffset);
              estimatedPose = new Pose2d(estimatedPose.getTranslation(), correctedRotation);
            });

    poseHistoryBuffer.addSample(odometryMeasurement.timestamp(), estimatedPose);

    Twist2d filteredTwist = previousEstimatedPose.log(estimatedPose);
    filteredPose = filteredPose.exp(filteredTwist);
  }

  public void addVisionMeasurement(VisionMeasurement visionMeasurement) {
    double visionTimestamp = visionMeasurement.timestamp();

    try {
      double oldestValidTimestamp =
          poseHistoryBuffer.getInternalBuffer().lastKey() - POSE_BUFFER_DURATION_SECONDS;
      if (oldestValidTimestamp > visionTimestamp) {
        return;
      }
    } catch (NoSuchElementException ex) {
      return;
    }

    var odometrySampleAtVisionTime = poseHistoryBuffer.getSample(visionTimestamp);
    if (odometrySampleAtVisionTime.isEmpty()) {
      return;
    }

    Pose2d odometryPoseAtVisionTime = odometrySampleAtVisionTime.get();
    Transform2d odometryToCurrentTransform =
        new Transform2d(odometryPoseAtVisionTime, estimatedPose);
    Transform2d currentToOdometryTransform =
        new Transform2d(estimatedPose, odometryPoseAtVisionTime);

    Pose2d filteredPoseAtVisionTime = filteredPose.plus(currentToOdometryTransform);

    double[] measurementVariance = new double[3];
    Matrix<N3, N1> visionStdDeviations = visionMeasurement.standardDeviations();
    for (int row = 0; row < 3; ++row) {
      double stdDev = visionStdDeviations.get(row, 0);
      measurementVariance[row] = stdDev * stdDev;
    }

    Matrix<N3, N3> kalmanGainMatrix = new Matrix<>(Nat.N3(), Nat.N3());
    for (int index = 0; index < 3; ++index) {
      double processVariance = visionProcessVariance.get(index, 0);
      if (processVariance == 0.0) {
        kalmanGainMatrix.set(index, index, 0.0);
      } else {
        double combinedVariance =
            processVariance + Math.sqrt(processVariance * measurementVariance[index]);
        kalmanGainMatrix.set(index, index, processVariance / combinedVariance);
      }
    }

    Transform2d filteredToVisionTransform =
        new Transform2d(
            filteredPoseAtVisionTime, visionMeasurement.visionPose().toPose2d());

    var stateCorrectionVector =
        kalmanGainMatrix.times(
            VecBuilder.fill(
                filteredToVisionTransform.getX(),
                filteredToVisionTransform.getY(),
                filteredToVisionTransform.getRotation().getRadians()));

    Transform2d correctionTransform =
        new Transform2d(
            stateCorrectionVector.get(0, 0),
            stateCorrectionVector.get(1, 0),
            Rotation2d.fromRadians(stateCorrectionVector.get(2, 0)));

    filteredPose =
        filteredPoseAtVisionTime.plus(correctionTransform).plus(odometryToCurrentTransform);
  }

  public record OdometryMeasurement(
      double timestamp,
      SwerveModulePosition[] modulePositions,
      Optional<Rotation2d> gyroRotation) {}

  public record VisionMeasurement(
      double timestamp, Pose3d visionPose, Matrix<N3, N1> standardDeviations) {}
}