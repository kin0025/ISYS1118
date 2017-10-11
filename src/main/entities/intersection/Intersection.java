/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.entities.intersection;

import main.entities.Road;
import main.entities.car.Car;
import main.entities.interfaces.CarMovable;
import main.entities.interfaces.SimulationTimed;
import main.utils.BoundingBox;
import main.utils.DimensionManager;
import main.utils.Position;
import main.utils.enums.CardinalDirection;
import main.utils.enums.CollisionStatus;
import main.utils.enums.LightStatus;
import main.utils.enums.Orientation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Intersection implements CarMovable, SimulationTimed {
    private final HashMap<Road, CardinalDirection> roadDirections = new HashMap<>();
    private ArrayList<Road> roads = new ArrayList<>();
    private final BoundingBox boundingBox;
    private final HashMap<Orientation, TrafficLight> lights = new HashMap<>(2);
    private final LinkedList<Car> carNorth = new LinkedList<>();
    private final LinkedList<Car> carSouth = new LinkedList<>();
    private final LinkedList<Car> carEast = new LinkedList<>();
    private final LinkedList<Car> carWest = new LinkedList<>();
    private final LinkedList<Car> cars = new LinkedList<>();

    public Intersection(Position centre, int lightTimeVertical, int lightTimeHorizontal, Orientation startingLights) {
        lights.put(Orientation.VERTICAL, new TrafficLight(lightTimeVertical, Orientation.VERTICAL));
        lights.put(Orientation.HORIZONTAL, new TrafficLight(lightTimeHorizontal, Orientation.HORIZONTAL));

        lights.get(startingLights).restartCycle();
        this.boundingBox = new BoundingBox(centre, DimensionManager.widthOfIntersectionPixels, DimensionManager.widthOfIntersectionPixels);
    }

    public void incrementTime() {
        //Stop cars that are going into the intersection on the correct lanes.
        for (TrafficLight light : lights.values()) {
            //Increment the time of the lights
            LightStatus original = light.getStatus();
            light.incrementTime();
            //Detect the transition from amber to red, and swap the lights then.
            if (original == LightStatus.AMBER && light.getStatus() == LightStatus.RED) {
                lights.get(light.getOrientation().swapValue()).restartCycle();
                for (Road road : roads) {
                    //Find if the road's orientation matches that of the light that just changed to red.
                    if (road.getOrientation() == light.getOrientation().swapValue()) {
                        //If it isn't in the same direction, start the cars.
                        road.startCars(this);
                    } else {
                        //If it doesn't, stop cars.
                        road.stopCars(this);
                    }

                }
            }
        }
        checkCarCollisions();
        checkCarPositions();
        for(Car car : cars){
            car.start();
            car.incrementTime();
        }
    }

    /**
     * Iterates through the linked list and stops any cars that are getting too close to each other.
     * Returns True if no collisions occurred
     */
    private void checkCarCollisions() {
        boolean carTooClose = false;
        for (int i = cars.size() - 1; i > 0; i--) {
            Car currentCar = cars.get(i);
            Car nextCar = cars.get(i - 1);
            if (nextCar != null) {
                if (currentCar.getPosition().getDifference(nextCar.getPosition()) < DimensionManager.minimumFollowingDistancePixels) {
                    currentCar.stop();
                } else {
                    currentCar.start();
                }
            }
        }
    }

    /**
     * Iterates through the linked list and finds any cars outside the box - moves them to the next lane if they are outside the lane
     */
    void checkCarPositions() {
        ArrayList<Car> move = new ArrayList<>();
        for (Car car : cars) {
            if (!boundingBox.isInsideBoundingBox(car.getPosition())) {
                if (!car.isInsideParent()) {
                    move.add(car);
                }
            }
        }
        for (Car car : move) {
            car.moveToNext(this);
        }
    }

    @Override
    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    @Override
    public CardinalDirection getDirection() {
        return null;
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }

    public LinkedList<Car> getCars() {
        return cars;
    }

    public LightStatus getLightStatus(Orientation orientation) {
        return lights.get(orientation).getStatus();
    }

    /**
     * Gets the direction of an road relative to the intersection, if it is attached to one.
     *
     * @param road The road you want the direction of
     * @return a direction, or null. E.g if a road is leaving the east end of an intersection, a direction of the
     * value east will be returned.
     */
    public CardinalDirection getRoadDirection(Road road) {
        return roadDirections.get(road);
    }

    public void addRoad(Road road, CardinalDirection direction) {
        roads.add(road);
        roadDirections.put(road, direction);
    }

    public void setLightTiming(Orientation orientation, int newGreenTime) {
        lights.get(orientation).setTiming(newGreenTime);

    }


    public void removeRoads() {
        roads = null;
        roads = new ArrayList<>();
    }


    @Override
    public boolean addCar(Car car) {
        return cars.add(car);
    }

    @Override
    public boolean moveCar(CarMovable moveTo, Car car) {
        if(cars.peek() == car) {
//            cars.peek().moveToNext(this);
            moveTo.addCar(cars.peek());
            return this.removeCar(cars.peek());
        }
        return false;
    }

    @Override
    public boolean removeCar(Car car) {
        if (cars.element() == car) {
            cars.remove();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isInsideBoundingBox(Position position) {
        return boundingBox.isInsideBoundingBox(position);
    }

    @Override
    public Position getCentre() {
        return boundingBox.getCentre();
    }
}
