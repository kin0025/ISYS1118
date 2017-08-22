package main.gui;

import main.entities.*;
import main.utils.DimensionManager;

import javax.swing.*;
import java.awt.*;

public class SimulationOutput extends JPanel {

    private MapGrid grid;
    private JFrame frame = new JFrame("Simulator Output");


    public SimulationOutput(MapGrid grid) {
        this.grid = grid;
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        int WIDTH = grid.getWidth() * (DimensionManager.widthOfIntersectionPixels + DimensionManager.lengthOfRoadPixels);
        int HEIGHT = grid.getHeight() * (DimensionManager.widthOfIntersectionPixels + DimensionManager.lengthOfRoadPixels);

        //FIXME I have no idea what this does, please make sure it is correct later
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setContentPane(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //http://docs.oracle.com/javase/tutorial/2d/geometry/strokeandfill.html
    //http://docs.oracle.com/javase/tutorial/2d/geometry/primitives.html
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Dimension d = getSize();
        Color bgColour = Color.green;
        Color carColour = Color.red;
        Color roadColour = Color.black;
        Color intersectionColour = Color.orange;

        g2.setPaint(bgColour);
        g2.fill(new Rectangle.Double(0,0,d.width-1,d.height-1));
        g2.drawRect(0, 0, d.width - 1, d.height - 1);

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
