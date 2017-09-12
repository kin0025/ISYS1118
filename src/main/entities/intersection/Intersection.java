/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.entities.intersection;

import main.entities.Car;
import main.entities.Road;
import main.entities.interfaces.CarMoveable;
import main.entities.interfaces.SimulationTimed;
import main.utils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Intersection implements CarMoveable, SimulationTimed {
    private HashMap<Road, Direction> roadDirections = new HashMap<>();
    private ArrayList<Road> roads = new ArrayList<>();
    private BoundingBox boundingBox;
    private HashMap<Orientation, TrafficLight> lights = new HashMap<>(2);
    private LinkedList<Car> cars = new LinkedList<>();

    public Intersection(Position centre, int lightTimeVertical, int lightTimeHorizontal, Orientation startingLights) {
        lights.put(Orientation.VERTICAL, new TrafficLight(lightTimeVertical, Orientation.VERTICAL));
        lights.put(Orientation.HORIZONTAL, new TrafficLight(lightTimeHorizontal, Orientation.HORIZONTAL));

        lights.get(startingLights).restartCycle();
        this.boundingBox = new BoundingBox(centre,DimensionManager.widthOfIntersectionPixels, DimensionManager.widthOfIntersectionPixels);
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
    }

    @Override
    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    @Override
    public Direction getDirection() {
        return null;
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }

    public LinkedList<Car> getCars() {
        return cars;
    }

    public HashMap<Orientation, TrafficLight> getLights() {
        return lights;
    }

    /**
     * Gets the direction of an road relative to the intersection, if it is attached to one.
     *
     * @param road The road you want the direction of
     * @return a direction, or null. E.g if a road is leaving the east end of an intersection, a direction of the
     * value east will be returned.
     */
    public Direction getRoadDirection(Road road) {
        return roadDirections.get(road);
    }

    public void addRoad(Road road, Direction direction) {
        roads.add(road);
        roadDirections.put(road, direction);
    }

    public void setLightTiming(Orientation orientation, int newGreenTime) {
        lights.get(orientation).setTiming(newGreenTime);
    }

    public boolean addCar(Car car) {
        return false;
    }

    public void removeRoads(){
        roads = null;
        roads = new ArrayList<>();
    }

    @Override
    public boolean removeCar(Car car) {
        return false;
    }

    @Override
    public boolean moveCar(CarMoveable moveTo) {
        return false;
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
