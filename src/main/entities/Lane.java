/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.entities;

import main.entities.interfaces.CarMoveable;
import main.utils.Direction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Lane implements CarMoveable{
    private Road parentRoad;
    private LinkedList<Car> cars = new LinkedList<>();
    private Direction direction;
    ArrayList <Direction.TURN_DIRECTION> turnDirections;

    public Lane(){
        parentRoad = null;
    }

    public Lane(Road parentRoad, Direction direction, ArrayList<Direction.TURN_DIRECTION> turnDirections) {
        this.turnDirections = turnDirections;
        this.parentRoad = parentRoad;
        this.direction = direction;
    }


    public void incrementTime() {
    }

    public Road getParentRoad() {
        return parentRoad;
    }

    public Queue<Car> getCars() {
        return cars;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean hasTurnDirection(Direction.TURN_DIRECTION turnDirection) {
        return turnDirections.contains(turnDirection);
    }

    @Override
    public boolean addCar(Car car) {
        return false;
    }

    @Override
    public boolean moveCar(Car car) {
        return false;
    }

    @Override
    public boolean removeCar(Car car) {
        return false;
    }
}
