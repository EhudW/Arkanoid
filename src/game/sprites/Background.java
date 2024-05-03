package game.sprites;

import java.awt.Color;
import biuoop.DrawSurface;
import game.animation.GameLevel;
import game.axes.Rectangle;

/** The class for draw background (color) on DrawSurface. optional to draw also another Sprite. */
public class Background implements Sprite {

    /** The color of the background. */
    private Color color;
    /** Another sprite to draw on the colored background [optional]. */
    private Sprite topSprite;

    /**
     * Constructor which also get sprite to draw on the color background.
     * NOTICE this class also notify the sprite that the timePassed()
     * Can get null. (which will be ignored at the drawing)
     *
     * @param color of the background
     * @param topSprite to draw on the background. CAN be null.
     */
    public Background(Color color, Sprite topSprite) {
        this.color = color;
        this.topSprite = topSprite;
    }

    /**
     * Constructor which color to the background.
     * no another sprite will be drawn by this instance.
     *
     * @param color of the background
     */
    public Background(Color color) {
        this(color, null);
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
        // draw the background and the sprite on it
        ColorsUtil.drawBackground(surface, this.color, false);
        if (topSprite != null) {
            topSprite.drawOn(surface);
        }
    }

    @Override
    public void timePassed() {
        // notice the inner sprite that the time passed
        if (topSprite != null) {
            topSprite.timePassed();
        }
    }

}