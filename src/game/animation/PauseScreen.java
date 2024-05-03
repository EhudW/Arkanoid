package game.animation;

import biuoop.DrawSurface;
import java.awt.Color;
import game.sprites.SmileyAndBee;
import game.sprites.Sprite;
import game.sprites.Background;

/** The class for pause screen. */
public class PauseScreen implements Animation {
    /** The background of the instance. */
    private Sprite background;

    /** Constructor which create background instance for later use. */
    public PauseScreen() {
        background = new Background(Color.GREEN.brighter(), new SmileyAndBee());
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // draw the background and update it that the time passed
        background.drawOn(d);
        background.timePassed();

        int leftIndent = 10;
        int middleHeightOfScreen = d.getHeight() / 2;
        int bigFontSize = 32;

        d.setColor(Color.BLACK);
        d.drawText(leftIndent, middleHeightOfScreen, "paused -- press space to continue", bigFontSize);
    }

    @Override
    public boolean shouldStop() {
        // show the animation always
        return false;
    }
}