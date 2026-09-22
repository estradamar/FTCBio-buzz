package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.subsystems.chassis;

public class VisionMecanumDrive {
    private final RobotHardware robot;
    private final chassis robotChassis;

    // Proportional gain for Limelight automatic alignment
    private final double Kp_TURN = 0.03;
    private final double MIN_TURN_POWER = 0.05;

    public VisionMecanumDrive(RobotHardware robot) {
        this.robot = robot;
        this.robotChassis = new chassis(robot);
        // Direction and behavior (BRAKE) are already configured in RobotHardware
    }

    public void driveMecanum(double forward, double strafe, double turn) {
        // Use the centralized chassis class
        robotChassis.drive(forward, strafe, turn);
    }

    // Automatic alignment using horizontal error (tx) from Limelight
    public boolean alignToTarget(double tx) {
        if (Math.abs(tx) < 1.0) { // 1 degree tolerance
            driveMecanum(0, 0, 0);
            return true; // Successfully aligned
        }

        double turnPower = tx * Kp_TURN;
        if (Math.abs(turnPower) < MIN_TURN_POWER) {
            turnPower = Math.signum(turnPower) * MIN_TURN_POWER;
        }

        driveMecanum(0, 0, turnPower);
        return false;
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior) {
        robot.frontLeft.setZeroPowerBehavior(behavior);
        robot.backLeft.setZeroPowerBehavior(behavior);
        robot.frontRight.setZeroPowerBehavior(behavior);
        robot.backRight.setZeroPowerBehavior(behavior);
    }
}
