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
  private final Module[] swerveModules = new Module[4];

  private final Alert gyroDisconnectedAlert =
      new Alert("Disconnected gyro, using kinematics as fallback.", AlertType.kError);

  private static final LoggedTunableNumber coastDelaySeconds =
      new LoggedTunableNumber("Drive/CoastWaitTimeSeconds", 0.5);
  private static final LoggedTunableNumber coastSpeedThresholdMetersPerSecond =
      new LoggedTunableNumber("Drive/CoastMetersPerSecThreshold", 0.05);

  private final Timer lastMovementTimer = new Timer();
  private boolean hasStartedCoast = false;

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
    odometryLock.lock();
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

    boolean anyModuleMovingAboveThreshold =
        Arrays.stream(swerveModules)
            .anyMatch(
                module ->
                    Math.abs(module.getVelocityMetersPerSec())
                        > coastSpeedThresholdMetersPerSecond.get());

    if (anyModuleMovingAboveThreshold) {
      lastMovementTimer.reset();
    }

    if (DriverStation.isDisabled()) {
      if (hasStartedCoast || lastMovementTimer.hasElapsed(coastDelaySeconds.get())) {
        for (Module module : swerveModules) {
          module.coast();
        }
        hasStartedCoast = true;
      } else {
        for (Module module : swerveModules) {
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
    for (Module module : swerveModules) {
      module.periodicAfterScheduler();
    }
    LoggedTracer.record("Drive/PeriodicAfterScheduler");
  }

  public void runVelocity(ChassisSpeeds desiredChassisSpeeds) {
    ChassisSpeeds discretizedChassisSpeeds =
        ChassisSpeeds.discretize(desiredChassisSpeeds, Constants.loopPeriodSecs);

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

  public void runCharacterization(double driveOutput) {
    for (Module module : swerveModules) {
      module.runCharacterization(driveOutput);
    }
  }

  public void stop() {
    runVelocity(new ChassisSpeeds());
  }

  public void stopWithX() {
    Rotation2d[] xLockHeadings = new Rotation2d[swerveModules.length];
    for (int moduleIndex = 0; moduleIndex < swerveModules.length; moduleIndex++) {
      xLockHeadings[moduleIndex] = DriveConstants.moduleTranslations[moduleIndex].getAngle();
    }
    swerveKinematics.resetHeadings(xLockHeadings);
    stop();
  }

  @AutoLogOutput(key = "SwerveStates/Measured")
  private SwerveModuleState[] getModuleStates() {
    SwerveModuleState[] moduleStates = new SwerveModuleState[swerveModules.length];
    for (int moduleIndex = 0; moduleIndex < swerveModules.length; moduleIndex++) {
      moduleStates[moduleIndex] = swerveModules[moduleIndex].getState();
    }
    return moduleStates;
  }

  private SwerveModulePosition[] getModulePositions() {
    SwerveModulePosition[] modulePositions = new SwerveModulePosition[swerveModules.length];
    for (int moduleIndex = 0; moduleIndex < swerveModules.length; moduleIndex++) {
      modulePositions[moduleIndex] = swerveModules[moduleIndex].getPosition();
    }
    return modulePositions;
  }

  @AutoLogOutput(key = "SwerveChassisSpeeds/Measured")
  private ChassisSpeeds getChassisSpeeds() {
    return swerveKinematics.toChassisSpeeds(getModuleStates());
  }

  public double[] getWheelRadiusCharacterizationPositions() {
    double[] wheelRadiusPositions = new double[swerveModules.length];
    for (int moduleIndex = 0; moduleIndex < swerveModules.length; moduleIndex++) {
      wheelRadiusPositions[moduleIndex] =
          swerveModules[moduleIndex].getWheelRadiusCharacterizationPosition();
    }
    return wheelRadiusPositions;
  }
}