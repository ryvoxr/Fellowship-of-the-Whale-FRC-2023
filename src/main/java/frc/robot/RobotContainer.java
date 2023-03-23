package frc.robot;

import frc.robot.subsystems.*;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;


public class RobotContainer {
  // Controllers
  public static Joystick driveJoystick = new Joystick(0);
  public static XboxController systemController = new XboxController(1);

  // subsystems
  private final Drivetrain drive = new Drivetrain();
  public static Elevator elevator = new Elevator();
  public static Arm arm = new Arm();
  public static Claw claw = new Claw();

  // Control commands
  private final DriveWithJoystick driving = new DriveWithJoystick(drive);
  private final ElevatorWithController elevatoring = new ElevatorWithController(elevator);
  private final ArmWithController arming = new ArmWithController(arm);
  private final ClawWithController clawing = new ClawWithController(claw);

  public RobotContainer() {
    configureBindings();
    resetEncoders();

    // Drive init
    driving.addRequirements(drive);
    drive.setDefaultCommand(driving);

    // Elevator init
    elevatoring.addRequirements(elevator);
    elevator.setDefaultCommand(elevatoring);
    elevator.enable();
    elevator.setSetpoint(0);

    // Arm init
    arming.addRequirements(arm);
    arm.setDefaultCommand(arming);
    arm.enable();
    arm.setSetpoint(0);

    // Claw init
    clawing.addRequirements(claw);
    claw.setDefaultCommand(clawing);
  }

  private void configureBindings() {}

  public void resetEncoders() {
    drive.resetEncoders();
    elevator.resetEncoders();
    arm.resetEncoders();
  }
}
