import main.Simulator;
import main.gui.CommandLineController;
import main.gui.GraphicsController;
import main.gui.InputController;
import main.gui.SimulationOutput;
import main.utils.DimensionManager;
import main.utils.enums.Orientation;

import java.util.Timer;
import java.util.TimerTask;

public class TrafficSimulator {

    public static void main(String args[]) {

        boolean graphicalConfig = false;
        boolean displayGrid = true;

        InputController inputController;

        Timer timer = new Timer();

        Simulator simulator = new Simulator();
        simulator.createNewMap(4,5);

        if(graphicalConfig) {
            inputController = new GraphicsController(simulator);
        }else{
            inputController = new CommandLineController(simulator);
        }

        //TEMP STUFF FOR THINGS
        SimulationOutput output = new SimulationOutput(simulator.getMapGrid(), displayGrid);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                simulator.getMapGrid().addIntersection(i, j, DimensionManager.secondsToTicks(5), DimensionManager.secondsToTicks(20), Orientation.HORIZONTAL);
            }
        }
        simulator.getMapGrid().removeIntersections(1, 2);
        simulator.getMapGrid().fillRoads();
        System.out.println(simulator.getMapGrid().addRoad(0, 0, Orientation.VERTICAL));
        //simulator.getMapGrid().addLane();
        //simulator.addSpawnPoint();


        Thread UI = new Thread() {
            public void run() {
                try {
                    System.out.println("We made it!");
                    boolean go = true;
                    while(go){
                        go = inputController.mainMenu();

                    }
                    Thread.sleep(1000);
                    System.exit(0);
                } catch(InterruptedException v) {
                    System.out.println(v);
                }
            }
        };

        UI.start();


        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(!simulator.isPaused()) {
                    simulator.runSimulation();
                    output.repaint();
                }
            }
        }, 20, 20);



    }

}
