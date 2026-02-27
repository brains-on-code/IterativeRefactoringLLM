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
    double linearMagnitude =
        MathUtil.applyDeadband(Math.hypot(xInput, yInput), JOYSTICK_DEADBAND);
    Rotation2d linearDirection = new Rotation2d(xInput, yInput);

    linearMagnitude = linearMagnitude * linearMagnitude;

    return new Pose2d(Translation2d.kZero, linearDirection)
        .transformBy(new Transform2d(linearMagnitude, 0.0, Rotation2d.kZero))
        .getTranslation();
  }

  public static double getAngularVelocityFromJoystick(double omegaInput) {
    double omega = MathUtil.applyDeadband(omegaInput, JOYSTICK_DEADBAND);
    return omega * omega * Math.signum(omega);
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
      DoubleSupplier xSupplier,
      DoubleSupplier ySupplier,
      DoubleSupplier omegaSupplier) {
    return Commands.run(
        () -> {
          ChassisSpeeds desiredSpeeds =
              getChassisSpeedsFromJoysticks(
                  xSupplier.getAsDouble(), ySupplier.getAsDouble(), omegaSupplier.getAsDouble());

          Rotation2d robotHeading = RobotState.getInstance().getRotation();
          Rotation2d fieldRelativeHeading =
              isRedAlliance()
                  ? robotHeading.plus(Rotation2d.kPi)
                  : robotHeading;

          drive.runVelocity(
              ChassisSpeeds.fromFieldRelativeSpeeds(desiredSpeeds, fieldRelativeHeading));
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
      Drive drive, DoubleSupplier xSupplier, DoubleSupplier ySupplier) {
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

          double currentHeadingRadians =
              RobotState.getInstance().getRotation().getRadians();
          var launchParameters = LaunchCalculator.getInstance().getParameters();
          State targetState =
              new State(
                  launchParameters.driveAngle().getRadians(),
                  launchParameters.driveVelocity());

          double pidOutput = headingController.calculate(currentHeadingRadians, targetState);
          double commandedOmega =
              headingController.getSetpoint().velocity + pidOutput;

          ChassisSpeeds desiredSpeeds =
              getChassisSpeedsFromJoysticks(
                  xSupplier.getAsDouble(), ySupplier.getAsDouble(), commandedOmega);

          Rotation2d robotHeading = RobotState.getInstance().getRotation();
          Rotation2d fieldRelativeHeading =
              isRedAlliance()
                  ? robotHeading.plus(Rotation2d.kPi)
                  : robotHeading;

          drive.runVelocity(
              ChassisSpeeds.fromFieldRelativeSpeeds(desiredSpeeds, fieldRelativeHeading));
        },
        drive);
  }

  /** Measures the robot's wheel radius by spinning in a circle. */
  public static Command wheelRadiusCharacterization(Drive drive) {
    SlewRateLimiter angularVelocityLimiter =
        new SlewRateLimiter(WHEEL_RADIUS_RAMP_RATE);
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
                  characterizationState.wheelPositions =
                      drive.getWheelRadiusCharacterizationPositions();
                  characterizationState.lastHeading =
                      RobotState.getInstance().getRotation();
                  characterizationState.totalGyroDeltaRadians = 0.0;
                }),
            Commands.run(
                    () -> {
                      Rotation2d currentHeading =
                          RobotState.getInstance().getRotation();
                      characterizationState.totalGyroDeltaRadians +=
                          Math.abs(
                              currentHeading
                                  .minus(characterizationState.lastHeading)
                                  .getRadians());
                      characterizationState.lastHeading = currentHeading;

                      double[] currentWheelPositions =
                          drive.getWheelRadiusCharacterizationPositions();
                      double averageWheelDeltaRadians = 0.0;
                      for (int i = 0; i < 4; i++) {
                        averageWheelDeltaRadians +=
                            Math.abs(
                                    currentWheelPositions[i]
                                        - characterizationState.wheelPositions[i])
                                / 4.0;
                      }

                      double estimatedWheelRadiusMeters =
                          (characterizationState.totalGyroDeltaRadians
                                  * DriveConstants.driveBaseRadius)
                              / averageWheelDeltaRadians;

                      Logger.recordOutput(
                          "Drive/WheelDelta", averageWheelDeltaRadians);
                      Logger.recordOutput(
                          "Drive/WheelRadius", estimatedWheelRadiusMeters);
                    })
                .finallyDo(
                    () -> {
                      double[] finalWheelPositions =
                          drive.getWheelRadiusCharacterizationPositions();
                      double averageWheelDeltaRadians = 0.0;
                      for (int i = 0; i < 4; i++) {
                        averageWheelDeltaRadians +=
                            Math.abs(
                                    finalWheelPositions[i]
                                        - characterizationState.wheelPositions[i])
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
    double[] wheelPositions = new double[4];
    Rotation2d lastHeading = Rotation2d.kZero;
    double totalGyroDeltaRadians = 0.0;
  }
}