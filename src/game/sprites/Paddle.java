/**
 * @author [Ehud Wasserman] [ID *********]
 */

package game.sprites;

import java.awt.Color;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.animation.GameLevel;
import game.axes.Line;
import game.axes.Point;
import game.axes.Rectangle;
import game.axes.Velocity;

/**
 * Class for paddle of arkanoid game. implements Sprite + Collidable interfaces.
 */
public class Paddle implements Sprite, Collidable {

    /** The paddle-shape rectangle. */
    private Rectangle rectangle;
    /** The color of the paddle. */
    private Color color;
    /** The proprety if to draw the paddle as '3D'. */
    private boolean is3D;
    /** The associated game with this paddle. */
    private GameLevel game;
    /** The associated keyboard-senseor that controls these paddle. */
    private KeyboardSensor keyboard;
    /** Speed size of the paddle when pressing arrow key. */
    private double speedSizeByPress;
    /** Rectangle that the paddle musnt leave / intersect. */
    private Rectangle movementRectangle;
    /** whether the paddle can 'fly' or only move left-right. */
    private boolean alsoUpDown;

    /**
     * Constructor which give some defaults values.
     *
     * @param rectangle the paddle rectangle (paddle shape = place + width + height)
     * @param color to the drawing of the paddle
     * @param keyboard sensor to control the paddle when timePassed() called
     * @param speed the speed that the paddle move when arrow keys are pressed
     */
    public Paddle(Rectangle rectangle, Color color, KeyboardSensor keyboard, double speed) {
        this.rectangle = rectangle;
        this.color = color;
        this.is3D = false;
        this.keyboard = keyboard;
        this.speedSizeByPress = speed;
        this.alsoUpDown = false;
    }

    /**
     * Method for set if the paddle will be drawn as 3D .
     *
     * @param state the new state of the 3D property
     */
    public void set3D(boolean state) {
        this.is3D = state;
    }

    /**
     * Method for move the paddle within a game,
     * without intersecting sprites[that don't want to be intersected],
     * and withoud intersecting the gui borders(according to the constructor that used).
     *
     * @param v1 velocity to move the paddle
     */
    public void move(Velocity v1) {
        if (v1 == null) {
            return;
        }
        //Get the velocity to the closest sprite
        Velocity vToSprite = (game != null) ? game.getSpirteCollection().getClosestVelocity(v1, this) : Velocity.ZERO;
        //Get the velocity to the gui border (if constructor make it as a limit)
        boolean isThereBordersLimit = movementRectangle != null;
        Velocity  vToBorders = isThereBordersLimit ? this.rectangle.velocityToCollision(movementRectangle, v1) : null;

        //Get the min velocity and apply it on the paddle(on its rectangle)
        Velocity minV = Velocity.min(v1, vToSprite);
        minV = Velocity.min(minV, vToBorders);
        //apply lower velocity to keep distance from the sprites/borders
        this.rectangle =  minV.lower().applyToRectangle(this.rectangle);
    }

    /**
     * Method for move the paddle right.
     */
    public void moveRight() {
        move(Velocity.fromAngleAndSpeed(Velocity.RIGHT_ANGLE, speedSizeByPress));
    }

    /**
     * Method for move the paddle left.
     */
    public void moveLeft() {
        move(Velocity.fromAngleAndSpeed(Velocity.LEFT_ANGLE, speedSizeByPress));
    }

    /* Sprite interface methods */

    @Override
    public void addToGameLevel(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
        this.game = g;
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
        //check if the paddle is not transparent
        if (color.getTransparency() != Color.OPAQUE) {
            return;
        }
        //check if the paddle is flat
        if (!this.is3D) {
            ColorsUtil.drawFlatRectangle(surface, this.rectangle, this.color, true);
            return;
        }

        double startX = this.rectangle.getUpperLeft().getX();
        double startY = this.rectangle.getUpperLeft().getY();
        double width = this.rectangle.getWidth();
        double height = this.rectangle.getHeight();

        Color edgesColor = ColorsUtil.isSimilarColor(Color.RED, this.color) ? Color.GRAY : Color.RED;
        // draw 2 3D balls(="edges") at the 2 corners
        double ballR = height / 2;
        ColorsUtil.drawBall(surface, startX + ballR, startY + ballR, ballR, edgesColor, true);
        ColorsUtil.drawBall(surface, startX + width - ballR, startY  + ballR, ballR, edgesColor, true);

        // now draw the rectangle between them(with Left Right edges Chopped - this is the 'true' below)
        Rectangle mainPaddleRect = new Rectangle(new Point(startX + ballR, startY), width - 2 * ballR, height);
        ColorsUtil.draw3dRectangle(surface, mainPaddleRect, this.color, true);
    }

    @Override
    public void timePassed() {
        // enable up/down if 'u' or 'U' is pressed
        this.alsoUpDown = this.alsoUpDown || this.keyboard.isPressed("u") || this.keyboard.isPressed("U");

        // sum up the velocities we get from pressing the arrows keys
        Velocity velocity = Velocity.ZERO;

        // 4 possible moves: up / down / left / right,
        // each move has velocityAngle(movesAngles); keyBinding(movesStrings); isEnabled(movesShouldApply)
        double[] movesAngles = new double[] {Velocity.LEFT_ANGLE, Velocity.RIGHT_ANGLE,
                                                Velocity.UP_ANGLE, Velocity.DOWN_ANGLE};
        String[][] movesStrings = new String[][]{
                new String[]{KeyboardSensor.LEFT_KEY, "a"} ,
                new String[]{KeyboardSensor.RIGHT_KEY, "d"} ,
                new String[]{KeyboardSensor.UP_KEY, "w"} ,
                new String[]{KeyboardSensor.DOWN_KEY, "s"} ,
        };
        boolean[] movesShouldApply = new boolean[] {true, true, this.alsoUpDown, this.alsoUpDown};

        // for each possible move
        for (int i = 0; i < movesStrings.length; i++) {
            // ignore if its disabled and continue to next move
            if (movesShouldApply[i] == false) continue;

            // disable for now until key binding is found pressed
            movesShouldApply[i] = false;
            for (String key : movesStrings[i]) {
                movesShouldApply[i] = movesShouldApply[i] || this.keyboard.isPressed(key);
                movesShouldApply[i] = movesShouldApply[i] || this.keyboard.isPressed(key.toUpperCase());
                movesShouldApply[i] = movesShouldApply[i] || this.keyboard.isPressed(key.toLowerCase());
            }

            // if key binding was found pressed, apply if influence on the result velocity
            if (movesShouldApply[i] == true)
                velocity = velocity.plus(Velocity.fromAngleAndSpeed(movesAngles[i], speedSizeByPress));
        }

        // move the paddle the result velocity
        move(velocity);
    }

    /* Collidable interface methods */

    @Override
    public Rectangle getCollisionRectangle() {
        return rectangle;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        if (collisionPoint == null) {
            return currentVelocity;
        }

        //check where the collision point is
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        boolean collidedWithUpperLine = this.rectangle.getUpperLine().isIntersecting(collisionPoint);
        boolean collidedWithLowerLine = this.rectangle.getLowerLine().isIntersecting(collisionPoint);
        boolean collidedWithLeftLine = this.rectangle.getLeftLine().isIntersecting(collisionPoint);
        boolean collidedWithRightLine = this.rectangle.getRightLine().isIntersecting(collisionPoint);
        //if the ball hit the bottom edge that are not the upper edge return velocity with -1*dy
        if (!collidedWithUpperLine && collidedWithLowerLine) {
            dy = -dy;
        }
        //if the ball hit the left right edges that are not the upper edge return velocity with -1*dx
        if (!collidedWithUpperLine && (collidedWithLeftLine || collidedWithRightLine)) {
            dx = -dx;
        }
        if (!collidedWithUpperLine) {
            return new Velocity(dx, dy);
        }

        /* if the collision point is on the upper edge, change its angle
           according to the region it hit */

        // start from left angle
        double newAngle = Velocity.LEFT_ANGLE;
        int regions = 5;
        // the higer region that the hit was at, the more angle we add to the new angle towards the right direction
        double angleFromLeftToRight = (Velocity.RIGHT_ANGLE - Velocity.LEFT_ANGLE + 360) % 360;
        double eachRegionAngleAddition = angleFromLeftToRight / (regions + 1);
        double y = collisionPoint.getY();
        double leftX = this.rectangle.getUpperLeft().getX();
        double rightX = this.rectangle.getUpperRight().getX();
        double distanceX = (rightX - leftX) / regions;
        for (int i = 1; i <= regions; i++) {
            // check if the collision is on the region line
            double currRegionLeftX = leftX + (i - 1) * distanceX;
            double currRegionRightX = currRegionLeftX + distanceX;
            Line currRegionLine = new Line(currRegionLeftX, y, currRegionRightX, y);
            boolean isMiddleRegion = (regions + 1) / 2 == i;
            //if it is the middle region just to turn upside down the dy component
            if (isMiddleRegion && currRegionLine.isXInRange(collisionPoint.getX())) {
                return new Velocity(currentVelocity.getDx(), -1 * currentVelocity.getDy());
            }
            //if it isn't the middle region add to the current angle according to the hitted region
            if (currRegionLine.isXInRange(collisionPoint.getX())) {
                newAngle += i * eachRegionAngleAddition;
                break;
            }
        }
        return Velocity.fromAngleAndSpeed(newAngle, currentVelocity.getSize());
    }
}