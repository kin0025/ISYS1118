/*
 * ISYS1118: Assignment.
 * Group: Null
 * File created by: Alexander Kinross-Smith, s3603437
 */

package main.utils;

import main.utils.enums.CardinalDirection;
import main.utils.enums.CollisionStatus;

public class MovingBox extends BoundingBox {
    int time = 0;
    private BoundingBox parentBox;
    private double angle;

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
            this.getCentre().setPosition(parentBox.getCentre().getX(), parentBox.getCentre().getY());
        }
    }

    @Override
    public double getxMin() {
        double min = this.getCorners()[0].getX();
        for (Position position : this.getCorners()) {
            if (position.getX() < min) {
                min = position.getX();
            }
        }
        return min;
    }

    @Override
    public double getyMin() {
        double min = this.getCorners()[0].getY();
        for (Position position : this.getCorners()) {
            if (position.getY() < min) {
                min = position.getY();
            }
        }
        return min;
    }

    @Override
    public double getxMax() {
        double max = this.getCorners()[0].getX();
        for (Position position : this.getCorners()) {
            if (position.getX() > max) {
                max = position.getX();
            }
        }
        return max;
    }

    @Override
    public double getyMax() {
        double max = this.getCorners()[0].getY();
        for (Position position : this.getCorners()) {
            if (position.getY() > max) {
                max = position.getY();
            }
        }
        return max;
    }

    public Position[] getCorners() {
        Position[] corners = new Position[4];
        //Returns 4 corners of the thing
        for (int i = 0; i < corners.length; i++) {
            corners[i] = new Position();
        }
        corners[0].setX(getCentre().getX() + Math.sin(angle + Math.toRadians(-45)) * getWidth() / 2);
        corners[0].setY(getCentre().getY() + Math.cos(angle + Math.toRadians(-45)) * getHeight() / 2);

        corners[1].setX(getCentre().getX() + Math.cos(angle + Math.toRadians(45)) * getWidth() / 2);
        corners[1].setY(getCentre().getY() + Math.sin(angle + Math.toRadians(45)) * getHeight() / 2);


        corners[2].setX(getCentre().getX() + Math.sin(angle + Math.toRadians(45)) * getWidth() / 2);
        corners[2].setY(getCentre().getY() - Math.cos(angle + Math.toRadians(45)) * getHeight() / 2);

        corners[3].setX(getCentre().getX() + Math.cos(angle + Math.toRadians(-45)) * getWidth() / 2);
        corners[3].setY(getCentre().getY() - Math.sin(angle + Math.toRadians(-45)) * getHeight() / 2);
        return corners;
    }

    public CollisionStatus getCollisionStatus() {
        if (parentBox.isInsideBoundingBox(getxMax(), getyMax()) && parentBox.isInsideBoundingBox(getxMin(), getyMin()) && parentBox
                .isInsideBoundingBox(this.getCentre())) {
            return CollisionStatus.ENCLOSED;
        } else if (parentBox.isInsideBoundingBox(this.getCentre())) {
            return CollisionStatus.TOUCHING;
        }
        return CollisionStatus.OUTSIDE;
    }

    public CollisionStatus getCollisionStatus(CardinalDirection side) {
        switch (side) {
            case NORTH:
                if (parentBox.isInsideBoundingBox(parentBox.getCentre().getX(), getyMin()) && parentBox.isInsideBoundingBox(this.getCentre())) {
                    return CollisionStatus.ENCLOSED;
                }
                break;
            case EAST:
                if (parentBox.isInsideBoundingBox(getxMax(), parentBox.getCentre().getY()) && parentBox.isInsideBoundingBox(this.getCentre())) {
                    return CollisionStatus.ENCLOSED;
                }
                break;
            case SOUTH:
                if (parentBox.isInsideBoundingBox(parentBox.getCentre().getX(), getyMax()) && parentBox.isInsideBoundingBox(this.getCentre())) {
                    return CollisionStatus.ENCLOSED;
                }
                break;
            case WEST:
                if (parentBox.isInsideBoundingBox(getxMin(), parentBox.getCentre().getY()) && parentBox.isInsideBoundingBox(this.getCentre())) {
                    return CollisionStatus.ENCLOSED;
                }
                break;

        }
        if (parentBox.isInsideBoundingBox(this.getCentre())) {
            return CollisionStatus.TOUCHING;
        }
        return CollisionStatus.OUTSIDE;
    }

    public void setAngle(int angleDegrees) {
        this.angle = Math.toRadians(angle);
    }

    public double getAngle() {
        return Math.toDegrees(angle);
    }

    public void setAngle(CardinalDirection direction) {
        this.angle = Math.toRadians(direction.toDegrees());
    }

    public void moveForward(double amount) {
        getCentre().movePosition(-amount * Math.sin(angle), -amount * Math.cos(angle));
    }
}
