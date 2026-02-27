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
  static final Lock ioLock = new ReentrantLock();

  private final GyroIO gyroIO;
  private final GyroIOInputsAutoLogged gyroInputs = new GyroIOInputsAutoLogged();
  private final Module[] modules = new Module[4];

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
    modules[0] = new Module(frontLeftModuleIO, 0);
    modules[1] = new Module(frontRightModuleIO, 1);
    modules[2] = new Module(backLeftModuleIO, 2);
    modules[3] = new Module(backRightModuleIO, 3);

    coastTimer.start();
    for (Module module : modules) {
      module.brake();
    }
  }

  @Override
  public void periodic() {
    ioLock.lock();
    gyroIO.updateInputs(gyroInputs);
    LoggedTracer.record("Drive/GyroInputs");
    Logger.processInputs("Drive/Gyro", gyroInputs);
    for (Module module : modules) {
      module.periodic();
    }
    ioLock.unlock();

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

    if (Arrays.stream(modules)
        .anyMatch(
            module ->
                Math.abs(module.getVelocityMetersPerSec())
                    > coastSpeedThresholdMetersPerSecond.get())) {
      coastTimer.reset();
    }

    if (DriverStation.isDisabled()) {
      if (hasCoastedSinceDisable || coastTimer.hasElapsed(coastDelaySeconds.get())) {
        for (Module module : modules) {
          module.coast();
        }
        hasCoastedSinceDisable = true;
      } else {
        for (Module module : modules) {
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
    for (Module module : modules) {
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

    for (int i = 0; i < 4; i++) {
      modules[i].runSetpoint(desiredModuleStates[i]);
    }

    Logger.recordOutput("SwerveStates/SetpointsOptimized", desiredModuleStates);
  }

  public void setDriveVoltage(double voltage) {
    for (int i = 0; i < 4; i++) {
      modules[i].setDriveVoltage(voltage);
    }
  }

  public void stop() {
    drive(new ChassisSpeeds());
  }

  public void resetModuleHeadingsToTranslations() {
    Rotation2d[] moduleHeadings = new Rotation2d[4];
    for (int i = 0; i < 4; i++) {
      moduleHeadings[i] = DriveConstants.moduleTranslations[i].getAngle();
    }
    swerveKinematics.resetHeadings(moduleHeadings);
    stop();
  }

  @AutoLogOutput(key = "SwerveStates/Measured")
  private SwerveModuleState[] getMeasuredModuleStates() {
    SwerveModuleState[] measuredModuleStates = new SwerveModuleState[4];
    for (int i = 0; i < 4; i++) {
      measuredModuleStates[i] = modules[i].getState();
    }
    return measuredModuleStates;
  }

  private SwerveModulePosition[] getModulePositions() {
    SwerveModulePosition[] modulePositions = new SwerveModulePosition[4];
    for (int i = 0; i < 4; i++) {
      modulePositions[i] = modules[i].getPosition();
    }
    return modulePositions;
  }

  @AutoLogOutput(key = "SwerveChassisSpeeds/Measured")
  private ChassisSpeeds getMeasuredChassisSpeeds() {
    return swerveKinematics.toChassisSpeeds(getMeasuredModuleStates());
  }

  public double[] getWheelRadiusCharacterizationPositions() {
    double[] wheelRadiusCharacterizationPositions = new double[4];
    for (int i = 0; i < 4; i++) {
      wheelRadiusCharacterizationPositions[i] =
          modules[i].getWheelRadiusCharacterizationPosition();
    }
    return wheelRadiusCharacterizationPositions;
  }
}