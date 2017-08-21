package main.entities;

public class CarDestroy extends Intersection {
    @Override
    public boolean addCar(Car car){
        car = null;
        return;
    }
}
