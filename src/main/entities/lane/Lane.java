/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.entities.lane;

import main.entities.car.Car;
import main.entities.interfaces.CarMovable;
import main.entities.interfaces.SimulationTimed;
import main.utils.BoundingBox;
import main.utils.DimensionManager;
import main.utils.Direction;
import main.utils.Position;
import main.utils.enums.CardinalDirection;
import main.utils.enums.TurnDirection;

import java.util.ArrayList;
import java.util.LinkedList;

public class Lane implements CarMovable, SimulationTimed {
    private final ArrayList<TurnDirection> turnDirections;
    private final LinkedList<Car> cars = new LinkedList<>();
    private final CardinalDirection direction;
    private final int lanesFromEdge;
    private final BoundingBox laneBox;

    public Lane(CardinalDirection direction, ArrayList<TurnDirection> turnDirections, int lanesFromEdge, BoundingBox laneBox) {
        this.turnDirections = turnDirections;
        this.direction = direction;
        this.lanesFromEdge = lanesFromEdge;
        this.laneBox = laneBox;
    }

    public void incrementTime() {
        checkCarCollisions();
        checkCarPositions();
        for (Car car : cars) {
            car.incrementTime();
        }
    }

    public BoundingBox getBoundingBox() {
        return laneBox;
    }

    public LinkedList<Car> getCars() {
        return cars;
    }

    /**
     * Stops the first car in the list
     */
    public void stopFirstCar() {
        if (cars.element() != null) {
            cars.element().stop();
        }
    }

    public void startFirstCar() {
        if (cars.element() != null) {
            cars.element().accelerate();
        }
    }

    /**
     * Iterates through the linked list and stops any cars that are getting too close to each other.
     * Returns True if no collisions occurred
     */
    boolean checkCarCollisions() {
        boolean carTooClose = false;
        for (int i = 0; i < cars.size() - 1; i++) {
            Car currentCar = cars.get(i);
            Car nextCar = cars.get(i + 1);
            if (nextCar != null) {
                if (currentCar.getPosition().getDifference(nextCar.getPosition()) < DimensionManager.minimumFollowingDistancePixels) {
                    currentCar.stop();
                    carTooClose = true;
                } else {
                    currentCar.accelerate();
                }
            }
        }
        return !carTooClose;
    }

    /**
     * Iterates through the linked list and finds any cars outside the box
     * * Returns True if none outside the lane.
     */
    boolean checkCarPositions() {
        boolean carOutsideBox = false;
        for (Car car : cars) {
            if (!laneBox.isInsideBoundingBox(car.getPosition())) {
                carOutsideBox = true;
            }
        }
        return !carOutsideBox;
    }

    public CardinalDirection getDirection() {
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
        return cars.add(car);
    }

    @Override
    public boolean moveCar(CarMovable moveTo) {
        moveTo.addCar(cars.element());
        return this.removeCar(cars.element());
    }

    @Override
    public boolean removeCar(Car car) {
        if (cars.element() == car) {
            cars.remove();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isInsideBoundingBox(Position position) {
        return laneBox.isInsideBoundingBox(position);
    }

    @Override
    public Position getCentre() {
        return laneBox.getCentre();
    }
}
