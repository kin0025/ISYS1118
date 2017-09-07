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

public class CarTest {
    private Car car;
    private Position carPosition;
    private Direction carDirection;
    private ArrayList<CarMoveable> carPath;

    @Before
    public void setUp() throws Exception {
        carPosition = new Position(1, 1);
        ArrayList<CarMoveable> carPath = new ArrayList<>();
        carPath.add(new Lane(new Direction(CardinalDirection.NORTH), new ArrayList<>(), 0, new Position(0, 0)));
        carPath.add(new Lane(new Direction(CardinalDirection.NORTH), new ArrayList<>(), 0, new Position(0, 0)));

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
        assertEquals("Car doesn't accelerate", DimensionManager.kmphToPixelTick(50), car.getSpeed(), 0.05);

    }

    @Test
    public void stopping() {
        //Get to max speed and stay there
        car.accelerate();
        assertEquals("Car max speed wrong", DimensionManager.kmphToPixelTick(50), car.getSpeed(), 0.05);
        car.stop();
        assertEquals("Car decelerates below zero", 0, car.getSpeed(), 0.05);
    }

    @Test
    public void checkMovement() {
        car.accelerate();
        car.incrementTime();
        assertEquals("Car not moving correctly", 1 + DimensionManager.kmphToPixelTick(50), car.getPosition().getY(), 0.05);
        assertEquals("Car not moving correctly", 1, car.getPosition().getX(), 0.05);
    }

    @Test
    public void isMoving() {
        car.accelerate();
        assertEquals(true, car.isMoving());
        car.stop();
        assertEquals(false, car.isMoving());
    }

}