package game.sprites;

import java.awt.Color;
import biuoop.DrawSurface;
import game.axes.Rectangle;
import game.animation.GameLevel;

/** The class for draw SpaceShip on DrawSurface. Use only on one drawsurface.
 *  [meaning one defenitoin of hegiht x width]. */
public class SpaceShip implements Sprite {

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
        double doubleY = surface.getHeight() * 2 / 3;
        double doubleX = surface.getWidth() * 2 / 3;
        int x = (int) doubleX;
        int y = (int) doubleY;
        // draw the space ship at the right-down corner
        drawSpaceShip(surface, x, y);
    }

    @Override
    public void timePassed() {
        // do nothing
    }

    /**
     * Draw space ship in the given x,y.
     *
     * @param surface to draw on it
     * @param x - x value where to draw
     * @param y - y value where to draw
     */
    private void drawSpaceShip(DrawSurface surface, int x, int y) {
        // fill black oval with reasonable size
        int r1 = 160;
        int r2 = 100;
        surface.setColor(Color.BLACK);
        surface.fillOval(x, y, r1, r2);

        // draw white window next to the left-up corner
        int windowWidth = r1 / 5;
        int windowHeight = windowWidth / 2;
        // For mathematical considerations of oval
        int xLeftWindow = x + r1 / 4;
        int yUpWindow = y + r2 / 4;
        surface.setColor(Color.WHITE);
        surface.fillRectangle(xLeftWindow, yUpWindow, windowWidth, windowHeight);

        // draw line for 'broken' glass / light reflection from left-down corner of the window to up-(littel)right
        surface.setColor(Color.BLACK);
        surface.drawLine(xLeftWindow, yUpWindow + windowHeight, xLeftWindow + windowWidth / 3, yUpWindow);

        // draw fire at the tail of the space ship
        // go the right edge
        x += r1;
        // go to middle of the ship height
        y += r2 / 2;

        // draw some levels of fire flame
        int fireWidth = 80;

        int bigFlameHeight = 24;
        int middleFlameHeight = 16;
        int smallFlameHeight = 8;

        surface.setColor(Color.RED);
        surface.fillOval(x, y - bigFlameHeight / 2, fireWidth, bigFlameHeight);

        surface.setColor(Color.ORANGE);
        surface.fillOval(x, y - middleFlameHeight / 2, fireWidth, middleFlameHeight);

        surface.setColor(Color.YELLOW);
        surface.fillOval(x, y - smallFlameHeight / 2, fireWidth, smallFlameHeight);
    }
}