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
  public static final double JOYSTICK_DEADBAND = 0.1;
  private static final double WHEEL_RADIUS_CHARACTERIZATION_ANGULAR_SPEED = 0.25;
  private static final double WHEEL_RADIUS_CHARACTERIZATION_SLEW_RATE = 0.05;

  private static final LoggedTunableNumber ALIGN_KP =
      new LoggedTunableNumber("DriveCommands/Launching/kP", 0.0);
  private static final LoggedTunableNumber ALIGN_KD =
      new LoggedTunableNumber("DriveCommands/Launching/kD", 0.0);
  private static final LoggedTunableNumber ALIGN_MAX_VELOCITY =
      new LoggedTunableNumber("DriveCommands/Launching/MaxVelocity", 4.0);
  private static final LoggedTunableNumber ALIGN_MAX_ACCELERATION =
      new LoggedTunableNumber("DriveCommands/Launching/MaxAcceleration", 6.0);
  private static final LoggedTunableNumber ALIGN_TOLERANCE_RADIANS =
      new LoggedTunableNumber("DriveCommands/Launching/Tolerance", Units.degreesToRadians(5));

  private DriveCommands() {}

  public static Translation2d applyRadialDeadbandAndSquare(double xInput, double yInput) {
    double magnitude =
        MathUtil.applyDeadband(Math.hypot(xInput, yInput), JOYSTICK_DEADBAND);
    Rotation2d direction = new Rotation2d(xInput, yInput);

    double squaredMagnitude = magnitude * magnitude;

    return new Pose2d(Translation2d.kZero, direction)
        .transformBy(new Transform2d(squaredMagnitude, 0.0, Rotation2d.kZero))
        .getTranslation();
  }

  public static double applyDeadbandAndSquareWithSign(double input) {
    double deadbandedInput = MathUtil.applyDeadband(input, JOYSTICK_DEADBAND);
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
            <= ALIGN_TOLERANCE_RADIANS.get();
  }

  public static Command driveWithLaunchAlignment(
      Drive drive, DoubleSupplier xSupplier, DoubleSupplier ySupplier) {
    ProfiledPIDController headingController =
        new ProfiledPIDController(
            ALIGN_KP.get(),
            0.0,
            ALIGN_KD.get(),
            new Constraints(ALIGN_MAX_VELOCITY.get(), ALIGN_MAX_ACCELERATION.get()));
    headingController.enableContinuousInput(-Math.PI, Math.PI);

    return Commands.run(
        () -> {
          int controllerId = headingController.hashCode();
          if (ALIGN_KP.hasChanged(controllerId)
              || ALIGN_KD.hasChanged(controllerId)
              || ALIGN_MAX_VELOCITY.hasChanged(controllerId)
              || ALIGN_MAX_ACCELERATION.hasChanged(controllerId)) {
            headingController.setPID(ALIGN_KP.get(), 0.0, ALIGN_KD.get());
            headingController.setConstraints(
                new Constraints(ALIGN_MAX_VELOCITY.get(), ALIGN_MAX_ACCELERATION.get()));
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
        new SlewRateLimiter(WHEEL_RADIUS_CHARACTERIZATION_SLEW_RATE);
    WheelRadiusCharacterizationState characterizationState =
        new WheelRadiusCharacterizationState();

    return Commands.parallel(
        Commands.sequence(
            Commands.runOnce(() -> omegaLimiter.reset(0.0)),
            Commands.run(
                () -> {
                  double omega =
                      omegaLimiter.calculate(WHEEL_RADIUS_CHARACTERIZATION_ANGULAR_SPEED);
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
                      for (int wheelIndex = 0; wheelIndex < 4; wheelIndex++) {
                        averageWheelDeltaRadians +=
                            Math.abs(
                                    currentWheelPositions[wheelIndex]
                                        - characterizationState.initialWheelPositions[wheelIndex])
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
                      for (int wheelIndex = 0; wheelIndex < 4; wheelIndex++) {
                        averageWheelDeltaRadians +=
                            Math.abs(
                                    currentWheelPositions[wheelIndex]
                                        - characterizationState.initialWheelPositions[wheelIndex])
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