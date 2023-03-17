package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
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

  public final Command up = Commands.repeatingSequence(Commands.run(() -> addToSetpoint(Constants.armSetpointStep), this));
  public final Command down = Commands.repeatingSequence(Commands.run(() -> addToSetpoint(-Constants.armSetpointStep), this));
  public final Command top = Commands.runOnce(() -> setSetpoint(Constants.armMaxRot), this);
  //public final Command bottom = Commands.runOnce(() -> setSetpoint(Constants.armMinRot), this);

  public final ArmFeedforward ff = new ArmFeedforward(0.1, 0.3, 0.2);

  public Arm() {
    super(new PIDController(0.1, 0, 0.0005));
    setSetpoint(0);
  }

  @Override
  public void useOutput(double output, double setpoint) {
    double speed = output;

    if (output > 0) {
      speed *= 2;
    }

    SmartDashboard.putNumber("arm rad", getRad());
    SmartDashboard.putNumber("arm rot", leftEncoder.getPosition());

    SmartDashboard.putNumber("armEncoder", (-leftEncoder.getPosition() + rightEncoder.getPosition()) / 2);
    SmartDashboard.putNumber("armSpeed", speed);

    left.set(speed);
    right.set(-speed);

    double ffv = ff.calculate(0, 0);
    SmartDashboard.putNumber("ff", ffv);
  }

  @Override
  public double getMeasurement() {
    return leftEncoder.getPosition();
  }

  public void addToSetpoint(double addend) {
    double newSetpoint = getSetpoint() + addend;
    setSetpoint(newSetpoint);
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

  public void resetEncoders() {
    leftEncoder.setPosition(0);
    rightEncoder.setPosition(0);
  }
}
