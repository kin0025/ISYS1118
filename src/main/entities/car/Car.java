package main.entities.car;

import main.entities.interfaces.CarMovable;
import main.entities.interfaces.SimulationTimed;
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
    private final CarPath carPath;
    private double speed = maxSpeed;
    private MovingBox carBox;
    //Always set to the direction of the parent lane.
    private CardinalDirection direction;

    public Car(Position carPosition, CarPath carPath) {
        this.carPath = carPath;
        if (carPath != null && carPath.getSize() != 0) {
            this.carPath.addCar(this);
            this.direction = carPath.get(0).getDirection();
            this.carBox = new MovingBox(carPosition, DimensionManager.widthOfCarPixels, DimensionManager.lengthOfCarPixels, carPath.get(0)
                    .getBoundingBox());
        } else {
            this.carBox = new MovingBox(carPosition, DimensionManager.widthOfCarPixels, DimensionManager.lengthOfCarPixels, null);
        }

    }

    public void start() {
        speed = maxSpeed;
    }

    public void stop() {
        speed = 0;
    }

    public void incrementTime() {
        try {
            carBox.setAngle(carPath.getCarPosition(this).getDirection());
        } catch (NullPointerException e) {
            return;
        }
        carBox.moveForward(speed);
    }

    public boolean moveToNext(CarMovable moveFrom) {
        boolean result = carPath.moveCarToNext(this);
        moveFrom.moveCar(carPath.getCarPosition(this), this);
        System.out.println("car was moved to next lane");
        return result;
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

    public MovingBox getCarBox() {
        return carBox;
    }

    public CollisionStatus getCollisionStatus() {
        return carBox.getCollisionStatus();
    }

    public boolean isInsideParent() {
        return carBox.isInsideParent();
    }

    public boolean isMoving() {
        return (speed != 0);
    }

}
