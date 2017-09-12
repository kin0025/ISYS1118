import main.Simulator;
import main.entities.lane.CarSpawn;
import main.gui.SimulationOutput;
import main.utils.Orientation;

import javax.swing.*;
import java.awt.*;

public class TrafficSimulator {

    public static void main(String args[]) {
        Simulator sim = new Simulator();
        sim.createNewMap(4, 5);

        SimulationOutput output = new SimulationOutput(sim.getMapGrid());

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                sim.getMapGrid().addIntersection(i, j, 10, 10, Orientation.HORIZONTAL);
            }
        }
        sim.getMapGrid().removeIntersections(1, 2);
        sim.getMapGrid().fillRoads();
        //sim.addSpawnPoint();
        while (true) {
            sim.runSimulation();
        }
    }

}
