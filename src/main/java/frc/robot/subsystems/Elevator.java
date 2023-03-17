package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants;

public class Elevator extends PIDSubsystem {

  private CANSparkMax left = new CANSparkMax(Constants.elevatorLeftId, MotorType.kBrushless);
  private CANSparkMax right = new CANSparkMax(Constants.elevatorRightId, MotorType.kBrushless);

  private RelativeEncoder leftEncoder = left.getEncoder();
  private RelativeEncoder rightEncoder = right.getEncoder();

  public final Command up = Commands.repeatingSequence(Commands.run(() -> addToSetpoint(Constants.elevatorSetpointStep), this));
  public final Command down = Commands.repeatingSequence(Commands.run(() -> addToSetpoint(-Constants.elevatorSetpointStep), this));
  public final Command bottom = Commands.runOnce(() -> setSetpoint(Constants.elevatorMinRot), this);
  public final Command top = Commands.runOnce(() -> setSetpoint(Constants.elevatorMaxRot), this);


  public Elevator() {
    super(new PIDController(Constants.elevatorKp, Constants.elevatorKi, Constants.elevatorKd));
    setSetpoint(0);

    left.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
    left.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
    right.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
    right.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);

    left.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, Constants.elevatorMinRot);
    left.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, -Constants.elevatorMaxRot);
    right.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, Constants.elevatorMaxRot);
    right.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, Constants.elevatorMinRot);
  }

  @Override
  public void useOutput(double output, double setpoint) {
    double speed = output*Constants.elevatorSpeedK;

    SmartDashboard.putNumber("elevSetpoint", setpoint);
    SmartDashboard.putNumber("elevEncoder", (-leftEncoder.getPosition() + rightEncoder.getPosition()) / 2);
    SmartDashboard.putNumber("elevSpeed", speed);
    SmartDashboard.putNumber("elevOutput", output);

    left.set(-speed);
    right.set(speed);
  }

  @Override
  public double getMeasurement() {
    return (-leftEncoder.getPosition() + rightEncoder.getPosition()) / 2;
  }

  public void addToSetpoint(double addend) {
    double newSetpoint = getSetpoint() + addend;
    if (newSetpoint < Constants.elevatorMaxRot && newSetpoint > Constants.elevatorMinRot) {
      setSetpoint(newSetpoint);
    }
  }

  public double getHeight() {
    double height = (-leftEncoder.getPosition() * (2/3)) + Constants.elevatorInitHeight;
    return height;
  }
  
  public void stop() {
    left.stopMotor();
    right.stopMotor();
  }

  public void resetEncoders() {
    leftEncoder.setPosition(0);
    rightEncoder.setPosition(0);
  }
}
