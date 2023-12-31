// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DisableAllWheelsCommand extends CommandBase {

  private DriveSubsystem driveSubsystem;
  

  //Disables all wheels on the robot. Made as an emergency response for if a wheel fails.
  public DisableAllWheelsCommand(DriveSubsystem subsystem) {
    driveSubsystem = subsystem;

    addRequirements(subsystem);
  }



  @Override
  public void initialize() {
    driveSubsystem.disableAllWheels();
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return true;
  }
}
