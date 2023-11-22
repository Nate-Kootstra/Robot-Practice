// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;

public class TestAuto1 extends SequentialCommandGroup {


  public TestAuto1(DriveSubsystem driveSubsystem, ArmSubsystem armSubsystem) {
    addCommands(
        new DriveForDistanceCommand(Constants.Units.Metric.CENTIMETER, 50, 0.4, driveSubsystem),
        new TurnForDegreesCommand(180, 0.4, driveSubsystem),
        new DriveForDistanceCommand(Constants.Units.Metric.CENTIMETER, 50, 0.4, driveSubsystem),
        new RollForTimeCommand(5, 0.4, armSubsystem)
    );
  }
}
