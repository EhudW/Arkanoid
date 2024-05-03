/**
 * @author [Ehud Wasserman] [ID *********]
 */

package game.sprites;

import biuoop.DrawSurface;
import game.animation.GameLevel;
import game.axes.Rectangle;

/** The interface for object that draw on the screen. */
public interface Sprite {

    /** Rectangle to return / be compared with, if the Sprite instance want to "hide" its true place. */
    // using Rectangle.UNREACHABLE_RECT in order the methods of class Rectangle will recognize it
    Rectangle UNREACHABLE_RECT = Rectangle.UNREACHABLE_RECT;

    /**
     * Method for add the Sprite instance for GameLevel instance.
     * Should be called once and only once time for each sprite.
     *
     * @param g game level to be added to
     */
    void addToGameLevel(GameLevel g);

    /**
     * Method for remove the Sprite instance from GameLevel instance.
     *
     * @param g game level to be removed from
     */
    void removeFromGameLevel(GameLevel g);

    /**
     * Method for get the Sprite place(rectangle), in order not to draw over it.
     * It has different purpose than Collidable.getCollisionRectangle() .
     * For example: Ball can't go through Collidable but can go through Sprite .
     *              Paddle can't go through Sprite [and therefor can't go over Collidable].
     *
     * If the sprite is untouchable, meaning no one is disrupted BY the sprite,
     * AND able TO disrupte the sprite,
     * because the sprite is just drawn to the screen without others have dependence to it,
     * it should return Sprite.UNREACHABLE_RECT  .
     *
     * @return Rectangle where the sprite disruptes(TO) / should not be disrupted(BY)
     */
    Rectangle getSpriteRectangle();

    /**
     * Draw the sprite on the given DrawSurface.
     *
     * @param surface to draw on it.
     */
    void drawOn(DrawSurface surface);


    /**
     * Notify the sprite that time has passed.
     */
    void timePassed();
}