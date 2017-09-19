/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.utils;

import com.sun.istack.internal.Nullable;
import main.entities.car.Car;
import main.utils.enums.CardinalDirection;
import main.utils.enums.Orientation;
import main.utils.enums.TurnDirection;

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
    private static final int east = 90;
    /**
     * The constant south.
     */
    private static final int south = 180;
    /**
     * The constant west.
     */
    private static final int west = 270;

    //Our actual stored direction. Angle in degrees.
    private int direction;


    /**
     * Instantiates a new main.utils.Direction.
     *
     * @param direction the direction to start facing
     */
    public Direction(CardinalDirection direction) {
        switch (direction) {
            case NORTH:
                this.direction = north;
                break;
            case EAST:
                this.direction = east;
                break;
            case SOUTH:
                this.direction = south;
                break;
            case WEST:
                this.direction = west;
                break;
            default:
        }
    }

    /**
     * Turns right.
     */
    public void turnRight() {
        if (direction != west) {
            direction += 90;
        } else {
            direction = north;
        }
    }

    /**
     * Turns left.
     */
    public void turnLeft() {
        if (direction != north) {
            direction -= 90;
        } else {
            direction = west;
        }
    }

    /**
     * Reverses direction.
     */
    public void reverse() {
        switch (direction) {
            case north:
                direction = south;
                break;
            case east:
                direction = west;
                break;
            case south:
                direction = north;
                break;
            case west:
                direction = east;
                break;
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
                array[1] = 1;
                break;
            case east:
                array[0] = 1;
                break;
            case south:
                array[1] = -1;
                break;
            case west:
                array[0] = -1;
                break;
        }
        return array;
    }

    /**
     * Gets current direction.
     *
     * @return the current direction
     */
    @Nullable
    public CardinalDirection getDirection() {
        switch (direction) {
            case north:
                return CardinalDirection.NORTH;
            case east:
                return CardinalDirection.EAST;
            case south:
                return CardinalDirection.SOUTH;
            case west:
                return CardinalDirection.WEST;
            default:
                return null;
        }
    }

    /**
     * Gets the turn direction from this direction turning into the argument direction
     *
     * @param turningTo the direction we are turning to
     * @return The turn direction enum
     */
    public TurnDirection getTurnDirection(Direction turningTo) {
        switch (turningTo.direction - direction) {
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
                throw new RuntimeException("Something went seriously wrong or there is a logic error");
        }
    }

    public Orientation getOrientation() {
        if (direction == north || direction == south) {
            return Orientation.VERTICAL;
        } else {
            return Orientation.HORIZONTAL;
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




