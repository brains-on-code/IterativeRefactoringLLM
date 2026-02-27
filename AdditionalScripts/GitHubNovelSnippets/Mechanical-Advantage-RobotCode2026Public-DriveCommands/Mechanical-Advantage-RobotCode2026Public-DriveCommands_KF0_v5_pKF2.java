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

  // Wheel radius characterization
  private static final double WHEEL_RADIUS_MAX_VELOCITY = 0.25; // rad/s
  private static final double WHEEL_RADIUS_RAMP_RATE = 0.05; // rad/s^2

  // Launch alignment tuning
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

  /**
   * Converts joystick X/Y inputs into a linear velocity vector.
   *
   * <p>Process:
   *
   * <ul>
   *   <li>Apply a radial deadband.
   *   <li>Square the magnitude for finer low-speed control.
   * </ul>
   */
  public static Translation2d getLinearVelocityFromJoysticks(double x, double y) {
    double linearMagnitude = MathUtil.applyDeadband(Math.hypot(x, y), DEADBAND);
    Rotation2d linearDirection = new Rotation2d(x, y);

    linearMagnitude *= linearMagnitude;

    return new Pose2d(Translation2d.kZero, linearDirection)
        .transformBy(new Transform2d(linearMagnitude, 0.0, Rotation2d.kZero))
        .getTranslation();
  }

  /**
   * Converts joystick omega input into an angular velocity scalar.
   *
   * <p>Process:
   *
   * <ul>
   *   <li>Apply deadband.
   *   <li>Square magnitude while preserving sign.
   * </ul>
   */
  public static double getOmegaFromJoysticks(double driverOmega) {
    double omega = MathUtil.applyDeadband(driverOmega, DEADBAND);
    return omega * omega * Math.signum(omega);
  }

  /** Converts joystick inputs into {@link ChassisSpeeds} in robot coordinates. */
  public static ChassisSpeeds getSpeedsFromJoysticks(
      double driverX, double driverY, double driverOmega) {
    Translation2d linearVelocity =
        getLinearVelocityFromJoysticks(driverX, driverY).times(DriveConstants.maxLinearSpeed);

    double omega = getOmegaFromJoysticks(driverOmega);

    return new ChassisSpeeds(
        linearVelocity.getX(), linearVelocity.getY(), omega * DriveConstants.maxAngularSpeed);
  }

  /**
   * Field-relative drive using joysticks.
   *
   * <p>Behavior:
   *
   * <ul>
   *   <li>X/Y control linear velocity.
   *   <li>Omega controls angular velocity.
   *   <li>Field frame is flipped for the red alliance.
   * </ul>
   */
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

          Rotation2d robotRotation = RobotState.getInstance().getRotation();
          Rotation2d fieldRelativeRotation = getAllianceAdjustedRotation(robotRotation);

          drive.runVelocity(ChassisSpeeds.fromFieldRelativeSpeeds(speeds, fieldRelativeRotation));
        },
        drive);
  }

  /** Returns whether the robot is within the configured launch angle tolerance. */
  @AutoLogOutput
  public static boolean atLaunchGoal() {
    if (!DriverStation.isEnabled()) {
      return false;
    }

    Rotation2d currentRotation = RobotState.getInstance().getRotation();
    Rotation2d targetRotation = LaunchCalculator.getInstance().getParameters().driveAngle();

    double errorRadians = currentRotation.minus(targetRotation).getRadians();
    return Math.abs(errorRadians) <= driveLaunchTolerance.get();
  }

  /**
   * Field-relative drive while automatically aiming for launching.
   *
   * <p>Behavior:
   *
   * <ul>
   *   <li>X/Y are driver-controlled.
   *   <li>Heading is controlled by a profiled PID to the launch angle.
   * </ul>
   */
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

          double currentHeading = RobotState.getInstance().getRotation().getRadians();
          var launchParams = LaunchCalculator.getInstance().getParameters();
          State goalState =
              new State(
                  launchParams.driveAngle().getRadians(), launchParams.driveVelocity());

          double pidOutput = controller.calculate(currentHeading, goalState);
          double omegaOutput = controller.getSetpoint().velocity + pidOutput;

          ChassisSpeeds speeds =
              getSpeedsFromJoysticks(xSupplier.getAsDouble(), ySupplier.getAsDouble(), omegaOutput);

          Rotation2d robotRotation = RobotState.getInstance().getRotation();
          Rotation2d fieldRelativeRotation = getAllianceAdjustedRotation(robotRotation);

          drive.runVelocity(ChassisSpeeds.fromFieldRelativeSpeeds(speeds, fieldRelativeRotation));
        },
        drive);
  }

  /**
   * Characterizes the robot's wheel radius.
   *
   * <p>Method:
   *
   * <ul>
   *   <li>Spin in place at a controlled angular velocity.
   *   <li>Compare integrated gyro rotation to integrated wheel rotation.
   * </ul>
   */
  public static Command wheelRadiusCharacterization(Drive drive) {
    SlewRateLimiter limiter = new SlewRateLimiter(WHEEL_RADIUS_RAMP_RATE);
    WheelRadiusCharacterizationState state = new WheelRadiusCharacterizationState();

    Command driveControlSequence =
        Commands.sequence(
            Commands.runOnce(() -> limiter.reset(0.0)),
            Commands.run(
                () -> {
                  double speed = limiter.calculate(WHEEL_RADIUS_MAX_VELOCITY);
                  drive.runVelocity(new ChassisSpeeds(0.0, 0.0, speed));
                },
                drive));

    Command measurementSequence =
        Commands.sequence(
            Commands.waitSeconds(1.0),
            Commands.runOnce(
                () -> {
                  state.positions = drive.getWheelRadiusCharacterizationPositions();
                  state.lastAngle = RobotState.getInstance().getRotation();
                  state.gyroDelta = 0.0;
                }),
            Commands.run(
                    () -> {
                      Rotation2d rotation = RobotState.getInstance().getRotation();
                      state.gyroDelta += Math.abs(rotation.minus(state.lastAngle).getRadians());
                      state.lastAngle = rotation;

                      double[] positions = drive.getWheelRadiusCharacterizationPositions();
                      double wheelDelta = calculateAverageWheelDelta(positions, state.positions);

                      double wheelRadius =
                          (state.gyroDelta * DriveConstants.driveBaseRadius) / wheelDelta;

                      Logger.recordOutput("Drive/WheelDelta", wheelDelta);
                      Logger.recordOutput("Drive/WheelRadius", wheelRadius);
                    })
                .finallyDo(
                    () -> {
                      double[] positions = drive.getWheelRadiusCharacterizationPositions();
                      double wheelDelta = calculateAverageWheelDelta(positions, state.positions);

                      double wheelRadius =
                          (state.gyroDelta * DriveConstants.driveBaseRadius) / wheelDelta;

                      NumberFormat formatter = new DecimalFormat("#0.00000000");
                      System.out.println(
                          "********** Wheel Radius Characterization Results **********");
                      System.out.println(
                          "\tWheel Delta: " + formatter.format(wheelDelta) + " radians");
                      System.out.println(
                          "\tGyro Delta: " + formatter.format(state.gyroDelta) + " radians");
                      System.out.println(
                          "\tWheel Radius: "
                              + formatter.format(wheelRadius)
                              + " meters, "
                              + formatter.format(Units.metersToInches(wheelRadius))
                              + " inches");
                    }));

    return Commands.parallel(driveControlSequence, measurementSequence);
  }

  /** Returns robot rotation adjusted for alliance (flips 180° on red). */
  private static Rotation2d getAllianceAdjustedRotation(Rotation2d robotRotation) {
    Optional<Alliance> alliance = DriverStation.getAlliance();
    if (alliance.isPresent() && alliance.get() == Alliance.Red) {
      return robotRotation.plus(Rotation2d.kPi);
    }
    return robotRotation;
  }

  /** Updates launch controller gains and constraints if any tunables changed. */
  private static void updateLaunchControllerGains(ProfiledPIDController controller) {
    int hash = controller.hashCode();
    boolean gainsChanged = driveLaunchKp.hasChanged(hash) || driveLaunchKd.hasChanged(hash);
    boolean constraintsChanged =
        driveLaunchMaxVelocity.hasChanged(hash) || driveLaunchMaxAcceleration.hasChanged(hash);

    if (gainsChanged) {
      controller.setPID(driveLaunchKp.get(), 0.0, driveLaunchKd.get());
    }

    if (constraintsChanged) {
      controller.setConstraints(
          new Constraints(driveLaunchMaxVelocity.get(), driveLaunchMaxAcceleration.get()));
    }
  }

  /** Computes average absolute wheel position delta across all four modules. */
  private static double calculateAverageWheelDelta(double[] current, double[] initial) {
    double wheelDelta = 0.0;
    for (int i = 0; i < 4; i++) {
      wheelDelta += Math.abs(current[i] - initial[i]) / 4.0;
    }
    return wheelDelta;
  }

  private static class WheelRadiusCharacterizationState {
    double[] positions = new double[4];
    Rotation2d lastAngle = Rotation2d.kZero;
    double gyroDelta = 0.0;
  }
}