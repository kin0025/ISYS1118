import main.Simulator;
import main.gui.CommandLineController;
import main.gui.GraphicsController;
import main.gui.InputController;
import main.gui.SimulationOutput;

import java.util.Timer;
import java.util.TimerTask;

public class TrafficSimulator {

    public static void main(String args[]) {

        boolean graphicalConfig = false;
        boolean displayGrid = true;

        InputController inputController;

        Timer timer = new Timer();

        Simulator simulator = new Simulator();
        simulator.createNewMap(5, 5);

        if (graphicalConfig) {
            inputController = new GraphicsController(simulator);
        } else {
            inputController = new CommandLineController(simulator);
        }

        //TEMP STUFF FOR THINGS
        SimulationOutput output = new SimulationOutput(simulator.getMapGrid(), displayGrid);

        Thread UI = new Thread(() -> {
            try {
                boolean go = true;
                while (go) {
                    go = inputController.mainMenu();

                }
                Thread.sleep(1000);
                System.exit(0);
            } catch (InterruptedException v) {
                System.out.println(v);
            }
        });

        UI.start();


        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!simulator.isLocked()) {
                    simulator.runSimulation();
                    output.repaint();
                }

            }
        }, 20, 20);


    }

}
