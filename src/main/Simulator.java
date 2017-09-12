package main;

import main.entities.MapGrid;
import main.entities.Road;
import main.entities.intersection.Intersection;
import main.entities.lane.CarSpawn;


public class Simulator {
    private MapGrid mapGrid;

    /**
     * Runs the simulation through one step
     */
    public void runSimulation() {
        boolean stop = false;
        boolean pause = false;
        //This entire thing is temporary.

        for (Intersection[] row : mapGrid.getGrid()) {
            for (Intersection intersection : row) {
                if (intersection != null) {
                    intersection.incrementTime();
                }
            }

        }
        for (Road roads : mapGrid.getRoads()) {
            roads.incrementTime();

        }


    }

    public MapGrid getMapGrid() {
        return mapGrid;
    }

    public void createNewMap(int width, int height) {
        mapGrid = new MapGrid(width, height);
    }


    public boolean addSpawnPoint(CarSpawn spawner, int roadColumn, int roadRow) {
        return mapGrid.addLane(spawner, roadColumn, roadRow);
    }

    public void addDestroyPoint() {

    }
}
