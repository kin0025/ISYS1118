package main.entities;

import main.entities.intersection.Intersection;
import main.utils.DimensionManager;
import main.utils.Direction;
import main.utils.Orientation;
import main.utils.Position;

import java.util.ArrayList;

public class MapGrid {
    private final Intersection[][] grid;
    private final int width;
    private final int height;
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

    public int getHeight() {
        return height;
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
                    double posX = grid[i][j].getPosition().getX() + (grid[i][j].getPosition().getDifference(grid[i][j + 1].getPosition(), Position
                            .DIMENSION.X))/2;
                    double posY = grid[i][j].getPosition().getY() + (grid[i][j].getPosition().getDifference(grid[i][j + 1].getPosition(), Position
                            .DIMENSION.Y))/2;
                    Road newRoad = new Road(Orientation.HORIZONTAL, new Position(posX, posY));
                    roads.add(newRoad);
                    grid[i][j].addRoad(newRoad, new Direction(Direction.COMPASS_DIRECTION.EAST));
                    grid[i][j + 1].addRoad(newRoad, new Direction(Direction.COMPASS_DIRECTION.WEST));
                }
                //Check the vertical grid
                if (grid[i][j] != null && grid[i + 1][j] != null) {
                    double posX = grid[i][j].getPosition().getX() + (grid[i][j].getPosition().getDifference(grid[i + 1][j].getPosition(), Position
                            .DIMENSION.X))/2;
                    double posY = grid[i][j].getPosition().getY() + (grid[i][j].getPosition().getDifference(grid[i + 1][j].getPosition(), Position
                            .DIMENSION.Y))/2;
                    System.out.println(grid[i][j].getPosition().getX());
                    System.out.println(posX);
                    Road newRoad = new Road(Orientation.VERTICAL, new Position(posX, posY));
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
