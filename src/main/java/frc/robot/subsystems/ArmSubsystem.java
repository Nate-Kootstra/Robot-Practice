// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
  
  private WPI_TalonSRX base = new WPI_TalonSRX(Constants.Motors.Arm.IDs.BASE);
  private WPI_TalonSRX roller = new WPI_TalonSRX(Constants.Motors.Arm.IDs.ROLLER);



  public ArmSubsystem() {

    setInverted(false, false);
    setBrake(true);
    setSafety(false);

    setShuffleboardOutput(true, true, true);

  }



  public void setSafety(boolean safe){
    base.setSafetyEnabled(safe);
    roller.setSafetyEnabled(safe);
  }

  public void setBrake(boolean brake){
    base.setNeutralMode(Constants.Motors.NEUTRALMODES[brake ? 1 : 0]);
    roller.setNeutralMode(Constants.Motors.NEUTRALMODES[brake ? 1 : 0]);
  }

  public void setInverted(boolean base, boolean roller){
    this.base.setInverted(base);
    this.roller.setInverted(roller);
  }

  public void setShuffleboardOutput(boolean safetyEnabled, boolean base, boolean roller){
    if(safetyEnabled)
      Shuffleboard.getTab(Constants.Shuffleboard.MAINTAB).addBoolean("Arm Safety Enabled", getSafetyEnabled());
    if(base)
      Shuffleboard.getTab(Constants.Shuffleboard.MAINTAB).addDouble("Arm Base", getArmEncoder(Constants.Motors.Arm.IDs.BASE));
    if(roller)
      Shuffleboard.getTab(Constants.Shuffleboard.MAINTAB).addDouble("Arm Roller", getArmEncoder(Constants.Motors.Arm.IDs.ROLLER));
  }

  public DoubleSupplier getArmEncoder(int armID){
    return () -> Constants.Motors.Arm.PARTS[armID].getSelectedSensorPosition();
  }

  public BooleanSupplier getSafetyEnabled(){
    return () -> (base.isSafetyEnabled());
  }

  public void rotateBase(double speed){
    base.set(speed);
  }

  public void rotateRoller(double speed){
    roller.set(speed);
  }

}