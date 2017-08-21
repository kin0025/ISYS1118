package main;

import main.entities.Intersection;
import main.entities.MapGrid;


public class Simulator {

    Intersection[][] grid = new Intersection[5][5];

    public void runSimulation(){
        boolean stop = false;
        boolean pause = false;
        //This entire thing is temporary.
        while(!stop) {
            while (!pause) {
                long time = 0;
                //MapGrid grid = new MapGrid();



                time++;
            }
            try{
                wait(1);
            }catch (InterruptedException e){
                break;
            }
        }
    }

    public boolean addRoad(){return false;}
}
