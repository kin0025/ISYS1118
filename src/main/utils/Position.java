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
    Position(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Move the location of the position by x elements in the x direction and y elements in the y direction.
     *
     * @param x the amount to change x by in pixels
     * @param y the amount to change y by in pixels
     */
    public void movePosition(double x, double y){
        this.x +=x;
        this.y +=y;
    }

    public void movePosition(double[] array){
        this.x += array[0];
        this.y += array[1];
    }

public boolean movePosition(double[] array, double[] min, double[] max){return false;}
public boolean movePosition(double x, double y, double[] min, double[] max){return false;}
    /**
     * Set position.
     *
     * @param x the new value of x in pixels
     * @param y the new value of y in pixels
     */
    public void setPosition(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void setPosition(double[] array){
        this.x = array[0];
        this.y = array[1];
    }


    public boolean setPosition(double[] array, double[] min, double[] max){return false;}
    public boolean setPosition(double x, double y, double[] min, double[] max){return false;}

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

    public double[] getPosition(){
        double[] array = {x,y};
        return array;
    }

    public double getDifference(Position positionDiff){return 0;}

    public double getDifference(Position positionDiff, int dimension){return 0;}


}
