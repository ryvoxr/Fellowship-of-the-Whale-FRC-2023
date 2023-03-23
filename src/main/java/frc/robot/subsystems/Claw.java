package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Claw extends SubsystemBase {
  CANSparkMax left = new CANSparkMax(9, MotorType.kBrushless);
  CANSparkMax right = new CANSparkMax(10, MotorType.kBrushless);

  private final double inspeed = 0.1;
  private final double exspeed = 0.2;

  private void setSpeed(double speed) {
    left.set(speed);
    right.set(-speed);
  }
  
  public void clawWithController() {
    XboxController controller = RobotContainer.systemController;
    double inspeed = controller.getRightTriggerAxis()/2;
    double outspeed = -controller.getLeftTriggerAxis()/2;
    SmartDashboard.putNumber("inspeed", inspeed);
    SmartDashboard.putNumber("outpseed", outspeed);
    if (inspeed > 0) {
      setSpeed(inspeed);
    } else if (outspeed < 0) {
      setSpeed(outspeed);
    } else {
      setSpeed(0);
    }
  }
}
