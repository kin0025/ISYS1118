/*
 * ISYS1118: Assignment.
 * Group: Null
 * File created by: Alexander Kinross-Smith, s3603437
 */

package main.utils;

import main.utils.enums.CardinalDirection;
import main.utils.enums.CollisionStatus;

public class MovingBox extends BoundingBox {
    private BoundingBox parentBox;
    private int angle;

    public MovingBox(double xMin, double yMin, double xMax, double yMax, BoundingBox parentBox) throws NumberFormatException {
        super(xMin, yMin, xMax, yMax);
        this.parentBox = parentBox;
    }

    public MovingBox(Position centre, double xWidth, double yWidth, BoundingBox parentBox) {
        super(centre, xWidth, yWidth);
        this.parentBox = parentBox;
    }

    public void setParentBox(BoundingBox parentBox) {
        this.parentBox = parentBox;
    }

    public void forceInsideParentBox() {
        if (getCollisionStatus() == CollisionStatus.ENCLOSED) {
            return;
        } else {
            //TODO IMPLEMENT
            //this.getCentre().setPosition(x,y);
        }
    }

    public CollisionStatus getCollisionStatus() {
        if(parentBox.isInsideBoundingBox(xMax,yMax) && parentBox.isInsideBoundingBox(xMax,yMax) && parentBox.isInsideBoundingBox(this.getCentre())){
            return CollisionStatus.ENCLOSED;
        }else if(parentBox.isInsideBoundingBox(this.getCentre())){
            return CollisionStatus.TOUCHING;
        }
        return CollisionStatus.OUTSIDE;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public void setAngle(CardinalDirection direction) {
        this.angle = direction.toDegrees();
    }

    public void moveForward(double amount) {
        amount = Math.toRadians(amount);
        xMin += amount * Math.cos(angle);
        yMin += amount * Math.sin(angle);
        xMax += amount * Math.cos(angle);
        yMax += amount * Math.sin(angle);
        getCentre().movePosition(amount * Math.cos(angle),amount * Math.sin(angle));
    }
}
