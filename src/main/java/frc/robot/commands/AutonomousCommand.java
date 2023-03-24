package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.*;

public class AutonomousCommand extends CommandBase{
    private static Drivetrain drive;
    private static Elevator elevator;
    private static Arm arm;
    private static Claw claw;

    public AutonomousCommand(Drivetrain d, Elevator e, Arm a, Claw c) {
        drive = d;
        elevator = e;
        arm = a;
        claw = c;

        addRequirements(drive, elevator, arm, claw);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if (arm.getSetpoint() > -13.8) {
            arm.addToSetpoint(-0.1);
        }
        if (elevator.getSetpoint() > -35) {
            elevator.addToSetpoint(-0.5);
        }
        // Move arm and elevator to top cube posistion
        // Extake cube
        // Arm and elevator to rest position
        // Back up
        // Turn around
    }
}

