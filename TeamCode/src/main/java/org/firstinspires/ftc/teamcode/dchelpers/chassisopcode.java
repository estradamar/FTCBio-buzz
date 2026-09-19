/*package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="chassis op", group="Linear OpMode")
public class chassisop extends LinearOpMode {

    // Declarar los 4 motores del chasis
    private DcMotor leftFront = null;
    private DcMotor leftRear = null;
    private DcMotor rightFront = null;
    private DcMotor rightRear = null;

    @Override
    public void runOpMode() {

        // 1. Inicializar los motores con los nombres dados en el Driver Station
        leftFront  = hardwareMap.get(DcMotor.class, "left_front");
        leftRear   = hardwareMap.get(DcMotor.class, "left_rear");
        rightFront = hardwareMap.get(DcMotor.class, "right_front");
        rightRear  = hardwareMap.get(DcMotor.class, "right_rear");

        // 2. Configurar la dirección de giro (Invertir el lado derecho suele ser necesario)
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        leftRear.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightRear.setDirection(DcMotor.Direction.REVERSE);

        // Mensaje de preparación
        telemetry.addData("Estado", "Inicialización Completa");
        telemetry.update();

        waitForStart();

        // Bucle principal del OpMode
        while (opModeIsActive()) {

            // Leer los joysticks (Invertimos el eje Y porque hacia arriba da valores negativos)
            double powerLeft = -gamepad1.left_stick_y;
            double powerRight = -gamepad1.right_stick_y;

            // Asignar la misma potencia a los motores de un mismo lado
            leftFront.setPower(powerLeft);
            leftRear.setPower(powerLeft);

            rightFront.setPower(powerRight);
            rightRear.setPower(powerRight);

            // Mostrar información en pantalla del Driver Station
            telemetry.addData("Potencia Izquierda", "%.2f", powerLeft);
            telemetry.addData("Potencia Derecha", "%.2f", powerRight);
            telemetry.update();
        }
    }
}

 */

//*/