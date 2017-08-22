import main.Simulator;
import main.entities.Intersection;
import main.gui.SimulationOutput;

public class TrafficSimulator {

    public static void main(String args[]) {
        Simulator sim = new Simulator();
        sim.createNewMap(4,5);

        SimulationOutput output = new SimulationOutput(sim.getMapGrid());

        for(int i = 0; i < 4; i++){
            for(int j = 0; j <5; j++){
                sim.getMapGrid().addIntersection(i,j);
            }
        }
        sim.getMapGrid().fillRoads();
        sim.runSimulation();

    }

}
