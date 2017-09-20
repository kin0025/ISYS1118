/*
 * ISYS1118: Assignment.
 * Group: Null
 * File created by: Alexander Kinross-Smith, s3603437
 */

package main.utils.enums;

import com.sun.org.apache.xpath.internal.operations.Or;

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

    public static Orientation stringToOrientation(String input){
        if(input.equalsIgnoreCase("horizontal")){
            return HORIZONTAL;
        }else if(input.equalsIgnoreCase("vertical")){
            return VERTICAL;
        }
        return null;
    }
}
