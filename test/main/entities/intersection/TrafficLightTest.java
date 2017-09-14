/*
 * ISYS1118: Assignment.
 * Group: Null
 * File created by: Alexander Kinross-Smith, s3603437
 */

package main.entities.intersection;

import main.utils.DimensionManager;
import main.utils.enums.Orientation;
import main.utils.enums.LightStatus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TrafficLightTest {

	private TrafficLight trafficLight;

	@Before
	public void setUp() throws Exception {
		trafficLight = new TrafficLight(30, Orientation.HORIZONTAL);
	}

	@Test
	public void testDefaultLight() throws Exception {
		assertEquals("Default Traffic Light State incorrect", LightStatus.RED, trafficLight.getStatus());
	}

	@Test
	public void testStartGreen() throws Exception {
		trafficLight.restartCycle();
		assertEquals("Reset Traffic Light State incorrect", LightStatus.GREEN, trafficLight.getStatus());
	}

	@Test
	public void testTrafficTime() throws Exception {
		trafficLight.restartCycle();
		for (int i = 0; i < 30; i++) {
			trafficLight.incrementTime();
		}
		assertEquals("Traffic Light time incorrect", LightStatus.GREEN, trafficLight.getStatus());
	}

	@Test
	public void testAmberLight() throws Exception {
		trafficLight.restartCycle();
		for (int i = 0; i <= 30; i++) {
			trafficLight.incrementTime();
		}
		assertEquals("Traffic Light amber time incorrect", LightStatus.AMBER, trafficLight.getStatus());
	}

	@Test
	public void testRedLight() throws Exception {
		trafficLight.restartCycle();
		for (int i = 0; i <= (30+DimensionManager.amberLightTimeOn); i++) {
			trafficLight.incrementTime();
		}
		assertEquals("Traffic Light red time incorrect", LightStatus.RED, trafficLight.getStatus());
	}

	@Test
	public void checkTiming() {
		assertEquals("Does not equal", 30, trafficLight.checkTiming());
	}
}

