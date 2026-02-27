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
public class Class1 {
  private static final double POSE_BUFFER_DURATION_SECONDS = 2.0;
  private static final Matrix<N3, N1> VISION_STD_DEVS =
      new Matrix<>(VecBuilder.fill(0.003, 0.003, 0.002));

  @Getter @AutoLogOutput private Pose2d estimatedPose = Pose2d.kZero;
  @Getter @AutoLogOutput private Pose2d filteredPose = Pose2d.kZero;

  private final TimeInterpolatableBuffer<Pose2d> poseBuffer =
      TimeInterpolatableBuffer.createBuffer(POSE_BUFFER_DURATION_SECONDS);
  private final Matrix<N3, N1> visionVariance = new Matrix<>(Nat.N3(), Nat.N1());

  private final SwerveDriveKinematics kinematics;
  private SwerveModulePosition[] lastModulePositions =
      new SwerveModulePosition[] {
        new SwerveModulePosition(),
        new SwerveModulePosition(),
        new SwerveModulePosition(),
        new SwerveModulePosition()
      };
  private Rotation2d gyroOffset = Rotation2d.kZero;

  @Getter @Setter private ChassisSpeeds measuredChassisSpeeds = new ChassisSpeeds();

  private static Class1 instance;

  public static Class1 getInstance() {
    if (instance == null) instance = new Class1();
    return instance;
  }

  private Class1() {
    for (int i = 0; i < 3; ++i) {
      visionVariance.set(i, 0, Math.pow(VISION_STD_DEVS.get(i, 0), 2));
    }
    kinematics = new SwerveDriveKinematics(DriveConstants.moduleTranslations);
  }

  public void resetPose(Pose2d newPose) {
    gyroOffset = newPose.getRotation().minus(estimatedPose.getRotation().minus(gyroOffset));
    filteredPose = newPose;
    estimatedPose = newPose;
    poseBuffer.clear();
  }

  public Rotation2d getRotation() {
    return filteredPose.getRotation();
  }

  public ChassisSpeeds getFieldRelativeSpeeds() {
    return ChassisSpeeds.fromRobotRelativeSpeeds(measuredChassisSpeeds, getRotation());
  }

  public void addOdometryMeasurement(OdometryMeasurement measurement) {
    Twist2d twist = kinematics.toTwist2d(lastModulePositions, measurement.modulePositions());
    lastModulePositions = measurement.modulePositions();
    Pose2d previousEstimatedPose = estimatedPose;
    estimatedPose = estimatedPose.exp(twist);

    measurement.gyroRotation()
        .ifPresent(
            gyroRotation -> {
              Rotation2d correctedRotation = gyroRotation.plus(gyroOffset);
              estimatedPose = new Pose2d(estimatedPose.getTranslation(), correctedRotation);
            });

    poseBuffer.addSample(measurement.timestamp(), estimatedPose);

    Twist2d filteredTwist = previousEstimatedPose.log(estimatedPose);
    filteredPose = filteredPose.exp(filteredTwist);
  }

  public void addVisionMeasurement(VisionMeasurement measurement) {
    try {
      if (poseBuffer.getInternalBuffer().lastKey() - POSE_BUFFER_DURATION_SECONDS
          > measurement.timestamp()) {
        return;
      }
    } catch (NoSuchElementException ex) {
      return;
    }

    var sample = poseBuffer.getSample(measurement.timestamp());
    if (sample.isEmpty()) {
      return;
    }

    Pose2d odometryPoseAtVisionTime = sample.get();
    Transform2d odometryToCurrent = new Transform2d(odometryPoseAtVisionTime, estimatedPose);
    Transform2d currentToOdometry = new Transform2d(estimatedPose, odometryPoseAtVisionTime);

    Pose2d filteredPoseAtVisionTime = filteredPose.plus(currentToOdometry);

    double[] measurementVariance = new double[3];
    for (int i = 0; i < 3; ++i) {
      measurementVariance[i] =
          measurement.stdDevs().get(i, 0) * measurement.stdDevs().get(i, 0);
    }

    Matrix<N3, N3> kalmanGain = new Matrix<>(Nat.N3(), Nat.N3());
    for (int i = 0; i < 3; ++i) {
      double processVar = visionVariance.get(i, 0);
      if (processVar == 0.0) {
        kalmanGain.set(i, i, 0.0);
      } else {
        kalmanGain.set(
            i, i, processVar / (processVar + Math.sqrt(processVar * measurementVariance[i])));
      }
    }

    Transform2d filteredToVision =
        new Transform2d(filteredPoseAtVisionTime, measurement.visionPose().toPose2d());

    var stateCorrectionVector =
        kalmanGain.times(
            VecBuilder.fill(
                filteredToVision.getX(),
                filteredToVision.getY(),
                filteredToVision.getRotation().getRadians()));

    Transform2d correctionTransform =
        new Transform2d(
            stateCorrectionVector.get(0, 0),
            stateCorrectionVector.get(1, 0),
            Rotation2d.fromRadians(stateCorrectionVector.get(2, 0)));

    filteredPose =
        filteredPoseAtVisionTime.plus(correctionTransform).plus(odometryToCurrent);
  }

  public record OdometryMeasurement(
      double timestamp, SwerveModulePosition[] modulePositions, Optional<Rotation2d> gyroRotation) {}

  public record VisionMeasurement(double timestamp, Pose3d visionPose, Matrix<N3, N1> stdDevs) {}
}