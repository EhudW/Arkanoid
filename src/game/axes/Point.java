/**
 * @author [Ehud Wasserman] [ID *********]
 */

package game.axes;

import java.util.List;
import java.util.Random;

/**
 * The class represents a point in Coordinate system, immutable object.
 * The class operations - constructor, getX, getY, equals, distance, getRandomPixel(x2), getClosetPoint
 */
public class Point {

    /** x coordinate value. */
    private double x;
    /** y coordinate value. */
    private double y;

    /**
     * Constructor with configurable values.
     *
     * @param x ,x coordinate value.
     * @param y ,y coordinate value.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the x value of the point.
     *
     * @return x value of this point
     */
    public double getX() {
        return this.x;
    }

    /**
     * Get the y value of the point.
     *
     * @return y value of this point
     */
    public double getY() {
        return this.y;
    }

    /**
     * Check if 2 points are the same.
     *
     * @param other point to compare with
     * @return true if the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        // use method isApproximatelyEqual to ignore very low diffrences between number
        return MathUtil.isApproximatelyEqual(this.x, other.getX())
               && MathUtil.isApproximatelyEqual(this.y, other.getY());
    }

    /**
     * Method of get the distance between points.
     *
     * @param other point to measure with
     * @return the distance(double) of this point to the other point,
     *         If one coordinate is Nan\INFINITY =>NaN will be returned.
     */
    public double distance(Point other) {
        // Mathematical formula for distance between 2 points
        return Math.sqrt(Math.pow(this.y - other.getY(), 2) + Math.pow(this.x - other.getX(), 2));
    }

    /**
     * Get random value of the pixel = point with positive values.
     *
     * @param fromX minimum[include] value - must be positive , and less than toX.
     * @param toX maximum[include] value - must be positive
     * @param fromY minimum[include] value - must be positive , and less than toY.
     * @param toY maximum[include] value - must be positive
     * @return new point instance, OR null if 'to' > 'from' OR if negative numbers was accepted
     */
    public static Point getRandomPixel(int fromX, int toX, int fromY, int toY) {
        if (toX < fromX || toY < fromY || fromX < 0 || fromY < 0) {
            return null;
        }
        Random rand = new Random();
        int x = rand.nextInt(toX - fromX + 1) + fromX;
        int y = rand.nextInt(toY - fromY + 1) + fromY;
        return new Point(x, y);
    }

    /**
     * Get random value of a pixel = point with positive values.
     *
     * @param toX maximum[include] value - must be positive
     * @param toY maximum[include] value - must be positive
     * @return new point instance from (0,0) to (toX,toY) [include]  OR null if negative numbers was accepted.
     */
    public static Point getRandomPixel(int toX, int toY) {
        return getRandomPixel(0, toX, 0, toY);
    }

    /**
     * Get the closer point between 2 points to another point.
     * returns null if the selected Point is null.
     * if one of the point is null, the other is returned.
     * point CAN be compared with itself reference.
     *
     * @param selectedPoint which we want the point will be closer to
     * @param pt1 first point to be checked
     * @param pt2 second point to be checked
     * @return Point that is closer
     */
    public static Point getClosetPoint(Point selectedPoint, Point pt1, Point pt2) {
        if (selectedPoint == null) {
            return null;
        }
        if (pt1 == null) {
            return pt2;
        }
        if (pt2 == null) {
            return pt1;
        }
        if (selectedPoint.distance(pt1) < selectedPoint.distance(pt2)) {
            return pt1;
        }
        return pt2;
    }

    /**
     * Get the closer point from list of points to given point.
     * returns null if the given point / list of points is null / contains only null points.
     * if one of the point is null, the other is considered more closer.
     * point CAN be compared with itself reference.
     *
     * @param list which we want to get from it the closer point to the selected Point
     * @param selectedPoint which we want the point will be closer to
     * @return Point that is the most closer
     */
    public static Point getClosetPoint(List<Point> list, Point selectedPoint) {
        if (list == null) {
            return null;
        }
        Point currClosetPt = null;
        for (Point currPt : list) {
            currClosetPt = Point.getClosetPoint(selectedPoint, currPt, currClosetPt);
        }
        return currClosetPt;
    }

}