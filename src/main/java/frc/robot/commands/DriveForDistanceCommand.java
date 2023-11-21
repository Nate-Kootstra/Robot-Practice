// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;

public class DriveForDistanceCommand extends CommandBase {

  private DriveSubsystem driveSubsystem;

  private double targetDistance;
  private double remainingDistance;
  private double speed;
  private double direction;
  
  private double precision = 2000;

  public DriveForDistanceCommand(double unit, double distance, double speed, DriveSubsystem driveSubsystem) {

    this.driveSubsystem = driveSubsystem;

    this.speed = speed;
    targetDistance = unit * distance * Constants.Units.InternalMultipliers.ENCODERMULTIPLER;

    addRequirements(driveSubsystem);

  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {

    remainingDistance = targetDistance - driveSubsystem.getWheelEncoder(Constants.Motors.Wheels.IDs.FRONTLEFT).getAsDouble();
    direction = (remainingDistance > 0 ? 1 : -1);

    driveSubsystem.arcadeDrive(Math.min(speed, 1) * direction, 0);

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
