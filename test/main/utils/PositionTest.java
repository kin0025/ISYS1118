package main.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Testing for the Position Class
 */
public class PositionTest {
    private Position position;

    /**
     * Sets up.
     *
     */
    @Before
    public void setUp()  {
        position = new Position(1,1);
    }

    /**
     * Tear down.
     *
     */
    @After
    public void tearDown()  {
    }


    /**
     * Tests for movement working properly with an , no bounds
     *
     */
    @Test
    public void movePositionArray()  {
        double[] moveBy = {2,1};
        position.movePosition(moveBy);
        assertEquals("Positive X movement by , no bounds failed",3.0,position.getX(),0.01);
        assertEquals("Positive Y movement by , no bounds failed",2.0,position.getY(),0.01);
        moveBy[0] = -6;
        moveBy[1] = -4;
        position.movePosition(moveBy);
        assertEquals("Negative X movement by , no bounds failed", -3,position.getX(),0.01);
        assertEquals("Negative Y movement by , no bounds failed", -2,position.getY(),0.01);
    }

    /**
     * Tests for movement working properly with individual variables, no bounds
     *
     */
    @Test
    public void movePosition()  {
        position.movePosition(2,1);
        assertEquals("Positive X movement individual, no bounds failed",3.0,position.getX(),0.01);
        assertEquals("Positive Y movement individual, no bounds failed",2.0,position.getY(),0.01);
        position.movePosition(-6,-4);
        assertEquals("Negative X movement individual, no bounds failed", -3,position.getX(),0.01);
        assertEquals("Negative Y movement individual, no bounds failed", -2,position.getY(),0.01);
    }

    /**
     * Tests for movement working properly with an , with bounds. Shouldn't error
     *
     */
    @Test
    public void movePositionBoundsArrayCorrect()  {
        double[] moveBy = {2,1};
        double[] maxBounds = {10,10};
        double[] minBounds = {-10,-10};
        assertEquals("Positive movement  with bounds failed",true,position.movePosition(moveBy,minBounds,maxBounds));
        assertEquals("Positive X movement  with bounds wrong result",3.0,position.getX(),0.01);
        assertEquals("Positive Y movement  with bounds wrong result",2.0,position.getY(),0.01);
        moveBy[0] = -6;
        moveBy[1] = -4;
        assertEquals("Negative movement  with bounds failed",true,position.movePosition(moveBy,minBounds,maxBounds));
        assertEquals("Negative X movement  with bounds wrong result", -3,position.getX(),0.01);
        assertEquals("Negative Y movement  with bounds wrong result", -2,position.getY(),0.01);
    }

    /**
     * Tests for movement working properly with bounds. Shouldn't error
     *
     */
    @Test
    public void movePositionBoundsCorrect()  {
        double[] maxBounds = {10,10};
        double[] minBounds = {-10,-10};
        assertEquals("Positive movement  with bounds failed",true,position.movePosition(2,1,minBounds,maxBounds));
        assertEquals("Positive X movement  with bounds wrong result",3.0,position.getX(),0.01);
        assertEquals("Positive Y movement  with bounds wrong result",2.0,position.getY(),0.01);
        assertEquals("Negative movement  with bounds failed",true,position.movePosition(-6,-4,minBounds,maxBounds));
        assertEquals("Negative X movement  with bounds wrong result", -3,position.getX(),0.01);
        assertEquals("Negative Y movement  with bounds wrong result", -2,position.getY(),0.01);
    }

    /**
     * Tests for movement working properly with an , with bounds. Shouldn't error
     *
     */
    @Test
    public void movePositionBoundsArrayIncorrect()  {
        double[] moveBy = {20,20};
        double[] maxBounds = {10,10};
        double[] minBounds = {-10,-10};
        assertEquals("Positive movement  with bounds failed",false,position.movePosition(moveBy,minBounds,maxBounds));
        assertEquals("Positive X movement  with bounds wrong result",1.0,position.getX(),0.01);
        assertEquals("Positive Y movement  with bounds wrong result",1.0,position.getY(),0.01);
        moveBy[0] = -20;
        moveBy[1] = -20;
        assertEquals("Negative movement  with bounds failed",false,position.movePosition(moveBy,minBounds,maxBounds));
        assertEquals("Negative X movement  with bounds wrong result", 1,position.getX(),0.01);
        assertEquals("Negative Y movement  with bounds wrong result", 1,position.getY(),0.01);
    }

    /**
     * Tests for movement working properly with bounds. Shouldn't error
     *
     */
    @Test
    public void movePositionBoundsIncorrect()  {
        double[] maxBounds = {10,10};
        double[] minBounds = {-10,-10};
        assertEquals("Positive movement  with bounds failed",false,position.movePosition(20,20,maxBounds,minBounds));
        assertEquals("Positive X movement  with bounds wrong result",1.0,position.getX(),0.01);
        assertEquals("Positive Y movement  with bounds wrong result",1.0,position.getY(),0.01);
        assertEquals("Negative movement  with bounds failed",false,position.movePosition(-20,-20,maxBounds,minBounds));
        assertEquals("Negative X movement  with bounds wrong result", 1,position.getX(),0.01);
        assertEquals("Negative Y movement  with bounds wrong result", 1,position.getY(),0.01);
    }

    /**
     * Sets position array.
     *
     */
    @Test
    public void setPositionArray()  {
        double[] array = {10,10};
        position.setPosition(array);
        assertArrayEquals("Set position Array failed",array,position.getPosition(),0.01);
    }

    /**
     * Sets position.
     *
     */
    @Test
    public void setPosition()  {
        double[] array = {10,10};
        position.setPosition(10,10);
        assertArrayEquals("Set position standard failed",array,position.getPosition(),0.01);
    }

    /**
     * Gets x.
     *
     */
    @Test
    public void getX()  {
            assertEquals("Get X failed",1,position.getX(),0.01);

    }

    /**
     * Gets y.
     *
     */
    @Test
    public void getY()  {
        assertEquals("Get Y failed",1,position.getY(),0.01);

    }

    /**
     * Gets position.
     *
     */
    @Test
    public void getPosition()  {
        double[] expectedPosition = {1,1};
        assertArrayEquals("Get Position array failed",expectedPosition,position.getPosition(),0.01);

    }

    /**
     * Tests for set working properly with an , with bounds. Shouldn't error
     *
     */
    @Test
    public void setPositionBoundsArrayCorrect()  {
        double[] setTo = {2,3};
        double[] maxBounds = {10,10};
        double[] minBounds = {-10,-10};
        assertEquals("Set with bounds failed",true,position.setPosition(setTo,minBounds,maxBounds));
        assertArrayEquals("Set position array with bounds failed",setTo,position.getPosition(),0.01);
    }

    /**
     * Tests for set working properly with bounds. Shouldn't error
     *
     */
    @Test
    public void setPositionBoundsCorrect()  {
        double[] setTo = {2,3};
        double[] maxBounds = {10,10};
        double[] minBounds = {-10,-10};
        assertEquals("Set with bounds failed",true,position.setPosition(2,3,minBounds,maxBounds));
        assertArrayEquals("Set position array with bounds failed",setTo,position.getPosition(),0.01);
    }

    /**
     * Tests for setworking properly with an , with bounds. Shouldn't error
     *
     */
    @Test
    public void setPositionBoundsArrayIncorrect()  {
        double[] setTo = {20,20};
        double[] maxBounds = {10,10};
        double[] minBounds = {-10,-10};
        assertEquals("Positive set with bounds failed",false,position.setPosition(setTo,minBounds,maxBounds));
        assertEquals("Positive X set with bounds wrong result",1.0,position.getX(),0.01);
        assertEquals("Positive Y set with bounds wrong result",1.0,position.getY(),0.01);
        setTo[0] = -20;
        setTo[1] = -20;
        assertEquals("Negative set with bounds failed",false,position.setPosition(setTo,minBounds,maxBounds));
        assertEquals("Negative X set with bounds wrong result", 1,position.getX(),0.01);
        assertEquals("Negative Y set with bounds wrong result", 1,position.getY(),0.01);
    }

    /**
     * Tests for setworking properly with bounds. Shouldn't error
     *
     */
    @Test
    public void setPositionBoundsIncorrect()  {
        double[] maxBounds = {10,10};
        double[] minBounds = {-10,-10};
        assertEquals("Positive set with bounds failed",false,position.setPosition(20,20,maxBounds,minBounds));
        assertEquals("Positive X set with bounds wrong result",1.0,position.getX(),0.01);
        assertEquals("Positive Y set with bounds wrong result",1.0,position.getY(),0.01);
        assertEquals("Negative set with bounds failed",false,position.setPosition(-20,-20,maxBounds,minBounds));
        assertEquals("Negative X set with bounds wrong result", 1,position.getX(),0.01);
        assertEquals("Negative Y set with bounds wrong result", 1,position.getY(),0.01);
    }

    /**
     * Tests the difference in both axis.
     *
     */
    @Test
    public void getDifference()  {
        Position difference = new Position(10,15);
        assertEquals("Difference calculation returned incorrect result",16.6433,position.getDifference(difference),0.05);
    }

    /**
     * Tests the difference in the x axis.
     *
     */
    @Test
    public void getDifferenceX()  {
        BoundingBox difference = new Position(10,15);
        assertEquals("Difference calculation returned incorrect result",9,position.getDifference(difference, Position.DIMENSION.X),0.05);

    }

    /**
     * Tests for a difference in the Y axis
     *
     */
    @Test
    public void getDifferenceY()  {
        BoundingBox difference = new Position(10,15);
        assertEquals("Difference calculation returned incorrect result",14,position.getDifference(difference, Position.DIMENSION.Y),0.05);

    }



}