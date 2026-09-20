/*package org.firstinspires.ftc.teamcode.mains;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Limelight;
// Asegúrate de importar las rutas correctas de tus clases de chasis:
// import org.firstinspires.ftc.teamcode.chassis.ChassisOp;
// import org.firstinspires.ftc.teamcode.chassis.ChassisInOpVelocity;
// O el constructor que utilizan

@TeleOp(name = "MainRobotCodes", group = "TeleOp")
public class MainCodes extends LinearOpMode {

    // 1. Declaración de las instancias de tus subsistemas
    private Limelight limelight;

    // Inicializar los subsistemas del chasis (ajusta los nombres de las clases según tus constructores)
    // private ChassisOp chassisOp;

    /*
    {
        chassisOp = new ChassisOp(hardwareMap);

    }
    */



    // private ChassisVelocity chassisVelocity;

    //public MainCodes() {

        // chassisVelocity = new ChassisInOpVelocity(hardwareMap);

/*
    }

    @Override
    public void runOpMode() {

        // 2. Inicialización de componentes (al presionar "INIT" en el Driver Station)
        telemetry.addData("Estado", "Inicializando...");
        telemetry.update();

        // Conectar los componentes pasando el hardwareMap
        limelight = new Limelight(hardwareMap);

        telemetry.addData("Estado", "Listo para iniciar");
        telemetry.update();

        // Esperar a que el driver presione el botón "START"
        waitForStart();

        // 3. Bucle principal de ejecución
        while (opModeIsActive()) {

            // --- Actualización de Visión ---
            limelight.updateDashboard();

            if (limelight.hasTarget()) {
                telemetry.addData("AprilTag Detectado ID", limelight.hasTarget());
            } else {
                telemetry.addData("AprilTag", "No detectado");
            }

            // --- Control del Chasis ---
            // Aquí pasas los joysticks del control (gamepad1) para mover el robot.
            // Dependiendo de cómo hayas programado tus clases, los métodos pueden llamarse distinto (ej. drive, update, move, etc.)

            // Ejemplo usando ChassisOp (control manual estándar)
            // chassisOp.name(gamepad1);

            // Si necesitas integrar la velocidad o control específico de ChassisInOpVelocity:
            // chassisVelocity.update(gamepad1);

            // Mostrar telemetría combinada
            telemetry.addData("Chasis Status", "Activo");
            telemetry.update();
        }
    }
}
*/