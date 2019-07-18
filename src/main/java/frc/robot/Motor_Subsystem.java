/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;




/**
 * Add your docs here.
 */
public class Motor_Subsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public CANSparkMax spark = new CANSparkMax(12, MotorType.kBrushless);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void controlSpeed(double speed){
    spark.set(capSpeed(speed, 0.1));
  }

  private double capSpeed(double speed, double limit){
    if(speed > 0)
    {
      if(speed > limit)
      {
        return limit;
      }
      else return speed;
    }
    else{
      if(speed < -1*limit)
      {
        return -1*limit;
      }
      else{
        return speed;
      }
    }

  }
}
