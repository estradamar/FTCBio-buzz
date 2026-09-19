package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "AutoAlineacion_DeLimelight", group = "TeleOp")
public class AutoAlineacion_DeLimelight extends LinearOpMode {

    private Limelight3A limelight;
    private DcMotor leftFront, rightFront, leftBack, rightBack;

    // Ganancias del controlador Proporcional (Ajustar según comportamiento)
    private final double Kp_TURN = 0.03;  // Sensibilidad de giro
    private final double Kp_DRIVE = 0.05; // Sensibilidad de avance

    // Desplazamiento objetivo de distancia (ejemplo basado en el área del AprilTag 'ta')
    private final double TARGET_AREA = 3.5;

    @Override
    public void runOpMode() throws InterruptedException {
        // Inicialización de hardware
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        leftFront  = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack   = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack  = hardwareMap.get(DcMotor.class, "rightBack");

        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);

        limelight.pipelineSwitch(0); // Cargar Pipeline de AprilTags
        limelight.start();

        telemetry.addData("Estado", "Listo");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            LLResult result = limelight.getLatestResult();

            // Botón A para activar alineación automática
            if (gamepad1.a && result != null && result.isValid() && !result.getFiducialResults().isEmpty()) {
                LLResultTypes.FiducialResult fr = result.getFiducialResults().get(0);
                int id = fr.getFiducialId();

                // Verificar si detecta uno de los 4 IDs válidos (ejemplo: IDs 1, 2, 3, 4)
                if (id >= 1 && id <= 4) {
                    double tx = result.getTx(); // Error horizontal en grados (-29 a 29)
                    double ta = result.getTa(); // Área que ocupa el tag en pantalla

                    double turn = tx * Kp_TURN;
                    double drive = (TARGET_AREA - ta) * Kp_DRIVE;

                    // Limitar potencia máxima por seguridad
                    turn = Range.clip(turn, -0.5, 0.5);
                    drive = Range.clip(drive, -0.4, 0.4);

                    // Aplicar potencia al chasis Mecanum
                    double frontLeftPower  = drive + turn;
                    double frontRightPower = drive - turn;
                    double backLeftPower   = drive + turn;
                    double backRightPower  = drive - turn;

                    setMotorPowers(frontLeftPower, frontRightPower, backLeftPower, backRightPower);

                    telemetry.addData("Alineando a ID", id);
                    telemetry.addData("Error Ángulo (tx)", tx);
                } else {
                    stopMotors();
                }
            } else {
                // Control manual del teleoperado cuando no se presiona 'A'
                double drive  = -gamepad1.left_stick_y;
                double strafe =  gamepad1.left_stick_x;
                double turn   =  gamepad1.right_stick_x;

                setMotorPowers(
                        drive + strafe + turn,
                        drive - strafe - turn,
                        drive - strafe + turn,
                        drive + strafe - turn
                );
            }

            telemetry.update();
        }
    }

    private void setMotorPowers(double fl, double fr, double bl, double br) {
        leftFront.setPower(fl);
        rightFront.setPower(fr);
        leftBack.setPower(bl);
        rightBack.setPower(br);
    }

    private void stopMotors() {
        setMotorPowers(0, 0, 0, 0);
    }
}