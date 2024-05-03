package game.levels;

import java.util.List;
import game.axes.Velocity;
import game.sprites.Block;
import game.sprites.Sprite;

/**
 * Interface of information which is the start point of the level of arkanoid.
 * It gives all the information needed in order to start a level according to this information.
 * It makes it easier to make more of flexible levels of arkanoid.
 */
public interface LevelInformation {

    /**
     * Get the num of balls should be at each turn in this level.
     *
     * @return num of balls should be at each turn in this level
     */
    int numberOfBalls();

    /**
     * Get The initial velocity of each ball.
     * Note that initialBallVelocities().size() == numberOfBalls()
     *
     * @return List of velocities(size&angle) of the balls
     */
    List<Velocity> initialBallVelocities();

    /**
     * Get the paddle speed in this level.
     *
     * @return the paddleSpeed when the left/right arrow key is pressed.
     */
    int paddleSpeed();

    /**
     * Get the paddle width in the level.
     * [paddle height should be some reasonable size in GameLevel class]
     *
     * @return the paddlw width[paddle short/wide]
     */
    int paddleWidth();

    /**
     * Get the level name will be displayed at the top of the screen.
     *
     * @return String represention of the level name.
     */
    String levelName();

    /**
     * Returns a sprite with the background of the level.
     * The sprite should draw under all the other sprites.
     *
     * @return Sprite which is the background of this level
     */
    Sprite getBackground();

    /**
     * Get the Blocks that make up this level, each block contains
     * its size, color and location. Doesn't contains border blocks.
     *
     * @return List of the level blocks
     */
    List<Block> blocks();

    /**
     * Get the number of blocks that should be removed
     * before the level is considered to be "cleared".
     * This number <= blocks.size()
     *
     * @return number (positive) of how much blocks should be removed from screen to win this level
     */
    int numberOfBlocksToRemove();
}