// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {
  
  private WPI_TalonSRX backLeftWheel = new WPI_TalonSRX(Constants.Motors.IDs.BACKLEFT);
  private WPI_TalonSRX backRightWheel = new WPI_TalonSRX(Constants.Motors.IDs.BACKRIGHT);
  private WPI_TalonSRX frontLeftWheel = new WPI_TalonSRX(Constants.Motors.IDs.FRONTLEFT);
  private WPI_TalonSRX frontRightWheel = new WPI_TalonSRX(Constants.Motors.IDs.FRONTRIGHT);
  private MotorControllerGroup leftWheels = new MotorControllerGroup(frontLeftWheel, backLeftWheel);
  private MotorControllerGroup rightWheels = new MotorControllerGroup(frontRightWheel, backRightWheel);
  private DifferentialDrive wheels = new DifferentialDrive(leftWheels, rightWheels);

  private Gyro gyro = new ADXRS450_Gyro();


  
  public DriveSubsystem() {

    setInverted(false, false, false, false);
    setBrake(true);
    setSafety(false);
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


  public void resetGyro(){
    gyro.reset();
  }

  public double getGyro(){
    return gyro.getAngle();
  }

  public void arcadeDrive(double speed, double rotation){
    wheels.arcadeDrive(speed, rotation);
  }

}
