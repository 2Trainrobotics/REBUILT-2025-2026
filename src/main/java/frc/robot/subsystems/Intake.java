package frc.robot.subsystems; 

import com.revrobotics.AbsoluteEncoder; 
import com.revrobotics.spark.SparkLowLevel.MotorType; 
import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder; 
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode; 
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase; 
import com.revrobotics.spark.config.SparkBaseConfig; 

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; 
import edu.wpi.first.wpilibj2.command.SubsystemBase; 
import frc.robot.Constants; 
import frc.robot.Constants.IntakeConstants; 

public class Intake extends SubsystemBase {
    private SparkMax leftLifter, rightLifter; 
    private SparkMax intakeSpinner; 
    private RelativeEncoder leftLifterEncoder, rightLifterEncoder; 
    private boolean enableGuards = false; 

    // Here we declare every component config profile: 

    private final SparkMaxConfig lifterConfig = new SparkMaxConfig(); 
    private final SparkMaxConfig intakeSpinnerConfig = new SparkMaxConfig(); 

      private double currentPositionLeft = leftLifterEncoder.getPosition(); 
      private  double currentPositionRight = rightLifterEncoder.getPosition();
   

    public Intake() {

        /*  Here, we recall the Intake Components from the Constants class and assign them to the 
            the variables we declared above. 
        */
        leftLifter = new SparkMax(Constants.IntakeConstants.kLeftIntakeLifterCanId, MotorType.kBrushless); 
        rightLifter = new SparkMax(Constants.IntakeConstants.kRightIntakeLifterCanId, MotorType.kBrushless); 
         intakeSpinner = new SparkMax(Constants.IntakeConstants.kIntakeSpinnerCanId, MotorType.kBrushless); 

         // Here, we set the idle mode for the intake motors to brake so that they hold their position when not powered. 
         /*  The rollers motors on the other hand will be set to coast so that they can spin freely when not powered
            through inertia. 

            Keep in mind that during previous years, the api was much simpler but led to bugs and issues with the motors. 
            This year, the API is more complex but it allows us to have more control over the motors and their behavior. 
          */ 
        // lifterConfig.idleMode(IdleMode.kBrake);

            lifterConfig
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(30); // Set a smart current limit to prevent damage to the motors (30 amps).

            
           leftLifter.configure(
            lifterConfig,
            SparkBase.ResetMode.kNoResetSafeParameters,
            SparkBase.PersistMode.kPersistParameters
           ); 

           rightLifter.configure(
            lifterConfig,
            SparkBase.ResetMode.kNoResetSafeParameters,
            SparkBase.PersistMode.kPersistParameters
           ); 

              intakeSpinnerConfig
                .idleMode(IdleMode.kCoast)
                .smartCurrentLimit(30); 
    
                intakeSpinner.configure(
                 intakeSpinnerConfig,
                 SparkBase.ResetMode.kNoResetSafeParameters,
                 SparkBase.PersistMode.kPersistParameters
                );

            // Here's where we'll be declaring the two lifters' encoders: 
            leftLifterEncoder = leftLifter.getEncoder(); 
            rightLifterEncoder = rightLifter.getEncoder();

            rightLifterEncoder.setPosition(0); 
            leftLifterEncoder.setPosition(0);

            // Here, we configure the directions of the lifters' motors: 
            /*Keep in mind that this api is not future proof and at any point with any new Sparkmax update and once this
             * api gets phased out, this method will stop working and we'll have to research for a new method to set the direction
             * of the motors. 
             */
             leftLifter.setInverted(true);
             rightLifter.setInverted(false);

    }

    public void toggleGuard() {
        enableGuards = !enableGuards; 
    }

        public void extendIntake() {


            SmartDashboard.putNumber("left lifter position", currentPositionLeft);
            SmartDashboard.putNumber("right lifter position", currentPositionRight);

            if (enableGuards) {
                if (currentPositionLeft < IntakeConstants.extendMax && currentPositionRight < IntakeConstants.extendMax) {
                    leftLifter.set(IntakeConstants.kIntakeLifterSpeed); 
                    rightLifter.set(IntakeConstants.kIntakeLifterSpeed); 
                } else {
                    leftLifter.set(0); 
                    rightLifter.set(0); 
                }
            } else {
                leftLifter.set(IntakeConstants.kIntakeLifterSpeed); 
                rightLifter.set(IntakeConstants.kIntakeLifterSpeed); 
            }

        }

        public void retractIntake() {
    

            SmartDashboard.putNumber("left lifter position", currentPositionLeft);
            SmartDashboard.putNumber("right lifter position", currentPositionRight);

            if (enableGuards) {
                if (currentPositionLeft > IntakeConstants.retractMax && currentPositionRight > IntakeConstants.retractMax) {
                    leftLifter.set(-IntakeConstants.kIntakeLifterSpeed); 
                    rightLifter.set(-IntakeConstants.kIntakeLifterSpeed); 
                } else {
                    leftLifter.set(0); 
                    rightLifter.set(0); 
                }
            } else {
                leftLifter.set(-IntakeConstants.kIntakeLifterSpeed); 
                rightLifter.set(-IntakeConstants.kIntakeLifterSpeed); 
            }
        }

        public void stopIntakeLifter() {


           SmartDashboard.putNumber("left lifter position", currentPositionLeft);
            SmartDashboard.putNumber("right lifter position", currentPositionRight);
            
            leftLifter.set(0); 
            rightLifter.set(0); 
        }

        public void intakeRollersIn() {
                    intakeSpinner.set(Constants.IntakeConstants.kIntakeSpinnerSpeed); 
                }
           

        public void fixJam() {      
                    intakeSpinner.set(-Constants.IntakeConstants.kIntakeSpinnerSpeed); 
        }
        public void intakeRollersOff() {
            intakeSpinner.set(0); 
        }
}


