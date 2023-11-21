// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.DisableAllWheelsCommand;
import frc.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {

  private final DriveSubsystem driveSubsystem = new DriveSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  // private final CommandXboxController m_driverController =
  //     new CommandXboxController(OperatorConstants.kDriverControllerPort);


  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    // Disables all wheels if one wheel is not working.
    new Trigger(driveSubsystem::isWheelBroken)
        .onTrue(new DisableAllWheelsCommand(driveSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    // m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
  }

  // public Command getAutonomousCommand() {
  //   return Autos.exampleAuto(m_exampleSubsystem);
  // }
}
