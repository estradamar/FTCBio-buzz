/*package org.firstinspires.ftc.teamcode.mains;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Limelight;

@TeleOp(name = "MainRobotAll", group = "TeleOp")
public class MainAll extends LinearOpMode {

    // 1. Declaración de las instancias de tus otros códigos/subsistemas
    private Limelight limelight;

    @Override
    public void runOpMode() {

        // 2. Inicialización de componentes (al presionar "INIT" en el Driver Station)
        telemetry.addData("Estado", "Inicializando...");
        telemetry.update();

        // Conectar la clase Limelight pasando el hardwareMap
        limelight = new Limelight(hardwareMap);

        telemetry.addData("Estado", "Listo para iniciar");
        telemetry.update();

        // Esperar a que el driver presione el botón "START"
        waitForStart();

        // 3. Bucle principal de ejecución
        while (opModeIsActive()) {

            // Actualizar lecturas de la cámara/visión
            limelight.updateDashboard();

            // Ejemplo de lectura de la visión
            if (limelight.hasTarget()) {
                telemetry.addData("AprilTag Detectado ID", limelight.hasTarget());
            } else {
                telemetry.addData("AprilTag", "No detectado");
            }

            // Actualizar la pantalla de la Driver Station
            telemetry.update();
        }

    }
}
*/