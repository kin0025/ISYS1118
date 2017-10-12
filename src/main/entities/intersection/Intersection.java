/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.entities.intersection;

import main.entities.RoadSegment;
import main.entities.car.Car;
import main.entities.interfaces.CarMovable;
import main.entities.interfaces.SimulationTimed;
import main.utils.BoundingBox;
import main.utils.DimensionManager;
import main.utils.Position;
import main.utils.enums.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Intersection implements CarMovable, SimulationTimed {
    private final HashMap<RoadSegment, CardinalDirection> roadDirections = new HashMap<>();
    private ArrayList<RoadSegment> roadSegments = new ArrayList<>();
    private final BoundingBox boundingBox;
    private final HashMap<Orientation, TrafficLight> lights = new HashMap<>(2);
    private final HashMap<CarDirection, Car> cars = new HashMap<>();

    class CarDirection {
        TurnDirection turnDirection;
        CardinalDirection directionFrom;

        public CarDirection(TurnDirection turnDirection, CardinalDirection directionFrom) {
            switch (turnDirection) {
                case LEFT:
                case STRAIGHT:
                    this.turnDirection = TurnDirection.STRAIGHT;
                    break;
                case RIGHT:
                case REVERSE:
                    this.turnDirection = TurnDirection.RIGHT;
                    break;
            }
            this.directionFrom = directionFrom;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof CarDirection) {
                CarDirection carDirection = (CarDirection) obj;
                if (this.directionFrom == carDirection.directionFrom && this.turnDirection == carDirection.turnDirection) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            //Enums are basically hash codes anyway, so combine to two
            return turnDirection.ordinal() + (directionFrom.ordinal() * 10);
        }
    }

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
            if (light.getStatus() != LightStatus.GREEN) {
                for (RoadSegment roadSegment : roadSegments) {
                    //Find if the roadSegment's orientation matches that of the light that is not green.
                    if (roadSegment.getOrientation() == light.getOrientation()) {
                        //If it matches, stop cars.
                        roadSegment.stopCars(this);
                    }

                }
            }
            //Detect the transition from amber to red, and swap the lights then.
            if (original == LightStatus.AMBER && light.getStatus() == LightStatus.RED) {
                lights.get(light.getOrientation().swapValue()).restartCycle();

                for (RoadSegment roadSegment : roadSegments) {
                    if (roadSegment.getOrientation() == light.getOrientation().swapValue()) {
                        //If it isn't in the same direction, start the cars.
                        roadSegment.startCars(this);
                    }
                }
            }
        }

        for (Car car : cars.values()) {
            car.incrementTime();
            car.start();
        }
        checkCarPositions();
    }

    /**
     * Finds out if a direction has free spaces or not from a direction
     *
     * @param entryDirection
     * @param turnDirection
     * @return
     */
    public boolean hasFreeSpaces(CardinalDirection entryDirection, TurnDirection turnDirection) {
        CarDirection direction = new CarDirection(turnDirection, entryDirection);
        return hasFreeSpaces(direction);
    }

    /**
     * Finds out if a direction has free spaces or not from a direction
     *
     * @param carDirection
     * @return
     */
    public boolean hasFreeSpaces(CarDirection carDirection) {
        CarDirection direction = new CarDirection(carDirection.turnDirection, carDirection.directionFrom);
        return !cars.containsKey(direction);
    }

    /**
     * Finds out if a direction has free spaces or not from a direction
     *
     * @return
     */
    public boolean hasFreeSpaces(Car car) {
        CarDirection carDirection = new CarDirection(car.getCurrentTurnDirection(), car.getDirection());

        CarDirection direction = new CarDirection(carDirection.turnDirection, carDirection.directionFrom);
        return !cars.containsKey(direction);
    }

    /**
     * Iterates through the linked list and finds any cars outside the box - moves them to the next lane if they are outside the lane
     */
    void checkCarPositions() {
        ArrayList<Car> move = new ArrayList<>();
        for (Car car : cars.values()) {
            if (!car.isInsideParent() && car.getNextLane().getNumberOfFreeSpaces() >= 1) {
                move.add(car);
            }else if(car.isInsideParent()){
                car.start();
            }else{
                car.stop();
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

    public ArrayList<RoadSegment> getRoadSegments() {
        return roadSegments;
    }

    public Car getCar(CardinalDirection direction, TurnDirection turnDirection) {
        return cars.get(new CarDirection(turnDirection, direction));
    }

    public Collection<Car> getCars() {
        return cars.values();
    }

    public LightStatus getLightStatus(Orientation orientation) {
        return lights.get(orientation).getStatus();
    }

    /**
     * Gets the direction of an roadSegment relative to the intersection, if it is attached to one.
     *
     * @param roadSegment The roadSegment you want the direction of
     * @return a direction, or null. E.g if a roadSegment is leaving the east end of an intersection, a direction of the
     * value east will be returned.
     */
    public CardinalDirection getRoadDirection(RoadSegment roadSegment) {
        return roadDirections.get(roadSegment);
    }

    public void addRoad(RoadSegment roadSegment, CardinalDirection direction) {
        roadSegments.add(roadSegment);
        roadDirections.put(roadSegment, direction);
    }

    public void setLightTiming(Orientation orientation, int newGreenTime) {
        lights.get(orientation).setTiming(newGreenTime);
    }


    public void removeRoads() {
        roadSegments = null;
        roadSegments = new ArrayList<>();
    }


    @Override
    public boolean addCar(Car car) {
        if (!cars.containsValue(car)) {
            CarDirection carDirection = new CarDirection(car.getCurrentTurnDirection(), car.getDirection());
            if (hasFreeSpaces(carDirection)) {
                cars.put(carDirection, car);
                car.setParent(boundingBox);
                int counter = 0;
                while (!car.isInsideParent()) {
                    counter++;
                    if (counter > 100) {
                        car.getCarBox().setCentre(boundingBox.getCentre().clone());
                    }
                    car.incrementTime();
                }

                return true;
            }
        }
        return false;
    }

    @Override
    public boolean moveCar(CarMovable moveTo, Car car) {
        if (cars.containsValue(car)) {
            moveTo.addCar(car);
            return this.removeCar(car);
        }
        return false;
    }

    @Override
    public boolean removeCar(Car car) {
        if (cars.containsValue(car)) {
            CarDirection carDirection = new CarDirection(car.getCurrentTurnDirection(), car.getDirection());
            cars.remove(carDirection);
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
