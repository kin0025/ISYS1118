import main.Simulator;
import main.gui.SimulationOutput;
import main.utils.DimensionManager;
import main.utils.Orientation;

import java.util.Timer;
import java.util.TimerTask;

public class TrafficSimulator {

    public static void main(String args[]) {
        boolean graphicsDebug = true;

        Simulator sim = new Simulator();
        sim.createNewMap(4, 5);

        @SuppressWarnings("ConstantConditions") SimulationOutput output = new SimulationOutput(sim.getMapGrid(),graphicsDebug);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                sim.getMapGrid().addIntersection(i, j, DimensionManager.secondsToTicks(5), DimensionManager.secondsToTicks(20), Orientation.HORIZONTAL);
            }
        }
        sim.getMapGrid().removeIntersections(1, 2);
        sim.getMapGrid().fillRoads();
        System.out.println(sim.getMapGrid().addRoad(0,0,Orientation.VERTICAL));
        //sim.getMapGrid().addLane();
        //sim.addSpawnPoint();

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sim.runSimulation();
                output.repaint();
            }
        }, 20, 20);

    }

}
