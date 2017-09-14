package main.entities.intersection;
import static org.junit.Assert.*;

import main.entities.Road;
import main.utils.*;

import main.utils.enums.CardinalDirection;
import main.utils.enums.Orientation;
import org.junit.Test;
import org.junit.Before;

public class IntersectionTest {
	
	private Intersection intersection;
    
	@Before
    public void createIntersection() throws Exception{
		 intersection = new Intersection(new Position(0,0),10,10,Orientation.HORIZONTAL);
    }

   
    
    @Test
    public void getRoadDirection(){
    	Direction roadDirection = new Direction(CardinalDirection.NORTH);
    	Road road = new Road(Orientation.VERTICAL,new BoundingBox(new Position(0,0),DimensionManager.widthOfRoadPixels,DimensionManager.lengthOfRoadPixels));
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
