package main;

import main.entities.MapGrid;
import main.entities.Road;
import main.entities.intersection.Intersection;
import main.entities.lane.CarSpawn;

import java.util.ArrayList;


public class Simulator {
    private MapGrid mapGrid;
    boolean pause = true;

    /**
     * Runs the simulation through one step
     */
    public void runSimulation() {
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

    public boolean isPaused() {
        return pause;
    }

    public void pause() {
        pause = true;
    }

    public void start() {
        pause = false;
    }

    public MapGrid getMapGrid() {
        return mapGrid;
    }

    public void createNewMap(int width, int height) {
        mapGrid = new MapGrid(width, height);
    }


    public boolean createSpawnPoint(ArrayList<Intersection> intersections, int roadColumn, int roadRow) {
        //addDestroyPoint();
        //CarSpawn spawner = new CarSpawn(mapGrid.findPathFromIntersections(intersections, this, this));
        //return mapGrid.addLane(spawner, roadColumn, roadRow);
        return false;
    }

    public void addDestroyPoint(int roadColumn, int roadRow) {
//TODO
    }
}
