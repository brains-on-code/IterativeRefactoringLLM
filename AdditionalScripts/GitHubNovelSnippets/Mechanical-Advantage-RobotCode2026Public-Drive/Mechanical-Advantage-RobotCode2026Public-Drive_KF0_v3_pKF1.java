// Copyright (c) 2025-2026 Littleton Robotics
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package org.littletonrobotics.frc2026.subsystems.drive;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.littletonrobotics.frc2026.Constants;
import org.littletonrobotics.frc2026.Robot;
import org.littletonrobotics.frc2026.RobotState;
import org.littletonrobotics.frc2026.RobotState.OdometryObservation;
import org.littletonrobotics.frc2026.util.FullSubsystem;
import org.littletonrobotics.frc2026.util.LoggedTracer;
import org.littletonrobotics.frc2026.util.LoggedTunableNumber;
import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;

public class Drive extends FullSubsystem {
  static final Lock odometryLock = new ReentrantLock();

  private final GyroIO gyroIO;
  private final GyroIOInputsAutoLogged gyroInputs = new GyroIOInputsAutoLogged();

  // Module order: front-left, front-right, back-left, back-right
  private final Module[] swerveModules = new Module[4];

  private final Alert gyroDisconnectedAlert =
      new Alert("Disconnected gyro, using kinematics as fallback.", AlertType.kError);

  private static final LoggedTunableNumber coastDelaySeconds =
      new LoggedTunableNumber("Drive/CoastWaitTimeSeconds", 0.5);
  private static final LoggedTunableNumber coastSpeedThresholdMetersPerSecond =
      new LoggedTunableNumber("Drive/CoastMetersPerSecThreshold", 0.05);

  private final Timer lastMovementTimer = new Timer();
  private boolean hasEnteredCoastMode = false;

  private final SwerveDriveKinematics swerveKinematics =
      new SwerveDriveKinematics(DriveConstants.moduleTranslations);

  public Drive(
      GyroIO gyroIO,
      ModuleIO frontLeftModuleIO,
      ModuleIO frontRightModuleIO,
      ModuleIO backLeftModuleIO,
      ModuleIO backRightModuleIO) {
    this.gyroIO = gyroIO;

    swerveModules[0] = new Module(frontLeftModuleIO, 0);
    swerveModules[1] = new Module(frontRightModuleIO, 1);
    swerveModules[2] = new Module(backLeftModuleIO, 2);
    swerveModules[3] = new Module(backRightModuleIO, 3);

    lastMovementTimer.start();
    for (Module module : swerveModules) {
      module.brake();
    }
  }

  @Override
  public void periodic() {
    odometryLock.lock(); // Prevents odometry updates while reading data
    gyroIO.updateInputs(gyroInputs);
    LoggedTracer.record("Drive/GyroInputs");
    Logger.processInputs("Drive/Gyro", gyroInputs);

    for (Module module : swerveModules) {
      module.periodic();
    }
    odometryLock.unlock();

    if (DriverStation.isDisabled()) {
      Logger.recordOutput("SwerveStates/Setpoints", new SwerveModuleState[] {});
      Logger.recordOutput("SwerveStates/SetpointsOptimized", new SwerveModuleState[] {});
    }

    RobotState.getInstance()
        .addOdometryObservation(
            new OdometryObservation(
                Timer.getTimestamp(),
                getModulePositions(),
                Optional.ofNullable(gyroInputs.connected ? gyroInputs.yawPosition : null)));
    RobotState.getInstance().setRobotVelocity(getChassisSpeeds());

    gyroDisconnectedAlert.set(Robot.showHardwareAlerts() && !gyroInputs.connected);

    boolean anyModuleAboveCoastThreshold =
        Arrays.stream(swerveModules)
            .anyMatch(
                module ->
                    Math.abs(module.getVelocityMetersPerSec())
                        > coastSpeedThresholdMetersPerSecond.get());

    if (anyModuleAboveCoastThreshold) {
      lastMovementTimer.reset();
    }

    if (DriverStation.isDisabled()) {
      if (hasEnteredCoastMode || lastMovementTimer.hasElapsed(coastDelaySeconds.get())) {
        for (Module module : swerveModules) {
          module.coast();
        }
        hasEnteredCoastMode = true;
      } else {
        for (Module module : swerveModules) {
          module.brake();
        }
      }
    } else {
      hasEnteredCoastMode = false;
    }

    LoggedTracer.record("Drive/Periodic");
  }

  @Override
  public void periodicAfterScheduler() {
    for (Module module : swerveModules) {
      module.periodicAfterScheduler();
    }
    LoggedTracer.record("Drive/PeriodicAfterScheduler");
  }

  /**
   * Runs the drive at the desired velocity.
   *
   * @param chassisSpeeds Speeds in meters/sec
   */
  public void runVelocity(ChassisSpeeds chassisSpeeds) {
    ChassisSpeeds discretizedChassisSpeeds =
        ChassisSpeeds.discretize(chassisSpeeds, Constants.loopPeriodSecs);

    SwerveModuleState[] desiredModuleStates =
        swerveKinematics.toSwerveModuleStates(discretizedChassisSpeeds);
    SwerveDriveKinematics.desaturateWheelSpeeds(
        desiredModuleStates, DriveConstants.maxLinearSpeed);

    Logger.recordOutput("SwerveStates/Setpoints", desiredModuleStates);
    Logger.recordOutput("SwerveChassisSpeeds/Setpoints", discretizedChassisSpeeds);

    for (int moduleIndex = 0; moduleIndex < swerveModules.length; moduleIndex++) {
      swerveModules[moduleIndex].runSetpoint(desiredModuleStates[moduleIndex]);
    }

    Logger.recordOutput("SwerveStates/SetpointsOptimized", desiredModuleStates);
  }

  /** Runs the drive in a straight line with the specified drive output. */
  public void runCharacterization(double driveOutput) {
    for (Module module : swerveModules) {
      module.runCharacterization(driveOutput);
    }
  }

  /** Stops the drive. */
  public void stop() {
    runVelocity(new ChassisSpeeds());
  }

  /**
   * Stops the drive and turns the modules to an X arrangement to resist movement. The modules will
   * return to their normal orientations the next time a nonzero velocity is requested.
   */
  public void stopWithX() {
    Rotation2d[] xLockHeadings = new Rotation2d[swerveModules.length];
    for (int moduleIndex = 0; moduleIndex < swerveModules.length; moduleIndex++) {
      xLockHeadings[moduleIndex] = DriveConstants.moduleTranslations[moduleIndex].getAngle();
    }
    swerveKinematics.resetHeadings(xLockHeadings);
    stop();
  }

  /** Returns the module states (turn angles and drive velocities) for all of the modules. */
  @AutoLogOutput(key = "SwerveStates/Measured")
  private SwerveModuleState[] getModuleStates() {
    SwerveModuleState[] measuredStates = new SwerveModuleState[swerveModules.length];
    for (int moduleIndex = 0; moduleIndex < swerveModules.length; moduleIndex++) {
      measuredStates[moduleIndex] = swerveModules[moduleIndex].getState();
    }
    return measuredStates;
  }

  /** Returns the module positions (turn angles and drive positions) for all of the modules. */
  private SwerveModulePosition[] getModulePositions() {
    SwerveModulePosition[] measuredPositions = new SwerveModulePosition[swerveModules.length];
    for (int moduleIndex = 0; moduleIndex < swerveModules.length; moduleIndex++) {
      measuredPositions[moduleIndex] = swerveModules[moduleIndex].getPosition();
    }
    return measuredPositions;
  }

  /** Returns the measured chassis speeds of the robot. */
  @AutoLogOutput(key = "SwerveChassisSpeeds/Measured")
  private ChassisSpeeds getChassisSpeeds() {
    return swerveKinematics.toChassisSpeeds(getModuleStates());
  }

  /** Returns the position of each module in radians. */
  public double[] getWheelRadiusCharacterizationPositions() {
    double[] wheelRadiusPositions = new double[swerveModules.length];
    for (int moduleIndex = 0; moduleIndex < swerveModules.length; moduleIndex++) {
      wheelRadiusPositions[moduleIndex] =
          swerveModules[moduleIndex].getWheelRadiusCharacterizationPosition();
    }
    return wheelRadiusPositions;
  }
}