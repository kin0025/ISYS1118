package main.entities;

import main.entities.interfaces.CarMoveable;
import main.entities.lane.Lane;
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
        carPath.add(new Lane());
        carPath.add(new Lane());
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
        for (int i = 0; i < 20; i++) {
            car.incrementTime();
            assertEquals("Car accelerates wrong", DimensionManager.metersToPixels(2) * i, car.getSpeed(), 0.05);
        }
        //Get to max speed and stay there
        for (int i = 0; i < 200; i++) {
            car.incrementTime();
        }
        assertEquals("Car max speed wrong", DimensionManager.metersToPixels(13.9), car.getSpeed(), 0.05);

    }

    @Test
    public void stopping() {
        fail("Not yet implemented");

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