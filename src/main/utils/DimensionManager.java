package main.utils;

public class DimensionManager {
    private static int numberOfTicksPerSecond = 30;
    private static int numberOfMetersPerPixel = 1;

    public static double metersToPixels(double meters) {
        return meters * numberOfMetersPerPixel;
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
