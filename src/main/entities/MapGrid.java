package main.entities;

import main.entities.intersection.Intersection;
import main.entities.lane.Lane;
import main.utils.*;

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

    public boolean addIntersection(int x, int y, int lightTimeV, int lightTimeH, Orientation startingLight) {
        //FIXME Needs a hilarious amount added
        int offset = DimensionManager.lengthOfRoadPixels + DimensionManager.widthOfIntersectionPixels;
        grid[x][y] = new Intersection(new Position((x * offset) + DimensionManager.lengthOfRoadPixels, (y * offset) + DimensionManager
                .lengthOfRoadPixels), lightTimeV, lightTimeH, startingLight);
        return false;
    }

    public void removeIntersections(int x,int y){
        grid[x][y] = null;
    }
    public boolean addLane(Lane lane, int columnm, int row) {
        boolean added = false;
        for (Road road : roads
                ) {
            if (road.getRoadCoordinate()[0] == columnm && road.getRoadCoordinate()[1] == row) {
                road.addLane(lane);
                added = true;
            }

        }
        return added;
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
        //TODO remove all existing roads from intersections before filling the grid.
        roads = new ArrayList<>();

        //Iterate through all the columns of the grid
        for (int i = 0; i < grid.length - 1; i++) {
            //Iterate through the rows of intersections
            for (int j = 0; j < grid[i].length - 1; j++) {
                if (grid[i][j] != null) {
                    //Clear existing roads.
                    grid[i][j].removeRoads();

                    //Check the horizontal grid
                    if (grid[i][j] != null && grid[i][j + 1] != null) {
                        double posX = grid[i][j].getCentre().getX() + (grid[i][j].getCentre().getDifference(grid[i][j + 1].getCentre(), Position
                                .DIMENSION.X)) / 2;
                        double posY = grid[i][j].getCentre().getY() + (grid[i][j].getCentre().getDifference(grid[i][j + 1].getCentre(), Position
                                .DIMENSION.Y)) / 2;
                        Road newRoad = new Road(Orientation.VERTICAL, new BoundingBox(new Position(posX, posY), DimensionManager
                                .widthOfRoadPixels, DimensionManager.lengthOfRoadPixels));
                        roads.add(newRoad);
                        grid[i][j].addRoad(newRoad, new Direction(CardinalDirection.EAST));
                        grid[i][j + 1].addRoad(newRoad, new Direction(CardinalDirection.WEST));
                    }
                    //Check the vertical grid
                    if (grid[i][j] != null && grid[i + 1][j] != null) {
                        double posX = grid[i][j].getCentre().getX() + (grid[i][j].getCentre().getDifference(grid[i + 1][j].getCentre(), Position
                                .DIMENSION.X)) / 2;
                        double posY = grid[i][j].getCentre().getY() + (grid[i][j].getCentre().getDifference(grid[i + 1][j].getCentre(), Position
                                .DIMENSION.Y)) / 2;
                        System.out.println(grid[i][j].getCentre().getX());
                        System.out.println(posX);
                        Road newRoad = new Road(Orientation.HORIZONTAL, new BoundingBox(new Position(posX, posY), DimensionManager.lengthOfRoadPixels,
                                DimensionManager.widthOfIntersectionPixels));
                        roads.add(newRoad);
                        grid[i][j].addRoad(newRoad, new Direction(CardinalDirection.SOUTH));
                        grid[i+1][j].addRoad(newRoad, new Direction(CardinalDirection.NORTH));
                    }
                }
            }
        }

    }

    public ArrayList<Road> getRoads() {
        return roads;
    }
}
