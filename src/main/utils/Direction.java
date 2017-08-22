/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.utils;

import main.exceptions.IncorrectDirectionException;

/**
 * The type main.utils.Direction. Used to indicate what direction an object is facing
 */
public class Direction {
    /**
     * The constant north.
     */
    private static final int north = 0;
    /**
     * The constant east.
     */
    private static final int east = 1;
    /**
     * The constant south.
     */
    private static final int south = 2;
    /**
     * The constant west.
     */
    private static final int west = 3;

    //Our actual stored direction
    private int direction;


    /**
     * Instantiates a new main.utils.Direction.
     *
     * @param direction the direction to start facing
     * @throws IncorrectDirectionException the incorrect direction exception
     */
    public Direction(COMPASS_DIRECTION direction) throws IncorrectDirectionException {
        switch (direction) {
            case NORTH:
                this.direction = 0;
                break;
            case EAST:
                this.direction = 1;
                break;
            case SOUTH:
                this.direction = 2;
                break;
            case WEST:
                this.direction = 3;
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
    public COMPASS_DIRECTION getDirection() {
        switch (direction) {
            case north:
                return COMPASS_DIRECTION.NORTH;
            case east:
                return COMPASS_DIRECTION.EAST;
            case south:
                return COMPASS_DIRECTION.SOUTH;
            case west:
                return COMPASS_DIRECTION.WEST;
            default:
                return null;
        }
    }

    /**
     * Gets the turn direction from this direction turning into the argument direction
     *
     * @param turningTo the direction we are turning to
     * @return
     */
    public TURN_DIRECTION getTurnDirection(Direction turningTo) {
        //TODO make neater
        switch (turningTo.direction - direction) {
            //Right
            case 1:
                return TURN_DIRECTION.RIGHT;
            case -1:
                return TURN_DIRECTION.LEFT;
            case 2:
                return TURN_DIRECTION.REVERSE;
            case 0:
                return TURN_DIRECTION.STRAIGHT;
            case -3:
                return TURN_DIRECTION.RIGHT;
            case 3:
                return TURN_DIRECTION.LEFT;
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

    public enum TURN_DIRECTION {LEFT, STRAIGHT, RIGHT, REVERSE}


    public enum COMPASS_DIRECTION {NORTH, EAST, SOUTH, WEST}
}




