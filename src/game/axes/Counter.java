/**
 * @author [Ehud Wasserman] [ID *********]
 */

package game.axes;

/**  Class that is used for counting things. Counting is via int type. mutable object. */
public class Counter {

    /** The counter value. */
    private int value;

    /**
     * Constructor for the counter,
     * with adjustable start value.
     *
     * @param startValue value of the start counting state.
     */
    public Counter(int startValue) {
        this.value = startValue;
    }

    /**
     * Constructor for the counter,
     * with default start value = zero.
     */
    public Counter() {
        this(0);
    }

    /**
     * Add number to current count.
     *
     * @param number number to add to the counting.
     */
    public void increase(int number) {
        this.value += number;
    }

    /**
     * Subtract number from current count.
     *
     * @param number number to subtract from the counting.
     */
    public void decrease(int number) {
        this.value -= number;
    }

    /**
     * Get current count value.
     *
     * @return int of the current count value.
     */
    public int getValue() {
        return this.value;
    }

}