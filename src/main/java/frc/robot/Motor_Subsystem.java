/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PWMTalonSRX;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import java.lang.Math;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;





/**
 * Add your docs here.
 */
public class Motor_Subsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public CANSparkMax spark = new CANSparkMax(RobotMap.SPARK_CAN_ID, MotorType.kBrushless);
  public TalonSRX talon = new TalonSRX(RobotMap.TALON_CAN_ID);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void controlSpeed(double speed){
    spark.set(capSpeed(speed, 1));
  }

  public void controlTalonSpeed(double speed){
    talon.set(ControlMode.PercentOutput, capSpeed(speed, 0.5)); 
    SmartDashboard.putNumber("Talon Output", capSpeed(speed, 0.5));

  }

  private double capSpeed(double speed, double limit){
  /*
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
*/

if(Math.abs(speed) > limit)
{
  if(speed < limit)
  {
    return -1*limit;
  }
  else return limit;
}
else return speed;
}
}
