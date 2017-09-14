/*
 * ISYS1118: Assignment.
 * Group: Null
 * File created by: Alexander Kinross-Smith, s3603437
 */

package main.utils;

import main.utils.enums.CollisionStatus;

public class MovingBox extends BoundingBox {
    private BoundingBox parentBox;
    private int angle;

    public MovingBox(double xMin, double yMin, double xMax, double yMax, BoundingBox parentBox) throws NumberFormatException {
        super(xMin, yMin, xMax, yMax);
        this.parentBox = parentBox;
    }

    public MovingBox(Position centre, double xWidth, double yWidth, BoundingBox parentBox) {
        super(centre, xWidth, yWidth);
        this.parentBox = parentBox;
    }

    public void setParentBox(BoundingBox parentBox) {
        this.parentBox = parentBox;
    }

    public void forceInsideParentBox(){
if(getCollisionStatus() == CollisionStatus.ENCLOSED){
    return;
}else{
    //TODO IMPLEMENT
     //this.getCentre().setPosition(x,y);
}
    }

    public CollisionStatus getCollisionStatus(){
        //TODO IMPLEMENT
        return CollisionStatus.OUTSIDE;
    }

    public void setAngle(int angle){
        this.angle = angle;
    }


}
