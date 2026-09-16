package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RobotHardware {

    public DcMotorEx frontLeft;
    public DcMotorEx backLeft;
    public DcMotorEx frontRight;
    public DcMotorEx backRight;

    public RobotHardware(HardwareMap hwMap) {

        try {
            frontLeft = hwMap.get(DcMotorEx.class, "frontLeft");
        } catch (Exception e) {
            frontLeft = null;
        }
        try {
            backLeft = hwMap.get(DcMotorEx.class, "backLeft");
        } catch (Exception e) {
            backLeft = null;
        }
        try {
            frontRight = hwMap.get(DcMotorEx.class, "frontRight");
        } catch (Exception e) {
            frontRight = null;
        }
        try {
            backRight = hwMap.get(DcMotorEx.class, "backRight");
        } catch (Exception e) {
            backRight = null;
        }

        if (frontRight != null)
            frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        if (backRight != null)
            backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        if (frontLeft != null)
            frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        if (backLeft != null)
            backLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        if (frontLeft != null)
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if (backLeft != null)
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if (frontRight != null)
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if (backRight != null)
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if (frontLeft != null)
            frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        if (backLeft != null)
            backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        if (frontRight != null)
            frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        if (backRight != null)
            backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}
