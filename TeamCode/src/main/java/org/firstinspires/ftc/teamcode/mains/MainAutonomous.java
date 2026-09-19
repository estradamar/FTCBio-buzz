package org.firstinspires.ftc.teamcode.mains;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;

@Autonomous(name = "MainAutonomous", group = "Autonomous")
public class MainAutonomous extends LinearOpMode {

    private MecanumDrive drive;
    private Limelight3A limelight;

    @Override
    public void runOpMode() {
        drive = new MecanumDrive(hardwareMap);
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        limelight.pipelineSwitch(0);
        limelight.start();

        telemetry.addData("Status", "Autónomo Listo");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            // Paso 1: Avanzar durante 1.5 segundos
            drive.driveMecanum(0.4, 0, 0);
            sleep(1500);

            // Detenerse brevemente
            drive.driveMecanum(0, 0, 0);
            sleep(500);

            // Paso 2: Buscar AprilTag y alinearse (Máximo 5 segundos de intento)
            long startTime = System.currentTimeMillis();
            boolean aligned = false;

            while (opModeIsActive() && !aligned && (System.currentTimeMillis() - startTime < 5000)) {
                LLResult result = limelight.getLatestResult();

                if (result != null && result.isValid()) {
                    double tx = result.getTx();
                    aligned = drive.alignToTarget(tx);
                    telemetry.addData("Alineando a AprilTag", "tx: " + tx);
                } else {
                    // Girar lentamente para buscar el objetivo
                    drive.driveMecanum(0, 0, 0.15);
                    telemetry.addData("Buscando", "Girando...");
                }
                telemetry.update();
            }

            // Detener el robot al finalizar
            drive.driveMecanum(0, 0, 0);
            telemetry.addData("Status", "Autónomo Completado");
            telemetry.update();
        }
    }
}
