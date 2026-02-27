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
import java.util.Optional;
import java.util.function.DoubleSupplier;
import org.littletonrobotics.frc2026.RobotState;
import org.littletonrobotics.frc2026.subsystems.launcher.LaunchCalculator;
import org.littletonrobotics.frc2026.subsystems.var6.Drive;
import org.littletonrobotics.frc2026.subsystems.var6.DriveConstants;
import org.littletonrobotics.frc2026.util.LoggedTunableNumber;
import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;

public class Class1 {
  public static final double JOYSTICK_DEADBAND = 0.1;
  private static final double CHARACTERIZATION_TARGET_ANGULAR_SPEED = 0.25;
  private static final double CHARACTERIZATION_SLEW_RATE = 0.05;

  private static final LoggedTunableNumber kP =
      new LoggedTunableNumber("DriveCommands/Launching/kP", 0.0);
  private static final LoggedTunableNumber kD =
      new LoggedTunableNumber("DriveCommands/Launching/kD", 0.0);
  private static final LoggedTunableNumber maxVelocity =
      new LoggedTunableNumber("DriveCommands/Launching/MaxVelocity", 4.0);
  private static final LoggedTunableNumber maxAcceleration =
      new LoggedTunableNumber("DriveCommands/Launching/MaxAcceleration", 6.0);
  private static final LoggedTunableNumber angleToleranceRad =
      new LoggedTunableNumber("DriveCommands/Launching/Tolerance", Units.degreesToRadians(5));

  private Class1() {}

  /** Applies deadband and squaring to joystick X/Y and returns a scaled translation vector. */
  public static Translation2d applyTranslationalInput(double xInput, double yInput) {
    double magnitude = MathUtil.applyDeadband(Math.hypot(xInput, yInput), JOYSTICK_DEADBAND);
    Rotation2d direction = new Rotation2d(xInput, yInput);

    // Square magnitude for finer control at low speeds
    magnitude = magnitude * magnitude;

    return new Pose2d(Translation2d.kZero, direction)
        .transformBy(new Transform2d(magnitude, 0.0, Rotation2d.kZero))
        .getTranslation();
  }

  /** Applies deadband and signed squaring to a single joystick axis. */
  public static double applyRotationalInput(double input) {
    double processed = MathUtil.applyDeadband(input, JOYSTICK_DEADBAND);
    return processed * processed * Math.signum(processed);
  }

  /** Converts joystick inputs into chassis speeds in robot-relative coordinates. */
  public static ChassisSpeeds createChassisSpeeds(
      double xInput, double yInput, double rotationInput) {
    Translation2d translation =
        applyTranslationalInput(xInput, yInput).times(DriveConstants.maxLinearSpeed);
    double rotation = applyRotationalInput(rotationInput);

    return new ChassisSpeeds(
        translation.getX(), translation.getY(), rotation * DriveConstants.maxAngularSpeed);
  }

  /** Field-relative drive command using raw joystick suppliers. */
  public static Command method4(
      Drive drive, DoubleSupplier xSupplier, DoubleSupplier ySupplier, DoubleSupplier rotSupplier) {
    return Commands.run(
        () -> {
          ChassisSpeeds speeds =
              createChassisSpeeds(
                  xSupplier.getAsDouble(), ySupplier.getAsDouble(), rotSupplier.getAsDouble());

          Rotation2d robotRotation = RobotState.getInstance().getRotation();
          Rotation2d fieldReference = getAllianceAdjustedRotation(robotRotation);

          drive.runVelocity(ChassisSpeeds.fromFieldRelativeSpeeds(speeds, fieldReference));
        },
        drive);
  }

  private static Rotation2d getAllianceAdjustedRotation(Rotation2d baseRotation) {
    Optional<Alliance> alliance = DriverStation.getAlliance();
    if (alliance.isPresent() && alliance.get() == Alliance.Red) {
      return baseRotation.plus(Rotation2d.kPi);
    }
    return baseRotation;
  }

  @AutoLogOutput
  public static boolean method5() {
    if (!DriverStation.isEnabled()) {
      return false;
    }

    Rotation2d currentRotation = RobotState.getInstance().getRotation();
    Rotation2d targetRotation = LaunchCalculator.getInstance().getParameters().driveAngle();

    double errorRad = currentRotation.minus(targetRotation).getRadians();
    return Math.abs(errorRad) <= angleToleranceRad.get();
  }

  /** Drive command that aligns robot heading to the launcher drive angle using a profiled PID. */
  public static Command method6(Drive drive, DoubleSupplier xSupplier, DoubleSupplier ySupplier) {
    ProfiledPIDController headingController =
        new ProfiledPIDController(
            kP.get(), 0.0, kD.get(), new Constraints(maxVelocity.get(), maxAcceleration.get()));
    headingController.enableContinuousInput(-Math.PI, Math.PI);

    return Commands.run(
        () -> {
          // Update tunable PID/constraints if changed
          if (kP.hasChanged(headingController.hashCode())
              || kD.hasChanged(headingController.hashCode())
              || maxVelocity.hasChanged(headingController.hashCode())
              || maxAcceleration.hasChanged(headingController.hashCode())) {
            headingController.setPID(kP.get(), 0.0, kD.get());
            headingController.setConstraints(
                new Constraints(maxVelocity.get(), maxAcceleration.get()));
          }

          Rotation2d currentRotation = RobotState.getInstance().getRotation();
          var launchParams = LaunchCalculator.getInstance().getParameters();

          State goalState =
              new State(
                  launchParams.driveAngle().getRadians(), launchParams.driveVelocity());

          double controllerOutput =
              headingController.calculate(currentRotation.getRadians(), goalState);
          double commandedAngularVelocity = headingController.getSetpoint().velocity + controllerOutput;

          ChassisSpeeds speeds =
              createChassisSpeeds(
                  xSupplier.getAsDouble(), ySupplier.getAsDouble(), commandedAngularVelocity);

          Rotation2d fieldReference = getAllianceAdjustedRotation(currentRotation);
          drive.runVelocity(ChassisSpeeds.fromFieldRelativeSpeeds(speeds, fieldReference));
        },
        drive);
  }

  /** Wheel radius characterization command. */
  public static Command method7(Drive drive) {
    SlewRateLimiter angularSpeedLimiter = new SlewRateLimiter(CHARACTERIZATION_SLEW_RATE);
    WheelRadiusCharacterizationState state = new WheelRadiusCharacterizationState();

    Command spinCommand =
        Commands.sequence(
            Commands.runOnce(() -> angularSpeedLimiter.reset(0.0)),
            Commands.run(
                () -> {
                  double angularSpeed =
                      angularSpeedLimiter.calculate(CHARACTERIZATION_TARGET_ANGULAR_SPEED);
                  drive.runVelocity(new ChassisSpeeds(0.0, 0.0, angularSpeed));
                },
                drive));

    Command measureCommand =
        Commands.sequence(
            Commands.waitSeconds(1.0),
            Commands.runOnce(
                () -> {
                  state.initialWheelPositions = drive.getWheelRadiusCharacterizationPositions();
                  state.previousGyroRotation = RobotState.getInstance().getRotation();
                  state.totalGyroDelta = 0.0;
                }),
            Commands.run(
                    () -> {
                      Rotation2d currentRotation = RobotState.getInstance().getRotation();
                      state.totalGyroDelta +=
                          Math.abs(currentRotation.minus(state.previousGyroRotation).getRadians());
                      state.previousGyroRotation = currentRotation;

                      double[] currentWheelPositions =
                          drive.getWheelRadiusCharacterizationPositions();
                      double averageWheelDelta =
                          computeAverageWheelDelta(currentWheelPositions, state.initialWheelPositions);

                      double wheelRadius =
                          (state.totalGyroDelta * DriveConstants.driveBaseRadius)
                              / averageWheelDelta;

                      Logger.recordOutput("Drive/WheelDelta", averageWheelDelta);
                      Logger.recordOutput("Drive/WheelRadius", wheelRadius);
                    })
                .finallyDo(
                    () -> {
                      double[] finalWheelPositions =
                          drive.getWheelRadiusCharacterizationPositions();
                      double averageWheelDelta =
                          computeAverageWheelDelta(finalWheelPositions, state.initialWheelPositions);

                      double wheelRadius =
                          (state.totalGyroDelta * DriveConstants.driveBaseRadius)
                              / averageWheelDelta;

                      NumberFormat formatter = new DecimalFormat("#0.00000000");
                      System.out.println(
                          "********** Wheel Radius Characterization Results **********");
                      System.out.println(
                          "\tWheel Delta: " + formatter.format(averageWheelDelta) + " radians");
                      System.out.println(
                          "\tGyro Delta: " + formatter.format(state.totalGyroDelta) + " radians");
                      System.out.println(
                          "\tWheel Radius: "
                              + formatter.format(wheelRadius)
                              + " meters, "
                              + formatter.format(Units.metersToInches(wheelRadius))
                              + " inches");
                    }));

    return Commands.parallel(spinCommand, measureCommand);
  }

  private static double computeAverageWheelDelta(double[] current, double[] initial) {
    double sum = 0.0;
    int wheelCount = Math.min(current.length, initial.length);
    for (int i = 0; i < wheelCount; i++) {
      sum += Math.abs(current[i] - initial[i]);
    }
    return sum / wheelCount;
  }

  private static class WheelRadiusCharacterizationState {
    double[] initialWheelPositions = new double[4];
    Rotation2d previousGyroRotation = Rotation2d.kZero;
    double totalGyroDelta = 0.0;
  }
}