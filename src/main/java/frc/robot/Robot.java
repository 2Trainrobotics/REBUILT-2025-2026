// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

// package edu.wpi.first.wpilibj.examples.gettingstarted;
// our import are below here


import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the manifest file in the resource directory.
 */
// here we're going to Defining the variables for our sample robot

public class Robot extends TimedRobot {
  /*
   * What is on the robot:
   * 2x Rev Robotics SPARK Flex Motor Controller + NEO Vortex Brushless Motor:  shooter
   *   Probably CAN, probably not PWM
   * 4x Rev Robotics ?:  swerve drive
   * 2x Rev Robotics NEO REV-21-1650 motors:  ?
   */
  // Old RoboRIO Hostname:  "NI-roboRIO2-0-03249427"
  private final SparkFlex leftShooterMotor = new SparkFlex(9, MotorType.kBrushless);
  private final SparkFlex rightShooterMotor = new SparkFlex(10, MotorType.kBrushless);
  private final XboxController m_controller = new XboxController(1);
  private final Timer m_timer = new Timer();

//Robot Initialization is below

  /** Called once at the beginning of the robot program. */
  public Robot() {
    leftShooterMotor.setInverted(true);
    rightShooterMotor.setInverted(false);
  }
// here is the first Simple Autonomous Example

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_timer.restart();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    // if (m_timer.get() < 2.0) {
    //   // Drive forwards half speed, make sure to turn input squaring off
    //   m_robotDrive.arcadeDrive(0.5, 0.0, false);
    // } else {
    //   m_robotDrive.stopMotor(); // stop robot
    // }
  }

  // here we're gonna declare the Joystick Control for Teleoperation

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    double shooterPower = m_controller.getLeftY();
    leftShooterMotor.set(shooterPower);
    rightShooterMotor.set(shooterPower);
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}