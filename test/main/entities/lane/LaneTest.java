/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.entities.lane;

import main.entities.Car;
import main.entities.interfaces.CarMoveable;
import main.utils.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LaneTest {
    Lane lane;

    @Before
    public void setUp() throws Exception {
        lane = new Lane();
    }

    @Test
    public void moveCars() throws Exception {
        ArrayList<CarMoveable> carList = new ArrayList<>();
        Lane lane2 = new Lane();
        carList.add(lane);
        carList.add(lane2);
        Car car1 = new Car(new Position(0, 0), carList);
        Car car2 = new Car(new Position(0, 0), carList);
        assertEquals("Cars in lane incorrect initial.", null, lane.getCars());
        lane.addCar(car1);
        assertEquals("Cars in lane incorrect car 1.", true, lane.getCars().contains(car1));
        assertEquals("Cars in lane incorrect.", false, lane.getCars().contains(car2));
        lane.addCar(car2);
        assertEquals("Cars in lane incorrect.", true, lane.getCars().contains(car2));
        assertEquals("Lane allows removing car that isn't first", false, lane.removeCar(car2));
        assertEquals("Removing car failed", true, lane.removeCar(car1));
        assertEquals("Car was not removed from lane correctly", false, lane.getCars().contains(car1));
        assertEquals("Car was not moved correctly", true, lane.moveCar(lane2));
        assertEquals("Car was not moved from lane correctly", false, lane.getCars().contains(car2));
        assertEquals("Car was not moved to lane correctly", true, lane2.getCars().contains(car2));

    }

    @Test
    public void getDirection() throws Exception {
        fail("Not yet implemented");

    }

    @Test
    public void hasTurnDirection() throws Exception {
        fail("Not yet implemented");
    }


}