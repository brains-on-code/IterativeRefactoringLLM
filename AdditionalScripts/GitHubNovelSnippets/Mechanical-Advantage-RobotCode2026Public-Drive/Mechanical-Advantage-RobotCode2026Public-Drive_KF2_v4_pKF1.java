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

  private static final LoggedTunableNumber coastDelaySeconds =
      new LoggedTunableNumber("Drive/CoastWaitTimeSeconds", 0.5);
  private static final LoggedTunableNumber coastSpeedThresholdMetersPerSecond =
      new LoggedTunableNumber("Drive/CoastMetersPerSecThreshold", 0.05);

  private final Timer lastMovementTimer = new Timer();
  private boolean hasStartedCoast = false;

  private final SwerveDriveKinematics kinematics =
      new SwerveDriveKinematics(DriveConstants.moduleTranslations);

  public Drive(
      GyroIO gyroIO,
      ModuleIO frontLeftModuleIO,
      ModuleIO frontRightModuleIO,
      ModuleIO backLeftModuleIO,
      ModuleIO backRightModuleIO) {
    this.gyroIO = gyroIO;

    modules[0] = new Module(frontLeftModuleIO, 0);
    modules[1] = new Module(frontRightModuleIO, 1);
    modules[2] = new Module(backLeftModuleIO, 2);
    modules[3] = new Module(backRightModuleIO, 3);

    lastMovementTimer.start();
    for (Module module : modules) {
      module.brake();
    }
  }

  @Override
  public void periodic() {
    odometryLock.lock();
    gyroIO.updateInputs(gyroInputs);
    LoggedTracer.record("Drive/GyroInputs");
    Logger.processInputs("Drive/Gyro", gyroInputs);

    for (Module module : modules) {
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

    boolean anyModuleMovingAboveThreshold =
        Arrays.stream(modules)
            .anyMatch(
                module ->
                    Math.abs(module.getVelocityMetersPerSec())
                        > coastSpeedThresholdMetersPerSecond.get());

    if (anyModuleMovingAboveThreshold) {
      lastMovementTimer.reset();
    }

    if (DriverStation.isDisabled()) {
      if (hasStartedCoast || lastMovementTimer.hasElapsed(coastDelaySeconds.get())) {
        for (Module module : modules) {
          module.coast();
        }
        hasStartedCoast = true;
      } else {
        for (Module module : modules) {
          module.brake();
        }
      }
    } else {
      hasStartedCoast = false;
    }

    LoggedTracer.record("Drive/Periodic");
  }

  @Override
  public void periodicAfterScheduler() {
    for (Module module : modules) {
      module.periodicAfterScheduler();
    }
    LoggedTracer.record("Drive/PeriodicAfterScheduler");
  }

  public void runVelocity(ChassisSpeeds desiredChassisSpeeds) {
    ChassisSpeeds discretizedChassisSpeeds =
        ChassisSpeeds.discretize(desiredChassisSpeeds, Constants.loopPeriodSecs);

    SwerveModuleState[] desiredModuleStates =
        kinematics.toSwerveModuleStates(discretizedChassisSpeeds);
    SwerveDriveKinematics.desaturateWheelSpeeds(
        desiredModuleStates, DriveConstants.maxLinearSpeed);

    Logger.recordOutput("SwerveStates/Setpoints", desiredModuleStates);
    Logger.recordOutput("SwerveChassisSpeeds/Setpoints", discretizedChassisSpeeds);

    for (int moduleIndex = 0; moduleIndex < modules.length; moduleIndex++) {
      modules[moduleIndex].runSetpoint(desiredModuleStates[moduleIndex]);
    }

    Logger.recordOutput("SwerveStates/SetpointsOptimized", desiredModuleStates);
  }

  public void runCharacterization(double driveOutput) {
    for (Module module : modules) {
      module.runCharacterization(driveOutput);
    }
  }

  public void stop() {
    runVelocity(new ChassisSpeeds());
  }

  public void stopWithX() {
    Rotation2d[] xLockHeadings = new Rotation2d[modules.length];
    for (int moduleIndex = 0; moduleIndex < modules.length; moduleIndex++) {
      xLockHeadings[moduleIndex] = DriveConstants.moduleTranslations[moduleIndex].getAngle();
    }
    kinematics.resetHeadings(xLockHeadings);
    stop();
  }

  @AutoLogOutput(key = "SwerveStates/Measured")
  private SwerveModuleState[] getModuleStates() {
    SwerveModuleState[] moduleStates = new SwerveModuleState[modules.length];
    for (int moduleIndex = 0; moduleIndex < modules.length; moduleIndex++) {
      moduleStates[moduleIndex] = modules[moduleIndex].getState();
    }
    return moduleStates;
  }

  private SwerveModulePosition[] getModulePositions() {
    SwerveModulePosition[] modulePositions = new SwerveModulePosition[modules.length];
    for (int moduleIndex = 0; moduleIndex < modules.length; moduleIndex++) {
      modulePositions[moduleIndex] = modules[moduleIndex].getPosition();
    }
    return modulePositions;
  }

  @AutoLogOutput(key = "SwerveChassisSpeeds/Measured")
  private ChassisSpeeds getChassisSpeeds() {
    return kinematics.toChassisSpeeds(getModuleStates());
  }

  public double[] getWheelRadiusCharacterizationPositions() {
    double[] wheelRadiusPositions = new double[modules.length];
    for (int moduleIndex = 0; moduleIndex < modules.length; moduleIndex++) {
      wheelRadiusPositions[moduleIndex] =
          modules[moduleIndex].getWheelRadiusCharacterizationPosition();
    }
    return wheelRadiusPositions;
  }
}