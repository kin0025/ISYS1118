/*
 * ISYS1118: Assignment.
 * Group: Null
 * File created by: Alexander Kinross-Smith, s3603437
 */

package main.entities.lane;

import main.entities.car.Car;
import main.entities.car.CarPath;
import main.entities.interfaces.CarMovable;
import main.utils.BoundingBox;
import main.utils.DimensionManager;
import main.utils.Position;
import main.utils.enums.CardinalDirection;
import main.utils.enums.TurnDirection;

import java.util.ArrayList;

/**
 * Spawns cars.
 */
public class CarSpawn extends Lane {
    private final Position spawnPosition;
    //Ticks between each car spawn
    private final int spawnDelay;
    //Generate this in the constructor
    private CarPath carPath;
    private boolean active;
    //The current time for the spawner - needed for delay logic.
    private long tick = 0;

    /**
     * Instantiates a new Car spawn. Creates a lanes list for cars to follow and figures out the...
     *
     * @param spawnDelay the spawn delay
     */
    public CarSpawn(CardinalDirection directionOfLane, ArrayList<TurnDirection> turnDirections, int lanesFromEdge, BoundingBox laneBox, int
            spawnDelay) {
        super(directionOfLane, turnDirections, lanesFromEdge, laneBox);
        this.spawnDelay = spawnDelay;
        this.active = false;

        double xPos = 0;
        double yPos = 0;
        if (directionOfLane == CardinalDirection.SOUTH) {
            xPos = (laneBox.getxMin() + laneBox.getxMax()) / 2;
            yPos = laneBox.getyMin() + DimensionManager.lengthOfCarPixels;
        }
        if (directionOfLane == CardinalDirection.NORTH) {
            xPos = (laneBox.getxMin() + laneBox.getxMax()) / 2;
            yPos = laneBox.getyMax() -  DimensionManager.lengthOfCarPixels;
        }
        if (directionOfLane == CardinalDirection.WEST) {
            xPos = laneBox.getxMin() +  DimensionManager.lengthOfCarPixels;
            yPos = (laneBox.getyMin() + laneBox.getyMax()) / 2;
        }
        if (directionOfLane == CardinalDirection.EAST) {
            xPos = laneBox.getxMax() -  DimensionManager.lengthOfCarPixels;
            yPos = (laneBox.getyMin() + laneBox.getyMax()) / 2;
        }
        spawnPosition = new Position(xPos, yPos);
    }


    /**
     * Spawns a car. Creates a new car, puts it in the correct lane and sends it off.
     *
     * @return if the car couldn't spawn due to been blocked returns false.
     */
    boolean spawnCar() {
        //Check cars aren't going to be too close together.
        if (!getCars().isEmpty() && getCars().getLast().getPosition().getDifference(spawnPosition) <= 20) {
            return false;
        }
        return active && addCar(new Car(new Position(spawnPosition.getX(), spawnPosition.getY()), carPath));
    }

    /**
     * Increments time. Performs all the logic that is needed per tick for the car spawner.
     * Must be called every tick.
     */
    public void incrementTime() {
        tick++;
        super.incrementTime();
        if (active) {
            if (tick % spawnDelay == 0) {
                spawnCar();
            }
        }

    }

    public boolean initialiseCarPath() {
        carPath = new CarPath();
        return carPath.initialisePath(this);
    }

    public boolean addToPath(CarMovable toAdd) {
        return carPath.addPartToPath(toAdd);
    }

    public boolean finalisePath(CarDestroy destructor) {
        active = true;
        return carPath.finalisePath(destructor);
    }
}
