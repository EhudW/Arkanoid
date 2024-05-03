/**
 * @author [Ehud Wasserman] [ID *********]
 */

package game.axes;

 /**
  * The class offers some useful math functions.
  * The class operations - isApproximatelyEqual, argsToDoubles, doublesToInts, isApproximatelyInRange, isInRange(x2) .
  */
public class MathUtil {

    /** SMALL_EPSILON - const for short distance between real nums which we ignore - FOR calculation . */
    public static final double SMALL_EPSILON = 10e-8;
    /** MEDIUM_EPSILON - FOR creating axes shapes that will still function properly. */
    public static final double MEDIUM_EPSILON = 1000 * SMALL_EPSILON;
    /** BIG_EPSILON - FOR keeping distance between objects. */
    public static final double BIG_EPSILON = 1000 * MEDIUM_EPSILON;

     /**
     * The method compare 2 real nums and return whether they approximately equal.
     *
     * @param num1 double number - can be real num , NAN/INFINITY
     * @param num2 double number - can be real num , NAN/INFINITY
     * @return true if they are the same OR distance is less than SMALL_EPSILON[class member]
     */
    public static Boolean isApproximatelyEqual(double num1, double num2) {
        //check if they are the same, espacially need for positive/negative infinity
        boolean areEqual = num1 == num2;
        //check if they both NaN (Double.NaN == Double.NaN returns false)
        boolean areBothNaN = Double.isNaN(num1) && Double.isNaN(num2);
        //if distance between real nums are small which we ignore it
        boolean areCloseEnough = Math.abs(num1 - num2) < SMALL_EPSILON;
        return areEqual || areBothNaN || areCloseEnough;
    }

    /**
     * The method parse each string in the array and give back array of doubles.
     *
     * @param args strings to be parsed (may throw RuntimeException if a string isn't suitable)
     * @return array of doubles of the strings array
     */
    public static double[] argsToDoubles(String[] args) {
        double[] array = new double[args.length];
            for (int i = 0; i < args.length; i++) {
                array[i] = Double.parseDouble(args[i]);
            }
        return array;
    }

    /**
     * The method cast each double in the array and give back array of integers.
     *
     * @param doubleArray to be cast
     * @return array of integers of the double array
     */
    public static int[] doublesToInts(double[] doubleArray) {
        int[] intArray = new int[doubleArray.length];
            for (int i = 0; i < doubleArray.length; i++) {
                intArray[i] = (int) doubleArray[i];
            }
        return intArray;
    }

    /**
     * The method check if a number is approximately between[include] limit1 to limit2.
     * The order of
     *
     * @param num to check
     * @param limit1 limit to check [can be max OR min]
     * @param limit2 limit to check [can be min OR max]
     * @return true if the number is approximately between the limits
     */
    public static boolean isApproximatelyInRange(double num, double limit1, double limit2) {
        return (limit1 < num && num < limit2) || (limit2 < num && num < limit1)
                || isApproximatelyEqual(num, limit1) || isApproximatelyEqual(num, limit2);
    }

    /**
     * The method check each double in the array if it's between min[include] to max[include].
     *
     * @param array to check its cells
     * @param min limit
     * @param max limit
     * @return true if ALL the doubles in the range
     */
    public static boolean isInRange(double[] array, double min, double max) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] < min || array[i] > max) {
                return false;
            }
        }
        return true;
    }

    /**
     * The method check each int in the array if it's between min[include] to max[include].
     *
     * @param array to check its cells
     * @param min limit
     * @param max limit
     * @return true if ALL the integers in the range
     */
    public static boolean isInRange(int[] array, double min, double max) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] < min || array[i] > max) {
                return false;
            }
        }
        return true;
    }
}