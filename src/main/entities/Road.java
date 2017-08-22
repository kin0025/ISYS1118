package main.entities;


import main.utils.Direction;
import main.utils.Orientation;

import java.util.ArrayList;
import java.util.HashMap;

public class Road {
    private Lane[] lanes;
    private Orientation orientation;
    private HashMap<Intersection, Direction.COMPASS_DIRECTION> intersectionDirections;


    public Road(Orientation orientation) {
        this.orientation = orientation;
        lanes = new Lane[4];
        //Adithya Please rewrite or comment. Not very good code, but needed for adding gui.
        for (int i = 0; i < lanes.length; i++) {
            ArrayList<Direction.TURN_DIRECTION> turnDirections = new ArrayList<>();
            Direction laneDirection;
            if (i % 2 == 0) {
                turnDirections.add(Direction.TURN_DIRECTION.LEFT);
                turnDirections.add(Direction.TURN_DIRECTION.STRAIGHT);
            } else {
                turnDirections.add(Direction.TURN_DIRECTION.RIGHT);
            }

            if (orientation.getCurrentOrientation() == Orientation.ENUM.VERTICAL) {
                if (i < lanes.length / 2) {
                    laneDirection = new Direction(Direction.COMPASS_DIRECTION.NORTH);
                }else{
                    laneDirection = new Direction(Direction.COMPASS_DIRECTION.SOUTH);

                }
            }else{
                if (i < lanes.length / 2) {
                    laneDirection = new Direction(Direction.COMPASS_DIRECTION.EAST);
                }else{
                    laneDirection = new Direction(Direction.COMPASS_DIRECTION.WEST);
                }
            }
            lanes[i] = new Lane(this, laneDirection, turnDirections);
        }
    }

    public void addIntersection(Intersection intersection, Direction directionFromIntersection) {
        directionFromIntersection.reverse();
        intersectionDirections.put(intersection, directionFromIntersection.getDirection());
        //We don't want to change the original value
        directionFromIntersection.reverse();
    }

    public Lane[] getLanes() {
        return lanes;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Gets the direction of an intersection relative to the road, if it is attached to one.
     *
     * @param intersection The intersection you want the direction of
     * @return a direction, or null. E.g if a road is leaving the east end of an intersection, the intersection is on
     * the west end of the road. The road will return a direction of the value west.
     */
    public Direction.COMPASS_DIRECTION getIntersectionDirection(Intersection intersection) {
        return intersectionDirections.get(intersection);
    }
}
