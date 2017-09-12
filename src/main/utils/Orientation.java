package main.utils;

/**
 * The type main.utils.Orientation. Used to indicate whether something is horizontal or vertical
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
