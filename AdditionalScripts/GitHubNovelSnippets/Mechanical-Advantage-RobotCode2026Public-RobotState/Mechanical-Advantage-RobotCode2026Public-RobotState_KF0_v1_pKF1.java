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
import java.util.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.ExtensionMethod;
import org.littletonrobotics.frc2026.subsystems.drive.DriveConstants;
import org.littletonrobotics.frc2026.util.geometry.GeomUtil;
import org.littletonrobotics.junction.AutoLogOutput;

@ExtensionMethod({GeomUtil.class})
public class RobotState {
  // Constants
  private static final double POSE_BUFFER_DURATION_SECONDS = 2.0;
  private static final Matrix<N3, N1> ODOMETRY_STATE_STD_DEVS =
      new Matrix<>(VecBuilder.fill(0.003, 0.003, 0.002));

  // MARK: - Class fields

  // Pose estimation fields
  @Getter @AutoLogOutput private Pose2d odometryPose = Pose2d.kZero;
  @Getter @AutoLogOutput private Pose2d estimatedPose = Pose2d.kZero;

  private final TimeInterpolatableBuffer<Pose2d> poseHistoryBuffer =
      TimeInterpolatableBuffer.createBuffer(POSE_BUFFER_DURATION_SECONDS);

  /** Process noise covariance (Q) diagonal entries for x, y, theta. */
  private final Matrix<N3, N1> processNoiseVariances = new Matrix<>(Nat.N3(), Nat.N1());

  // Odometry fields
  private final SwerveDriveKinematics swerveKinematics;

  private SwerveModulePosition[] previousModulePositions =
      new SwerveModulePosition[] {
        new SwerveModulePosition(),
        new SwerveModulePosition(),
        new SwerveModulePosition(),
        new SwerveModulePosition()
      };

  /** Offset between gyro-reported heading and field-relative heading. */
  private Rotation2d gyroOffset = Rotation2d.kZero;

  @Getter @Setter private ChassisSpeeds robotRelativeVelocity = new ChassisSpeeds();

  // MARK: - Initialization

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
      processNoiseVariances.set(i, 0, stdDev * stdDev);
    }
    swerveKinematics = new SwerveDriveKinematics(DriveConstants.moduleTranslations);
  }

  // MARK: - Drive & vision methods

  /** Reset the pose estimate and odometry pose to the given pose. */
  public void resetPose(Pose2d newPose) {
    // Gyro offset is the rotation that maps the old gyro rotation (estimated - offset)
    // to the new frame of rotation.
    gyroOffset = newPose.getRotation().minus(odometryPose.getRotation().minus(gyroOffset));
    estimatedPose = newPose;
    odometryPose = newPose;
    poseHistoryBuffer.clear();
  }

  /** Get the rotation of the estimated pose. */
  public Rotation2d getRotation() {
    return estimatedPose.getRotation();
  }

  public ChassisSpeeds getFieldRelativeVelocity() {
    return ChassisSpeeds.fromRobotRelativeSpeeds(robotRelativeVelocity, getRotation());
  }

  /** Adds a new odometry sample from the drive subsystem. */
  public void addOdometryObservation(OdometryObservation observation) {
    // Update odometry pose from module positions
    Twist2d odometryTwist =
        swerveKinematics.toTwist2d(previousModulePositions, observation.modulePositions());
    previousModulePositions = observation.modulePositions();

    Pose2d previousOdometryPose = odometryPose;
    odometryPose = odometryPose.exp(odometryTwist);

    // Replace odometry heading with gyro if present
    observation.gyroAngle()
        .ifPresent(
            measuredGyroAngle -> {
              Rotation2d correctedHeading = measuredGyroAngle.plus(gyroOffset);
              odometryPose = new Pose2d(odometryPose.getTranslation(), correctedHeading);
            });

    // Add pose to history buffer at timestamp
    poseHistoryBuffer.addSample(observation.timestamp(), odometryPose);

    // Apply odometry delta to vision pose estimate
    Twist2d odometryDelta = previousOdometryPose.log(odometryPose);
    estimatedPose = estimatedPose.exp(odometryDelta);
  }

  /** Adds a new vision pose observation from the vision subsystem. */
  public void addVisionObservation(VisionObservation observation) {
    // If measurement is old enough to be outside the pose buffer's timespan, skip.
    try {
      double newestBufferedTimestamp = poseHistoryBuffer.getInternalBuffer().lastKey();
      if (newestBufferedTimestamp - POSE_BUFFER_DURATION_SECONDS > observation.timestamp()) {
        return;
      }
    } catch (NoSuchElementException ex) {
      return;
    }

    // Get odometry-based pose at observation timestamp
    var odometrySampleAtObservationTime = poseHistoryBuffer.getSample(observation.timestamp());
    if (odometrySampleAtObservationTime.isEmpty()) {
      return;
    }

    Pose2d odometryPoseAtObservationTime = odometrySampleAtObservationTime.get();

    // Calculate transforms between current odometry pose and odometry pose at observation time
    Transform2d sampleToCurrentOdometryTransform =
        new Transform2d(odometryPoseAtObservationTime, odometryPose);
    Transform2d currentOdometryToSampleTransform =
        new Transform2d(odometryPose, odometryPoseAtObservationTime);

    // Shift estimated pose backwards to observation time
    Pose2d estimatedPoseAtObservationTime =
        estimatedPose.plus(currentOdometryToSampleTransform);

    // Calculate 3 x 3 vision measurement covariance diagonal (R)
    double[] visionMeasurementVariances = new double[3];
    for (int i = 0; i < 3; ++i) {
      double stdDev = observation.standardDeviations().get(i, 0);
      visionMeasurementVariances[i] = stdDev * stdDev;
    }

    // Solve for closed form Kalman gain for continuous Kalman filter with A = 0 and C = I.
    // See wpimath/algorithms.md.
    Matrix<N3, N3> kalmanGain = new Matrix<>(Nat.N3(), Nat.N3());
    for (int row = 0; row < 3; ++row) {
      double processVariance = processNoiseVariances.get(row, 0);
      if (processVariance == 0.0) {
        kalmanGain.set(row, row, 0.0);
      } else {
        kalmanGain.set(
            row,
            row,
            processVariance
                / (processVariance + Math.sqrt(processVariance * visionMeasurementVariances[row])));
      }
    }

    // Calculate the transform from the shifted estimate to the observation pose
    Transform2d estimateToVisionTransform =
        new Transform2d(
            estimatedPoseAtObservationTime, observation.visionPose().toPose2d());

    // Scale the transform by the Kalman gain
    var kalmanGainTimesTransform =
        kalmanGain.times(
            VecBuilder.fill(
                estimateToVisionTransform.getX(),
                estimateToVisionTransform.getY(),
                estimateToVisionTransform.getRotation().getRadians()));

    Transform2d scaledEstimateToVisionTransform =
        new Transform2d(
            kalmanGainTimesTransform.get(0, 0),
            kalmanGainTimesTransform.get(1, 0),
            Rotation2d.fromRadians(kalmanGainTimesTransform.get(2, 0)));

    // Recalculate the current estimate by applying the scaled transform to the old estimate
    // then shifting forwards using odometry data
    estimatedPose =
        estimatedPoseAtObservationTime
            .plus(scaledEstimateToVisionTransform)
            .plus(sampleToCurrentOdometryTransform);
  }

  // MARK: - Type declarations

  public record OdometryObservation(
      double timestamp,
      SwerveModulePosition[] modulePositions,
      Optional<Rotation2d> gyroAngle) {}

  public record VisionObservation(
      double timestamp, Pose3d visionPose, Matrix<N3, N1> standardDeviations) {}
}