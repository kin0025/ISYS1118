package main.entities;

import main.utils.Direction;
import main.utils.Position;

import java.util.HashMap;
import java.util.LinkedList;

public class Intersection {
    private HashMap<Direction, Road> roads;
    private Position position;
    private TrafficLight[] lights;
    private LinkedList<Car> cars;

    public Position getPosition() {
        return position;
    }

    public HashMap<Direction, Road> getRoads() {
        return roads;
    }

    public TrafficLight[] getLights() {
        return lights;
    }

    public LinkedList<Car> getCars() {
        return cars;
    }

    //Probably shouldn't be void
    public boolean addCar(){
        return false;
    }
}
