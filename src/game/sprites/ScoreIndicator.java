/**
 * @author [Ehud Wasserman] [ID *********]
 */
package game.sprites;

import java.awt.Color;
import biuoop.DrawSurface;
import game.animation.GameLevel;
import game.axes.Counter;
import game.axes.Rectangle;

/**
 * The class represents a score(contains score value) 'sign' sprite. implements Sprite interface.
 */
public class ScoreIndicator implements Sprite {

    private Counter lives;
    /** Counter object which contain the score. */
    private Counter score;
    private String levelName;
    /** The Rectangle where to draw the sprite(and its width & height). */
    private Rectangle rect;

    /**
     * Constructor with adjustable value of string to print on screen before the score.
     * The given counter is represents the score of the user, which will be shown on screen.
     *
     * @param livesCounter the lives of the user to show
     * @param scoreCounter the score to show
     * @param levelName the name of the level that will be shown
     * @param rect the sprite rect to be drawn
     */
    public ScoreIndicator(Counter livesCounter, Counter scoreCounter, String levelName, Rectangle rect) {
        this.lives = livesCounter;
        this.score = scoreCounter;
        this.levelName = levelName;
        this.rect = rect;
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
        // Other Sprite cant collision with this sprite
        return Sprite.UNREACHABLE_RECT;
    }

    @Override
    public void drawOn(DrawSurface surface) {
        // draw (filled rectangle and write the score on it)
        ColorsUtil.drawFlatRectangle(surface, this.rect, Color.WHITE, true);
        surface.setColor(Color.BLACK);

        String livesStr = "Lives: " + lives.getValue();
        String scoreStr = "Score: " + score.getValue();
        String levelNameStr = "Level Name: " + levelName;
        String spaces = "                                       ";
        String outputStr = "   " + livesStr + spaces + scoreStr + spaces + levelNameStr;

        int fontSize = 18;
        // get the middle of this sprite's rectangle
        int middleY = (int) this.rect.getMiddlePoint().getY() + fontSize / 2;
        // draw text at the middle of this sprite's rectangle
        surface.drawText((int) this.rect.getUpperLeft().getX() , middleY, outputStr, fontSize);
    }

    @Override
    public void timePassed() {
        // ignore
    }
}