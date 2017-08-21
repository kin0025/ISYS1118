package main.entities;


import main.utils.Orientation;

public class Road {
    private Lane[] lanes;
    private Orientation orientation;
private Intersection[] intersections;
    public Lane[] getLanes() {
        return lanes;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public Intersection[] getIntersections() {
        return intersections;
    }
}
