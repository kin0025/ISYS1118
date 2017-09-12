/*
 * ISYS1118: Assignment.
 * Group: Null
 * File created by: Alexander Kinross-Smith, s3603437
 */

package main.utils;

public class BoundingBox {
    double xMin;
    double yMin;
    double xMax;
    double yMax;
    Position centre;

    public BoundingBox(double xMin, double yMin, double xMax, double yMax) throws NumberFormatException {
        if(xMin >= xMax || yMin >= yMax){
            throw new NumberFormatException("Max must be greater than minimum");
        }
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
        double xCenter = xMin + ((xMax - xMin)/2);
        double yCenter = yMin + ((yMax - yMin)/2);
        centre = new Position(xCenter,yCenter);
    }

    public BoundingBox(Position centre, double xWidth, double yWidth){
        this.centre = centre;
        xMin = centre.getX() - (xWidth/2);
        yMin = centre.getY() - (yWidth/2);
        xMax = centre.getX() + (xWidth/2);
        yMax = centre.getY() + (yWidth/2);
    }


    public double getxMin() {
        return xMin;
    }

    public void setxMin(double xMin) {
        this.xMin = xMin;
    }

    public double getyMin() {
        return yMin;
    }

    public void setyMin(double yMin) {
        this.yMin = yMin;
    }

    public double getxMax() {
        return xMax;
    }

    public void setxMax(double xMax) {
        this.xMax = xMax;
    }

    public double getyMax() {
        return yMax;
    }

    public void setyMax(double yMax) {
        this.yMax = yMax;
    }

    public

    public Position getCentre() {
        return centre;
    }

    public boolean isInsideBoundingBox(Position position) {
        double y = position.getY();
        double x = position.getX();
        return x > xMin && x < xMax && y > yMin && y < yMax;
    }
}
