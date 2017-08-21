package main.utils;

import main.exceptions.IncorrectDirectionException;

/**
 * The type main.utils.Direction. Used to indicate what direction an object is facing
 */
public class Direction {
    /**
     * The constant north.
     */
    public static final int north = 0;
    /**
     * The constant east.
     */
    public static final int east = 1;
    /**
     * The constant south.
     */
    public static final int south = 2;
    /**
     * The constant west.
     */
    public static final int west = 3;
    private int direction;


    /**
     * Instantiates a new main.utils.Direction.
     *
     * @param direction the direction to start facing
     * @throws IncorrectDirectionException the incorrect direction exception
     */
    public Direction(int direction) throws IncorrectDirectionException {
        switch (direction) {
            case north:
                this.direction = north;
                break;
            case east:
                this.direction = east;
                break;
            case south:
                this.direction = south;
                break;
            case west:
                this.direction = west;
                break;
            default:
                throw new IncorrectDirectionException("Not a compass direction");
        }
    }

    /**
     * Turns right.
     */
    public void turnRight() {
        if (direction != west) {
            direction++;
        } else {
            direction = north;
        }
    }

    /**
     * Turns left.
     */
    public void turnLeft() {
        if (direction != north) {
            direction--;
        } else {
            direction = west;
        }
    }

    /**
     * Gets current direction.
     *
     * @return the current direction
     */
    public int[] getDirectionVector() {
        int[] array = new int[2];
        switch (direction) {
            case north:
                array[0] = 1;
                break;
            case east:
                array[1] = 1;
                break;
            case south:
                array[0] = -1;
                break;
            case west:
                array[1] = -1;
                break;
        }
        return array;
    }

    /**
     * Gets current direction.
     *
     * @return the current direction
     */
    public int getDirection() {
        return direction;
    }

    public int getTurnDirection(Direction turningTo) {
        switch(turningTo.direction - direction){
            case 1:
                return 1;
            case -1:
                return -1;
            case 2:
                return 2;
            case 0:
                return 0;
            case -3:
                return 1;
            case 3:
                return -1;
                default:
                    throw new RuntimeException("Something went seriously wrong or there is a logic error");
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        Direction comparator = (Direction) o;
        return direction == comparator.direction;
    }
}




