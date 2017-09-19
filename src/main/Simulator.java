package main;

import main.entities.MapGrid;
import main.entities.Road;
import main.entities.car.CarPath;
import main.entities.intersection.Intersection;
import main.utils.Direction;
import main.utils.enums.Orientation;

import java.util.ArrayList;


public class Simulator {
    boolean pause = true;
    private MapGrid mapGrid;

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

    public int[] getGridSize() {
        return mapGrid.getSize();
    }

    public Intersection getIntersection(int[] coords){
        if(coords.length == 2) {
            return mapGrid.getIntersection(coords[0], coords[1]);
        }
        return null;
    }

    public Intersection getIntersection(int x, int y){
        return mapGrid.getIntersection(x,y);
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


    /**
     * Adds a road between two intersections
     *
     * @param intersection1 a intersection that the road must be created between
     * @param intersection2 a intersection that the road is created between
     * @return Whether the road can be added to the map - if a road already exists, or the intersections are not adjacent it will return false.
     */
    public boolean addRoad(Intersection intersection1, Intersection intersection2) {
        //TODO Add road between these two intersections if possible.
        return false;
    }

    /**
     * Gets the road between two intersections
     *
     * @param intersectionFrom the first intersection, order doesn't matter
     * @param intersectionTo   the second intersection, order doesn't matter
     * @return the Road between the two, or Null if there is no road/intersections aren't adjacent.
     */
    public Road getRoad(Intersection intersectionFrom, Intersection intersectionTo) {
        //TODO Test this
        return null;
    }

    /**
     * Gets a road on the edge of the map grid
     *
     * @param intersectionFrom The intersection on the edge of the map grid
     * @param sideFrom         the direction the road is coming from on the map grid
     * @return the road on the edge of the grid, or Null if there is not road attached to the intersection from
     * that direction/ the intersection is on the end of the grid
     */
    public Road getRoad(Intersection intersectionFrom, Direction sideFrom) {
        //TODO Test this
        return null;
    }

    public void addSpawnPoint() {
//TODO Add code here - select the road the intersection should be placed on
    }


    /**
     * Adds an intersection to the map grid
     *
     * @param x                   the column in the grid the intersection will be placed in
     * @param y                   the row in the grid the intersection will be placed in
     * @param lightTimeVertical   the time in ticks the vertical lights will be green for
     * @param lightTimeHorizontal the time in ticks the horizontal lights will be green for
     * @param startingLight       the orientation (horizontal or vertical) of the lights.
     * @return whether the intersection was added successfully - if there was already an intersection in the location it will fail to add.
     */
    public boolean addIntersection(int x, int y, int lightTimeVertical, int lightTimeHorizontal, Orientation startingLight) {
        return mapGrid.addIntersection(x, y, lightTimeVertical, lightTimeHorizontal, startingLight);
    }


    /**
     * Creates a spawn path after a spawn point has already been created.
     *
     * @param intersection1
     * @param intersection2
     * @return
     */
    public CarPath createSpawnPath() {
        //Create the spawn pathh
        return new CarPath();
    }


    public void removeSpawnPoint() {

    }


    public void removeIntersection() {

    }


    public void removeRoad() {

    }


    public void changeTrafficLights() {

    }
}
