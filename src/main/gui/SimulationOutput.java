package main.gui;

import main.entities.*;

public class SimulationOutput {

    public void displayOutput(MapGrid grid) {
        //Display all the somethings in the something

        //Display all the objects in the grid
        for (int i = 0; i < grid.getGrid().length; i++) {
            for (Intersection intersection : grid.getGrid()[i]) {
                //Display the intersection here

                for (Car intersectionCars : intersection.getCars()) {
                    //Display cars here

                }
                for (Road roads : intersection.getRoads()) {
                    //Check if road hasn't already been displayed? and then show it
                    for (Lane lane : roads.getLanes()) {

                        for (Car laneCars : lane.getCars()) {
                            //Display the cars from here
                        }

                    }

                }

            }
        }
    }
}
