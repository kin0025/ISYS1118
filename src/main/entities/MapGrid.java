package main.entities;

import main.entities.intersection.Intersection;
import main.entities.lane.Lane;
import main.utils.*;
import main.utils.enums.CardinalDirection;
import main.utils.enums.Orientation;

import java.util.ArrayList;

public class MapGrid {
    private final Intersection[][] grid;
    private final int width;
    private final int height;
    private final Road[][] horizontalRoads;
    private final Road[][] verticalRoads;
    private ArrayList<Road> roads = new ArrayList<>();

    //FIXME Not final at all.
    public MapGrid(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new Intersection[width][height];
        //A list of all the roads in the grid - used to create roads and display them.
        horizontalRoads = new Road[width + 1][height];
        verticalRoads = new Road[width][height + 1];
    }

    public Intersection[][] getGrid() {
        return grid;
    }

    public boolean addIntersection(int x, int y, int lightTimeV, int lightTimeH, Orientation startingLight) {
        //FIXME Needs a hilarious amount added
        int offset = DimensionManager.lengthOfRoadPixels + DimensionManager.widthOfIntersectionPixels;
        grid[x][y] = new Intersection(new Position((x * offset) + offset, (y * offset) + offset), lightTimeV, lightTimeH, startingLight);
        return false;
    }

    public void removeIntersections(int x, int y) {
        grid[x][y] = null;
    }

    public boolean addLane(Lane lane, int column, int row) {
        if (lane.getDirection().getOrientation() == Orientation.HORIZONTAL && horizontalRoads[column][row] != null) {
            horizontalRoads[column][row].addLane(lane);
            return true;
        } else if (verticalRoads[column][row] != null) {
            verticalRoads[column][row].addLane(lane);
            return true;
        }
        return false;
    }

    public boolean addRoad(int x, int y, Orientation orientation) {
        if (x > width || x < -1 || y > height || y < -1) {
            return false;
        }
        double xPos, xWidth;
        double yPos, yWidth;
        int offset = DimensionManager.lengthOfRoadPixels + DimensionManager.widthOfIntersectionPixels;
        if (orientation == Orientation.VERTICAL) {
            if (verticalRoads[x][y] != null) {
                return false;
            }
            xPos = (x) * offset + offset;
            yPos = (y) * offset + (offset/2);
            xWidth = DimensionManager.widthOfRoadPixels;
            yWidth = DimensionManager.lengthOfRoadPixels;
        } else {
            if (horizontalRoads[x][y] != null) {
                return false;
            }
            xPos = (x * offset) + (offset/2);
            yPos = (y * offset) + offset;
            xWidth = DimensionManager.lengthOfRoadPixels;
            yWidth = DimensionManager.widthOfRoadPixels;


        }
        roads.add(new Road(orientation, new BoundingBox(new Position(xPos, yPos), xWidth, yWidth)));
        return true;
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
                        roads.add(newRoad);
                        horizontalRoads[i][j + 1] = newRoad;
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
                        verticalRoads[i + 1][j] = newRoad;
                        grid[i][j].addRoad(newRoad, new Direction(CardinalDirection.SOUTH));
                        grid[i + 1][j].addRoad(newRoad, new Direction(CardinalDirection.NORTH));
                    }
                }
            }
        }

    }

    public ArrayList<Road> getRoads() {
        return roads;
    }
}
