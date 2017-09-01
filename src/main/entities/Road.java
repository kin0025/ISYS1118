package main.entities;


import javafx.geometry.Pos;
import main.entities.intersection.Intersection;
import main.entities.lane.CarDestroy;
import main.entities.lane.CarSpawn;
import main.entities.lane.Lane;
import main.exceptions.PathNotFoundException;
import main.utils.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Road {
    private ArrayList<Lane> lanes;
    private Orientation orientation;
    private HashMap<Intersection, CardinalDirection> intersectionDirections;
    private Position position;

    public Road(Orientation orientation, Position position) {
        this.orientation = orientation;
        this.position = position;
        lanes = new ArrayList<>(4);
        //TODO: @Adithya Please rewrite or comment. Not very good code, but needed for adding gui/prototyping
        for (int i = 0; i < lanes.size(); i++) {
            ArrayList<TurnDirection> turnDirections = new ArrayList<>();
            Direction laneDirection;
            int laneDistance = i % (lanes.size() / 2);
            if (i % 2 == 0) {
                turnDirections.add(TurnDirection.LEFT);
                turnDirections.add(TurnDirection.STRAIGHT);
            } else {
                turnDirections.add(TurnDirection.RIGHT);

            }

            if (orientation == Orientation.VERTICAL) {
                if (i < lanes.size() / 2) {
                    laneDirection = new Direction(CardinalDirection.NORTH);
                } else {
                    laneDirection = new Direction(CardinalDirection.SOUTH);
                }
            } else {
                if (i < lanes.size() / 2) {
                    laneDirection = new Direction(CardinalDirection.EAST);
                } else {
                    laneDirection = new Direction(CardinalDirection.WEST);
                }
            }
            //FIXME Incorrect position applied here.
            lanes.add(new Lane(laneDirection, turnDirections, laneDistance, new Position(this.position.getX(), this.position.getY())));
        }
    }

    public void addIntersection(Intersection intersection, Direction directionFromIntersection) {
        directionFromIntersection.reverse();
        intersectionDirections.put(intersection, directionFromIntersection.getDirection());
        //We don't want to change the original value
        directionFromIntersection.reverse();
    }

    public void addSpawnLane(Direction direction) {
       /* CarSpawn carSpawn;
        try {
            carSpawn = new CarSpawn(direction);
        } catch (PathNotFoundException e) {
            return;
        }
        lanes.add(carSpawn);*/

    }

    public void addDestroyerLane() {
        //lanes.add(new CarDestroy());

    }

    /**
     * Checks that cars are still inside the road and puts them back in if they aren't. Fixes direction of cars if they are moving in the wrong
     * direction.
     *
     * @return
     */
    public boolean checkCarsInsideRoad() {
        return false;
    }

    public Position getPosition() {
        return position;
    }

    public ArrayList<Lane> getLanes() {
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
    public CardinalDirection getIntersectionDirection(Intersection intersection) {
        return intersectionDirections.get(intersection);
    }
}
