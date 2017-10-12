/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.entities.lane;

import main.entities.MapGrid;
import main.utils.DimensionManager;
import main.utils.enums.CardinalDirection;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarSpawnTest {
    private CarSpawn carSpawn;
    private MapGrid mapGrid;

    @Before
    public void setUp() throws Exception {
        //TODO Implement once other stuff is in:
        mapGrid = new MapGrid(5, 5);

        mapGrid.generateStandardGrid();
        carSpawn = mapGrid.getRoad(mapGrid.getIntersection(1,1),CardinalDirection.NORTH).getSpawnLane(CardinalDirection.SOUTH);

    }


    @Test
    public void checkPathing() throws Exception {
        for (int i = 1; i < 11; i += 2) {
            // assertEquals("Incorrect Spawn path generated", mapGrid.getIntersection(0, i / 2), carSpawn.getCarPath().get(i));
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
        carSpawn.spawnCar();
        carSpawn.spawnCar();
        assertEquals("Car spawned on top of other car", 1, carSpawn.getCars().size());
    }

    @Test
    public void checkInitialNoSpawn(){
        carSpawn.incrementTime();
        assertEquals("Initial Spawn incorrect", 0, carSpawn.getCars().size());

    }

    @Test
    public void checkSpawnTimingCorrect() {
        for (int i = 0; i < DimensionManager.secondsToTicks(5); i++) {
            carSpawn.incrementTime();
        }

        assertEquals("Car not spawned at correct timing", 1, carSpawn.getCars().size());
    }

    @Test
    public void checkSpawnTimingJustBefore() {
        for (int i = 0; i < 9; i++) {
            carSpawn.incrementTime();
        }
        assertEquals("Car spawned at incorrect timing", 0, carSpawn.getCars().size());
    }


}