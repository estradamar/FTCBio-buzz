package org.firstinspires.ftc.teamcode.opmodes.autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.vision.limelight;

@Autonomous(name = "Autonomo Competencia FTC", group = "Autonomous")
public class autonomo extends LinearOpMode {

    private MecanumDrive drive;
    private limelight limelight;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        // 1. Inicialización de Componentes oficiales de FTC
        telemetry.addData("Status", "Inicializando Subsistemas...");
        telemetry.update();

        drive = new MecanumDrive(hardwareMap);
        limelight = new limelight(hardwareMap);

        telemetry.addData("Status", "Listo para la Competencia - Esperando Inicio");
        telemetry.update();

        // Esperar a que comiencen los 30 segundos oficiales del periodo Autónomo
        waitForStart();
        runtime.reset();

        // 2. Ejecución durante los 30 segundos del periodo autónomo
        if (opModeIsActive()) {
            
            // PASO 1: Avanzar hacia adelante para salir de la zona de inicio (1.5 segundos)
            telemetry.addData("Autónomo", "Paso 1: Avanzando hacia adelante");
            telemetry.update();
            
            drive.driveMecanum(0.3, 0.0, 0.0); // Avanzar a potencia moderada (Y, X, RX)
            sleep(1500); // Detener el hilo por 1500 milisegundos

            // Detener el chasis momentáneamente
            drive.driveMecanum(0.0, 0.0, 0.0);
            sleep(500);

            // PASO 2: Usar Limelight para buscar un AprilTag y alinearse (Máximo hasta los 25 segundos)
            telemetry.addData("Autónomo", "Paso 2: Buscando y alineando con AprilTag");
            telemetry.update();

            while (opModeIsActive() && runtime.seconds() < 25.0) {
                // Actualizar la lectura de la Limelight en cada ciclo
                limelight.update();

                if (limelight.hasTarget()) {
                    double tx = limelight.getTx();
                    // Intentar alinearse automáticamente con el error horizontal obtenido
                    boolean aligned = drive.alignToTarget(tx);
                    
                    if (aligned) {
                        telemetry.addData("Visión", "¡Alineación Completa con el Objetivo!");
                        drive.driveMecanum(0.0, 0.0, 0.0);
                    } else {
                        telemetry.addData("Visión", "Ajustando ángulo. Error tx: %.2f", tx);
                    }
                } else {
                    // Si no ve ningún tag, realiza un giro de búsqueda muy lento y seguro
                    drive.driveMecanum(0.0, 0.0, 0.1);
                    telemetry.addData("Visión", "Buscando AprilTag visible...");
                }
                
                telemetry.addData("Tiempo Restante", "%.1f s", 30.0 - runtime.seconds());
                telemetry.update();
            }

            // PASO 3: Apagado de seguridad absoluto antes de cumplir los 30 segundos
            drive.driveMecanum(0.0, 0.0, 0.0);
            limelight.stop();
            telemetry.addData("Autónomo", "Completado con éxito y estacionado de forma segura");
            telemetry.update();
        }
    }
}
