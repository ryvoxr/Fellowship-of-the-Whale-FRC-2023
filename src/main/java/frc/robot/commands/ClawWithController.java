package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Claw;

public class ClawWithController extends CommandBase {
private final Claw claw;

  public ClawWithController(Claw c){
    claw = c;
    addRequirements(claw);
    
  }
  @Override
  public void initialize() {}

  @Override
  public void execute() {
    claw.clawWithController();
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished(){
    return false;
  }
}