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

    @Override
    public double getxMin() {
        //TODO IMPLEMENT
        return super.getxMin();
    }

    @Override
    public double getyMin()    //TODO IMPLEMENT
    {
        return super.getyMin();
    }

    @Override
    public double getxMax() {    //TODO IMPLEMENT

        return super.getxMax();
    }

    @Override
    public double getyMax()    //TODO IMPLEMENT
    {
        //TODO IMPLEMENT

        return super.getyMax();
    }

    public double[][] getCorners(){
        //Returns 4 corners of the thing
        return null;
    }

    public CollisionStatus getCollisionStatus() {
        if (parentBox.isInsideBoundingBox(xMax, yMax) && parentBox.isInsideBoundingBox(xMin, yMin) && parentBox.isInsideBoundingBox(this.getCentre
                ())) {
            return CollisionStatus.ENCLOSED;
        } else if (parentBox.isInsideBoundingBox(this.getCentre())) {
            return CollisionStatus.TOUCHING;
        }
        return CollisionStatus.OUTSIDE;
    }

    public CollisionStatus getCollisionStatus(CardinalDirection side) {
        switch (side) {
            case NORTH:
                if (parentBox.isInsideBoundingBox(parentBox.getCentre().getX(), yMin) && parentBox.isInsideBoundingBox(this.getCentre())) {
                    return CollisionStatus.ENCLOSED;
                }
                break;
            case EAST:
                if (parentBox.isInsideBoundingBox(xMax, parentBox.getCentre().getY()) && parentBox.isInsideBoundingBox(this.getCentre())) {
                    return CollisionStatus.ENCLOSED;
                }
                break;
            case SOUTH:
                if (parentBox.isInsideBoundingBox(parentBox.getCentre().getX(), yMax) && parentBox.isInsideBoundingBox(this.getCentre())) {
                    return CollisionStatus.ENCLOSED;
                }
                break;
            case WEST:
                if (parentBox.isInsideBoundingBox(xMin, parentBox.getCentre().getY()) && parentBox.isInsideBoundingBox(this.getCentre())) {
                    return CollisionStatus.ENCLOSED;
                }
                break;

        }
        if (parentBox.isInsideBoundingBox(this.getCentre())) {
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
        double angleRad = Math.toRadians(angle);
        yMin -= amount * Math.cos(angleRad);
        xMin -= amount * Math.sin(angleRad);
        yMax -= amount * Math.cos(angleRad);
        xMax -= amount * Math.sin(angleRad);
        getCentre().movePosition(-amount * Math.sin(angleRad), -amount * Math.cos(angleRad));
    }
}
