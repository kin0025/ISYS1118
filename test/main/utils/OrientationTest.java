package main.utils;

import com.sun.org.apache.xpath.internal.operations.Or;
import main.exceptions.IncorrectOrientationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrientationTest {
    Orientation orientation;

    @Before
    public void setUp() throws Exception {
        orientation = new Orientation('v');
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void swapOrientation() throws Exception {
        orientation.swapOrientation();
        assertEquals("Swapping from V to H failed",Orientation.horizontal,orientation.getCurrentOrientation());
   orientation.swapOrientation();
        assertEquals("Swapping from V to H failed",Orientation.vertical,orientation.getCurrentOrientation());
    }

    @Test
    public void getCurrentOrientation() throws Exception {
        assertEquals("Getting orientation failed",Orientation.vertical,orientation.getCurrentOrientation());

    }

    @Test (expected=IncorrectOrientationException.class)
    public void checkConstructor() throws IncorrectOrientationException {
        new Orientation('y');
    }


}