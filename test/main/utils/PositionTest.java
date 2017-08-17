package main.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PositionTest {
    private Position position;

    @Before
    public void setUp() throws Exception {
        position = new Position(1,1);
    }

    @After
    public void tearDown() throws Exception {
    }


    /**
     * Tests for movement working properly with an , no bounds
     * @throws Exception An exception
     */
    @Test
    public void movePositionArray() throws Exception {
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
     * @throws Exception An exception
     */
    @Test
    public void movePosition() throws Exception {
        position.movePosition(2,1);
        assertEquals("Positive X movement individual, no bounds failed",3.0,position.getX(),0.01);
        assertEquals("Positive Y movement individual, no bounds failed",2.0,position.getY(),0.01);
        position.movePosition(-6,-4);
        assertEquals("Negative X movement individual, no bounds failed", -3,position.getX(),0.01);
        assertEquals("Negative Y movement individual, no bounds failed", -2,position.getY(),0.01);
    }
    /**
     * Tests for movement working properly with an , with bounds. Shouldn't error
     * @throws Exception An exception
     */
    @Test
    public void movePositionBoundsArrayCorrect() throws Exception {
        double[] moveBy = {2,1};
        double[] maxBounds = {10,10};
        double[] minBounds = {-10,-10};
        assertEquals("Positive movement  with bounds failed",true,position.movePosition(moveBy,maxBounds,minBounds));
        assertEquals("Positive X movement  with bounds wrong result",3.0,position.getX(),0.01);
        assertEquals("Positive Y movement  with bounds wrong result",2.0,position.getY(),0.01);
        moveBy[0] = -6;
        moveBy[1] = -4;
        assertEquals("Negative movement  with bounds failed",true,position.movePosition(moveBy,maxBounds,minBounds));
        assertEquals("Negative X movement  with bounds wrong result", -3,position.getX(),0.01);
        assertEquals("Negative Y movement  with bounds wrong result", -2,position.getY(),0.01);
    }

    /**
     * Tests for movement working properly with bounds. Shouldn't error
     * @throws Exception An exception
     */
    @Test
    public void movePositionBoundsCorrect() throws Exception {
        double[] maxBounds = {10,10};
        double[] minBounds = {-10,-10};
        assertEquals("Positive movement  with bounds failed",true,position.movePosition(2,1,maxBounds,minBounds));
        assertEquals("Positive X movement  with bounds wrong result",3.0,position.getX(),0.01);
        assertEquals("Positive Y movement  with bounds wrong result",2.0,position.getY(),0.01);
        assertEquals("Negative movement  with bounds failed",true,position.movePosition(-6,-4,maxBounds,minBounds));
        assertEquals("Negative X movement  with bounds wrong result", -3,position.getX(),0.01);
        assertEquals("Negative Y movement  with bounds wrong result", -2,position.getY(),0.01);
    }

    /**
     * Tests for movement working properly with an , with bounds. Shouldn't error
     * @throws Exception An exception
     */
    @Test
    public void movePositionBoundsArrayIncorrect() throws Exception {
        double[] moveBy = {20,20};
        double[] maxBounds = {10,10};
        double[] minBounds = {-10,-10};
        assertEquals("Positive movement  with bounds failed",false,position.movePosition(moveBy,maxBounds,minBounds));
        assertEquals("Positive X movement  with bounds wrong result",1.0,position.getX(),0.01);
        assertEquals("Positive Y movement  with bounds wrong result",1.0,position.getY(),0.01);
        moveBy[0] = -20;
        moveBy[1] = -20;
        assertEquals("Negative movement  with bounds failed",false,position.movePosition(moveBy,maxBounds,minBounds));
        assertEquals("Negative X movement  with bounds wrong result", 1,position.getX(),0.01);
        assertEquals("Negative Y movement  with bounds wrong result", 1,position.getY(),0.01);
    }

    /**
     * Tests for movement working properly with bounds. Shouldn't error
     * @throws Exception An exception
     */
    @Test
    public void movePositionBoundsIncorrect() throws Exception {
        double[] maxBounds = {10,10};
        double[] minBounds = {-10,-10};
        assertEquals("Positive movement  with bounds failed",false,position.movePosition(20,20,maxBounds,minBounds));
        assertEquals("Positive X movement  with bounds wrong result",1.0,position.getX(),0.01);
        assertEquals("Positive Y movement  with bounds wrong result",1.0,position.getY(),0.01);
        assertEquals("Negative movement  with bounds failed",false,position.movePosition(-20,-20,maxBounds,minBounds));
        assertEquals("Negative X movement  with bounds wrong result", -1,position.getX(),0.01);
        assertEquals("Negative Y movement  with bounds wrong result", -1,position.getY(),0.01);
    }

    @Test
    public void movePositionBounds() throws Exception {
    }

    @Test
    public void setPositionArray() throws Exception {
        double[] array = {10,10};
        position.setPosition(array);
        assertArrayEquals("Set position Array failed",array,position.getPosition(),0.01);
    }

    @Test
    public void setPosition() throws Exception {
        double[] array = {10,10};
        position.setPosition(10,10);
        assertArrayEquals("Set position standard failed",array,position.getPosition(),0.01);
    }

    @Test
    public void getX() throws Exception {
            assertEquals("Get X failed",1,position.getX(),0.01);

    }

    @Test
    public void getY() throws Exception {
        assertEquals("Get Y failed",1,position.getY(),0.01);

    }

    @Test
    public void getPosition() throws Exception {
        double[] expectedPosition = {1,1};
        assertArrayEquals("Get Position array failed",expectedPosition,position.getPosition(),0.01);

    }

    /**
     * Tests for set working properly with an , with bounds. Shouldn't error
     * @throws Exception An exception
     */
    @Test
    public void setPositionBoundsArrayCorrect() throws Exception {
        double[] setTo = {2,3};
        double[] maxBounds = {10,10};
        double[] minBounds = {-10,-10};
        assertEquals("Set with bounds failed",true,position.setPosition(setTo,maxBounds,minBounds));
        assertArrayEquals("Set position array with bounds failed",setTo,position.getPosition(),0.01);
    }

    /**
     * Tests for set working properly with bounds. Shouldn't error
     * @throws Exception An exception
     */
    @Test
    public void setPositionBoundsCorrect() throws Exception {
        double[] setTo = {2,3};
        double[] maxBounds = {10,10};
        double[] minBounds = {-10,-10};
        assertEquals("Set with bounds failed",true,position.setPosition(2,3,maxBounds,minBounds));
        assertArrayEquals("Set position array with bounds failed",setTo,position.getPosition(),0.01);
    }

    /**
     * Tests for setworking properly with an , with bounds. Shouldn't error
     * @throws Exception An exception
     */
    @Test
    public void setPositionBoundsArrayIncorrect() throws Exception {
        double[] setTo = {20,20};
        double[] maxBounds = {10,10};
        double[] minBounds = {-10,-10};
        assertEquals("Positive set with bounds failed",false,position.setPosition(setTo,maxBounds,minBounds));
        assertEquals("Positive X set with bounds wrong result",1.0,position.getX(),0.01);
        assertEquals("Positive Y set with bounds wrong result",1.0,position.getY(),0.01);
        setTo[0] = -20;
        setTo[1] = -20;
        assertEquals("Negative set with bounds failed",false,position.setPosition(setTo,maxBounds,minBounds));
        assertEquals("Negative X set with bounds wrong result", 1,position.getX(),0.01);
        assertEquals("Negative Y set with bounds wrong result", 1,position.getY(),0.01);
    }

    /**
     * Tests for setworking properly with bounds. Shouldn't error
     * @throws Exception An exception
     */
    @Test
    public void setPositionBoundsIncorrect() throws Exception {
        double[] maxBounds = {10,10};
        double[] minBounds = {-10,-10};
        assertEquals("Positive set with bounds failed",false,position.setPosition(20,20,maxBounds,minBounds));
        assertEquals("Positive X set with bounds wrong result",1.0,position.getX(),0.01);
        assertEquals("Positive Y set with bounds wrong result",1.0,position.getY(),0.01);
        assertEquals("Negative set with bounds failed",false,position.setPosition(-20,-20,maxBounds,minBounds));
        assertEquals("Negative X set with bounds wrong result", -1,position.getX(),0.01);
        assertEquals("Negative Y set with bounds wrong result", -1,position.getY(),0.01);
    }

    @Test
    public void getDifference() throws Exception {
        Position difference = new Position(10,15);
        assertEquals("Difference calculation returned incorrect result",16.6433,position.getDifference(difference),0.05);
    }

    @Test
    public void getDifferenceX() throws Exception {
        Position difference = new Position(10,15);
        assertEquals("Difference calculation returned incorrect result",9,position.getDifference(difference,0),0.05);

    }    
    
    @Test
    public void getDifferenceY() throws Exception {
        Position difference = new Position(10,15);
        assertEquals("Difference calculation returned incorrect result",14,position.getDifference(difference,0),0.05);

    }



}