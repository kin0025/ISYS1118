/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.entities.lane;

import main.entities.car.Car;
import main.entities.interfaces.CarMovable;
import main.entities.interfaces.SimulationTimed;
import main.utils.BoundingBox;
import main.utils.DimensionManager;
import main.utils.Position;
import main.utils.enums.CardinalDirection;
import main.utils.enums.CollisionStatus;
import main.utils.enums.TurnDirection;

import java.util.ArrayList;
import java.util.LinkedList;

import static main.utils.DimensionManager.*;

public class Lane implements CarMovable, SimulationTimed {
    final ArrayList<TurnDirection> turnDirections;
    final LinkedList<Car> cars = new LinkedList<>();
    private final CardinalDirection direction;
    private final int lanesFromEdge;
    private final BoundingBox laneBox;
    private boolean carsCanLeaveLane = true;
    final Position entryPosition;

    public Lane(CardinalDirection direction, ArrayList<TurnDirection> turnDirections, int lanesFromEdge, BoundingBox laneBox) {
        this.turnDirections = turnDirections;
        this.direction = direction;
        this.lanesFromEdge = lanesFromEdge;
        this.laneBox = laneBox;

        double xPos = 0;
        double yPos = 0;
        if (direction == CardinalDirection.SOUTH) {
            xPos = (laneBox.getxMin() + laneBox.getxMax()) / 2;
            yPos = laneBox.getyMin() + DimensionManager.lengthOfCarPixels;
        }
        if (direction == CardinalDirection.NORTH) {
            xPos = (laneBox.getxMin() + laneBox.getxMax()) / 2;
            yPos = laneBox.getyMax() - DimensionManager.lengthOfCarPixels;
        }
        if (direction == CardinalDirection.WEST) {
            xPos = laneBox.getxMax() - DimensionManager.lengthOfCarPixels;
            yPos = (laneBox.getyMin() + laneBox.getyMax()) / 2;
        }
        if (direction == CardinalDirection.EAST) {
            xPos = laneBox.getxMin() + DimensionManager.lengthOfCarPixels;
            yPos = (laneBox.getyMin() + laneBox.getyMax()) / 2;
        }
        this.entryPosition = new Position(xPos, yPos);
    }

    public void incrementTime() {
            if (!cars.isEmpty()) {
                for (Car car : cars) {
                    car.incrementTime();
                    car.start();
                }
            }
        checkCarCollisions();
        checkCarPositions();

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
        carsCanLeaveLane = false;
    }

    public void startFirstCar() {
        carsCanLeaveLane = true;
    }

    /**
     * Iterates through the linked list and stops any cars that are getting too close to each other.
     * Returns True if no collisions occurred
     */
    boolean checkCarCollisions() {
        boolean carTooClose = false;
        for (int i = cars.size() - 1; i > 0; i--) {
            Car currentCar = cars.get(i);
            Car nextCar = cars.get(i - 1);
            if (nextCar != null) {
                if (currentCar.getPosition().getDifference(nextCar.getPosition()) < minimumFollowingDistancePixels) {
                    currentCar.stop();
                    carTooClose = true;
                } else {
                    currentCar.start();
                }
            } else {
                currentCar.start();
            }
        }
        return !carTooClose;
    }

    /**
     * Iterates through the linked list and finds any cars outside the box - moves them to the next lane if they are outside the lane
     */
    void checkCarPositions() {
        Car move = null;
        for (Car car : cars) {
            if (!laneBox.isInsideBoundingBox(car.getPosition())) {
                if (!car.isInsideParent()) {
                    if (carsCanLeaveLane) {
                        if (move == null) {
                            move = car;
                        } else {
                            throw new RuntimeException("Tried to remove multiple cars in one operation - cars must be in same location");
                        }
                    } else {
                        car.stop();
                    }
                }
            }
        }
        if (move != null) {
            move.moveToNext(this);
        }
    }

    public CardinalDirection getDirection() {
        return direction;
    }

    public int getLanesFromEdge() {
        return lanesFromEdge;
    }

    public int getNumberOfFreeSpaces() {
        return (lengthOfLanePixels / minimumFollowingDistancePixels) - cars.size();
    }

    public boolean hasTurnDirection(TurnDirection turnDirection) {
        return turnDirections.contains(turnDirection);
    }

    @Override
    public boolean addCar(Car car) {
        if (cars.add(car)) {
            car.getCarBox().setCentre(entryPosition.clone());
            return true;
        }
        return false;
    }

    @Override
    public boolean moveCar(CarMovable moveTo, Car car) {
        if (cars.peek() == car && moveTo.addCar(car)) {
            return this.removeCar(cars.peek());
        }
        return false;
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
