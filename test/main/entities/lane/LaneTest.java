/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.entities.lane;

import main.entities.MapGrid;
import main.entities.car.Car;
import main.utils.DimensionManager;
import main.utils.Position;
import main.utils.enums.CardinalDirection;
import main.utils.enums.TurnDirection;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LaneTest {
    private Lane lane;
    private Lane nextLane;

    private Car currentCar;
    private Car nextCar;

    @Before
    public void setUp() throws Exception {
        MapGrid mapGrid = new MapGrid(5, 5);
        mapGrid.generateStandardGrid();

        lane = mapGrid.getRoad(mapGrid.getIntersection(1, 1), CardinalDirection.NORTH).getSpawnLane(CardinalDirection.SOUTH);

        nextLane = mapGrid.getRoad(mapGrid.getIntersection(1, 1), mapGrid.getIntersection(1, 2)).getLane(CardinalDirection.SOUTH, TurnDirection
                .STRAIGHT);

        do {
            mapGrid.incrementTime();
            currentCar = mapGrid.getRoad(mapGrid.getIntersection(1, 1), CardinalDirection.NORTH).getSpawnLane(CardinalDirection.SOUTH).getCars()
                    .peek();
        } while (currentCar == null);

        do {
            mapGrid.incrementTime();
            nextCar = mapGrid.getRoad(mapGrid.getIntersection(1, 1), CardinalDirection.NORTH).getSpawnLane(CardinalDirection.SOUTH).getCars()
                    .peekLast();
        } while (nextCar == currentCar && nextCar != null);

    }

    @Test
    public void checkInitialCars() {
        assertEquals("Cars in lane incorrect initial.", currentCar, lane.getCars().peek());
        assertEquals("Cars in lane incorrect initial.", true, lane.getCars().contains(currentCar));
        assertEquals("Cars in lane incorrect initial.", nextCar, lane.getCars().peekLast());
        assertEquals("Cars in lane incorrect initial.", true, lane.getCars().contains(nextCar));
        assertEquals("Cars in lane 2 incorrect", null, nextLane.getCars().peek());
    }

    @Test
    public void addCar() {
        Car car1 = new Car(new Position(0, 0), null);
        lane.addCar(car1);
        assertEquals("Cars in lane incorrect car 1.", car1, lane.getCars().peekLast());
    }

    @Test
    public void moveCar() {
        lane.moveCar(nextLane,currentCar);
        assertEquals("Cars in lane1 incorrect after move", false, lane.getCars().contains(currentCar));
        assertEquals("Cars in lane1 incorrect after move", true, lane.getCars().contains(nextCar));

        assertEquals("Cars in lane2 incorrect after move", true, nextLane.getCars().contains(currentCar));
        assertEquals("Cars in lane2 incorrect after move", false, nextLane.getCars().contains(nextCar));
    }

    @Test
    public void moveMultipleCarMultipleTimes() {
        lane.moveCar(nextLane,currentCar);
        //And now they should both be moved to the same lane
        lane.moveCar(nextLane,nextCar);
        assertEquals("Cars in lane1 incorrect after move", false, lane.getCars().contains(currentCar));
        assertEquals("Cars in lane1 incorrect after move", false, lane.getCars().contains(nextCar));
        assertEquals("Cars in lane2 incorrect after move", true, nextLane.getCars().contains(currentCar));
        assertEquals("Cars in lane2 incorrect after move", true, nextLane.getCars().contains(nextCar));
    }

    @Test
    public void removeCar() throws Exception {
        assertEquals("Removing car failed", true, lane.removeCar(currentCar));
        assertEquals("Car was not removed from lane correctly", false, lane.getCars().contains(currentCar));
        assertEquals("Car was not removed from lane correctly", true, lane.getCars().contains(nextCar));

    }

    @Test
    public void removeMultipleCar() throws Exception {
        assertEquals("Removing car failed", true, lane.removeCar(currentCar));
        assertEquals("Removing car failed", true, lane.removeCar(nextCar));
        assertEquals("Car was not removed from lane correctly", false, lane.getCars().contains(currentCar));
        assertEquals("Car was not removed from lane correctly", false, lane.getCars().contains(nextCar));
    }

    @Test
    public void removeMultipleCarWrongOrder() throws Exception {
        assertEquals("Removing car failed", false, lane.removeCar(nextCar));
        assertEquals("Removing car failed", true, lane.removeCar(currentCar));
        assertEquals("Car was not removed from lane correctly", false, lane.getCars().contains(currentCar));
        assertEquals("Car was not removed from lane correctly", true, lane.getCars().contains(nextCar));
    }

    @Test
    public void getDirection() throws Exception {
        assertEquals("Wrong lane direction", CardinalDirection.SOUTH, lane.getDirection());
    }

    @Test
    public void carsGoBackwards() throws Exception {
        lane.getDirection();
        fail("Not yet implemented");
    }

    @Test
    public void carsGoForwards() throws Exception {
        lane.getDirection();
        fail("Not yet implemented");
    }


    @Test
    public void hasTurnDirection() throws Exception {
        assertEquals("Spawn Lane has wrong turn directions", true, lane.hasTurnDirection(TurnDirection.STRAIGHT));
        assertEquals("Spawn Lane has wrong turn directions", true, lane.hasTurnDirection(TurnDirection.RIGHT));
        assertEquals("Spawn Lane has wrong turn directions", true, lane.hasTurnDirection(TurnDirection.LEFT));
        assertEquals("Spawn Lane has wrong turn directions", false, lane.hasTurnDirection(TurnDirection.REVERSE));

        assertEquals("Lane has wrong turn directions", true, nextLane.hasTurnDirection(TurnDirection.STRAIGHT));
        assertEquals("Lane has wrong turn directions", false, nextLane.hasTurnDirection(TurnDirection.RIGHT));
        assertEquals("Lane has wrong turn directions", true, nextLane.hasTurnDirection(TurnDirection.LEFT));
        assertEquals("Lane has wrong turn directions", false, nextLane.hasTurnDirection(TurnDirection.REVERSE));
    }

    @Test
    public void checkCollisionsOccur() {
        Car car1 = new Car(new Position(0, 0), null);
        Car car2 = new Car(new Position(0, 0), null);
        lane.addCar(car1);
        lane.addCar(car2);
        assertEquals(false, lane.checkCarCollisions());
    }

    @Test
    public void checkCollisionsNoOccur() {
        Car car1 = new Car(new Position(0, 0), null);
        Car car2 = new Car(new Position(0, 100), null);
        lane.addCar(car1);
        lane.addCar(car2);
        assertEquals(true, lane.checkCarCollisions());
    }

    @Test
    public void checkCollisionsBorderline() {
        Car car1 = new Car(new Position(0, 0), null);
        Car car2 = new Car(new Position(0, DimensionManager.minimumFollowingDistancePixels), null);
        lane.addCar(car1);
        lane.addCar(car2);
        assertEquals(true, lane.checkCarCollisions());
    }


}