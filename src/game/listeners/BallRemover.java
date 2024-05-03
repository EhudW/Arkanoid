/**
 * @author [Ehud Wasserman] [ID *********]
 */

package game.listeners;

import game.animation.GameLevel;
import game.axes.Counter;
import game.sprites.Ball;
import game.sprites.Block;

/**
 * The class for BallRemover that is in charge of removing ball from the game,
 * when a ball hits block that this instance listen to,
 * as well as keeping count of the number of balls that remain. implements HitListener.
 */
public class BallRemover implements HitListener {

    /** The game to remove the ball/s from. */
    private GameLevel game;
    /** The counter of the remaining balls (in the game). */
    private Counter remainingBalls;

    /**
     * Constructor which get a game to remove balls from,
     * and counter(object by reference) to update(decrease) after removing ball from the game.
     * The given counter is represents the remaining balls in the given game. The original counter will be updated.
     *
     * @param game to remove hitter balls from.
     * @param remainingBalls Counter object to update after removing ball from the game.
     */
    public BallRemover(GameLevel game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    /**
     * This method is called whenever the beingHit Block is hit,
     * and then remove the hitter BALL from the game.
     * The Counter of the remaining balls that given in the constructor will be decreased.
     *
     * @param beingHit Block that is hit. ignored.
     * @param hitter Ball that's doing the hitting.(and will be removed from the game)
     */
     @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // (the BLOCK is the HitNotifier, so we don't need to remove this listener from the ball audience)
        hitter.removeFromGameLevel(this.game);
        // decrease the remaining ball counter by 1
        this.remainingBalls.decrease(1);
    }
}