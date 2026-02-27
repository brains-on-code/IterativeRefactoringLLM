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
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.ExtensionMethod;
import org.littletonrobotics.frc2026.subsystems.drive.DriveConstants;
import org.littletonrobotics.frc2026.util.geometry.GeomUtil;
import org.littletonrobotics.junction.AutoLogOutput;

@ExtensionMethod({GeomUtil.class})
public class Class1 {
  private static final double HISTORY_DURATION_SECONDS = 2.0;

  private static final Matrix<N3, N1> STATE_STD_DEVS =
      new Matrix<>(VecBuilder.fill(0.003, 0.003, 0.002));

  @Getter
  @AutoLogOutput
  private Pose2d estimatedPose = Pose2d.kZero;

  @Getter
  @AutoLogOutput
  private Pose2d odometryPose = Pose2d.kZero;

  private final TimeInterpolatableBuffer<Pose2d> poseHistory =
      TimeInterpolatableBuffer.createBuffer(HISTORY_DURATION_SECONDS);

  private final Matrix<N3, N1> stateCovariance = new Matrix<>(Nat.N3(), Nat.N1());

  private final SwerveDriveKinematics kinematics;

  private SwerveModulePosition[] lastModulePositions =
      new SwerveModulePosition[] {
        new SwerveModulePosition(),
        new SwerveModulePosition(),
        new SwerveModulePosition(),
        new SwerveModulePosition()
      };

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
      stateCovariance.set(i, 0, Math.pow(STATE_STD_DEVS.get(i, 0), 2));
    }
    kinematics = new SwerveDriveKinematics(DriveConstants.moduleTranslations);
  }

  public void resetPose(Pose2d newPose) {
    gyroOffset = newPose.getRotation().minus(estimatedPose.getRotation().minus(gyroOffset));
    odometryPose = newPose;
    estimatedPose = newPose;
    poseHistory.clear();
  }

  public Rotation2d getRotation() {
    return odometryPose.getRotation();
  }

  public ChassisSpeeds getFieldRelativeSpeeds() {
    return ChassisSpeeds.fromRobotRelativeSpeeds(robotRelativeSpeeds, getRotation());
  }

  public void updateOdometry(OdometryUpdate update) {
    Twist2d twist = kinematics.toTwist2d(lastModulePositions, update.modulePositions());
    lastModulePositions = update.modulePositions();

    Pose2d previousEstimatedPose = estimatedPose;
    estimatedPose = estimatedPose.exp(twist);

    update
        .gyroRotation()
        .ifPresent(
            gyro -> {
              Rotation2d correctedRotation = gyro.plus(gyroOffset);
              estimatedPose = new Pose2d(estimatedPose.getTranslation(), correctedRotation);
            });

    poseHistory.addSample(update.timestampSeconds(), estimatedPose);

    Twist2d delta = previousEstimatedPose.log(estimatedPose);
    odometryPose = odometryPose.exp(delta);
  }

  public void addVisionMeasurement(VisionUpdate update) {
    try {
      if (poseHistory.getInternalBuffer().lastKey() - HISTORY_DURATION_SECONDS
          > update.timestampSeconds()) {
        return;
      }
    } catch (NoSuchElementException ex) {
      return;
    }

    var historySample = poseHistory.getSample(update.timestampSeconds());
    if (historySample.isEmpty()) {
      return;
    }

    Pose2d historicalPose = historySample.get();

    Transform2d fieldToHistory = new Transform2d(historicalPose, estimatedPose);
    Transform2d historyToField = new Transform2d(estimatedPose, historicalPose);

    Pose2d visionAtCurrentTime = odometryPose.plus(historyToField);

    double[] visionVariances = new double[3];
    for (int i = 0; i < 3; ++i) {
      double stdDev = update.visionStdDevs().get(i, 0);
      visionVariances[i] = stdDev * stdDev;
    }

    Matrix<N3, N3> kalmanGain = new Matrix<>(Nat.N3(), Nat.N3());
    for (int i = 0; i < 3; ++i) {
      double stateVariance = stateCovariance.get(i, 0);
      if (stateVariance == 0.0) {
        kalmanGain.set(i, i, 0.0);
      } else {
        kalmanGain.set(
            i, i, stateVariance / (stateVariance + Math.sqrt(stateVariance * visionVariances[i])));
      }
    }

    Transform2d visionError =
        new Transform2d(visionAtCurrentTime, update.visionPose().toPose2d());

    var correctionVector =
        kalmanGain.times(
            VecBuilder.fill(
                visionError.getX(),
                visionError.getY(),
                visionError.getRotation().getRadians()));

    Transform2d correction =
        new Transform2d(
            correctionVector.get(0, 0),
            correctionVector.get(1, 0),
            Rotation2d.fromRadians(correctionVector.get(2, 0)));

    odometryPose = visionAtCurrentTime.plus(correction).plus(fieldToHistory);
  }

  public record OdometryUpdate(
      double timestampSeconds,
      SwerveModulePosition[] modulePositions,
      Optional<Rotation2d> gyroRotation) {}

  public record VisionUpdate(
      double timestampSeconds, Pose3d visionPose, Matrix<N3, N1> visionStdDevs) {}
}