/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.CanbusDistanceSensor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  public static OI m_oi = new OI();
  public static Motor_Subsystem m_motor = new Motor_Subsystem();
  public static CanbusDistanceSensor m_canbus = new CanbusDistanceSensor();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */

  private double a;
  private int b;

  public static int distanceSensorLoad = 0;
  public static double loadSensorSerial;
  public static double loadSensorPart;
  public static double loadSensorFirmware;
  public static byte[] hwdataLoad = new byte[8];

  public static int distanceSensorRocket = 0;
  public static double rocketSensorSerial;
  public static double rocketSensorPart;
  public static double rocketSensorFirmware;
  public static byte[] hwdataRocket  = new byte[8];

  @Override
  public void robotInit() {
    System.out.println("Starting My Code!");
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    hwdataLoad = CanbusDistanceSensor.readHeartbeat(distanceSensorLoad);
    double[] temp = CanbusDistanceSensor.getSensorInfo(hwdataLoad);
    loadSensorSerial = temp[0];
    loadSensorPart = temp[1];
    loadSensorFirmware = temp[2];
    SmartDashboard.putNumber("LoadSerial", loadSensorSerial);
    SmartDashboard.putNumber("LoadPart", loadSensorPart);
    SmartDashboard.putNumber("LoadFirmware", loadSensorFirmware);
    double temp1[] = CanbusDistanceSensor.readCalibrationState(distanceSensorLoad);
    SD.putN("X", temp1[0]);
    SD.putN("Y", temp1[1]);
    SD.putN("Offset", temp1[2]);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    System.out.println("Autonomous Initialized");
  }

  @Override
  public void teleopInit() {
    System.out.println("Teleop Initialized");
    //Distance loop here (getDistanceMM(int id) function in CanbusDistanceSensor)
    //dist 1000, motor = 1
    //dist <50, motor = 0
    //otherwise linear

    //motor = (dist-50)/1000
    m_motor.controlSpeed((CanbusDistanceSensor.getDistanceMM(distanceSensorLoad)-50)/1000);
  }

  @Override
  public void testInit() {
    System.out.println("Test Mode Initialized");
  }

  @Override
  public void disabledInit() {
    System.out.println("Disabled");
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

    SmartDashboard.putNumber("Left X", m_oi.getXAxis(Hand.kLeft));
    SmartDashboard.putNumber("Right X", m_oi.getXAxis(Hand.kRight));



    if(m_oi.getButton(1))
    {
      System.out.println("Button 1 pressed");
    }

    m_motor.controlSpeed(m_oi.getXAxis(Hand.kRight));
    m_motor.controlTalonSpeed(m_oi.getXAxis(Hand.kLeft));

    Scheduler.getInstance().run();
    SmartDashboard.putNumber("TTT", Timer.getFPGATimestamp() - a);
    a = Timer.getFPGATimestamp();
    b++;
    if (b >= 10) {
      double dist = CanbusDistanceSensor.getDistanceMM(distanceSensorLoad);
      SmartDashboard.putNumber("RawDistance", dist);
      SD.putN0("DistMM", dist);
      SD.putN2("DistFt", dist / 304.8);
      double temp[] = CanbusDistanceSensor.readQuality(distanceSensorLoad);
      SD.putN0("AmbLight", temp[0]);
      SD.putN0("StdDev", temp[1]);

      b = 0;
    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
