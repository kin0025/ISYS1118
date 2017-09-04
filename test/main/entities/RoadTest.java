package main.entities;
import static org.junit.Assert.*;

import main.utils.CardinalDirection;
import org.junit.Test;

import main.entities.intersection.Intersection;
import main.utils.Direction;
import main.utils.Orientation;
import main.utils.Position;

public class RoadTest {
    Road road;
	@Test
	public void setUp() throws Exception {
		road = new Road(Orientation.HORIZONTAL, new Position(0,0));
	}
	
    @Test
    public void addIntersection() throws Exception {
    	Intersection intersection = new Intersection(new Position(0,0));
    	Direction direction = new Direction(CardinalDirection.NORTH);
    	road.addIntersection(intersection, direction);
    	assertEquals("Intersection not added correctly",direction,road.getIntersectionDirection(intersection));
    }

    @Test
    public void getPosition() throws Exception {
    }

    @Test
    public void getLanes() throws Exception {
    }

    @Test
    public void getOrientation() throws Exception {
    }

    @Test
    public void getIntersectionDirection() throws Exception {
    }
}
