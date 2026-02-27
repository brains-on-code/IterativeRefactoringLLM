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

public class DriveCommands {
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

  private DriveCommands() {}

  public static Translation2d applyRadialDeadbandAndSquare(double xInput, double yInput) {
    double magnitude =
        MathUtil.applyDeadband(Math.hypot(xInput, yInput), kJoystickDeadband);
    Rotation2d direction = new Rotation2d(xInput, yInput);

    magnitude = magnitude * magnitude;

    return new Pose2d(Translation2d.kZero, direction)
        .transformBy(new Transform2d(magnitude, 0.0, Rotation2d.kZero))
        .getTranslation();
  }

  public static double applyDeadbandAndSquareWithSign(double input) {
    double deadbandedInput = MathUtil.applyDeadband(input, kJoystickDeadband);
    return deadbandedInput * deadbandedInput * Math.signum(deadbandedInput);
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
          ChassisSpeeds chassisSpeeds =
              createChassisSpeeds(
                  xSupplier.getAsDouble(), ySupplier.getAsDouble(), omegaSupplier.getAsDouble());
          Rotation2d robotRotation =
              DriverStation.getAlliance().isPresent()
                      && DriverStation.getAlliance().get() == Alliance.Red
                  ? RobotState.getInstance().getRotation().plus(Rotation2d.kPi)
                  : RobotState.getInstance().getRotation();
          drive.runVelocity(ChassisSpeeds.fromFieldRelativeSpeeds(chassisSpeeds, robotRotation));
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
          int controllerId = headingController.hashCode();
          if (kAlignKp.hasChanged(controllerId)
              || kAlignKd.hasChanged(controllerId)
              || kAlignMaxVelocity.hasChanged(controllerId)
              || kAlignMaxAcceleration.hasChanged(controllerId)) {
            headingController.setPID(kAlignKp.get(), 0.0, kAlignKd.get());
            headingController.setConstraints(
                new Constraints(kAlignMaxVelocity.get(), kAlignMaxAcceleration.get()));
          }

          double currentHeadingRadians = RobotState.getInstance().getRotation().getRadians();
          double targetHeadingRadians =
              LaunchCalculator.getInstance().getParameters().driveAngle().getRadians();
          double targetAngularVelocity =
              LaunchCalculator.getInstance().getParameters().driveVelocity();

          double controllerOutput =
              headingController.calculate(
                  currentHeadingRadians,
                  new State(targetHeadingRadians, targetAngularVelocity));
          double omegaSetpoint =
              headingController.getSetpoint().velocity + controllerOutput;

          ChassisSpeeds chassisSpeeds =
              createChassisSpeeds(
                  xSupplier.getAsDouble(), ySupplier.getAsDouble(), omegaSetpoint);

          Rotation2d robotRotation =
              DriverStation.getAlliance().isPresent()
                      && DriverStation.getAlliance().get() == Alliance.Red
                  ? RobotState.getInstance().getRotation().plus(Rotation2d.kPi)
                  : RobotState.getInstance().getRotation();
          drive.runVelocity(ChassisSpeeds.fromFieldRelativeSpeeds(chassisSpeeds, robotRotation));
        },
        drive);
  }

  public static Command characterizeWheelRadius(Drive drive) {
    SlewRateLimiter omegaLimiter =
        new SlewRateLimiter(kWheelRadiusCharacterizationSlewRate);
    WheelRadiusCharacterizationState characterizationState =
        new WheelRadiusCharacterizationState();

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
                  characterizationState.initialWheelPositions =
                      drive.getWheelRadiusCharacterizationPositions();
                  characterizationState.previousGyroRotation =
                      RobotState.getInstance().getRotation();
                  characterizationState.totalGyroDeltaRadians = 0.0;
                }),
            Commands.run(
                    () -> {
                      Rotation2d currentGyroRotation =
                          RobotState.getInstance().getRotation();
                      characterizationState.totalGyroDeltaRadians +=
                          Math.abs(
                              currentGyroRotation
                                  .minus(characterizationState.previousGyroRotation)
                                  .getRadians());
                      characterizationState.previousGyroRotation = currentGyroRotation;

                      double[] currentWheelPositions =
                          drive.getWheelRadiusCharacterizationPositions();
                      double averageWheelDeltaRadians = 0.0;
                      for (int i = 0; i < 4; i++) {
                        averageWheelDeltaRadians +=
                            Math.abs(
                                    currentWheelPositions[i]
                                        - characterizationState.initialWheelPositions[i])
                                / 4.0;
                      }
                      double wheelRadiusMeters =
                          (characterizationState.totalGyroDeltaRadians
                                  * DriveConstants.driveBaseRadius)
                              / averageWheelDeltaRadians;

                      Logger.recordOutput("Drive/WheelDelta", averageWheelDeltaRadians);
                      Logger.recordOutput("Drive/WheelRadius", wheelRadiusMeters);
                    })
                .finallyDo(
                    () -> {
                      double[] currentWheelPositions =
                          drive.getWheelRadiusCharacterizationPositions();
                      double averageWheelDeltaRadians = 0.0;
                      for (int i = 0; i < 4; i++) {
                        averageWheelDeltaRadians +=
                            Math.abs(
                                    currentWheelPositions[i]
                                        - characterizationState.initialWheelPositions[i])
                                / 4.0;
                      }
                      double wheelRadiusMeters =
                          (characterizationState.totalGyroDeltaRadians
                                  * DriveConstants.driveBaseRadius)
                              / averageWheelDeltaRadians;

                      NumberFormat formatter = new DecimalFormat("#0.00000000");
                      System.out.println(
                          "********** Wheel Radius Characterization Results **********");
                      System.out.println(
                          "\tWheel Delta: "
                              + formatter.format(averageWheelDeltaRadians)
                              + " radians");
                      System.out.println(
                          "\tGyro Delta: "
                              + formatter.format(
                                  characterizationState.totalGyroDeltaRadians)
                              + " radians");
                      System.out.println(
                          "\tWheel Radius: "
                              + formatter.format(wheelRadiusMeters)
                              + " meters, "
                              + formatter.format(
                                  Units.metersToInches(wheelRadiusMeters))
                              + " inches");
                    })));
  }

  private static class WheelRadiusCharacterizationState {
    double[] initialWheelPositions = new double[4];
    Rotation2d previousGyroRotation = Rotation2d.kZero;
    double totalGyroDeltaRadians = 0.0;
  }
}