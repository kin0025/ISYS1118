/*
 * ISYS1118: Assignment.
 * Group: Null
 * File created by: Alexander Kinross-Smith, s3603437
 */

package main.utils.enums;

/**
 * The type main.utils.enums.Orientation. Used to indicate whether something is horizontal or vertical
 */
public enum Orientation {
    HORIZONTAL, VERTICAL;

    public Orientation swapValue() {
        if (this.equals(HORIZONTAL)){
            return VERTICAL;
        }else{
            return HORIZONTAL;
        }
    }
}
