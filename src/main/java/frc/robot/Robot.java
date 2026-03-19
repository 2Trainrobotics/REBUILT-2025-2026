// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Intake; 

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

  private final SparkMax leftFeederMotor = new SparkMax(Constants.DriveConstants.kLeftFeederCanId, MotorType.kBrushless);
  private final SparkMax rightFeederMotor = new SparkMax(Constants.DriveConstants.kRightFeederCanId, MotorType.kBrushless);

  // private final SparkMax leftIntakeLifterMotor = new SparkMax(Constants.DriveConstants.kLeftIntakeLifterCanId, MotorType.kBrushless);
  // private final SparkMax rightIntakeLifterMotor = new SparkMax(Constants.DriveConstants.kRightIntakeLifterCanId, MotorType.kBrushless);
  // private final SparkMax intakeSpinnerMotor = new SparkMax(Constants.DriveConstants.kIntakeSpinnerCanId, MotorType.kBrushless);

  private final XboxController driverController = new XboxController(OIConstants.kDriverControllerPort);
  private final XboxController operatorController = new XboxController(OIConstants.kOperatorControllerPort);
  private final Timer timer = new Timer();

  private static final String kDefaultAuto = "Default";
  private static final String kStraightAuto = "Straight Auto";
  private static final String kLeftAuto = "Left Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  // Here's where we recall the intake subsystem from Intake.java class. 

  Intake intake = new Intake(); 

  /*
   * Controls for One Controller:
   * Driver Controller:
   *   Left Stick: Forward/Backward and Left/Right movement
   *   Right Stick: Rotation
   *   A Button: Shoot
   *   B Button:  Feeder/Pulley
   *   D-Pad Up: Lift Intake
   *   D-Pad Down: Lower Intake
   *   D-Pad Left: Spin Intake Forward
   * 
   * Controls for Two Controllers:
   * Driver Controller:
   *   Left Stick: Forward/Backward and Left/Right movement
   *   Right Stick: Rotation
   *   D-Pad Up: Lift Intake
   *   D-Pad Down: Lower Intake
   *   Y Button: Spin Intake Forward
* Operator Controller:
   *   A Button: Shoot
   *   B Button:  Feeder/Pulley
   *   D-Pad Up: Lift Intake
   *   D-Pad Down: Lower Intake
   *   Y Button: Spin Intake Forward
   */

  /** Called once at the beginning of the robot program. */
  public Robot() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("Straight Auto", kStraightAuto);
    m_chooser.addOption("Left Auto", kLeftAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    leftShooterMotor.setInverted(true);
    rightShooterMotor.setInverted(false);

    leftFeederMotor.setInverted(true);
    rightFeederMotor.setInverted(false);

    // Transfered to intake: 

    // leftIntakeLifterMotor.setInverted(true);
    // rightIntakeLifterMotor.setInverted(false);


  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    timer.restart();
    m_autoSelected = m_chooser.getSelected();
  }

  public void moveStraightFor2Seconds() {
    // Drive for 2 seconds
    if (timer.get() < 2.0) {
      // Drive forwards half speed, make sure to turn input squaring off
      robotDrive.drive(0.5, 0.0, 0.0, false);
    } else {
      robotDrive.drive(0.0, 0.0, 0.0, false);
    }
  }

  public void moveLeftFor2Seconds() {
    // Drive for 2 seconds
    if (timer.get() < 2.0) {
      // Drive left at half speed, make sure to turn input squaring off
      robotDrive.drive(0.0, 0.5, 0.0, false);
    } else {
      robotDrive.drive(0.0, 0.0, 0.0, false);
    }
  }

  public void moveLeftFor2SecondsThenStraightFor3Seconds() {
    // Drive for 4 seconds
    if (timer.get() < 2.0) {
      // Drive left at half speed, make sure to turn input squaring off
      robotDrive.drive(0.0, 0.5, 0.0, false);
    } else if (timer.get() < 5.0) {
      // Drive forwards half speed, make sure to turn input squaring off
      robotDrive.drive(0.5, 0.0, 0.0, false);
    } else if (timer.get() < 6.0) {
      // Drive forwards half speed, make sure to turn input squaring off
      // leftShooterMotor.set(0.5);
      // rightShooterMotor.set(0.5); 
    } else {
      robotDrive.drive(0.0, 0.0, 0.0, false);
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kStraightAuto:
        moveStraightFor2Seconds();
        break;
      case kLeftAuto:
        moveLeftFor2Seconds();
        break;
      case kDefaultAuto:
      default:
        moveLeftFor2SecondsThenStraightFor3Seconds();
        break;
    }
  }

  @Override
  public void autonomousExit() {
    robotDrive.drive(0.0, 0.0, 0.0, false);
  }

  // here we're gonna declare the Joystick Control for Teleoperation

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    // 1. Drive:  driver controller, left and right joysticks
    robotDrive.drive(
                -MathUtil.applyDeadband(driverController.getLeftY(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(driverController.getLeftX(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(driverController.getRightX(), OIConstants.kDriveDeadband),
                true);

    // 2.a. Intake up/down:
    //   driver controller, D-Pad Up/Down
    //   operator controller, D-Pad Up/Down
     if (driverController.getPOV() == 0) {
      // Lift intake
      intake.retractIntake(); 
    } else if (driverController.getPOV() == 180) {
      // Lower intake
     intake.extendIntake();
    } 
    else {
     intake.intakeRollersOff(); 
    }
    
   

    // // 2.b. Intake spinner:
    // //   driver controller, Y button
    // //   operator controller, Y button
    // if (driverController.getYButton()) {
    //   // Engage spinner motor
    //   intakeSpinnerMotor.set(0.5);
    // } else if (operatorController.getYButton()) {
    //   // Engage spinner motor
    //   intakeSpinnerMotor.set(0.5);
    // } else {
    //   // Stop spinner motor
    //   intakeSpinnerMotor.set(0.0);
    // }

    // 3. Shooter:  operator controller, A button
    if (operatorController.getAButton()) {
      leftShooterMotor.set(0.5);
      rightShooterMotor.set(0.5); 
    } else {
      leftShooterMotor.set(0.0);
      rightShooterMotor.set(0.0); 
    }

    // 4. Feeder:  operator controller, B button
    if (operatorController.getBButton()) {
      leftFeederMotor.set(0.5);
      rightFeederMotor.set(0.5); 
    } else {
      leftFeederMotor.set(0.0);
      rightFeederMotor.set(0.0); 
    }
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}