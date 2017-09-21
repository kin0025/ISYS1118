/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.entities.lane;

import main.entities.car.Car;
import main.entities.interfaces.CarMovable;
import main.utils.BoundingBox;
import main.utils.DimensionManager;
import main.utils.Position;
import main.utils.enums.CardinalDirection;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class LaneTest {
    private Lane lane;

    @Before
    public void setUp() throws Exception {
        lane = new Lane(CardinalDirection.NORTH,new ArrayList<>(), 0, new BoundingBox(new Position(0, 40), 5, 100));
    }

    @Test
    public void addCar() {
        ArrayList<CarMovable> carList = new ArrayList<>();
        Car car1 = new Car(new Position(0, 0), null);
        assertEquals("Cars in lane incorrect initial.", null, lane.getCars().peek());
        lane.addCar(car1);
        assertEquals("Cars in lane incorrect car 1.", true, lane.getCars().contains(car1));
    }

    @Test
    public void moveCar() {
        ArrayList<CarMovable> carList = new ArrayList<>();
        Lane lane2 = new Lane(CardinalDirection.NORTH, new ArrayList<>(), 0, new BoundingBox(new Position(0, 40), 5, 100));
        carList.add(lane);
        carList.add(lane2);
        Car car1 = new Car(new Position(0, 0), null);
        lane.addCar(car1);
        lane.moveCar(lane2);
        assertEquals("Cars in lane1 incorrect after move", false, lane.getCars().contains(car1));
        assertEquals("Cars in lane2 incorrect after move", true, lane2.getCars().contains(car1));
    }

    @Test
    public void moveMultipleCar() {
        ArrayList<CarMovable> carList = new ArrayList<>();
        Lane lane2 = new Lane(CardinalDirection.NORTH, new ArrayList<>(), 0, new BoundingBox(new Position(0, 40), 5, 100));
        carList.add(lane);
        carList.add(lane2);
        Car car1 = new Car(new Position(0, 0), null);
        Car car2 = new Car(new Position(0, 0), null);
        lane.addCar(car1);
        lane.addCar(car2);
        lane.moveCar(lane2);
        //First car in, first car out - car 1 should be in lane 2, car 2 should be in lane 1
        assertTrue("Cars in lane1 incorrect after move", lane.getCars().contains(car2));
        assertFalse("Cars in lane1 incorrect after move", lane.getCars().contains(car1));
        assertFalse("Cars in lane2 incorrect after move", lane2.getCars().contains(car2));
        assertTrue("Cars in lane2 incorrect after move", lane2.getCars().contains(car1));
    }

    @Test
    public void moveMultipleCarMultipleTimes() {
        ArrayList<CarMovable> carList = new ArrayList<>();
        Lane lane2 = new Lane(CardinalDirection.NORTH, new ArrayList<>(), 0, new BoundingBox(new Position(0, 40), 5, 100));
        carList.add(lane);
        carList.add(lane2);
        Car car1 = new Car(new Position(0, 0), null);
        Car car2 = new Car(new Position(0, 0), null);
        lane.addCar(car1);
        lane.addCar(car2);
        lane.moveCar(lane2);
        //And now they should both be moved to the same lane
        lane.moveCar(lane2);
        assertEquals("Cars in lane1 incorrect after move", false, lane.getCars().contains(car2));
        assertEquals("Cars in lane1 incorrect after move", false, lane.getCars().contains(car1));
        assertEquals("Cars in lane2 incorrect after move", true, lane2.getCars().contains(car2));
        assertEquals("Cars in lane2 incorrect after move", true, lane2.getCars().contains(car1));
    }

    @Test
    public void removeCar() throws Exception {
        ArrayList<CarMovable> carList = new ArrayList<>();
        carList.add(lane);
        Car car = new Car(new Position(0, 0), null);
        lane.addCar(car);
        assertEquals("Removing car failed", true, lane.removeCar(car));
        assertEquals("Car was not removed from lane correctly", false, lane.getCars().contains(car));
    }

    @Test
    public void getDirection() throws Exception {
        lane.getDirection();
        fail("Not yet implemented");

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
        fail("Not yet implemented");
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