package main.utils;

public class DimensionManager {
    // Program Constants
    private static final int numberOfTicksPerSecond = 30;
    private static final int numberOfMetersPerPixel = 1;
    public static final int numberOfLanesPerRoad = 4;

    //Dimensional Constants
    public static final int widthOfIntersectionPixels = 30;
    public static final int lengthOfRoadPixels = 100;
    public static final int widthOfRoadPixels = 30;
    public static final int lengthOfLanePixels = lengthOfRoadPixels;
    public static final int widthOfLanePixels = widthOfRoadPixels/numberOfLanesPerRoad;
    public static final int lengthOfCarPixels = 4;
    public static final int widthOfCarPixels = 2;

    //Other constants
    public static final int minimumFollowingDistancePixels = 5;
    public static final int amberLightTimeOn = 5;


    public static double metersToPixels(double meters) {
        return meters * numberOfMetersPerPixel;
    }

    public static double meterSecToPixelTick(double meters) {
        return meters * numberOfMetersPerPixel / numberOfTicksPerSecond;
    }

    public static double kmphToPixelTick(double kmph) {
        return (kmph / 3.6) * numberOfMetersPerPixel / numberOfTicksPerSecond;
    }

    public static double pixelsToMeters(double pixels) {
        return pixels / numberOfMetersPerPixel;
    }

    public static double ticksToSeconds(int ticks) {
        return (double) ticks / numberOfTicksPerSecond;
    }

    public static int secondsToTicks(double seconds) {
        return (int) seconds * numberOfTicksPerSecond;
    }
}
