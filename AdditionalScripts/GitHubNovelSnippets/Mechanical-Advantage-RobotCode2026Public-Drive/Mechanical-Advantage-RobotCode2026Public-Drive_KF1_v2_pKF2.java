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

public class Class1 extends FullSubsystem {
  /** Global lock for all drive-related operations. */
  static final Lock driveLock = new ReentrantLock();

  private final GyroIO gyroIO;
  private final GyroIOInputsAutoLogged gyroInputs = new GyroIOInputsAutoLogged();
  private final Module[] modules = new Module[4];

  private final Alert gyroDisconnectedAlert =
      new Alert("Disconnected gyro, using kinematics as fallback.", AlertType.kError);

  private static final LoggedTunableNumber coastWaitTimeSeconds =
      new LoggedTunableNumber("Drive/CoastWaitTimeSeconds", 0.5);
  private static final LoggedTunableNumber coastMetersPerSecThreshold =
      new LoggedTunableNumber("Drive/CoastMetersPerSecThreshold", 0.05);

  private final Timer coastTimer = new Timer();
  private boolean hasCoastedSinceDisable = false;

  private SwerveDriveKinematics kinematics =
      new SwerveDriveKinematics(DriveConstants.moduleTranslations);

  public Class1(
      GyroIO gyroIO,
      ModuleIO frontLeftIO,
      ModuleIO frontRightIO,
      ModuleIO backLeftIO,
      ModuleIO backRightIO) {
    this.gyroIO = gyroIO;

    modules[0] = new Module(frontLeftIO, 0);
    modules[1] = new Module(frontRightIO, 1);
    modules[2] = new Module(backLeftIO, 2);
    modules[3] = new Module(backRightIO, 3);

    coastTimer.start();
    for (var module : modules) {
      module.brake();
    }
  }

  @Override
  public void method1() {
    driveLock.lock();
    gyroIO.updateInputs(gyroInputs);
    LoggedTracer.record("Drive/GyroInputs");
    Logger.processInputs("Drive/Gyro", gyroInputs);

    for (var module : modules) {
      module.method1();
    }
    driveLock.unlock();

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
                Math.abs(module.getVelocityMetersPerSec()) > coastMetersPerSecThreshold.get())) {
      coastTimer.reset();
    }

    if (DriverStation.isDisabled()) {
      if (hasCoastedSinceDisable || coastTimer.hasElapsed(coastWaitTimeSeconds.get())) {
        for (var module : modules) {
          module.coast();
        }
        hasCoastedSinceDisable = true;
      } else {
        for (var module : modules) {
          module.brake();
        }
      }
    } else {
      hasCoastedSinceDisable = false;
    }

    LoggedTracer.record("Drive/Periodic");
  }

  @Override
  public void method2() {
    for (var module : modules) {
      module.method2();
    }
    LoggedTracer.record("Drive/PeriodicAfterScheduler");
  }

  /** Sets desired chassis speeds for the swerve drive. */
  public void method3(ChassisSpeeds desiredChassisSpeeds) {
    ChassisSpeeds discretizedSpeeds =
        ChassisSpeeds.discretize(desiredChassisSpeeds, Constants.loopPeriodSecs);

    SwerveModuleState[] desiredModuleStates =
        kinematics.toSwerveModuleStates(discretizedSpeeds);
    SwerveDriveKinematics.desaturateWheelSpeeds(
        desiredModuleStates, DriveConstants.maxLinearSpeed);

    Logger.recordOutput("SwerveStates/Setpoints", desiredModuleStates);
    Logger.recordOutput("SwerveChassisSpeeds/Setpoints", discretizedSpeeds);

    for (int i = 0; i < 4; i++) {
      modules[i].runSetpoint(desiredModuleStates[i]);
    }

    Logger.recordOutput("SwerveStates/SetpointsOptimized", desiredModuleStates);
  }

  /** Sets all modules to the same drive voltage. */
  public void method4(double voltage) {
    for (int i = 0; i < 4; i++) {
      modules[i].method4(voltage);
    }
  }

  /** Commands zero chassis speeds (stops the drive). */
  public void method5() {
    method3(new ChassisSpeeds());
  }

  /** Points all modules to their default (translation) angles and stops. */
  public void method6() {
    Rotation2d[] moduleHeadings = new Rotation2d[4];
    for (int i = 0; i < 4; i++) {
      moduleHeadings[i] = DriveConstants.moduleTranslations[i].getAngle();
    }
    kinematics.resetHeadings(moduleHeadings);
    method5();
  }

  /** Returns the measured module states. */
  @AutoLogOutput(key = "SwerveStates/Measured")
  private SwerveModuleState[] getMeasuredModuleStates() {
    SwerveModuleState[] states = new SwerveModuleState[4];
    for (int i = 0; i < 4; i++) {
      states[i] = modules[i].getState();
    }
    return states;
  }

  /** Returns the measured module positions. */
  private SwerveModulePosition[] getModulePositions() {
    SwerveModulePosition[] positions = new SwerveModulePosition[4];
    for (int i = 0; i < 4; i++) {
      positions[i] = modules[i].getPosition();
    }
    return positions;
  }

  /** Returns the measured chassis speeds from the module states. */
  @AutoLogOutput(key = "SwerveChassisSpeeds/Measured")
  private ChassisSpeeds getMeasuredChassisSpeeds() {
    return kinematics.toChassisSpeeds(getMeasuredModuleStates());
  }

  /** Returns wheel radius characterization positions for all modules. */
  public double[] method10() {
    double[] positions = new double[4];
    for (int i = 0; i < 4; i++) {
      positions[i] = modules[i].getWheelRadiusCharacterizationPosition();
    }
    return positions;
  }
}