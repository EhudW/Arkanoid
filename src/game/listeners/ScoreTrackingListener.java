/**
 * @author [Ehud Wasserman] [ID *********]
 */

package game.listeners;

import game.axes.Counter;
import game.sprites.Ball;
import game.sprites.Block;

/**
 * The class for Score tracking that is in charge of updating the score when Block is hit. implements HitListener.
 */
public class ScoreTrackingListener implements HitListener {

    /** Points that are added to user score for each hit on Block that notify an instance of this class. */
    public static final int SCORE_PER_BLOCK = 5;
    /** Reference of Counter of the current score. */
    private Counter currentScore;

    /**
     * Constructor which get counter(object by reference) to update(increase by 5) after removing block from the game.
     * The given counter is represents the score of the user. The original counter will be updated.
     *
     * @param scoreCounter Counter object to update after removing ball from the game.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * This method is called whenever the beingHit Block is hit,
     * and then remove the hitter BALL from the game.
     * The Counter of the remaining balls that given in the constructor will be decreased.
     *
     * @param beingHit Block that is hit. ignored.
     * @param hitter Ball that's doing the hitting. ignored.
     */
     @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        /* We don't Remove this ScoreTrackingListener instance from the block's listeners list,
           because we cannot know for sure that the block has been removed from the game.
           (maybe another hit will give score)  */

        // increase the score counter by SCORE_PER_BLOCK(= 5)
        this.currentScore.increase(SCORE_PER_BLOCK);
    }
}