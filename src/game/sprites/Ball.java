/**
 * @author [Ehud Wasserman] [ID *********]
 */
package game.sprites;

import java.awt.Color;
import biuoop.DrawSurface;
import game.animation.GameLevel;
import game.axes.Line;
import game.axes.MathUtil;
import game.axes.Point;
import game.axes.Rectangle;
import game.axes.Velocity;

/**
 * The class represents a ball in coordinate system(developed\suitable for cp gui). implements Sprite interface.
 */
public class Ball implements Sprite {

    /** The ball center. */
    private Point center;
    /** The ball radius==size. */
    private int radius;
    /** The ball color. */
    private Color color;
    /** The ball velocity. */
    private Velocity velocity;
    /** The proprety that set if the ball drawn as '3D'. */
    private boolean is3D;
    /** The associated game with this ball. */
    private GameLevel game = null;

    /**
     * Constructor which create ball instance.
     * Default value for '3d' = false
     *
     * @param center ,the point which is the center of the ball.
     * @param radius ,the size of the ball-,must be > 0.
     * @param color ,the color of the ball.
     * @param velocity ,the velocity of the ball.
     */
    public Ball(Point center, int radius, Color color, Velocity velocity) {
        if (center == null || radius <= 0 || color == null || velocity == null) {
            throw new RuntimeException("A ball must have center & positive size & color & velocity");
        }
        this.center = center;
        this.radius = radius;
        this.velocity = velocity;
        this.color = color;
        // default value
        this.is3D = false;
    }

    /**
     * Constructor which create ball with default velocity[zero], and default proprety 'is3D'[false].
     *
     * @param center ,the point which is the center of the ball.
     * @param radius ,the size of the ball-,must be > 0.
     * @param color ,the color of the ball.
     */
    public Ball(Point center, int radius, Color color) {
        this(center, radius, color, Velocity.ZERO);
    }

    /**
     * Constructor which create ball with default velocity[zero], and default proprety 'is3D'[false].
     *
     * @param x ,the x value of the center of the ball.
     * @param y ,the y value of the center of the ball.
     * @param radius ,the size of the ball-,must be > 0.
     * @param color ,the color of the ball.
     */
    public Ball(int x, int y, int radius, Color color) {
        this(new Point(x, y), radius, color);
    }

    /* Getters */

    /**
     * Get the x value of the center of the ball.
     *
     * @return x value of the center of the ball.
     */
    public double getX() {
        return center.getX();
    }

    /**
     * Get the y value of the center of the ball.
     *
     * @return y value of the center of the ball.
     */
    public double getY() {
        return center.getY();
    }

    /**
     * Get the center point of the ball.
     *
     * @return Point center of the ball
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Get the size==radius of the ball.
     *
     * @return the radius of the ball.
     */
    public int getSize() {
        return radius;
    }

    /**
     * Get the color of the ball[immutable object].
     *
     * @return the color of the ball.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Get the velocity of the ball.
     * It is immutable object, but ball velocity can be switched by setVelocity().
     *
     * @return velocity of the ball.
     */
    public Velocity getVelocity() {
        return velocity;
    }

    /* Setters */

    /**
     * Set the velocity of the ball.
     * The velocity is immutable object, so changing the ball velocity is only by setVelocity().
     *
     * @param v new velocity of the ball.
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
        if (v == null) {
            this.velocity = Velocity.ZERO;
        }
    }

    /**
     * Set the velocity of the ball.
     *
     * @param dx new dx-component velocity of the ball.
     * @param dy new dy-component velocity of the ball.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * Set if the ball will be drawn as '3D'.
     *
     * @param state to set the '3D' proprety to that state
     */
    public void set3D(boolean state) {
        this.is3D = state;
    }


    /* Sprite interface methods */

    @Override
    public void addToGameLevel(GameLevel g) {
        this.game = g;
        g.addSprite(this);
    }

    @Override
    public void removeFromGameLevel(GameLevel g) {
        g.removeSprite(this);
    }

    @Override
    public Rectangle getSpriteRectangle() {
        // The ball needs MathUtil.MEDIUM_EPSILON distance from it center for each direction in order it want get stuck
        Point upperLeftPt = new Point(this.getX() - MathUtil.MEDIUM_EPSILON, this.getY() - MathUtil.MEDIUM_EPSILON);
        return new Rectangle(upperLeftPt, 2 * MathUtil.MEDIUM_EPSILON, 2 * MathUtil.MEDIUM_EPSILON);
    }

    @Override
    public void drawOn(DrawSurface surface) {
        ColorsUtil.drawBall(surface, this.getX(), this.getY(), this.getSize(), this.color, this.is3D);
    }

    @Override
    public void timePassed() {
        // go to the next point according to the velcoity and to the associated game
        this.moveOneStep();
    }

    /**
     * Move the ball to its new center according to the velocity,
     * and according to the GameLevel (if) associated with the ball.
     */
    public void moveOneStep() {
        // if there is no game associated just move the ball
        if (this.game == null) {
            Point nextCenter = this.velocity.applyToPoint(this.center);
            this.center = nextCenter;
            return;
        }

        // if there is game associated with this ball, get the closest collision  in the ball trajectory
        Line trajectory = new Line(this.center, this.velocity.applyToPoint(this.center));
        CollisionInfo closestCollision = this.game.getEnvironment().getClosestCollision(trajectory);
        // if there is no collision just move the ball
        if (closestCollision == null) {
            Point nextCenter = this.velocity.applyToPoint(this.center);
            this.center = nextCenter;
            return;
        }
        // if there is a collision go to that point, and keep small distance
        Point collisionPoint = closestCollision.collisionPoint();
        Velocity velcoityToCollision = new Velocity(this.center, collisionPoint);
        this.center = velcoityToCollision.lower().applyToPoint(this.center);
        // and update the velocity after the collision
        this.velocity = closestCollision.collisionObject().hit(this, collisionPoint, this.velocity);
    }

    /**
     * Move the ball to its new center according to the velocity,
     * and there is limit where the ball can go,
     * but it indeed can move inside the 'frame' if it started outside.
     * [Without connection to the GameLevel / gui.GameEnvironment / gui.SpriteCollection]
     *
     * @param minWidth min width value that the ball can't go outside from.
     * @param minHeight min height value that the ball can't go outside from.
     * @param maxWidth max width value that the ball can't go outside from.
     * @param maxHeight max height value that the ball can't go outside from.
     */
    public void moveOneStep(int minWidth,  int minHeight, int maxWidth, int maxHeight) {
        // get the next center if there was no limits
        Point nextCenter = this.velocity.applyToPoint(this.center);
        // check if it will be outside the left/right limit AND if has no plan[check velocity] to go inside the frame
        boolean isGoOutsideRight = nextCenter.getX() +  radius > maxWidth && velocity.getDx() >= 0;
        boolean isGoOutsideLeft = nextCenter.getX() - radius < minWidth && velocity.getDx() <= 0;
        //if there is a problem, force the ball to stay in the frame, and change its dx-component for the NEXT time
        if (isGoOutsideRight) {
            velocity = new Velocity(-1 * velocity.getDx(), velocity.getDy());
            nextCenter = new Point(maxWidth - (radius), nextCenter.getY());
        }
        if (isGoOutsideLeft) {
            velocity = new Velocity(-1 * velocity.getDx(), velocity.getDy());
            nextCenter = new Point(minWidth + (radius), nextCenter.getY());
        }
        // check if it will be outside the up/down limit AND if has no plan[check velocity] to go inside the frame
        boolean isGoOutsideUp = nextCenter.getY() - radius < minHeight && velocity.getDy() <= 0;
        boolean isGoOutsideDown = nextCenter.getY() + radius > maxHeight && velocity.getDy() >= 0;
        //if there is a problem, force the ball to stay in the frame, and change its dy-component for the NEXT time
        if (isGoOutsideUp) {
            velocity = new Velocity(velocity.getDx(), -1 * velocity.getDy());
            nextCenter = new Point(nextCenter.getX(), minHeight + (radius));
        }
        if (isGoOutsideDown) {
            velocity = new Velocity(velocity.getDx(), -1 * velocity.getDy());
            nextCenter = new Point(nextCenter.getX(), maxHeight - (radius));
        }
        this.center = nextCenter;
    }
}