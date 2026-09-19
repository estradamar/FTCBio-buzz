package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Control_Robot_BIOBUZZ", group = "Linear OpMode")
public class controlchassis2 extends LinearOpMode {

    // Declaración de Motores del Chasís Mecanum
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;

    // Declaración de Actuadores (Mecanismos del reto)
    private DcMotor intakeMotor = null;
    private Servo clawServo = null;

    // Posiciones del Servo para Abrir / Cerrar
    private static final double CLAW_OPEN = 0.5;
    private static final double CLAW_CLOSED = 0.0;

    @Override
    public void runOpMode() {

        // 1. Inicialización de Hardware desde el HardwareMap
        frontLeft  = hardwareMap.get(DcMotor.class, "front_left");
        frontRight = hardwareMap.get(DcMotor.class, "front_right");
        backLeft   = hardwareMap.get(DcMotor.class, "back_left");
        backRight  = hardwareMap.get(DcMotor.class, "back_right");

        intakeMotor = hardwareMap.get(DcMotor.class, "intake_motor");
        clawServo   = hardwareMap.get(Servo.class, "claw_servo");

        // 2. Invertir la dirección de los motores de un lado para alinearlos correctamente
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        // Opcional: Establecer frenado automático al soltar controles
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Estado", "Inicializado - Listo para BIOBUZZ");
        telemetry.update();

        // Esperar a que el conductor presione PLAY en la Driver Station
        waitForStart();

        // Bucle principal de ejecución durante la fase de control manual
        while (opModeIsActive()) {

            // --- A. CONTROL DEL CHASÍS MECANUM (Gamepad 1) ---
            // Leemos los valores del joystick izquierdo y derecho
            double y = -gamepad1.left_stick_y; // Avance / Reversa (invertido por convención de joysticks)
            double x = gamepad1.left_stick_x * 1.1; // Desplazamiento lateral (Strafe)
            double rx = gamepad1.right_stick_x; // Rotación sobre su propio eje

            // Cálculo de potencia para cada rueda en un chasis Mecanum
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.0);
            double frontLeftPower  = (y + x + rx) / denominator;
            double backLeftPower   = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower  = (y + x - rx) / denominator;

            // Enviar potencia a los motores del chasis
            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);


            // --- B. CONTROL DE MECANISMOS / INTAKE (Gamepad 1 o 2) ---
            // Control con Triggers/Gatillos para subir o bajar mecanismo
            if (gamepad1.right_trigger > 0.1) {
                intakeMotor.setPower(gamepad1.right_trigger); // Elevar
            } else if (gamepad1.left_trigger > 0.1) {
                intakeMotor.setPower(-gamepad1.left_trigger); // Bajar
            } else {
                intakeMotor.setPower(0); // Detener
            }

            // Control de Garra / Actuador con Botones A y B
            if (gamepad1.a) {
                clawServo.setPosition(CLAW_CLOSED); // Cerrar garra
            } else if (gamepad1.b) {
                clawServo.setPosition(CLAW_OPEN);   // Abrir garra
            }

            // --- C. TELEMETRÍA DE MONITOREO ---
            telemetry.addData("Chasís FL / FR", "%.2f | %.2f", frontLeftPower, frontRightPower);
            telemetry.addData("Chasís BL / BR", "%.2f | %.2f", backLeftPower, backRightPower);
            telemetry.addData("Servo Garra", clawServo.getPosition());
            telemetry.update();
        }
    }
}
