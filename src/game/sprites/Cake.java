package game.sprites;

import java.awt.Color;
import biuoop.DrawSurface;
import game.animation.GameLevel;
import game.axes.Rectangle;

/** The class for draw Cake on bottom-right on DrawSurface. Use only on one drawsurface. */
public class Cake implements Sprite {

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

    /**
     * Draw cherry with its branch on given x,y on the SrawSurface.
     *
     * @param surface to draw on
     * @param x - x value where to draw the cherry
     * @param y - y value where to draw the cherry
     */
    public void drawCherry(DrawSurface surface, double x, double y) {
        //draw the branch of the cherry
        surface.setColor(Color.GREEN.darker());
        // the branch will have Gradient of 5 / 10
        surface.drawLine((int) x, (int) y, (int) x + 10, (int) y - 5);

        // draw the fruit [radius of 4]
        surface.setColor(Color.RED.darker());
        surface.fillCircle((int) x, (int) y, 4);
    }

    @Override
    public void drawOn(DrawSurface surface) {
        int x = (int) (surface.getWidth() / 2);
        int y = (int) (surface.getHeight() / 2);

        // The cake have cherries, top[MAGENTA], 1st layer[WHITE], 2nd layer[PINK] (part of) bottom[PINK]
        int cakeWidth = 170;
        int cakeHeight = 60;
        int thickFromSide = 90;

        // draw the bottom of the cake [contains also part of the 2nd layer]
        // fill
        surface.setColor(Color.PINK);
        surface.fillOval(x, y + cakeHeight, cakeWidth, thickFromSide);
        // borders
        surface.setColor(Color.BLACK);
        surface.drawOval(x, y + cakeHeight, cakeWidth, thickFromSide);

        // draw the 2nd level between the bottom, and the 1st level [we draw on it the 1st level]
        // fill
        surface.setColor(Color.PINK);
        surface.fillRectangle(x, y + thickFromSide / 2, cakeWidth, cakeHeight);

        // draw the 1st level between the  2nd and the top
        int layer1Height = cakeHeight / 2;
        int layer1Y = y + thickFromSide - thickFromSide + layer1Height;
        // fill oval of the layer
        surface.setColor(Color.WHITE);
        surface.fillOval(x, layer1Y, cakeWidth, thickFromSide);
        // fill with rectangle the gap between the 1st layer and the top of the cake
        surface.setColor(Color.WHITE);
        surface.fillRectangle(x, y + thickFromSide / 2, cakeWidth, layer1Height);

        // borders of both two layers from left & right
        surface.setColor(Color.BLACK);
        surface.drawLine(x, y + thickFromSide / 2, x, y + thickFromSide / 2 + cakeHeight);
        surface.drawLine(x + cakeWidth, y + thickFromSide / 2, x + cakeWidth, y + thickFromSide / 2 + cakeHeight);

        // draw the top of the cake ('flat')
        // fill
        surface.setColor(Color.MAGENTA);
        surface.fillOval(x, y, cakeWidth, thickFromSide);
        // borders
        surface.setColor(Color.BLACK);
        surface.drawOval(x, y, cakeWidth, thickFromSide);

        // show cutting marks of quarter of the cake
        surface.drawLine(x + cakeWidth / 2 , y + thickFromSide / 2, x + cakeWidth, y + thickFromSide / 2);
        surface.drawLine(x + cakeWidth / 2 , y + thickFromSide / 2, x + cakeWidth / 2, y + thickFromSide + cakeHeight);

        // put cherries on the top of the cake
        drawCherry(surface, x + cakeWidth / 1.4, y + thickFromSide / 1.4);
        drawCherry(surface, x + cakeWidth / 1.4, y + thickFromSide / 3);
        drawCherry(surface, x + cakeWidth / 5, y + thickFromSide / 1.4);
    }

    @Override
    public void timePassed() {
        // do nothing
    }

}