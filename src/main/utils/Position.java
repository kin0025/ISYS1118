package main.utils;

/**
 * The type main.utils.Position.
 */
public class Position {
    private double x;
    private double y;

    /**
     * Instantiates a new main.utils.Position.
     *
     * @param x the x position in pixels
     * @param y the y position in pixels
     */
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Move the location of the position by x elements in the x direction and y elements in the y direction.
     *
     * @param x the amount to change x by in pixels
     * @param y the amount to change y by in pixels
     */
    public void movePosition(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void movePosition(double[] array) {
        this.x += array[0];
        this.y += array[1];
    }

    public boolean movePosition(double[] array, double[] min, double[] max) {
        if(array.length != 2 || min.length != 2 || max.length != 2){
            return false;
        }
        //Check the x
        if((x + array[0]) > max[0] || (x + array[0]) < min[0]){
            return false;
        }
        if((y + array[1]) > max[1] || (y + array[1]) < min[1]){
            return false;
        }
        x += array[0];
        y += array[1];
        return true;
    }

    public boolean movePosition(double x, double y, double[] min, double[] max) {
        if(min.length != 2 || max.length != 2){
            return false;
        }
        //Check the x
        if(this.x + x > max[0] || this.x + x < min[0]){
            return false;
        }
        if(this.y + y > max[1] || this.y + y < min[1]){
            return false;
        }
        this.x += x;
        this.y += y;
        return true;
    }

    /**
     * Set position.
     *
     * @param x the new value of x in pixels
     * @param y the new value of y in pixels
     */
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(double[] array) {
        this.x = array[0];
        this.y = array[1];
    }


    public boolean setPosition(double[] array, double[] min, double[] max) {
        if(array.length != 2 || min.length != 2 || max.length != 2){
            return false;
        }
        //Check the x
        if(array[0] > max[0] ||array[0] < min[0]){
            return false;
        }
        //Check the Y
        if(array[1] > max[1] || array[1] < min[1]){
            return false;
        }
        x = array[0];
        y = array[1];
        return true;
    }

    public boolean setPosition(double x, double y, double[] min, double[] max) {
        if(min.length != 2 || max.length != 2){
            return false;
        }
        //Check the x
        if(x > max[0] || x < min[0]){
            return false;
        }
        //Check the y
        if(y > max[1] || y < min[1]){
            return false;
        }
        this.x = x;
        this.y = y;
        return true;

    }

    /**
     * Gets x.
     *
     * @return the x component of the position
     */
    public double getX() {
        return x;
    }

    /**
     * Gets y.
     *
     * @return the y component of the position
     */
    public double getY() {
        return y;
    }

    public double[] getPosition() {
        return new double[]{x, y};
    }

    public double getDifference(Position positionDiff) {
        //a^2 + b^2 = c^2 , return c.
        return Math.sqrt(Math.pow((positionDiff.getX() - this.x),2) + Math.pow((positionDiff.getY() - this.y),2));
    }

    /**
     * Gets the difference in dimension between this object and the argument.
     * @param positionDiff the position that the current position is subtracted from.
     * @param dimension the dimension (X or Y) that the difference is needed in.
     * @return the difference positionDiff - this in the dimensions specified as a double
     */
    public double getDifference(Position positionDiff, DIMENSION dimension) {
        switch (dimension){
            case X:
                return positionDiff.getX() - x;
            case Y:
                return  positionDiff.getY() - y;
                default:
                    throw new RuntimeException("Wrong dimension");
        }
    }

    public enum DIMENSION{X,Y}

}
