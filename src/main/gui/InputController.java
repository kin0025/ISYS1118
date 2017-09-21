package main.gui;

import main.entities.lane.CarSpawn;

public interface InputController {

    boolean mainMenu();

    void addRoad();

    void addSpawnPoint();

    void addIntersection();

    void createSpawnPath(CarSpawn spawn);

    void removeSpawnPoint();

    void removeIntersection();

    void removeRoad();

    void changeTrafficLights();
}
