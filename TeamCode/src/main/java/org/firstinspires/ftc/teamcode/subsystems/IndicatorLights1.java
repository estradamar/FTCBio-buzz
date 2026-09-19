package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IndicatorLights1 {
    
    private final Servo rgbLight;

    // Valores PWM (posiciones de servo) correspondientes a cada color.
    // **NOTA IMPORTANTE:** Tendrás que ajustar estos valores (entre 0.0 y 1.0) 
    // según el manual de tu luz goBILDA para que coincidan con los colores reales.
    public static final double RED = 0.279; // ¡Cambiar según manual!
    public static final double OFF = 0.0;   // ¡Cambiar según manual!

    /**
     * Constructor del subsistema de Luces
     * @param hardwareMap Mapa de hardware proveniente del OpMode.
     */
    public IndicatorLights1(HardwareMap hardwareMap) {
        this(hardwareMap, "rgb_light"); 
    }

    /**
     * Constructor con nombre personalizado por si usas más de una luz
     */
    public IndicatorLights1(HardwareMap hardwareMap, String deviceName) {
        // En tu Control Hub, la luz debe estar configurada en un puerto de Servo
        rgbLight = hardwareMap.get(Servo.class, deviceName);
    }

    /**
     * Cambia la luz a un valor específico
     */
    public void setColor(double colorPosition) {
        rgbLight.setPosition(colorPosition);
    }
    
    /**
     * Enciende la luz en Rojo
     */
    public void setRed() {
        setColor(RED);
    }
    
    /**
     * Apaga la luz (o la pone en color neutral)
     */
    public void setOff() {
        setColor(OFF);
    }
}