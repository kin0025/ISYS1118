package main.entities.car;

import main.entities.Road;
import main.entities.interfaces.CarMovable;
import main.entities.intersection.Intersection;
import main.entities.lane.CarDestroy;
import main.entities.lane.CarSpawn;
import main.entities.lane.Lane;
import main.exceptions.PathNotFoundException;
import main.utils.Direction;
import main.utils.enums.TurnDirection;

import java.util.ArrayList;
import java.util.HashMap;

public class CarPath {
    private final ArrayList<CarMovable> carPath = new ArrayList<>();
    private final HashMap<Car, Integer> carPosition = new HashMap<>();
    private boolean pathComplete;

    public CarPath() {
        pathComplete = false;
    }

    public ArrayList<CarMovable> findPathFromIntersections(ArrayList<Intersection> pathIntersections, Lane initialLane, Lane endLane) throws
            PathNotFoundException {
        ArrayList<CarMovable> carPath = new ArrayList<>(pathIntersections.size() * 2);

        //Add this lane as the first one in the path
        carPath.add(initialLane);

        Lane currentLane = initialLane;

        //Then iterate through all the intersections this will pass through.
        for (int i = 0; i < pathIntersections.size() - 1; i++) {
            //Add the intersection the car will go to first
            carPath.add(pathIntersections.get(i));

            //Now we figure out what lane we want to go to next
            Intersection currentIntersection = pathIntersections.get(i);
            Intersection nextIntersection = pathIntersections.get(i + 1);
            boolean laneFound = false;
            //Find the next road.
            for (Road intersectionRoad : nextIntersection.getRoads()) {
                if (intersectionRoad.getIntersectionDirection(nextIntersection) != null) {
                    //We've found the road! Now find the turn direction.
                    TurnDirection turnDirection = currentLane.getDirection().getTurnDirection
                            (currentIntersection
                                    .getRoadDirection(intersectionRoad));

                    //Save the compass direction of the road as well.
                    Direction laneDirection = nextIntersection.getRoadDirection(intersectionRoad);

                    //Now we pick a lane to go to - find the correct lane in the road to move the car to.
                    for (Lane lane : intersectionRoad.getLanes()) {
                        //We've iterated through all lanes, now we find a lane that is going in the right direction
                        // and has the correct turn direction.
                        if (lane.getDirection().getDirection().equals(laneDirection.getDirection()) &&
                                lane.hasTurnDirection(turnDirection)) {
                            laneFound = true;
                            //If we find the right lane, add and move the lane.
                            carPath.add(lane);
                            currentLane = lane;
                            break;
                        }

                    }
                }
            }
            if (!laneFound) {
                throw new PathNotFoundException("Couldn't find a connecting lane when generating a path.");
            }
        }
        boolean found = false;
        for (Road road : pathIntersections.get(pathIntersections.size() - 1).getRoads()
                ) {
            for (Lane lane : road.getLanes()
                    ) {
                if (lane.equals(endLane)) {
                    found = true;
                }
            }
        }
        if (found) {
            carPath.add(endLane);
        } else {
            throw new PathNotFoundException("End Lane disconnected");
        }
        return carPath;
    }

    public boolean isPathComplete() {
        return pathComplete;
    }

    /**
     * Initialised a path for adding items to it - needs a spawn lane added.
     *
     * @param initialLane the spawn lane to add to the end of the path
     * @return Whether the initialisation was correct/worked.
     */
    public boolean initialisePath(CarSpawn initialLane) {
        if (!pathComplete && carPath.isEmpty()) {
            carPath.add(initialLane);
            return true;
        }
        return false;
    }

    /**
     * @param addedObject
     * @return
     */
    public boolean addPartToPath(CarMovable addedObject) {
        if (!pathComplete && !carPath.isEmpty() && carPath.get(carPath.size() - 1) != addedObject) {
            //Now check we can actually path to the object from the last lane position.

            carPath.add(addedObject);
        }
        return false;
    }

    public boolean finalisePath(CarDestroy destructor) {
        if (!pathComplete) {
            //TODO Some logic to prevent errors and check that the path is valid
            addPartToPath(destructor);
            pathComplete = true;
        }
        return false;
    }

    public CarMovable get(int position) {
        if (pathComplete) {
            return carPath.get(position);
        }
        return null;
    }

    public CarMovable getCarPosition(Car car) {
        if (pathComplete) {
            return carPath.get(carPosition.get(car));
        }
        return null;
    }

    public boolean addCar(Car car) {
        if (pathComplete && !carPosition.containsKey(car)) {
            carPosition.put(car, 0);
            return true;
        }
        return false;
    }

    public boolean moveCarToNext(Car car) {
        if (pathComplete && carPosition.containsKey(car)) {
            if (carPosition.get(car) >= carPath.size()) {
                carPosition.remove(car);
            } else {
                carPosition.put(car, carPosition.get(car) + 1);
            }
            return true;
        }
        return false;
    }

    public int getSize() {
        if (pathComplete) {
            return carPath.size();
        }
        return 0;
    }

}
