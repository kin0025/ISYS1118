/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.entities.lane;

import main.entities.Car;
import main.entities.interfaces.CarMoveable;
import main.utils.CardinalDirection;
import main.utils.Direction;
import main.utils.Position;
import main.utils.TurnDirection;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LaneTest {
    Lane lane;

    @Before
    public void setUp() throws Exception {
        lane = new Lane(new Direction(CardinalDirection.NORTH),new ArrayList<TurnDirection>());
    }

    @Test
    public void addCar() {
        ArrayList<CarMoveable> carList = new ArrayList<>();
        Car car1 = new Car(new Position(0, 0), carList);
        assertEquals("Cars in lane incorrect initial.", null, lane.getCars().peek());
        lane.addCar(car1);
        assertEquals("Cars in lane incorrect car 1.", true, lane.getCars().contains(car1));
    }

    @Test
    public void moveCar() {
        ArrayList<CarMoveable> carList = new ArrayList<>();
        Lane lane2 = new Lane(new Direction(CardinalDirection.NORTH), new ArrayList<>());
        carList.add(lane);
        carList.add(lane2);
        Car car1 = new Car(new Position(0, 0), carList);
        lane.addCar(car1);
        lane.moveCar(lane2);
        assertEquals("Cars in lane1 incorrect after move", false, lane.getCars().contains(car1));
        assertEquals("Cars in lane2 incorrect after move", true, lane2.getCars().contains(car1));
    }

    @Test
    public void moveMultipleCar() {
        ArrayList<CarMoveable> carList = new ArrayList<>();
        Lane lane2 = new Lane(new Direction(CardinalDirection.NORTH), new ArrayList<>());
        carList.add(lane);
        carList.add(lane2);
        Car car1 = new Car(new Position(0, 0), carList);
        Car car2 = new Car(new Position(0, 0), carList);
        lane.addCar(car1);
        lane.addCar(car2);
        lane.moveCar(lane2);
        assertEquals("Cars in lane1 incorrect after move", false, lane.getCars().contains(car2));
        assertEquals("Cars in lane1 incorrect after move", true, lane.getCars().contains(car1));
        assertEquals("Cars in lane2 incorrect after move", true, lane2.getCars().contains(car2));
        assertEquals("Cars in lane2 incorrect after move", false, lane2.getCars().contains(car1));
        lane.moveCar(lane2);
        assertEquals("Cars in lane1 incorrect after move", false, lane.getCars().contains(car2));
        assertEquals("Cars in lane1 incorrect after move", false, lane.getCars().contains(car1));
        assertEquals("Cars in lane2 incorrect after move", true, lane2.getCars().contains(car2));
        assertEquals("Cars in lane2 incorrect after move", true, lane2.getCars().contains(car1));
    }

    @Test
    public void removeCar() throws Exception {
        ArrayList<CarMoveable> carList = new ArrayList<>();
        carList.add(lane);
        Car car = new Car(new Position(0, 0), carList);
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
    public void hasTurnDirection() throws Exception {
        fail("Not yet implemented");
    }


}