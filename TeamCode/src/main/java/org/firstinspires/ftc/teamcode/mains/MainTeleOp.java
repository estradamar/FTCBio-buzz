package org.firstinspires.ftc.teamcode.mains;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;

@TeleOp(name = "MainTeleOp", group = "TeleOp")
public class MainTeleOp extends LinearOpMode {

    private MecanumDrive drive;
    private Limelight3A limelight;

    @Override
    public void runOpMode() {
        drive = new MecanumDrive(hardwareMap);
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        // Seleccionar el pipeline de AprilTags configurado en la web de Limelight
        limelight.pipelineSwitch(0);
        limelight.start();

        telemetry.addData("Status", "Robot Inicializado");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            LLResult result = limelight.getLatestResult();

            // Alineación automática con el botón 'A' del Gamepad 1
            if (gamepad1.a && result != null && result.isValid()) {
                double tx = result.getTx();
                boolean aligned = drive.alignToTarget(tx);
                telemetry.addData("Alineando", aligned ? "¡ALINEADO!" : "Ajustando...");
                telemetry.addData("Error Horizontal (tx)", tx);
            } else {
                // Control Mecanum manual: Y (avance), X (lateral), Right Stick X (giro)
                double y = -gamepad1.left_stick_y;
                double x = gamepad1.left_stick_x * 1.1; // Factor de corrección para deslizamiento lateral
                double rx = gamepad1.right_stick_x;

                drive.driveMecanum(y, x, rx);
            }

            // Datos de la visión en la Driver Station
            if (result != null && result.isValid()) {
                telemetry.addData("Target ID", result.getFiducialResults().isEmpty() ?
                        "N/A" : result.getFiducialResults().get(0).getFiducialId());
            } else {
                telemetry.addData("Limelight", "Buscando AprilTag...");
            }

            telemetry.update();
        }
    }
}
