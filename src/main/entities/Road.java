package main.entities;


import main.entities.intersection.Intersection;
import main.entities.lane.CarDestroy;
import main.entities.lane.Lane;
import main.utils.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Road {
    private ArrayList<Lane> lanes;
    private Orientation orientation;
    private HashMap<Intersection, CardinalDirection> intersectionDirections = new HashMap<>();
    private BoundingBox boundingBox;
    private int row;
    private int column;


    public Road(Orientation orientation, BoundingBox boundingBox, int row, int column) {
        this.orientation = orientation;
        this.boundingBox = boundingBox;
        this.row = row;
        this.column = column;
        lanes = new ArrayList<>(4);

        for (int i = 0; i < DimensionManager.numberOfLanesPerRoad; i++) {
            ArrayList<TurnDirection> turnDirections = new ArrayList<>();
            Direction laneDirection;
            int laneDistance = i % (DimensionManager.numberOfLanesPerRoad / 2);
            if (i % 2 == 0) {
                turnDirections.add(TurnDirection.LEFT);
                turnDirections.add(TurnDirection.STRAIGHT);
            } else {
                turnDirections.add(TurnDirection.RIGHT);
            }

            double xMin, xMax, yMin, yMax;
            if (orientation == Orientation.VERTICAL) {
                xMin = boundingBox.getxMin() + (DimensionManager.widthOfLanePixels * i);
                xMax = xMin + DimensionManager.widthOfLanePixels;
                yMax = boundingBox.getyMax();
                yMin = boundingBox.getyMin();
                if (i < DimensionManager.numberOfLanesPerRoad / 2) {
                    laneDirection = new Direction(CardinalDirection.NORTH);
                } else {
                    laneDirection = new Direction(CardinalDirection.SOUTH);
                }
            } else {
                yMin = boundingBox.getyMin() + (DimensionManager.widthOfLanePixels * i);
                yMax = yMin + DimensionManager.widthOfLanePixels;
                xMax = boundingBox.getxMax();
                xMin = boundingBox.getxMin();

                if (i < lanes.size() / 2) {
                    laneDirection = new Direction(CardinalDirection.EAST);
                } else {
                    laneDirection = new Direction(CardinalDirection.WEST);
                }
            }
            lanes.add(new Lane(laneDirection, turnDirections, laneDistance, new BoundingBox(xMin, yMin, xMax, yMax)));
        }
    }

    public void addIntersection(Intersection intersection, Direction directionFromIntersection) {
        directionFromIntersection.reverse();
        intersectionDirections.put(intersection, directionFromIntersection.getDirection());
        //We don't want to change the original value
        directionFromIntersection.reverse();
    }

    public void addLane(Lane lane) {
        lanes.add(lane);

    }

    public int[] getRoadCoordinate() {
        return new int[]{column, row};
    }

    public void addDestroyerLane() {
        //FIXME
        lanes.add(new CarDestroy(null, null, 0, boundingBox));

    }

    public void stopCars(Intersection intersection) {
        CardinalDirection direction = intersectionDirections.get(intersection);
        for (Lane lane : lanes) {
            if (lane.getDirection().getDirection() == direction) {
                lane.stopFirstCar();
            }
        }
    }

    public void startCars(Intersection intersection) {
        CardinalDirection direction = intersectionDirections.get(intersection);
        for (Lane lane : lanes) {
            if (lane.getDirection().getDirection() == direction) {
                lane.startFirstCar();
            }
        }
    }


    public void incrementTime() {
        for (Lane lane : lanes) {
            lane.incrementTime();
        }
        checkCarsInsideRoad();
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

    public BoundingBox getBoundingBox() {
        return boundingBox;
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
