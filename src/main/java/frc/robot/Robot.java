// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.DriveSubsystem;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the manifest file in the resource directory.
 */
// here we're going to Defining the variables for our sample robot

public class Robot extends TimedRobot {
  // Old RoboRIO Hostname:  "NI-roboRIO2-0-03249427"
  /*
   * What is on the robot:
   * Drive: 4x Swerve Modules, each with 2x Rev Robotics SPARK MAX Motor Controllers + NEO Vortex Brushless Motor + NEO 550 Motor
   * Shooter:  2x Rev Robotics SPARK Flex Motor Controller + NEO Vortex Brushless Motor
   * Feeder/Pulley:  2x Rev Robotics NEO REV-21-1650 motors
   * ?Intake: 2x Rev Robotics NEO REV-21-1650 motors for lifting, 1x Rev Robotics NEO REV-21-1650 motor for spinning
   * Controller: 1x Xbox Controller
   */
  private final DriveSubsystem robotDrive = new DriveSubsystem();

  private final SparkFlex leftShooterMotor = new SparkFlex(Constants.DriveConstants.kLeftShooterCanId, MotorType.kBrushless);
  private final SparkFlex rightShooterMotor = new SparkFlex(Constants.DriveConstants.kRightShooterCanId, MotorType.kBrushless);

  // private final SparkMax leftPulleyMotor = new SparkMax(Constants.DriveConstants.kLeftPulleyCanId, MotorType.kBrushless);
  // private final SparkMax rightPulleyMotor = new SparkMax(Constants.DriveConstants.kRightPulleyCanId, MotorType.kBrushless);

  // private final SparkFlex leftIntakeLifterMotor = new SparkFlex(Constants.DriveConstants.kLeftIntakeLifterCanId, MotorType.kBrushless);
  // private final SparkFlex rightIntakeLifterMotor = new SparkFlex(Constants.DriveConstants.kRightIntakeLifterCanId, MotorType.kBrushless);
  // private final SparkFlex intakeSpinnerMotor = new SparkFlex(Constants.DriveConstants.kIntakeSpinnerCanId, MotorType.kBrushless);

  private final XboxController driverController = new XboxController(OIConstants.kDriverControllerPort);
  private final Timer timer = new Timer();

  /** Called once at the beginning of the robot program. */
  public Robot() {
    leftShooterMotor.setInverted(true);
    rightShooterMotor.setInverted(false);
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    timer.restart();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    // if (timer.get() < 2.0) {
    //   // Drive forwards half speed, make sure to turn input squaring off
    //   robotDrive.drive(0.5, 0.0, 0.0, false);
    // } else {
    //   robotDrive.drive(0.0, 0.0, 0.0, false);
    //   robotDrive.stopMotor(); // stop robot
    // }
  }

  // here we're gonna declare the Joystick Control for Teleoperation

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    // double shooterPower = driverController.getLeftY();
    // leftShooterMotor.set(shooterPower);
    // rightShooterMotor.set(shooterPower);

    robotDrive.drive(
                -MathUtil.applyDeadband(driverController.getLeftY(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(driverController.getLeftX(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(driverController.getRightX(), OIConstants.kDriveDeadband),
                true);
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}