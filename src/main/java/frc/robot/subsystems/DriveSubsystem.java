// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {
  
  private WPI_TalonSRX backLeftWheel = new WPI_TalonSRX(Constants.Motors.Wheels.IDs.BACKLEFT);
  private WPI_TalonSRX backRightWheel = new WPI_TalonSRX(Constants.Motors.Wheels.IDs.BACKRIGHT);
  private WPI_TalonSRX frontLeftWheel = new WPI_TalonSRX(Constants.Motors.Wheels.IDs.FRONTLEFT);
  private WPI_TalonSRX frontRightWheel = new WPI_TalonSRX(Constants.Motors.Wheels.IDs.FRONTRIGHT);
  private MotorControllerGroup leftWheels = new MotorControllerGroup(frontLeftWheel, backLeftWheel);
  private MotorControllerGroup rightWheels = new MotorControllerGroup(frontRightWheel, backRightWheel);
  private DifferentialDrive wheels = new DifferentialDrive(leftWheels, rightWheels);

  private Gyro gyro = new ADXRS450_Gyro();



  public DriveSubsystem() {

    setInverted(false, false, false, false);
    setBrake(true);
    setSafety(false);

    setShuffleboardOutput(true, true, false, false, true, false);

  }



  public void setSafety(boolean safe){
    wheels.setSafetyEnabled(safe);
  }

  public void setBrake(boolean brake){
    backLeftWheel.setNeutralMode(Constants.Motors.NEUTRALMODES[brake ? 1 : 0]);
    backRightWheel.setNeutralMode(Constants.Motors.NEUTRALMODES[brake ? 1 : 0]);
    frontLeftWheel.setNeutralMode(Constants.Motors.NEUTRALMODES[brake ? 1 : 0]);
    frontRightWheel.setNeutralMode(Constants.Motors.NEUTRALMODES[brake ? 1 : 0]);
  }

  public void setInverted(boolean backLeft, boolean backRight, boolean frontLeft, boolean frontRight){
    backLeftWheel.setInverted(backLeft);
    backRightWheel.setInverted(backRight);
    frontLeftWheel.setInverted(frontLeft);
    frontRightWheel.setInverted(frontRight);
  }

  public void setShuffleboardOutput(boolean gyro, boolean safetyEnabled, boolean backLeftWheel, boolean backRightWheel, boolean frontLeftWheel, boolean frontRightWheel){
    if(gyro)
      Shuffleboard.getTab(Constants.Shuffleboard.MAINTAB).addDouble("Gyro", getGyro());
    if(safetyEnabled)
      Shuffleboard.getTab(Constants.Shuffleboard.MAINTAB).addBoolean("Safety Enabled", getSafetyEnabled());
    if(backLeftWheel)
      Shuffleboard.getTab(Constants.Shuffleboard.MAINTAB).addDouble("Back Left Wheel", getWheelEncoder(Constants.Motors.Wheels.IDs.BACKLEFT));
    if(backRightWheel)
      Shuffleboard.getTab(Constants.Shuffleboard.MAINTAB).addDouble("Back Right Wheel", getWheelEncoder(Constants.Motors.Wheels.IDs.BACKRIGHT));
    if(frontLeftWheel)
      Shuffleboard.getTab(Constants.Shuffleboard.MAINTAB).addDouble("Front Left Wheel", getWheelEncoder(Constants.Motors.Wheels.IDs.FRONTLEFT));
    if(frontRightWheel)
      Shuffleboard.getTab(Constants.Shuffleboard.MAINTAB).addDouble("Front Right Wheel", getWheelEncoder(Constants.Motors.Wheels.IDs.FRONTRIGHT));
  }

  public void resetGyro(){
    gyro.reset();
  }

  public DoubleSupplier getGyro(){
    return () -> gyro.getAngle();
  }

  public DoubleSupplier getWheelEncoder(int wheelID){
    return () -> Constants.Motors.Wheels.WHEELS[wheelID].getSelectedSensorPosition();
  }

  public BooleanSupplier getSafetyEnabled(){
    return () -> (backLeftWheel.isSafetyEnabled());
  }

  public boolean isWheelBroken(){
    return !(backLeftWheel.isAlive() && backRightWheel.isAlive() && frontLeftWheel.isAlive() && frontRightWheel.isAlive());
  }

  public void disableAllWheels(){
    backLeftWheel.disable();
    backRightWheel.disable();
    frontLeftWheel.disable();
    frontRightWheel.disable();
  }

  public void arcadeDrive(double speed, double rotation){
    wheels.arcadeDrive(speed, rotation);
  }

}