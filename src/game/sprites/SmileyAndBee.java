package game.sprites;

import java.awt.Color;
import biuoop.DrawSurface;
import game.animation.GameLevel;
import game.axes.Point;
import game.axes.Rectangle;
import game.axes.Velocity;

/** The class for draw Smiley And Bee higher it, on DrawSurface. Use only on one drawsurface.
 *  [meaning one defenitoin of hegiht x width]. */
public class SmileyAndBee implements Sprite {
    /** Where the be placed. we represnt it by Ball which can also move. */
    private Ball beePlace = null;
    /** The height of the drawSurface. */
    private int winHeight;
    /** The width of the drawSurface. */
    private int winWidth;

    @Override
    public void addToGameLevel(GameLevel g) {
        g.addSprite(this);
    }

    @Override
    public void removeFromGameLevel(GameLevel g) {
        g.removeSprite(this);
    }

    @Override
    public Rectangle getSpriteRectangle() {
        return Sprite.UNREACHABLE_RECT;
    }

    @Override
    public void drawOn(DrawSurface surface) {
        // if this the first time to draw this Sprite, initiallize beePlace & this.winHeight & this.winWidth
        if (this.beePlace == null) {
            this.winHeight = surface.getHeight();
            this.winWidth = surface.getWidth();
            Velocity normalVelocity = new Velocity(2, 0);
            // we won't draw the ball, so the radius not important, but it's important for effect on moveOneStep()
            int ignoredRadius = 1;
            this.beePlace = new Ball(new Point(this.winWidth / 6, this.winHeight / 5),
                                                            ignoredRadius, Color.WHITE, normalVelocity);
        }

        // bee x, y
        int xBee = (int) this.beePlace.getX();
        int yBee = (int) this.beePlace.getY();

        // fill black oval - the body of the bee
        int bodyWidth = 36;
        int bodyHeight = 15;
        surface.setColor(Color.BLACK);
        surface.fillOval(xBee, yBee, bodyWidth, bodyHeight);

        boolean flyLeft = this.beePlace.getVelocity().getDx() < 0;
        int wingHeight = 20;
        int wingWidth = 7;
        // most of the wings are outside the body
        int yWing = yBee - 10;
        int xRightWing;
        int xDiffWings = 4;

        if (flyLeft) {
            xRightWing = xBee + bodyWidth / 5 + xDiffWings;
        } else {
            xRightWing = xBee + bodyWidth - bodyWidth / 5 - wingWidth;
        }

        // draw the border of ovals - the wings of the bee
        // draw the rigt wing
        surface.drawOval(xRightWing, yWing, wingWidth, wingHeight);
        // draw the left wing
        surface.drawOval(xRightWing - xDiffWings, yWing, wingWidth, wingHeight);


        // draw two yellow Stripes on the body of the bee
        surface.setColor(Color.YELLOW);
        int stripThick = 3;
        surface.fillRectangle(xBee + 1 * bodyWidth / 4, yBee + bodyHeight / 4, stripThick, bodyHeight / 2);
        surface.fillRectangle(xBee + 3 * bodyWidth / 4, yBee + bodyHeight / 4, stripThick, bodyHeight / 2);

        //smiley x, y
        int xSmiley = this.winWidth / 6;
        // smiley under the bee
        int ySmiley = this.winHeight / 5 + 70;

        // draw the face of the smiley
        int smileyRadius = 35;
        surface.setColor(Color.YELLOW);
        surface.fillCircle(xSmiley, ySmiley, smileyRadius);
        surface.setColor(Color.BLACK);
        surface.drawCircle(xSmiley, ySmiley, smileyRadius);

        // move the eye left\ right according to the bee movement
        int whiteOfEyeRadius = 8;
        int appleOfTheEyeRadius = 4;
        // according to the limits at timePassed of the possible movement of the bee
        double horizinalIndent = (xBee - xSmiley) / ((this.winWidth / 3 - 0) / 2.0);
        horizinalIndent = horizinalIndent * (whiteOfEyeRadius - appleOfTheEyeRadius);
        double verticalIndent = -(whiteOfEyeRadius - appleOfTheEyeRadius) + Math.abs(horizinalIndent);
        // the appleOfTheEyeRadius should be seen as look towards up
        verticalIndent -= 2;
        // to make the animation more fluency we avoid peek of when Math.abs(horizinalIndent) == 0
        if (Math.abs(horizinalIndent) == 0) {
            verticalIndent++;
        }


        // move the eye up/down according to the bee movement

        // right eye
        int rightEyeX = xSmiley + smileyRadius / 3;
        int rightEyeY = ySmiley - 2 * smileyRadius / 4;
        surface.setColor(Color.WHITE);
        surface.fillCircle(rightEyeX, rightEyeY, whiteOfEyeRadius);
        surface.setColor(Color.BLACK);
        surface.fillCircle(rightEyeX + (int) horizinalIndent, rightEyeY + (int) verticalIndent, appleOfTheEyeRadius);

        // left eye
        int leftEyeX = xSmiley - smileyRadius / 3;
        int leftEyeY = ySmiley - 2 * smileyRadius / 4;
        surface.setColor(Color.WHITE);
        surface.fillCircle(leftEyeX, leftEyeY, whiteOfEyeRadius);
        surface.setColor(Color.BLACK);
        surface.fillCircle(leftEyeX + (int) horizinalIndent, leftEyeY + (int) verticalIndent, appleOfTheEyeRadius);
    }

    @Override
    public void timePassed() {
        /* move the bee.
           notice that according to the limits of the possible movement of the bee,
           there are things dependence on it in drawOn() */
        this.beePlace.moveOneStep(0, 0, this.winWidth / 3, this.winHeight);
    }

}