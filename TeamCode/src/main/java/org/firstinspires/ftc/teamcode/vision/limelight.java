package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.hardware.limelightvision.LLResult;
import org.firstinspires.ftc.teamcode.RobotHardware;

public class Limelight {

    private final RobotHardware robot;
    private LLResult latestResult = null;

    public Limelight(RobotHardware robot) {
        this.robot = robot;
        if (robot.limelight != null) {
            robot.limelight.setPollRateHz(100); // Standard for Limelight 3A
            robot.limelight.pipelineSwitch(0);
            robot.limelight.start();
        }
    }

    /**
     * Reads the latest data from the camera. Must be called once per loop in TeleOp/Auto.
     */
    public void update() {
        if (robot.limelight != null) {
            latestResult = robot.limelight.getLatestResult();
        }
    }

    public void updateDashboard() {
        update();
    }

    public boolean hasTarget() {
        return latestResult != null && latestResult.isValid();
    }

    /**
     * ID of the primary detected AprilTag (-1 if none).
     */
    public int getPrimaryTagId() {
        if (!hasTarget() || latestResult.getFiducialResults().isEmpty()) {
            return -1;
        }
        return latestResult.getFiducialResults().get(0).getFiducialId();
    }

    public int getTargetId() {
        return getPrimaryTagId();
    }

    public double getTx() {
        return hasTarget() ? latestResult.getTx() : 0.0;
    }

    public double getTy() {
        return hasTarget() ? latestResult.getTy() : 0.0;
    }

    public double getTa() {
        return hasTarget() ? latestResult.getTa() : 0.0;
    }

    public LLResult getLatestResult() {
        return latestResult;
    }

    public void setPipeline(int pipelineIndex) {
        if (robot.limelight != null) {
            robot.limelight.pipelineSwitch(pipelineIndex);
        }
    }

    public void stop() {
        if (robot.limelight != null) {
            robot.limelight.stop();
        }
    }
}
