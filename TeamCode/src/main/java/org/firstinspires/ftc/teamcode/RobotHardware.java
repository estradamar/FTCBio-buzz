package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

public class RobotHardware {
    public DcMotorEx m0 = null;
    public DcMotorEx m1 = null;
    public DcMotorEx m2 = null;
    public DcMotorEx m3 = null;

    public VoltageSensor batteryVoltageSensor;

    // MAX RPM for REV Core Hex
    public static final double MAX_RPM = 125.0;
    public static final double TICKS_PER_REV = 288.0;

    public RobotHardware(HardwareMap hwMap) {
        // m0 is mandatory. If not found, hwMap.get throws IllegalArgumentException.
        m0 = hwMap.get(DcMotorEx.class, "m0");
        configureMotor(m0);

        // m1, m2, m3 are optional
        m1 = tryGetMotor(hwMap, "m1");
        m2 = tryGetMotor(hwMap, "m2");
        m3 = tryGetMotor(hwMap, "m3");

        if (hwMap.voltageSensor.iterator().hasNext()) {
            batteryVoltageSensor = hwMap.voltageSensor.iterator().next();
        }
    }

    private DcMotorEx tryGetMotor(HardwareMap hwMap, String name) {
        try {
            DcMotorEx motor = hwMap.get(DcMotorEx.class, name);
            if (motor != null) {
                configureMotor(motor);
            }
            return motor;
        } catch (IllegalArgumentException e) {
            return null; // Not found, which is fine for optional motors
        }
    }

    private void configureMotor(DcMotorEx motor) {
        if (motor == null) return;
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
