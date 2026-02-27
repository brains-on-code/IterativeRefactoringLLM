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
  private static final int MODULE_COUNT = 4;

  static final Lock odometryLock = new ReentrantLock();

  private final GyroIO gyroIO;
  private final GyroIOInputsAutoLogged gyroInputs = new GyroIOInputsAutoLogged();
  private final Module[] modules = new Module[MODULE_COUNT]; // FL, FR, BL, BR

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
    setBrakeModeForAllModules();
  }

  @Override
  public void periodic() {
    updateInputsAndModules();
    clearSetpointsWhenDisabled();
    updateRobotState();
    updateGyroAlert();
    updateBrakeMode();
    LoggedTracer.record("Drive/Periodic");
  }

  @Override
  public void periodicAfterScheduler() {
    for (Module module : modules) {
      module.periodicAfterScheduler();
    }
    LoggedTracer.record("Drive/PeriodicAfterScheduler");
  }

  /**
   * Runs the drive at the desired velocity.
   *
   * @param speeds Speeds in meters/sec
   */
  public void runVelocity(ChassisSpeeds speeds) {
    ChassisSpeeds discreteSpeeds = ChassisSpeeds.discretize(speeds, Constants.loopPeriodSecs);
    SwerveModuleState[] setpointStates = kinematics.toSwerveModuleStates(discreteSpeeds);
    SwerveDriveKinematics.desaturateWheelSpeeds(setpointStates, DriveConstants.maxLinearSpeed);

    Logger.recordOutput("SwerveStates/Setpoints", setpointStates);
    Logger.recordOutput("SwerveChassisSpeeds/Setpoints", discreteSpeeds);

    for (int i = 0; i < MODULE_COUNT; i++) {
      modules[i].runSetpoint(setpointStates[i]);
    }

    Logger.recordOutput("SwerveStates/SetpointsOptimized", setpointStates);
  }

  /** Runs the drive in a straight line with the specified drive output. */
  public void runCharacterization(double output) {
    for (Module module : modules) {
      module.runCharacterization(output);
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
    Rotation2d[] headings = new Rotation2d[MODULE_COUNT];
    for (int i = 0; i < MODULE_COUNT; i++) {
      headings[i] = DriveConstants.moduleTranslations[i].getAngle();
    }
    kinematics.resetHeadings(headings);
    stop();
  }

  /** Returns the position of each module in radians. */
  public double[] getWheelRadiusCharacterizationPositions() {
    double[] values = new double[MODULE_COUNT];
    for (int i = 0; i < MODULE_COUNT; i++) {
      values[i] = modules[i].getWheelRadiusCharacterizationPosition();
    }
    return values;
  }

  @AutoLogOutput(key = "SwerveStates/Measured")
  private SwerveModuleState[] getModuleStates() {
    SwerveModuleState[] states = new SwerveModuleState[MODULE_COUNT];
    for (int i = 0; i < MODULE_COUNT; i++) {
      states[i] = modules[i].getState();
    }
    return states;
  }

  private SwerveModulePosition[] getModulePositions() {
    SwerveModulePosition[] positions = new SwerveModulePosition[MODULE_COUNT];
    for (int i = 0; i < MODULE_COUNT; i++) {
      positions[i] = modules[i].getPosition();
    }
    return positions;
  }

  @AutoLogOutput(key = "SwerveChassisSpeeds/Measured")
  private ChassisSpeeds getChassisSpeeds() {
    return kinematics.toChassisSpeeds(getModuleStates());
  }

  private void updateInputsAndModules() {
    odometryLock.lock();
    try {
      gyroIO.updateInputs(gyroInputs);
      LoggedTracer.record("Drive/GyroInputs");
      Logger.processInputs("Drive/Gyro", gyroInputs);

      for (Module module : modules) {
        module.periodic();
      }
    } finally {
      odometryLock.unlock();
    }
  }

  private void clearSetpointsWhenDisabled() {
    if (!DriverStation.isDisabled()) {
      return;
    }

    Logger.recordOutput("SwerveStates/Setpoints", new SwerveModuleState[] {});
    Logger.recordOutput("SwerveStates/SetpointsOptimized", new SwerveModuleState[] {});
  }

  private void updateRobotState() {
    RobotState robotState = RobotState.getInstance();

    robotState.addOdometryObservation(
        new OdometryObservation(
            Timer.getTimestamp(),
            getModulePositions(),
            gyroInputs.connected ? Optional.of(gyroInputs.yawPosition) : Optional.empty()));

    robotState.setRobotVelocity(getChassisSpeeds());
  }

  private void updateGyroAlert() {
    gyroDisconnectedAlert.set(Robot.showHardwareAlerts() && !gyroInputs.connected);
  }

  private void updateBrakeMode() {
    if (modulesMovingAboveThreshold()) {
      lastMovementTimer.reset();
    }

    if (DriverStation.isDisabled()) {
      if (hasStartedCoast || lastMovementTimer.hasElapsed(coastWaitTime.get())) {
        setCoastModeForAllModules();
        hasStartedCoast = true;
      } else {
        setBrakeModeForAllModules();
      }
    } else {
      hasStartedCoast = false;
    }
  }

  private boolean modulesMovingAboveThreshold() {
    double threshold = coastMetersPerSecondThreshold.get();
    return Arrays.stream(modules)
        .anyMatch(module -> Math.abs(module.getVelocityMetersPerSec()) > threshold);
  }

  private void setBrakeModeForAllModules() {
    for (Module module : modules) {
      module.brake();
    }
  }

  private void setCoastModeForAllModules() {
    for (Module module : modules) {
      module.coast();
    }
  }
}