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
  public static final double JOYSTICK_DEADBAND = 0.1;

  private static final double WHEEL_RADIUS_MAX_ANGULAR_VELOCITY = 0.25; // Rad/Sec
  private static final double WHEEL_RADIUS_RAMP_RATE = 0.05; // Rad/Sec^2

  private static final LoggedTunableNumber launchDriveKp =
      new LoggedTunableNumber("DriveCommands/Launching/kP", 0.0);
  private static final LoggedTunableNumber launchDriveKd =
      new LoggedTunableNumber("DriveCommands/Launching/kD", 0.0);
  private static final LoggedTunableNumber launchDriveMaxVelocity =
      new LoggedTunableNumber("DriveCommands/Launching/MaxVelocity", 4.0);
  private static final LoggedTunableNumber launchDriveMaxAcceleration =
      new LoggedTunableNumber("DriveCommands/Launching/MaxAcceleration", 6.0);
  private static final LoggedTunableNumber launchDriveToleranceRadians =
      new LoggedTunableNumber("DriveCommands/Launching/Tolerance", Units.degreesToRadians(5));

  private DriveCommands() {}

  public static Translation2d getLinearVelocityFromJoysticks(double xInput, double yInput) {
    double linearSpeedMagnitude =
        MathUtil.applyDeadband(Math.hypot(xInput, yInput), JOYSTICK_DEADBAND);
    Rotation2d linearDirection = new Rotation2d(xInput, yInput);

    double scaledLinearMagnitude = linearSpeedMagnitude * linearSpeedMagnitude;

    return new Pose2d(Translation2d.kZero, linearDirection)
        .transformBy(new Transform2d(scaledLinearMagnitude, 0.0, Rotation2d.kZero))
        .getTranslation();
  }

  public static double getAngularVelocityFromJoystick(double omegaInput) {
    double angularInput = MathUtil.applyDeadband(omegaInput, JOYSTICK_DEADBAND);
    return angularInput * angularInput * Math.signum(angularInput);
  }

  public static ChassisSpeeds getChassisSpeedsFromJoysticks(
      double xInput, double yInput, double omegaInput) {
    Translation2d linearVelocity =
        getLinearVelocityFromJoysticks(xInput, yInput).times(DriveConstants.maxLinearSpeed);

    double angularVelocity = getAngularVelocityFromJoystick(omegaInput);

    return new ChassisSpeeds(
        linearVelocity.getX(),
        linearVelocity.getY(),
        angularVelocity * DriveConstants.maxAngularSpeed);
  }

  /**
   * Field or robot relative drive command using two joysticks (controlling linear and angular
   * velocities).
   */
  public static Command joystickDrive(
      Drive drive,
      DoubleSupplier xInputSupplier,
      DoubleSupplier yInputSupplier,
      DoubleSupplier omegaInputSupplier) {
    return Commands.run(
        () -> {
          ChassisSpeeds desiredChassisSpeeds =
              getChassisSpeedsFromJoysticks(
                  xInputSupplier.getAsDouble(),
                  yInputSupplier.getAsDouble(),
                  omegaInputSupplier.getAsDouble());

          Rotation2d robotHeading = RobotState.getInstance().getRotation();
          Rotation2d fieldRelativeHeading =
              isRedAlliance() ? robotHeading.plus(Rotation2d.kPi) : robotHeading;

          drive.runVelocity(
              ChassisSpeeds.fromFieldRelativeSpeeds(desiredChassisSpeeds, fieldRelativeHeading));
        },
        drive);
  }

  @AutoLogOutput
  public static boolean atLaunchGoal() {
    Rotation2d currentHeading = RobotState.getInstance().getRotation();
    Rotation2d targetHeading = LaunchCalculator.getInstance().getParameters().driveAngle();

    return DriverStation.isEnabled()
        && Math.abs(currentHeading.minus(targetHeading).getRadians())
            <= launchDriveToleranceRadians.get();
  }

  public static Command joystickDriveWhileLaunching(
      Drive drive, DoubleSupplier xInputSupplier, DoubleSupplier yInputSupplier) {
    ProfiledPIDController headingController =
        new ProfiledPIDController(
            launchDriveKp.get(),
            0.0,
            launchDriveKd.get(),
            new Constraints(
                launchDriveMaxVelocity.get(), launchDriveMaxAcceleration.get()));
    headingController.enableContinuousInput(-Math.PI, Math.PI);

    return Commands.run(
        () -> {
          int controllerId = headingController.hashCode();

          if (launchDriveKp.hasChanged(controllerId)
              || launchDriveKd.hasChanged(controllerId)
              || launchDriveMaxVelocity.hasChanged(controllerId)
              || launchDriveMaxAcceleration.hasChanged(controllerId)) {
            headingController.setPID(launchDriveKp.get(), 0.0, launchDriveKd.get());
            headingController.setConstraints(
                new Constraints(
                    launchDriveMaxVelocity.get(), launchDriveMaxAcceleration.get()));
          }

          double currentHeadingRadians = RobotState.getInstance().getRotation().getRadians();
          var launchParameters = LaunchCalculator.getInstance().getParameters();
          State targetHeadingState =
              new State(
                  launchParameters.driveAngle().getRadians(),
                  launchParameters.driveVelocity());

          double headingControllerOutput =
              headingController.calculate(currentHeadingRadians, targetHeadingState);
          double commandedAngularVelocity =
              headingController.getSetpoint().velocity + headingControllerOutput;

          ChassisSpeeds desiredChassisSpeeds =
              getChassisSpeedsFromJoysticks(
                  xInputSupplier.getAsDouble(),
                  yInputSupplier.getAsDouble(),
                  commandedAngularVelocity);

          Rotation2d robotHeading = RobotState.getInstance().getRotation();
          Rotation2d fieldRelativeHeading =
              isRedAlliance() ? robotHeading.plus(Rotation2d.kPi) : robotHeading;

          drive.runVelocity(
              ChassisSpeeds.fromFieldRelativeSpeeds(desiredChassisSpeeds, fieldRelativeHeading));
        },
        drive);
  }

  /** Measures the robot's wheel radius by spinning in a circle. */
  public static Command wheelRadiusCharacterization(Drive drive) {
    SlewRateLimiter angularVelocityLimiter = new SlewRateLimiter(WHEEL_RADIUS_RAMP_RATE);
    WheelRadiusCharacterizationState characterizationState =
        new WheelRadiusCharacterizationState();

    return Commands.parallel(
        Commands.sequence(
            Commands.runOnce(() -> angularVelocityLimiter.reset(0.0)),
            Commands.run(
                () -> {
                  double limitedAngularVelocity =
                      angularVelocityLimiter.calculate(WHEEL_RADIUS_MAX_ANGULAR_VELOCITY);
                  drive.runVelocity(new ChassisSpeeds(0.0, 0.0, limitedAngularVelocity));
                },
                drive)),
        Commands.sequence(
            Commands.waitSeconds(1.0),
            Commands.runOnce(
                () -> {
                  characterizationState.initialWheelPositions =
                      drive.getWheelRadiusCharacterizationPositions();
                  characterizationState.previousHeading =
                      RobotState.getInstance().getRotation();
                  characterizationState.totalGyroDeltaRadians = 0.0;
                }),
            Commands.run(
                    () -> {
                      Rotation2d currentHeading = RobotState.getInstance().getRotation();
                      characterizationState.totalGyroDeltaRadians +=
                          Math.abs(
                              currentHeading
                                  .minus(characterizationState.previousHeading)
                                  .getRadians());
                      characterizationState.previousHeading = currentHeading;

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

  private static boolean isRedAlliance() {
    Optional<Alliance> alliance = DriverStation.getAlliance();
    return alliance.isPresent() && alliance.get() == Alliance.Red;
  }

  private static class WheelRadiusCharacterizationState {
    double[] initialWheelPositions = new double[4];
    Rotation2d previousHeading = Rotation2d.kZero;
    double totalGyroDeltaRadians = 0.0;
  }
}