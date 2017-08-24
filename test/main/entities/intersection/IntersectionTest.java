package main.entities.intersection;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

public class IntersectionTest {
	
	private Car car;
    private Direction roadDirection;
    private ArrayList<Intersection> intersection;
    
    
	@Before
    public void createIntersection() throws Exception{
		 roadDirections.put(1, North);
		 roadDirections.put(2, East);
		 roadDirections.put(3, South);
		 roadDirections.put(4, West );
    }

    @After
    public void closeIntersection() {
    	roadDirections.<Road, Direction>emptyMap();
    }
    
    @Test
    public void getRoadDirection(){
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
