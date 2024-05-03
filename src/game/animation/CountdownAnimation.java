package game.animation;

import java.awt.Color;
import biuoop.DrawSurface;
import game.sprites.SpriteCollection;

/** The CountdownAnimation will display the given gameScreen, passed as SpriteCollection,
 *  for numOfSeconds seconds, and on top of them it will show,
 *  a countdown from countFrom back to 1, where each number will appear,
 * on the screen for (numOfSeconds / countFrom) seconds,
 *  before it is replaced with the next one.
 */
public class CountdownAnimation implements Animation {
    /** How much time to count the countdown. */
    private double numOfSecondsToCount;
    /** What is the first number for the countdown.(1 is the last). */
    private int countFrom;
    /** The 'screen'(by its drawAllOn()) to show under the countdown. */
    private SpriteCollection gameScreen;
    /** The first time frame of this countdown animation was drawn. */
    private long startTime;
    /** Boolean wheter first frame of this countdown animation was drawn. */
    private boolean hasStartedToShown;
    /** The gui width. */
    private int guiWidth;
    /** The gui height. */
    private int guiHeight;

    /**
     * Constructor for the class.
     * which gets from where to count and for how much time, and what to draw in back of the countdown.
     *
     * @param numOfSecondsToCount how much time this animation will be.
     * @param countFrom the first num to show at the countdown. ( should be >= 1)
     * @param gameScreen the sprite collection which represnts the 'background' in back of this counter.
     */
    public CountdownAnimation(double numOfSecondsToCount, int countFrom, SpriteCollection gameScreen) {
        this.numOfSecondsToCount = numOfSecondsToCount;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.hasStartedToShown = false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // if this is the first frame, initialize gui width & gui height & startTime
        if (!hasStartedToShown) {
            this.guiWidth = d.getWidth();
            this.guiHeight = d.getHeight();
            hasStartedToShown = true;
            this.startTime = System.currentTimeMillis();
        }
        // draw the game screen
        gameScreen.drawAllOn(d);
        // get the current time
        long currTime = System.currentTimeMillis();
        // calc how many seconds were passed since the first frame [notice we use 1000.0 = double]
        double passedSeconds = (currTime - this.startTime) / 1000.0;
        // calc how many seconds each num should be
        double secondsPerNum = numOfSecondsToCount / this.countFrom;
        // calc how many num were already absolutly count (round down by cast to int)
        int numThatPassed = (int) (passedSeconds / secondsPerNum);
        //that means the correct num is:
        int currNum = countFrom - numThatPassed;
        // (don't show the '0' as countdown)
        if (currNum <= 0) {
            return;
        }

        // in front of the 'game screen' draw the num, duplicated to empasizh it
        int topOfTheScreen = guiHeight / 6;
        d.setColor(Color.BLACK);
        d.drawText(guiWidth / 2, topOfTheScreen, currNum + "", 55);
        d.setColor(Color.RED);
        d.drawText(guiWidth / 2, topOfTheScreen, currNum + "", 45);
    }

    @Override
    public boolean shouldStop() {
        long currTime = System.currentTimeMillis();
        // the animation should stop if the first frame was drawn, and we pass the time to count
        return this.hasStartedToShown && currTime > this.startTime + this.numOfSecondsToCount * 1000;
    }
}