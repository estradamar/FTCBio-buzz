package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.subsystems.AutonomousDriveControll;

@Autonomous(name = "Test: Autónomo Básico por CM", group = "Tests")
public class AutoDriveTest extends LinearOpMode {

    private RobotHardware robot;
    private AutonomousDriveControll autoDrive;

    @Override
    public void runOpMode() {
        // Inicializamos hardware
        robot = new RobotHardware(hardwareMap);
        
        // Pasamos el LinearOpMode actual (this) para que autoDrive cheque opModeIsActive()
        autoDrive = new AutonomousDriveControll(robot, this);

        telemetry.addData("Estado", "Inicializado y listo para correr Autónomo.");
        telemetry.update();

        // Esperar a que el piloto presione PLAY
        waitForStart();

        if (opModeIsActive()) {
            
            // Ejemplo de ruta básica:
            
            // 1. Avanzar 50 cm a 0.5 de potencia, con timeout de 3 segundos
            telemetry.addData("Paso", "1: Avanzar 50cm");
            telemetry.update();
            autoDrive.driveForward(50, 0.5, 3.0);
            
            sleep(500); // Pequeña pausa entre movimientos para estabilidad

            // 2. Strafe a la derecha 30 cm a 0.5 de potencia, timeout 2 segundos
            telemetry.addData("Paso", "2: Strafe Derecha 30cm");
            telemetry.update();
            autoDrive.strafeRight(30, 0.5, 2.0);
            
            sleep(500);
            
            // 3. Retroceder 50 cm a 0.5 de potencia
            telemetry.addData("Paso", "3: Retroceder 50cm");
            telemetry.update();
            autoDrive.driveForward(-50, 0.5, 3.0); // Nota: distancia negativa = atrás
            
            sleep(500);

            // 4. Strafe a la izquierda 30 cm
            telemetry.addData("Paso", "4: Strafe Izquierda 30cm");
            telemetry.update();
            autoDrive.strafeRight(-30, 0.5, 2.0); // Nota: distancia negativa = izquierda

            telemetry.addData("Estado", "Autónomo Terminado");
            telemetry.update();
        }
    }
}
