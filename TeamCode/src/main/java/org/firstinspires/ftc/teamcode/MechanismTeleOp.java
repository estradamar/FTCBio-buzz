package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name="Mechanism TeleOp", group="TeleOp")
public class MechanismTeleOp extends LinearOpMode {

    // Instantiate the hardware map class we created earlier
    HWmap robot = new HWmap();

    @Override
    public void runOpMode() {
        // Initialize the hardware variables.
        // The init() method of the hardware class does all the work here
        robot.init(hardwareMap);

        // Default maximum power for the launcher (30%)
        double maxLauncherPower = 0.30;
        
        // Variables to track previous button states so we only register one click per press
        boolean prevDpadUp = false;
        boolean prevDpadDown = false;
        boolean prevDpadLeft = false;
        boolean prevDpadRight = false;

        telemetry.addData("Status", "Initialized. Waiting for start.");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY on Driver Station)
        waitForStart();

        // Run repeatedly until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // ==========================================
            // LAUNCHER POWER MODIFIER (Gamepad 2 D-Pad)
            // ==========================================
            // Adjust max power on button press (not hold)
            if (gamepad2.dpad_up && !prevDpadUp) {
                maxLauncherPower += 0.05; // Add 5%
            }
            if (gamepad2.dpad_down && !prevDpadDown) {
                maxLauncherPower -= 0.05; // Subtract 5%
            }
            if (gamepad2.dpad_right && !prevDpadRight) {
                maxLauncherPower += 0.01; // Add 1%
            }
            if (gamepad2.dpad_left && !prevDpadLeft) {
                maxLauncherPower -= 0.01; // Subtract 1%
            }

            // Removed the limit so it can go above 100% or below 0%

            // Update previous button states for the next loop iteration
            prevDpadUp = gamepad2.dpad_up;
            prevDpadDown = gamepad2.dpad_down;
            prevDpadLeft = gamepad2.dpad_left;
            prevDpadRight = gamepad2.dpad_right;

            // ==========================================
            // LAUNCHER CONTROL (Gamepad 2 Triggers)
            // ==========================================
            // Triggers return a value from 0.0 to 1.0 depending on how hard they are pressed.
            // Right Trigger moves launcher forward, Left Trigger moves it backward.
            // We scale this by the maxLauncherPower modifier.
            double launcherPower = (gamepad2.right_trigger - gamepad2.left_trigger) * maxLauncherPower;
            
            robot.launcherLeft.setPower(launcherPower);
            robot.launcherRight.setPower(launcherPower);


            // ==========================================
            // INTAKE & FEEDER CONTROL (Gamepad 2 Bumpers)
            // ==========================================
            // Right bumper pulls elements in (forward), Left bumper pushes them out (reverse)
            double intakePower = 0.0;
            double feederPower = 0.0;

            if (gamepad2.right_bumper) {
                intakePower = 1.0;  // Full power forward
                feederPower = 1.0;
            } else if (gamepad2.left_bumper) {
                intakePower = -1.0; // Full power reverse
                feederPower = -1.0;
            }

            // ==========================================
            // AUTO-INDEXER LOGIC (Color Sensors)
            // ==========================================
            // Default to stopping the piece from entering the launcher
            boolean stop_artifact = true;
            
            // If the right trigger (launcher) is pressed, release the block
            if (gamepad2.right_trigger > 0.05) {
                stop_artifact = false;
            }

            // Check if either sensor detects an object within 5 centimeters
            double dist1 = robot.colorSensor1.getDistance(DistanceUnit.CM);
            double dist2 = robot.colorSensor2.getDistance(DistanceUnit.CM);
            boolean artifactDetected = (dist1 < 5.0 || dist2 < 5.0);

            // Override feeder if artifact is detected and we are blocked from launching
            if (artifactDetected && stop_artifact) {
                // Stop and brake the feeder so the piece waits
                feederPower = 0.0; 
                // Intake continues to spin if a bumper is held to fully suck the piece in
            }

            robot.intake.setPower(intakePower);
            robot.feeder.setPower(feederPower);


            // ==========================================
            // ELEVATOR / HANG (4 CR Servos)
            // ==========================================
            // Gamepad 2 B = Elevate Up
            // Gamepad 2 X = Go Back (Down)
            double elevatorPower = 0.0;

            if (gamepad2.b) {
                elevatorPower = 1.0;   // Full power up
            } else if (gamepad2.x) {
                elevatorPower = -1.0;  // Full power down
            } else {
                // "Stay stiff so it doesn't fall back down"
                // Because CR Servos don't automatically hold their position, we need to apply 
                // a small constant upward power when you let go of the buttons.
                // NOTE: You MUST tune this number! 0.1 is a starting guess.
                // If it falls down, increase it (e.g. 0.15). If it continues moving up, decrease it.
                elevatorPower = 0.1;   
            }

            robot.crServo1.setPower(elevatorPower);
            robot.crServo2.setPower(elevatorPower);
            robot.crServo3.setPower(elevatorPower);
            robot.crServo4.setPower(elevatorPower);


            // Send telemetry data back to the driver station so you can monitor motor powers
            telemetry.addData("Launcher Power Limit", "%.0f%%", maxLauncherPower * 100.0);
            telemetry.addData("Launcher Actual Pwr", "%.2f", launcherPower);
            telemetry.addData("Intake Power", "%.2f", intakePower);
            telemetry.addData("Feeder Power", "%.2f", feederPower);
            telemetry.addData("Elevator/Hang Power", "%.2f", elevatorPower);
            telemetry.addData("Artifact Detected?", artifactDetected);
            telemetry.addData("Stop Artifact (Block)?", stop_artifact);
            telemetry.update();
        }
    }
}
