// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;

public class DriveForDistanceCommand extends CommandBase {

  private DriveSubsystem driveSubsystem;

  private double targetDistance;
  private double speed;
  private double direction;
  
  private double precision = 2000;

  //Drives for a certain amount of a certain unit. Does not drive TO a position, instead driving that much further from the current one.
  public DriveForDistanceCommand(double unit, double distance, double speed, DriveSubsystem driveSubsystem) {

    this.driveSubsystem = driveSubsystem;

    if(Math.abs(speed) > 1)
      throw new IllegalArgumentException("Absolute speed cannot exceed 1.");
    if(speed < 0)
    throw new IllegalArgumentException("Speed cannot be negative. If you wish to drive backwards, distance should be negative.");
    this.speed = speed;
    targetDistance = unit * distance * Constants.Units.InternalMultipliers.ENCODERMULTIPLER + driveSubsystem.getWheelEncoder(Constants.Motors.Wheels.IDs.FRONTLEFT).getAsDouble();

    setShuffleboardOutput(false, true, false, true);
    addRequirements(driveSubsystem);

  }

  public void setShuffleboardOutput(boolean targetDistance, boolean remainingDistance, boolean speed, boolean direction){
    if(targetDistance)
      Shuffleboard.getTab(Constants.Shuffleboard.MAINTAB).addDouble("Drive Command Target Distance", () -> this.targetDistance);
    if(remainingDistance)
      Shuffleboard.getTab(Constants.Shuffleboard.MAINTAB).addDouble("Drive Command Remaining Distance", getRemainingDistance());
    if(speed)
      Shuffleboard.getTab(Constants.Shuffleboard.MAINTAB).addDouble("Drive Command Speed", () -> this.speed);
    if(direction)
      Shuffleboard.getTab(Constants.Shuffleboard.MAINTAB).addDouble("Drive Command Direction", () -> this.direction);
  }

  public DoubleSupplier getRemainingDistance(){
    return () -> targetDistance - driveSubsystem.getWheelEncoder(Constants.Motors.Wheels.IDs.FRONTLEFT).getAsDouble();
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    direction = (getRemainingDistance().getAsDouble() > 0 ? 1 : -1);

    driveSubsystem.arcadeDrive(Math.min(speed, 1) * direction, 0);

  }

  @Override
  public void end(boolean interrupted) {
    driveSubsystem.arcadeDrive(0, 0);
  }

  @Override
  public boolean isFinished() {
    return (Math.abs(getRemainingDistance().getAsDouble()) < precision);
  }
}
