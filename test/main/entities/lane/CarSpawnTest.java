/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.entities.lane;

import main.entities.MapGrid;
import main.entities.intersection.Intersection;
import main.utils.*;
import main.utils.enums.CardinalDirection;
import main.utils.enums.Orientation;
import main.utils.enums.TurnDirection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CarSpawnTest {
    private CarSpawn carSpawn;
    private MapGrid mapGrid;
    private CarDestroy endLane;

    @Before
    public void setUp() throws Exception {
        //TODO Implement once other stuff is in:
        mapGrid = new MapGrid(1, 5);
        ArrayList<Intersection> intersections = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            mapGrid.addIntersection(0, i, 10, 10, Orientation.HORIZONTAL);
            intersections.add(mapGrid.getIntersection(0, i));
        }
        mapGrid.fillRoads();

        ArrayList<TurnDirection> turnDir = new ArrayList<>();
        turnDir.add(TurnDirection.STRAIGHT);
        turnDir.add(TurnDirection.LEFT);
        //FIXME
/*
        endLane = new CarDestroy(new Direction(CardinalDirection.NORTH), turnDir, 0, new BoundingBox(new Position(0, 40), 5, 100));
        carSpawn = new CarSpawn(new Direction(CardinalDirection.NORTH), turnDir, 0, new BoundingBox(new Position(0, 40), 5, 100), intersections,
                endLane, new Position(0, 0), 10);*/
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void checkPathing() throws Exception {
        for (int i = 1; i < 11; i += 2) {
            assertEquals("Incorrect Spawn path generated", mapGrid.getIntersection(0, i / 2), carSpawn.getCarPath().get(i));
        }
    }

    @Test
    public void spawnCar() throws Exception {
        assertEquals("Initial conditions incorrect", 0, carSpawn.getCars().size());
        carSpawn.spawnCar();
        assertEquals("Car failed to spawn correctly", 1, carSpawn.getCars().size());
    }


    @Test
    public void spawnCarTooQuickly() throws Exception {
        assertEquals("Initial conditions incorrect", 0, carSpawn.getCars().size());
        carSpawn.spawnCar();
        carSpawn.spawnCar();
        assertEquals("Car spawned on top of other car", 1, carSpawn.getCars().size());
    }

    @Test
    public void checkSpawnTiming() {
        carSpawn.incrementTime();
        assertEquals("Initial Spawn incorrect", 1, carSpawn.getCars().size());
        for (int i = 0; i < 9; i++) {
            carSpawn.incrementTime();
        }
        assertEquals("Car spawned at incorrect timing", 1, carSpawn.getCars().size());
        carSpawn.incrementTime();
        assertEquals("Car not spawned at correct timing", 2, carSpawn.getCars().size());
    }


}