package frc.robot;



public final class Constants {
  // elevator
  public static final double elevatorSpeedK = 0.5;
  public static final float elevatorMaxRot = 36;
  public static final float elevatorMinRot = 0;
  public static final double elevatorKp = 0.1;
  public static final double elevatorKi = 0;
  public static final double elevatorKd = 0.01;
  public static final double elevatorSetpointStep = .7;

  // arm
  public static final float armMaxRot = 0;
  public static final double armSetpointStep = .2;
  public static final double armRotToRadK = Math.PI/37.3;
  public static final double armRadA = Math.PI/2;

  // CAN
  public static final int driveLeftMasterId = 1;
  public static final int driveLeftSlaveId = 2;
  public static final int driveRightMasterId = 3;
  public static final int driveRightSlaveId = 4;
  public static final int elevatorLeftId = 5;
  public static final int elevatorRightId = 6;
  public static final int armLeftId = 7;
  public static final int armRightId = 8;
  
  // measurements
  public static final double armLength = 28;
  public static final double baseWidth = 1.5;
  public static final double elevatorInitHeight = 13;
  public static final double elevatorSafetyA = -5;
}
