package main.entities;

import main.utils.DimensionManager;
import main.utils.Direction;
import main.utils.Orientation;
import main.utils.Position;

import java.util.ArrayList;

public class MapGrid {
    private Intersection[][] grid;
    private int width;
    private int height;
    private ArrayList<Road> roads = new ArrayList<>();


    //FIXME Not final at all.
    public MapGrid(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new Intersection[width][height];

        //A list of all the roads in the grid - used to create roads and display them.
    }

    public Intersection[][] getGrid() {
        return grid;
    }

    public void setGrid(Intersection[][] grid) {
        this.grid = grid;
    }

    public boolean addIntersection(int x, int y) {
        //FIXME Needs a hilarious amount added
        int offset = DimensionManager.lengthOfRoadPixels + DimensionManager.widthOfIntersectionPixels;
        grid[x][y] = new Intersection(new Position((x * offset) + DimensionManager.lengthOfRoadPixels, (y * offset) + DimensionManager
                .lengthOfRoadPixels));
        return false;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Needs to empty any roads in intersections if there are any - call removeAllRoads on intersections?
     * Generate all connecting roads - lanes are auto created by the roads.
     */
    public void fillRoads() {
        //TODO remove all existing roads from intersections before filling the grid.
        //Iterate through all the columns of the grid
        for (int i = 0; i < grid.length - 1; i++) {
            //Iterate through the rows of intersections
            for (int j = 0; j < grid[i].length - 1; j++) {
                //Check the horizontal grid
                if (grid[i][j] != null && grid[i][j + 1] != null) {
                    Road newRoad = new Road(new Orientation(Orientation.ENUM.HORIZONTAL));
                    roads.add(newRoad);
                    grid[i][j].addRoad(newRoad, new Direction(Direction.COMPASS_DIRECTION.EAST));
                    grid[i][j + 1].addRoad(newRoad, new Direction(Direction.COMPASS_DIRECTION.WEST));
                }
                //Check the vertical grid
                if (grid[i][j] != null && grid[i + 1][j] != null) {
                    Road newRoad = new Road(new Orientation(Orientation.ENUM.VERTICAL));
                    roads.add(newRoad);
                    grid[i][j].addRoad(newRoad, new Direction(Direction.COMPASS_DIRECTION.SOUTH));
                    grid[i][j + 1].addRoad(newRoad, new Direction(Direction.COMPASS_DIRECTION.NORTH));
                }
            }
        }

    }

    public ArrayList<Road> getRoads() {
        return roads;
    }
}
