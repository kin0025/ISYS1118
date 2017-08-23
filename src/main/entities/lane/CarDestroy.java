/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.entities.lane;

import main.entities.Car;

public class CarDestroy extends Lane {
    @Override
    public boolean addCar(Car car) {
        car = null;
        return false;
    }
}
