package game.animation;

import biuoop.DrawSurface;
import game.axes.Counter;
import game.sprites.Cake;
import java.awt.Color;
import game.sprites.Sprite;
import game.sprites.Background;

/** Class for animation for GameOverScreen. */
public class GameOverScreen implements Animation {
    /** The background of the instance. */
    private Sprite background;
    /** The user score before he loose. */
    private Counter score;

    /**
     * Constructor which get the score to show.
     *
     * @param score the final user score to be shown
     */
    public GameOverScreen(Counter score) {
        this.score = score;
        // create colored background with cake.
        this.background = new Background(Color.CYAN.darker(), new Cake());
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        background.drawOn(d);
        d.setColor(Color.BLACK);
        int leftIndent = 10;
        int topOfScreen = d.getHeight() / 8;
        int bigFontSize = 32;
        d.drawText(leftIndent, topOfScreen, "Game Over. Your score is "  + this.score.getValue(), bigFontSize);

        int bottomOfScreen = 5 * d.getHeight() / 8;
        int regularFontSize = 17;
        d.drawText(leftIndent, bottomOfScreen, "The cake is a lie.", regularFontSize);
    }

    @Override
    public boolean shouldStop() {
        // show the animation always
        return false;
    }
}