/*
 * ISYS1118: Assignment.
 * Group: Null
 * File created by: Alexander Kinross-Smith, s3603437
 */

package main.entities.lane;

import main.entities.car.Car;
import main.entities.interfaces.CarMovable;
import main.utils.BoundingBox;
import main.utils.Direction;
import main.utils.Position;
import main.utils.enums.TurnDirection;

import java.util.ArrayList;

/**
 * Spawns cars.
 */
public class CarSpawn extends Lane {
    //Generate this in the constructor
    private final ArrayList<CarMovable> carPath;

    private final Position spawnPosition;
    //Ticks between each car spawn
    private final int spawnDelay;

    //The current time for the spawner - needed for delay logic.
    private long tick = 0;

    /**
     * Instantiates a new Car spawn. Creates a lanes list for cars to follow and figures out the...
     *
     * @param spawnPosition     the spawn position
     * @param spawnDelay        the spawn delay
     */
    public CarSpawn(Direction direction, ArrayList<TurnDirection> turnDirections, int lanesFromEdge, BoundingBox laneBox, ArrayList<CarMovable>
            carPath, Position spawnPosition, int spawnDelay) {
        super(direction, turnDirections, lanesFromEdge, laneBox);
        this.carPath = carPath;
        this.spawnDelay = spawnDelay;
        this.spawnPosition = spawnPosition;
    }


    /**
     * Spawns a car. Creates a new car, puts it in the correct lane and sends it off.
     *
     * @return the boolean
     */
    public boolean spawnCar() {
        addCar(new Car(new Position(spawnPosition.getX(), spawnPosition.getY()), carPath));
        return false;
    }

    /**
     * Increments time. Performs all the logic that is needed per tick for the car spawner.
     * Must be called every tick.
     */
    public void incrementTime() {
        //Checks that the last car added has moved enough. Might throw a null.
        if (getCars().getLast() != null) {
            if (getCars().getLast().getPosition().getDifference(spawnPosition) <= 20) {
                //Too close, don't spawn a car.
                //TODO: What sort of logic do we expect here for spawning cars if they are already full?
                return;
            }
        }
        if (tick % spawnDelay == 0) {
            spawnCar();
        }
        tick++;
    }

    public ArrayList<CarMovable> getCarPath() {
        return carPath;
    }
}
