package main.entities;

import main.entities.lane.Lane;
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
    //private static final double accelerationRate = DimensionManager.metersToPixels(2);
    //private static final double decelerationRate = DimensionManager.metersToPixels(4);
    private static final double maxSpeed = DimensionManager.metersToPixels(13.9);
    private double speed = maxSpeed;
    private Position position;

    //Always set to the direction of the parent lane.
    private Direction direction;

    //The status of the next turn - 0 if left turn, 1 if straight, 2 if right turn.
    private int turning;
    private ArrayList<CarMoveable> carPath;

    public Car(Position position, ArrayList<CarMoveable> carPath) {
        this.position = position;
        this.carPath = carPath;
        if(carPath.get(0).getClass() == Lane.class){
            //this.direction = (Lane)(carPath.get(0)).getDirection();
        }
    }

    public void accelerate() {
        speed = maxSpeed;
    }

    public void stop() {
        speed = 0;
    }

    public void incrementTime() {

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

    public boolean isMoving() {
        return !(speed != 0);
    }

}
