/*
 * ISYS1118: Assignment.
 * Group: Null
 * File created by: Alexander Kinross-Smith, s3603437
 */

package main.entities.lane;

import main.entities.Car;
import main.entities.interfaces.CarMoveable;
import main.utils.CardinalDirection;
import main.utils.Direction;
import main.utils.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CarDestroyTest {
    CarDestroy carDestroy;
    @Before
    public void setUp() throws Exception {
        carDestroy = new CarDestroy(new Direction(CardinalDirection.NORTH), new ArrayList<>(),0,new Position(0,0));
    }

    @Test
    public void addCar() throws Exception {
        ArrayList<CarMoveable> carPath = new ArrayList<>();
        Car car = new Car(new Position(0,0), carPath);
        carDestroy.addCar(car);
        assertEquals("Destroying a car failed.",null,carDestroy.getCars());
    }

}