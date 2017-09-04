package main.entities.intersection;
import static org.junit.Assert.*;

import main.entities.Road;
import main.utils.CardinalDirection;
import main.utils.Direction;
import main.utils.Orientation;
import main.utils.Position;

import org.junit.Test;
import org.junit.Before;

public class IntersectionTest {
	
	Intersection intersection;
    
	@Before
    public void createIntersection() throws Exception{
		 intersection = new Intersection(new Position(0,0));
    }

   
    
    @Test
    public void getRoadDirection(){
    	Direction roadDirection = new Direction(CardinalDirection.NORTH);
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
