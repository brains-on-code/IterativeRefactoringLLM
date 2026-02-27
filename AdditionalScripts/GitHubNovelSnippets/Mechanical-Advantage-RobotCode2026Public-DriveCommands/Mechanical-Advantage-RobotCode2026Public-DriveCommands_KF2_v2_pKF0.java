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
  private static final double WHEEL_RADIUS_MAX_VELOCITY = 0.25;
  private static final double WHEEL_RADIUS_RAMP_RATE = 0.05;

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

  public static Translation2d getLinearVelocityFromJoysticks(double x, double y) {
    double linearMagnitude = MathUtil.applyDeadband(Math.hypot(x, y), DEADBAND);
    Rotation2d linearDirection = new Rotation2d(x, y);

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
          drive.runVelocity(
              ChassisSpeeds.fromFieldRelativeSpeeds(
                  speeds, getAllianceAdjustedRotation(robotRotation)));
        },
        drive);
  }

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
          if (driveLaunchKp.hasChanged(controller.hashCode())
              || driveLaunchKd.hasChanged(controller.hashCode())
              || driveLaunchMaxVelocity.hasChanged(controller.hashCode())
              || driveLaunchMaxAcceleration.hasChanged(controller.hashCode())) {
            controller.setPID(driveLaunchKp.get(), 0.0, driveLaunchKd.get());
            controller.setConstraints(
                new Constraints(driveLaunchMaxVelocity.get(), driveLaunchMaxAcceleration.get()));
          }

          double currentRotation = RobotState.getInstance().getRotation().getRadians();
          var launchParams = LaunchCalculator.getInstance().getParameters();
          State goalState =
              new State(
                  launchParams.driveAngle().getRadians(), launchParams.driveVelocity());

          double pidOutput = controller.calculate(currentRotation, goalState);
          double omegaOutput = controller.getSetpoint().velocity + pidOutput;

          ChassisSpeeds speeds =
              getSpeedsFromJoysticks(
                  xSupplier.getAsDouble(), ySupplier.getAsDouble(), omegaOutput);

          Rotation2d robotRotation = RobotState.getInstance().getRotation();
          drive.runVelocity(
              ChassisSpeeds.fromFieldRelativeSpeeds(
                  speeds, getAllianceAdjustedRotation(robotRotation)));
        },
        drive);
  }

  public static Command wheelRadiusCharacterization(Drive drive) {
    SlewRateLimiter limiter = new SlewRateLimiter(WHEEL_RADIUS_RAMP_RATE);
    WheelRadiusCharacterizationState state = new WheelRadiusCharacterizationState();

    Command rampCommand =
        Commands.sequence(
            Commands.runOnce(() -> limiter.reset(0.0)),
            Commands.run(
                () -> {
                  double speed = limiter.calculate(WHEEL_RADIUS_MAX_VELOCITY);
                  drive.runVelocity(new ChassisSpeeds(0.0, 0.0, speed));
                },
                drive));

    Command measureCommand =
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
                      updateGyroDelta(state);
                      double wheelDelta =
                          calculateWheelDelta(
                              drive.getWheelRadiusCharacterizationPositions(), state.positions);
                      double wheelRadius =
                          calculateWheelRadius(state.gyroDelta, wheelDelta);

                      Logger.recordOutput("Drive/WheelDelta", wheelDelta);
                      Logger.recordOutput("Drive/WheelRadius", wheelRadius);
                    })
                .finallyDo(
                    () -> {
                      double[] positions = drive.getWheelRadiusCharacterizationPositions();
                      double wheelDelta = calculateWheelDelta(positions, state.positions);
                      double wheelRadius =
                          calculateWheelRadius(state.gyroDelta, wheelDelta);

                      printWheelRadiusResults(state.gyroDelta, wheelDelta, wheelRadius);
                    }));

    return Commands.parallel(rampCommand, measureCommand);
  }

  private static Rotation2d getAllianceAdjustedRotation(Rotation2d baseRotation) {
    Optional<Alliance> alliance = DriverStation.getAlliance();
    if (alliance.isPresent() && alliance.get() == Alliance.Red) {
      return baseRotation.plus(Rotation2d.kPi);
    }
    return baseRotation;
  }

  private static void updateGyroDelta(WheelRadiusCharacterizationState state) {
    Rotation2d currentRotation = RobotState.getInstance().getRotation();
    state.gyroDelta += Math.abs(currentRotation.minus(state.lastAngle).getRadians());
    state.lastAngle = currentRotation;
  }

  private static double calculateWheelDelta(double[] currentPositions, double[] initialPositions) {
    double wheelDelta = 0.0;
    for (int i = 0; i < 4; i++) {
      wheelDelta += Math.abs(currentPositions[i] - initialPositions[i]) / 4.0;
    }
    return wheelDelta;
  }

  private static double calculateWheelRadius(double gyroDelta, double wheelDelta) {
    return (gyroDelta * DriveConstants.driveBaseRadius) / wheelDelta;
  }

  private static void printWheelRadiusResults(
      double gyroDelta, double wheelDelta, double wheelRadius) {
    NumberFormat formatter = new DecimalFormat("#0.00000000");
    System.out.println("********** Wheel Radius Characterization Results **********");
    System.out.println("\tWheel Delta: " + formatter.format(wheelDelta) + " radians");
    System.out.println("\tGyro Delta: " + formatter.format(gyroDelta) + " radians");
    System.out.println(
        "\tWheel Radius: "
            + formatter.format(wheelRadius)
            + " meters, "
            + formatter.format(Units.metersToInches(wheelRadius))
            + " inches");
  }

  private static class WheelRadiusCharacterizationState {
    double[] positions = new double[4];
    Rotation2d lastAngle = Rotation2d.kZero;
    double gyroDelta = 0.0;
  }
}