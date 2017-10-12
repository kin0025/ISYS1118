package main.entities;

import main.entities.intersection.Intersection;
import main.utils.BoundingBox;
import main.utils.Position;
import main.utils.enums.CardinalDirection;
import main.utils.enums.Orientation;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RoadSegmentTest {
    private RoadSegment roadSegment;

    @Before
    public void setUp() throws Exception {
        roadSegment = new RoadSegment(Orientation.HORIZONTAL, new BoundingBox(new Position(0, 0), 20, 100));
    }

    @Test
    public void addIntersection() throws Exception {
        Intersection intersection = new Intersection(new Position(0, 0), 10, 10, Orientation.HORIZONTAL);
        roadSegment.addIntersection(intersection, CardinalDirection.NORTH);
        assertEquals("Intersection not added correctly", CardinalDirection.SOUTH, roadSegment.getIntersectionDirection(intersection));
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
