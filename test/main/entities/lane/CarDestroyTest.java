/*
 * ISYS1118: Assignment.
 * Group: Null
 * File created by: Alexander Kinross-Smith, s3603437
 */

package main.entities.lane;

import main.entities.car.Car;
import main.entities.interfaces.CarMovable;
import main.utils.BoundingBox;
import main.utils.enums.CardinalDirection;
import main.utils.Direction;
import main.utils.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class CarDestroyTest {
    private CarDestroy carDestroy;
    @Before
    public void setUp() throws Exception {
        carDestroy = new CarDestroy(new Direction(CardinalDirection.NORTH), new ArrayList<>(),0,new BoundingBox(new Position(0,0),20,100));
    }

    @Test
    public void addCar() throws Exception {
        ArrayList<CarMovable> carPath = new ArrayList<>();
        Car car = new Car(new Position(0,0), carPath);
        carDestroy.addCar(car);
        //assertEquals("Destroying a car failed.",0,carDestroy.getCars().size());
    }

}