/*
 * ISYS1118: Assignment.
 * Group: Null
 * File created by: Alexander Kinross-Smith, s3603437
 */

package main.utils;

import javafx.geometry.Pos;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MovingBoxTest {
    BoundingBox parentBox;
    MovingBox movingBox;

    @Before
    public void setUp() throws Exception {
        parentBox = new BoundingBox(new Position(0, 0), 10, 10);
        movingBox = new MovingBox(new Position(0, 0), 2, 5, parentBox);
        movingBox.setAngle(0);
    }

    @Test
    public void isInsideParent() throws Exception {
        assertEquals(true, movingBox.isInsideParent());
    }

    @Test
    public void setParentBox() throws Exception {
        BoundingBox box = new BoundingBox(new Position(10, 10), 1, 1);
        movingBox.setParentBox(box);
        assertEquals(false, movingBox.isInsideParent());
    }

    @Test
    public void forceInsideParentBox() throws Exception {
        BoundingBox box = new BoundingBox(new Position(10, 10), 100, 100);
        movingBox.setParentBox(box);
        movingBox.forceInsideParentBox();
        assertEquals(true, movingBox.isInsideParent());

    }

    @Test
    public void getCorners() throws Exception {
        Position[] corners = movingBox.getCorners();
        assertEquals(true, corners[0].equals(new Position(1, 2.5)));
        assertEquals(true, corners[1].equals(new Position(-1, 2.5)));
        assertEquals(true, corners[2].equals(new Position(1, -2.5)));
        assertEquals(true, corners[3].equals(new Position(-1, -2.5)));
    }

    @Test
    public void getAngle() throws Exception {
        assertEquals(0, movingBox.getAngle(), 0.05);
    }

    @Test
    public void moveForward() throws Exception {
        movingBox.moveForward(1);
        assertEquals(true, movingBox.getCentre().equals(new Position(0, -1)));
    }

}