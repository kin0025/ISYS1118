package main.gui;

import main.entities.Car;
import main.entities.MapGrid;
import main.entities.Road;
import main.entities.intersection.Intersection;
import main.entities.lane.Lane;
import main.utils.*;
import main.utils.enums.LightStatus;
import main.utils.enums.Orientation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class SimulationOutput extends JPanel {

    private MapGrid grid;
    private JFrame frame = new JFrame("Simulator Output");
    private boolean debug;

    public SimulationOutput(MapGrid grid, boolean graphicsDebug) {
        this.debug = graphicsDebug;
        this.grid = grid;
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        int WIDTH = grid.getWidth() * (DimensionManager.widthOfIntersectionPixels + DimensionManager.lengthOfRoadPixels) + DimensionManager
                .lengthOfRoadPixels;
        int HEIGHT = grid.getHeight() * (DimensionManager.widthOfIntersectionPixels + DimensionManager.lengthOfRoadPixels) + DimensionManager
                .lengthOfRoadPixels;

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

        //Set the colours for things here.
        Color bgColour = Color.getHSBColor(0.33334f, 1f, 0.27f);
        Color carColour = Color.red;
        Color laneColour = Color.white;
        Color roadColour = Color.black;
        Color intersectionColour = Color.orange;


        g2.setPaint(bgColour);
        g2.setBackground(bgColour);
        g2.fill(new Rectangle.Double(0, 0, d.width - 1, d.height - 1));
        g2.drawRect(0, 0, d.width - 1, d.height - 1);

        //Display all the roads
        for (Road road : grid.getRoads()) {
            if (road != null) {
                g2.setPaint(roadColour);
                BoundingBox roadBox = road.getBoundingBox();
                g2.fill(new Rectangle2D.Double(roadBox.getxMin(),
                        roadBox.getyMin(),
                        roadBox.getWidth(), roadBox.getHeight()));

                for (Lane lane : road.getLanes()) {
                    if (lane != null) {
                        g2.setPaint(laneColour);
                        g2.draw(new Rectangle2D.Double(lane.getBoundingBox().getxMin(), lane.getBoundingBox().getyMin(), lane.getBoundingBox()
                                .getWidth(), lane
                                .getBoundingBox().getHeight()));

                        for (Car laneCars : lane.getCars()) {
                            Position carPos = laneCars.getPosition();
                            g2.setPaint(carColour);
                            g2.fill(new Rectangle2D.Double(carPos.getX(), carPos.getY(), DimensionManager.lengthOfCarPixels, DimensionManager
                                    .widthOfCarPixels));
                            //Display the cars from here
                        }
                    }
                }

            }
        }

        //Display all the objects in intersections in the grid
        for (int i = 0; i < grid.getGrid().length; i++) {
            for (Intersection intersection : grid.getGrid()[i]) {
                if (intersection != null) {
                    BoundingBox intersectionBox = intersection.getBoundingBox();
                    g2.setPaint(intersectionColour);
                    g2.fill(new Rectangle2D.Double(intersectionBox.getxMin(), intersectionBox.getyMin(), intersectionBox.getWidth(),
                            intersectionBox.getHeight()));

                    Orientation[] orientations = {Orientation.HORIZONTAL, Orientation.VERTICAL};
                    for (int x = 0; x < orientations.length; x++) {
                        LightStatus lightStatus = intersection.getLightStatus(orientations[x]);
                        switch (lightStatus) {
                            case RED:
                                g2.setPaint(Color.red);
                                break;
                            case AMBER:
                                g2.setPaint(Color.orange);
                                break;
                            case GREEN:
                                g2.setPaint(Color.green);
                                break;
                            default:
                                g2.setPaint(Color.white);

                        }
                        double xPos1 = -DimensionManager
                                .sizeOfLightPixels / 2;
                        double yPos1 = -DimensionManager
                                .sizeOfLightPixels / 2;
                        double yPos2 = -DimensionManager
                                .sizeOfLightPixels / 2;
                        double xPos2 = -DimensionManager
                                .sizeOfLightPixels / 2;
                        ;
                        if (orientations[x] == Orientation.HORIZONTAL) {
                            xPos1 += intersection.getCentre().getX() - (DimensionManager.widthOfIntersectionPixels / 2);
                            xPos2 += intersection.getCentre().getX() + (DimensionManager.widthOfIntersectionPixels / 2);
                            yPos1 += intersection.getCentre().getY();
                            yPos2 += intersection.getCentre().getY();
                        } else {
                            xPos1 += intersection.getCentre().getX();
                            xPos2 += intersection.getCentre().getX();
                            yPos1 += intersection.getCentre().getY() - (DimensionManager.widthOfIntersectionPixels / 2);
                            yPos2 += intersection.getCentre().getY() + (DimensionManager.widthOfIntersectionPixels / 2);
                        }
                        g2.fill(new Ellipse2D.Double(xPos1, yPos1, DimensionManager.sizeOfLightPixels, DimensionManager.sizeOfLightPixels));
                        g2.fill(new Ellipse2D.Double(xPos2, yPos2, DimensionManager.sizeOfLightPixels, DimensionManager.sizeOfLightPixels));
                        g2.setPaint(Color.white);
                        g2.draw(new Ellipse2D.Double(xPos1, yPos1, DimensionManager.sizeOfLightPixels, DimensionManager.sizeOfLightPixels));
                        g2.draw(new Ellipse2D.Double(xPos2, yPos2, DimensionManager.sizeOfLightPixels, DimensionManager.sizeOfLightPixels));
                    }
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

}
