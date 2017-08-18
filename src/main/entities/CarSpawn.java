package main.entities;

import main.utils.Position;

import java.util.Queue;

/**
 * Spawns cars.
 */
public class CarSpawn {
    //Get this from the constructor
    private Queue<Intersection> pathIntersections;
    //Generate this in the constructor
    private Queue<Lane> pathLanes;

    private int spawnSpeed;
    private Position spawnPosition;

    //Ticks between each car spawn
    private int spawnDelay;

    //The current time for the spawner - needed for delay logic.
    private long tick = 0;

    /**
     * Instantiates a new Car spawn. Creates a lanes list for cars to follow and figures out the...
     *
     * @param pathIntersections the path intersections
     * @param spawnSpeed        the spawn speed
     * @param spawnPosition     the spawn position
     * @param spawnDelay        the spawn delay
     */
    public CarSpawn(Queue<Intersection> pathIntersections, int spawnSpeed, Position spawnPosition, int spawnDelay) {
        this.pathIntersections = pathIntersections;
        //TODO: Some logic to set the lanes here as well.
        this.spawnSpeed = spawnSpeed;
        this.spawnPosition = spawnPosition;
        this.spawnDelay = spawnDelay;
    }

    /**
     * Spawns a car. Creates a new car, puts it in the correct lane and sends it off.
     *
     * @return the boolean
     */
    public boolean spawnCar(){return false;}

    /**
     * Increments time. Performs all the logic that is needed per tick for the car spawner.
     * Must be called every tick.
     */
    void incrementTime(){}
}
