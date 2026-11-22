
package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;

public class Climber extends SubsystemBase {

    // Two motors for the climber
    private final SparkMax leftMotor =
        new SparkMax(5, MotorType.kBrushless);

    private final SparkMax rightMotor =
        new SparkMax(6, MotorType.kBrushless);

    public Climber() {

        rightMotor.setInverted(true);
    }

    // Set speed for both motors
    public void setClimberSpeed(double speed) {

        leftMotor.set(speed);
        rightMotor.set(speed);
    }

    // Stop both motors
    public void stopClimber() {

        leftMotor.set(0);
        rightMotor.set(0);
    }

    // Command to raise the robot
    public Command raiseCommand() {

        return runEnd(
            () -> setClimberSpeed(0.7),
            () -> stopClimber()
        );
    }

    // Command to lower the robot
    public Command lowerCommand() {

        return runEnd(
            () -> setClimberSpeed(-0.5),
            () -> stopClimber()
        );
    }
}
