package main;

import main.entities.*;
import main.entities.intersection.Intersection;
import main.entities.lane.Lane;


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
                intersection.incrementTime();
            }

        }
        for (Road roads : mapGrid.getRoads()) {
            for (Lane lane : roads.getLanes()) {
                for (Car car : lane.getCars()) {
                    car.incrementTime();
                }
                lane.incrementTime();

            }

        }


    }

    public MapGrid getMapGrid() {
        return mapGrid;
    }

    public void createNewMap(int width, int height) {
        mapGrid = new MapGrid(width, height);
    }


    public void addSpawnPoint() {

    }

    public void addDestroyPoint() {

    }
}
