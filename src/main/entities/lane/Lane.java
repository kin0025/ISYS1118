/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.entities.lane;

import main.entities.Car;
import main.entities.interfaces.CarMoveable;
import main.entities.interfaces.SimulationTimed;
import main.utils.Direction;
import main.utils.TurnDirection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Lane implements CarMoveable,SimulationTimed {
    private ArrayList<TurnDirection> turnDirections;
    private LinkedList<Car> cars = new LinkedList<>();
    private Direction direction;

    public Lane(Direction direction, ArrayList<TurnDirection> turnDirections) {
        this.turnDirections = turnDirections;
        this.direction = direction;
    }

    public void incrementTime() {

    }


    public LinkedList<Car> getCars() {
        return cars;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean hasTurnDirection(TurnDirection turnDirection) {
        return turnDirections.contains(turnDirection);
    }

    @Override
    public boolean addCar(Car car) {

        cars.add(car);
        return true;
    }

    @Override
    public boolean moveCar(CarMoveable moveTo) {
        return false;
    }

    @Override
    public boolean removeCar(Car car) {
        cars.remove();
        return false;
    }
}
