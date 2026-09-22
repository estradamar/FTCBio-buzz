package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.RobotHardware;

public class IndicatorLights {
    
    private final RobotHardware robot;

    // Approximate PWM values (servo positions) for the goBILDA Indicator Light.
    // **IMPORTANT NOTE:** You might need to slightly tune these values (between 0.0 and 1.0) 
    // if the colors don't match exactly on your specific light.
    public static final double OFF = 0.0;
    public static final double RED = 0.279;
    public static final double ORANGE = 0.4;
    public static final double YELLOW = 0.55;
    public static final double GREEN = 0.7;
    public static final double BLUE = 0.85;
    public static final double PURPLE = 1.0;

    public IndicatorLights(RobotHardware robot) {
        this.robot = robot;
    }

    public void setColor(double colorPosition) {
        if (robot.rgbLight != null) {
            robot.rgbLight.setPosition(colorPosition);
        }
    }
    
    public void setRed() {
        setColor(RED);
    }

    public void setGreen() {
        setColor(GREEN);
    }
    
    public void setBlue() {
        setColor(BLUE);
    }
    
    public void setOff() {
        setColor(OFF);
    }
}