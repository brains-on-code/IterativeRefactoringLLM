
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
import java.util.function.DoubleSupplier;
import org.littletonrobotics.frc2026.RobotState;
import org.littletonrobotics.frc2026.subsystems.drive.Drive;
import org.littletonrobotics.frc2026.subsystems.drive.DriveConstants;
import org.littletonrobotics.frc2026.subsystems.launcher.LaunchCalculator;
import org.littletonrobotics.frc2026.util.LoggedTunableNumber;
import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;

public class DriveCommands {
  public static final double deadband = 0.1;
  private static final double wheelRadiusMaxVelocity = 0.25;
  private static final double wheelRadiusRampRate = 0.05;

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

  public static Translation2d getLinearVelocityFromJoysticks(double x, double y) {
    double linearMagnitude = MathUtil.applyDeadband(Math.hypot(x, y), deadband);
    Rotation2d linearDirection = new Rotation2d(x, y);

    linearMagnitude = linearMagnitude * linearMagnitude;

    return new Pose2d(Translation2d.kZero, linearDirection)
        .transformBy(new Transform2d(linearMagnitude, 0.0, Rotation2d.kZero))
        .getTranslation();
  }

  public static double getOmegaFromJoysticks(double driverOmega) {
    double omega = MathUtil.applyDeadband(driverOmega, deadband);
    return omega * omega * Math.signum(omega);
  }

  public static ChassisSpeeds getSpeedsFromJoysticks(
      double driverX, double driverY, double driverOmega) {
    Translation2d linearVelocity =
        getLinearVelocityFromJoysticks(driverX, driverY).times(DriveConstants.maxLinearSpeed);

    double omega = getOmegaFromJoysticks(driverOmega);

    return new ChassisSpeeds(
        linearVelocity.getX(), linearVelocity.getY(), omega * DriveConstants.maxAngularSpeed);
  }


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
                  speeds,
                  DriverStation.getAlliance().isPresent()
                          && DriverStation.getAlliance().get() == Alliance.Red
                      ? RobotState.getInstance().getRotation().plus(Rotation2d.kPi)
                      : RobotState.getInstance().getRotation()));
        },
        drive);
  }

  @AutoLogOutput
  public static boolean atLaunchGoal() {
    return DriverStation.isEnabled()
        && Math.abs(
                RobotState.getInstance()
                    .getRotation()
                    .minus(LaunchCalculator.getInstance().getParameters().driveAngle())
                    .getRadians())
            <= driveLaunchTolerance.get();
  }

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
          if (driveLaunchKp.hasChanged(controller.hashCode())
              || driveLaunchKd.hasChanged(controller.hashCode())
              || driveLaunchMaxVelocity.hasChanged(controller.hashCode())
              || driveLaunchMaxAcceleration.hasChanged(controller.hashCode())) {
            controller.setPID(driveLaunchKp.get(), 0.0, driveLaunchKd.get());
            controller.setConstraints(
                new Constraints(driveLaunchMaxVelocity.get(), driveLaunchMaxAcceleration.get()));
          }

          var pidOutput =
              controller.calculate(
                  RobotState.getInstance().getRotation().getRadians(),
                  new State(
                      LaunchCalculator.getInstance().getParameters().driveAngle().getRadians(),
                      LaunchCalculator.getInstance().getParameters().driveVelocity()));
          double omegaOutput = controller.getSetpoint().velocity + pidOutput;
          ChassisSpeeds speeds =
              getSpeedsFromJoysticks(xSupplier.getAsDouble(), ySupplier.getAsDouble(), omegaOutput);
          drive.runVelocity(
              ChassisSpeeds.fromFieldRelativeSpeeds(
                  speeds,
                  DriverStation.getAlliance().isPresent()
                          && DriverStation.getAlliance().get() == Alliance.Red
                      ? RobotState.getInstance().getRotation().plus(Rotation2d.kPi)
                      : RobotState.getInstance().getRotation()));
        },
        drive);
  }


  public static Command wheelRadiusCharacterization(Drive drive) {
    SlewRateLimiter limiter = new SlewRateLimiter(wheelRadiusRampRate);
    WheelRadiusCharacterizationState state = new WheelRadiusCharacterizationState();

    return Commands.parallel(
        Commands.sequence(
            Commands.runOnce(() -> limiter.reset(0.0)),

            Commands.run(
                () -> {
                  double speed = limiter.calculate(wheelRadiusMaxVelocity);
                  drive.runVelocity(new ChassisSpeeds(0.0, 0.0, speed));
                },
                drive)),

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
                      var rotation = RobotState.getInstance().getRotation();
                      state.gyroDelta += Math.abs(rotation.minus(state.lastAngle).getRadians());
                      state.lastAngle = rotation;

                      double[] positions = drive.getWheelRadiusCharacterizationPositions();
                      double wheelDelta = 0.0;
                      for (int i = 0; i < 4; i++) {
                        wheelDelta += Math.abs(positions[i] - state.positions[i]) / 4.0;
                      }
                      double wheelRadius =
                          (state.gyroDelta * DriveConstants.driveBaseRadius) / wheelDelta;

                      Logger.recordOutput("Drive/WheelDelta", wheelDelta);
                      Logger.recordOutput("Drive/WheelRadius", wheelRadius);
                    })

                .finallyDo(
                    () -> {
                      double[] positions = drive.getWheelRadiusCharacterizationPositions();
                      double wheelDelta = 0.0;
                      for (int i = 0; i < 4; i++) {
                        wheelDelta += Math.abs(positions[i] - state.positions[i]) / 4.0;
                      }
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
                    })));
  }

  private static class WheelRadiusCharacterizationState {
    double[] positions = new double[4];
    Rotation2d lastAngle = Rotation2d.kZero;
    double gyroDelta = 0.0;
  }
}
