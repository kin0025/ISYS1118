/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.entities;

public class CarDestroy extends Intersection {
    @Override
    public boolean addCar(Car car) {
        car = null;
        return false;
    }
}
