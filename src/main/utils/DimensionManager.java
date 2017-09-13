package main.utils;

public class DimensionManager {
    // Program Constants
    private static final int numberOfTicksPerSecond = 30;
    private static final int numberOfMetersPerPixel = 1;
    public static final int numberOfLanesPerRoad = 4;

    //Dimensional Constants
    public static final int widthOfIntersectionPixels = 40;
    public static final int lengthOfRoadPixels = 150;
    public static final int widthOfRoadPixels = 40;
    public static final int lengthOfLanePixels = lengthOfRoadPixels;
    public static final int widthOfLanePixels = widthOfRoadPixels/numberOfLanesPerRoad;
    public static final int lengthOfCarPixels = 8;
    public static final int widthOfCarPixels = 5;
    public static final int sizeOfLightPixels = 10;

    //Other constants
    public static final int minimumFollowingDistancePixels = 5;
    public static final int amberLightTimeOn = secondsToTicks(2);


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
