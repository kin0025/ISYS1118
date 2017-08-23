/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.entities.intersection;

import main.entities.interfaces.SimulationTimed;
import main.utils.Orientation;

public class TrafficLight implements SimulationTimed {
    static final int amberLightTiming = 5;
    Orientation orientation;
    int greenLightTiming;
    int timeToAmber;

    public void incrementTime() {
    }

    public boolean restartCycle() {
        return false;
    } // Can only be run if red
    // - sets light to green and counts down

    public  STATUS getStatus() {
        return STATUS.RED;
    }// returns r,y,g (red,yellow,green)

    public enum STATUS{RED,YELLOW,GREEN}
}
