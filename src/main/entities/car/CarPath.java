package main.entities.car;

import main.entities.RoadSegment;
import main.entities.interfaces.CarMovable;
import main.entities.intersection.Intersection;
import main.entities.lane.CarDestroy;
import main.entities.lane.CarSpawn;
import main.entities.lane.Lane;
import main.utils.enums.TurnDirection;

import java.util.HashMap;
import java.util.LinkedList;

public class CarPath {
    private final LinkedList<CarMovable> carPath = new LinkedList<>();
    private final HashMap<Car, Integer> carPosition = new HashMap<>();
    private boolean pathComplete;

    public CarPath() {
        pathComplete = false;
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
     * Adds an item to an initialised carPath
     *
     * @param addedObject the object to be added to a path
     * @return whether the object could be added
     */
    public boolean addPartToPath(CarMovable addedObject) {
        if (!pathComplete && !carPath.isEmpty() && carPath.get(carPath.size() - 1) != addedObject && addedObject != null) {
            //Now check we can actually path to the object from the last lane position.

            carPath.add(addedObject);
        }
        return false;
    }

    /**
     * Finishes the path with a final destructor lane
     *
     * @param destructor the destructor lane
     * @return whether the path was completed and fully connected.
     */
    public boolean finalisePath(CarDestroy destructor) {
        if (!pathComplete) {
            if (carPath.get(carPath.size() - 1).getClass() == Intersection.class) {
                boolean foundConnection = false;
                Intersection intersection = (Intersection) carPath.get(carPath.size() - 1);
                for (RoadSegment roadSegment : intersection.getRoadSegments()) {
                    for (Lane lane : roadSegment.getLanes()) {
                        if (lane == destructor) {
                            foundConnection = true;
                        }
                    }

                }
                if (!foundConnection) {
                    return false;
                }
            } else {
                return false;
            }
            addPartToPath(destructor);
            pathComplete = true;
        }
        return true;
    }

    /**
     * Gets the item at a position in the car path
     *
     * @param position the position in the car path
     * @return the item in the position
     */
    public CarMovable get(int position) {
        if (pathComplete) {
            return carPath.get(position);
        }
        return null;
    }

    /**
     * Gets the position of a car in the path
     *
     * @param car the car to get the position of
     * @return the position of the car
     */
    public CarMovable getCarPosition(Car car) {
        if (pathComplete && !carPath.isEmpty() && carPosition.containsKey(car)) {
            return carPath.get(carPosition.get(car));
        }
        return null;
    }

    /**
     * Gets the position of a car in the path
     *
     * @param car the car to get the position of
     * @return the position of the car
     */
    public CarMovable getNextCarPosition(Car car) {
        if (pathComplete && !carPath.isEmpty() && carPosition.containsKey(car)) {
            return carPath.get(carPosition.get(car)+1);
        }
        return null;
    }

    /**
     * Adds a car to the position of the first object
     *
     * @param car the car to be added
     * @return whether the car was added correctly
     */
    public boolean addCar(Car car) {
        if (pathComplete && !carPosition.containsKey(car)) {
            carPosition.put(car, 0);
            return true;
        }
        return false;
    }



    /**
     * Moves a car to the next position in the path, or deletes it if it has reached the end of the path
     *
     * @param car the car to be moved
     * @return whether it was moved/exists to be moved
     */
    public boolean moveCarToNext(Car car) {
        if (pathComplete && carPosition.containsKey(car)) {
            if ((carPosition.get(car) + 1) >= carPath.size()) {
                carPath.get(carPosition.get(car)).removeCar(car);
                carPosition.remove(car);
            } else if (carPath.get(carPosition.get(car) + 1).getClass() == CarDestroy.class) {
                carPosition.remove(car);
            } else {
                carPosition.put(car, carPosition.get(car) + 1);
            }
            return true;
        }
        return false;
    }

    /**
     * Gets the next lane a car will enter, excluding the one it is in now
     *
     * @param car the car
     * @return the next lane it will enter
     */
    public Lane getNextLane(Car car) {
        if (pathComplete && carPosition.containsKey(car) && carPosition.get(car) < carPath.size()) {
            for (int i = carPosition.get(car) + 1; i < carPath.size(); i++) {
                if (carPath.get(i) instanceof Lane) {
                    return (Lane) carPath.get(i);
                }
            }
        }
        return null;
    }

    /**
     * Gets the current lane a car is in
     *
     * @param car the car
     * @return the lane the car is in or null
     */
    public Lane getCurrentLane(Car car) {
        if (pathComplete && carPosition.containsKey(car)) {
            if (carPath.get(carPosition.get(car)) instanceof Lane) {
                return (Lane) carPath.get(carPosition.get(car));

            }
        }
        return null;
    }

    /**
     * Gets the current Intersection a car is in
     *
     * @param car the car
     * @return the intersection the car is in or null
     */
    public Intersection getCurrentIntersection(Car car) {
        if (pathComplete && carPosition.containsKey(car)) {
            if (carPath.get(carPosition.get(car)) instanceof Intersection) {
                return (Intersection) carPath.get(carPosition.get(car));

            }
        }
        return null;
    }

    /**
     * Gets the next intersection the car will go through
     *
     * @param car the car to check for traveling through
     * @return the next intersection
     */
    public Intersection getNextIntersection(Car car) {
        if (pathComplete && carPosition.containsKey(car) && carPosition.get(car) < carPath.size()) {
            for (int i = carPosition.get(car) + 1; i < carPath.size(); i++) {
                if (carPath.get(i).getClass() == Intersection.class) {
                    return (Intersection) carPath.get(i);
                }
            }
        }
        return null;
    }

    /**
     * Gets the current turn direction
     *
     * @param car the car to check for turning
     * @return the turn direction
     */
    public TurnDirection getCurrentTurn(Car car) {
        if (pathComplete && carPosition.containsKey(car)) {

            Lane lane1 = getCurrentLane(car);
            Lane lane2 = getNextLane(car);
            if(lane1 == null){
                lane1 = (Lane)carPath.get(carPosition.get(car) - 1);
            }
            if(lane1 != null && lane2 != null){
                return lane1.getDirection().getTurnDirection(lane2.getDirection());
            }
        }
        return null;
    }

    /**
     * Gets the number of items/nodes in the path
     *
     * @return the number of items/nodes in the path
     */
    public int getSize() {
        if (pathComplete) {
            return carPath.size();
        }
        return 0;
    }

}
