/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.entities.lane;

import main.entities.Car;
import main.utils.BoundingBox;
import main.utils.Direction;
import main.utils.TurnDirection;

import java.util.ArrayList;

public class CarDestroy extends Lane {
    public CarDestroy(Direction direction, ArrayList<TurnDirection> turnDirections, int lanesFromEdge, BoundingBox laneBox) {
        super(direction, turnDirections, lanesFromEdge, laneBox);
    }

    @Override
    public boolean addCar(Car car) {
        return false;
    }
}
