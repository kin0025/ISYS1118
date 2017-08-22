package main.entities;

import main.entities.interfaces.CarMoveable;
import main.utils.Direction;
import main.utils.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Intersection implements CarMoveable{
    private HashMap<Road,Direction> roadDirections;
    private ArrayList<Road> roads;
    private Position position;
    private TrafficLight[] lights;
    private LinkedList<Car> cars;

    public Position getPosition() {
        return position;
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }

    /**
     * Gets the direction of an road relative to the intersection, if it is attached to one.
     * @param road The road you want the direction of
     * @return a direction, or null. E.g if a road is leaving the east end of an intersection, a direction of the
     * value east will be returned.
     */
    public Direction getRoadDirection(Road road){
        return roadDirections.get(road);
    }

    public TrafficLight[] getLights() {
        return lights;
    }

    public LinkedList<Car> getCars() {
        return cars;
    }

    public boolean addCar(Car car) {
        return false;
    }

    @Override
    public boolean removeCar(Car car) {
        return false;
    }

    @Override
    public boolean moveCar(Car car) {
        return false;
    }
}
