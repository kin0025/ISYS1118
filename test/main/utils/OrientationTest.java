package main.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrientationTest {
    private Orientation orientation;

    @Before
    public void setUp()  {
        orientation = new Orientation(Orientation.ENUM.VERTICAL);
    }

    @After
    public void tearDown()  {
    }

    @Test
    public void swapOrientation()  {
        orientation.swapOrientation();
        assertEquals("Swapping from V to H failed", Orientation.ENUM.HORIZONTAL,orientation.getCurrentOrientation());
   orientation.swapOrientation();
        assertEquals("Swapping from V to H failed", Orientation.ENUM.VERTICAL,orientation.getCurrentOrientation());
    }

    @Test
    public void getCurrentOrientation()  {
        assertEquals("Getting orientation failed", Orientation.ENUM.VERTICAL,orientation.getCurrentOrientation());

    }

}