package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LimelightHiveTracker {
    // Configuración Cinemática
    public double cameraPitchDeg = 35.0; // Ajustable según el robot
    public double defaultTagSpacing = 0.1524; // ~6 pulgadas por defecto
    private static final double EMA_ALPHA = 0.2;

    private Map<HiveVisionConfig.HiveCell, double[]> smoothedPoses = new HashMap<>();

    // Resultados públicos del tracker
    public double targetX = 0; // Strafe error (positivo = derecha)
    public double targetZ = 0; // Forward error (positivo = lejos)
    public double targetYaw = 0; // Rotational error en grados
    public boolean targetFound = false;
    public HiveVisionConfig.HiveCell currentTargetCell = HiveVisionConfig.HiveCell.UNKNOWN;
    public int tagsInCluster = 0;

    public void update(Limelight3A limelight, HiveVisionConfig.HiveCell targetCell) {
        LLResult result = limelight.getLatestResult();
        if (result == null || !result.isValid()) {
            targetFound = false;
            return;
        }

        List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();
        Map<Integer, double[]> tagPoses = new HashMap<>();

        for (LLResultTypes.FiducialResult f : fiducials) {
            int id = f.getFiducialId();
            
            // Usamos TargetPoseCameraSpace (posición del tag respecto a la cámara)
            Pose3D pose = f.getTargetPoseCameraSpace();
            if (pose != null) {
                // Asumiendo que getPosition().x devuelve metros en FTC SDK.
                double xc = pose.getPosition().x;
                double yc = pose.getPosition().y;
                double zc = pose.getPosition().z;

                // Aplicar inclinación (Pitch) de la cámara para proyectar al suelo del robot
                double theta = Math.toRadians(cameraPitchDeg);
                double robotZ = zc * Math.cos(theta) + yc * Math.sin(theta);
                double robotX = xc;

                // Extraemos el Yaw directo proporcionado por Limelight en la orientación 3D
                // Esto puede variar según la orientación montada de la cámara.
                double rawYaw = pose.getOrientation().getYaw(org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.RADIANS);
                
                tagPoses.put(id, new double[]{robotX, robotZ, rawYaw});
            }
        }

        List<Integer> visibleIndices = new ArrayList<>();
        for (int j = 0; j < targetCell.tagIds.length; j++) {
            if (tagPoses.containsKey(targetCell.tagIds[j])) {
                visibleIndices.add(j);
            }
        }

        if (!visibleIndices.isEmpty()) {
            double wallYaw = 0;
            double dynamicSpacing = defaultTagSpacing;

            if (visibleIndices.size() >= 2) {
                // Cálculo dinámico de yaw y separación usando extremos del cluster
                int leftIdx = visibleIndices.get(0);
                int rightIdx = visibleIndices.get(visibleIndices.size() - 1);

                double[] pL = tagPoses.get(targetCell.tagIds[leftIdx]);
                double[] pR = tagPoses.get(targetCell.tagIds[rightIdx]);

                wallYaw = Math.atan2(pR[1] - pL[1], pR[0] - pL[0]);
                double dist = Math.hypot(pR[0] - pL[0], pR[1] - pL[1]);
                dynamicSpacing = dist / (rightIdx - leftIdx);
            } else {
                // Fallback si solo ve un tag
                int idx = visibleIndices.get(0);
                double[] p = tagPoses.get(targetCell.tagIds[idx]);
                wallYaw = p[2]; // Usa el yaw directo del tag
            }

            double sumCx = 0;
            double sumCz = 0;

            for (int idx : visibleIndices) {
                double[] p = tagPoses.get(targetCell.tagIds[idx]);
                
                // Extrapolar al centro de la celda de 4 tags (índice central = 1.5)
                double offsetFromCenter = (idx - (targetCell.tagIds.length - 1) / 2.0) * dynamicSpacing;

                double cx = p[0] - offsetFromCenter * Math.cos(wallYaw);
                double cz = p[1] - offsetFromCenter * Math.sin(wallYaw);

                sumCx += cx;
                sumCz += cz;
            }

            int c = visibleIndices.size();
            double rawX = sumCx / c;
            double rawZ = sumCz / c;
            double rawYaw = Math.toDegrees(wallYaw);

            // Suavizado (EMA)
            if (!smoothedPoses.containsKey(targetCell)) {
                smoothedPoses.put(targetCell, new double[]{rawX, rawZ, rawYaw});
            } else {
                double[] s = smoothedPoses.get(targetCell);
                s[0] = s[0] + EMA_ALPHA * (rawX - s[0]);
                s[1] = s[1] + EMA_ALPHA * (rawZ - s[1]);
                s[2] = s[2] + EMA_ALPHA * (rawYaw - s[2]);
            }

            double[] smoothed = smoothedPoses.get(targetCell);
            this.targetX = smoothed[0];
            this.targetZ = smoothed[1];
            this.targetYaw = smoothed[2];
            this.targetFound = true;
            this.currentTargetCell = targetCell;
            this.tagsInCluster = c;
        } else {
            this.targetFound = false;
            this.tagsInCluster = 0;
        }
    }
}
