/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.entities.intersection;

import main.entities.interfaces.SimulationTimed;
import main.utils.Orientation;

public class TrafficLight implements SimulationTimed {
    static final int amberLightTiming = 5;
    private Orientation orientation;
    private int greenLightTiming;
    private int timeToAmber;

    public TrafficLight(int greenLightTiming, Orientation.ENUM orientationEnum) {
        this.greenLightTiming = greenLightTiming;
        this.orientation = new Orientation(orientationEnum);
    }

    public void incrementTime() {
    }

    public boolean restartCycle() {
        return false;
    } // Can only be run if red
    // - sets light to green and counts down

    public STATUS getStatus() {
        return STATUS.RED;
    }// returns r,y,g (red,yellow,green)

    public enum STATUS{RED,YELLOW,GREEN}
}
