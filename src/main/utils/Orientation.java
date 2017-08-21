package main.utils;

import main.exceptions.IncorrectOrientationException;

/**
 * The type main.utils.Orientation. Used to indicate whether something is horizontal or vertical
 */
public class Orientation {
    private boolean orientation;
    /**
     * The constant horizontal.
     */
    public static final boolean horizontal = false;
    /**
     * The constant vertical.
     */
    public static final boolean vertical = true;

    /**
     * Instantiates a new main.utils.Orientation.
     *
     * @param orientation the orientation to start on
     * @throws IncorrectOrientationException the incorrect orientation exception
     */
    public Orientation(char orientation) throws IncorrectOrientationException {
        switch (orientation) {
            case 'v':
                this.orientation = vertical;
                break;
            case 'h':
                this.orientation = horizontal;
                break;
            default:
                throw new IncorrectOrientationException("Not a compass direction");
        }
    }

    /**
     * Swaps the orientation between horizontal or vertical, or vice versa.
     */
    public void swapOrientation() {
        orientation = !orientation;
    }

    /**
     * Gets current orientation.
     *
     * @return the current orientation
     */
    public boolean getCurrentOrientation() {
        return orientation;
    }
}
