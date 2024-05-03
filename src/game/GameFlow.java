package game;

import java.util.List;

import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.animation.GameLevel;
import game.levels.LevelInformation;
import game.animation.Animation;
import game.animation.GameOverScreen;
import game.animation.YouWinScreen;
import game.animation.AnimationRunner;
import game.animation.KeyPressStoppableAnimation;
import game.axes.Counter;

/** The class to run levels one after one, and show YouWinScreen / GameOverScreen at the end.
  * The class won't close the given gui.
  */
public class GameFlow {
    /** Gui to run the levels within. */
    private GUI gui;
    /** User Score. */
    private Counter score;
    /** User 'lives'. */
    private Counter lives;
    /** Border blocks thick in each GameLevel. */
    private int borderThick;
    /** Property to decide if the animation will be '3D'. */
    private boolean is3D;

    /**
     * Constructor with adjustable setting of the start point of the game.
     * The given gui must be created before, and should be close manually.(after runLevels()).
     *
     * @param gui to draw the animation within.
     * @param score the start score of the user.(usually should be set to 0).
     * @param lives of the user in the game. should be positive.
     * @param borderThick which represnts the border blocks thick. shouldn't be negative.
     * @param is3D decide if the animation will be '3D'.
     */
    public GameFlow(GUI gui, Counter score, Counter lives, int borderThick, boolean is3D) {
        this.gui = gui;
        this.score = score;
        this.lives = lives;
        this.borderThick = borderThick;
        this.is3D = is3D;
    }

    /**
     * Run one by one the given levels, in their orders,
     * unless the user will loose in the middle ):
     * if the user will win/loose show suitable screen.
     *
     * @param levels to run in their order.
     */
    public void runLevels(List<LevelInformation> levels) {
        if (levels == null) {
            return;
        }
        // create animation runner with default value = 60 frames per seconds
        AnimationRunner runner = new AnimationRunner(this.gui, 60);

        for (LevelInformation levelInfo : levels) {
            if (levelInfo == null) {
                continue;
            }
            // pass the current lives & score to the game level
            GameLevel level = new GameLevel(runner, this.gui, levelInfo, score, lives, this.borderThick, is3D);
            level.initialize();
            // while the user didn't finish the level and he has 'lives' play a turn in the GameLevel
            while (this.lives.getValue() > 0 && level.remainedBlocks() > 0) {
                level.playOneTurn();
            }
            // if the user has no more 'lives' stop the loop
            if (this.lives.getValue() == 0) {
                break;
            }
            //otherwise, continue to the next level the user need to pass
        }

        String msg;
        Animation animation;
        KeyboardSensor keyboard = this.gui.getKeyboardSensor();
        // check if the user loose
        if (this.lives.getValue() == 0) {
            animation = new GameOverScreen(score);
        } else {
            animation = new YouWinScreen(score);
        }
        animation = new KeyPressStoppableAnimation(keyboard, KeyboardSensor.SPACE_KEY, animation);
        // run a suitable screen with the final score
        runner.run(animation);
        // < The gui won't be closed after the method will finish >
    }
}