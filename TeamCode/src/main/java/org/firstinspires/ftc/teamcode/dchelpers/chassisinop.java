/*package org.firstinspires.ftc.teamcode.subsystems;

public class chassisinop {

    // Voltajes actuales que se envían a los motores (rango típico: -12.0 a 12.0 V)
    private double leftMotorVoltage  = 0.0;
    private double rightMotorVoltage = 0.0;

    // Límites de seguridad
    private static final double MAX_VOLTAGE = 12.0;
    private static final double MIN_VOLTAGE = -12.0;


    public void setLeftMotorVoltage(double voltage) {
        this.leftMotorVoltage = saturate(voltage);
    }


    public void setRightMotorVoltage(double voltage) {
        this.rightMotorVoltage = saturate(voltage);
    }

    /**
     * Establece ambos voltajes al mismo tiempo (útil para control diferencial).
     */
   /* public void setVoltages(double leftVoltage, double rightVoltage) {
        setLeftMotorVoltage(leftVoltage);
        setRightMotorVoltage(rightVoltage);
    }


    public void stop() {
        setVoltages(0.0, 0.0);
    }

    // ========== Getters ==========

    public double getLeftMotorVoltage() {
        return leftMotorVoltage;
    }

    public double getRightMotorVoltage() {
        return rightMotorVoltage;
    }

    public double[] getVoltages() {
        return new double[]{leftMotorVoltage, rightMotorVoltage};
    }

    // ========== Utilidades muy privadas ==========

    private double saturate(double voltage) {
        if (voltage > MAX_VOLTAGE) return MAX_VOLTAGE;
        if (voltage < MIN_VOLTAGE) return MIN_VOLTAGE;
        return voltage;
    }

    @Override
    public String toString() {
        return String.format("chassisinop{left=%.2f V, right=%.2f V}",
                leftMotorVoltage, rightMotorVoltage);
    }


}
*/
