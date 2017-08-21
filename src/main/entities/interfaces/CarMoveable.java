/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.entities.interfaces;

import main.entities.Car;

public interface CarMoveable {
    public boolean addCar(Car car);
    public boolean removeCar(Car car);
    public boolean moveCar(Car car);
}
