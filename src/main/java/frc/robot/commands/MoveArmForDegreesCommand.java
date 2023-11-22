// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;

public class MoveArmForDegreesCommand extends CommandBase {

  private ArmSubsystem armSubsystem;

  private double targetDistance;
  private double remainingDistance;
  private double speed;
  private double direction;
  
  private double precision = 2000;

  //Turns a certain amount of degrees.
  public MoveArmForDegreesCommand(double degrees, double speed, ArmSubsystem armSubsystem) {

    this.armSubsystem = armSubsystem;

    if(Math.abs(speed) > 1)
      throw new IllegalArgumentException("Absolute speed cannot exceed 1.");
    this.speed = speed;
    targetDistance = Constants.Units.armDegreesToEncoder(degrees) + armSubsystem.getArmEncoder(Constants.Motors.Arm.IDs.BASE).getAsDouble();

    setShuffleboardOutput(false, true, false, true);
    addRequirements(armSubsystem);

  }

  public void setShuffleboardOutput(boolean targetDistance, boolean remainingDistance, boolean speed, boolean direction){
    if(targetDistance)
      Shuffleboard.getTab(Constants.Shuffleboard.MAINTAB).addDouble("Move Arm Command Target Distance", () -> this.targetDistance);
    if(remainingDistance)
      Shuffleboard.getTab(Constants.Shuffleboard.MAINTAB).addDouble("Move Arm Command Remaining Distance", getRemainingDistance());
    if(speed)
      Shuffleboard.getTab(Constants.Shuffleboard.MAINTAB).addDouble("Move Arm Command Speed", () -> this.speed);
    if(direction)
      Shuffleboard.getTab(Constants.Shuffleboard.MAINTAB).addDouble("Move Arm Command Direction", () -> this.direction);
  }

  public DoubleSupplier getRemainingDistance(){
    return () -> targetDistance - armSubsystem.getArmEncoder(Constants.Motors.Arm.IDs.BASE).getAsDouble();
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {

    remainingDistance = targetDistance - armSubsystem.getArmEncoder(Constants.Motors.Arm.IDs.BASE).getAsDouble();
    direction = (remainingDistance > 0 ? 1 : -1);

    armSubsystem.rotateBase(speed * direction);

  }

  @Override
  public void end(boolean interrupted) {
    armSubsystem.rotateBase(0);
  }

  @Override
  public boolean isFinished() {
    return (Math.abs(remainingDistance) < precision);
  }
}
