package main.entities.intersection;

import main.utils.*;

public class TrafficLight {

	private Orientation orientation;
	private int greenLightTiming;
	private int time;
	private static int amberLightTimeOn = DimensionManager.amberLightTimeOn;

	public TrafficLight(int greenLightTiming, Orientation orientationEnum) {
		this.greenLightTiming = greenLightTiming;
		this.orientation = orientationEnum;
		time = greenLightTiming + amberLightTimeOn + 1;
	}
	// time on how long green light stays on

	public void incrementTime() {
		this.time++;
	}

	public boolean restartCycle() {
		if(time >= (greenLightTiming + amberLightTimeOn)){
			time = 0;
			return true;
		}else{
			return false;
		}
	}

	public LightStatus getStatus() {
		 if (time <= greenLightTiming)
			 return LightStatus.GREEN;
			 else if (time <= (greenLightTiming + amberLightTimeOn))
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

	public void setTiming(int greenLightTimeOn){
	    this.greenLightTiming =  greenLightTiming;
    }
}
