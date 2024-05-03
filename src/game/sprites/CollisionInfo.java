/**
 * @author [Ehud Wasserman] [ID *********]
 */

package game.sprites;

import game.axes.Point;

/** Class that represents Collision and unite its data. */
public class CollisionInfo {

    /** The collision point. */
    private Point collisionPoint;
    /** The collisionObject from type Collidable. */
    private Collidable collisionObject;

    /**
     * Constructor to data of the collision.
     * If no collision was, so use 'null' instead of create new CollisionInfo instance.
     *
     * @param collisionObject -the Collidable instance which involved in the collision
     * @param collisionPoint -the collision point
     */
    public CollisionInfo(Collidable collisionObject, Point collisionPoint) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * Get the point at which the collision occurs.
     *
     * @return Point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * Get the the collidable object involved in the collision.
     *
     * @return Collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}