package game.sprites;

import java.awt.Color;
import biuoop.DrawSurface;
import game.animation.GameLevel;
import game.axes.Rectangle;
import game.axes.Point;

/** The class for draw sight on DrawSurface. Use only on one drawsurface.
 *  [meaning one defenitoin of hegiht x width]. */
public class Sight implements Sprite {

    /** The x value of the center point of the sight. */
    private int xCenter;
    /** The y value of the center point of the sight. */
    private int yCenter;

    /**
     * Constructor which get the x,y values of the center point.
     *
     * @param xCenter The x value of the center point of the sight
     * @param yCenter The y value of the center point of the sight
     */
    public Sight(int xCenter, int yCenter) {
        this.xCenter = xCenter;
        this.yCenter =  yCenter;
    }

    /**
     * Constructor which get center point of the sight.
     *
     * @param center the center point of the sight
     */
    public Sight(Point center) {
        this((int) center.getX(), (int) center.getY());
    }

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
        // draw circles around the given point with reasonable sizes
        surface.setColor(Color.BLUE);
        int innerCircleSize = 50;
        int middleCircleSize = 70;
        int outerCircleSize = 90;
        surface.drawCircle(xCenter , yCenter, innerCircleSize);
        surface.drawCircle(xCenter , yCenter, middleCircleSize);
        surface.drawCircle(xCenter , yCenter, outerCircleSize);
        // draw lines that cross the circles horizitional and vertical
        int extraEdgeForCrossLine = 10;
        // outerCircleSize is the maximum value
        int totalLineLengthFromCenter = extraEdgeForCrossLine + outerCircleSize;
        // draw horizitional line
        surface.drawLine(xCenter - totalLineLengthFromCenter, yCenter, xCenter + totalLineLengthFromCenter, yCenter);
        // draw vertical line
        surface.drawLine(xCenter, yCenter - totalLineLengthFromCenter, xCenter, yCenter + totalLineLengthFromCenter);
    }

    @Override
    public void timePassed() {
        // do nothing
    }
}