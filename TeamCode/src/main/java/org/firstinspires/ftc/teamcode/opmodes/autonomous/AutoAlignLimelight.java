package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.vision.HiveVisionConfig;
import org.firstinspires.ftc.teamcode.vision.LimelightHiveTracker;

@Autonomous(name="Auto Align Limelight", group="Pruebas")
public class AutoAlignLimelight extends LinearOpMode {

    private RobotHardware robot;
    private Chassis chassis;
    private Limelight3A limelight;
    private LimelightHiveTracker tracker;

    // Constantes de Alineación (Ajustables)
    private static final double TARGET_Z_M = 0.45; // Distancia óptima desde la celda
    private static final double P_GAIN_X = 0.8;
    private static final double P_GAIN_Z = 0.8;
    private static final double P_GAIN_YAW = 0.02;

    @Override
    public void runOpMode() {
        robot = new RobotHardware(hardwareMap);
        chassis = new Chassis(robot);
        
        try {
            limelight = hardwareMap.get(Limelight3A.class, "limelight");
            limelight.pipelineSwitch(0); // Asegurar que está en el pipeline de AprilTags
            limelight.start();
        } catch(Exception e) {
            telemetry.addLine("Limelight no encontrada en HardwareMap.");
        }

        tracker = new LimelightHiveTracker();

        // En este ejemplo buscaremos la celda roja de Scoring
        HiveVisionConfig.HiveCell targetCell = HiveVisionConfig.HiveCell.RED_SCORING;

        telemetry.addData("Status", "Iniciando Limelight tracker...");
        telemetry.addData("Target Cell", targetCell.name());
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (limelight != null) {
                tracker.update(limelight, targetCell);
            }

            double drive = 0;
            double strafe = 0;
            double turn = 0;

            if (tracker.targetFound) {
                // Cálculo de Errores
                double errorX = tracker.targetX; 
                double errorZ = tracker.targetZ - TARGET_Z_M;
                double errorYaw = tracker.targetYaw; // Asumiendo 0 es estar perpendicular a la pared

                // Control Proporcional Simple (P-Controller)
                strafe = errorX * P_GAIN_X;
                drive  = errorZ * P_GAIN_Z;
                turn   = errorYaw * P_GAIN_YAW;

                telemetry.addData("Estado", "Alineando...");
                telemetry.addData("Tags Vistos", tracker.tagsInCluster);
                telemetry.addData("Error X (Strafe)", "%.3f m", errorX);
                telemetry.addData("Error Z (Avance)", "%.3f m", errorZ);
                telemetry.addData("Error Yaw", "%.1f deg", errorYaw);
            } else {
                telemetry.addData("Estado", "Buscando objetivo %s...", targetCell.name());
                // Si no ve nada, se detiene
                drive = 0;
                strafe = 0;
                turn = 0;
            }

            // Aplicar cinemática del chasis Mecanum
            chassis.drive(drive, strafe, turn);
            
            telemetry.update();
        }

        if (limelight != null) {
            limelight.stop();
        }
    }
}
