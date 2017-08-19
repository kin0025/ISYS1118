package main.entities;

import main.utils.Direction;

import java.util.*;

public class Lane {
    private Road parentRoad;
    private LinkedList<Car> cars = new LinkedList<Car>();
    private ArrayList<Lane> outputLanes;
    private Direction direction;

    public Lane(Road parentRoad, ArrayList<Lane> outputLanes, Direction direction) {
        this.parentRoad = parentRoad;
        this.outputLanes = outputLanes;
        this.direction = direction;
    }

    public void incrementTime(){}

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
}
