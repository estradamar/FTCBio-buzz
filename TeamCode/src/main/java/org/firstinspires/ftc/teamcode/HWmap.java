package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HWmap {
    
    // Define Hardware Members
    // 4 REV HD Hex Motors
    public DcMotor launcherLeft;
    public DcMotor launcherRight;
    public DcMotor intake;
    public DcMotor feeder;

    // 4 Continuous Rotation Servos
    public CRServo crServo1;
    public CRServo crServo2;
    public CRServo crServo3;
    public CRServo crServo4;

    // 2 REV Color Sensors V3 (using DistanceSensor interface for proximity)
    public DistanceSensor colorSensor1;
    public DistanceSensor colorSensor2;

    // Local reference to the hardware map
    HardwareMap hwMap = null;

    // Constructor
    public HWmap() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // --- Define and Initialize Motors ---
        // The strings here ("launcherLeft", etc.) MUST match the names configured on the Robot Controller / Driver Station.
        launcherLeft  = hwMap.get(DcMotor.class, "launcherLeft");
        launcherRight = hwMap.get(DcMotor.class, "launcherRight");
        intake        = hwMap.get(DcMotor.class, "intake");
        feeder        = hwMap.get(DcMotor.class, "feeder");

        // Set motor directions. 
        // You may need to flip these depending on how your motors are physically mounted.
        // Usually, mirrored mechanisms (like a two-motor launcher) have one motor reversed.
        launcherLeft.setDirection(DcMotor.Direction.FORWARD);
        launcherRight.setDirection(DcMotor.Direction.REVERSE);
        intake.setDirection(DcMotor.Direction.FORWARD);
        feeder.setDirection(DcMotor.Direction.FORWARD);

        // Set Zero Power Behavior (BRAKE stops quickly, FLOAT coasts)
        launcherLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT); // Launchers usually coast to preserve motor health
        launcherRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        feeder.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Set all motors to zero power
        launcherLeft.setPower(0);
        launcherRight.setPower(0);
        intake.setPower(0);
        feeder.setPower(0);

        // Set motors to run without encoders by default. 
        // If you plug in the encoder cables to use velocity control, change this to RUN_USING_ENCODER
        launcherLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        launcherRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        feeder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // --- Define and Initialize Continuous Rotation Servos ---
        crServo1 = hwMap.get(CRServo.class, "crServo1");
        crServo2 = hwMap.get(CRServo.class, "crServo2");
        crServo3 = hwMap.get(CRServo.class, "crServo3");
        crServo4 = hwMap.get(CRServo.class, "crServo4");

        // Set all CR Servos to zero power (stopped)
        crServo1.setPower(0);
        crServo2.setPower(0);
        crServo3.setPower(0);
        crServo4.setPower(0);

        // --- Define and Initialize Sensors ---
        colorSensor1 = hwMap.get(DistanceSensor.class, "colorSensor1");
        colorSensor2 = hwMap.get(DistanceSensor.class, "colorSensor2");
    }
}
