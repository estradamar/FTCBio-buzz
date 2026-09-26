package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotHardware;

public class AutonomousDriveControll {

    private RobotHardware robot;
    private LinearOpMode opMode;

    // --- CONSTANTES FÍSICAS ---
    // NOTA: Ajusta los Ticks/Rev según el motor que uses.
    // goBILDA 312 RPM (19.2:1) = 537.7
    // goBILDA 435 RPM (13.7:1) = 384.5
    public static final double TICKS_PER_REV = 537.7; 
    
    // Diámetro de la rueda Mecanum estándar de goBILDA (96 mm = 9.6 cm)
    public static final double WHEEL_DIAMETER_CM = 9.6;
    public static final double WHEEL_CIRCUMFERENCE_CM = Math.PI * WHEEL_DIAMETER_CM;
    
    public static final double TICKS_PER_CM = (TICKS_PER_REV) / WHEEL_CIRCUMFERENCE_CM;
    
    // Factor de fricción lateral (deslizamiento) para el Strafe
    // En mecanum, se necesita avanzar un poco más en lateral para cubrir la misma distancia física.
    public static final double STRAFE_FACTOR = 1.15; 

    /**
     * Constructor que recibe el hardware y el opMode actual para leer opModeIsActive() y usar telemetría.
     */
    public AutonomousDriveControll(RobotHardware robot, LinearOpMode opMode) {
        this.robot = robot;
        this.opMode = opMode;
    }

    /**
     * Mueve el robot hacia adelante (positivo) o hacia atrás (negativo).
     * @param distanceCm Distancia en centímetros.
     * @param power Potencia base (0.0 a 1.0).
     * @param timeoutS Tiempo máximo de seguridad antes de cancelar la orden (por si se atora).
     */
    public void driveForward(double distanceCm, double power, double timeoutS) {
        int targetTicks = (int) (distanceCm * TICKS_PER_CM);
        
        // En mecanum, para ir adelante/atrás las 4 ruedas van en la misma dirección.
        setTargetsAndMove(targetTicks, targetTicks, targetTicks, targetTicks, power, timeoutS);
    }

    /**
     * Hace strafe lateral a la derecha (positivo) o izquierda (negativo).
     * @param distanceCm Distancia en centímetros.
     * @param power Potencia base (0.0 a 1.0).
     * @param timeoutS Tiempo máximo de seguridad.
     */
    public void strafeRight(double distanceCm, double power, double timeoutS) {
        // En mecanum, el strafe derecho requiere:
        // FrontLeft (+), BackLeft (-)
        // FrontRight (-), BackRight (+)
        // Y aplicamos el STRAFE_FACTOR para compensar el deslizamiento de los rodillos a 45°.
        
        int targetTicks = (int) (distanceCm * TICKS_PER_CM * STRAFE_FACTOR);
        
        setTargetsAndMove(targetTicks, -targetTicks, -targetTicks, targetTicks, power, timeoutS);
    }

    /**
     * Método interno (Fase 1: Encoders). 
     * Cuando llegue el Pinpoint (Fase 2), este método se puede reescribir para leer coordenadas (X, Y)
     * ¡Y tus llamadas a driveForward() en el autónomo no tendrán que cambiar!
     */
    private void setTargetsAndMove(int flTicks, int blTicks, int frTicks, int brTicks, double power, double timeoutS) {
        // Reiniciar los encoders a cero (posición actual)
        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Establecer el target (la posición actual ya es cero, así que pasamos los ticks directos)
        robot.frontLeft.setTargetPosition(flTicks);
        robot.backLeft.setTargetPosition(blTicks);
        robot.frontRight.setTargetPosition(frTicks);
        robot.backRight.setTargetPosition(brTicks);

        // Cambiar modo a RUN_TO_POSITION
        robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Aplicar potencia absoluta
        power = Math.abs(power);
        robot.frontLeft.setPower(power);
        robot.backLeft.setPower(power);
        robot.frontRight.setPower(power);
        robot.backRight.setPower(power);

        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();

        // Bucle de bloqueo temporal: Esperar mientras:
        // 1. El OpMode siga vivo (no han apretado STOP)
        // 2. El tiempo no haya superado el timeout de seguridad
        // 3. Los motores aún no llegan a su destino (isBusy)
        while (opMode.opModeIsActive() && 
               (runtime.seconds() < timeoutS) &&
               (robot.frontLeft.isBusy() && robot.backLeft.isBusy() && 
                robot.frontRight.isBusy() && robot.backRight.isBusy())) {
            
            // Puedes imprimir la telemetría para depurar en vivo en el Driver Station
            opMode.telemetry.addData("Autónomo", "Moviendo a objetivo...");
            opMode.telemetry.addData("Ticks Target", "FL:%d BL:%d FR:%d BR:%d", flTicks, blTicks, frTicks, brTicks);
            opMode.telemetry.addData("Ticks Actual", "FL:%d BL:%d", 
                                     robot.frontLeft.getCurrentPosition(), 
                                     robot.backLeft.getCurrentPosition());
            opMode.telemetry.update();
        }

        // 1. Parar de aplicar potencia
        stopMotors();

        // 2. Regresar a modo de velocidad normal
        robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Detiene todos los motores del chasis.
     */
    public void stopMotors() {
        robot.frontLeft.setPower(0);
        robot.backLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.backRight.setPower(0);
    }
}
