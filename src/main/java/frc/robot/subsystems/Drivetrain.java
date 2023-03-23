package frc.robot.subsystems;
import frc.robot.RobotContainer;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

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
    Joystick joystick = RobotContainer.driveJoystick;
    drive.arcadeDrive(Math.pow(-joystick.getY(), 3), Math.pow(-joystick.getX(), 3));
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
