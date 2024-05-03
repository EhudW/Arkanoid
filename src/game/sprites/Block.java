/**
 * @author [Ehud Wasserman] [ID *********]
 */

package game.sprites;

import java.util.List;
import java.util.ArrayList;

import java.awt.Color;
import biuoop.DrawSurface;
import game.animation.GameLevel;
import game.axes.Point;
import game.axes.Rectangle;
import game.axes.Velocity;
import game.listeners.HitListener;
import game.listeners.HitNotifier;

/**
 * The class for Block instance which implements Collidable, Sprite, HitNotifier interfaces.
 */
public class Block implements Sprite, Collidable, HitNotifier {

    /** The rectangle of the block in the axes. */
    private Rectangle rectangle;
    /** The color of the block to be drawn. */
    private Color color;
    /** The property of if to be drawn as '3D' or as flat block. */
    private boolean is3D;

    /** List of listeners to be notified when this Block 'is hit' [=this.hit()] by object. */
    private List<HitListener> hitListeners;

    /**
     * Constructor which gets rectangle and color,
     * and turn off the 3D property of the block.
     *
     * @param rectangle the rectangle of the block, the rectangle also indicated its place in the axes
     * @param color color of the block
     */
    public Block(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
        this.is3D = false;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * Method for set if the block will be drawn as 3D .
     *
     * @param state the new state of the 3D property
     */
    public void set3D(boolean state) {
        this.is3D = state;
    }


    /* Sprite interface methods */

    @Override
    public void addToGameLevel(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    @Override
    public void removeFromGameLevel(GameLevel g) {
        g.removeSprite(this);
        g.removeCollidable(this);
    }

    @Override
    public Rectangle getSpriteRectangle() {
        return rectangle;
    }

    @Override
    public void drawOn(DrawSurface surface) {
        if (this.is3D) {
            // draw 3d rectangle without chopping the edges (this is the 'false' param)
            ColorsUtil.draw3dRectangle(surface, this.rectangle, this.color, false);
        } else {
            //draw flat rectangle with black border (this is the 'true' param)
            ColorsUtil.drawFlatRectangle(surface, this.rectangle, this.color, true);
        }
    }

    @Override
    public void timePassed() {
        //nothing for now
    }

    /* Collidable interface methods */

    @Override
    public Rectangle getCollisionRectangle() {
        return rectangle;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // notify all listeners that a hit was occured
        this.notifyHit(hitter);

        if (collisionPoint == null) {
            return currentVelocity;
        }

        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        boolean isCollidedWithUpperLine = this.rectangle.getUpperLine().isIntersecting(collisionPoint);
        boolean isCollidedWithLowerLine = this.rectangle.getLowerLine().isIntersecting(collisionPoint);
        boolean isCollidedWithLeftLine = this.rectangle.getLeftLine().isIntersecting(collisionPoint);
        boolean isCollidedWithRightLine = this.rectangle.getRightLine().isIntersecting(collisionPoint);

        // change the dx / dy component if it is needed
        if (isCollidedWithUpperLine || isCollidedWithLowerLine) {
            dy = -dy;
        }
        if (isCollidedWithLeftLine || isCollidedWithRightLine) {
            dx = -dx;
        }

        // in order to avoid calculation problems* if a object say he hit the block, we believe it
        // *it isn't supppose to be, but if a object say it hit,
        // but we cannot find which line is Intersecting with we need to give it hope that it won't get stuck
        if (!isCollidedWithLeftLine && !isCollidedWithRightLine
                && !isCollidedWithLowerLine && !isCollidedWithUpperLine) {
            return currentVelocity.multiply(-1);
        }

        return new Velocity(dx, dy);
    }

    /**
     * Notify all the hit listeners about hit that occured.
     *
     * @param hitter the Ball that's doing the hitting.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }


    /* HitNotifier interface methods */

    @Override
    public void addHitListener(HitListener hl) {
        // notice that HitListener can register itself twice.
        if (hl != null) {
            this.hitListeners.add(hl);
        }
    }

    @Override
    public void removeHitListener(HitListener hl) {
        /* Will remove only maximum one instance of hl from the list(even if was registered twice),
           or 0 if not in the list. */
        if (this.hitListeners.contains(hl)) {
            this.hitListeners.remove(hl);
        }
    }

}