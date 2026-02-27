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
  // Joystick deadband for all drive inputs.
  public static final double kJoystickDeadband = 0.1;

  // Constant angular velocity used during wheel radius characterization (rad/s).
  private static final double kWheelRadiusCharacterizationOmega = 0.25;

  // Slew rate for wheel radius characterization (rad/s^2).
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
   * Converts joystick X/Y inputs into a translation vector.
   *
   * <p>Steps:
   * <ul>
   *   <li>Apply deadband to the input magnitude</li>
   *   <li>Compute direction from X/Y</li>
   *   <li>Square the magnitude for finer low-speed control</li>
   * </ul>
   */
  public static Translation2d method1(double xInput, double yInput) {
    double magnitude =
        MathUtil.applyDeadband(Math.hypot(xInput, yInput), kJoystickDeadband);
    Rotation2d direction = new Rotation2d(xInput, yInput);

    magnitude *= magnitude;

    return new Pose2d(Translation2d.kZero, direction)
        .transformBy(new Transform2d(magnitude, 0.0, Rotation2d.kZero))
        .getTranslation();
  }

  /**
   * Processes a single joystick input (e.g. rotation).
   *
   * <p>Steps:
   * <ul>
   *   <li>Apply deadband</li>
   *   <li>Square the value while preserving sign</li>
   * </ul>
   */
  public static double method2(double input) {
    double processed = MathUtil.applyDeadband(input, kJoystickDeadband);
    return processed * processed * Math.signum(processed);
  }

  /**
   * Converts joystick inputs into robot-relative {@link ChassisSpeeds}.
   *
   * @param xInput translation X joystick input
   * @param yInput translation Y joystick input
   * @param omegaInput rotation joystick input
   */
  public static ChassisSpeeds method3(double xInput, double yInput, double omegaInput) {
    Translation2d translation =
        method1(xInput, yInput).times(DriveConstants.maxLinearSpeed);
    double omega = method2(omegaInput);

    return new ChassisSpeeds(
        translation.getX(), translation.getY(), omega * DriveConstants.maxAngularSpeed);
  }

  /**
   * Field-relative drive command using joystick suppliers.
   *
   * <p>Translation:
   * <ul>
   *   <li>Squared magnitude</li>
   *   <li>Scaled by {@link DriveConstants#maxLinearSpeed}</li>
   * </ul>
   *
   * <p>Rotation:
   * <ul>
   *   <li>Squared</li>
   *   <li>Scaled by {@link DriveConstants#maxAngularSpeed}</li>
   * </ul>
   *
   * <p>Field-relative orientation is flipped for the red alliance.
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
          Rotation2d fieldHeading = getAllianceAdjustedHeading(robotHeading);

          drive.runVelocity(ChassisSpeeds.fromFieldRelativeSpeeds(speeds, fieldHeading));
        },
        drive);
  }

  /**
   * Returns true when:
   * <ul>
   *   <li>The robot is enabled</li>
   *   <li>The robot heading is within the configured angular tolerance of the desired launch drive
   *       angle</li>
   * </ul>
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
   * Field-relative drive command that aligns robot heading to the launch angle while allowing
   * manual translation control.
   *
   * <p>Heading:
   * <ul>
   *   <li>Controlled by a profiled PID controller</li>
   *   <li>Goal is the launch drive angle and velocity</li>
   * </ul>
   *
   * <p>Translation:
   * <ul>
   *   <li>Controlled directly by joystick inputs</li>
   * </ul>
   *
   * @param drive drive subsystem
   * @param xSupplier translation X joystick supplier
   * @param ySupplier translation Y joystick supplier
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
          updateHeadingControllerTunables(headingController);

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
          Rotation2d fieldHeading = getAllianceAdjustedHeading(robotHeading);

          drive.runVelocity(ChassisSpeeds.fromFieldRelativeSpeeds(speeds, fieldHeading));
        },
        drive);
  }

  /**
   * Characterizes wheel radius by spinning the robot in place and comparing gyro rotation to wheel
   * encoder rotation.
   *
   * <p>Behavior:
   * <ul>
   *   <li>Spins the robot at a constant angular velocity</li>
   *   <li>Measures gyro and wheel deltas over time</li>
   *   <li>Logs intermediate values to AdvantageScope</li>
   *   <li>Prints final results to the console</li>
   * </ul>
   */
  public static Command method7(Drive drive) {
    SlewRateLimiter omegaLimiter = new SlewRateLimiter(kWheelRadiusCharacterizationSlewRate);
    WheelRadiusCharacterizationState state = new WheelRadiusCharacterizationState();

    Command spinCommand =
        Commands.sequence(
            Commands.runOnce(() -> omegaLimiter.reset(0.0)),
            Commands.run(
                () -> {
                  double omega = omegaLimiter.calculate(kWheelRadiusCharacterizationOmega);
                  drive.runVelocity(new ChassisSpeeds(0.0, 0.0, omega));
                },
                drive));

    Command measureCommand =
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
                    () -> updateWheelRadiusCharacterization(drive, state))
                .finallyDo(() -> finishWheelRadiusCharacterization(drive, state)));

    return Commands.parallel(spinCommand, measureCommand);
  }

  private static Rotation2d getAllianceAdjustedHeading(Rotation2d robotHeading) {
    Optional<Alliance> alliance = DriverStation.getAlliance();
    if (alliance.isPresent() && alliance.get() == Alliance.Red) {
      return robotHeading.plus(Rotation2d.kPi);
    }
    return robotHeading;
  }

  private static void updateHeadingControllerTunables(ProfiledPIDController controller) {
    int id = controller.hashCode();
    boolean pidChanged =
        kAlignKp.hasChanged(id) || kAlignKd.hasChanged(id);
    boolean constraintsChanged =
        kAlignMaxVelocity.hasChanged(id) || kAlignMaxAcceleration.hasChanged(id);

    if (pidChanged) {
      controller.setPID(kAlignKp.get(), 0.0, kAlignKd.get());
    }
    if (constraintsChanged) {
      controller.setConstraints(
          new Constraints(kAlignMaxVelocity.get(), kAlignMaxAcceleration.get()));
    }
  }

  private static void updateWheelRadiusCharacterization(
      Drive drive, WheelRadiusCharacterizationState state) {
    Rotation2d currentGyro = RobotState.getInstance().getRotation();
    state.totalGyroDeltaRadians +=
        Math.abs(currentGyro.minus(state.previousGyroRotation).getRadians());
    state.previousGyroRotation = currentGyro;

    double averageWheelDelta =
        computeAverageWheelDelta(
            drive.getWheelRadiusCharacterizationPositions(), state.wheelPositionsStart);

    double wheelRadius =
        (state.totalGyroDeltaRadians * DriveConstants.driveBaseRadius) / averageWheelDelta;

    Logger.recordOutput("Drive/WheelDelta", averageWheelDelta);
    Logger.recordOutput("Drive/WheelRadius", wheelRadius);
  }

  private static void finishWheelRadiusCharacterization(
      Drive drive, WheelRadiusCharacterizationState state) {
    double averageWheelDelta =
        computeAverageWheelDelta(
            drive.getWheelRadiusCharacterizationPositions(), state.wheelPositionsStart);

    double wheelRadius =
        (state.totalGyroDeltaRadians * DriveConstants.driveBaseRadius) / averageWheelDelta;

    NumberFormat formatter = new DecimalFormat("#0.00000000");
    System.out.println("********** Wheel Radius Characterization Results **********");
    System.out.println(
        "\tWheel Delta: " + formatter.format(averageWheelDelta) + " radians");
    System.out.println(
        "\tGyro Delta: " + formatter.format(state.totalGyroDeltaRadians) + " radians");
    System.out.println(
        "\tWheel Radius: "
            + formatter.format(wheelRadius)
            + " meters, "
            + formatter.format(Units.metersToInches(wheelRadius))
            + " inches");
  }

  private static double computeAverageWheelDelta(double[] current, double[] start) {
    double averageWheelDelta = 0.0;
    for (int i = 0; i < 4; i++) {
      averageWheelDelta += Math.abs(current[i] - start[i]) / 4.0;
    }
    return averageWheelDelta;
  }

  private static class WheelRadiusCharacterizationState {
    double[] wheelPositionsStart = new double[4];
    Rotation2d previousGyroRotation = Rotation2d.kZero;
    double totalGyroDeltaRadians = 0.0;
  }
}