package main.gui;

import main.entities.*;
import main.entities.intersection.Intersection;
import main.entities.lane.Lane;
import main.utils.DimensionManager;
import main.utils.Orientation;
import main.utils.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

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
        g2.setBackground(bgColour);
        g2.fill(new Rectangle.Double(0, 0, d.width - 1, d.height - 1));
        g2.drawRect(0, 0, d.width - 1, d.height - 1);

        //Display all the roads
        for (Road road : grid.getRoads()) {
            g2.setPaint(roadColour);
            double x, y, x2, y2;
            if (road.getOrientation() == Orientation.VERTICAL) {
                x = road.getPosition().getX() ;//- (DimensionManager.lengthOfRoadPixels / 2);
                x2 = (DimensionManager.lengthOfRoadPixels);
                y = road.getPosition().getY() ;//- (DimensionManager.widthOfRoadPixels / 2);
                y2 = (DimensionManager.widthOfRoadPixels);
            } else {
                y = road.getPosition().getY() ;//- (DimensionManager.lengthOfRoadPixels/2);
                y2 = (DimensionManager.lengthOfRoadPixels);
                x = road.getPosition().getX() ;//- (DimensionManager.widthOfRoadPixels/2);
                x2 = (DimensionManager.widthOfRoadPixels);
            }
            g2.fill(new Rectangle2D.Double(x, y, x2, y2));

            //Check if road hasn't already been displayed? and then show it
            for (Lane lane : road.getLanes()) {
                for (Car laneCars : lane.getCars()) {
                    Position carPos = laneCars.getPosition();
                    g2.setPaint(carColour);
                    g2.fill(new Rectangle2D.Double(carPos.getX(), carPos.getY(), DimensionManager.lengthOfCarPixels, DimensionManager
                            .widthOfCarPixels));

                    //Display the cars from here
                }

            }

        }

        //Display all the objects in intersections in the grid
        for (int i = 0; i < grid.getGrid().length; i++) {
            for (Intersection intersection : grid.getGrid()[i]) {
                Position intersectionPos = intersection.getPosition();
                g2.setPaint(intersectionColour);
                g2.fill(new Ellipse2D.Double(intersectionPos.getX(), intersectionPos.getY(), DimensionManager.widthOfIntersectionPixels,
                        DimensionManager.widthOfIntersectionPixels));

                for (Car intersectionCar : intersection.getCars()) {
                    Position carPos = intersectionCar.getPosition();
                    g2.setPaint(carColour);
                    g2.fill(new Rectangle2D.Double(carPos.getX(), carPos.getY(), DimensionManager.lengthOfCarPixels, DimensionManager
                            .widthOfCarPixels));
                    //TODO Display cars here

                }


            }
        }


    }

}
