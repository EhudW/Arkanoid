/**
 * @author [Ehud Wasserman] [ID *********]
 */

package game.sprites;

import game.axes.Point;
import game.axes.Rectangle;
import game.axes.Velocity;

/** The interface for collidable object in the axes. Collidable shoulde extends Sprite interface. */
public interface Collidable {

    /**
     * Return the "collision shape" of the object.
     * It has different purpose than Sprite.getSpriteRectangle() .
     *
     * @return Rectangle of the Collidable. OR null if not collidable right now.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the Collidable object that we collided with it at collisionPoint with
     * a given velocity.
     *
     * @param hitter the Ball that's doing the hitting.
     * @param collisionPoint the point we collided with
     * @param currentVelocity the velocity to the collision
     * @return new velocity expected after the hit (based on the force the object inflicted on us).
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}