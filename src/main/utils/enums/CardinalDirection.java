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

@SuppressWarnings("ALL")
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

    public TurnDirection getTurnDirection(CardinalDirection cardinalDirection) {
        switch (cardinalDirection.toDegrees() - this.toDegrees()) {
            //Right
            case -270:
            case 90:
                return TurnDirection.RIGHT;
            case 270:
            case -90:
                return TurnDirection.LEFT;
            case -180:
            case 180:
                return TurnDirection.REVERSE;
            case 0:
                return TurnDirection.STRAIGHT;
            default:
                return null;
        }
    }

    /**
     * Gets current direction.
     *
     * @return the current direction
     */
    public int[] getDirectionVector() {
        int[] array = new int[2];
        switch (this) {
            case NORTH:
                array[1] = 1;
                break;
            case EAST:
                array[0] = 1;
                break;
            case SOUTH:
                array[1] = -1;
                break;
            case WEST:
                array[0] = -1;
                break;
        }
        return array;
    }

    /**
     * Turns right.
     */
    public CardinalDirection turnRight() {
        switch (this) {
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            case WEST:
                return NORTH;
            default:
                return null;
        }
    }

    /**
     * Turns left.
     */
    public CardinalDirection turnLeft() {
        switch (this) {
            case NORTH:
                return WEST;
            case EAST:
                return NORTH;
            case SOUTH:
                return EAST;
            case WEST:
                return SOUTH;
            default:
                return null;
        }
    }

    public static CardinalDirection stringToDirection(String input) {
        input = input.toLowerCase();
        switch (input) {
            case "north":
                return CardinalDirection.NORTH;
            case "east":
                return CardinalDirection.EAST;
            case "south":
                return CardinalDirection.SOUTH;
            case "west":
                return CardinalDirection.WEST;
            default:
                return null;

        }
    }

}
