package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Autonomous(name = "Odometria BIOBUZZ Test", group = "Autonomous")
@SuppressWarnings("unused")
class OdometriaBiobuzzTest extends LinearOpMode {

    GoBildaPinpointDriver pinpoint;

    @Override
    public void runOpMode() {
        // Inicializar el hardware del Pinpoint
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

        // Configurar la orientación de los encoders y las podas (según el montaje de tu chasis)
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_PINPOINT);
        pinpoint.setOffsets(-84.0, 150.0); // Ejemplo de offsets en mm (ajustar a las medidas reales de tu robot)
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.FORWARD);

        // Resetear posición inicial en la cancha (X: 0, Y: 0, Heading: 0)
        pinpoint.resetPosAndImu();

        telemetry.addData("Estado", "Listo para BIOBUZZ. Esperando inicio...");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Actualizar lecturas del sensor
            pinpoint.update();

            // Obtener la posición actual (Pose)
            Pose2D currentPos = pinpoint.getPosition();

            if (currentPos != null) {
                double x = currentPos.getX(DistanceUnit.CM);
                double y = currentPos.getY(DistanceUnit.CM);
                double heading = currentPos.getHeading(AngleUnit.DEGREES);

                // Mostrar telemetría en la Driver Station
                telemetry.addData("Posición X (cm)", "%.2f", x);
                telemetry.addData("Posición Y (cm)", "%.2f", y);
                telemetry.addData("Orientación (Grados)", "%.2f", heading);
            } else {
                telemetry.addData("Pinpoint", "Position is null");
            }
            telemetry.update();
        }
    }

    public static class GoBildaPinpointDriver {
        public enum GoBildaOdometryPods {
            goBILDA_4_PINPOINT,
            goBILDA_ANRY_4_PINPOINT
        }

        public enum EncoderDirection {
            FORWARD,
            REVERSED
        }

        @SuppressWarnings("unused")
        public void setEncoderResolution(GoBildaOdometryPods resolution) {
        }

        @SuppressWarnings("unused")
        public void setOffsets(double xOffset, double yOffset) {
        }

        @SuppressWarnings("unused")
        public void setEncoderDirections(EncoderDirection xDirection, EncoderDirection yDirection) {
        }

        public Pose2D getPosition() {
            return null;
        }

        public void update() {
        }

        public void resetPosAndImu() {
        }
    }
}
