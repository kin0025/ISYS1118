/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.entities.lane;

import main.entities.car.Car;
import main.utils.BoundingBox;
import main.utils.enums.CardinalDirection;
import main.utils.enums.TurnDirection;

import java.util.ArrayList;

public class CarDestroy extends Lane {
    public CarDestroy(CardinalDirection direction, int lanesFromEdge, BoundingBox laneBox) {
        super(direction, new ArrayList<>(), lanesFromEdge, laneBox);
        turnDirections.add(TurnDirection.STRAIGHT);
        turnDirections.add(TurnDirection.LEFT);
        turnDirections.add(TurnDirection.RIGHT);
        turnDirections.add(TurnDirection.REVERSE);
    }

    @Override
    public boolean addCar(Car car) {
        return false;
    }
}
