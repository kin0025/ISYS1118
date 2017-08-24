/*
 * ISYS1118: Assignment.
 * Group: Null
 * File created by: Alexander Kinross-Smith, s3603437
 */

package main.entities.intersection;

import main.utils.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import main.entities.intersection.*;

public class TrafficLightTest {
    TrafficLight trafficLight;

    @Before
    public void setUp() throws Exception {
        trafficLight = new TrafficLight(30, Orientation.ENUM.HORIZONTAL);
    }

    @Test
    public void restartCycle() throws Exception {
        assertEquals("Default Traffic Light State incorrect", STATUS.RED, trafficLight.getStatus());
        trafficLight.restartCycle();
        assertEquals("Reset Traffic Light State incorrect", STATUS.GREEN, trafficLight.getStatus());
        for (int i = 0; i <= 29; i++) {
            trafficLight.incrementTime();
            assertEquals("Reset Traffic Light time incorrect", STATUS.GREEN, trafficLight.getStatus());
        }
        for (int i = 0; i <= 4; i++) {
            trafficLight.incrementTime();
            assertEquals("Reset Traffic Light amber time incorrect", STATUS.AMBER, trafficLight.getStatus());
        }
        trafficLight.incrementTime();
        assertEquals("Reset Traffic Light red time incorrect", STATUS.RED, trafficLight.getStatus());
    }

    @Test
    public void getStatus() throws Exception {
    }

    @Test
    public void checkTiming() {

    }

}