package main.entities;


import main.utils.Direction;
import main.utils.Orientation;

import java.util.HashMap;

public class Road {
    private Lane[] lanes;
    private Orientation orientation;
    private HashMap<Intersection,Direction> intersectionDirections;

    public Lane[] getLanes() {
        return lanes;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Gets the direction of an intersection relative to the road, if it is attached to one.
     * @param intersection The intersection you want the direction of
     * @return a direction, or null. E.g if a road is leaving the east end of an intersection, the intersection is on
     * the west end of the road. The road will return a direction of the value west.
     */
    public Direction getIntersectionDirection(Intersection intersection) {
        return intersectionDirections.get(intersection);
    }
}
