package main.entities.intersection;
import static org.junit.Assert.*;

import main.entities.Road;
import main.utils.*;

import main.utils.enums.CardinalDirection;
import main.utils.enums.Orientation;
import org.junit.Test;
import org.junit.Before;

public class IntersectionTest {
    @Test
    public void incrementTime() throws Exception {
    }

    @Test
    public void getBoundingBox() throws Exception {
    }

    @Test
    public void getDirection() throws Exception {
    }

    @Test
    public void getRoads() throws Exception {
    }

    @Test
    public void getCars() throws Exception {
    }

    @Test
    public void getLightStatus() throws Exception {
    }

    @Test
    public void getRoadDirection1() throws Exception {
    }

    @Test
    public void addRoad1() throws Exception {
    }

    @Test
    public void setLightTiming() throws Exception {
    }

    @Test
    public void addCar1() throws Exception {
    }

    @Test
    public void removeRoads() throws Exception {
    }

    @Test
    public void removeCar1() throws Exception {
    }

    @Test
    public void moveCar() throws Exception {
    }

    @Test
    public void isInsideBoundingBox() throws Exception {
    }

    @Test
    public void getCentre() throws Exception {
    }

    private Intersection intersection;
    
	@Before
    public void createIntersection() throws Exception{
		 intersection = new Intersection(new Position(0,0),10,10,Orientation.HORIZONTAL);
    }

   
    
    @Test
    public void getRoadDirection(){
    	CardinalDirection roadDirection = CardinalDirection.NORTH;
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
