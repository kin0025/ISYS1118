package main.gui;


public interface InputController {

    boolean mainMenu();

    void addRoad();

    void addSpawnPoint();

    void addIntersection();

    void removeSpawnPoint();

    void removeIntersection();

    void removeRoad();

    void changeTrafficLights();
}
