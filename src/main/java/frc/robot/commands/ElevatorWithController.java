package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;

public class ElevatorWithController extends CommandBase {
private final Elevator elevator;

  public ElevatorWithController(Elevator e){
    elevator = e;
    addRequirements(elevator);
    
  }
  @Override
  public void initialize() {}

  @Override
  public void execute() {
    elevator.elevatorWithController();
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished(){
    return false;
  }
}