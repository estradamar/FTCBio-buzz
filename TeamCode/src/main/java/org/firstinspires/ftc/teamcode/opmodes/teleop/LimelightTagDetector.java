package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.IndicatorLights1;

@TeleOp(name = "Limelight Tag Detector", group = "Main")
public class MainTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // 1. Inicializamos ambos subsistemas
        Limelight limelight = new Limelight(hardwareMap);
        IndicatorLights1 indicatorLights = new IndicatorLights1(hardwareMap);

        telemetry.addData("Estado", "Inicializado - Esperando inicio");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            
            // 2. Le pedimos a la Limelight que actualice su visión
            limelight.updateDashboard(); 
            
            // 3. Verificamos si ve el objetivo y prendemos la luz acorde
            if (limelight.hasTarget()) {
                indicatorLights.setRed();
                telemetry.addData("AprilTag", "¡Detectado! Luz en ROJO.");
            } else {
                indicatorLights.setOff();
                telemetry.addData("AprilTag", "No detectado. Luz apagada.");
            }

            // Aquí puedes agregar el update de tu chasis
            // chasis.drive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

            telemetry.update();
        }
        
        // Detenemos la cámara al terminar
        limelight.stop();
    }
}