package main.utils;

import main.exceptions.IncorrectDirectionException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DirectionTest {

    private Direction direction;

    @Before
    public void setUp() throws IncorrectDirectionException{
        direction = new Direction(Direction.north);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void turnRight() {
        direction.turnRight();
        assertEquals("Turn from N to E failed", Direction.east, direction.getDirection());
        direction.turnRight();
        assertEquals("Turn from E to S failed", Direction.south, direction.getDirection());
        direction.turnRight();
        assertEquals("Turn from S to W failed", Direction.west, direction.getDirection());
        direction.turnRight();
        assertEquals("Turn from W to N failed", Direction.north, direction.getDirection());
    }

    @Test
    public void turnLeft() {
        direction.turnLeft();
        assertEquals("Turn from N to W failed", Direction.west, direction.getDirection());
        direction.turnLeft();
        assertEquals("Turn from W to S failed", Direction.south, direction.getDirection());
        direction.turnLeft();
        assertEquals("Turn from S to E failed", Direction.east, direction.getDirection());
        direction.turnLeft();
        assertEquals("Turn from E to N failed", Direction.north, direction.getDirection());

    }

    @Test
    public void getDirection() {
        assertEquals("Get Direction Failed", Direction.north, direction.getDirection());

    }

    @Test
    public void getDirectionVector() {
        int[] vector = {1, 0};
        assertArrayEquals("Get Direction Vector Failed", vector, direction.getDirectionVector());

    }

}