package main.entities;

import main.utils.Position;

import java.util.LinkedList;

public class Intersection {
    private Position position;
    private Road[] roads;
    private TrafficLight[] lights;
    private LinkedList<Car> cars;

    public Position getPosition() {
        return position;
    }

    public Road[] getRoads() {
        return roads;
    }

    public TrafficLight[] getLights() {
        return lights;
    }

    public LinkedList<Car> getCars() {
        return cars;
    }
}
