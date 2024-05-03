package game.animation;

import biuoop.DrawSurface;
import game.axes.Counter;
import java.awt.Color;
import game.sprites.SpaceShip;
import game.sprites.ColorsUtil;
import game.sprites.Sprite;

/** The class for Winnig screen. */
public class YouWinScreen implements Animation {
    /** Sprite of SpaceShip which will bew drawn in the backgroud. */
    private Sprite spaceShip;
    /** The user score before he won. */
    private Counter score;

    /**
     * Constructor which get the score to show.
     *
     * @param score the final user score to be shown
     */
    public YouWinScreen(Counter score) {
        this.score = score;
        // create SpaceShip which will bew drawn in the backgroud.
        this.spaceShip = new SpaceShip();
    }

    @Override
    public void doOneFrame(DrawSurface surface) {
        // draw backgroud of space with starts
        ColorsUtil.drawBackground(surface, Color.BLUE.darker().darker(), true);
        // draw spaceShip on it
        this.spaceShip.drawOn(surface);

        int leftIndent = 10;
        int middleHeightOfScreen = surface.getHeight() / 2;
        int bigFontSize = 32;

        surface.setColor(Color.GREEN);
        surface.drawText(leftIndent, middleHeightOfScreen, "You Win! Your score is " + this.score.getValue(),
                                                                                                bigFontSize);
    }

    @Override
    public boolean shouldStop() {
        // show the animation always
        return false;
    }
}