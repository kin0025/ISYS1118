/*
 * ISYS1118: Assignment.
 * Group: Null
 * File created by: Alexander Kinross-Smith, s3603437
 */

package main.entities.lane;

import main.entities.Car;
import main.entities.Road;
import main.entities.interfaces.CarMoveable;
import main.entities.intersection.Intersection;
import main.exceptions.PathNotFoundException;
import main.utils.Direction;
import main.utils.Orientation;
import main.utils.Position;
import main.utils.TurnDirection;

import java.util.ArrayList;

/**
 * Spawns cars.
 */
public class CarSpawn extends Lane {
    //Generate this in the constructor
    private ArrayList<CarMoveable> carPath = new ArrayList<>();

    private Position spawnPosition;
    //Ticks between each car spawn
    private int spawnDelay;

    //The current time for the spawner - needed for delay logic.
    private long tick = 0;

    /**
     * Instantiates a new Car spawn. Creates a lanes list for cars to follow and figures out the...
     *
     * @param pathIntersections the path intersections
     * @param spawnPosition     the spawn position
     * @param spawnDelay        the spawn delay
     */
    public CarSpawn(Direction direction, ArrayList<TurnDirection> turnDirections , int lanesFromEdge, Position position, ArrayList<Intersection> pathIntersections, CarDestroy
            endLane, Position spawnPosition, int spawnDelay) throws PathNotFoundException {
        super(direction, turnDirections,lanesFromEdge, position);

        this.spawnDelay = spawnDelay;
        this.spawnPosition = spawnPosition;

        //Add this lane as the first one in the path
        carPath.add(this);

        Lane currentLane = this;

        //Then iterate through all the intersections this will pass through.
        for (int i = 0; i < pathIntersections.size() - 1; i++) {
            //Add the intersection the car will go to first
            carPath.add(pathIntersections.get(i));

            //Now we figure out what lane we want to go to next
            Intersection currentIntersection = pathIntersections.get(i);
            Intersection nextIntersection = pathIntersections.get(i + 1);
            boolean laneFound = false;
            //Find the next road.
            for (Road intersectionRoad : nextIntersection.getRoads()) {
                if (intersectionRoad.getIntersectionDirection(nextIntersection) != null) {
                    //We've found the road! Now find the turn direction.
                    TurnDirection turnDirection = currentLane.getDirection().getTurnDirection
                            (currentIntersection
                                    .getRoadDirection(intersectionRoad));

                    //Save the compass direction of the road as well.
                    Direction laneDirection = nextIntersection.getRoadDirection(intersectionRoad);

                    //Now we pick a lane to go to - find the correct lane in the road to move the car to.
                    for (Lane lane : intersectionRoad.getLanes()) {
                        //We've iterated through all lanes, now we find a lane that is going in the right direction
                        // and has the correct turn direction.
                        if (lane.getDirection().getDirection().equals(laneDirection.getDirection()) &&
                                lane.hasTurnDirection(turnDirection)) {
                            laneFound = true;
                            //If we find the right lane, add and move the lane.
                            carPath.add(lane);
                            currentLane = lane;
                            break;
                        }

                    }
                }
            }
            if (!laneFound) {
                throw new PathNotFoundException("Couldn't find a connecting lane when generating a path.");
            }

        }
        boolean found = false;
        for (Road road : pathIntersections.get(pathIntersections.size() - 1).getRoads()
                ) {
            for (Lane lane : road.getLanes()
                    ) {
                if (lane.equals(endLane)) {
                    found = true;
                }
            }
        }
        if (found) {
            carPath.add(endLane);
        } else {
            throw new PathNotFoundException("End Lane dissconnected");
        }
    }


    /**
     * Spawns a car. Creates a new car, puts it in the correct lane and sends it off.
     *
     * @return the boolean
     */
    public boolean spawnCar() {
        addCar(new Car(new Position(spawnPosition.getX(),spawnPosition.getY()),carPath));
        return false;
    }

    /**
     * Increments time. Performs all the logic that is needed per tick for the car spawner.
     * Must be called every tick.
     */
    public void incrementTime() {
        //Checks that the last car added has moved enough. Might throw a null.
        if(getCars().getLast() != null) {
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

    public ArrayList<CarMoveable> getCarPath() {
        return carPath;
    }
}
