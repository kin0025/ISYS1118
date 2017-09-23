package main.entities.car;

import main.entities.interfaces.SimulationTimed;
import main.utils.BoundingBox;
import main.utils.DimensionManager;
import main.utils.MovingBox;
import main.utils.Position;
import main.utils.enums.CardinalDirection;
import main.utils.enums.CollisionStatus;

/**
 * The type main.entities.car.Car.
 */
public class Car implements SimulationTimed {
    private static final double maxSpeed = DimensionManager.kmphToPixelTick(50);
    private double speed = maxSpeed;
    private MovingBox carBox;

    //Always set to the direction of the parent lane.
    private CardinalDirection direction;
    private final CarPath carPath;
    private int carPathPosition = 0;
    private boolean moveMe = false;

    public Car(Position carPosition, CarPath carPath) {
        this.carPath = carPath;
        if (carPath != null && carPath.getSize() != 0) {
            this.direction = carPath.get(0).getDirection();
            this.carBox = new MovingBox(carPosition, DimensionManager.lengthOfCarPixels, DimensionManager.widthOfCarPixels, carPath.get(0)
                    .getBoundingBox());
        }

    }

    public void start() {
        speed = maxSpeed;
    }

    public void stop() {
        speed = 0;
    }

    public void incrementTime() {
        carBox.setAngle(carPath.getCarPosition(this).getDirection());
        carBox.moveForward(speed);
        /*BoundingBox currentObjectBox = carPath.get(carPathPosition).getBoundingBox();
        if (currentObjectBox != null && carBox.getCollisionStatus() == CollisionStatus.INSIDE) {
            moveMe = true;
            carPathPosition++;
        } else {
            moveMe = false;
        }
*/

        /*
        if (carPosition.getDifference(intersectionPath.element().getBoundingBox()) <= 0) {
            //TODO: Move to another lane logic here
            lanePath.remove().getCars().remove(this);
            lanePath.element().getCars().add(this);
            this.direction = lanePath.element().getDirection();
        }
        */
    }

    public CardinalDirection getDirection() {
        return direction;
    }

    public double getSpeed() {
        return speed;
    }

    public Position getPosition() {
        return carBox.getCentre();
    }

    public boolean isMoving() {
        return (speed != 0);
    }

}
