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

    //FIXME Not final at all.
    public MapGrid(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new Intersection[width][height];
        //A list of all the roads in the grid - used to create roads and display them.
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
        if (grid[x][y] == null && numberOfIntersections < 10) {
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


    public CarSpawn createSpawnPoint(Intersection intersection, CardinalDirection direction, int spawnDelay) {
        ArrayList<TurnDirection> turnDirections = new ArrayList<>();
        turnDirections.add(TurnDirection.LEFT);
        turnDirections.add(TurnDirection.STRAIGHT);
        turnDirections.add(TurnDirection.RIGHT);

        if (getRoad(intersection, direction) == null) {
            addRoad(intersection, direction);
        }

        Road road = getRoad(intersection, direction);

        BoundingBox laneBox = road.getLanes().get(0).getBoundingBox();

        CarSpawn carSpawn = new CarSpawn(direction.reverse(), turnDirections, 0, laneBox, spawnDelay);
        road.addLane(carSpawn);
        return carSpawn;
    }


    public boolean createLinePath(CarSpawn carSpawn, int indexNumber, CardinalDirection startFrom) throws PathNotFoundException {
        carSpawn.initialiseCarPath();
        switch (startFrom) {
            case NORTH:
                for (int i = 0; i < height - 1; i++) {
                    Intersection thisIntersection = grid[indexNumber][i];
                    Intersection nextIntersection = grid[indexNumber][i + 1];
                    carSpawn.addToPath(thisIntersection);
                    carSpawn.addToPath(getRoad(thisIntersection, nextIntersection).getLane(startFrom, TurnDirection.STRAIGHT));
                }
                return carSpawn.finalisePath(getRoad(grid[indexNumber][height - 1], startFrom.reverse()).getDestroyerLane(startFrom));

            case EAST:
                for (int i = width - 1; i > 0; i--) {
                    Intersection thisIntersection = grid[i][indexNumber];
                    Intersection nextIntersection = grid[i - 1][indexNumber];
                    carSpawn.addToPath(thisIntersection);
                    carSpawn.addToPath(getRoad(thisIntersection, nextIntersection).getLane(startFrom, TurnDirection.STRAIGHT));
                }
                return carSpawn.finalisePath(getRoad(grid[0][indexNumber], startFrom.reverse()).getDestroyerLane(startFrom));

            case SOUTH:
                for (int i = height - 1; i > 0; i--) {
                    Intersection thisIntersection = grid[indexNumber][i];
                    Intersection nextIntersection = grid[indexNumber][i - 1];
                    carSpawn.addToPath(thisIntersection);
                    carSpawn.addToPath(getRoad(thisIntersection, nextIntersection).getLane(startFrom, TurnDirection.STRAIGHT));
                }
                return carSpawn.finalisePath(getRoad(grid[indexNumber][0], startFrom.reverse()).getDestroyerLane(startFrom));
            case WEST:
                for (int i = 0; i < width - 1; i++) {
                    Intersection thisIntersection = grid[i][indexNumber];
                    Intersection nextIntersection = grid[i + 1][indexNumber];
                    carSpawn.addToPath(thisIntersection);
                    carSpawn.addToPath(getRoad(thisIntersection, nextIntersection).getLane(startFrom, TurnDirection.STRAIGHT));
                }
                return carSpawn.finalisePath(getRoad(grid[height - 1][indexNumber], startFrom.reverse()).getDestroyerLane(startFrom));
            default:
                return false;
        }
    }

    public boolean addRoad(Intersection intersection1, Intersection intersection2, Orientation orientation) {
        if (intersection1 == null || intersection2 == null) {
            return false;
        }
        if (intersectionsAreAdjacent(intersection1, intersection2) && getRoad(intersection1, intersection2) == null) {

            double xPos, xWidth;
            double yPos, yWidth;
            int offset = DimensionManager.lengthOfRoadPixels + DimensionManager.widthOfIntersectionPixels;
            if (orientation == Orientation.VERTICAL) {
                xPos = (getIntersectionCoords(intersection1)[0]) * offset + offset;
                yPos = (getIntersectionCoords(intersection1)[1]) * offset + (offset / 2);
                xWidth = DimensionManager.widthOfRoadPixels;
                yWidth = DimensionManager.lengthOfRoadPixels;
            } else {
                xPos = (getIntersectionCoords(intersection1)[0] * offset) + (offset / 2);
                yPos = (getIntersectionCoords(intersection1)[1] * offset) + offset;
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
            return true;
        }
        return false;
    }

    public boolean addRoad(Intersection intersection1, CardinalDirection direction) {
        if (getRoad(intersection1, direction) == null) {
            double xPos, xWidth;
            double yPos, yWidth;
            int offset = DimensionManager.lengthOfRoadPixels + DimensionManager.widthOfIntersectionPixels;
            if (direction == CardinalDirection.NORTH) {
                xPos = (getIntersectionCoords(intersection1)[0]) * offset;
                yPos = (getIntersectionCoords(intersection1)[1]) * offset + offset;
                xWidth = DimensionManager.widthOfRoadPixels;
                yWidth = DimensionManager.lengthOfRoadPixels;
            } else if (direction == CardinalDirection.SOUTH) {
                xPos = (getIntersectionCoords(intersection1)[0]) * offset;
                yPos = (getIntersectionCoords(intersection1)[1]) * offset - offset;
                xWidth = DimensionManager.widthOfRoadPixels;
                yWidth = DimensionManager.lengthOfRoadPixels;
            } else if (direction == CardinalDirection.EAST) {
                xPos = (getIntersectionCoords(intersection1)[0]) * offset + offset;
                yPos = (getIntersectionCoords(intersection1)[1]) * offset;
                xWidth = DimensionManager.widthOfRoadPixels;
                yWidth = DimensionManager.lengthOfRoadPixels;
            } else {
                xPos = (getIntersectionCoords(intersection1)[0]) * offset;
                yPos = (getIntersectionCoords(intersection1)[1]) * offset - offset;
                xWidth = DimensionManager.widthOfRoadPixels;
                yWidth = DimensionManager.lengthOfRoadPixels;
            }
            Road newRoad = new Road(direction.getAxis(), new BoundingBox(new Position(xPos, yPos), xWidth, yWidth));
            roads.add(newRoad);

            newRoad.addIntersection(intersection1, direction);
            intersection1.addRoad(newRoad, direction);
            return true;
        }
        return false;
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
                        addRoad(getIntersection(i, j), getIntersection(i, j + 1), Orientation.VERTICAL);
                        /*double posX = grid[i][j].getCentre().getX() + (grid[i][j].getCentre().getDifference(grid[i][j + 1].getCentre(), Position
                                .DIMENSION.X)) / 2;
                        double posY = grid[i][j].getCentre().getY() + (grid[i][j].getCentre().getDifference(grid[i][j + 1].getCentre(), Position
                                .DIMENSION.Y)) / 2;
                        Road newRoad = new Road(Orientation.VERTICAL, new BoundingBox(new Position(posX, posY), DimensionManager
                                .widthOfRoadPixels, DimensionManager.lengthOfRoadPixels));
                        newRoad.addIntersection(grid[i][j], CardinalDirection.EAST);
                        newRoad.addIntersection(grid[i][j + 1], CardinalDirection.WEST);
                        roads.add(newRoad);
                        grid[i][j].addRoad(newRoad, CardinalDirection.EAST);
                        grid[i][j + 1].addRoad(newRoad, CardinalDirection.WEST);*/
                    }
                    //Check the vertical grid
                    if (i + 1 != grid.length && grid[i][j] != null && grid[i + 1][j] != null) {
                        addRoad(getIntersection(i, j), getIntersection(i + 1, j), Orientation.HORIZONTAL);/*
                        double posX = grid[i][j].getCentre().getX() + (grid[i][j].getCentre().getDifference(grid[i + 1][j].getCentre(), Position
                                .DIMENSION.X)) / 2;
                        double posY = grid[i][j].getCentre().getY() + (grid[i][j].getCentre().getDifference(grid[i + 1][j].getCentre(), Position
                                .DIMENSION.Y)) / 2;
                        Road newRoad = new Road(Orientation.HORIZONTAL, new BoundingBox(new Position(posX, posY), DimensionManager.lengthOfRoadPixels,
                                DimensionManager.widthOfIntersectionPixels));
                        roads.add(newRoad);
                        newRoad.addIntersection(grid[i][j], CardinalDirection.SOUTH);
                        newRoad.addIntersection(grid[i + 1][j], CardinalDirection.NORTH);
                        grid[i][j].addRoad(newRoad, CardinalDirection.SOUTH);
                        grid[i + 1][j].addRoad(newRoad, CardinalDirection.NORTH);*/
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


}
