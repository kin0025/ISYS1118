/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.entities.lane;

import main.entities.Car;
import main.entities.interfaces.CarMoveable;
import main.entities.interfaces.SimulationTimed;
import main.utils.DimensionManager;
import main.utils.Direction;
import main.utils.Position;
import main.utils.TurnDirection;

import java.util.ArrayList;
import java.util.LinkedList;

public class Lane implements CarMoveable, SimulationTimed {
    private ArrayList<TurnDirection> turnDirections;
    private LinkedList<Car> cars = new LinkedList<>();
    private Direction direction;
    private int lanesFromEdge;
    private Position lanePosition;

    public Lane(Direction direction, ArrayList<TurnDirection> turnDirections, int lanesFromEdge, Position lanePosition) {
        this.turnDirections = turnDirections;
        this.direction = direction;
        this.lanesFromEdge = lanesFromEdge;
    }

    public void incrementTime() {
        for (Car car : cars) {
            car.incrementTime();
        }
        checkCarCollisions();
    }

    public Position getPosition() {
        return lanePosition;
    }

    public LinkedList<Car> getCars() {
        return cars;
    }

    /**
     * Stops the first car in the list
     */
    public void stopFirstCar(){
        if(cars.element() != null){
            cars.element().stop();
        }
    }

    public void startFirstCar(){
        if(cars.element() != null){
            cars.element().accelerate();
        }
    }

    /**
     * Iterates through the linked list and stops any cars that are getting too close to each other.
     */
    public void checkCarCollisions() {
        //TODO: Might need to be reversed based on how Linked List indexing works.
        for (int i = 0; i < cars.size(); i++) {
            Car currentCar = cars.get(i);
            Car nextCar = cars.get(i + 1);
            if (nextCar != null) {
                if (currentCar.getPosition().getDifference(nextCar.getPosition()) < DimensionManager.minimumFollowingDistancePixels) {
                    currentCar.stop();
                }else{
                    currentCar.accelerate();
                }
            }
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public int getLanesFromEdge() {
        return lanesFromEdge;
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
