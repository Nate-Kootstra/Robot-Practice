// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SPI.Port;



public final class Constants {
  public static final class Motors {
    public static final class Wheels {
      public static final class IDs {
        public static final int BACKLEFT = 0;
        public static final int BACKRIGHT = 0;
        public static final int FRONTLEFT = 0;
        public static final int FRONTRIGHT = 0;
      }

      public static final WPI_TalonSRX[] WHEELS = {new WPI_TalonSRX(Constants.Motors.Wheels.IDs.BACKLEFT), new WPI_TalonSRX(Constants.Motors.Wheels.IDs.BACKRIGHT), new WPI_TalonSRX(Constants.Motors.Wheels.IDs.FRONTLEFT), new WPI_TalonSRX(Constants.Motors.Wheels.IDs.FRONTRIGHT)};
    }

    public static final class Arm {
      public static final class IDs {
        public static final int BASE = 0;
        public static final int ROLLER = 0;
      }

      public static final WPI_TalonSRX[] PARTS = {new WPI_TalonSRX(Constants.Motors.Arm.IDs.BASE), new WPI_TalonSRX(Constants.Motors.Arm.IDs.ROLLER)};
    }

    public static final NeutralMode[] NEUTRALMODES = {NeutralMode.Coast, NeutralMode.Brake};

  }

  public static final class Shuffleboard {
    public static final String  MAINTAB = "Debug";
  }
}
