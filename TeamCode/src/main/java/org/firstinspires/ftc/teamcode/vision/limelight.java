package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class limelight {

    private final Limelight3A limelight;

    public limelight(HardwareMap hardwareMap) {
        this(hardwareMap, "limelight");
    }

    public limelight(HardwareMap hardwareMap, String name) {
        limelight = hardwareMap.get(Limelight3A.class, name);
        limelight.pipelineSwitch(0);
        limelight.start();
    }

    public boolean hasTarget() {
        LLResult result = limelight.getLatestResult();
        return result != null && result.isValid();
    }

    /**
     * ID del AprilTag principal detectado (-1 si no hay ninguno).
     */
    public int getPrimaryTagId() {
        LLResult result = limelight.getLatestResult();
        if (result == null || !result.isValid() || result.getFiducialResults().isEmpty()) {
            return -1;
        }
        return result.getFiducialResults().get(0).getFiducialId();
    }

    public double getTx() {
        LLResult result = limelight.getLatestResult();
        return (result != null && result.isValid()) ? result.getTx() : 0.0;
    }

    public void stop() {
        limelight.stop();
    }
}
