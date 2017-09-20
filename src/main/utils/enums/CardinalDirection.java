/*
 * ISYS1118: Assignment.
 * Group: Null
 * File created by: Alexander Kinross-Smith, s3603437
 */

/*
 * ISYS1118: Assignment.
 * Group: Null
 * File created by: Alexander Kinross-Smith, s3603437
 */

package main.utils.enums;

public enum CardinalDirection {
    NORTH, EAST, SOUTH, WEST;

    public CardinalDirection reverse() {
        switch (this) {
            case NORTH:
                return SOUTH;
            case EAST:
                return WEST;
            case SOUTH:
                return NORTH;
            case WEST:
                return EAST;
            default:
                return null;
        }
    }

    public Orientation getAxis() {
        switch (this) {
            case NORTH:
            case SOUTH:
                return Orientation.VERTICAL;
            case EAST:
            case WEST:
                return Orientation.HORIZONTAL;
            default:
                return null;
        }
    }

    public int toDegrees() {
        switch (this) {
            case NORTH:
                return 0;
            case SOUTH:
                return 180;
            case EAST:
                return 90;
            case WEST:
                return 270;
            default:
                return -1;
        }
    }

}
