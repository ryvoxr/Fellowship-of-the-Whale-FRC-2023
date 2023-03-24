package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.AutonomousCommand;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends TimedRobot {
  private Command autonomousCommand;

  private RobotContainer robotContainer;
  private Timer timer = new Timer();

  @Override
  public void robotInit() {
    robotContainer = new RobotContainer();
    CameraServer.startAutomaticCapture();
    timer.reset();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {
    autonomousCommand = new AutonomousCommand(RobotContainer.drive, RobotContainer.elevator, RobotContainer.arm, RobotContainer.claw);
    autonomousCommand.schedule();
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
