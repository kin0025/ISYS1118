package main.entities;

import main.entities.intersection.Intersection;
import main.entities.lane.Lane;
import main.utils.BoundingBox;
import main.utils.DimensionManager;
import main.utils.Direction;
import main.utils.Position;
import main.utils.enums.CardinalDirection;
import main.utils.enums.Orientation;

import java.util.ArrayList;

public class MapGrid {
    private final int width;
    private final int height;
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
        //FIXME Needs a hilarious amount added
        int offset = DimensionManager.lengthOfRoadPixels + DimensionManager.widthOfIntersectionPixels;
        grid[x][y] = new Intersection(new Position((x * offset) + offset, (y * offset) + offset), lightTimeV, lightTimeH, startingLight);
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
        for (Road road : roads) {
            if (road.hasIntersection(intersection)) {
                System.out.println("Removing Road");
                roads.remove(road);
            }
        }
        grid[x][y] = null;
    }

    public boolean addLane(Lane lane, Intersection intersection1, Intersection intersection2) {
        Road road = getRoad(intersection1, intersection2);
        if (road.getOrientation() == lane.getDirection().getOrientation()) {
            road.addLane(lane);
            return true;
        }
        return false;
    }

    public boolean addRoad(Intersection intersection1, Intersection intersection2, Orientation orientation) {
        if (intersectionsAreAdjacent(intersection1, intersection2, orientation) && getRoad(intersection1, intersection2) == null) {

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
            roads.add(new Road(orientation, new BoundingBox(new Position(xPos, yPos), xWidth, yWidth)));
            return true;
        }
        return false;
    }

    public boolean intersectionsAreAdjacent(Intersection intersection1, Intersection intersection2) {
        return intersectionsAreAdjacent(intersection1, intersection2, null);
    }

    public boolean intersectionsAreAdjacent(Intersection intersection1, Intersection intersection2, Orientation orientation) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (grid[x][y] == intersection1) {
                    if (orientation == null || orientation == Orientation.VERTICAL) {
                        if (grid[x][y + 1] == intersection2) {
                            return true;
                        }
                        if (grid[x][y - 1] == intersection2) {
                            return true;
                        }
                    }
                    if (orientation == null || orientation == Orientation.HORIZONTAL) {
                        if (grid[x + 1][y] == intersection2) {
                            return true;
                        }
                        if (grid[x - 1][y] == intersection2) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    public Intersection getIntersection(int x, int y) {
        return grid[x][y];
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
                        double posX = grid[i][j].getCentre().getX() + (grid[i][j].getCentre().getDifference(grid[i][j + 1].getCentre(), Position
                                .DIMENSION.X)) / 2;
                        double posY = grid[i][j].getCentre().getY() + (grid[i][j].getCentre().getDifference(grid[i][j + 1].getCentre(), Position
                                .DIMENSION.Y)) / 2;
                        Road newRoad = new Road(Orientation.VERTICAL, new BoundingBox(new Position(posX, posY), DimensionManager
                                .widthOfRoadPixels, DimensionManager.lengthOfRoadPixels));
                        newRoad.addIntersection(grid[i][j],CardinalDirection.EAST);
                        newRoad.addIntersection(grid[i][j+1],CardinalDirection.WEST);
                        roads.add(newRoad);
                        grid[i][j].addRoad(newRoad, new Direction(CardinalDirection.EAST));
                        grid[i][j + 1].addRoad(newRoad, new Direction(CardinalDirection.WEST));
                    }
                    //Check the vertical grid
                    if (i + 1 != grid.length && grid[i][j] != null && grid[i + 1][j] != null) {
                        double posX = grid[i][j].getCentre().getX() + (grid[i][j].getCentre().getDifference(grid[i + 1][j].getCentre(), Position
                                .DIMENSION.X)) / 2;
                        double posY = grid[i][j].getCentre().getY() + (grid[i][j].getCentre().getDifference(grid[i + 1][j].getCentre(), Position
                                .DIMENSION.Y)) / 2;
                        Road newRoad = new Road(Orientation.HORIZONTAL, new BoundingBox(new Position(posX, posY), DimensionManager.lengthOfRoadPixels,
                                DimensionManager.widthOfIntersectionPixels));
                        roads.add(newRoad);
                        newRoad.addIntersection(grid[i][j],CardinalDirection.SOUTH);
                        newRoad.addIntersection(grid[i+1][j],CardinalDirection.NORTH);
                        grid[i][j].addRoad(newRoad, new Direction(CardinalDirection.SOUTH));
                        grid[i + 1][j].addRoad(newRoad, new Direction(CardinalDirection.NORTH));
                    }
                }
            }
        }

    }


    /**
     * Expands the grid so its
     *
     * @param x
     * @param y
     */
    public void expandGrid(int x, int y) {
        if (x > width && y > height) {
            Intersection[][] gridReplace = new Intersection[x][y];
            for (int i = 0; i < width; x++) {
                System.arraycopy(grid[i], 0, gridReplace[i], 0, height);
            }
            grid = gridReplace;
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


}
