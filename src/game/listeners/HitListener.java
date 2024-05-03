/**
 * @author [Ehud Wasserman] [ID *********]
 */

package game.listeners;

import game.sprites.Ball;
import game.sprites.Block;

/** The interface for listener who want to be notified when HitNotifier is being hit. */
public interface HitListener {

    /**
     * This method is called whenever the beingHit Block is hit.
     * [no guarantee EXACTLY when - before / after other internal associated-hit Block methods].
     * The hitter parameter is the Ball that's doing the hitting.
     *
     * @param beingHit Block that is hit.
     * @param hitter Ball that's doing the hitting.
     */
    void hitEvent(Block beingHit, Ball hitter);
}