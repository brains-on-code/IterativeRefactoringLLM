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
    setAllModulesBrake();
  }

  @Override
  public void periodic() {
    updateSensorsAndModules();
    clearSetpointLoggingWhenDisabled();
    updateRobotState();
    updateGyroAlert();
    updateCoastOrBrake();
    LoggedTracer.record("Drive/Periodic");
  }

  @Override
  public void periodicAfterScheduler() {
    for (var module : modules) {
      module.periodicAfterScheduler();
    }
    LoggedTracer.record("Drive/PeriodicAfterScheduler");
  }

  public void runVelocity(ChassisSpeeds speeds) {
    ChassisSpeeds discreteSpeeds = ChassisSpeeds.discretize(speeds, Constants.loopPeriodSecs);
    SwerveModuleState[] setpointStates = kinematics.toSwerveModuleStates(discreteSpeeds);
    SwerveDriveKinematics.desaturateWheelSpeeds(setpointStates, DriveConstants.maxLinearSpeed);

    Logger.recordOutput("SwerveStates/Setpoints", setpointStates);
    Logger.recordOutput("SwerveChassisSpeeds/Setpoints", discreteSpeeds);

    for (int i = 0; i < modules.length; i++) {
      modules[i].runSetpoint(setpointStates[i]);
    }

    Logger.recordOutput("SwerveStates/SetpointsOptimized", setpointStates);
  }

  public void runCharacterization(double output) {
    for (var module : modules) {
      module.runCharacterization(output);
    }
  }

  public void stop() {
    runVelocity(new ChassisSpeeds());
  }

  public void stopWithX() {
    Rotation2d[] headings = new Rotation2d[modules.length];
    for (int i = 0; i < modules.length; i++) {
      headings[i] = DriveConstants.moduleTranslations[i].getAngle();
    }
    kinematics.resetHeadings(headings);
    stop();
  }

  public double[] getWheelRadiusCharacterizationPositions() {
    double[] values = new double[modules.length];
    for (int i = 0; i < modules.length; i++) {
      values[i] = modules[i].getWheelRadiusCharacterizationPosition();
    }
    return values;
  }

  private void updateSensorsAndModules() {
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
  }

  private void clearSetpointLoggingWhenDisabled() {
    if (!DriverStation.isDisabled()) {
      return;
    }

    Logger.recordOutput("SwerveStates/Setpoints", new SwerveModuleState[] {});
    Logger.recordOutput("SwerveStates/SetpointsOptimized", new SwerveModuleState[] {});
  }

  private void updateRobotState() {
    RobotState.getInstance()
        .addOdometryObservation(
            new OdometryObservation(
                Timer.getTimestamp(),
                getModulePositions(),
                Optional.ofNullable(gyroInputs.connected ? gyroInputs.yawPosition : null)));

    RobotState.getInstance().setRobotVelocity(getChassisSpeeds());
  }

  private void updateGyroAlert() {
    gyroDisconnectedAlert.set(Robot.showHardwareAlerts() && !gyroInputs.connected);
  }

  private void updateCoastOrBrake() {
    boolean anyModuleMoving =
        Arrays.stream(modules)
            .anyMatch(
                module ->
                    Math.abs(module.getVelocityMetersPerSec())
                        > coastMetersPerSecondThreshold.get());

    if (anyModuleMoving) {
      lastMovementTimer.reset();
    }

    if (!DriverStation.isDisabled()) {
      hasStartedCoast = false;
      return;
    }

    boolean shouldCoast = hasStartedCoast || lastMovementTimer.hasElapsed(coastWaitTime.get());

    if (shouldCoast) {
      setAllModulesCoast();
      hasStartedCoast = true;
    } else {
      setAllModulesBrake();
    }
  }

  private void setAllModulesCoast() {
    for (var module : modules) {
      module.coast();
    }
  }

  private void setAllModulesBrake() {
    for (var module : modules) {
      module.brake();
    }
  }

  @AutoLogOutput(key = "SwerveStates/Measured")
  private SwerveModuleState[] getModuleStates() {
    SwerveModuleState[] states = new SwerveModuleState[modules.length];
    for (int i = 0; i < modules.length; i++) {
      states[i] = modules[i].getState();
    }
    return states;
  }

  private SwerveModulePosition[] getModulePositions() {
    SwerveModulePosition[] positions = new SwerveModulePosition[modules.length];
    for (int i = 0; i < modules.length; i++) {
      positions[i] = modules[i].getPosition();
    }
    return positions;
  }

  @AutoLogOutput(key = "SwerveChassisSpeeds/Measured")
  private ChassisSpeeds getChassisSpeeds() {
    return kinematics.toChassisSpeeds(getModuleStates());
  }
}