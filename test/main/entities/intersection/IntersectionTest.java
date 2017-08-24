package main.entities.intersection;
import static org.junit.Assert.*;

import java.util.HashMap;

import main.entities.Car;
import main.entities.Road;
import main.utils.Direction;
import main.utils.Orientation;
import main.utils.Position;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

public class IntersectionTest {
	
	Intersection intersection;
    
	@Before
    public void createIntersection() throws Exception{
		 intersection = new Intersection(new Position(0,0));
    }

   
    
    @Test
    public void getRoadDirection(){
    	Direction roadDirection = new Direction(Direction.COMPASS_DIRECTION.NORTH);
    	Road road = new Road(Orientation.VERTICAL,new Position(0,0)); 
		intersection.addRoad(road,roadDirection);
    	assertEquals("Couldn't find added road", roadDirection, intersection.getRoadDirection(road));
    }

    @Test
    public void addRoad() {
    }

    @Test
    public void addCar() {
    }
    
    @Test
    public void removeCar(){
    }
}
