package main.utils;

import main.utils.enums.CardinalDirection;
import main.utils.enums.TurnDirection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DirectionTest {
    private Direction direction;

    @Before
    public void setUp()  {
        direction = new Direction(CardinalDirection.NORTH);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void turnRight() {
        direction.turnRight();
        assertEquals("Turn from N to E failed", CardinalDirection.EAST, direction.getDirection());
        direction.turnRight();
        assertEquals("Turn from E to S failed", CardinalDirection.SOUTH, direction.getDirection());
        direction.turnRight();
        assertEquals("Turn from S to W failed", CardinalDirection.WEST, direction.getDirection());
        direction.turnRight();
        assertEquals("Turn from W to N failed", CardinalDirection.NORTH, direction.getDirection());
    }

    @Test
    public void turnLeft() {
        direction.turnLeft();
        assertEquals("Turn from N to W failed", CardinalDirection.WEST, direction.getDirection());
        direction.turnLeft();
        assertEquals("Turn from W to S failed", CardinalDirection.SOUTH, direction.getDirection());
        direction.turnLeft();
        assertEquals("Turn from S to E failed", CardinalDirection.EAST, direction.getDirection());
        direction.turnLeft();
        assertEquals("Turn from E to N failed", CardinalDirection.NORTH, direction.getDirection());

    }

    @Test
    public void getDirection() {
        assertEquals("Get Direction Failed", CardinalDirection.NORTH, direction.getDirection());

    }

    @Test
    public void getDirectionVector() {
        int[] vector = {0, 1};
        assertArrayEquals("Get Direction Vector Failed", vector, direction.getDirectionVector());
    }

    @Test
    public void getTurnDirection()  {
        Direction north = new Direction(CardinalDirection.NORTH);
        Direction south = new Direction(CardinalDirection.SOUTH);
        Direction east = new Direction(CardinalDirection.EAST);
        Direction west = new Direction(CardinalDirection.WEST);

        assertEquals("Turn direction from N to E failed", TurnDirection.RIGHT, north.getTurnDirection(east));
        assertEquals("Turn direction from N to N failed", TurnDirection.STRAIGHT, north.getTurnDirection(north));
        assertEquals("Turn direction from N to W failed", TurnDirection.LEFT, north.getTurnDirection(west));
        assertEquals("Turn direction from N to S failed", TurnDirection.REVERSE, north.getTurnDirection(south));

        assertEquals("Turn direction from S to E failed", TurnDirection.LEFT, south.getTurnDirection(east));
        assertEquals("Turn direction from S to S failed", TurnDirection.STRAIGHT, south.getTurnDirection(south));
        assertEquals("Turn direction from S to W failed", TurnDirection.RIGHT, south.getTurnDirection(west));
        assertEquals("Turn direction from S to N failed", TurnDirection.REVERSE, south.getTurnDirection(north));

        assertEquals("Turn direction from E to N failed", TurnDirection.LEFT, east.getTurnDirection(north));
        assertEquals("Turn direction from E to S failed", TurnDirection.RIGHT, east.getTurnDirection(south));
        assertEquals("Turn direction from E to E failed", TurnDirection.STRAIGHT, east.getTurnDirection(east));
        assertEquals("Turn direction from E to W failed", TurnDirection.REVERSE, east.getTurnDirection(west));

        assertEquals("Turn direction from W to W failed", TurnDirection.STRAIGHT, west.getTurnDirection(west));
        assertEquals("Turn direction from W to E failed", TurnDirection.REVERSE, west.getTurnDirection(east));
        assertEquals("Turn direction from W to N failed", TurnDirection.RIGHT, west.getTurnDirection(north));
        assertEquals("Turn direction from W to S failed", TurnDirection.LEFT, west.getTurnDirection(south));

    }

}