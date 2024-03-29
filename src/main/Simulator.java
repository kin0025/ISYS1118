package main;

import main.entities.MapGrid;
import main.entities.RoadSegment;
import main.entities.intersection.Intersection;
import main.entities.lane.CarSpawn;
import main.exceptions.PathNotFoundException;
import main.utils.enums.CardinalDirection;
import main.utils.enums.Orientation;


public class Simulator {
    private boolean makingChanges = false;
    private boolean pause = true;
    private MapGrid mapGrid;

    /**
     * Runs the simulation through one step
     */
    public void incrementTime() {
        //This entire thing is temporary.
        if (!pause) {
            mapGrid.incrementTime();
        }

    }

    public boolean isUnlocked() {
        return !makingChanges;
    }

    private void lock() {
        makingChanges = true;
    }

    private void unlock() {
        makingChanges = false;
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
        lock();
        mapGrid = new MapGrid(width, height);
        unlock();
    }

    public int[] getGridSize() {
        return mapGrid.getSize();
    }

    public Intersection getIntersection(int[] coords) {
        if (coords.length == 2) {
            return mapGrid.getIntersection(coords[0], coords[1]);
        }
        return null;
    }

    public Intersection getIntersection(int x, int y) {
        return mapGrid.getIntersection(x, y);
    }

    public int[] getIntersectionCoords(Intersection intersection) {
        return mapGrid.getIntersectionCoords(intersection);
    }


    public CarSpawn createSpawnPoint(Intersection intersection, CardinalDirection directionSpawnFrom, int spawnDelay) {
        lock();
        if (intersection == null || directionSpawnFrom == null) {
            return null;
        }
        CarSpawn carSpawn = mapGrid.createSpawnPoint(intersection, directionSpawnFrom, spawnDelay);
        unlock();
        return carSpawn;
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
    public boolean addRoad(Intersection intersection1, Intersection intersection2, Orientation orientation) {
        lock();
        RoadSegment segment = mapGrid.addRoad(intersection1, intersection2, orientation);
        unlock();
        return segment != null;
    }

    /**
     * Gets the road between two intersections
     *
     * @param intersectionFrom the first intersection, order doesn't matter
     * @param intersectionTo   the second intersection, order doesn't matter
     * @return the RoadSegment between the two, or Null if there is no road/intersections aren't adjacent.
     */
    public RoadSegment getRoad(Intersection intersectionFrom, Intersection intersectionTo) {
        return mapGrid.getRoad(intersectionFrom, intersectionTo);
    }

    /**
     * Gets a road on the edge of the map grid
     *
     * @param intersectionFrom The intersection on the edge of the map grid
     * @param sideFrom         the direction the road is coming from on the map grid
     * @return the road on the edge of the grid, or Null if there is not road attached to the intersection from
     * that direction/ the intersection is on the end of the grid
     */
    public RoadSegment getRoad(Intersection intersectionFrom, CardinalDirection sideFrom) {
        return mapGrid.getRoad(intersectionFrom, sideFrom);
    }

    public void addSpawnPoint() {
//TODO Add code here - select the road the intersection should be placed on
    }

    public boolean createLinePath(CarSpawn carSpawn, int indexNumber, CardinalDirection startFrom) {
        lock();
        boolean result = mapGrid.createLinePath(carSpawn, indexNumber, startFrom);
        unlock();
        return result;

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
    public void addIntersection(int x, int y, int lightTimeVertical, int lightTimeHorizontal, Orientation startingLight) {
        mapGrid.addIntersection(x, y, lightTimeVertical, lightTimeHorizontal, startingLight);
    }

    public void removeSpawnPoint() {

    }


    public void removeIntersection(int x, int y) {
        lock();
        mapGrid.removeIntersection(x, y);
        unlock();
    }


    public void removeRoad() {

    }


    public void changeTrafficLights(int[] intersectionCoords, int newTimeSecondsVer, int newTimeSecondsHor, Orientation startOrientation) {
        lock();
        if (getIntersection(intersectionCoords) == null) {
            return;
        }
        mapGrid.setLightTiming(getIntersection(intersectionCoords), newTimeSecondsHor, Orientation.HORIZONTAL);
        mapGrid.setLightTiming(getIntersection(intersectionCoords), newTimeSecondsHor, Orientation.VERTICAL);
        unlock();
    }

    public void generateStandardGrid() {
        lock();
        mapGrid.generateStandardGrid();
        unlock();
    }
}
