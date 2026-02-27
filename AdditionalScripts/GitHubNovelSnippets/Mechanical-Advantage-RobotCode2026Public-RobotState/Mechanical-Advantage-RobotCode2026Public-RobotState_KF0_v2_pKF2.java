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
  /** Maximum age (seconds) of poses stored in the interpolation buffer. */
  private static final double kPoseBufferSizeSec = 2.0;

  /** Odometry state standard deviations for (x, y, theta). */
  private static final Matrix<N3, N1> kOdometryStateStdDevs =
      new Matrix<>(VecBuilder.fill(0.003, 0.003, 0.002));

  /** Current odometry-only pose estimate. */
  @Getter @AutoLogOutput private Pose2d odometryPose = Pose2d.kZero;

  /** Current fused (odometry + vision) pose estimate. */
  @Getter @AutoLogOutput private Pose2d estimatedPose = Pose2d.kZero;

  /** Time-indexed history of odometry poses for vision backtracking. */
  private final TimeInterpolatableBuffer<Pose2d> poseBuffer =
      TimeInterpolatableBuffer.createBuffer(kPoseBufferSizeSec);

  /** Process noise covariance (Q) diagonal entries for (x, y, theta). */
  private final Matrix<N3, N1> qStdDevs = new Matrix<>(Nat.N3(), Nat.N1());

  /** Swerve kinematics used to integrate wheel positions into a chassis twist. */
  private final SwerveDriveKinematics kinematics;

  /** Last wheel positions used to compute odometry deltas. */
  private SwerveModulePosition[] lastWheelPositions =
      new SwerveModulePosition[] {
        new SwerveModulePosition(),
        new SwerveModulePosition(),
        new SwerveModulePosition(),
        new SwerveModulePosition()
      };

  /** Offset that maps raw gyro readings into the field reference frame. */
  private Rotation2d gyroOffset = Rotation2d.kZero;

  /** Robot velocity in the robot frame. */
  @Getter @Setter private ChassisSpeeds robotVelocity = new ChassisSpeeds();

  /** Singleton instance. */
  private static RobotState instance;

  public static RobotState getInstance() {
    if (instance == null) {
      instance = new RobotState();
    }
    return instance;
  }

  private RobotState() {
    // Precompute Q diagonal entries from odometry standard deviations.
    for (int i = 0; i < 3; ++i) {
      double stdDev = kOdometryStateStdDevs.get(i, 0);
      qStdDevs.set(i, 0, stdDev * stdDev);
    }
    kinematics = new SwerveDriveKinematics(DriveConstants.moduleTranslations);
  }

  /** Resets both odometry and fused pose to the given pose. */
  public void resetPose(Pose2d pose) {
    // Adjust gyro offset so that the current gyro reading corresponds to the new pose rotation.
    gyroOffset = pose.getRotation().minus(odometryPose.getRotation().minus(gyroOffset));

    estimatedPose = pose;
    odometryPose = pose;
    poseBuffer.clear();
  }

  /** Returns the rotation of the current fused pose estimate. */
  public Rotation2d getRotation() {
    return estimatedPose.getRotation();
  }

  /** Returns the robot's velocity expressed in the field frame. */
  public ChassisSpeeds getFieldVelocity() {
    return ChassisSpeeds.fromRobotRelativeSpeeds(robotVelocity, getRotation());
  }

  /** Adds a new odometry measurement from the drive subsystem. */
  public void addOdometryObservation(OdometryObservation observation) {
    // Integrate wheel deltas into odometry pose.
    Twist2d twist = kinematics.toTwist2d(lastWheelPositions, observation.wheelPositions());
    lastWheelPositions = observation.wheelPositions();

    Pose2d lastOdometryPose = odometryPose;
    odometryPose = odometryPose.exp(twist);

    // If a gyro reading is available, override the heading in the odometry pose.
    observation.gyroAngle.ifPresent(
        gyroAngle -> {
          Rotation2d angle = gyroAngle.plus(gyroOffset);
          odometryPose = new Pose2d(odometryPose.getTranslation(), angle);
        });

    // Store the updated odometry pose in the interpolation buffer.
    poseBuffer.addSample(observation.timestamp(), odometryPose);

    // Apply the same odometry delta to the fused pose.
    Twist2d finalTwist = lastOdometryPose.log(odometryPose);
    estimatedPose = estimatedPose.exp(finalTwist);
  }

  /** Adds a new vision-based pose measurement. */
  public void addVisionObservation(VisionObservation observation) {
    // Skip if the measurement is older than the buffer can support.
    try {
      if (poseBuffer.getInternalBuffer().lastKey() - kPoseBufferSizeSec > observation.timestamp()) {
        return;
      }
    } catch (NoSuchElementException ex) {
      return;
    }

    // Retrieve the odometry pose at the time of the vision measurement.
    var sample = poseBuffer.getSample(observation.timestamp());
    if (sample.isEmpty()) {
      return;
    }

    Pose2d odometryAtTime = sample.get();

    // Transforms between odometry at measurement time and current odometry.
    Transform2d sampleToOdometryTransform = new Transform2d(odometryAtTime, odometryPose);
    Transform2d odometryToSampleTransform = new Transform2d(odometryPose, odometryAtTime);

    // Shift the current fused estimate back to the time of the vision measurement.
    Pose2d estimateAtTime = estimatedPose.plus(odometryToSampleTransform);

    // Build the vision measurement covariance (R) diagonal.
    double[] r = new double[3];
    for (int i = 0; i < 3; ++i) {
      double stdDev = observation.stdDevs().get(i, 0);
      r[i] = stdDev * stdDev;
    }

    // Closed-form Kalman gain for continuous filter with A = 0 and C = I.
    // See wpimath/algorithms.md.
    Matrix<N3, N3> visionK = new Matrix<>(Nat.N3(), Nat.N3());
    for (int row = 0; row < 3; ++row) {
      double q = qStdDevs.get(row, 0);
      if (q == 0.0) {
        visionK.set(row, row, 0.0);
      } else {
        visionK.set(row, row, q / (q + Math.sqrt(q * r[row])));
      }
    }

    // Transform from the back-shifted estimate to the vision measurement.
    Transform2d transform =
        new Transform2d(estimateAtTime, observation.visionPose().toPose2d());

    // Apply the Kalman gain to the transform.
    var kTimesTransform =
        visionK.times(
            VecBuilder.fill(
                transform.getX(), transform.getY(), transform.getRotation().getRadians()));

    Transform2d scaledTransform =
        new Transform2d(
            kTimesTransform.get(0, 0),
            kTimesTransform.get(1, 0),
            Rotation2d.fromRadians(kTimesTransform.get(2, 0)));

    // Update the estimate at measurement time, then bring it forward using odometry.
    estimatedPose = estimateAtTime.plus(scaledTransform).plus(sampleToOdometryTransform);
  }

  /** Odometry input at a given timestamp. */
  public record OdometryObservation(
      double timestamp, SwerveModulePosition[] wheelPositions, Optional<Rotation2d> gyroAngle) {}

  /** Vision pose measurement at a given timestamp. */
  public record VisionObservation(double timestamp, Pose3d visionPose, Matrix<N3, N1> stdDevs) {}
}