package org.littletonrobotics.frc2026.subsystems.drive;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.var16.ChassisSpeeds;
import edu.wpi.first.math.var16.SwerveDriveKinematics;
import edu.wpi.first.math.var16.SwerveModulePosition;
import edu.wpi.first.math.var16.SwerveModuleState;
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

public class DriveSubsystem extends FullSubsystem {
  private static final int SWERVE_MODULE_COUNT = 4;

  private static final Lock ioUpdateLock = new ReentrantLock();

  private final GyroIO gyroIO;
  private final GyroIOInputsAutoLogged gyroInputs = new GyroIOInputsAutoLogged();
  private final Module[] swerveModules = new Module[SWERVE_MODULE_COUNT];

  private final Alert gyroDisconnectedAlert =
      new Alert("Disconnected gyro, using kinematics as fallback.", AlertType.kError);

  private static final LoggedTunableNumber coastDelaySeconds =
      new LoggedTunableNumber("Drive/CoastWaitTimeSeconds", 0.5);
  private static final LoggedTunableNumber coastSpeedThresholdMetersPerSecond =
      new LoggedTunableNumber("Drive/CoastMetersPerSecThreshold", 0.05);

  private final Timer coastTimer = new Timer();
  private boolean hasCoastedSinceDisable = false;

  private SwerveDriveKinematics swerveKinematics =
      new SwerveDriveKinematics(DriveConstants.moduleTranslations);

  public DriveSubsystem(
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

    coastTimer.start();
    for (Module module : swerveModules) {
      module.brake();
    }
  }

  @Override
  public void periodic() {
    ioUpdateLock.lock();
    gyroIO.updateInputs(gyroInputs);
    LoggedTracer.record("Drive/GyroInputs");
    Logger.processInputs("Drive/Gyro", gyroInputs);
    for (Module module : swerveModules) {
      module.periodic();
    }
    ioUpdateLock.unlock();

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
    RobotState.getInstance().setRobotVelocity(getMeasuredChassisSpeeds());

    gyroDisconnectedAlert.set(Robot.showHardwareAlerts() && !gyroInputs.connected);

    boolean anyModuleAboveCoastSpeedThreshold =
        Arrays.stream(swerveModules)
            .anyMatch(
                module ->
                    Math.abs(module.getVelocityMetersPerSec())
                        > coastSpeedThresholdMetersPerSecond.get());

    if (anyModuleAboveCoastSpeedThreshold) {
      coastTimer.reset();
    }

    if (DriverStation.isDisabled()) {
      if (hasCoastedSinceDisable || coastTimer.hasElapsed(coastDelaySeconds.get())) {
        for (Module module : swerveModules) {
          module.coast();
        }
        hasCoastedSinceDisable = true;
      } else {
        for (Module module : swerveModules) {
          module.brake();
        }
      }
    } else {
      hasCoastedSinceDisable = false;
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

  public void drive(ChassisSpeeds desiredChassisSpeeds) {
    ChassisSpeeds discretizedChassisSpeeds =
        ChassisSpeeds.discretize(desiredChassisSpeeds, Constants.loopPeriodSecs);
    SwerveModuleState[] desiredModuleStates =
        swerveKinematics.toSwerveModuleStates(discretizedChassisSpeeds);
    SwerveDriveKinematics.desaturateWheelSpeeds(
        desiredModuleStates, DriveConstants.maxLinearSpeed);

    Logger.recordOutput("SwerveStates/Setpoints", desiredModuleStates);
    Logger.recordOutput("SwerveChassisSpeeds/Setpoints", discretizedChassisSpeeds);

    for (int moduleIndex = 0; moduleIndex < SWERVE_MODULE_COUNT; moduleIndex++) {
      swerveModules[moduleIndex].runSetpoint(desiredModuleStates[moduleIndex]);
    }

    Logger.recordOutput("SwerveStates/SetpointsOptimized", desiredModuleStates);
  }

  public void setDriveVoltage(double voltage) {
    for (int moduleIndex = 0; moduleIndex < SWERVE_MODULE_COUNT; moduleIndex++) {
      swerveModules[moduleIndex].setDriveVoltage(voltage);
    }
  }

  public void stop() {
    drive(new ChassisSpeeds());
  }

  public void resetModuleHeadingsToTranslations() {
    Rotation2d[] moduleHeadings = new Rotation2d[SWERVE_MODULE_COUNT];
    for (int moduleIndex = 0; moduleIndex < SWERVE_MODULE_COUNT; moduleIndex++) {
      moduleHeadings[moduleIndex] = DriveConstants.moduleTranslations[moduleIndex].getAngle();
    }
    swerveKinematics.resetHeadings(moduleHeadings);
    stop();
  }

  @AutoLogOutput(key = "SwerveStates/Measured")
  private SwerveModuleState[] getMeasuredModuleStates() {
    SwerveModuleState[] measuredModuleStates = new SwerveModuleState[SWERVE_MODULE_COUNT];
    for (int moduleIndex = 0; moduleIndex < SWERVE_MODULE_COUNT; moduleIndex++) {
      measuredModuleStates[moduleIndex] = swerveModules[moduleIndex].getState();
    }
    return measuredModuleStates;
  }

  private SwerveModulePosition[] getModulePositions() {
    SwerveModulePosition[] modulePositions = new SwerveModulePosition[SWERVE_MODULE_COUNT];
    for (int moduleIndex = 0; moduleIndex < SWERVE_MODULE_COUNT; moduleIndex++) {
      modulePositions[moduleIndex] = swerveModules[moduleIndex].getPosition();
    }
    return modulePositions;
  }

  @AutoLogOutput(key = "SwerveChassisSpeeds/Measured")
  private ChassisSpeeds getMeasuredChassisSpeeds() {
    return swerveKinematics.toChassisSpeeds(getMeasuredModuleStates());
  }

  public double[] getWheelRadiusCharacterizationPositions() {
    double[] wheelRadiusCharacterizationPositions = new double[SWERVE_MODULE_COUNT];
    for (int moduleIndex = 0; moduleIndex < SWERVE_MODULE_COUNT; moduleIndex++) {
      wheelRadiusCharacterizationPositions[moduleIndex] =
          swerveModules[moduleIndex].getWheelRadiusCharacterizationPosition();
    }
    return wheelRadiusCharacterizationPositions;
  }
}