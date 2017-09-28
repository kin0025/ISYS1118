package main.entities;

import main.entities.intersection.Intersection;
import main.entities.lane.CarSpawn;
import main.entities.lane.Lane;
import main.exceptions.PathNotFoundException;
import main.utils.BoundingBox;
import main.utils.DimensionManager;
import main.utils.Position;
import main.utils.enums.CardinalDirection;
import main.utils.enums.Orientation;
import main.utils.enums.TurnDirection;

import java.util.ArrayList;

public class MapGrid {
    private final int width;
    private final int height;
    private int numberOfIntersections = 0;
    private Intersection[][] grid;
    private ArrayList<Road> roads = new ArrayList<>();
    private boolean maxIntersectionsDisabled = true;

    public MapGrid(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new Intersection[width][height];
        //A list of all the roads in the grid - used to create roads and display them.
    }

    public void incrementTime() {
        for (Intersection[] row : getGrid()) {
            for (Intersection intersection : row) {
                if (intersection != null) {
                    intersection.incrementTime();
                }
            }

        }
        for (Road roads : getRoads()) {
            roads.incrementTime();
        }
    }

    /**
     * Gets the grid of intersections
     *
     * @return the intersection grid
     **/
    public Intersection[][] getGrid() {
        return grid;
    }

    /**
     * Adds an intersection to the map grid
     *
     * @param x             the column in the grid the intersection will be placed in
     * @param y             the row in the grid the intersection will be placed in
     * @param lightTimeV    the time in ticks the vertical lights will be green for
     * @param lightTimeH    the time in ticks the horizontal lights will be green for
     * @param startingLight the orientation (horizontal or vertical) of the lights.
     * @return whether the intersection was added successfully - if there was already an intersection in the location it will fail to add.
     */
    public boolean addIntersection(int x, int y, int lightTimeV, int lightTimeH, Orientation startingLight) {
        int offset = DimensionManager.lengthOfRoadPixels + DimensionManager.widthOfIntersectionPixels;
        //Check if there is already an intersection there
        if (grid[x][y] == null && (numberOfIntersections < 10 || maxIntersectionsDisabled)) {
            grid[x][y] = new Intersection(new Position((x * offset) + DimensionManager.lengthOfRoadPixels, (y * offset) + DimensionManager
                    .lengthOfRoadPixels), lightTimeV, lightTimeH, startingLight);
            numberOfIntersections++;
            return true;
        }
        return false;
    }

    /**
     * Removes an intersection at the grid co-ordinates x and y
     *
     * @param x the column in the grid the intersection will be placed in
     * @param y the row the intersection will be placed in
     */
    public void removeIntersection(int x, int y) {
        Intersection intersection = grid[x][y];
        ArrayList<Road> removalRoads = new ArrayList<>();
        for (Road road : roads) {
            if (road.hasIntersection(intersection)) {
                removalRoads.add(road);
            }
        }
        roads.removeAll(removalRoads);
        grid[x][y] = null;
        numberOfIntersections--;
    }

    public boolean intersectionsAreAdjacent(Intersection intersection1, Intersection intersection2) {
        return intersectionsAreAdjacent(intersection1, intersection2, null);
    }

    public boolean intersectionsAreAdjacent(Intersection intersection1, Intersection intersection2, Orientation orientation) {
        CardinalDirection direction = getDirectionFrom(intersection1, intersection2);
        return direction != null && (orientation == null || direction.getAxis() == orientation);
    }

    public Orientation getIntersectionAxis(Intersection intersection1, Intersection intersection2) {
        CardinalDirection direction = getDirectionFrom(intersection1, intersection2);
        if (direction != null) {
            return direction.getAxis();
        }
        return null;
    }

    public CardinalDirection getDirectionFrom(Intersection from, Intersection to) {
        int[] coords = getIntersectionCoords(from);
        if (coords == null) {
            return null;
        }
        int x = coords[0];
        int y = coords[1];

        if (y < height - 1 && grid[x][y + 1] == to) {
            return CardinalDirection.NORTH;
        }
        if (y > 0 && grid[x][y - 1] == to) {
            return CardinalDirection.SOUTH;
        }
        if (x < width - 1 && grid[x + 1][y] == to) {
            return CardinalDirection.EAST;
        }
        if (x > 0 && grid[x - 1][y] == to) {
            return CardinalDirection.WEST;
        }
        return null;
    }

    public Intersection getIntersection(int x, int y) {
        if (x < width && y < height) {
            return grid[x][y];
        } else {
            return null;
        }
    }


    /**
     * Returns the co-ordinates of an intersection
     *
     * @param intersection the intersection
     * @return the co-ordinates or null if no intersection exists
     */
    public int[] getIntersectionCoords(Intersection intersection) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (grid[x][y] == intersection) {
                    return new int[]{x, y};
                }
            }
        }
        return null;
    }


    public boolean addLane(Lane lane, Intersection intersection1, Intersection intersection2) {
        Road road = getRoad(intersection1, intersection2);
        if (road.getOrientation() == lane.getDirection().getAxis()) {
            road.addLane(lane);
            return true;
        }
        return false;
    }

    public boolean addLane(Lane lane, Intersection intersection1, CardinalDirection direction) {
        Road road = getRoad(intersection1, direction);
        if (road.getOrientation() == lane.getDirection().getAxis()) {
            road.addLane(lane);
            return true;
        }
        return false;
    }


    public CarSpawn createSpawnPoint(Intersection intersection, CardinalDirection direction, int spawnDelay) {
        int axis;
        if (direction.getAxis() == Orientation.HORIZONTAL) {
            axis = 0;
        } else {
            axis = 1;
        }
        if (getEdgeIntersection(getIntersectionCoords(intersection)[axis], direction) != intersection) {
            return null;
        }

        ArrayList<TurnDirection> turnDirections = new ArrayList<>();
        turnDirections.add(TurnDirection.LEFT);
        turnDirections.add(TurnDirection.STRAIGHT);
        turnDirections.add(TurnDirection.RIGHT);
        Road road = getRoad(intersection, direction);
        if (road == null) {
            road = addRoad(intersection, direction);
        }
        BoundingBox laneBox;
        if (!road.getLanes().isEmpty()) {
            laneBox = road.getLanes().get(0).getBoundingBox();
        } else {
            laneBox = road.getBoundingBox();
        }
        CarSpawn carSpawn = new CarSpawn(direction.reverse(), turnDirections, 0, laneBox, spawnDelay);
        road.addLane(carSpawn);
        return carSpawn;
    }


    public boolean createLinePath(CarSpawn carSpawn, int indexNumber, CardinalDirection goTo) throws PathNotFoundException {
        boolean pathEnded = false;
        boolean pathStarted = false;
        boolean pathWorked = true;
        Intersection lastIntersection;
        carSpawn.initialiseCarPath();
        switch (goTo) {
            case SOUTH:
                for (int i = 0; i < height - 1; i++) {
                    Intersection thisIntersection = grid[indexNumber][i];
                    Intersection nextIntersection = grid[indexNumber][i + 1];

                    if (!pathStarted && thisIntersection != null) {
                        pathStarted = true;
                    }
                    if (thisIntersection != null && nextIntersection != null) {
                        carSpawn.addToPath(thisIntersection);
                        carSpawn.addToPath(getRoad(thisIntersection, nextIntersection).getLane(goTo, TurnDirection.STRAIGHT));
                    } else if (pathEnded && nextIntersection != null) {
                        pathWorked = false;
                    } else {
                        pathEnded = true;
                    }
                    if (!pathWorked) {
                        return false;
                    }
                }
                break;

            case WEST:
                for (int i = width - 1; i > 0; i--) {
                    Intersection thisIntersection = grid[i][indexNumber];
                    Intersection nextIntersection = grid[i - 1][indexNumber];

                    if (!pathStarted && thisIntersection != null) {
                        pathStarted = true;
                    }
                    if (thisIntersection != null && nextIntersection != null) {
                        carSpawn.addToPath(thisIntersection);
                        carSpawn.addToPath(getRoad(thisIntersection, nextIntersection).getLane(goTo, TurnDirection.STRAIGHT));
                    } else if (pathEnded && nextIntersection != null) {
                        pathWorked = false;
                    } else {
                        pathEnded = true;
                    }
                    if (!pathWorked) {
                        return false;
                    }

                }
                break;
            case NORTH:
                for (int i = height - 1; i > 0; i--) {
                    Intersection thisIntersection = grid[indexNumber][i];
                    Intersection nextIntersection = grid[indexNumber][i - 1];

                    if (!pathStarted && thisIntersection != null) {
                        pathStarted = true;
                    }
                    if (thisIntersection != null && nextIntersection != null) {
                        carSpawn.addToPath(thisIntersection);
                        carSpawn.addToPath(getRoad(thisIntersection, nextIntersection).getLane(goTo, TurnDirection.STRAIGHT));
                    } else if (pathEnded && nextIntersection != null) {
                        pathWorked = false;
                    } else {
                        pathEnded = true;
                    }
                    if (!pathWorked) {
                        return false;
                    }

                }
                break;
            case EAST:
                for (int i = 0; i < width - 1; i++) {
                    Intersection thisIntersection = grid[i][indexNumber];
                    Intersection nextIntersection = grid[i + 1][indexNumber];

                    if (!pathStarted && thisIntersection != null) {
                        pathStarted = true;
                    }
                    if (thisIntersection != null && nextIntersection != null) {
                        carSpawn.addToPath(thisIntersection);
                        carSpawn.addToPath(getRoad(thisIntersection, nextIntersection).getLane(goTo, TurnDirection.STRAIGHT));
                    } else if (pathEnded && nextIntersection != null) {
                        pathWorked = false;
                    } else {
                        pathEnded = true;
                    }
                    if (!pathWorked) {
                        return false;
                    }

                }
                break;
            default:
                return false;
        }
        lastIntersection = getEdgeIntersection(indexNumber, goTo);
        carSpawn.addToPath(lastIntersection);
        Road lastRoad = getRoad(lastIntersection, goTo);
        if (lastRoad == null) {
            lastRoad = addRoad(lastIntersection, goTo);
            lastRoad.addDestroyerLane(goTo);
        } else if (lastRoad.getDestructorLane(goTo) == null) {
            lastRoad.addDestroyerLane(goTo);
        }
        return carSpawn.finalisePath(getRoad(lastIntersection, goTo).getDestructorLane
                (goTo));
    }

    private Intersection getEdgeIntersection(int index, CardinalDirection edgeSide) {
        switch (edgeSide) {
            case NORTH:
                for (int i = 0; i < height; i++) {
                    if (getIntersection(index, i) != null) {
                        return getIntersection(index, i);
                    }
                }
                return null;
            case EAST:
                for (int i = width - 1; i >= 0; i--) {
                    if (getIntersection(i, index) != null) {
                        return getIntersection(i, index);
                    }
                }
                return null;
            case SOUTH:
                for (int i = height - 1; i >= 0; i--) {
                    if (getIntersection(index, i) != null) {
                        return getIntersection(index, i);
                    }
                }
                return null;
            case WEST:
                for (int i = 0; i < width; i++) {
                    if (getIntersection(i, index) != null) {
                        return getIntersection(i, index);
                    }
                }
                return null;
            default:
                return null;
        }
    }

    public Road addRoad(Intersection intersection1, Intersection intersection2, Orientation orientation) {
        if (intersection1 == null || intersection2 == null) {
            return null;
        }
        if (intersectionsAreAdjacent(intersection1, intersection2) && getRoad(intersection1, intersection2) == null) {
            double xPos, xWidth;
            double yPos, yWidth;
            //int offset = DimensionManager.lengthOfRoadPixels + DimensionManager.widthOfIntersectionPixels;
            if (orientation == Orientation.VERTICAL) {
                xPos = (intersection1.getCentre().getX());
                yPos = (intersection1.getCentre().getY() + intersection2.getCentre().getY()) / 2;
                xWidth = DimensionManager.widthOfRoadPixels;
                yWidth = DimensionManager.lengthOfRoadPixels;
            } else {
                xPos = (intersection1.getCentre().getX() + intersection2.getCentre().getX()) / 2;
                yPos = (intersection1.getCentre().getY());
                xWidth = DimensionManager.lengthOfRoadPixels;
                yWidth = DimensionManager.widthOfRoadPixels;


            }

            Road newRoad = new Road(orientation, new BoundingBox(new Position(xPos, yPos), xWidth, yWidth));
            roads.add(newRoad);

            CardinalDirection direction = getDirectionFrom(intersection1, intersection2);
            newRoad.addIntersection(intersection1, direction);
            newRoad.addIntersection(intersection2, direction.reverse());
            intersection1.addRoad(newRoad, direction);
            intersection2.addRoad(newRoad, direction.reverse());
            return newRoad;
        }
        return null;
    }

    public Road addRoad(Intersection intersection1, CardinalDirection directionFromIntersection) {
        if (getRoad(intersection1, directionFromIntersection) == null) {
            double xPos = intersection1.getCentre().getX();
            double xWidth;
            double yPos = intersection1.getCentre().getY();
            double yWidth;
            int offset = DimensionManager.lengthOfRoadPixels + DimensionManager.widthOfIntersectionPixels;
            switch (directionFromIntersection) {
                case NORTH:
                    //xPos += 0;
                    yPos -= offset / 2;
                    xWidth = DimensionManager.widthOfRoadPixels;
                    yWidth = DimensionManager.lengthOfRoadPixels;
                    break;
                case SOUTH://xPos += 0;
                    yPos += offset / 2;
                    xWidth = DimensionManager.widthOfRoadPixels;
                    yWidth = DimensionManager.lengthOfRoadPixels;
                    break;
                case EAST:
                    xPos += offset / 2;
                    //yPos += 0;
                    yWidth = DimensionManager.widthOfRoadPixels;
                    xWidth = DimensionManager.lengthOfRoadPixels;
                    break;
                case WEST:
                    xPos -= offset / 2;
                    //yPos += 0;
                    yWidth = DimensionManager.widthOfRoadPixels;
                    xWidth = DimensionManager.lengthOfRoadPixels;
                    break;
                    default:
                        return null;
            }
            Road newRoad = new Road(directionFromIntersection.getAxis(), new BoundingBox(new Position(xPos, yPos), xWidth, yWidth));
            roads.add(newRoad);

            newRoad.addIntersection(intersection1, directionFromIntersection);
            intersection1.addRoad(newRoad, directionFromIntersection);
            return newRoad;
        }

        return null;
    }

    public void setLightTiming(Intersection intersection, double newTimeSeconds, Orientation orientation) {
        intersection.setLightTiming(orientation, DimensionManager.secondsToTicks(newTimeSeconds));
    }

    /**
     * Needs to empty any roads in intersections if there are any - call removeAllRoads on intersections?
     * Generate all connecting roads - lanes are auto created by the roads.
     */
    public void fillRoads() {
        roads = new ArrayList<>();
        //Iterate through all the columns of the grid
        for (int i = 0; i < grid.length; i++) {
            //Iterate through the rows of intersections
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null) {
                    //Clear existing roads.
                    grid[i][j].removeRoads();
                    //Check the horizontal grid
                    if (j + 1 != grid[i].length && grid[i][j] != null && grid[i][j + 1] != null) {
                        addRoad(getIntersection(i, j + 1), getIntersection(i, j), Orientation.VERTICAL);
                    }
                    //Check the vertical grid
                    if (i + 1 != grid.length && grid[i][j] != null && grid[i + 1][j] != null) {
                        addRoad(getIntersection(i + 1, j), getIntersection(i, j), Orientation.HORIZONTAL);
                    }
                }
            }
        }

    }

    public ArrayList<Road> getRoads() {
        return roads;
    }

    /**
     * Gets the road that runs between two intersections
     *
     * @param intersection1 an intersection
     * @param intersection2 another intersection
     * @return null if no road, or the road between two intersections
     */
    public Road getRoad(Intersection intersection1, Intersection intersection2) {
        for (Road road : roads) {
            if (road.hasIntersection(intersection1) && road.hasIntersection(intersection2)) {
                return road;
            }
        }
        return null;
    }

    /**
     * Gets the road that runs between two intersections
     *
     * @param intersection1 an intersection
     * @return null if no road, or the road between two intersections
     */
    public Road getRoad(Intersection intersection1, CardinalDirection directionToGrid) {
        for (Road road : roads) {
            if (road.getIntersectionDirection(intersection1) == directionToGrid.reverse() && road.numberOfIntersections() == 1) {
                return road;
            }
        }
        return null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getSize() {
        return new int[]{width, height};
    }


    public void generateStandardGrid() {
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                addIntersection(i, j, DimensionManager.secondsToTicks(10), DimensionManager.secondsToTicks(10), Orientation.HORIZONTAL);
            }
        }
        fillRoads();

        Intersection intersection = getIntersection(1, 1);
        CardinalDirection direction = CardinalDirection.NORTH;
        CarSpawn spawn = createSpawnPoint(intersection, direction, 10);

        int index;
        if (spawn.getDirection().getAxis() == Orientation.HORIZONTAL) {
            index = getIntersectionCoords(intersection)[0];
        } else {
            index = getIntersectionCoords(intersection)[1];
        }
        try {
            createLinePath(spawn, index, spawn.getDirection());

        } catch (PathNotFoundException e) {
            System.out.println("Creating the spawn path failed.");

        }


        direction = CardinalDirection.WEST;
        spawn = createSpawnPoint(intersection, direction, 10);

        if (spawn.getDirection().getAxis() == Orientation.HORIZONTAL) {
            index = getIntersectionCoords(intersection)[0];
        } else {
            index = getIntersectionCoords(intersection)[1];
        }
        try {
            createLinePath(spawn, index, spawn.getDirection());

        } catch (PathNotFoundException e) {
            System.out.println("Creating the spawn path failed.");

        }
    }
}
