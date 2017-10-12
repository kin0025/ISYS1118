package main.entities.car;

import main.entities.MapGrid;
import main.utils.DimensionManager;
import main.utils.enums.CardinalDirection;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarTest {
    private Car car;

    @Before
    public void setUp() throws Exception {
        MapGrid mapGrid = new MapGrid(5,5);
        mapGrid.generateStandardGrid();
        do {
            mapGrid.incrementTime();
            car = mapGrid.getRoad(mapGrid.getIntersection(1, 1), CardinalDirection.NORTH).getSpawnLane(CardinalDirection.SOUTH).getCars().peek();
        }while (car == null);
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
        double x = car.getPosition().getX();
        double y = car.getPosition().getY();
        car.incrementTime();
        assertEquals("Car not moving correctly", y + car.getSpeed(), car.getPosition().getY(), 0.05);
        assertEquals("Car not moving correctly", x, car.getPosition().getX(), 0.05);
    }

    @Test
    public void isMoving() {
        car.start();
        assertEquals(true, car.isMoving());
        car.stop();
        assertEquals(false, car.isMoving());
    }

}