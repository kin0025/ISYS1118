/*
 * ISYS1118: Assignment.
 * Group: Null
 * File created by: Alexander Kinross-Smith, s3603437
 */

package main.utils;

import main.utils.enums.CollisionStatus;

@SuppressWarnings("SpellCheckingInspection")
public class BoundingBox {
    private double width;
    private double height;
    private Position centre;

    public BoundingBox(double xMin, double yMin, double xMax, double yMax) throws NumberFormatException {
        if (xMin >= xMax || yMin >= yMax) {
            throw new NumberFormatException("Max must be greater than minimum");
        }
        width = xMax - xMin;
        height = yMax - yMin;
        double xCenter = xMin + ((xMax - xMin) / 2);
        double yCenter = yMin + ((yMax - yMin) / 2);
        centre = new Position(xCenter, yCenter);
    }


    public BoundingBox(Position centre, double xWidth, double yWidth) {
        this.centre = centre;
        width = xWidth;
        height = yWidth;
    }

    public double getxMin() {
        return centre.getX() - width / 2;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getyMin() {
        return centre.getY() - height/2;
    }


    public double getxMax() {
        return centre.getX() + width/2;
    }


    public double getyMax() {
        return centre.getY() + height/2;
    }


    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Position getCentre() {
        return centre;
    }

    public boolean isInsideBoundingBox(Position position) {
        double y = position.getY();
        double x = position.getX();
        return isInsideBoundingBox(x, y);
    }

    public boolean isInsideBoundingBox(double x, double y) {
        return x > getxMin() && x < getxMax() && y > getyMin() && y < getyMax();
    }
}
