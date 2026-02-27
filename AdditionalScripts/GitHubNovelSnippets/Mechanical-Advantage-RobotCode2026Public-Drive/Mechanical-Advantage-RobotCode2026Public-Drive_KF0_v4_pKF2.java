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
  /** Synchronizes odometry updates with sensor reads. */
  static final Lock odometryLock = new ReentrantLock();

  private final GyroIO gyroIO;
  private final GyroIOInputsAutoLogged gyroInputs = new GyroIOInputsAutoLogged();

  /** Swerve modules in order: front-left, front-right, back-left, back-right. */
  private final Module[] modules = new Module[4];

  private final Alert gyroDisconnectedAlert =
      new Alert("Disconnected gyro, using kinematics as fallback.", AlertType.kError);

  private static final LoggedTunableNumber coastWaitTime =
      new LoggedTunableNumber("Drive/CoastWaitTimeSeconds", 0.5);

  private static final LoggedTunableNumber coastMetersPerSecondThreshold =
      new LoggedTunableNumber("Drive/CoastMetersPerSecThreshold", 0.05);

  private final Timer lastMovementTimer = new Timer();
  private boolean hasStartedCoast = false;

  private final SwerveDriveKinematics kinematics =
      new SwerveDriveKinematics(DriveConstants.moduleTranslations);

  public Drive(
      GyroIO gyroIO,
      ModuleIO flModuleIO,
      ModuleIO frModuleIO,
      ModuleIO blModuleIO,
      ModuleIO brModuleIO) {
    this.gyroIO = gyroIO;

    modules[0] = new Module(flModuleIO, 0);
    modules[1] = new Module(frModuleIO, 1);
    modules[2] = new Module(blModuleIO, 2);
    modules[3] = new Module(brModuleIO, 3);

    lastMovementTimer.start();
    for (var module : modules) {
      module.brake();
    }
  }

  @Override
  public void periodic() {
    // --- Sensor updates (gyro + modules), synchronized with odometry ---
    odometryLock.lock();
    try {
      gyroIO.updateInputs(gyroInputs);
      LoggedTracer.record("Drive/GyroInputs");
      Logger.processInputs("Drive/Gyro", gyroInputs);

      for (var module : modules) {
        module.periodic();
      }
    } finally {
      odometryLock.unlock();
    }

    // Clear setpoint logs while disabled so stale values don't appear
    if (DriverStation.isDisabled()) {
      Logger.recordOutput("SwerveStates/Setpoints", new SwerveModuleState[] {});
      Logger.recordOutput("SwerveStates/SetpointsOptimized", new SwerveModuleState[] {});
    }

    // --- Odometry and velocity update ---
    RobotState.getInstance()
        .addOdometryObservation(
            new OdometryObservation(
                Timer.getTimestamp(),
                getModulePositions(),
                Optional.ofNullable(gyroInputs.connected ? gyroInputs.yawPosition : null)));
    RobotState.getInstance().setRobotVelocity(getChassisSpeeds());

    // --- Hardware alerts ---
    gyroDisconnectedAlert.set(Robot.showHardwareAlerts() && !gyroInputs.connected);

    // --- Brake / coast management ---
    boolean anyModuleMoving =
        Arrays.stream(modules)
            .anyMatch(
                module ->
                    Math.abs(module.getVelocityMetersPerSec())
                        > coastMetersPerSecondThreshold.get());
    if (anyModuleMoving) {
      lastMovementTimer.reset();
    }

    if (DriverStation.isDisabled()) {
      boolean shouldCoast =
          hasStartedCoast || lastMovementTimer.hasElapsed(coastWaitTime.get());

      for (var module : modules) {
        if (shouldCoast) {
          module.coast();
        } else {
          module.brake();
        }
      }

      if (shouldCoast) {
        hasStartedCoast = true;
      }
    } else {
      hasStartedCoast = false;
    }

    LoggedTracer.record("Drive/Periodic");
  }

  @Override
  public void periodicAfterScheduler() {
    for (var module : modules) {
      module.periodicAfterScheduler();
    }
    LoggedTracer.record("Drive/PeriodicAfterScheduler");
  }

  /**
   * Command the drive with desired chassis speeds.
   *
   * @param speeds desired chassis speeds in meters/sec and radians/sec
   */
  public void runVelocity(ChassisSpeeds speeds) {
    // Convert continuous speeds into per-loop motion and compute module states
    ChassisSpeeds discreteSpeeds = ChassisSpeeds.discretize(speeds, Constants.loopPeriodSecs);
    SwerveModuleState[] setpointStates = kinematics.toSwerveModuleStates(discreteSpeeds);
    SwerveDriveKinematics.desaturateWheelSpeeds(setpointStates, DriveConstants.maxLinearSpeed);

    Logger.recordOutput("SwerveStates/Setpoints", setpointStates);
    Logger.recordOutput("SwerveChassisSpeeds/Setpoints", discreteSpeeds);

    // Apply setpoints to modules (runSetpoint may modify the states)
    for (int i = 0; i < modules.length; i++) {
      modules[i].runSetpoint(setpointStates[i]);
    }

    Logger.recordOutput("SwerveStates/SetpointsOptimized", setpointStates);
  }

  /** Run all modules in a straight line for drive characterization. */
  public void runCharacterization(double output) {
    for (int i = 0; i < modules.length; i++) {
      modules[i].runCharacterization(output);
    }
  }

  /** Stop the drive by commanding zero chassis speeds. */
  public void stop() {
    runVelocity(new ChassisSpeeds());
  }

  /**
   * Stop the drive and point the modules in an "X" to passively resist motion.
   *
   * <p>Modules will return to normal orientation on the next nonzero velocity command.
   */
  public void stopWithX() {
    Rotation2d[] headings = new Rotation2d[modules.length];
    for (int i = 0; i < modules.length; i++) {
      headings[i] = DriveConstants.moduleTranslations[i].getAngle();
    }
    kinematics.resetHeadings(headings);
    stop();
  }

  /** Current module states (turn angles and drive velocities) for all modules. */
  @AutoLogOutput(key = "SwerveStates/Measured")
  private SwerveModuleState[] getModuleStates() {
    SwerveModuleState[] states = new SwerveModuleState[modules.length];
    for (int i = 0; i < modules.length; i++) {
      states[i] = modules[i].getState();
    }
    return states;
  }

  /** Current module positions (turn angles and drive positions) for all modules. */
  private SwerveModulePosition[] getModulePositions() {
    SwerveModulePosition[] positions = new SwerveModulePosition[modules.length];
    for (int i = 0; i < modules.length; i++) {
      positions[i] = modules[i].getPosition();
    }
    return positions;
  }

  /** Measured chassis speeds of the robot. */
  @AutoLogOutput(key = "SwerveChassisSpeeds/Measured")
  private ChassisSpeeds getChassisSpeeds() {
    return kinematics.toChassisSpeeds(getModuleStates());
  }

  /** Wheel positions used for wheel radius characterization, in radians. */
  public double[] getWheelRadiusCharacterizationPositions() {
    double[] values = new double[modules.length];
    for (int i = 0; i < modules.length; i++) {
      values[i] = modules[i].getWheelRadiusCharacterizationPosition();
    }
    return values;
  }
}