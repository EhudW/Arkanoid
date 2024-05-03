/**
 * @author [Ehud Wasserman] [ID *********]
 */

package game.sprites;

import java.util.List;
import java.util.ArrayList;
import biuoop.DrawSurface;
import game.axes.Rectangle;
import game.axes.Velocity;

/**
 * The class represents a SpriteCollection for gui, set that instances within cannot repeat.
 * It also have some methods to apply on the collection.
 */
public class SpriteCollection {

    /** List that is the 'collection' of the sprite. */
    private List<Sprite> spritesList;

    /**
     * Constructor for the collection.
     */
    public SpriteCollection() {
        this.spritesList = new ArrayList<Sprite>();
    }

    /**
     * Add sprite to the collection, if it isn't null.
     * Each sprite can be only once in the collection.
     *
     * @param s Sprite to add
     */
    public void addSprite(Sprite s) {
        // avoid null Sprite or repeated sprite
        if (s != null && !this.spritesList.contains(s)) {
            this.spritesList.add(s);
        }
    }

    /**
     * Remove sprite from the collection, if it isn't null, and within the collection.
     *
     * @param s Sprite to remove from the collection
     */
    public void removeSprite(Sprite s) {
        // avoid null Sprite or not existed sprite
        if (s != null && this.spritesList.contains(s)) {
            this.spritesList.remove(s);
        }
    }

    /**
     * Call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        // Make a copy list of the sprites before iterating over them.
        List<Sprite> sprites = new ArrayList<Sprite>(this.spritesList);
        for (Sprite s : sprites) {
            s.timePassed();
        }
    }

    /**
     * Call drawOn(surface) on all sprites.
     *
     * @param surface to draw the sprite on it.
     */
    public void drawAllOn(DrawSurface surface) {
        // Make a copy list of the sprites before iterating over them.
        List<Sprite> sprites = new ArrayList<Sprite>(this.spritesList);
        for (Sprite s : sprites) {
            s.drawOn(surface);
        }
    }

    /**
     * Get the closest velocity - to a given velocity and given sprite -
     * that won't create intersection with other sprite.
     * (even if there will be no congruence if the velocity will be applied)
     *
     * Notice that each Sprite implements how it want the rectangle that crossing it will disrupted BY,
     * and may not care that something will get across it. (even with congruence afterwards)
     * The opposite is also true - only if the given sprite 'rectangle' will disrupts TO other sprite,
     * than the method will check the issue.[if givenSprite.getSpriteRectangle() != null]
     *
     * @param currVelocity the velocity that given sprite wants to move without cross other sprites
     * @param givenSprite the given sprite that want to move without cross other sprites
     * @return Velociy the closest velocity to the given velocity that won't cross any sprite
     */
    public Velocity getClosestVelocity(Velocity currVelocity, Sprite givenSprite) {
        // check that there velocity, givenSprite,
        // and that the givenSprite has place[rectangle] that may have problem to cross other sprites
        if (currVelocity == null || givenSprite == null || givenSprite.getSpriteRectangle() == null) {
            return currVelocity;
        }
        // collect the rectangles of the sprites in the collection [that are not the given, and have 'real' rectangle]
        ArrayList<Rectangle> rectangleList = new ArrayList<Rectangle>();
        for (Sprite s : this.spritesList) {
            if (s != givenSprite && s.getSpriteRectangle() != null) {
                rectangleList.add(s.getSpriteRectangle());
            }
        }

        Rectangle givenSpriteRect = givenSprite.getSpriteRectangle();
        // get the closest crossed rectangle (to the given sprite)
        Rectangle closestSpriteRect = givenSpriteRect.getClosestRectangle(rectangleList, currVelocity);
        // return the velocity (from the given sprite) to the closest rectangle
        return givenSpriteRect.velocityToCollision(closestSpriteRect, currVelocity);
    }
}