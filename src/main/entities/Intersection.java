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

}
