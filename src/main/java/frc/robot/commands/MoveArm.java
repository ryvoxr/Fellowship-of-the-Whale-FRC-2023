package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.*;

public class MoveArm extends CommandBase {
  private final Elevator elevator;
  private final Arm arm;

  public MoveArm(Elevator e, Arm a) {
    elevator = e;
    arm = a;
    addRequirements(elevator, arm);
    if (isSafe()) {
      elevator.addToSetpoint(Constants.armSetpointStep);
    }
  }

  public boolean isSafe() {
    double angle = arm.getRad();
    double armHeight = Constants.armLength * Math.cos(angle);
    double armWidth = Constants.armLength * Math.sin(angle);
    if (armWidth <= Constants.baseWidth && angle < 0) {
      return false;
    } else if (angle > Math.PI/2) {
      return false;
    }

    return true;
  }
}