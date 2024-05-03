package game.animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/** Decorator for Animation, which add feature of ending when given key is pressed. */
public class KeyPressStoppableAnimation implements Animation {
    /** The keyboard sensor of the gui of the animation. */
    private KeyboardSensor keyboard;
    /** The key which will stop the animation. */
    private String key;
    /** The inner animation. */
    private Animation animation;
    /** Field which determines if the key pressed before the start of this KeyPressStoppableAnimation. */
    private boolean isAlreadyPressed;
    /** Field which determines whether this animation shouldStop, if the is pressed. */
    private boolean stop;

    /**
     * Constructor which gets KeyboardSensor, key to check if pressed, and animation to be decorated.
     *
     * @param keyboard senesor of the gui
     * @param key to check if it is being pressed
     * @param animation to decorate
     */
    public KeyPressStoppableAnimation(KeyboardSensor keyboard, String key, Animation animation) {
        this.keyboard = keyboard;
        this.key = key;
        this.animation = animation;
        // assume the key was already pressed
        this.isAlreadyPressed = true;
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // check if right now the key is pressed
        boolean isKeyPressed = this.keyboard.isPressed(key);
        // if the key pressed and not already pressed in the previous frame, set stop = true
        if (isKeyPressed && !this.isAlreadyPressed) {
            this.stop = true;
        }
        // prepare for the next frame, and remember if the key pressed this frame
        this.isAlreadyPressed = isKeyPressed;
        // do the inner animation frame
        this.animation.doOneFrame(d);
    }

    @Override
    public boolean shouldStop() {
        // the animation should stop if this.stop==true OR if the inner Animation had end its animation
        return this.stop || animation.shouldStop();
    }
}