/*package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

public class limelight {

    private final Limelight3A limelightSensor;
    private LLResult latestResult;

    /**
     * Constructor del subsistema de Limelight.
     * @param hardwareMap Mapa de hardware proveniente del OpMode.
     */
  /*  public limelight(HardwareMap hardwareMap) {
        this(hardwareMap, "limelight");
    }

    /**
     * Constructor del subsistema de Limelight con nombre personalizado.
     * @param hardwareMap Mapa de hardware proveniente del OpMode.
     * @param deviceName Nombre configurado en el Control Hub (ej. "limelight").
     */
   /* public limelight(HardwareMap hardwareMap, String deviceName) {
        limelightSensor = hardwareMap.get(Limelight3A.class, deviceName);

        // Seleccionar Pipeline 0 por defecto (debe estar configurado como AprilTag)
        limelightSensor.pipelineSwitch(0);
        limelightSensor.start();
    }

    /**
     * Actualiza las lecturas de la cámara. Debe llamarse en cada ciclo del loop.
     */
  /*  public void update() {
        latestResult = limelightSensor.getLatestResult();
    }

    /**
     * Actualiza la información y muestra datos básicos en telemetría.
     * Método invocado por los OpModes principales.
     */
  /*  public void updateDashboard() {
        update();
    }

    /**
     * Verifica si la cámara detecta algún objetivo válido.
     */
 /*   public boolean hasTarget() {
        if (latestResult == null) update();
        return latestResult != null && latestResult.isValid();
    }

    /**
     * Devuelve la desviación horizontal (tx) respecto al objetivo en grados.
     */
  /*  public double getTx() {
        if (hasTarget()) {
            return latestResult.getTx();
        }
        return 0.0;
    }

    /**
     * Devuelve la desviación vertical (ty) respecto al objetivo en grados.
     */
    /*public double getTy() {
        if (hasTarget()) {
            return latestResult.getTy();
        }
        return 0.0;
    }

    /**
     * Devuelve el área ocupada por el objetivo en la imagen (% de la pantalla).
     */
    /*public double getTa() {
        if (hasTarget()) {
            return latestResult.getTa();
        }
        return 0.0;
    }

    /**
     * Obtiene el ID del primer AprilTag detectado.
     * @return El ID del AprilTag, o -1 si no hay ninguno visible.
     */
   /* public int getTargetId() {
        if (hasTarget()) {
            List<LLResultTypes.FiducialResult> fiducials = latestResult.getFiducialResults();
            if (!fiducials.isEmpty()) {
                return fiducials.get(0).getFiducialId();
            }
        }
        return -1;
    }

    /**
     * Obtiene la posición 3D estimada del robot respecto al campo (Botpose).
     * Requiere que el mapa del campo esté configurado en la web de Limelight.
     */
   /* public Pose3D getBotpose() {
        if (hasTarget()) {
            return latestResult.getBotpose();
        }
        return null;
    }

    /**
     * Cambia el pipeline activo en la cámara.
     * @param pipelineIndex Índice del pipeline (0 a 9).
     */
  /*  public void setPipeline(int pipelineIndex) {
        limelightSensor.pipelineSwitch(pipelineIndex);
    }

    /**
     * Detiene la cámara.
     */
   /* public void stop() {
        limelightSensor.stop();
    }
}
*/