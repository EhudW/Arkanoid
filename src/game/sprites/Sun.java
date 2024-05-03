package game.sprites;

import java.awt.Color;
import biuoop.DrawSurface;
import game.animation.GameLevel;
import game.axes.Rectangle;

/** The class for draw sun and its rays on DrawSurface. Use only on one drawsurface.
 *  [meaning one defenitoin of hegiht x width]. */
public class Sun implements Sprite {

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
        int width = surface.getWidth();
        int height = surface.getHeight();
        // put the sun at the top left corner
        int xSun = width / 6;
        int ySun = height / 6;
        surface.setColor(Color.ORANGE);
        // draw cicrle around the sun
        // do it for circles that goes down in their size
        int maxCircleSize = 70;
        int secondCircleSize = 55;
        int thirdCircleSize = 45;
        // in the outer circle we don't fill it but draw many cicrles
        int maxCircleSizeDiff = 3;
        for (int i = 0; i < maxCircleSize - secondCircleSize; i += maxCircleSizeDiff) {
             surface.drawCircle(xSun, ySun, maxCircleSize - i);
        }

        // draw rays from the sun to line that cross the gui
        int yTargetRay = height / 2;
        int spaceBetweenRays = 7;
        for (int xTargetRay = 0; xTargetRay < width; xTargetRay += spaceBetweenRays) {
            surface.drawLine(xSun, ySun, xTargetRay, yTargetRay);
        }

        // fill second circle & third circle of the sun
        surface.setColor(Color.ORANGE.darker());
        surface.fillCircle(xSun, ySun, secondCircleSize);
        surface.setColor(Color.YELLOW.darker());
        surface.fillCircle(xSun, ySun, thirdCircleSize);
    }

    @Override
    public void timePassed() {
        // do nothing
    }

}