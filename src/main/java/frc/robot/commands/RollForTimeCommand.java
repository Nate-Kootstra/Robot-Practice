// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;

public class RollForTimeCommand extends CommandBase {

  private ArmSubsystem armSubsystem;

  private Timer timer;

  private double speed;
  private double seconds;

  //Turns a certain amount of degrees.
  public RollForTimeCommand(double seconds, double speed, ArmSubsystem armSubsystem) {

    this.armSubsystem = armSubsystem;

    if(Math.abs(speed) > 1)
      throw new IllegalArgumentException("Absolute speed cannot exceed 1.");
    this.speed = speed;
    this.seconds = seconds;
    

    setShuffleboardOutput(true, false, true);
    addRequirements(armSubsystem);

  }

  public void setShuffleboardOutput(boolean seconds, boolean speed, boolean timer){
    if(seconds)
      Shuffleboard.getTab(Constants.Shuffleboard.MAINTAB).addDouble("Roller Target Time", () -> this.seconds);
    if(speed)
      Shuffleboard.getTab(Constants.Shuffleboard.MAINTAB).addDouble("Roller Speed", () -> this.speed);
    if(timer)
      Shuffleboard.getTab(Constants.Shuffleboard.MAINTAB).addDouble("Roller Timer", () -> this.timer.get());
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {
    armSubsystem.rotateRoller(speed);
  }

  @Override
  public void end(boolean interrupted) {
    armSubsystem.rotateRoller(0);
    timer.stop();
  }

  @Override
  public boolean isFinished() {
    return (timer.get() > seconds);
  }
}
