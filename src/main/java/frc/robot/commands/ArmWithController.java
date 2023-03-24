package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;

public class ArmWithController extends CommandBase {
  private final Arm arm;

  public ArmWithController(Arm a){
    arm = a;
    addRequirements(arm);
    
  }
  @Override
  public void initialize() {}

  @Override
  public void execute() {
    arm.armWithController();
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished(){
    return false;
  }
}