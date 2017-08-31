/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.entities.interfaces;

import main.entities.Car;


public interface CarMoveable {
    /**
     * Adds a car to the object
     * @param car The car to be added
     * @return not sure why this would fail?
     */
    boolean addCar(Car car);

    /**
     * Removed the Car car if it is the first one in the queue. Returns false if there are other cars?
     * @param car the car to be removed
     * @return whether the car was the first in the queue
     */
    boolean removeCar(Car car);

    /**
     * Moves the first car in the queue to the object moveTo. Turns car as appropriate.
     * @param moveTo The object a car is been moved to.
     * @return whether the move operations succeeded.
     */
    boolean moveCar(CarMoveable moveTo);
}
