package main.gui;

import main.Simulator;

public class GraphicsController implements InputController {
    private Simulator simulator;

    private SimulationOutput output;

    public GraphicsController(Simulator simulator) {
        this.simulator = simulator;
    }

    @Override
    public boolean mainMenu() {
        return false;

    }

    @Override
    public void addSpawnPoint() {

    }

    @Override
    public void addIntersection() {

    }

    @Override
    public void createSpawnPath() {

    }

    @Override
    public void addRoad() {

    }

    @Override
    public void changeTrafficLights() {

    }

    @Override
    public void removeSpawnPoint() {

    }

    @Override
    public void removeIntersection() {

    }

    @Override
    public void removeRoad() {

    }
}
