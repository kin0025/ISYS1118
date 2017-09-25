package main.entities.car;

import main.entities.car.Car;
import main.entities.car.CarPath;
import main.utils.*;
import main.utils.enums.CardinalDirection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarTest {
    private Car car;
    private Position carPosition;
    private CarPath carPath;

    @Before
    public void setUp() throws Exception {
        carPosition = new Position(1, 1);

        car = new Car(carPosition, carPath);
    }

    @After
    public void tearDown() {
        CardinalDirection carDirection = null;
        car = null;
        carPosition = null;
    }

    @Test
    public void accelerate() {
        car.start();
        assertEquals("Car doesn't start", DimensionManager.kmphToPixelTick(50), car.getSpeed(), 0.05);

    }

    @Test
    public void stopping() {
        //Get to max speed and stay there
        car.start();
        assertEquals("Car max speed wrong", DimensionManager.kmphToPixelTick(50), car.getSpeed(), 0.05);
        car.stop();
        assertEquals("Car decelerates below zero", 0, car.getSpeed(), 0.05);
    }

    @Test
    public void checkMovement() {
        car.start();
        car.incrementTime();
        assertEquals("Car not moving correctly", 1 + DimensionManager.kmphToPixelTick(50), car.getPosition().getY(), 0.05);
        assertEquals("Car not moving correctly", 1, car.getPosition().getX(), 0.05);
    }

    @Test
    public void isMoving() {
        car.start();
        assertEquals(true, car.isMoving());
        car.stop();
        assertEquals(false, car.isMoving());
    }

}