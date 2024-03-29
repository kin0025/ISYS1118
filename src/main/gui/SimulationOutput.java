package main.gui;

import main.entities.MapGrid;
import main.entities.RoadSegment;
import main.entities.car.Car;
import main.entities.intersection.Intersection;
import main.entities.lane.Lane;
import main.utils.BoundingBox;
import main.utils.DimensionManager;
import main.utils.Position;
import main.utils.enums.LightStatus;
import main.utils.enums.Orientation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

public class SimulationOutput extends JPanel {

    private final MapGrid grid;
    private final JFrame frame = new JFrame("Simulator Output");
    private final boolean displayGrid;

    private final Color bgColour = Color.getHSBColor(0.33334f, 1f, 0.27f);
    private final Color carColour = Color.red;
    private final Color laneColour = Color.white;
    private final Color roadColour = Color.black;
    private final Color intersectionColour = Color.orange;

    public SimulationOutput(MapGrid grid, boolean displayGrid) {
        this.displayGrid = displayGrid;
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


        g2.setPaint(bgColour);
        g2.setBackground(bgColour);
        g2.fill(new Rectangle.Double(0, 0, d.width - 1, d.height - 1));
        g2.drawRect(0, 0, d.width - 1, d.height - 1);

        //Display all the roads
        for (RoadSegment roadSegment : grid.getRoadSegments()) {
            drawRoad(g2, roadSegment);
        }

        //Display all the objects in intersections in the grid
        for (int i = 0; i < grid.getGrid().length; i++) {
            for (Intersection intersection : grid.getGrid()[i]) {
                drawIntersection(g2, intersection);
            }
        }
        for (RoadSegment roadSegment : grid.getRoadSegments()) {
            for (Lane lane : roadSegment.getLanes()) {
                for (Car laneCar : lane.getCars()) {
                    drawCar(g2, laneCar);
                    //Display the cars from here
                }
            }
        }


    }

    public void drawRoad(Graphics2D g2, RoadSegment roadSegment) {
        if (roadSegment != null) {
            g2.setPaint(roadColour);
            BoundingBox roadBox = roadSegment.getBoundingBox();
            g2.fill(new Rectangle2D.Double(roadBox.getxMin(), roadBox.getyMin(), roadBox.getWidth(), roadBox.getHeight()));

            for (Lane lane : roadSegment.getLanes()) {
                if (lane != null) {
                    g2.setPaint(laneColour);
                    g2.draw(new Rectangle2D.Double(lane.getBoundingBox().getxMin(), lane.getBoundingBox().getyMin(), lane.getBoundingBox()
                            .getWidth(), lane
                            .getBoundingBox().getHeight()));
                }
            }

        }
    }


    public void drawIntersection(Graphics2D g2, Intersection intersection) {
        if (intersection == null) {
            return;
        }
        BoundingBox intersectionBox = intersection.getBoundingBox();
        g2.setPaint(intersectionColour);
        g2.fill(new Rectangle2D.Double(intersectionBox.getxMin(), intersectionBox.getyMin(), intersectionBox.getWidth(),
                intersectionBox.getHeight()));

        for (Car car : intersection.getCars()) {
            drawCar(g2, car);

        }
        Orientation[] orientations = {Orientation.HORIZONTAL, Orientation.VERTICAL};
        for (Orientation orientation : orientations) {
            LightStatus lightStatus = intersection.getLightStatus(orientation);
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
            if (orientation == Orientation.HORIZONTAL) {
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
    }

    public void drawCar(Graphics2D g, Car car) {
        if (car == null) {
            return;
        }
        //g.drawString("" +car.getCarBox().getAngle(), (int) car.getCarBox().getxMin(), (int) car.getCarBox().getyMin());
        g.setPaint(carColour);
        Position[] coordinates = car.getCarBox().getCorners();
        // fill and stroke GeneralPath
        GeneralPath carPolygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
                coordinates.length);
        carPolygon.moveTo(coordinates[0].getX(), coordinates[0].getY());
        //g.drawString("" + 0, (int) coordinates[0].getX(), (int) coordinates[0].getY());
        for (int index = 1; index < coordinates.length; index++) {
            //g.drawString("" + index, (int) coordinates[index].getX(), (int) coordinates[index].getY());
            carPolygon.lineTo(coordinates[index].getX(), coordinates[index].getY());
        }
        carPolygon.closePath();
        g.fill(carPolygon);
        // g.fill(new Ellipse2D.Double(car.getPosition().getX(), car.getPosition().getY(), DimensionManager.widthOfCarPixels, DimensionManager
        // .widthOfCarPixels));

    }

}
