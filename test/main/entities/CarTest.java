package main.entities;

import main.entities.interfaces.CarMoveable;
import main.utils.Direction;
import main.utils.Position;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

public class CarTest {
    private Car car;
    private Position carPosition;
    private Direction carDirection;
    private ArrayList<CarMoveable> carPath;

    @Before
    public void setUp() throws Exception{
        carPosition = new Position(1, 1);
        carDirection = new Direction(Direction.COMPASS_DIRECTION.NORTH);
        //car = new Car(carPosition, carDirection, pathLane, pathIntersection);
    }

    @After
    public void tearDown() {
        carDirection = null;
        car = null;
        carPosition = null;
    }

    @Test
    public void accelerate() {
        car.accelerate();

    }

    @Test
    public void stop() {
    }

    @Test
    public void incrementTime() {
    }

    @Test
    public void turnLeft() {
    }

    @Test
    public void turnRight() {
    }

    @Test
    public void getDirection() {
    }

    @Test
    public void getSpeed() {
    }

    @Test
    public void getPosition() {
    }

    @Test
    public void isAccelerating() {
    }

}