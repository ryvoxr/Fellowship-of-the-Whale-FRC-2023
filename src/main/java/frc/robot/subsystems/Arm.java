package frc.robot.subsystems;
import frc.robot.RobotContainer;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.REVLibError;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants;

public class Arm extends PIDSubsystem {

  private CANSparkMax left = new CANSparkMax(7, MotorType.kBrushless);
  private CANSparkMax right = new CANSparkMax(8, MotorType.kBrushless);

  private RelativeEncoder leftEncoder = left.getEncoder();
  private RelativeEncoder rightEncoder = right.getEncoder();

  // public final Command up = Commands.repeatingSequence(Commands.run(() -> addToSetpoint(Constants.armSetpointStep), this));
  // public final Command down = Commands.repeatingSequence(Commands.run(() -> addToSetpoint(-Constants.armSetpointStep), this));

  public final Command up = Commands.run(() -> addToSetpoint(Constants.armSetpointStep));
  public final Command down = Commands.run(() -> addToSetpoint(-Constants.armSetpointStep));
  public final Command top = Commands.runOnce(() -> setSetpoint(Constants.armMaxRot), this);
  
  public final ArmFeedforward ff = new ArmFeedforward(0.1, 0.1, 0.2);

  public Arm() {
    super(new PIDController(0.15, 0.1, 0));
    setSetpoint(0);
  }

  @Override
  public void useOutput(double output, double setpoint) {
    double speed = output;

    SmartDashboard.putNumber("arm rad", getRad());
    SmartDashboard.putNumber("arm rot", leftEncoder.getPosition());

    SmartDashboard.putNumber("armEncoder", (-leftEncoder.getPosition() + rightEncoder.getPosition()) / 2);
    SmartDashboard.putNumber("armSpeed", speed);

    double ffv = ff.calculate(rotToAngle(setpoint), 0.5);
    SmartDashboard.putNumber("ff", ffv);

    left.set(speed);
    right.set(-speed);
  }

  @Override
  public double getMeasurement() {
    return leftEncoder.getPosition();
  }

  public void addToSetpoint(double addend) {
    double newSetpoint = getSetpoint() + addend;
    if (isSafe(addend > 0)) {
      setSetpoint(newSetpoint);
    }
  }
  
  public void stop() {
    left.stopMotor();
    right.stopMotor();
  }

  public double getRad() {
    return (leftEncoder.getPosition() * Constants.armRotToRadK) + Constants.armRadA;
  }

  public double angleToRot(double angle) {
    return (angle - Constants.armRadA) * (1/Constants.armRotToRadK);
  }

  public double rotToAngle(double rot) {
    return (rot * Constants.armRotToRadK) + Constants.armRadA;
  }

  public void resetEncoders() {
    leftEncoder.setPosition(0);
    rightEncoder.setPosition(0);
  }

  private boolean isSafe(boolean goingUp) {
    double angle = getRad();
    double armHeight = Constants.armLength * Math.sin(angle);
    double armWidth = Constants.armLength * Math.cos(angle);
    boolean safe = true;

    if ((angle > Math.PI/2) && goingUp) {
      safe = false;
    } else if (-armHeight > (RobotContainer.elevator.getHeight() + Constants.elevatorSafetyA) && !goingUp) {
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

  public void armWithController() {
    XboxController controller = RobotContainer.systemController;
    double pos = -controller.getLeftY()/5;
    if (Math.abs(pos) < 0.02) {
      pos = 0;
    }
    addToSetpoint(pos);
  }
}
