package org.littletonrobotics.frc2026.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.math.trajectory.TrapezoidProfile.State;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.math.var23.ProfiledPIDController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.function.DoubleSupplier;
import org.littletonrobotics.frc2026.RobotState;
import org.littletonrobotics.frc2026.subsystems.launcher.LaunchCalculator;
import org.littletonrobotics.frc2026.subsystems.var6.Drive;
import org.littletonrobotics.frc2026.subsystems.var6.DriveConstants;
import org.littletonrobotics.frc2026.util.LoggedTunableNumber;
import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;

public class Class1 {
  public static final double kJoystickDeadband = 0.1;
  private static final double kWheelRadiusCharacterizationAngularSpeed = 0.25;
  private static final double kWheelRadiusCharacterizationSlewRate = 0.05;

  private static final LoggedTunableNumber kAlignKp =
      new LoggedTunableNumber("DriveCommands/Launching/kP", 0.0);
  private static final LoggedTunableNumber kAlignKd =
      new LoggedTunableNumber("DriveCommands/Launching/kD", 0.0);
  private static final LoggedTunableNumber kAlignMaxVelocity =
      new LoggedTunableNumber("DriveCommands/Launching/MaxVelocity", 4.0);
  private static final LoggedTunableNumber kAlignMaxAcceleration =
      new LoggedTunableNumber("DriveCommands/Launching/MaxAcceleration", 6.0);
  private static final LoggedTunableNumber kAlignToleranceRadians =
      new LoggedTunableNumber("DriveCommands/Launching/Tolerance", Units.degreesToRadians(5));

  private Class1() {}

  public static Translation2d applyRadialDeadbandAndSquare(double xInput, double yInput) {
    double magnitude = MathUtil.applyDeadband(Math.hypot(xInput, yInput), kJoystickDeadband);
    Rotation2d direction = new Rotation2d(xInput, yInput);

    magnitude = magnitude * magnitude;

    return new Pose2d(Translation2d.kZero, direction)
        .transformBy(new Transform2d(magnitude, 0.0, Rotation2d.kZero))
        .getTranslation();
  }

  public static double applyDeadbandAndSquareWithSign(double input) {
    double deadbanded = MathUtil.applyDeadband(input, kJoystickDeadband);
    return deadbanded * deadbanded * Math.signum(deadbanded);
  }

  public static ChassisSpeeds createChassisSpeeds(
      double xInput, double yInput, double omegaInput) {
    Translation2d translation =
        applyRadialDeadbandAndSquare(xInput, yInput).times(DriveConstants.maxLinearSpeed);

    double omega = applyDeadbandAndSquareWithSign(omegaInput);

    return new ChassisSpeeds(
        translation.getX(), translation.getY(), omega * DriveConstants.maxAngularSpeed);
  }

  public static Command driveWithJoysticksFieldRelative(
      Drive drive,
      DoubleSupplier xSupplier,
      DoubleSupplier ySupplier,
      DoubleSupplier omegaSupplier) {
    return Commands.run(
        () -> {
          ChassisSpeeds speeds =
              createChassisSpeeds(
                  xSupplier.getAsDouble(), ySupplier.getAsDouble(), omegaSupplier.getAsDouble());
          Rotation2d robotRotation =
              DriverStation.getAlliance().isPresent()
                      && DriverStation.getAlliance().get() == Alliance.Red
                  ? RobotState.getInstance().getRotation().plus(Rotation2d.kPi)
                  : RobotState.getInstance().getRotation();
          drive.runVelocity(ChassisSpeeds.fromFieldRelativeSpeeds(speeds, robotRotation));
        },
        drive);
  }

  @AutoLogOutput
  public static boolean isAlignedToLaunchAngle() {
    return DriverStation.isEnabled()
        && Math.abs(
                RobotState.getInstance()
                    .getRotation()
                    .minus(LaunchCalculator.getInstance().getParameters().driveAngle())
                    .getRadians())
            <= kAlignToleranceRadians.get();
  }

  public static Command driveWithLaunchAlignment(
      Drive drive, DoubleSupplier xSupplier, DoubleSupplier ySupplier) {
    ProfiledPIDController headingController =
        new ProfiledPIDController(
            kAlignKp.get(),
            0.0,
            kAlignKd.get(),
            new Constraints(kAlignMaxVelocity.get(), kAlignMaxAcceleration.get()));
    headingController.enableContinuousInput(-Math.PI, Math.PI);

    return Commands.run(
        () -> {
          if (kAlignKp.hasChanged(headingController.hashCode())
              || kAlignKd.hasChanged(headingController.hashCode())
              || kAlignMaxVelocity.hasChanged(headingController.hashCode())
              || kAlignMaxAcceleration.hasChanged(headingController.hashCode())) {
            headingController.setPID(kAlignKp.get(), 0.0, kAlignKd.get());
            headingController.setConstraints(
                new Constraints(kAlignMaxVelocity.get(), kAlignMaxAcceleration.get()));
          }

          double currentHeading = RobotState.getInstance().getRotation().getRadians();
          double targetHeading =
              LaunchCalculator.getInstance().getParameters().driveAngle().getRadians();
          double targetVelocity =
              LaunchCalculator.getInstance().getParameters().driveVelocity();

          double controllerOutput =
              headingController.calculate(currentHeading, new State(targetHeading, targetVelocity));
          double omegaSetpoint = headingController.getSetpoint().velocity + controllerOutput;

          ChassisSpeeds speeds =
              createChassisSpeeds(
                  xSupplier.getAsDouble(), ySupplier.getAsDouble(), omegaSetpoint);

          Rotation2d robotRotation =
              DriverStation.getAlliance().isPresent()
                      && DriverStation.getAlliance().get() == Alliance.Red
                  ? RobotState.getInstance().getRotation().plus(Rotation2d.kPi)
                  : RobotState.getInstance().getRotation();
          drive.runVelocity(ChassisSpeeds.fromFieldRelativeSpeeds(speeds, robotRotation));
        },
        drive);
  }

  public static Command characterizeWheelRadius(Drive drive) {
    SlewRateLimiter omegaLimiter =
        new SlewRateLimiter(kWheelRadiusCharacterizationSlewRate);
    WheelRadiusCharacterizationState state = new WheelRadiusCharacterizationState();

    return Commands.parallel(
        Commands.sequence(
            Commands.runOnce(() -> omegaLimiter.reset(0.0)),
            Commands.run(
                () -> {
                  double omega =
                      omegaLimiter.calculate(kWheelRadiusCharacterizationAngularSpeed);
                  drive.runVelocity(new ChassisSpeeds(0.0, 0.0, omega));
                },
                drive)),
        Commands.sequence(
            Commands.waitSeconds(1.0),
            Commands.runOnce(
                () -> {
                  state.initialWheelPositions =
                      drive.getWheelRadiusCharacterizationPositions();
                  state.previousGyroRotation = RobotState.getInstance().getRotation();
                  state.totalGyroDeltaRadians = 0.0;
                }),
            Commands.run(
                    () -> {
                      Rotation2d currentGyroRotation =
                          RobotState.getInstance().getRotation();
                      state.totalGyroDeltaRadians +=
                          Math.abs(
                              currentGyroRotation
                                  .minus(state.previousGyroRotation)
                                  .getRadians());
                      state.previousGyroRotation = currentGyroRotation;

                      double[] currentWheelPositions =
                          drive.getWheelRadiusCharacterizationPositions();
                      double averageWheelDelta = 0.0;
                      for (int i = 0; i < 4; i++) {
                        averageWheelDelta +=
                            Math.abs(currentWheelPositions[i] - state.initialWheelPositions[i])
                                / 4.0;
                      }
                      double wheelRadius =
                          (state.totalGyroDeltaRadians * DriveConstants.driveBaseRadius)
                              / averageWheelDelta;

                      Logger.recordOutput("Drive/WheelDelta", averageWheelDelta);
                      Logger.recordOutput("Drive/WheelRadius", wheelRadius);
                    })
                .finallyDo(
                    () -> {
                      double[] currentWheelPositions =
                          drive.getWheelRadiusCharacterizationPositions();
                      double averageWheelDelta = 0.0;
                      for (int i = 0; i < 4; i++) {
                        averageWheelDelta +=
                            Math.abs(currentWheelPositions[i] - state.initialWheelPositions[i])
                                / 4.0;
                      }
                      double wheelRadius =
                          (state.totalGyroDeltaRadians * DriveConstants.driveBaseRadius)
                              / averageWheelDelta;

                      NumberFormat formatter = new DecimalFormat("#0.00000000");
                      System.out.println(
                          "********** Wheel Radius Characterization Results **********");
                      System.out.println(
                          "\tWheel Delta: " + formatter.format(averageWheelDelta) + " radians");
                      System.out.println(
                          "\tGyro Delta: " + formatter.format(state.totalGyroDeltaRadians)
                              + " radians");
                      System.out.println(
                          "\tWheel Radius: "
                              + formatter.format(wheelRadius)
                              + " meters, "
                              + formatter.format(Units.metersToInches(wheelRadius))
                              + " inches");
                    })));
  }

  private static class WheelRadiusCharacterizationState {
    double[] initialWheelPositions = new double[4];
    Rotation2d previousGyroRotation = Rotation2d.kZero;
    double totalGyroDeltaRadians = 0.0;
  }
}