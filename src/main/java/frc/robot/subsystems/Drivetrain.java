package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class Drivetrain extends SubsystemBase {

  DifferentialDrive drive;

  private RelativeEncoder leftMasterEncoder;
  private RelativeEncoder leftSlaveEncoder;
  private RelativeEncoder rightMasterEncoder;
  private RelativeEncoder rightSlaveEncoder;

  double heading;

  public Drivetrain() {
    CANSparkMax leftMaster = new CANSparkMax(1, MotorType.kBrushless);
    CANSparkMax leftSlave = new CANSparkMax(2, MotorType.kBrushless);
    CANSparkMax rightMaster = new CANSparkMax(3, MotorType.kBrushless);
    CANSparkMax rightSlave = new CANSparkMax(4, MotorType.kBrushless);

    leftMasterEncoder = leftMaster.getEncoder();
    leftSlaveEncoder = leftSlave.getEncoder();
    rightMasterEncoder = rightMaster.getEncoder();
    rightSlaveEncoder = rightSlave.getEncoder();

    MotorControllerGroup leftMotors = new MotorControllerGroup(leftMaster, leftSlave);
    MotorControllerGroup rightMotors = new MotorControllerGroup(rightMaster, rightSlave);

    rightMotors.setInverted(true);

    drive = new DifferentialDrive(leftMotors, rightMotors);

    double speedK = 0.4;
  }

  @Override
  public void periodic() {
    // SmartDashboard.putNumber("leftMasterPosition", leftMasterEncoder.getPosition());
    // SmartDashboard.putNumber("rightMasterPosition", rightMasterEncoder.getPosition());
    // SmartDashboard.putNumber("leftSlavePosition", leftSlaveEncoder.getPosition());
    // SmartDashboard.putNumber("rightSlavePosition", rightSlaveEncoder.getPosition());
  }

  public void driveWithJoystick() {
    XboxController joystick = RobotContainer.driveController;
    drive.arcadeDrive((-joystick.getLeftY()/4), (-joystick.getLeftX()/4));
  }

  public void driveForward(int speed) {
    drive.tankDrive(speed, speed);
  }

  public void stop() {
    drive.stopMotor();
  }

  public void resetEncoders() {
    leftMasterEncoder.setPosition(0);
    leftSlaveEncoder.setPosition(0);
    rightMasterEncoder.setPosition(0);
    rightSlaveEncoder.setPosition(0);
  }
}
