/*
 * ISYS1118: Assignment.
 * Group: Null
 * File created by: Alexander Kinross-Smith, s3603437
 */

package main.utils;

import javafx.geometry.Pos;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoundingBoxTest {
    BoundingBox boundingBox;
    Position position;
    @Before
    public void setUp() throws Exception {
        position = new Position(0, 0);
        boundingBox = new BoundingBox(position, 5, 3);

    }

    @Test
    public void getWidth() throws Exception {
        assertEquals("Width Failed", 5, boundingBox.getWidth(), 0.05);
    }

    @Test
    public void getHeight() throws Exception {
        assertEquals("Height Failed", 3, boundingBox.getHeight(), 0.05);

    }

    @Test
    public void getCentre() throws Exception {
        assertEquals("Get centre failed", position, boundingBox.getCentre());
    }

    @Test
    public void isInsideBoundingBox() throws Exception {
        assertEquals("Is not inside bounding box when it should be", true, boundingBox.isInsideBoundingBox(0, 0));
        assertEquals("Is not inside bounding box when it should be", true, boundingBox.isInsideBoundingBox(2.5, 1.5));
        assertEquals("Is not inside bounding box when it should be", true, boundingBox.isInsideBoundingBox(-2.5, -1.5));
    }

    @Test
    public void isNotInsideBoundingBox() throws Exception {
        assertEquals("Is inside when not expected", false, boundingBox.isInsideBoundingBox(100, 100));
        assertEquals("Is inside when not expected", false, boundingBox.isInsideBoundingBox(2.6, 1.5));
        assertEquals("Is inside when not expected", false, boundingBox.isInsideBoundingBox(-2.2, -1.7));
    }

}