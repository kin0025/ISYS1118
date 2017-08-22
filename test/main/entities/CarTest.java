package main.entities;

import main.utils.Direction;
import main.utils.Position;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

public class CarTest {
    private Car car;
    private Position carPosition;
    private Direction carDirection;
    private LinkedList<Lane> pathLane;
    private LinkedList<Intersection> pathIntersection;

    @Before
    public void setUp() throws Exception {
        carPosition = new Position(1, 1);
        carDirection = new Direction(Direction.COMPASS_DIRECTION.NORTH);
        //car = new Car(carPosition, carDirection, pathLane, pathIntersection);
    }

    @After
    public void tearDown() throws Exception {
        carDirection = null;
        car = null;
        carPosition = null;
    }

    @Test
    public void accelerate() throws Exception {
        car.accelerate();
    }

    @Test
    public void stop() throws Exception {
    }

    @Test
    public void incrementTime() throws Exception {
    }

    @Test
    public void turnLeft() {
    }

    @Test
    public void turnRight() throws Exception {
    }

    @Test
    public void getDirection() throws Exception {
    }

    @Test
    public void getSpeed() throws Exception {
    }

    @Test
    public void getPosition() throws Exception {
    }

    @Test
    public void isAccelerating() throws Exception {
    }

}