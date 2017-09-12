import main.Simulator;
import main.gui.SimulationOutput;
import main.utils.Orientation;

public class TrafficSimulator {

    public static void main(String args[]) {
        boolean graphicsDebug = true;

        Simulator sim = new Simulator();
        sim.createNewMap(4, 5);

        @SuppressWarnings("ConstantConditions") SimulationOutput output = new SimulationOutput(sim.getMapGrid(),graphicsDebug);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                sim.getMapGrid().addIntersection(i, j, 10, 10, Orientation.HORIZONTAL);
            }
        }
        sim.getMapGrid().removeIntersections(1, 2);
        sim.getMapGrid().fillRoads();
        System.out.println(sim.getMapGrid().addRoad(0,0,Orientation.VERTICAL));
        //sim.addSpawnPoint();
        while (true) {
            sim.runSimulation();
        }
    }

}
