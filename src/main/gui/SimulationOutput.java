package main.gui;

import main.entities.*;
import main.utils.DimensionManager;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class SimulationOutput extends JPanel{

    private MapGrid grid;
    //private JFrame frame = new JFrame("Simulator Output");


    public SimulationOutput(MapGrid grid) {
        this.grid = grid;
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
        int WIDTH = grid.getWidth() * (DimensionManager.widthOfIntersectionPixels + DimensionManager.lengthOfRoadPixels);
        int HEIGHT = grid.getHeight() * (DimensionManager.widthOfIntersectionPixels + DimensionManager.lengthOfRoadPixels);

        setPreferredSize(new Dimension(WIDTH,HEIGHT));
    }

    public void displayOutput() {
        setBackground(Color.BLACK);
        //Display all the objects in intersections in the grid
        for (int i = 0; i < grid.getGrid().length; i++) {
            for (Intersection intersection : grid.getGrid()[i]) {
                //TODO Display the intersection here

                for (Car intersectionCars : intersection.getCars()) {
                    //TODO Display cars here

                }


            }
        }

        //Display all the roads
        for (Road roads : grid.getRoads()) {
            //Check if road hasn't already been displayed? and then show it
            for (Lane lane : roads.getLanes()) {
                for (Car laneCars : lane.getCars()) {
                    //Display the cars from here
                }

            }

        }
    }
}
