
package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class Drivetrain extends SubsystemBase {

    // Four motors
    private final SparkMax leftFront =
        new SparkMax(1, MotorType.kBrushless);

    private final SparkMax leftBack =
        new SparkMax(2, MotorType.kBrushless);

    private final SparkMax rightFront =
        new SparkMax(3, MotorType.kBrushless);

    private final SparkMax rightBack =
        new SparkMax(4, MotorType.kBrushless);

    // Gyro helps us know how much the robot has turned
    private final ADXRS450_Gyro gyro = new ADXRS450_Gyro();

    public Drivetrain() {

        // The motors on each side need to turn together. Depending on how the motors are mounted, one side may need to be inverted.
        leftFront.setInverted(true);
        leftBack.setInverted(true);

        rightFront.setInverted(false);
        rightBack.setInverted(false);
    }

    // Arcade drive method
    public void arcadeDrive(double forward, double turn) {

        double leftSpeed = forward + turn;
        double rightSpeed = forward - turn;

        // Keep motor speed between -1 and 1
        leftSpeed = Math.max(-1, Math.min(1, leftSpeed));
        rightSpeed = Math.max(-1, Math.min(1, rightSpeed));

        leftFront.set(leftSpeed);
        leftBack.set(leftSpeed);

        rightFront.set(rightSpeed);
        rightBack.set(rightSpeed);
    }

    // Stop all four motors
    public void stop() {
        leftFront.set(0);
        leftBack.set(0);
        rightFront.set(0);
        rightBack.set(0);
    }

    // Reads the joystick continuously
    public Command driveWithJoystick(Joystick joystick) {

        return run(() -> {
            double forward = -joystick.getY();
            double turn = joystick.getX();

            arcadeDrive(forward, turn);
        });
    }

    // Autonomous command
    public Command autonomousCommand() {

        return Commands.sequence(

            // Reset gyro before starting
            Commands.runOnce(() -> gyro.reset(), this),

            // Drive straight for 3 seconds
            Commands.run(() -> arcadeDrive(0.5, 0), this)
                .withTimeout(3),

            // Turn until the gyro reads 90 degrees
            Commands.run(() -> arcadeDrive(0, 0.35), this)
                .until(() -> Math.abs(gyro.getAngle()) >= 90),

            // Stop the robot
            Commands.runOnce(() -> stop(), this)
        );
    }
}
