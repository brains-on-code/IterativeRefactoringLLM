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

  private static final double POSE_BUFFER_DURATION_SECONDS = 2.0;
  private static final Matrix<N3, N1> VISION_STD_DEVS =
      new Matrix<>(VecBuilder.fill(0.003, 0.003, 0.002));
  private static final int STATE_DIMENSION = 3;
  private static final int MODULE_COUNT = 4;

  @Getter
  @AutoLogOutput
  private Pose2d odometryPose = Pose2d.kZero;

  @Getter
  @AutoLogOutput
  private Pose2d estimatedPose = Pose2d.kZero;

  private final TimeInterpolatableBuffer<Pose2d> poseBuffer =
      TimeInterpolatableBuffer.createBuffer(POSE_BUFFER_DURATION_SECONDS);

  /** Diagonal matrix of squared process noise (from VISION_STD_DEVS). */
  private final Matrix<N3, N1> processNoiseDiagonal = new Matrix<>(Nat.N3(), Nat.N1());

  private final SwerveDriveKinematics kinematics;

  private SwerveModulePosition[] lastModulePositions = createEmptyModulePositions();

  /** Gyro offset between field-relative and robot-relative heading. */
  private Rotation2d gyroOffset = Rotation2d.kZero;

  @Getter
  @Setter
  private ChassisSpeeds measuredChassisSpeeds = new ChassisSpeeds();

  private static Class1 instance;

  public static Class1 getInstance() {
    if (instance == null) {
      instance = new Class1();
    }
    return instance;
  }

  private Class1() {
    initializeProcessNoise();
    kinematics = new SwerveDriveKinematics(DriveConstants.moduleTranslations);
  }

  private static SwerveModulePosition[] createEmptyModulePositions() {
    SwerveModulePosition[] positions = new SwerveModulePosition[MODULE_COUNT];
    for (int i = 0; i < MODULE_COUNT; i++) {
      positions[i] = new SwerveModulePosition();
    }
    return positions;
  }

  private void initializeProcessNoise() {
    for (int i = 0; i < STATE_DIMENSION; ++i) {
      double stdDev = VISION_STD_DEVS.get(i, 0);
      processNoiseDiagonal.set(i, 0, stdDev * stdDev);
    }
  }

  /** Resets the pose estimate and odometry to the given pose. */
  public void resetPose(Pose2d newPose) {
    Rotation2d currentRotation = odometryPose.getRotation();
    gyroOffset = newPose.getRotation().minus(currentRotation.minus(gyroOffset));

    estimatedPose = newPose;
    odometryPose = newPose;
    poseBuffer.clear();
  }

  public Rotation2d getEstimatedRotation() {
    return estimatedPose.getRotation();
  }

  public ChassisSpeeds getFieldRelativeSpeeds() {
    return ChassisSpeeds.fromRobotRelativeSpeeds(measuredChassisSpeeds, getEstimatedRotation());
  }

  /** Updates odometry and pose estimate from module positions and optional gyro reading. */
  public void updateOdometry(OdometryUpdate update) {
    Twist2d twist = kinematics.toTwist2d(lastModulePositions, update.modulePositions());
    lastModulePositions = update.modulePositions();

    Pose2d previousOdometryPose = odometryPose;
    odometryPose = odometryPose.exp(twist);

    applyGyroCorrection(update);
    poseBuffer.addSample(update.timestampSeconds(), odometryPose);

    Twist2d delta = previousOdometryPose.log(odometryPose);
    estimatedPose = estimatedPose.exp(delta);
  }

  private void applyGyroCorrection(OdometryUpdate update) {
    update
        .gyroRotation()
        .ifPresent(
            gyroReading -> {
              Rotation2d correctedRotation = gyroReading.plus(gyroOffset);
              odometryPose = new Pose2d(odometryPose.getTranslation(), correctedRotation);
            });
  }

  /** Fuses a vision measurement into the pose estimate. */
  public void addVisionMeasurement(VisionUpdate update) {
    if (!isVisionTimestampInRange(update.timestampSeconds())) {
      return;
    }

    Optional<Pose2d> sample = poseBuffer.getSample(update.timestampSeconds());
    if (sample.isEmpty()) {
      return;
    }

    Pose2d odometryAtVisionTime = sample.get();
    Transform2d odometryToCurrent = new Transform2d(odometryAtVisionTime, odometryPose);
    Transform2d currentToOdometry = new Transform2d(odometryPose, odometryAtVisionTime);

    Pose2d estimatedAtVisionTime = estimatedPose.plus(currentToOdometry);

    double[] visionVariance = computeVisionVariance(update);
    Matrix<N3, N3> kalmanGain = computeKalmanGain(visionVariance);

    Transform2d estimatedToVision =
        new Transform2d(estimatedAtVisionTime, update.visionPose().toPose2d());

    var innovation =
        kalmanGain.times(
            VecBuilder.fill(
                estimatedToVision.getX(),
                estimatedToVision.getY(),
                estimatedToVision.getRotation().getRadians()));

    Transform2d correction =
        new Transform2d(
            innovation.get(0, 0),
            innovation.get(1, 0),
            Rotation2d.fromRadians(innovation.get(2, 0)));

    estimatedPose = estimatedAtVisionTime.plus(correction).plus(odometryToCurrent);
  }

  private boolean isVisionTimestampInRange(double visionTimestampSeconds) {
    try {
      double oldestValidTimestamp =
          poseBuffer.getInternalBuffer().lastKey() - POSE_BUFFER_DURATION_SECONDS;
      return oldestValidTimestamp <= visionTimestampSeconds;
    } catch (NoSuchElementException ex) {
      return false;
    }
  }

  private double[] computeVisionVariance(VisionUpdate update) {
    double[] visionVariance = new double[STATE_DIMENSION];
    for (int i = 0; i < STATE_DIMENSION; ++i) {
      double stdDev = update.stdDevs().get(i, 0);
      visionVariance[i] = stdDev * stdDev;
    }
    return visionVariance;
  }

  private Matrix<N3, N3> computeKalmanGain(double[] visionVariance) {
    Matrix<N3, N3> kalmanGain = new Matrix<>(Nat.N3(), Nat.N3());

    for (int i = 0; i < STATE_DIMENSION; ++i) {
      double processVar = processNoiseDiagonal.get(i, 0);
      if (processVar == 0.0) {
        kalmanGain.set(i, i, 0.0);
        continue;
      }

      double combinedStd = Math.sqrt(processVar * visionVariance[i]);
      double gain = processVar / (processVar + combinedStd);
      kalmanGain.set(i, i, gain);
    }

    return kalmanGain;
  }

  public record OdometryUpdate(
      double timestampSeconds,
      SwerveModulePosition[] modulePositions,
      Optional<Rotation2d> gyroRotation) {}

  public record VisionUpdate(
      double timestampSeconds, Pose3d visionPose, Matrix<N3, N1> stdDevs) {}
}