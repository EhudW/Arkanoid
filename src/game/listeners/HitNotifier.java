/**
 * @author [Ehud Wasserman] [ID *********]
 */

package game.listeners;

/** The interface for object that notify HitListener about that he was hit. */
public interface HitNotifier {

    /**
     * Add a listener to the hit events. His hitEvent() method will be called when this 'is hit'.
     *
     * @param hl HitListener to be added to this notifier's listeners list.
     */
    void addHitListener(HitListener hl);

    /**
     * Remove a listener from the list of listeners to hit events.
     *
     * @param hl HitListener to be removed from this notifier's listeners list.
     */
    void removeHitListener(HitListener hl);
}