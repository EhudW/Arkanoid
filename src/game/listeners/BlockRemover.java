/**
 * @author [Ehud Wasserman] [ID *********]
 */

package game.listeners;

import game.animation.GameLevel;
import game.axes.Counter;
import game.sprites.Ball;
import game.sprites.Block;

/**
 * The class for BlockRemover that is in charge of removing blocks from the game when they are hit,
 * as well as keeping count of the number of blocks that remain. implements HitListener.
 */
public class BlockRemover implements HitListener {

    /** The game to remove the block/s from. */
    private GameLevel game;
    /** The counter of the remaining blocks (in the game). */
    private Counter remainingBlocks;

    /**
     * Constructor which get a game to remove hitted blocks from,
     * and counter(object by reference) to update(decrease) after removing block from the game.
     * The given counter is represents the remaining blocks in the given game. The original counter will be updated.
     *
     * @param game to remove hitted blocks from.
     * @param remainingBlocks Counter object to update after removing block from the game.
     */
    public BlockRemover(GameLevel game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * This method is called whenever the beingHit Block is hit,
     * and then remove the hitted block from the game.
     * This listener will also be deleted from the HitListeners list of that block.
     * The Counter of the remaining blocks that given in the constructor will be decreased.
     *
     * @param beingHit Block that is hit.
     * @param hitter Ball that's doing the hitting. ignored.
     */
     @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        //Remove this BlockRemover instance from the block's listeners list.
        beingHit.removeHitListener(this);
        beingHit.removeFromGameLevel(this.game);
        // decrease the remaining block counter by 1
        this.remainingBlocks.decrease(1);
    }
}