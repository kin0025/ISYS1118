package main.entities;

import main.entities.interfaces.CarMoveable;
import main.entities.lane.Lane;
import main.utils.CardinalDirection;
import main.utils.DimensionManager;
import main.utils.Direction;
import main.utils.Position;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CarTest {
    private Car car;
    private Position carPosition;
    private Direction carDirection;
    private ArrayList<CarMoveable> carPath;

    @Before
    public void setUp() throws Exception {
        carPosition = new Position(1, 1);
        ArrayList<CarMoveable> carPath = new ArrayList<>();
        carPath.add(new Lane(new Direction(CardinalDirection.NORTH), new ArrayList<>(), 0, new Position(0,0)));
        carPath.add(new Lane(new Direction(CardinalDirection.NORTH), new ArrayList<>(), 0, new Position(0,0)));

        car = new Car(carPosition, carPath);
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
        assertEquals("Car doesn't accelerate", DimensionManager.metersToPixels(13.9), car.getSpeed(), 0.05);

    }

    @Test
    public void stopping() {
        //Get to max speed and stay there
        car.accelerate();
        assertEquals("Car max speed wrong", DimensionManager.metersToPixels(13.9), car.getSpeed(), 0.05);
        car.stop();
        assertEquals("Car decelerates below zero", 0, car.getSpeed(), 0.05);
    }

    @Test
    public void checkMovement(){
        car.accelerate();
        car.incrementTime();
        //assertEquals("Car not moving correctly", , car.getPosition(), 0.05);
        fail("Not done");

    }
    @Test
    public void turnLeft() {
        fail("Not yet implemented");
    }

    @Test
    public void turnRight() {
        fail("Not yet implemented");

    }

    @Test
    public void getDirection() {
        fail("Not yet implemented");

    }
    @Test
    public void isAccelerating() {
        fail("Not yet implemented");

    }

}