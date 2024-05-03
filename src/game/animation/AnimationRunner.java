package game.animation;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;

/**
 * The class for running animations that implement Animation interface.
 */
public class AnimationRunner {
    /** The gui to draw the frames of the animation on it. */
    private GUI gui;
    /** How many frames should be per second. */
    private int framesPerSecond;

    /**
     * Constructor which gets gui to draw the animation on,
     * and number of frames per second.
     *
     * @param gui to draw the animation on it.
     * @param framesPerSecond how many (estimated)frames will be in a second.
     */
    public AnimationRunner(GUI gui, int framesPerSecond) {
        this.gui = gui;
        this.framesPerSecond = framesPerSecond;
    }

    /**
     * Run a given animation until the animation notify to stop the running.
     * The method uses the given gui & framesPerSecond that was given in the constructor.
     * No problem should be if another animation will be start in the middle of one animation.
     *
     * @param animation to run and draw
     */
    public void run(Animation animation) {
        // how much time per frame
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        Sleeper sleeper = new Sleeper();
        // run the animation while it returns that it shouldn't stop
        while (!animation.shouldStop()) {
             // timing
            long startTime = System.currentTimeMillis();
            DrawSurface d = gui.getDrawSurface();

            // draw the frame on the surface & make move to next frame
            animation.doOneFrame(d);
            // show the frame in the gui
            gui.show(d);
            // wait for the next frame until enough time will pass to draw 'framesPerSecond' frames in second
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}