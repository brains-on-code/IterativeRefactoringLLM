// Copyright (c) 2025-2026 Littleton Robotics
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package org.littletonrobotics.frc2026.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.math.trajectory.TrapezoidProfile.State;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Optional;
import java.util.function.DoubleSupplier;
import org.littletonrobotics.frc2026.RobotState;
import org.littletonrobotics.frc2026.subsystems.drive.Drive;
import org.littletonrobotics.frc2026.subsystems.drive.DriveConstants;
import org.littletonrobotics.frc2026.subsystems.launcher.LaunchCalculator;
import org.littletonrobotics.frc2026.util.LoggedTunableNumber;
import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;

public class DriveCommands {
  public static final double DEADBAND = 0.1;

  private static final double WHEEL_RADIUS_MAX_VELOCITY = 0.25; // Rad/Sec
  private static final double WHEEL_RADIUS_RAMP_RATE = 0.05; // Rad/Sec^2

  private static final LoggedTunableNumber driveLaunchKp =
      new LoggedTunableNumber("DriveCommands/Launching/kP", 0.0);
  private static final LoggedTunableNumber driveLaunchKd =
      new LoggedTunableNumber("DriveCommands/Launching/kD", 0.0);
  private static final LoggedTunableNumber driveLaunchMaxVelocity =
      new LoggedTunableNumber("DriveCommands/Launching/MaxVelocity", 4.0);
  private static final LoggedTunableNumber driveLaunchMaxAcceleration =
      new LoggedTunableNumber("DriveCommands/Launching/MaxAcceleration", 6.0);
  private static final LoggedTunableNumber driveLaunchTolerance =
      new LoggedTunableNumber("DriveCommands/Launching/Tolerance", Units.degreesToRadians(5));

  private DriveCommands() {}

  /* ---------------------------- Joystick Helpers ---------------------------- */

  public static Translation2d getLinearVelocityFromJoysticks(double x, double y) {
    double linearMagnitude = MathUtil.applyDeadband(Math.hypot(x, y), DEADBAND);
    Rotation2d linearDirection = new Rotation2d(x, y);

    // Square magnitude for finer control at low speeds
    linearMagnitude *= linearMagnitude;

    return new Pose2d(Translation2d.kZero, linearDirection)
        .transformBy(new Transform2d(linearMagnitude, 0.0, Rotation2d.kZero))
        .getTranslation();
  }

  public static double getOmegaFromJoysticks(double driverOmega) {
    double omega = MathUtil.applyDeadband(driverOmega, DEADBAND);
    return omega * omega * Math.signum(omega);
  }

  public static ChassisSpeeds getSpeedsFromJoysticks(
      double driverX, double driverY, double driverOmega) {
    Translation2d linearVelocity =
        getLinearVelocityFromJoysticks(driverX, driverY).times(DriveConstants.maxLinearSpeed);
    double omega = getOmegaFromJoysticks(driverOmega);

    return new ChassisSpeeds(
        linearVelocity.getX(), linearVelocity.getY(), omega * DriveConstants.maxAngularSpeed);
  }

  /* ------------------------------ Basic Drive ------------------------------- */

  public static Command joystickDrive(
      Drive drive,
      DoubleSupplier xSupplier,
      DoubleSupplier ySupplier,
      DoubleSupplier omegaSupplier) {
    return Commands.run(
        () -> {
          ChassisSpeeds speeds =
              getSpeedsFromJoysticks(
                  xSupplier.getAsDouble(), ySupplier.getAsDouble(), omegaSupplier.getAsDouble());
          Rotation2d adjustedRotation =
              getAllianceAdjustedRotation(RobotState.getInstance().getRotation());

          drive.runVelocity(ChassisSpeeds.fromFieldRelativeSpeeds(speeds, adjustedRotation));
        },
        drive);
  }

  /* --------------------------- Launch Alignment ----------------------------- */

  @AutoLogOutput
  public static boolean atLaunchGoal() {
    if (!DriverStation.isEnabled()) {
      return false;
    }

    double currentAngle = RobotState.getInstance().getRotation().getRadians();
    double targetAngle =
        LaunchCalculator.getInstance().getParameters().driveAngle().getRadians();

    return Math.abs(currentAngle - targetAngle) <= driveLaunchTolerance.get();
  }

  public static Command joystickDriveWhileLaunching(
      Drive drive, DoubleSupplier xSupplier, DoubleSupplier ySupplier) {
    ProfiledPIDController controller =
        new ProfiledPIDController(
            driveLaunchKp.get(),
            0.0,
            driveLaunchKd.get(),
            new Constraints(driveLaunchMaxVelocity.get(), driveLaunchMaxAcceleration.get()));
    controller.enableContinuousInput(-Math.PI, Math.PI);

    return Commands.run(
        () -> {
          updateLaunchControllerGains(controller);

          double currentAngle = RobotState.getInstance().getRotation().getRadians();
          var launchParams = LaunchCalculator.getInstance().getParameters();
          State goalState =
              new State(
                  launchParams.driveAngle().getRadians(), launchParams.driveVelocity());

          double pidOutput = controller.calculate(currentAngle, goalState);
          double omegaOutput = controller.getSetpoint().velocity + pidOutput;

          ChassisSpeeds speeds =
              getSpeedsFromJoysticks(
                  xSupplier.getAsDouble(), ySupplier.getAsDouble(), omegaOutput);

          Rotation2d adjustedRotation =
              getAllianceAdjustedRotation(RobotState.getInstance().getRotation());

          drive.runVelocity(ChassisSpeeds.fromFieldRelativeSpeeds(speeds, adjustedRotation));
        },
        drive);
  }

  private static void updateLaunchControllerGains(ProfiledPIDController controller) {
    int id = controller.hashCode();
    boolean gainsChanged =
        driveLaunchKp.hasChanged(id)
            || driveLaunchKd.hasChanged(id)
            || driveLaunchMaxVelocity.hasChanged(id)
            || driveLaunchMaxAcceleration.hasChanged(id);

    if (!gainsChanged) {
      return;
    }

    controller.setPID(driveLaunchKp.get(), 0.0, driveLaunchKd.get());
    controller.setConstraints(
        new Constraints(driveLaunchMaxVelocity.get(), driveLaunchMaxAcceleration.get()));
  }

  /* ---------------------- Wheel Radius Characterization --------------------- */

  public static Command wheelRadiusCharacterization(Drive drive) {
    SlewRateLimiter limiter = new SlewRateLimiter(WHEEL_RADIUS_RAMP_RATE);
    WheelRadiusCharacterizationState state = new WheelRadiusCharacterizationState();

    Command driveControl =
        Commands.sequence(
            Commands.runOnce(() -> limiter.reset(0.0)),
            Commands.run(
                () -> {
                  double speed = limiter.calculate(WHEEL_RADIUS_MAX_VELOCITY);
                  drive.runVelocity(new ChassisSpeeds(0.0, 0.0, speed));
                },
                drive));

    Command measurement =
        Commands.sequence(
            Commands.waitSeconds(1.0),
            Commands.runOnce(() -> initializeWheelRadiusState(drive, state)),
            Commands.run(() -> updateWheelRadiusMeasurement(drive, state))
                .finallyDo(() -> logWheelRadiusResults(drive, state)));

    return Commands.parallel(driveControl, measurement);
  }

  private static void initializeWheelRadiusState(
      Drive drive, WheelRadiusCharacterizationState state) {
    state.positions = drive.getWheelRadiusCharacterizationPositions();
    state.lastAngle = RobotState.getInstance().getRotation();
    state.gyroDelta = 0.0;
  }

  private static void updateWheelRadiusMeasurement(
      Drive drive, WheelRadiusCharacterizationState state) {
    Rotation2d currentRotation = RobotState.getInstance().getRotation();
    state.gyroDelta += Math.abs(currentRotation.minus(state.lastAngle).getRadians());
    state.lastAngle = currentRotation;

    double[] positions = drive.getWheelRadiusCharacterizationPositions();
    double wheelDelta = calculateAverageWheelDelta(positions, state.positions);
    double wheelRadius = calculateWheelRadius(state.gyroDelta, wheelDelta);

    Logger.recordOutput("Drive/WheelDelta", wheelDelta);
    Logger.recordOutput("Drive/WheelRadius", wheelRadius);
  }

  private static void logWheelRadiusResults(
      Drive drive, WheelRadiusCharacterizationState state) {
    double[] positions = drive.getWheelRadiusCharacterizationPositions();
    double wheelDelta = calculateAverageWheelDelta(positions, state.positions);
    double wheelRadius = calculateWheelRadius(state.gyroDelta, wheelDelta);

    NumberFormat formatter = new DecimalFormat("#0.00000000");
    System.out.println("********** Wheel Radius Characterization Results **********");
    System.out.println("\tWheel Delta: " + formatter.format(wheelDelta) + " radians");
    System.out.println("\tGyro Delta: " + formatter.format(state.gyroDelta) + " radians");
    System.out.println(
        "\tWheel Radius: "
            + formatter.format(wheelRadius)
            + " meters, "
            + formatter.format(Units.metersToInches(wheelRadius))
            + " inches");
  }

  private static double calculateAverageWheelDelta(double[] current, double[] initial) {
    double wheelDelta = 0.0;
    int wheelCount = Math.min(current.length, initial.length);
    for (int i = 0; i < wheelCount; i++) {
      wheelDelta += Math.abs(current[i] - initial[i]) / wheelCount;
    }
    return wheelDelta;
  }

  private static double calculateWheelRadius(double gyroDelta, double wheelDelta) {
    return (gyroDelta * DriveConstants.driveBaseRadius) / wheelDelta;
  }

  /* ------------------------------ Utilities -------------------------------- */

  private static Rotation2d getAllianceAdjustedRotation(Rotation2d baseRotation) {
    Optional<Alliance> alliance = DriverStation.getAlliance();
    if (alliance.isPresent() && alliance.get() == Alliance.Red) {
      return baseRotation.plus(Rotation2d.kPi);
    }
    return baseRotation;
  }

  private static class WheelRadiusCharacterizationState {
    double[] positions = new double[4];
    Rotation2d lastAngle = Rotation2d.kZero;
    double gyroDelta = 0.0;
  }
}