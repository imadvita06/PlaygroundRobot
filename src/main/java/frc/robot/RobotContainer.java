package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Climber;

public class RobotContainer {

    private final Drivetrain drivetrain = new Drivetrain();

    // Joystick connected to port 0
    private final Joystick joystick = new Joystick(0);

    public RobotContainer() {

        // Robot will drive using joystick whenever
        // another drivetrain command is not running
        drivetrain.setDefaultCommand(
            drivetrain.driveWithJoystick(joystick)
        );
        configureButtonBindings();
    }

    public Drivetrain getDrivetrain() {
        return drivetrain;
    }
    
    
    // Create climber
    private final Climber climber = new Climber();

    // Controller connected to port 1
    private final CommandXboxController controller =
        new CommandXboxController(1);

    private void configureButtonBindings() {

        // Hold A to raise the robot
        controller.a().whileTrue(climber.raiseCommand());

        // Hold B to lower the robot
        controller.b().whileTrue(climber.lowerCommand());
    }

}

