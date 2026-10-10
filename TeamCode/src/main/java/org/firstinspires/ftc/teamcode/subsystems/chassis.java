package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.RobotHardware;

public class chassis {

    private final RobotHardware robot;

    public chassis(RobotHardware robot) {
        this.robot = robot;
    }

    public void drive(double forward, double strafe, double turn) {
        // Mecanum kinematics formula

        // Configuración matemática correcta para chasis Mecanum
        double frontLeftPower  = forward + strafe + turn;
        double frontRightPower = forward - strafe - turn;
        double backLeftPower   = forward - strafe + turn;
        double backRightPower  = forward + strafe - turn;

        // Normalization to maintain ratio if powers exceed 1.0
        double max = Math.max(Math.abs(frontLeftPower), Math.abs(backLeftPower));
        max = Math.max(max, Math.abs(frontRightPower));
        max = Math.max(max, Math.abs(backRightPower));

        if (max > 1.0) {
            frontLeftPower /= max;
            backLeftPower /= max;
            frontRightPower /= max;
            backRightPower /= max;
        }

        robot.frontLeft.setPower(frontLeftPower);
        robot.backLeft.setPower(backLeftPower);
        robot.frontRight.setPower(frontRightPower);
        robot.backRight.setPower(backRightPower);
    }

    /**
     * Stops all motors immediately.
     */
    public void stop() {
        robot.frontLeft.setPower(0);
        robot.backLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.backRight.setPower(0);
    }
}
