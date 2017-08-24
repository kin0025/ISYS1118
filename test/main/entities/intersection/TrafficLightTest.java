/*
 * ISYS1118: Assignment.
 * Group: Null
 * File created by: Alexander Kinross-Smith, s3603437
 */

package main.entities.intersection;

import main.utils.Orientation;
import main.utils.STATUS;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TrafficLightTest {
    TrafficLight trafficLight;

    @Before
    public void setUp() throws Exception {
        trafficLight = new TrafficLight(30, Orientation.ENUM.HORIZONTAL);
    }

    @Test
    public void trafficCycle() throws Exception {
        assertEquals("Default Traffic Light State incorrect", STATUS.RED, trafficLight.getStatus());
        trafficLight.restartCycle();
        assertEquals("Reset Traffic Light State incorrect", STATUS.GREEN, trafficLight.getStatus());
        for (int i = 0; i <= 29; i++) {
            trafficLight.incrementTime();
            assertEquals("Traffic Light time incorrect", STATUS.GREEN, trafficLight.getStatus());
        }
        for (int i = 0; i <= 4; i++) {
            trafficLight.incrementTime();
            assertEquals("Traffic Light amber time incorrect", STATUS.AMBER, trafficLight.getStatus());
        }
        trafficLight.incrementTime();
        assertEquals("Traffic Light red time incorrect", STATUS.RED, trafficLight.getStatus());
    }

    @Test
    public void checkTiming() {
        assertEquals("Does not equal", 30, trafficLight.checkTiming());
    }
}
    
