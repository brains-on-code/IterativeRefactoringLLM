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
  private static final int MODULE_COUNT = 4;

  private static final Lock ioLock = new ReentrantLock();

  private static final LoggedTunableNumber COAST_WAIT_TIME_SECONDS =
      new LoggedTunableNumber("Drive/CoastWaitTimeSeconds", 0.5);
  private static final LoggedTunableNumber COAST_METERS_PER_SEC_THRESHOLD =
      new LoggedTunableNumber("Drive/CoastMetersPerSecThreshold", 0.05);

  private final GyroIO gyroIO;
  private final GyroIOInputsAutoLogged gyroInputs = new GyroIOInputsAutoLogged();
  private final Module[] modules = new Module[MODULE_COUNT];

  private final Alert gyroDisconnectedAlert =
      new Alert("Disconnected gyro, using kinematics as fallback.", AlertType.kError);

  private final Timer coastTimer = new Timer();
  private boolean hasCoasted = false;

  private final SwerveDriveKinematics kinematics =
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
    setBrakeModeForAllModules();
  }

  @Override
  public void method1() {
    updateIO();
    handleDisabledLogging();
    updateRobotState();
    updateGyroAlert();
    handleCoastLogic();
    LoggedTracer.record("Drive/Periodic");
  }

  @Override
  public void method2() {
    for (Module module : modules) {
      module.method2();
    }
    LoggedTracer.record("Drive/PeriodicAfterScheduler");
  }

  public void method3(ChassisSpeeds desiredChassisSpeeds) {
    ChassisSpeeds discretizedSpeeds =
        ChassisSpeeds.discretize(desiredChassisSpeeds, Constants.loopPeriodSecs);

    SwerveModuleState[] desiredModuleStates =
        kinematics.toSwerveModuleStates(discretizedSpeeds);
    SwerveDriveKinematics.desaturateWheelSpeeds(
        desiredModuleStates, DriveConstants.maxLinearSpeed);

    Logger.recordOutput("SwerveStates/Setpoints", desiredModuleStates);
    Logger.recordOutput("SwerveChassisSpeeds/Setpoints", discretizedSpeeds);

    for (int i = 0; i < modules.length; i++) {
      modules[i].runSetpoint(desiredModuleStates[i]);
    }

    Logger.recordOutput("SwerveStates/SetpointsOptimized", desiredModuleStates);
  }

  public void method4(double voltage) {
    for (Module module : modules) {
      module.method4(voltage);
    }
  }

  public void method5() {
    method3(new ChassisSpeeds());
  }

  public void method6() {
    Rotation2d[] moduleHeadings = new Rotation2d[modules.length];
    for (int i = 0; i < modules.length; i++) {
      moduleHeadings[i] = DriveConstants.moduleTranslations[i].getAngle();
    }
    kinematics.resetHeadings(moduleHeadings);
    method5();
  }

  @AutoLogOutput(key = "SwerveStates/Measured")
  private SwerveModuleState[] getMeasuredModuleStates() {
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
  private ChassisSpeeds getMeasuredChassisSpeeds() {
    return kinematics.toChassisSpeeds(getMeasuredModuleStates());
  }

  public double[] method10() {
    double[] positions = new double[modules.length];
    for (int i = 0; i < modules.length; i++) {
      positions[i] = modules[i].getWheelRadiusCharacterizationPosition();
    }
    return positions;
  }

  private void updateIO() {
    ioLock.lock();
    try {
      gyroIO.updateInputs(gyroInputs);
      LoggedTracer.record("Drive/GyroInputs");
      Logger.processInputs("Drive/Gyro", gyroInputs);

      for (Module module : modules) {
        module.method1();
      }
    } finally {
      ioLock.unlock();
    }
  }

  private void handleDisabledLogging() {
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
            Optional.ofNullable(gyroInputs.connected ? gyroInputs.yawPosition : null)));

    robotState.setRobotVelocity(getMeasuredChassisSpeeds());
  }

  private void updateGyroAlert() {
    gyroDisconnectedAlert.set(Robot.showHardwareAlerts() && !gyroInputs.connected);
  }

  private void handleCoastLogic() {
    boolean anyModuleMoving =
        Arrays.stream(modules)
            .anyMatch(
                module ->
                    Math.abs(module.getVelocityMetersPerSec())
                        > COAST_METERS_PER_SEC_THRESHOLD.get());

    if (anyModuleMoving) {
      coastTimer.reset();
    }

    if (DriverStation.isDisabled()) {
      if (hasCoasted || coastTimer.hasElapsed(COAST_WAIT_TIME_SECONDS.get())) {
        setCoastModeForAllModules();
        hasCoasted = true;
      } else {
        setBrakeModeForAllModules();
      }
      return;
    }

    hasCoasted = false;
  }

  private void setCoastModeForAllModules() {
    for (Module module : modules) {
      module.coast();
    }
  }

  private void setBrakeModeForAllModules() {
    for (Module module : modules) {
      module.brake();
    }
  }
}