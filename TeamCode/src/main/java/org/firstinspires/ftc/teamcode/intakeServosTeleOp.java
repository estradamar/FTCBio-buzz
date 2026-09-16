package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Intake-test", group = "TeleOp")
public class intakeServosTeleOp extends LinearOpMode {

    // Declaración de los componentes de hardware
    private DcMotor motorIntake;
    private Servo servoIzquierdo;
    private Servo servoDerecho;

    // Constantes para las posiciones de los servos
    final double SERVO_POS_ABIERTO = 0.0;
    final double SERVO_POS_CERRADO = 1.0;

    // Potencia del motor cuando esté encendido
    final double MOTOR_POTENCIA = 1.0;

    @Override
    public void runOpMode() {
        // 1. Mapeo del hardware (los nombres entre comillas deben coincidir con la configuración de la Drive Station)
        motorIntake    = hardwareMap.get(DcMotor.class, "intake");
        servoIzquierdo = hardwareMap.get(Servo.class, "servo_izq");
        servoDerecho   = hardwareMap.get(Servo.class, "servo_der");

        // 2. Configuración de direcciones
        // Si tus servos están frente a frente, uno tiene que girar al revés para hacer el mismo movimiento mecánico.
        // Descomenta la línea de abajo si notas que un servo se abre y el otro se cierra al presionar el botón.

        // servoDerecho.setDirection(Servo.Direction.REVERSE);

        telemetry.addData("Estado", "Inicializado - Listo para arrancar");
        telemetry.update();

        // Espera a que el driver presione PLAY
        waitForStart();

        // Bucle principal de TeleOp
        while (opModeIsActive()) {

            if (gamepad1.left_bumper) {
                // --- ACCIÓN CON LB: Succión / Intake Normal ---
                motorIntake.setPower(MOTOR_POTENCIA); // Giro hacia adelante
                servoIzquierdo.setPosition(SERVO_POS_CERRADO);
                servoDerecho.setPosition(SERVO_POS_CERRADO);

            } else if (gamepad1.right_bumper) {
                // --- ACCIÓN CON RB: Expulsión / Reversa ---
                motorIntake.setPower(-MOTOR_POTENCIA); // Giro invertido (potencia negativa)
                servoIzquierdo.setPosition(SERVO_POS_CERRADO); // Los servos se mantienen cerrados/activos para presionar la pieza hacia afuera
                servoDerecho.setPosition(SERVO_POS_CERRADO);

            } else {
                // --- ACCIÓN POR DEFECTO: Estado de reposo ---
                motorIntake.setPower(0.0); // Motor apagado
                servoIzquierdo.setPosition(SERVO_POS_ABIERTO);
                servoDerecho.setPosition(SERVO_POS_ABIERTO);
            }

            // Telemetría en tiempo real
            telemetry.addData("Succionando (LB)", gamepad1.left_bumper);
            telemetry.addData("Expulsando (RB)", gamepad1.right_bumper);
            telemetry.addData("No-Intakea", motorIntake.getPower());
            telemetry.update();
        }
    }
}