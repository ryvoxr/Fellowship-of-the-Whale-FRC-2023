package frc.robot;

import frc.robot.subsystems.*;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class RobotContainer {
  public static Joystick joystick = new Joystick(0);
  public static XboxController driveController = new XboxController(1);

  private final Trigger buttonOne = new JoystickButton(joystick, 1);
  private final Trigger buttonTwo = new JoystickButton(joystick, 2);
  private final Trigger buttonThree = new JoystickButton(joystick, 3);
  private final Trigger buttonFour = new JoystickButton(joystick, 4);
  private final Trigger buttonFive = new JoystickButton(joystick, 5);
  private final Trigger buttonSix = new JoystickButton(joystick, 6);

  // subsystems
  private final Drivetrain drive = new Drivetrain();
  public final Elevator elevator = new Elevator();
  public final Arm arm = new Arm();
  private final DoubleSolenoid solenoid = new DoubleSolenoid(10, PneumaticsModuleType.REVPH, 0, 1);

  private final DriveWithJoystick driving = new DriveWithJoystick(drive);
  private final Command toggleSolenoid = Commands.runOnce(() -> solenoid.toggle());
  private final Command elevatorUp = Commands.run(() -> {
    if (elevatorSafe(true)) {
      elevator.addToSetpoint(Constants.elevatorSetpointStep);
    }
  });
  private final Command elevatorDown = Commands.run(() -> {
    if (elevatorSafe(false)) {
      elevator.addToSetpoint(-Constants.elevatorSetpointStep);
    }
  });
  private final Command armUp = Commands.run(() -> {
    if (armSafe(true)) {
      arm.addToSetpoint(Constants.armSetpointStep);
    }
  });
  private final Command armDown = Commands.run(() -> {
    if (armSafe(false)) {
      arm.addToSetpoint(-Constants.armSetpointStep);
    }
  });


  public RobotContainer() {
    configureBindings();
    resetEncoders();

    driving.addRequirements(drive);
    drive.setDefaultCommand(driving);
    elevator.enable();
    arm.enable();
    solenoid.set(Value.kReverse);
  }

  private void configureBindings() {
    elevator.setSetpoint(0);
    arm.setSetpoint(0);
    buttonOne.whileTrue(elevatorDown);
    buttonTwo.whileTrue(elevatorUp);
    buttonThree.whileTrue(armUp);
    buttonFour.whileTrue(armDown);
    buttonFive.onTrue(toggleSolenoid);
    buttonSix.onTrue(arm.top.alongWith(elevator.bottom));
  }

  private boolean elevatorSafe(boolean goingUp) {
    double angle = arm.getRad();
    double armHeight = Constants.armLength * Math.sin(angle);
    boolean safe = true;
    if (((elevator.height + Constants.elevatorSafetyA) <= -armHeight) && angle < 0 && !goingUp) {
      safe = false;
    }
    
    if (safe) {
      SmartDashboard.putString("elevator safety", "yes");
    } else {
      SmartDashboard.putString("elevator safety", "no");
    }

    return safe;
  }

  private boolean armSafe(boolean goingUp) {
    double angle = arm.getRad();
    double armHeight = Constants.armLength * Math.sin(angle);
    double armWidth = Constants.armLength * Math.cos(angle);
    boolean safe = true;

    if ((angle > Math.PI/2) && goingUp) {
      safe = false;
    } else if (-armHeight > (elevator.height + Constants.elevatorSafetyA) && !goingUp) {
      safe = false;
    }

    SmartDashboard.putNumber("armWidth", armWidth);
    SmartDashboard.putNumber("armHeight", armHeight);
    if (safe) {
      SmartDashboard.putString("arm safety", "yes");
    } else {
      SmartDashboard.putString("arm safety", "no");
    }

    return safe;
  }

  public void resetEncoders() {
    drive.resetEncoders();
    elevator.resetEncoders();
    arm.resetEncoders();
  }
}
