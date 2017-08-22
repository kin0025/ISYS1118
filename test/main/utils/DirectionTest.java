package main.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DirectionTest {
    private Direction direction;

    @Before
    public void setUp()  {
        direction = new Direction(Direction.COMPASS_DIRECTION.NORTH);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void turnRight() {
        direction.turnRight();
        assertEquals("Turn from N to E failed", Direction.COMPASS_DIRECTION.EAST, direction.getDirection());
        direction.turnRight();
        assertEquals("Turn from E to S failed", Direction.COMPASS_DIRECTION.SOUTH, direction.getDirection());
        direction.turnRight();
        assertEquals("Turn from S to W failed", Direction.COMPASS_DIRECTION.WEST, direction.getDirection());
        direction.turnRight();
        assertEquals("Turn from W to N failed", Direction.COMPASS_DIRECTION.NORTH, direction.getDirection());
    }

    @Test
    public void turnLeft() {
        direction.turnLeft();
        assertEquals("Turn from N to W failed", Direction.COMPASS_DIRECTION.WEST, direction.getDirection());
        direction.turnLeft();
        assertEquals("Turn from W to S failed", Direction.COMPASS_DIRECTION.SOUTH, direction.getDirection());
        direction.turnLeft();
        assertEquals("Turn from S to E failed", Direction.COMPASS_DIRECTION.EAST, direction.getDirection());
        direction.turnLeft();
        assertEquals("Turn from E to N failed", Direction.COMPASS_DIRECTION.NORTH, direction.getDirection());

    }

    @Test
    public void getDirection() {
        assertEquals("Get Direction Failed", Direction.COMPASS_DIRECTION.NORTH, direction.getDirection());

    }

    @Test
    public void getDirectionVector() {
        int[] vector = {1, 0};
        assertArrayEquals("Get Direction Vector Failed", vector, direction.getDirectionVector());

    }

    @Test
    public void getTurnDirection()  {
        Direction north = new Direction(Direction.COMPASS_DIRECTION.NORTH);
        Direction south = new Direction(Direction.COMPASS_DIRECTION.SOUTH);
        Direction east = new Direction(Direction.COMPASS_DIRECTION.EAST);
        Direction west = new Direction(Direction.COMPASS_DIRECTION.WEST);

        assertEquals("Turn direction from N to E failed", Direction.TURN_DIRECTION.RIGHT, north.getTurnDirection(east));
        assertEquals("Turn direction from N to N failed", Direction.TURN_DIRECTION.STRAIGHT, north.getTurnDirection(north));
        assertEquals("Turn direction from N to W failed", Direction.TURN_DIRECTION.LEFT, north.getTurnDirection(west));
        assertEquals("Turn direction from N to S failed", Direction.TURN_DIRECTION.REVERSE, north.getTurnDirection(south));

        assertEquals("Turn direction from S to E failed", Direction.TURN_DIRECTION.LEFT, south.getTurnDirection(east));
        assertEquals("Turn direction from S to S failed", Direction.TURN_DIRECTION.STRAIGHT, south.getTurnDirection(south));
        assertEquals("Turn direction from S to W failed", Direction.TURN_DIRECTION.RIGHT, south.getTurnDirection(west));
        assertEquals("Turn direction from S to N failed", Direction.TURN_DIRECTION.REVERSE, south.getTurnDirection(north));

        assertEquals("Turn direction from E to N failed", Direction.TURN_DIRECTION.LEFT, east.getTurnDirection(north));
        assertEquals("Turn direction from E to S failed", Direction.TURN_DIRECTION.RIGHT, east.getTurnDirection(south));
        assertEquals("Turn direction from E to E failed", Direction.TURN_DIRECTION.STRAIGHT, east.getTurnDirection(east));
        assertEquals("Turn direction from E to W failed", Direction.TURN_DIRECTION.REVERSE, east.getTurnDirection(west));

        assertEquals("Turn direction from W to W failed", Direction.TURN_DIRECTION.STRAIGHT, west.getTurnDirection(west));
        assertEquals("Turn direction from W to E failed", Direction.TURN_DIRECTION.REVERSE, west.getTurnDirection(east));
        assertEquals("Turn direction from W to N failed", Direction.TURN_DIRECTION.RIGHT, west.getTurnDirection(north));
        assertEquals("Turn direction from W to S failed", Direction.TURN_DIRECTION.LEFT, west.getTurnDirection(south));

    }

}