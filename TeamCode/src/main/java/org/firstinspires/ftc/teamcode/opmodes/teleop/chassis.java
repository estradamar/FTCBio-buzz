package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.RobotHardware;

public class Chassis {

    private final RobotHardware robot;

    public Chassis(RobotHardware robot) {
        this.robot = robot;
    }

    public void drive(double forward, double strafe, double turn) {
        // Fórmula ajustada para coincidir con la configuración física del robot.
        double frontLeftPower = forward + strafe + turn;
        double frontRightPower = forward - strafe - turn;
        double backLeftPower = forward - strafe + turn;
        double backRightPower = forward + strafe - turn;

        // Normalización y asignación de potencia
        double max = Math.max(Math.abs(frontLeftPower), Math.abs(backLeftPower));
        max = Math.max(max, Math.abs(frontRightPower));
        max = Math.max(max, Math.abs(backRightPower));

        if (max > 1.0) {
            frontLeftPower /= max;
            backLeftPower /= max;
            frontRightPower /= max;
            backRightPower /= max;
        }

        robot.frontLeft.setPower(frontLeftPower);
        robot.backLeft.setPower(backLeftPower);
        robot.frontRight.setPower(frontRightPower);
        robot.backRight.setPower(backRightPower);
    }

    /**
     * Detiene los motores suavemente en un periodo de 500ms para evitar desgaste.
     * Requiere el LinearOpMode para no bloquearse si el match termina abruptamente.
     */
    public void stop(LinearOpMode opMode) {
        // 1. Obtenemos la potencia actual de cada motor al momento de llamar a stop()
        double startFL = robot.frontLeft.getPower();
        double startBL = robot.backLeft.getPower();
        double startFR = robot.frontRight.getPower();
        double startBR = robot.backRight.getPower();

        // 2. Iniciamos un temporizador
        com.qualcomm.robotcore.util.ElapsedTime timer = new com.qualcomm.robotcore.util.ElapsedTime();
        double rampTime = 0.5; // 500 milisegundos expresados en segundos

        // 3. Ciclo de desaceleración (Ramping)
        // Se ejecuta mientras el OpMode esté activo y no hayan pasado los 500ms
        while (opMode != null && opMode.opModeIsActive() && timer.seconds() < rampTime) {

            // Calculamos la proporción restante (Va de 1.0 bajando hasta 0.0)
            double proportion = 1.0 - (timer.seconds() / rampTime);

            // Aplicamos la potencia reducida gradualmente
            robot.frontLeft.setPower(startFL * proportion);
            robot.backLeft.setPower(startBL * proportion);
            robot.frontRight.setPower(startFR * proportion);
            robot.backRight.setPower(startBR * proportion);
        }

        // 4. Paro total de seguridad al finalizar el tiempo
        stop();
    }

    /**
     * Sobrecarga de compatibilidad: Mantiene la versión original por si se
     * necesita un paro de emergencia (abrupto) o no se tiene acceso al opMode.
     */
    public void stop() {
        robot.frontLeft.setPower(0);
        robot.backLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.backRight.setPower(0);
    }
}
