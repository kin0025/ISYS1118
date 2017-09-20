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
     * @param spawnDelay    the spawn delay
     */
    public CarSpawn(CardinalDirection directionOfLane, ArrayList<TurnDirection> turnDirections, int lanesFromEdge, BoundingBox laneBox, int spawnDelay) {
        super(directionOfLane, turnDirections, lanesFromEdge, laneBox);
        this.spawnDelay = spawnDelay;
        this.active = false;

        double xpos = 0;
        double ypos = 0;
        if(directionOfLane == CardinalDirection.NORTH){
            xpos = (laneBox.getxMin() + laneBox.getxMax() )/2;
            ypos = laneBox.getyMin();
        }
        if(directionOfLane == CardinalDirection.SOUTH){
            xpos = (laneBox.getxMin() + laneBox.getxMax() )/2;
            ypos = laneBox.getyMax();
        }if(directionOfLane == CardinalDirection.EAST){
            xpos = laneBox.getxMin();
            ypos = (laneBox.getyMin() + laneBox.getyMax())/2;
        }
        if(directionOfLane == CardinalDirection.WEST){
            xpos = laneBox.getxMax();
            ypos = (laneBox.getyMin() + laneBox.getyMax())/2;
        }
        spawnPosition = new Position(xpos,ypos);
    }


    /**
     * Spawns a car. Creates a new car, puts it in the correct lane and sends it off.
     *
     * @return if the car couldn't spawn due to been blocked returns false.
     */
    boolean spawnCar() {
        return active && addCar(new Car(new Position(spawnPosition.getX(), spawnPosition.getY()), carPath));
    }

    /**
     * Increments time. Performs all the logic that is needed per tick for the car spawner.
     * Must be called every tick.
     */
    public void incrementTime() {
        if (!active) {
            active = carPath.isPathComplete();
        }
        //Checks that the last car added has moved enough.
        if (getCars().getLast() != null) {
            if (getCars().getLast().getPosition().getDifference(spawnPosition) <= 20) {
                //Too close, don't spawn a car.
                return;
            }
        }
        if (tick % spawnDelay == 0) {
            spawnCar();
        }
        tick++;
    }

    public boolean initialiseCarPath() {
        carPath = new CarPath();
        return carPath.initialisePath(this);
    }

    public boolean addToPath(CarMovable toAdd){
        return carPath.addPartToPath(toAdd);
    }

    public boolean finalisePath(CarDestroy destructor){
        return carPath.finalisePath(destructor);
    }
}
