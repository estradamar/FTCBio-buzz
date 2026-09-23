package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.RobotHardware;

@TeleOp(name="Prototype Controller", group="Prototype")
public class PrototypeTeleOp extends OpMode {

    private RobotHardware robot;

    class MotorState {
        DcMotorEx motor;
        boolean isRightSide;

        boolean tripped = false;
        double targetSpeedPct = 0.5; // Start at 50%
        boolean isReversed = false;

        double peakCurrent = 0.0;

        long stallStartTime = 0;
        boolean isVelocityStalling = false;

        long overcurrentStartTime = 0;
        boolean isOvercurrent = false;

        public MotorState(DcMotorEx m, boolean rightSide) {
            this.motor = m;
            this.isRightSide = rightSide;
            this.targetSpeedPct = 0.0; // Start at 0%
        }
    }

    private MotorState ms0, ms1, ms2, ms3;

    class GamepadState {
        boolean a, left_bumper, right_bumper, dpad_up, dpad_down, dpad_left, dpad_right;

        public void update(Gamepad gp) {
            if (gp == null) return;
            a = gp.a;
            left_bumper = gp.left_bumper;
            right_bumper = gp.right_bumper;
            dpad_up = gp.dpad_up;
            dpad_down = gp.dpad_down;
            dpad_left = gp.dpad_left;
            dpad_right = gp.dpad_right;
        }
    }

    private GamepadState prevGp1 = new GamepadState();
    private GamepadState prevGp2 = new GamepadState();

    private double lowestBatteryVoltage = 14.0;
    private boolean hardwareInitialized = false;

    @Override
    public void init() {
        try {
            robot = new RobotHardware(hardwareMap);
            hardwareInitialized = true;
        } catch (IllegalArgumentException e) {
            telemetry.addData("ERROR", "Mandatory motor m0 not found! Please check hardware map configuration.");
            telemetry.update();
            requestOpModeStop();
            return;
        }

        if (robot.m0 != null) ms0 = new MotorState(robot.m0, false);
        if (robot.m1 != null) ms1 = new MotorState(robot.m1, true);
        if (robot.m2 != null) ms2 = new MotorState(robot.m2, false);
        if (robot.m3 != null) ms3 = new MotorState(robot.m3, true);

        if (robot.batteryVoltageSensor != null) {
            lowestBatteryVoltage = robot.batteryVoltageSensor.getVoltage();
        }

        telemetry.addData("Status", "Initialized. Ready for Prototype Testing.");
        telemetry.update();
    }

    @Override
    public void loop() {
        if (!hardwareInitialized) {
            telemetry.addData("ERROR", "Initialization failed. Cannot run loop.");
            telemetry.update();
            return;
        }

        // Track battery
        if (robot.batteryVoltageSensor != null) {
            double v = robot.batteryVoltageSensor.getVoltage();
            if (v < lowestBatteryVoltage && v > 1.0) { // filter out random 0 drops
                lowestBatteryVoltage = v;
            }
            telemetry.addData("Battery Live", "%.2f V", v);
            telemetry.addData("Battery Lowest", "%.2f V", lowestBatteryVoltage);
            telemetry.addLine();
        }

        handleMotor(ms0, gamepad1, prevGp1, "m0");
        handleMotor(ms1, gamepad1, prevGp1, "m1");
        handleMotor(ms2, gamepad2, prevGp2, "m2");
        handleMotor(ms3, gamepad2, prevGp2, "m3");

        prevGp1.update(gamepad1);
        prevGp2.update(gamepad2);

        telemetry.addData("q querias q muestre la telemetria?", "Si");

        telemetry.update();
    }

    private void handleMotor(MotorState ms, Gamepad gp, GamepadState prevGp, String name) {
        if (ms == null || ms.motor == null || gp == null) return;

        // 1. Check for Reset if Tripped
        if (ms.tripped) {
            ms.motor.setPower(0);
            telemetry.addData(name + " STATUS", "TRIPPED! Press A to reset.");

            if (gp.a && !prevGp.a) {
                ms.tripped = false;
                ms.isVelocityStalling = false;
                ms.stallStartTime = 0;
                ms.isOvercurrent = false;
                ms.overcurrentStartTime = 0;
            }
            return; // Skip rest of logic if tripped
        }

        // 2. Read inputs
        boolean triggerPressed = ms.isRightSide ? (gp.right_trigger > 0.5) : (gp.left_trigger > 0.5);
        boolean bumperPressed = ms.isRightSide ? gp.right_bumper : gp.left_bumper;
        boolean prevBumperPressed = ms.isRightSide ? prevGp.right_bumper : prevGp.left_bumper;

        boolean upPressed = ms.isRightSide ? gp.dpad_right : gp.dpad_up;
        boolean prevUpPressed = ms.isRightSide ? prevGp.dpad_right : prevGp.dpad_up;
        boolean downPressed = ms.isRightSide ? gp.dpad_left : gp.dpad_down;
        boolean prevDownPressed = ms.isRightSide ? prevGp.dpad_left : prevGp.dpad_down;

        // 3. Process inputs
        if (bumperPressed && !prevBumperPressed) {
            ms.isReversed = !ms.isReversed;
        }

        if (upPressed && !prevUpPressed) {
            ms.targetSpeedPct += 0.05;
            if (ms.targetSpeedPct > 1.0) ms.targetSpeedPct = 1.0;
        }

        if (downPressed && !prevDownPressed) {
            ms.targetSpeedPct -= 0.05;
            if (ms.targetSpeedPct < 0.0) ms.targetSpeedPct = 0.0;
        }

        // 4. Apply power and check failsafes
        double currentAmps = ms.motor.getCurrent(CurrentUnit.AMPS);
        if (currentAmps > ms.peakCurrent) {
            ms.peakCurrent = currentAmps;
        }

        double measuredVelocity = Math.abs(ms.motor.getVelocity()); // ticks per sec

        if (triggerPressed) {
            double power = ms.targetSpeedPct * (ms.isReversed ? -1.0 : 1.0);
            ms.motor.setPower(power);

            long currentTime = System.currentTimeMillis();

            // Velocity Failsafe Check (< 10 ticks/s for 0.6s)
            if (measuredVelocity < 10.0 && ms.targetSpeedPct > 0.0) {
                if (!ms.isVelocityStalling) {
                    ms.isVelocityStalling = true;
                    ms.stallStartTime = currentTime;
                } else if (currentTime - ms.stallStartTime >= 600) {
                    tripMotor(ms, gp, "Stall detected (<10 ticks/s for >0.6s)");
                }
            } else {
                ms.isVelocityStalling = false;
                ms.stallStartTime = 0;
            }

            // Current Failsafe Check (Continuous > 4.0A, giving 0.2s for inrush to settle)
            if (currentAmps > 4.0) {
                if (!ms.isOvercurrent) {
                    ms.isOvercurrent = true;
                    ms.overcurrentStartTime = currentTime;
                } else if (currentTime - ms.overcurrentStartTime >= 200) {
                    tripMotor(ms, gp, "Overcurrent detected (>4.0A)");
                }
            } else {
                ms.isOvercurrent = false;
                ms.overcurrentStartTime = 0;
            }

        } else {
            ms.motor.setPower(0);
            ms.isVelocityStalling = false;
            ms.stallStartTime = 0;
            ms.isOvercurrent = false;
            ms.overcurrentStartTime = 0;
        }

        // 5. Telemetry
        double targetRPM = ms.targetSpeedPct * RobotHardware.MAX_RPM * (ms.isReversed ? -1.0 : 1.0);
        double actualRPM = (ms.motor.getVelocity() / RobotHardware.TICKS_PER_REV) * 60.0;

        telemetry.addData("--- " + name + " ---", ms.isRightSide ? "(Right Side)" : "(Left Side)");
        telemetry.addData("Target Power", "%.0f%% (%s)", ms.targetSpeedPct * 100.0, ms.isReversed ? "REV" : "FWD");
        telemetry.addData("Target RPM", "%.1f", targetRPM);
        telemetry.addData("Actual RPM", "%.1f", actualRPM);
        telemetry.addData("Current (Live/Peak)", "%.2f A / %.2f A", currentAmps, ms.peakCurrent);
        telemetry.addData("Status", triggerPressed ? "RUNNING" : "STOPPED");
    }

    private void tripMotor(MotorState ms, Gamepad gp, String reason) {
        ms.tripped = true;
        ms.motor.setPower(0);
        try {
            gp.rumble(500); // rumble 500ms
        } catch (Exception e) {
            // Rumble might fail on some platforms/controllers, safely ignore
        }
    }
}
