package main.entities.intersection;

import main.utils.*;

public class TrafficLight {

	static final int amberLightTiming = 5;
	private Orientation orientation;
	private int greenLightTiming;
	private int time;

	public TrafficLight(int greenLightTiming, Orientation orientationEnum) {
		this.greenLightTiming = greenLightTiming;
		this.orientation = orientationEnum;
		time = greenLightTiming + amberLightTiming + 1;
	}
	// time on how long green light stays on

	public void incrementTime() {
		this.time++;
	}

	public boolean restartCycle() {
		if(time >= (greenLightTiming + amberLightTiming)){
			time = 0;
			return true;
		}else{
			return false;
		}
	}

	/*
	 * public char getStatus() 
	 * { if (time <= greenLightTiming) { return 'g'; }
	 * else if (time <= (greenLightTiming + timeToAmber)) 
	 * { return 'a'; }
	 * else {
	 * return 'r'; } }
	 */

	public LightStatus getStatus() {
		 if (time <= greenLightTiming)
			 return LightStatus.GREEN;
			 else if (time <= (greenLightTiming + amberLightTiming))
			 return LightStatus.AMBER;
			 else 
			 return LightStatus.RED;
	}
	
	public int checkTiming() {
		return greenLightTiming;
	}
	public Orientation getOrientation() {
		return orientation;
	}
	
}
