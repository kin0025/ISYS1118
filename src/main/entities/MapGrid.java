package main.entities;

import main.utils.Orientation;

import java.util.ArrayList;

public class MapGrid {
    private Intersection[][] grid;
    private int width;
    private int height;
    private ArrayList<Road> roads;


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

    public boolean addIntersection(int x, int y, Intersection intersection) {
        //FIXME Needs a hilarious amount added
        grid[x][y] = intersection;
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

    public void fillGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                roads.add(new Road(new Orientation(Orientation.ENUM.VERTICAL)));
            }
        }
    }
}
