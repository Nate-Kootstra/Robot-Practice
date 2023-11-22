// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.Autos;
import frc.robot.commands.DisableAllWheelsCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.RollForTimeCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {

  private final DriveSubsystem driveSubsystem = new DriveSubsystem();
  private final ArmSubsystem armSubsystem = new ArmSubsystem();

  private final CommandXboxController driverController =
    new CommandXboxController(Constants.Controllers.IDs.driver);
  private final CommandXboxController operatorController =
    new CommandXboxController(Constants.Controllers.IDs.operator);


  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    // Disables all wheels if one wheel is not working.
    new Trigger(driveSubsystem::isWheelBroken).onTrue(
      new DisableAllWheelsCommand(driveSubsystem));

    // Drive the robot based on the driverController inputs.
    new Trigger(() -> true).whileTrue(
      new DriveCommand(driverController.getLeftY(), driverController.getRightX(), driveSubsystem));
    
    // Roll the roller when the "b" button on the operator controller is pressed.
    operatorController.b().onTrue(
      new RollForTimeCommand(5, 0.4, armSubsystem));

  }

  public Command getAutonomousCommand() {
    return Autos.mainAuto(driveSubsystem, armSubsystem);
  }
}
