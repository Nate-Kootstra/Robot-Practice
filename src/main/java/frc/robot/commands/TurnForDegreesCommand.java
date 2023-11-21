// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;

public class TurnForDegreesCommand extends CommandBase {

  private DriveSubsystem driveSubsystem;

  private double targetDistance;
  private double remainingDistance;
  private double speed;
  private double direction;
  
  private double precision = 2000;

  //Turns a certain amount of degrees.
  public TurnForDegreesCommand(double degrees, double speed, DriveSubsystem driveSubsystem) {

    this.driveSubsystem = driveSubsystem;

    if(speed < 0)
      throw new IllegalArgumentException("Speed cannot be negative. If you wish to turn the other way, degrees should be negative.");
    this.speed = speed;
    targetDistance = Constants.Units.degreesToEncoder(degrees) + driveSubsystem.getWheelEncoder(Constants.Motors.Wheels.IDs.FRONTLEFT).getAsDouble();

    addRequirements(driveSubsystem);

  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {

    remainingDistance = targetDistance - driveSubsystem.getWheelEncoder(Constants.Motors.Wheels.IDs.FRONTLEFT).getAsDouble();
    direction = (remainingDistance > 0 ? 1 : -1);

    driveSubsystem.arcadeDrive(0, Math.min(speed, 1) * direction);

  }

  @Override
  public void end(boolean interrupted) {
    driveSubsystem.arcadeDrive(0, 0);
  }

  @Override
  public boolean isFinished() {
    return (Math.abs(remainingDistance) < precision);
  }
}
