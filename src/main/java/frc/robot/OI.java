/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;


public class OI {
    private GenericHID controller = new GenericHID(0){
    
        @Override
        public double getY(Hand hand) {
            return 0;
        }
    
        @Override
        public double getX(Hand hand) {
            if (hand==Hand.kLeft)
                return getRawAxis(0);
            else
                return getRawAxis(2);
        }
    };
    
    public boolean getButton(int button){
        return controller.getRawButtonPressed(button);
    }

    public double getXAxis(Hand hand)
    {
        return controller.getX(hand);
    }
}


