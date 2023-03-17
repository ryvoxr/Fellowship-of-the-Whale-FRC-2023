package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.*;

public class MoveElevator extends CommandBase {
  private final Elevator elevator;
  private final Arm arm;

  public MoveElevator(Elevator e, Arm a) {
    elevator = e;
    arm = a;
    addRequirements(elevator, arm);
    if (isSafe()) {
      elevator.addToSetpoint(Constants.elevatorSetpointStep);
    }
  }

  public boolean isSafe() {
    double angle = arm.getRad();
    double armHeight = Constants.armLength * Math.cos(angle);
    if ((elevator.getHeight() <= armHeight) && angle < 0) {
      return false;
    }

    
    return true;
  }
}