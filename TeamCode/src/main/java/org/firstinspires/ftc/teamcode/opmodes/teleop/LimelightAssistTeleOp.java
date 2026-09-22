package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.vision.VisionMecanumDrive;
import org.firstinspires.ftc.teamcode.vision.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.IndicatorLights;

@TeleOp(name = "TeleOp: Limelight Assist", group = "TeleOp")
public class LimelightAssistTeleOp extends LinearOpMode {

    private RobotHardware robot;
    private VisionMecanumDrive drive;
    private Limelight limelightWrapper;
    private IndicatorLights lights;

    @Override
    public void runOpMode() {
        robot = new RobotHardware(hardwareMap);
        drive = new VisionMecanumDrive(robot);
        limelightWrapper = new Limelight(robot);
        lights = new IndicatorLights(robot);

        // Turn red initially to indicate no target found yet
        lights.setRed();

        telemetry.addData("Status", "Robot Initialized - Ready for Vision Assist");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Read latest data from camera
            limelightWrapper.update();

            boolean isAligned = false;

            // Automatic alignment with the 'A' button on Gamepad 1
            if (gamepad1.a && limelightWrapper.hasTarget()) {
                double tx = limelightWrapper.getTx();
                isAligned = drive.alignToTarget(tx);
                
                telemetry.addData("Aligning", isAligned ? "ALIGNED!" : "Adjusting...");
                telemetry.addData("Horizontal Error (tx)", tx);
            } else {
                // Manual Mecanum control: Y (forward), X (lateral), Right Stick X (turn)
                double y = -gamepad1.left_stick_y;
                double x = gamepad1.left_stick_x * 1.1; // Correction factor for lateral slip
                double rx = gamepad1.right_stick_x;

                drive.driveMecanum(y, x, rx);
            }

            // Update LED states based on camera and alignment status
            if (limelightWrapper.hasTarget()) {
                if (gamepad1.a) {
                    if (isAligned) {
                        lights.setGreen(); // Target locked and robot is aligned
                    } else {
                        lights.setColor(IndicatorLights.YELLOW); // Target found, currently aligning
                    }
                } else {
                    lights.setBlue(); // Target is visible, but not auto-aligning
                }
                
                telemetry.addData("Target ID", limelightWrapper.getTargetId());
            } else {
                lights.setRed(); // No target found
                telemetry.addData("Limelight", "Searching for AprilTag...");
            }

            telemetry.update();
        }
    }
}
