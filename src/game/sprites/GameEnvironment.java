/**
 * @author [Ehud Wasserman] [ID *********]
 */

package game.sprites;

import java.util.List;
import java.util.ArrayList;

import game.axes.Line;
import game.axes.Point;
import game.axes.Rectangle;

/**
 * The class that unites the Collidable objects in collection and have some methods on this collection.
 */
public class GameEnvironment {

    /** Set(no 'null' / repetition) of the collection of the Collidable objects. */
    private List<Collidable> collidables;

    /**
     * Constructor for the environment.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }

    /**
     * Add the given collidable to the environment,
     * 'null' / existing collidable in the environment won't be added.
     *
     * @param c collidable to add to the environment
     */
    public void addCollidable(Collidable c) {
        if (c != null && !this.collidables.contains(c)) {
            this.collidables.add(c);
        }
    }

    /**
     * Remove the given collidable to the environment,
     * 'null' / not existing collidable in the environment won't be removed.
     *
     * @param c collidable to remove from the environment
     */
    public void removeCollidable(Collidable c) {
        if (c != null && this.collidables.contains(c)) {
            this.collidables.remove(c);
        }
    }

    /**
     * Returns the information about the closest collision that is going to occur.
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, returns null.
     *
     * @param trajectory line to check if cross any Collidable. The start-end order important.
     * @return CollisionInfo of the first Collision, or null if there is no.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        // call the other overloading without collidable to not check for collision.
        return getClosestCollision(trajectory, null);
    }

    /**
     * Returns the information about the closest collision that is going to occur,
     * except for the sent Collidable sent via the function call.
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, returns null.
     *
     * @param trajectory line to check if cross any Collidable. The start-end order important.
     * @param exceptFor Collidable not to include in the Collision check.
     * @return CollisionInfo of the first Collision, or null if there is no.
     */
    public CollisionInfo getClosestCollision(Line trajectory, Collidable exceptFor) {
        if (trajectory == null || trajectory.start() == null || trajectory.end() == null) {
            return null;
        }

        Point start = trajectory.start();
        CollisionInfo closestCollision = null;

        // Make a copy list of the collidables before iterating over them.
        List<Collidable> copiedList = new ArrayList<Collidable>(this.collidables);

        // for each collidable that is not the exceptFor, check if it has closer collision on the trajectory
        for (Collidable object : copiedList) {
            // check the object is not 'exceptFor'
            if (object == exceptFor) {
                continue;
            }
            Rectangle rectangleOfCurrObject = object.getCollisionRectangle();
            // check the object have real rectangle
            if (rectangleOfCurrObject == null) {
                continue;
            }
            // get the closet point for the intersection of the trajectory and the object rectangle
            Point currentCollisionPoint = Point.getClosetPoint(rectangleOfCurrObject.intersectionPoints(trajectory),
                                                                start);
            // check that there is indeed some intersection point
            if (currentCollisionPoint == null) {
                continue;
            }
            // if right now there is no closestCollision so for sure the currentCollisionPoint is the closest for now
            if (closestCollision == null) {
                closestCollision = new CollisionInfo(object, currentCollisionPoint);
                continue;
            }
            // or if the current collision point is closer, update the closest Collision with the closest point & object
            Point closestPoint = closestCollision.collisionPoint();
            if (Point.getClosetPoint(start, closestPoint, currentCollisionPoint).equals(currentCollisionPoint)) {
                closestCollision = new CollisionInfo(object, currentCollisionPoint);
            }
        }

        return closestCollision;
    }
}