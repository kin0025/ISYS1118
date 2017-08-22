package main.entities;

import main.entities.interfaces.CarMoveable;
import main.entities.interfaces.SimulationTimed;
import main.utils.DimensionManager;
import main.utils.Direction;
import main.utils.Position;

import java.util.ArrayList;

/**
 * The type main.entities.Car.
 */
public class Car implements SimulationTimed {
    private static final double accelerationRate = DimensionManager.metersToPixels(2);
    private static final double decelerationRate = DimensionManager.metersToPixels(4);
    private static final double maxSpeed = DimensionManager.metersToPixels(13.9);
    private double speed = maxSpeed;
    private Position position;
    private Direction direction;
    private boolean accelerating = true;

    //The status of the next turn - 0 if left turn, 1 if straight, 2 if right turn.
    private int turning;
    private ArrayList<CarMoveable> carPath;

    public Car(Position position, Direction direction, ArrayList<CarMoveable> carPath) {
        this.position = position;
        this.direction = direction;
        this.carPath = carPath;
    }

    public void accelerate() {
        accelerating = true;
    }

    public void stop() {
        accelerating = false;
    }

    public void incrementTime() {
        //Run the acceleration logic.
        if (accelerating && speed < maxSpeed) {
            if (speed + accelerationRate < maxSpeed) {
                speed += accelerationRate;
            } else {
                speed = maxSpeed;
            }
        } else {
            if (speed - decelerationRate >= 0) {
                speed -= decelerationRate;
            } else {
                speed = 0;
            }
        }


        //Create the direction that we want to go in, and then move there.
        double[] moveBy = new double[2];
        for (int i = 0; i < moveBy.length; i++) {
            moveBy[i] = direction.getDirectionVector()[i] * speed;
        }
        position.movePosition(moveBy);
        //TODO: Shit tons of stuff

        //TODO: If decelerating don't do anything?

        /*
        if (position.getDifference(intersectionPath.element().getPosition()) <= 0) {
            //TODO: Move to another lane logic here
            lanePath.remove().getCars().remove(this);
            lanePath.element().getCars().add(this);
            this.direction = lanePath.element().getDirection();
        }
        */
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
        return position;
    }

    public boolean isAccelerating() {
        return accelerating;
    }
}
