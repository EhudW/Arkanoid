package game.animation;

import biuoop.DrawSurface;

/** Interface of Animation which has two components: frame(on DrawSurface) AND boolean if should stop.
    classes implements this interface can be run by AnimationRunner */
public interface Animation {
    /**
     * Draw one frame on given surface,
     * and also make 'move' in the progress for the next frame.
     *
     * @param d DrawSurface to draw the frame on it
     */
    void doOneFrame(DrawSurface d);

    /**
     * Return if the animation was ended.
     *
     * @return boolean true if the animation should stop.
     */
    boolean shouldStop();
}