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
    private ArrayList<Lane> outputLanes;
    private Direction direction;
    private boolean isRightTurnLane;

    public Lane(Road parentRoad, ArrayList<Lane> outputLanes, Direction direction, boolean isRightTurnLane) {
        this.isRightTurnLane = isRightTurnLane;
        this.parentRoad = parentRoad;
        this.outputLanes = outputLanes;
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

    public ArrayList<Lane> getOutputLanes() {
        return outputLanes;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isRightTurnLane() {
        return isRightTurnLane;
    }

    //Why did I make this?
    public boolean isLeftTurnLane() {
        return !isRightTurnLane;
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
