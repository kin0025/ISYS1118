package main.gui;

import main.Simulator;
import main.entities.car.CarPath;
import main.entities.intersection.Intersection;
import main.entities.lane.CarSpawn;
import main.utils.DimensionManager;
import main.utils.Direction;
import main.utils.enums.Orientation;

import java.util.Scanner;
import java.util.StringTokenizer;

public class CommandLineController implements InputController {

    private Scanner input = new Scanner(System.in);

    private Simulator simulator;


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
        for (int i = 30; i != 0; i--) {
            System.out.printf("\n");
        }
        //Print the first line, made of equal signs.
        printCharTimes('=', pageWidth, true);
        //Set the three components of a menu screen.
        String leftText = "Traffic Light Simulator";
        String centreText = title;
        String rightText;

        if (!simulator.isPaused()) {
            rightText = "Running";
        } else {
            rightText = "Paused";
        }
        //Find the length of the menu areas.
        int left = leftText.length();
        int centre = centreText.length();
        int right = rightText.length();
        //Find the spacing between the three elements. Total width/2 - the size of left and half of the centre.
        int leftSpacing = pageWidth / 2 - left - (int) centre / 2;
        //Due to float to int conversion, subtract 1 more than the length.
        int rightSpacing = (pageWidth / 2) - 1 - right - (int) centre / 2;
        //Print the left
        System.out.print(leftText);
        //Print the spacing
        printCharTimes(' ', leftSpacing, false);
        //Print the centre
        System.out.print(centreText);
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

    private String receiveFixedString(String flavourText, String[] options, boolean printOptionText, int outputLength){
        //Receive input
        String inputString = input.nextLine().toLowerCase();
        //If it is too short, prompt for input again.
        while (inputString.length() == 0) {
            //Print the stuff again.
            System.out.println("Answer needs to be entered");
            System.out.print(flavourText + " ");
            if (printOptionText) {
                System.out.println(stringArrayToString(options));
            } else System.out.println();
            //Request input again.
            inputString = input.nextLine().toLowerCase();
        }
        //Ensure input is the same.
        String inputChar = inputString.toLowerCase();
        //If the input we are given is longer than the specified maximum output, make it shorter.
        if (inputChar.length() >= outputLength) {
            inputChar = inputChar.substring(0, outputLength);
        }
        boolean isCorrect = false;
        int i = 0;
        while (i < options.length && !isCorrect) {
            if (options[i].equals(inputChar)) {
                isCorrect = true;
            }
            i++;
        }
        if (isCorrect) {
            return (inputChar);
        } else {
            System.out.println("Input was not an option. Please try again.");
            inputChar = receiveStringInput(flavourText, options, true, outputLength);//If they didn't get it right the first time, supply options.
        }
        return (inputChar);
    }

    /**
     * Receives an input. Prints the flavourText, then requests input from the user. Will continue requesting input from the user until input
     * matches an entry in the array options. if printOptionText is false will not show the user what options are avaliable. Final number is max length
     * of returned string
     **/
    private String receiveStringInput(String flavourText, String[] options, boolean printOptionText, boolean caseSensitive) {
        //Print the flavour text.
        System.out.print(flavourText);

        //If we have enabled option printing, print the array.
        if (printOptionText) {
            System.out.println(stringArrayToString(options));
        } else System.out.println(); //Otherwise end the line.
        return receiveStringInput(options,caseSensitive);
    }



    /**
     * Receives an input. Prints the flavourText, then requests input from the user. Will continue requesting input from the user until input
     * matches an entry in the array options. if printOptionText is false will not show the user what options are avaliable. Final number is max length
     * of returned string
     **/
    private String receiveStringInput(String[] options, boolean caseSensitive) {
        String output = receiveStringInput();
        if(!caseSensitive){
            output = output.toLowerCase();
        }

        boolean isCorrect = false;
        int i = 0;
        while (i < options.length && !isCorrect) {
            if (options[i].equals(output)) {
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

    /**
     * Gets a non-null string input
     * @return the string input received
     */
    private String receiveStringInput() {
        //Receive input
        String inputString = input.nextLine().toLowerCase();
        //If it is too short, prompt for input again.
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
        System.out.print(flavourText);
        //If we have enabled option printing, print the array.
        System.out.println("(x < " + max[0] + 1 + " and y < " + max[1] + 1);
        //Receive input
        String inputString = input.nextLine().toLowerCase();
        //If it is too short, prompt for input again.
        while (inputString.length() == 0) {
            //Print the stuff again.
            System.out.println("An answer needs to be entered");
            System.out.print(flavourText);
            //Request input again.
            inputString = input.nextLine().toLowerCase();
        }

        StringTokenizer tokenizer = new StringTokenizer(inputString, ",");
        if (tokenizer.countTokens() != 2) {
            System.out.println("Two coordinates were not entered, please try again.");
            return receiveCoordinateInput(flavourText, max);
        }
        int i = 0;
        while (tokenizer.hasMoreTokens()) {
            try {
                output[i] = Integer.getInteger(tokenizer.nextToken());
            } catch (NumberFormatException e) {
                System.out.println("Numbers were not entered in parseable format, please try again");
                return receiveCoordinateInput(flavourText, max);
            }
            i++;
            if (output[i] > max[i]) {
                System.out.println("Coordinate was too large, please try again");
                return receiveCoordinateInput(flavourText, max);
            }
        }
        return output;
    }

    /**
     * Receives an input. Prints the flavourText, then requests input from the user. Will continue requesting input from the user until input
     * matches an entry in the array options or is empty. if it is empty returns the defaultAnswer. Final number is length of returned string
     **/
    private String receiveStringInput(String flavourText, String[] options, String defaultAnswer, int outputLength) {
        //Print a prompt for the user to enter input.
        System.out.println(flavourText + " " + stringArrayToString(options) + "[" + defaultAnswer + "]");
        //Ensure we don't try to access a negative array index.
        if (outputLength <= 0) {
            outputLength = 1;
        }
        //Actually get input.
        String inputString = input.nextLine().toLowerCase();
        //If the user entered nothing, return the default input.
        if (inputString.length() == 0) {
            return ("" + defaultAnswer);
        }
        String inputChar = inputString.toLowerCase();
        if (inputChar.length() >= outputLength) {
            inputChar = inputChar.substring(0, outputLength);
        }
        boolean isCorrect = false;
        int i = 0;
        while (i < options.length && !isCorrect) {
            if (options[i].equals(inputChar)) {
                isCorrect = true;
            }
            i++;
        }
        if (isCorrect) {
            return (inputChar);
        } else {
            System.out.println("Input was not an option. Please try again.");
            inputChar = receiveStringInput(flavourText, options, defaultAnswer, outputLength);//If they didn't get it right the first time, supply
            // options.
        }
        return inputChar;
    }

    private double readDouble(String flavourText, double defaultAnswer, double min, double max) {
        //Print a prompt for the user to enter input.
        System.out.println(flavourText + " Min:" + min + " Max:" + max + "[" + defaultAnswer + "]");
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
        System.out.println("10. Exit");
        printCharTimes('=', 150, true);
        String[] options = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        String choice = receiveStringInput("Enter an option:", options, false, 2);
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
        Direction direction = new Direction(Direction.stringToDirection(receiveStringInput("Please enter the side you want cars to spawn from", new
                String[]{"north", "south", "east", "west"}, true, 4)));

        CarSpawn spawn = simulator.createSpawnPoint(intersection, direction);

        //If it was invalid keep prompting them till they get it right or quit
        while (spawn == null) {
            char go = receiveStringInput("Please enter the side you want cars to spawn from", new String[]{"y", "n"}, true, 1).charAt(0);
            if (go == 'n') {
                break;
            }
            intersection = simulator.getIntersection(receiveCoordinateInput("Please enter the co-ordinates of the intersection you " +
                    "want cars to spawn from in the form x,y", simulator.getGridSize()));
            direction = new Direction(Direction.stringToDirection(receiveStringInput("Please enter the side you want cars to spawn from",
                    new String[]{"north", "south", "east", "west"}, true, 4)));
            spawn = simulator.createSpawnPoint(intersection, direction);

        }
        createSpawnPath(spawn);
    }

    @Override
    public void addIntersection() {

        int horizontalTime = DimensionManager.secondsToTicks(readDouble("Please enter the length of the horizontal light in seconds", 5, 1, 100));
        int verticalTime = DimensionManager.secondsToTicks(readDouble("Please enter the length of the vertical light in seconds", 5, 1, 100));

        Orientation orientation = null;
        do {
            orientation = Orientation.stringToOrientation(receiveStringInput("Enter the orientation of the starting lights", new
                    String[]{"Horizontal", "Vertical"}, true, 10));
        } while (orientation == null);

        int[] coordinates = receiveCoordinateInput("Please enter the co-ordinates that you want to add the intersection to", simulator.getGridSize());

        simulator.addIntersection(coordinates[0], coordinates[1], verticalTime, horizontalTime, orientation);
    }

    @Override
    public void createSpawnPath(CarSpawn spawn) {
        boolean keepGoing = true;
        while(keepGoing){
            System.out.println(spawn.get);
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

    }

    @Override
    public void removeRoad() {

    }
}
