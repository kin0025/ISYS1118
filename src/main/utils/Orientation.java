package main.utils;

/**
 * The type main.utils.Orientation. Used to indicate whether something is horizontal or vertical
 */
public class Orientation {
    public enum ENUM {HORIZONTAL,VERTICAL}

    private ENUM orientation;

    /**
     * Instantiates a new main.utils.Orientation.
     *
     * @param orientation the orientation to start on
     */
    public Orientation(ENUM orientation) {
        this.orientation = orientation;
    }

    /**
     * Swaps the orientation between horizontal or vertical, or vice versa.
     */
    public void swapOrientation() {
        if(orientation == ENUM.HORIZONTAL){
            orientation = ENUM.VERTICAL;
        }else{
            orientation = ENUM.HORIZONTAL;
        }
    }

    /**
     * Gets current orientation.
     *
     * @return the current orientation
     */
    public ENUM getCurrentOrientation() {
       return orientation;
    }
}
