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
  // Maximum duration (seconds) to keep pose history for interpolation
  private static final double kHistoryDurationSeconds = 2.0;

  // Standard deviations for state (x, y, heading)
  private static final Matrix<N3, N1> kStateStdDevs =
      new Matrix<>(VecBuilder.fill(0.003, 0.003, 0.002));

  @Getter
  @AutoLogOutput
  private Pose2d estimatedPose = Pose2d.kZero;

  @Getter
  @AutoLogOutput
  private Pose2d odometryPose = Pose2d.kZero;

  private final TimeInterpolatableBuffer<Pose2d> poseHistory =
      TimeInterpolatableBuffer.createBuffer(kHistoryDurationSeconds);

  // State covariance (variance of x, y, heading)
  private final Matrix<N3, N1> stateCovariance = new Matrix<>(Nat.N3(), Nat.N1());

  private final SwerveDriveKinematics kinematics;

  private SwerveModulePosition[] lastModulePositions =
      new SwerveModulePosition[] {
        new SwerveModulePosition(),
        new SwerveModulePosition(),
        new SwerveModulePosition(),
        new SwerveModulePosition()
      };

  // Offset between gyro heading and field-relative heading
  private Rotation2d gyroOffset = Rotation2d.kZero;

  @Getter
  @Setter
  private ChassisSpeeds robotRelativeSpeeds = new ChassisSpeeds();

  private static Class1 instance;

  public static Class1 getInstance() {
    if (instance == null) {
      instance = new Class1();
    }
    return instance;
  }

  private Class1() {
    for (int i = 0; i < 3; ++i) {
      stateCovariance.set(i, 0, Math.pow(kStateStdDevs.get(i, 0), 2));
    }
    kinematics = new SwerveDriveKinematics(DriveConstants.moduleTranslations);
  }

  // Reset odometry and pose estimate to a new pose
  public void resetPose(Pose2d newPose) {
    gyroOffset = newPose.method3().minus(estimatedPose.method3().minus(gyroOffset));
    odometryPose = newPose;
    estimatedPose = newPose;
    poseHistory.clear();
  }

  // Current field-relative heading
  public Rotation2d getRotation() {
    return odometryPose.method3();
  }

  // Current field-relative chassis speeds
  public ChassisSpeeds getFieldRelativeSpeeds() {
    return ChassisSpeeds.fromRobotRelativeSpeeds(robotRelativeSpeeds, getRotation());
  }

  // Update odometry and pose estimate from module positions and optional gyro
  public void updateOdometry(OdometryUpdate update) {
    Twist2d twist = kinematics.toTwist2d(lastModulePositions, update.modulePositions());
    lastModulePositions = update.modulePositions();

    Pose2d previousEstimatedPose = estimatedPose;
    estimatedPose = estimatedPose.exp(twist);

    update.gyroRotation()
        .ifPresent(
            gyro -> {
              Rotation2d correctedRotation = gyro.plus(gyroOffset);
              estimatedPose = new Pose2d(estimatedPose.getTranslation(), correctedRotation);
            });

    poseHistory.addSample(update.timestampSeconds(), estimatedPose);

    Twist2d delta = previousEstimatedPose.log(estimatedPose);
    odometryPose = odometryPose.exp(delta);
  }

  // Fuse a single vision measurement into the pose estimate
  public void addVisionMeasurement(VisionUpdate update) {
    try {
      if (poseHistory.getInternalBuffer().lastKey() - kHistoryDurationSeconds
          > update.timestampSeconds()) {
        return;
      }
    } catch (NoSuchElementException ex) {
      return;
    }

    var sample = poseHistory.getSample(update.timestampSeconds());
    if (sample.isEmpty()) {
      return;
    }

    Transform2d fieldToHistory = new Transform2d(sample.get(), estimatedPose);
    Transform2d historyToField = new Transform2d(estimatedPose, sample.get());

    Pose2d visionAtCurrentTime = odometryPose.plus(historyToField);

    double[] visionVar = new double[3];
    for (int i = 0; i < 3; ++i) {
      double stdDev = update.visionStdDevs().get(i, 0);
      visionVar[i] = stdDev * stdDev;
    }

    Matrix<N3, N3> kalmanGain = new Matrix<>(Nat.N3(), Nat.N3());
    for (int i = 0; i < 3; ++i) {
      double stateVar = stateCovariance.get(i, 0);
      if (stateVar == 0.0) {
        kalmanGain.set(i, i, 0.0);
      } else {
        kalmanGain.set(i, i, stateVar / (stateVar + Math.sqrt(stateVar * visionVar[i])));
      }
    }

    Transform2d visionError =
        new Transform2d(visionAtCurrentTime, update.visionPose().toPose2d());

    var correctionVector =
        kalmanGain.times(
            VecBuilder.fill(
                visionError.getX(), visionError.getY(), visionError.method3().getRadians()));

    Transform2d correction =
        new Transform2d(
            correctionVector.get(0, 0),
            correctionVector.get(1, 0),
            Rotation2d.fromRadians(correctionVector.get(2, 0)));

    odometryPose = visionAtCurrentTime.plus(correction).plus(fieldToHistory);
  }

  // Odometry update: timestamp, module positions, optional gyro rotation
  public record OdometryUpdate(
      double timestampSeconds,
      SwerveModulePosition[] modulePositions,
      Optional<Rotation2d> gyroRotation) {}

  // Vision update: timestamp, measured pose, measurement standard deviations
  public record VisionUpdate(
      double timestampSeconds, Pose3d visionPose, Matrix<N3, N1> visionStdDevs) {}
}