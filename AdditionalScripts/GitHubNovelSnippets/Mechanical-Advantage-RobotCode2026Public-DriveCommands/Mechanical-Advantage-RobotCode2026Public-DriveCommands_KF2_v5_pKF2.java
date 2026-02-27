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

  /**
   * Converts joystick X/Y inputs into a squared-magnitude translation vector with deadband.
   *
   * <p>The magnitude is squared to provide finer control at low speeds while still allowing full
   * power at the extremes.
   *
   * @param x joystick X input
   * @param y joystick Y input
   * @return translation representing desired linear velocity direction and magnitude (0–1)
   */
  public static Translation2d getLinearVelocityFromJoysticks(double x, double y) {
    double linearMagnitude = MathUtil.applyDeadband(Math.hypot(x, y), DEADBAND);
    Rotation2d linearDirection = new Rotation2d(x, y);

    linearMagnitude *= linearMagnitude;

    return new Pose2d(Translation2d.kZero, linearDirection)
        .transformBy(new Transform2d(linearMagnitude, 0.0, Rotation2d.kZero))
        .getTranslation();
  }

  /**
   * Converts joystick omega input into a squared-magnitude rotational velocity with deadband.
   *
   * <p>The sign of the original input is preserved.
   *
   * @param driverOmega joystick rotational input
   * @return scaled rotational command in the range [-1, 1]
   */
  public static double getOmegaFromJoysticks(double driverOmega) {
    double omega = MathUtil.applyDeadband(driverOmega, DEADBAND);
    return omega * omega * Math.signum(omega);
  }

  /**
   * Converts joystick inputs into chassis-relative speeds.
   *
   * @param driverX joystick X input
   * @param driverY joystick Y input
   * @param driverOmega joystick rotational input
   * @return chassis speeds in robot coordinates
   */
  public static ChassisSpeeds getSpeedsFromJoysticks(
      double driverX, double driverY, double driverOmega) {
    Translation2d linearVelocity =
        getLinearVelocityFromJoysticks(driverX, driverY).times(DriveConstants.maxLinearSpeed);
    double omega = getOmegaFromJoysticks(driverOmega);

    return new ChassisSpeeds(
        linearVelocity.getX(), linearVelocity.getY(), omega * DriveConstants.maxAngularSpeed);
  }

  /**
   * Field-relative joystick drive command.
   *
   * <p>Translates joystick inputs into field-relative chassis speeds, adjusted for alliance, and
   * sends them to the drive subsystem.
   *
   * @param drive drive subsystem
   * @param xSupplier supplier for X joystick input
   * @param ySupplier supplier for Y joystick input
   * @param omegaSupplier supplier for rotational joystick input
   * @return command that drives based on joystick inputs
   */
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
          drive.runVelocity(
              ChassisSpeeds.fromFieldRelativeSpeeds(
                  speeds, getAllianceAdjustedRotation(RobotState.getInstance().getRotation())));
        },
        drive);
  }

  /**
   * Indicates whether the robot is within the configured angular tolerance of the launch angle.
   *
   * <p>Returns false when the robot is disabled.
   *
   * @return true if at goal while enabled, false otherwise
   */
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

  /**
   * Joystick drive command that automatically rotates the robot to the launch angle using a
   * profiled PID controller.
   *
   * <p>Translation is controlled by the joysticks; rotation is controlled by the launch controller.
   *
   * @param drive drive subsystem
   * @param xSupplier supplier for X joystick input
   * @param ySupplier supplier for Y joystick input
   * @return command that drives translation from joysticks and rotation from launch controller
   */
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
          int controllerId = controller.hashCode();
          if (driveLaunchKp.hasChanged(controllerId)
              || driveLaunchKd.hasChanged(controllerId)
              || driveLaunchMaxVelocity.hasChanged(controllerId)
              || driveLaunchMaxAcceleration.hasChanged(controllerId)) {
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

          drive.runVelocity(
              ChassisSpeeds.fromFieldRelativeSpeeds(
                  speeds, getAllianceAdjustedRotation(RobotState.getInstance().getRotation())));
        },
        drive);
  }

  /**
   * Characterizes wheel radius by spinning in place and comparing gyro rotation to wheel encoder
   * deltas.
   *
   * <p>The robot ramps up to a constant angular velocity, then measures the relationship between
   * gyro angle change and wheel encoder change to compute the effective wheel radius.
   *
   * @param drive drive subsystem
   * @return command that runs the characterization and logs results
   */
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
                      Rotation2d rotation = RobotState.getInstance().getRotation();
                      state.gyroDelta += Math.abs(rotation.minus(state.lastAngle).getRadians());
                      state.lastAngle = rotation;

                      double[] positions = drive.getWheelRadiusCharacterizationPositions();
                      double wheelDelta = calculateAverageWheelDelta(positions, state.positions);
                      double wheelRadius =
                          (state.gyroDelta * DriveConstants.driveBaseRadius) / wheelDelta;

                      Logger.recordOutput("Drive/WheelDelta", wheelDelta);
                      Logger.recordOutput("Drive/WheelRadius", wheelRadius);
                    })
                .finallyDo(
                    () -> {
                      double[] positions = drive.getWheelRadiusCharacterizationPositions();
                      double wheelDelta = calculateAverageWheelDelta(positions, state.positions);
                      double wheelRadius =
                          (state.gyroDelta * DriveConstants.driveBaseRadius) / wheelDelta;

                      NumberFormat formatter = new DecimalFormat("#0.00000000");
                      System.out.println(
                          "********** Wheel Radius Characterization Results **********");
                      System.out.println(
                          "\tWheel Delta: " + formatter.format(wheelDelta) + " radians");
                      System.out.println(
                          "\tGyro Delta: " + formatter.format(state.gyroDelta) + " radians");
                      System.out.println(
                          "\tWheel Radius: "
                              + formatter.format(wheelRadius)
                              + " meters, "
                              + formatter.format(Units.metersToInches(wheelRadius))
                              + " inches");
                    }));

    return Commands.parallel(rampCommand, measureCommand);
  }

  /**
   * Adjusts a rotation based on alliance color so that field-relative control is mirrored for the
   * red alliance.
   *
   * @param baseRotation original rotation
   * @return rotation adjusted for alliance
   */
  private static Rotation2d getAllianceAdjustedRotation(Rotation2d baseRotation) {
    Optional<Alliance> alliance = DriverStation.getAlliance();
    if (alliance.isPresent() && alliance.get() == Alliance.Red) {
      return baseRotation.plus(Rotation2d.kPi);
    }
    return baseRotation;
  }

  /**
   * Computes the average absolute change in wheel position between two samples.
   *
   * @param current current wheel positions
   * @param initial initial wheel positions
   * @return average absolute delta across all wheels
   */
  private static double calculateAverageWheelDelta(double[] current, double[] initial) {
    double wheelDelta = 0.0;
    int wheelCount = Math.min(current.length, initial.length);
    for (int i = 0; i < wheelCount; i++) {
      wheelDelta += Math.abs(current[i] - initial[i]) / wheelCount;
    }
    return wheelDelta;
  }

  private static class WheelRadiusCharacterizationState {
    double[] positions = new double[4];
    Rotation2d lastAngle = Rotation2d.kZero;
    double gyroDelta = 0.0;
  }
}