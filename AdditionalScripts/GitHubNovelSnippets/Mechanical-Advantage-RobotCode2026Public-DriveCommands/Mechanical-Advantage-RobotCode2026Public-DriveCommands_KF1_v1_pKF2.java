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
  /** Joystick deadband for all drive inputs. */
  public static final double kJoystickDeadband = 0.1;

  /** Constant angular velocity used during wheel radius characterization (rad/s). */
  private static final double kWheelRadiusCharacterizationOmega = 0.25;

  /** Slew rate for wheel radius characterization (rad/s^2). */
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

  /**
   * Applies deadband and squaring to joystick X/Y inputs and returns a translation vector in the
   * joystick direction with squared magnitude.
   */
  public static Translation2d method1(double xInput, double yInput) {
    double magnitude =
        MathUtil.applyDeadband(Math.hypot(xInput, yInput), kJoystickDeadband);
    Rotation2d direction = new Rotation2d(xInput, yInput);

    // Square the magnitude for finer control near center.
    magnitude = magnitude * magnitude;

    return new Pose2d(Translation2d.kZero, direction)
        .transformBy(new Transform2d(magnitude, 0.0, Rotation2d.kZero))
        .getTranslation();
  }

  /**
   * Applies deadband and signed squaring to a single joystick input (e.g. rotation).
   */
  public static double method2(double input) {
    double processed = MathUtil.applyDeadband(input, kJoystickDeadband);
    return processed * processed * Math.signum(processed);
  }

  /**
   * Converts joystick inputs into a {@link ChassisSpeeds} in robot-relative coordinates.
   *
   * @param xInput joystick X (translation)
   * @param yInput joystick Y (translation)
   * @param omegaInput joystick rotation
   */
  public static ChassisSpeeds method3(double xInput, double yInput, double omegaInput) {
    Translation2d translation =
        method1(xInput, yInput).times(DriveConstants.maxLinearSpeed);
    double omega = method2(omegaInput);

    return new ChassisSpeeds(
        translation.getX(), translation.getY(), omega * DriveConstants.maxAngularSpeed);
  }

  /**
   * Field-relative drive command using raw joystick suppliers.
   *
   * <p>Translation is squared and scaled by {@link DriveConstants#maxLinearSpeed}. Rotation is
   * squared and scaled by {@link DriveConstants#maxAngularSpeed}. Field-relative orientation is
   * flipped for the red alliance.
   */
  public static Command method4(
      Drive drive,
      DoubleSupplier xSupplier,
      DoubleSupplier ySupplier,
      DoubleSupplier omegaSupplier) {
    return Commands.run(
        () -> {
          ChassisSpeeds speeds =
              method3(
                  xSupplier.getAsDouble(),
                  ySupplier.getAsDouble(),
                  omegaSupplier.getAsDouble());

          Rotation2d robotHeading = RobotState.getInstance().getRotation();
          Rotation2d fieldHeading =
              DriverStation.getAlliance().isPresent()
                      && DriverStation.getAlliance().get() == Alliance.Red
                  ? robotHeading.plus(Rotation2d.kPi)
                  : robotHeading;

          drive.runVelocity(ChassisSpeeds.fromFieldRelativeSpeeds(speeds, fieldHeading));
        },
        drive);
  }

  /**
   * Returns true when the robot is enabled and within the configured angular tolerance of the
   * desired launch drive angle.
   */
  @AutoLogOutput
  public static boolean method5() {
    if (!DriverStation.isEnabled()) {
      return false;
    }

    Rotation2d current = RobotState.getInstance().getRotation();
    Rotation2d target = LaunchCalculator.getInstance().getParameters().driveAngle();
    double errorRadians = current.minus(target).getRadians();

    return Math.abs(errorRadians) <= kAlignToleranceRadians.get();
  }

  /**
   * Field-relative drive command that aligns the robot's heading to the launch angle using a
   * profiled PID controller while allowing manual translation control.
   *
   * @param drive drive subsystem
   * @param xSupplier joystick X (translation)
   * @param ySupplier joystick Y (translation)
   */
  public static Command method6(Drive drive, DoubleSupplier xSupplier, DoubleSupplier ySupplier) {
    ProfiledPIDController headingController =
        new ProfiledPIDController(
            kAlignKp.get(),
            0.0,
            kAlignKd.get(),
            new Constraints(kAlignMaxVelocity.get(), kAlignMaxAcceleration.get()));
    headingController.enableContinuousInput(-Math.PI, Math.PI);

    return Commands.run(
        () -> {
          // Update tunable PID/constraints if changed.
          if (kAlignKp.hasChanged(headingController.hashCode())
              || kAlignKd.hasChanged(headingController.hashCode())
              || kAlignMaxVelocity.hasChanged(headingController.hashCode())
              || kAlignMaxAcceleration.hasChanged(headingController.hashCode())) {
            headingController.setPID(kAlignKp.get(), 0.0, kAlignKd.get());
            headingController.setConstraints(
                new Constraints(kAlignMaxVelocity.get(), kAlignMaxAcceleration.get()));
          }

          double currentHeading = RobotState.getInstance().getRotation().getRadians();
          var launchParams = LaunchCalculator.getInstance().getParameters();
          State goalState =
              new State(
                  launchParams.driveAngle().getRadians(), launchParams.driveVelocity());

          double controllerOutput = headingController.calculate(currentHeading, goalState);
          double commandedOmega = headingController.getSetpoint().velocity + controllerOutput;

          ChassisSpeeds speeds =
              method3(
                  xSupplier.getAsDouble(),
                  ySupplier.getAsDouble(),
                  commandedOmega);

          Rotation2d robotHeading = RobotState.getInstance().getRotation();
          Rotation2d fieldHeading =
              DriverStation.getAlliance().isPresent()
                      && DriverStation.getAlliance().get() == Alliance.Red
                  ? robotHeading.plus(Rotation2d.kPi)
                  : robotHeading;

          drive.runVelocity(ChassisSpeeds.fromFieldRelativeSpeeds(speeds, fieldHeading));
        },
        drive);
  }

  /**
   * Characterizes the wheel radius by spinning the robot in place at a constant angular velocity
   * and comparing gyro rotation to wheel encoder rotation.
   *
   * <p>Outputs intermediate values to AdvantageScope and prints final results to the console.
   */
  public static Command method7(Drive drive) {
    SlewRateLimiter omegaLimiter = new SlewRateLimiter(kWheelRadiusCharacterizationSlewRate);
    WheelRadiusCharacterizationState state = new WheelRadiusCharacterizationState();

    return Commands.parallel(
        // Spin the robot in place with a ramped angular velocity.
        Commands.sequence(
            Commands.runOnce(() -> omegaLimiter.reset(0.0)),
            Commands.run(
                () -> {
                  double omega = omegaLimiter.calculate(kWheelRadiusCharacterizationOmega);
                  drive.runVelocity(new ChassisSpeeds(0.0, 0.0, omega));
                },
                drive)),

        // Measure wheel and gyro deltas and compute wheel radius.
        Commands.sequence(
            Commands.waitSeconds(1.0),
            Commands.runOnce(
                () -> {
                  state.wheelPositionsStart = drive.getWheelRadiusCharacterizationPositions();
                  state.previousGyroRotation = RobotState.getInstance().getRotation();
                  state.totalGyroDeltaRadians = 0.0;
                }),
            Commands
                .run(
                    () -> {
                      Rotation2d currentGyro = RobotState.getInstance().getRotation();
                      state.totalGyroDeltaRadians +=
                          Math.abs(currentGyro.minus(state.previousGyroRotation).getRadians());
                      state.previousGyroRotation = currentGyro;

                      double[] currentWheelPositions =
                          drive.getWheelRadiusCharacterizationPositions();
                      double averageWheelDelta = 0.0;
                      for (int i = 0; i < 4; i++) {
                        averageWheelDelta +=
                            Math.abs(currentWheelPositions[i] - state.wheelPositionsStart[i]) / 4.0;
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
                            Math.abs(currentWheelPositions[i] - state.wheelPositionsStart[i]) / 4.0;
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
    double[] wheelPositionsStart = new double[4];
    Rotation2d previousGyroRotation = Rotation2d.kZero;
    double totalGyroDeltaRadians = 0.0;
  }
}