package main.entities;

import main.entities.interfaces.CarMoveable;
import main.entities.interfaces.SimulationTimed;
import main.utils.BoundingBox;
import main.utils.DimensionManager;
import main.utils.Direction;
import main.utils.Position;

import java.util.ArrayList;

/**
 * The type main.entities.Car.
 */
public class Car implements SimulationTimed {
    private static final double maxSpeed = DimensionManager.kmphToPixelTick(50);
    private double speed = maxSpeed;
    private Position carPosition;

    //Always set to the direction of the parent lane.
    private Direction direction;

    //The status of the next turn - 0 if left turn, 1 if straight, 2 if right turn.
    private int turning;
    private ArrayList<CarMoveable> carPath;
    private int carPathPosition = 0;
    private boolean moveMe = false;

    public Car(Position carPosition, ArrayList<CarMoveable> carPath) {
        this.carPosition = carPosition;
        this.carPath = carPath;
        if(carPath != null && carPath.size() != 0) {
            this.direction = carPath.get(0).getDirection();
        }

    }

    public void accelerate() {
        speed = maxSpeed;
    }

    public void stop() {
        speed = 0;
    }

    public void incrementTime() {
        if (speed != 0) {
            direction = carPath.get(carPathPosition).getDirection();
            if (direction != null) {
                //Create the direction that we want to go in, and then move there.
                double[] moveBy = new double[2];
                for (int i = 0; i < moveBy.length; i++) {
                    moveBy[i] = direction.getDirectionVector()[i] * speed;
                }
                carPosition.movePosition(moveBy);
            }

            BoundingBox currentObjectPosition = carPath.get(carPathPosition).getBoundingBox();
            if (currentObjectPosition != null && currentObjectPosition.isInsideBoundingBox(carPosition)) {
                moveMe = true;
                carPathPosition++;
            } else {
                moveMe = false;
            }
        }
        /*
        if (carPosition.getDifference(intersectionPath.element().getBoundingBox()) <= 0) {
            //TODO: Move to another lane logic here
            lanePath.remove().getCars().remove(this);
            lanePath.element().getCars().add(this);
            this.direction = lanePath.element().getDirection();
        }
        */
    }

    public boolean needsToBeMoved() {
        return moveMe;
    }

    public void turnLeft() {
        direction.turnLeft();
    }

    public void turnRight() {
        direction.turnRight();
    }

    public Direction getDirection() {
        return direction;
    }

    public double getSpeed() {
        return speed;
    }

    public Position getPosition() {
        return carPosition;
    }

    public boolean isMoving() {
        return (speed != 0);
    }

}
