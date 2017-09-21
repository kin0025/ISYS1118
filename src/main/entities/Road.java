package main.entities;


import main.entities.car.Car;
import main.entities.intersection.Intersection;
import main.entities.lane.CarDestroy;
import main.entities.lane.Lane;
import main.utils.BoundingBox;
import main.utils.DimensionManager;
import main.utils.enums.CardinalDirection;
import main.utils.enums.Orientation;
import main.utils.enums.TurnDirection;

import java.util.ArrayList;
import java.util.HashMap;

public class Road {
    private final ArrayList<Lane> lanes;
    private final Orientation orientation;
    private final HashMap<Intersection, CardinalDirection> intersectionDirections = new HashMap<>();
    private final BoundingBox boundingBox;


    public Road(Orientation orientation, BoundingBox boundingBox) {
        this.orientation = orientation;
        this.boundingBox = boundingBox;

        lanes = new ArrayList<>(4);

        for (int i = 0; i < DimensionManager.numberOfLanesPerRoad; i++) {
            ArrayList<TurnDirection> turnDirections = new ArrayList<>();
            CardinalDirection laneDirection;
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
                    laneDirection = CardinalDirection.NORTH;
                } else {
                    laneDirection = CardinalDirection.SOUTH;
                }
            } else {
                yMin = boundingBox.getyMin() + (DimensionManager.widthOfLanePixels * i);
                yMax = yMin + DimensionManager.widthOfLanePixels;
                xMax = boundingBox.getxMax();
                xMin = boundingBox.getxMin();

                if (i < lanes.size() / 2) {
                    laneDirection = CardinalDirection.EAST;
                } else {
                    laneDirection = CardinalDirection.WEST;
                }
            }
            lanes.add(new Lane(laneDirection, turnDirections, laneDistance, new BoundingBox(xMin, yMin, xMax, yMax)));
        }
    }

    public void addIntersection(Intersection intersection, CardinalDirection directionFromIntersection) {
        intersectionDirections.put(intersection, directionFromIntersection.reverse());

    }

    public void addLane(Lane lane) {
        lanes.add(lane);

    }

    public Lane getLane(CardinalDirection direction,TurnDirection turnDirection){
        for(Lane lane : lanes){
            if(lane.getDirection() == direction && lane.hasTurnDirection(turnDirection)){
                return lane;
            }
        }
        return null;
    }

    public CarDestroy getDestroyerLane(CardinalDirection direction){
        for(Lane lane : lanes){
            if(lane.getDirection() == direction && lane.getClass() == CarDestroy.class){
                return (CarDestroy)lane;
            }
        }
        return null;
    }

    public void addDestroyerLane() {
        //FIXME
        lanes.add(new CarDestroy(null, null, 0, boundingBox));

    }

    public void stopCars(Intersection intersection) {
        CardinalDirection direction = getIntersectionDirection(intersection);
        for (Lane lane : lanes) {
            if (lane.getDirection() == direction) {
                lane.stopFirstCar();
            }
        }
    }

    public void startCars(Intersection intersection) {
        CardinalDirection direction = getIntersectionDirection(intersection);
        for (Lane lane : lanes) {
            if (lane.getDirection() == direction) {
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
     * @return whether cars are inside the road
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

    /**
     * Gets the direction of an intersection relative to the road, if it is attached to one.
     *
     * @param intersection The intersection you want the direction of
     * @return a direction, or null. E.g if a road is leaving the east end of an intersection, the intersection is on
     * the west end of the road. The road will return a direction of the value west.
     */
    public boolean hasIntersection(Intersection intersection) {
        return intersectionDirections.containsKey(intersection);
    }

    /**
     * Returns the number of intersections added to the road
     * @return the number of intersections
     */
    public int numberOfIntersections() {
        return intersectionDirections.size();
    }

}
