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
  public static final double JOYSTICK_DEADBAND = 0.1;
  private static final double WHEEL_RADIUS_MAX_ANGULAR_VELOCITY = 0.25;
  private static final double WHEEL_RADIUS_RAMP_RATE = 0.05;

  private static final LoggedTunableNumber launchHeadingKp =
      new LoggedTunableNumber("DriveCommands/Launching/kP", 0.0);
  private static final LoggedTunableNumber launchHeadingKd =
      new LoggedTunableNumber("DriveCommands/Launching/kD", 0.0);
  private static final LoggedTunableNumber launchMaxAngularVelocity =
      new LoggedTunableNumber("DriveCommands/Launching/MaxVelocity", 4.0);
  private static final LoggedTunableNumber launchMaxAngularAcceleration =
      new LoggedTunableNumber("DriveCommands/Launching/MaxAcceleration", 6.0);
  private static final LoggedTunableNumber launchAngleToleranceRadians =
      new LoggedTunableNumber("DriveCommands/Launching/Tolerance", Units.degreesToRadians(5));

  private DriveCommands() {}

  public static Translation2d getLinearVelocityFromJoysticks(
      double xAxisInput, double yAxisInput) {
    double linearSpeedMagnitude =
        MathUtil.applyDeadband(Math.hypot(xAxisInput, yAxisInput), JOYSTICK_DEADBAND);
    Rotation2d linearDirection = new Rotation2d(xAxisInput, yAxisInput);

    linearSpeedMagnitude = linearSpeedMagnitude * linearSpeedMagnitude;

    return new Pose2d(Translation2d.kZero, linearDirection)
        .transformBy(new Transform2d(linearSpeedMagnitude, 0.0, Rotation2d.kZero))
        .getTranslation();
  }

  public static double getAngularVelocityFromJoystick(double rotationAxisInput) {
    double angularVelocity = MathUtil.applyDeadband(rotationAxisInput, JOYSTICK_DEADBAND);
    return angularVelocity * angularVelocity * Math.signum(angularVelocity);
  }

  public static ChassisSpeeds getSpeedsFromJoysticks(
      double xAxisInput, double yAxisInput, double rotationAxisInput) {
    Translation2d linearVelocity =
        getLinearVelocityFromJoysticks(xAxisInput, yAxisInput)
            .times(DriveConstants.maxLinearSpeed);

    double angularVelocity = getAngularVelocityFromJoystick(rotationAxisInput);

    return new ChassisSpeeds(
        linearVelocity.getX(),
        linearVelocity.getY(),
        angularVelocity * DriveConstants.maxAngularSpeed);
  }

  public static Command joystickDrive(
      Drive drive,
      DoubleSupplier xAxisInputSupplier,
      DoubleSupplier yAxisInputSupplier,
      DoubleSupplier rotationAxisInputSupplier) {
    return Commands.run(
        () -> {
          ChassisSpeeds desiredChassisSpeeds =
              getSpeedsFromJoysticks(
                  xAxisInputSupplier.getAsDouble(),
                  yAxisInputSupplier.getAsDouble(),
                  rotationAxisInputSupplier.getAsDouble());

          Rotation2d robotHeading = RobotState.getInstance().getRotation();
          Rotation2d fieldRelativeHeading = getFieldRelativeHeading(robotHeading);

          drive.runVelocity(
              ChassisSpeeds.fromFieldRelativeSpeeds(desiredChassisSpeeds, fieldRelativeHeading));
        },
        drive);
  }

  @AutoLogOutput
  public static boolean atLaunchGoal() {
    Rotation2d currentHeading = RobotState.getInstance().getRotation();
    Rotation2d targetHeading = LaunchCalculator.getInstance().getParameters().driveAngle();

    double headingErrorRadians = currentHeading.minus(targetHeading).getRadians();

    return DriverStation.isEnabled()
        && Math.abs(headingErrorRadians) <= launchAngleToleranceRadians.get();
  }

  public static Command joystickDriveWhileLaunching(
      Drive drive, DoubleSupplier xAxisInputSupplier, DoubleSupplier yAxisInputSupplier) {
    ProfiledPIDController launchHeadingController =
        new ProfiledPIDController(
            launchHeadingKp.get(),
            0.0,
            launchHeadingKd.get(),
            new Constraints(
                launchMaxAngularVelocity.get(), launchMaxAngularAcceleration.get()));
    launchHeadingController.enableContinuousInput(-Math.PI, Math.PI);

    return Commands.run(
        () -> {
          int controllerId = launchHeadingController.hashCode();
          if (launchHeadingKp.hasChanged(controllerId)
              || launchHeadingKd.hasChanged(controllerId)
              || launchMaxAngularVelocity.hasChanged(controllerId)
              || launchMaxAngularAcceleration.hasChanged(controllerId)) {
            launchHeadingController.setPID(launchHeadingKp.get(), 0.0, launchHeadingKd.get());
            launchHeadingController.setConstraints(
                new Constraints(
                    launchMaxAngularVelocity.get(), launchMaxAngularAcceleration.get()));
          }

          Rotation2d currentHeading = RobotState.getInstance().getRotation();
          var launchParameters = LaunchCalculator.getInstance().getParameters();
          Rotation2d targetHeading = launchParameters.driveAngle();
          double targetAngularVelocity = launchParameters.driveVelocity();

          State targetHeadingState = new State(targetHeading.getRadians(), targetAngularVelocity);

          double headingPidOutput =
              launchHeadingController.calculate(currentHeading.getRadians(), targetHeadingState);
          double commandedAngularVelocity =
              launchHeadingController.getSetpoint().velocity + headingPidOutput;

          ChassisSpeeds desiredChassisSpeeds =
              getSpeedsFromJoysticks(
                  xAxisInputSupplier.getAsDouble(),
                  yAxisInputSupplier.getAsDouble(),
                  commandedAngularVelocity);

          Rotation2d fieldRelativeHeading = getFieldRelativeHeading(currentHeading);

          drive.runVelocity(
              ChassisSpeeds.fromFieldRelativeSpeeds(desiredChassisSpeeds, fieldRelativeHeading));
        },
        drive);
  }

  public static Command wheelRadiusCharacterization(Drive drive) {
    SlewRateLimiter angularVelocityLimiter = new SlewRateLimiter(WHEEL_RADIUS_RAMP_RATE);
    WheelRadiusCharacterizationState characterizationState = new WheelRadiusCharacterizationState();

    return Commands.parallel(
        Commands.sequence(
            Commands.runOnce(() -> angularVelocityLimiter.reset(0.0)),
            Commands.run(
                () -> {
                  double angularVelocity =
                      angularVelocityLimiter.calculate(WHEEL_RADIUS_MAX_ANGULAR_VELOCITY);
                  drive.runVelocity(new ChassisSpeeds(0.0, 0.0, angularVelocity));
                },
                drive)),
        Commands.sequence(
            Commands.waitSeconds(1.0),
            Commands.runOnce(
                () -> {
                  characterizationState.initialWheelPositions =
                      drive.getWheelRadiusCharacterizationPositions();
                  characterizationState.lastGyroAngle = RobotState.getInstance().getRotation();
                  characterizationState.totalGyroDeltaRadians = 0.0;
                }),
            Commands.run(
                    () -> {
                      Rotation2d currentGyroAngle = RobotState.getInstance().getRotation();
                      characterizationState.totalGyroDeltaRadians +=
                          Math.abs(
                              currentGyroAngle
                                  .minus(characterizationState.lastGyroAngle)
                                  .getRadians());
                      characterizationState.lastGyroAngle = currentGyroAngle;

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

                      double estimatedWheelRadiusMeters =
                          (characterizationState.totalGyroDeltaRadians
                                  * DriveConstants.driveBaseRadius)
                              / averageWheelDeltaRadians;

                      Logger.recordOutput("Drive/WheelDelta", averageWheelDeltaRadians);
                      Logger.recordOutput("Drive/WheelRadius", estimatedWheelRadiusMeters);
                    })
                .finallyDo(
                    () -> {
                      double[] finalWheelPositions =
                          drive.getWheelRadiusCharacterizationPositions();
                      double averageWheelDeltaRadians = 0.0;
                      for (int wheelIndex = 0; wheelIndex < 4; wheelIndex++) {
                        averageWheelDeltaRadians +=
                            Math.abs(
                                    finalWheelPositions[wheelIndex]
                                        - characterizationState.initialWheelPositions[wheelIndex])
                                / 4.0;
                      }

                      double estimatedWheelRadiusMeters =
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
                              + formatter.format(estimatedWheelRadiusMeters)
                              + " meters, "
                              + formatter.format(
                                  Units.metersToInches(estimatedWheelRadiusMeters))
                              + " inches");
                    })));
  }

  private static Rotation2d getFieldRelativeHeading(Rotation2d robotHeading) {
    Optional<Alliance> alliance = DriverStation.getAlliance();
    if (alliance.isPresent() && alliance.get() == Alliance.Red) {
      return robotHeading.plus(Rotation2d.kPi);
    }
    return robotHeading;
  }

  private static class WheelRadiusCharacterizationState {
    double[] initialWheelPositions = new double[4];
    Rotation2d lastGyroAngle = Rotation2d.kZero;
    double totalGyroDeltaRadians = 0.0;
  }
}