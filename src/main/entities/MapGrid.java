package main.entities;

public class MapGrid {
    Intersection[][] grid;
    int width, height;

    //FIXME Not final at all.
    public MapGrid(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new Intersection[width][height];
    }

    public Intersection[][] getGrid() {
        return grid;
    }

    public boolean addIntersection(int x, int y, Intersection intersection) {
        //FIXME Needs a hilarious amount added
        grid[x][y] = intersection;
        return false;
    }

    public void setGrid(Intersection[][] grid) {
        this.grid = grid;
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
}
