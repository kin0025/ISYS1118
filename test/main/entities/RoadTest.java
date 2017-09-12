package main.entities;

import main.entities.intersection.Intersection;
import main.utils.CardinalDirection;
import main.utils.Direction;
import main.utils.Orientation;
import main.utils.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RoadTest {
    Road road;

    @Before
    public void setUp() throws Exception {
        road = new Road(Orientation.HORIZONTAL, new Position(0, 0));
    }

    @Test
    public void addIntersection() throws Exception {
        Intersection intersection = new Intersection(new Position(0, 0));
        Direction direction = new Direction(CardinalDirection.NORTH);
        road.addIntersection(intersection, direction);
        assertEquals("Intersection not added correctly", direction, road.getIntersectionDirection(intersection));
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
