package main.entities;

import main.utils.Position;

import java.util.LinkedList;

/**
 * Spawns cars.
 */
public class CarSpawn extends Intersection {
    //Get this from the constructor
    private LinkedList<Intersection> pathIntersections;
    //Generate this in the constructor
    private LinkedList<Lane> pathLanes = new LinkedList<>();

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
    public CarSpawn(LinkedList<Intersection> pathIntersections, int spawnSpeed, Position spawnPosition, int spawnDelay) {
        this.pathIntersections = pathIntersections;
        this.spawnSpeed = spawnSpeed;
        this.spawnPosition = spawnPosition;
        this.spawnDelay = spawnDelay;

        for (int i = 0; i < pathIntersections.size(); i++) {
            Intersection nextIntersection = pathIntersections.remove();
            Road nextRoad;
            for (Road roads : nextIntersection.getRoads()) {
                for (Intersection iteratedIntersection : roads.getIntersections()) {
                    if (iteratedIntersection.equals(nextIntersection)) {
                        //We've found the road!. Now we need to figure out what lane to go to.
                        nextRoad = roads;
                    }
                }
                nextRoad = null;
            }
            //pathLanes.add()
        }
    }

    /**
     * Spawns a car. Creates a new car, puts it in the correct lane and sends it off.
     *
     * @return the boolean
     */
    public boolean spawnCar() {
        return false;
    }

    /**
     * Increments time. Performs all the logic that is needed per tick for the car spawner.
     * Must be called every tick.
     */
    void incrementTime() {
    }
}
