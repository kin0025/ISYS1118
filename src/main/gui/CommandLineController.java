package main.gui;

import main.Simulator;
import main.entities.intersection.Intersection;
import main.entities.lane.CarSpawn;
import main.utils.DimensionManager;
import main.utils.enums.CardinalDirection;
import main.utils.enums.Orientation;

import java.util.Scanner;
import java.util.StringTokenizer;

public class CommandLineController implements InputController {

    private final Scanner input = new Scanner(System.in);

    private final Simulator simulator;


    public CommandLineController(Simulator simulator) {
        this.simulator = simulator;
    }

    /*
    Input code is from an old assignment
     */


    //Functional Methods

    /**
     * Prints the specified character "c" number of "times", then if new line == true prints a new line at the end
     **/
    private static void printCharTimes(char c, int times, boolean newLine) {
        //Prints the character times.
        for (int i = 0; i < times; i++) {
            System.out.print(c);
        }
        //If we want to print a new line, print a new line.
        if (newLine) {
            System.out.println();
        }
    }

    /**
     * Creates a new page with the title "title"
     **/
    private void newPage(String title) {
        //Sets the width of the page.
        int pageWidth = 150;
        //Print a new line a bunch to clear the screen.
//        for (int i = 30; i != 0; i--) {
//            System.out.printf("\n");
//        }
        //Print the first line, made of equal signs.
        printCharTimes('=', pageWidth, true);
        //Set the three components of a menu screen.
        String leftText = "Traffic Light Simulator";
        String rightText;

        if (!simulator.isPaused()) {
            rightText = "Running";
        } else {
            rightText = "Paused";
        }
        //Find the length of the menu areas.
        int left = leftText.length();
        int centre = title.length();
        int right = rightText.length();
        //Find the spacing between the three elements. Total width/2 - the size of left and half of the centre.
        int leftSpacing = pageWidth / 2 - left - centre / 2;
        //Due to float to int conversion, subtract 1 more than the length.
        int rightSpacing = (pageWidth / 2) - 1 - right - centre / 2;
        //Print the left
        System.out.print(leftText);
        //Print the spacing
        printCharTimes(' ', leftSpacing, false);
        //Print the centre
        System.out.print(title);
        //Print the right spacing
        printCharTimes(' ', rightSpacing, false);
        //Print the right text
        System.out.println(rightText);

        //Finish off the menu with another border.
        printCharTimes('=', pageWidth, true);
        System.out.println();
    }

    /**
     * Returns an array of Strings (for example {a,b,c} as a String in the format (a/b/c). Used for input.
     **/
    private String stringArrayToString(String[] array) {
        StringBuilder result = new StringBuilder("(");
        //Add all the things together
        for (int i = 0; i < array.length; i++) {
            //If this isn't the last entry in the array, add a / on as well
            if (i < array.length - 1) {
                result.append(array[i]).append("/");
            } else { //If it is the last entry, add a closing bracket instead.
                result.append(array[i]).append(")");
            }
        }
        return (result.toString());
    }

    /**
     * Reads a string of fixed length.
     *
     * @param flavourText     The text to be printed asking for input
     * @param options         the valid values for input
     * @param printOptionText whether the options are displayed to the user
     * @param outputLength
     * @return
     */
    private String receiveFixedString(String flavourText, String[] options, boolean printOptionText, int outputLength) {
        String inputString;
        do {
            //Receive input
            inputString = receiveStringInput(flavourText, options, printOptionText, false);
        } while (inputString.length() == 0);
        //Ensure input is the same.

        inputString = inputString.toLowerCase();

        //If the input we are given is longer than the specified maximum output, make it shorter.
        if (inputString.length() >= outputLength) {
            inputString = inputString.substring(0, outputLength);
        }
        boolean isCorrect = false;
        int i = 0;
        while (i < options.length && !isCorrect) {
            if (options[i].equals(inputString)) {
                isCorrect = true;
            }
            i++;
        }
        if (!isCorrect) {
            System.out.println("Input was not an option. Please try again.");
            return receiveFixedString(flavourText, options, true, outputLength);
            //If they didn't get it right the first time, supply options.
        }
        return (inputString);
    }

    /**
     * Receives an input. Prints the flavourText, then requests input from the user. Will continue requesting input from the user until input
     * matches an entry in the array options. if printOptionText is false will not show the user what options are available. Final number is max
     * length
     * of returned string
     **/
    private String receiveStringInput(String flavourText, String[] options, boolean printOptionText, boolean caseSensitive) {
        //Print the flavour text.
        System.out.print(flavourText);

        //If we have enabled option printing, print the array.
        if (printOptionText) {
            System.out.println(stringArrayToString(options));
        } else System.out.println(); //Otherwise end the line.
        return receiveStringInput(options, caseSensitive);
    }


    /**
     * Receives an input. Prints the flavourText, then requests input from the user. Will continue requesting input from the user until input
     * matches an entry in the array options. if printOptionText is false will not show the user what options are available. Final number is max
     * length
     * of returned string
     **/
    private String receiveStringInput(String[] options, boolean caseSensitive) {
        String output = receiveStringInput();

        boolean isCorrect = false;
        int i = 0;
        while (i < options.length && !isCorrect) {
            if (!caseSensitive && options[i].equalsIgnoreCase(output)) {
                isCorrect = true;
            } else if (options[i].equals(output)) {
                isCorrect = true;
            }
            i++;
        }
        if (isCorrect) {
            return (output);
        } else {
            System.out.println("Input was not an option. Please try again.");
            return receiveStringInput(options, caseSensitive);
        }
    }

    private String receiveStringInput(String flavourText) {
        System.out.println(flavourText);
        return receiveStringInput();
    }

    /**
     * Gets a non-null string input
     *
     * @return the string input received
     */
    private String receiveStringInput() {
        //Receive input
        String inputString = input.nextLine().trim();       //If it is too short, prompt for input again.
        while (inputString.length() == 0) {
            //Print the stuff again.
            System.out.println("Answer needs to be entered");
            inputString = input.nextLine().toLowerCase();
        }
        return (inputString);
    }


    /**
     * Prompts the user for input of a co-ordinate between 0 and maxX/Y.
     *
     * @param flavourText the text printed to prompt the user
     * @param max         the maximum valid value for the coordinates
     * @return the co-ordinates of the intersection, or null if input was invalid
     */
    private int[] receiveCoordinateInput(String flavourText, int[] max) {
        int[] output = new int[2];
        //Print the flavour text.
        String inputString = receiveStringInput(flavourText + " (x < " + (max[0] + 1) + " and y < " + (max[1] + 1) + ")");

        StringTokenizer tokenizer = new StringTokenizer(inputString, ",");
        if (tokenizer.countTokens() != 2) {
            System.out.println("Two coordinates were not entered, please try again.");
            return receiveCoordinateInput(flavourText, max);
        }
        int i = 0;
        while (tokenizer.hasMoreTokens()) {
            try {
                output[i] = Integer.parseInt(tokenizer.nextToken());
            } catch (NumberFormatException e) {
                System.out.println("Numbers were not entered in parsable format, please try again");
                return receiveCoordinateInput(flavourText, max);
            }
            if (output[i] > max[i] || output[i] < 0) {
                System.out.println("Coordinate was too large, please try again");
                return receiveCoordinateInput(flavourText, max);
            }
            i++;
        }
        return output;
    }

    /**
     * Receives an input. Prints the flavourText, then requests input from the user. Will continue requesting input from the user until input
     * matches an entry in the array options or is empty. if it is empty returns the defaultAnswer. Final number is length of returned string
     **/
    private String receiveStringInput(String flavourText, String[] options, String defaultAnswer) {
        //Print a prompt for the user to enter input.
        System.out.println(flavourText + " " + stringArrayToString(options) + "[" + defaultAnswer + "]");

        //Actually get input.
        String inputString = input.nextLine().toLowerCase();
        //If the user entered nothing, return the default input.
        if (inputString.length() == 0) {
            return (defaultAnswer);
        }

        boolean isCorrect = false;
        int i = 0;
        while (i < options.length && !isCorrect) {
            if (options[i].equals(inputString)) {
                isCorrect = true;
            }
            i++;
        }
        if (isCorrect) {
            return inputString;
        } else {
            System.out.println("Input was not an option. Please try again.");
            return receiveStringInput(flavourText, options, defaultAnswer);//If they didn't get it right the first time, supply
            // options.
        }
    }

    private double readDouble(String flavourText, double defaultAnswer, double min, double max) {
        //Print a prompt for the user to enter input.
        System.out.println(flavourText + " Min:" + min + ", Max:" + max + "[" + defaultAnswer + "]");
        //Actually get input.
        String inputString = input.nextLine().toLowerCase();
        //If the user entered nothing, return the default input.
        if (inputString.length() == 0) {
            return defaultAnswer;
        }
        double output;
        try {
            output = Double.parseDouble(inputString);
        } catch (NumberFormatException e) {
            return readDouble("Number not in correct format", defaultAnswer, min, max);
        }
        if (output < min || output > max) {
            return readDouble("Number not within range", defaultAnswer, min, max);
        }
        return output;
    }

    private double readInt(String flavourText, double defaultAnswer, double min, double max) {
        //Print a prompt for the user to enter input.
        System.out.println(flavourText + " Min:" + min + ", Max:" + max + "[" + defaultAnswer + "]");
        //Actually get input.
        String inputString = input.nextLine().toLowerCase();
        //If the user entered nothing, return the default input.
        if (inputString.length() == 0) {
            return defaultAnswer;
        }
        double output;
        try {
            output = Integer.parseInt(inputString);
        } catch (NumberFormatException e) {
            return readDouble("Number not in correct format", defaultAnswer, min, max);
        }
        if (output < min || output > max) {
            return readDouble("Number not within range", defaultAnswer, min, max);
        }
        return output;
    }


    @Override
    public boolean mainMenu() {
        newPage("Home");
        System.out.println(" 1. Start Simulation");
        System.out.println(" 2. Stop Simulation");
        System.out.println(" 3. Add Intersection");
        System.out.println(" 4. Add Road");
        System.out.println(" 5. Add Spawn Point");
        System.out.println(" 6. Change light timings");
        System.out.println(" 7. Remove Intersection");
        System.out.println(" 8. Remove Road");
        System.out.println(" 9. Remove spawn point");
        System.out.println("10. Fill grid with intersections and roads");
        System.out.println("11. Exit");
        printCharTimes('=', 150, true);
        String[] options = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
        String choice = receiveStringInput("Enter an option:", options, false, false);
        switch (choice) {
            case "1":
                simulator.start();
                break;
            case "2":
                simulator.pause();
                break;
            case "3":
                addIntersection();
                break;
            case "4":
                addRoad();
                break;
            case "5":
                addSpawnPoint();
                break;
            case "6":
                changeTrafficLights();
                break;
            case "7":
                removeIntersection();
                break;
            case "8":
                removeRoad();
                break;
            case "9":
                removeSpawnPoint();
                break;
            case "10":
                fillIntersections();
                break;
            case "11":
                return false;
            default:
                break;
        }
        return true;
    }

    @Override
    public void addSpawnPoint() {

        //Get the intersection and direction from the user
        Intersection intersection = simulator.getIntersection(receiveCoordinateInput("Please enter the co-ordinates of the intersection you want " +
                "cars to spawn from in the form x,y", simulator.getGridSize()));
        CardinalDirection direction = (CardinalDirection.stringToDirection(receiveStringInput("Please enter the side you want cars to spawn from", new
                String[]{"north", "south", "east", "west"}, true, false)));
        int spawnDelay = DimensionManager.secondsToTicks(readDouble("Enter the delay between cars spawning, in seconds",1,0.1,10));
        CarSpawn spawn = simulator.createSpawnPoint(intersection, direction,spawnDelay);

        //If it was invalid keep prompting them till they get it right or quit
        while (spawn == null) {
            char go = receiveFixedString("Input was incorrect, do you want to continue? ", new String[]{"y", "n"}, true, 1).charAt(0);
            if (go == 'n') {
                return;
            }
            intersection = simulator.getIntersection(receiveCoordinateInput("Please enter the co-ordinates of the intersection you " +
                    "want cars to spawn from in the form x,y", simulator.getGridSize()));
            direction = CardinalDirection.stringToDirection(receiveStringInput("Please enter the side you want cars to spawn from",
                    new String[]{"north", "south", "east", "west"}, true, false));
            spawn = simulator.createSpawnPoint(intersection, direction,spawnDelay);

        }
        createSpawnPath(spawn);
    }

    @Override
    public void addIntersection() {

        int horizontalTime = DimensionManager.secondsToTicks(readDouble("Please enter the length of the horizontal light in seconds", 5, 1, 100));
        int verticalTime = DimensionManager.secondsToTicks(readDouble("Please enter the length of the vertical light in seconds", 5, 1, 100));

        Orientation orientation;
        do {
            orientation = Orientation.stringToOrientation(receiveStringInput("Enter the orientation of the starting lights", new
                    String[]{"Horizontal", "Vertical"}, true, false));
        } while (orientation == null);

        int[] coordinates = receiveCoordinateInput("Please enter the co-ordinates that you want to add the intersection to", simulator.getGridSize());

        simulator.addIntersection(coordinates[0], coordinates[1], verticalTime, horizontalTime, orientation);
    }

    @Override
    public void createSpawnPath(CarSpawn spawn) {
        boolean keepGoing = true;
        while (keepGoing) {
            //System.out.println(spawn.get);
        }

    }

    @Override
    public void addRoad() {
    }

    @Override
    public void changeTrafficLights() {

    }

    @Override
    public void removeSpawnPoint() {

    }

    @Override
    public void removeIntersection() {
        int[] coordinates = receiveCoordinateInput("Please enter the co-ordinates that you want to remove the intersection from", simulator
                .getGridSize());
        simulator.removeIntersection(coordinates[0], coordinates[1]);


    }

    @Override
    public void removeRoad() {

    }

    private void fillIntersections() {
        for (int i = 0; i < simulator.getGridSize()[0]; i++) {
            for (int j = 0; j < simulator.getGridSize()[1]; j++) {
                System.out.println("Adding intersection at " + i + "," + j);
                simulator.addIntersection(i, j, DimensionManager.secondsToTicks(10), DimensionManager.secondsToTicks(10), Orientation.HORIZONTAL);
            }
        }
        simulator.getMapGrid().fillRoads();
    }
}
