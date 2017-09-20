package main.utils;

import main.entities.car.Car;
import main.entities.lane.CarDestroy;
import main.utils.enums.CardinalDirection;
import main.utils.enums.TurnDirection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CardinalDirectionTest {

    @Before
    public void setUp()  {
    }

    @After
    public void tearDown() {
    }


    @Test
    public void getDirectionVector() {

        int[] vector = {0, 1};
        assertArrayEquals("Get CardinalDirection Vector Failed", vector, CardinalDirection.NORTH.getDirectionVector());
    }

    @Test
    public void getTurnDirection()  {
        CardinalDirection north =CardinalDirection.NORTH;
        CardinalDirection south =CardinalDirection.SOUTH;
        CardinalDirection east =CardinalDirection.EAST;
        CardinalDirection west =CardinalDirection.WEST;

        assertEquals("Turn CardinalDirection from N to E failed", TurnDirection.RIGHT, north.getTurnDirection(east));
        assertEquals("Turn CardinalDirection from N to N failed", TurnDirection.STRAIGHT, north.getTurnDirection(north));
        assertEquals("Turn CardinalDirection from N to W failed", TurnDirection.LEFT, north.getTurnDirection(west));
        assertEquals("Turn CardinalDirection from N to S failed", TurnDirection.REVERSE, north.getTurnDirection(south));

        assertEquals("Turn CardinalDirection from S to E failed", TurnDirection.LEFT, south.getTurnDirection(east));
        assertEquals("Turn CardinalDirection from S to S failed", TurnDirection.STRAIGHT, south.getTurnDirection(south));
        assertEquals("Turn CardinalDirection from S to W failed", TurnDirection.RIGHT, south.getTurnDirection(west));
        assertEquals("Turn CardinalDirection from S to N failed", TurnDirection.REVERSE, south.getTurnDirection(north));

        assertEquals("Turn CardinalDirection from E to N failed", TurnDirection.LEFT, east.getTurnDirection(north));
        assertEquals("Turn CardinalDirection from E to S failed", TurnDirection.RIGHT, east.getTurnDirection(south));
        assertEquals("Turn CardinalDirection from E to E failed", TurnDirection.STRAIGHT, east.getTurnDirection(east));
        assertEquals("Turn CardinalDirection from E to W failed", TurnDirection.REVERSE, east.getTurnDirection(west));

        assertEquals("Turn CardinalDirection from W to W failed", TurnDirection.STRAIGHT, west.getTurnDirection(west));
        assertEquals("Turn CardinalDirection from W to E failed", TurnDirection.REVERSE, west.getTurnDirection(east));
        assertEquals("Turn CardinalDirection from W to N failed", TurnDirection.RIGHT, west.getTurnDirection(north));
        assertEquals("Turn CardinalDirection from W to S failed", TurnDirection.LEFT, west.getTurnDirection(south));

    }

}