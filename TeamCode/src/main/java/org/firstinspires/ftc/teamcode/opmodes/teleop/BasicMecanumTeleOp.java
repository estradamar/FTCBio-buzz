package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.subsystems.chassis;

@TeleOp(name = "TeleOp: Basic Drive", group = "TeleOp")
public class BasicMecanumTeleOp extends LinearOpMode {

    private RobotHardware robot;
    private chassis robotChassis;

    @Override
    public void runOpMode() {

        // 1. Hardware initialization using the centralized class
        robot = new RobotHardware(hardwareMap);
        robotChassis = new chassis(robot);

        telemetry.addData("Status", "Initialized - Ready for Manual Drive");
        telemetry.update();

        // Wait for the driver to press PLAY on the Driver Station
        waitForStart();

        // Main execution loop during the manual control phase
        while (opModeIsActive()) {

            // --- A. MECANUM CHASSIS CONTROL (Gamepad 1) ---
            double y = -gamepad1.left_stick_y; // Forward / Reverse
            double x = gamepad1.left_stick_x * 1.1; // Lateral movement (Strafe)
            double rx = gamepad1.right_stick_x; // Rotation on its own axis

            // Drive using the centralized chassis class
            robotChassis.drive(y, x, rx);

            // --- B. MONITORING TELEMETRY ---
            telemetry.addData("Gamepad Y", y);
            telemetry.addData("Gamepad X", x);
            telemetry.addData("Gamepad Turn", rx);
            telemetry.update();
        }
    }
}
