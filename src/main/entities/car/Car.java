package main.entities.car;

import main.entities.interfaces.CarMovable;
import main.entities.interfaces.SimulationTimed;
import main.entities.intersection.Intersection;
import main.entities.lane.Lane;
import main.utils.BoundingBox;
import main.utils.DimensionManager;
import main.utils.MovingBox;
import main.utils.Position;
import main.utils.enums.CardinalDirection;
import main.utils.enums.CollisionStatus;
import main.utils.enums.TurnDirection;

/**
 * The type main.entities.car.Car.
 */
public class Car implements SimulationTimed {
    private static final double maxSpeed = DimensionManager.kmphToPixelTick(50);
    private final CarPath carPath;
    private double speed = maxSpeed;
    private final MovingBox carBox;
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
//In an intersection, don't bother changing direction
        }
        carBox.moveForward(speed);
    }

    public boolean moveToNext(CarMovable moveFrom) {
        if (!moveFrom.moveCar(carPath.getNextCarPosition(this), this)) {
            return false;
        }
        return carPath.moveCarToNext(this);
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

    public void setParent(BoundingBox box) {
        carBox.setParentBox(box);
    }

    public boolean isInsideParent() {
        return carBox.isInsideParent();
    }

    public boolean isMoving() {
        return (speed != 0);
    }

    public TurnDirection getCurrentTurnDirection() {
        return carPath.getCurrentTurn(this);
    }

    public Intersection getNextIntersection() {
        return carPath.getNextIntersection(this);
    }

    public Lane getNextLane() {
        return carPath.getNextLane(this);
    }

    public CarMovable getNextPosition() {
        return carPath.getNextCarPosition(this);
    }

}
