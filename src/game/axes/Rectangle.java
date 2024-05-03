/**
 * @author [Ehud Wasserman] [ID *********]
 */

package game.axes;

import java.util.List;
import java.util.ArrayList;

/**
 * The class that represents rectangle that is parallel to the axes. immutable object.
 * class operations - Constructor, getWidth, getHeight,
 *                    getUpperLeft, getUpperRight, getLowerLeft, getLowerRight,
 *                    getLeftLine, getRightLine, getUpperLine, getLowerLine, getMiddlePoint
 *                    intersectionPoints, velocityToCollision, getClosestRectangle
 */
public class Rectangle {

    /** Static refernce for rect that is not in the axes = 'unreachable'. */
    public static final Rectangle UNREACHABLE_RECT = null;

    /** The upper-Left point of the rectangle. */
    private Point upperLeft;
    /** The upper-right point of the rectangle. */
    private Point upperRight;
    /** The lower-Left point of the rectangle. */
    private Point lowerLeft;
    /** The lower-right point of the rectangle. */
    private Point lowerRight;

    /**
     * Create a new rectangle with upper-left-point location and width/height.
     *
     * @param upperLeft the upper left point of the rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        double upLeftX = upperLeft.getX();
        double upLeftY = upperLeft.getY();
        this.upperRight = new Point(upLeftX + width, upLeftY);
        this.lowerLeft = new Point(upLeftX, upLeftY + height);
        this.lowerRight = new Point(upLeftX + width, upLeftY + height);
    }

    /**
     * Get the rectangle width.
     *
     * @return the width of the rectangle
     */
    public double getWidth() {
        return upperLeft.distance(upperRight);
    }

    /**
     * Get the rectangle Height.
     *
     * @return the Height of the rectangle
     */
    public double getHeight() {
        return upperLeft.distance(lowerLeft);
    }

    /**
     * Get upper-left point of the rectangle.
     *
     * @return the upper-left point of the rectangle.
     */
    public Point getUpperLeft() {
        return upperLeft;
    }

    /**
     * Get upper-right point of the rectangle.
     *
     * @return the upper-right point of the rectangle.
     */
    public Point getUpperRight() {
        return upperRight;
    }

    /**
     * Get lower-left point of the rectangle.
     *
     * @return the lower-left point of the rectangle.
     */
    public Point getLowerLeft() {
        return lowerLeft;
    }

    /**
     * Get lower-right point of the rectangle.
     *
     * @return the lower-right point of the rectangle.
     */
    public Point getLowerRight() {
        return lowerRight;
    }

    /**
     * Get the line of the left edge.
     *
     * @return Line of the left edge of the rectangle
     */
    public Line getLeftLine() {
        return new Line(upperLeft, lowerLeft);
    }

    /**
     * Get the line of the right edge.
     *
     * @return Line of the right edge of the rectangle
     */
    public Line getRightLine() {
        return new Line(upperRight, lowerRight);
    }

    /**
     * Get the line of the upper edge.
     *
     * @return Line of the upper edge of the rectangle
     */
    public Line getUpperLine() {
        return new Line(upperLeft, upperRight);
    }

    /**
     * Get the line of the lower edge.
     *
     * @return Line of the lower edge of the rectangle
     */
    public Line getLowerLine() {
        return new Line(lowerLeft, lowerRight);
    }

    /**
     * Get the point that is in the center of the rectangle.
     *
     * @return Point of the center of this rectangle
     */
    public Point getMiddlePoint() {
        return new Point(getLowerLine().middle().getX(), getLeftLine().middle().getY());
    }

    /**
     * Get a (possibly empty) List of intersection points
     * with the specified line (and this rectangle).
     *
     * @param line to check intersection with
     * @return List of points of intersections. list can be empty, but won't be null
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> list = new ArrayList<Point>();
        Line rightLine = getRightLine();
        Line leftLine = getLeftLine();
        Line upperLine = getUpperLine();
        Line lowerLine = getLowerLine();
        // each edge, if it has intersection with the given line- add the intersection Points to the list
        if (line.isIntersecting(rightLine)) {
            list.add(line.intersectionWith(rightLine));
        }
        if (line.isIntersecting(leftLine)) {
            list.add(line.intersectionWith(leftLine));
        }
        if (line.isIntersecting(upperLine)) {
            list.add(line.intersectionWith(upperLine));
        }
        if (line.isIntersecting(lowerLine)) {
            list.add(line.intersectionWith(lowerLine));
        }
        return list;
    }

    /**
     * Check if this rectangle, while move the given velocity,
     * will intersect other [given] rectangle.
     * Returns new velocity for the first intersection, or the 'null' if there is no intersection.
     *
     * @param other rectangle to avoid this(current) rectangle to intersect it(=the other)
     * @param velocity that would apply on this rectangle that we want to check
     * @return new velocity for the first intersection
     */
    public Velocity velocityToCollision(Rectangle other, Velocity velocity) {
        if (other == null || other == this || velocity == null) {
            return null;
        }
        // if there is no intersection the return velocity is null
        Velocity velocityToCollision = null;

        /* we will check if moving the point of this OR of the other rectangle will make intersections,
           that way we consider any case that one rectangle is bigger/smaller than the second */

        // points of this rectangle
        Point[] thisPoints = new Point[]{this.getUpperLeft(), this.getUpperRight(),
                                            this.getLowerLeft(), this.getLowerRight()};
        // for each point check if applying the given velocity will make intersection with the other rectangle
        for (int i = 0; i < thisPoints.length; i++) {
            Line thisTrajectory = new Line(thisPoints[i], velocity);
            Point closestPoint = Point.getClosetPoint(other.intersectionPoints(thisTrajectory), thisPoints[i]);
            // check if was intersection in the point trajectory
            if (closestPoint == null) {
                continue;
            }
            /* if was intersection update the velocity if it [null or if it] has size larger than
               the distance we get from the current point to the closest intersection point */
            if (velocityToCollision == null || closestPoint.distance(thisPoints[i]) < velocityToCollision.getSize()) {
                velocityToCollision = Velocity.fromAngleAndSpeed(velocity.getAngle(),
                                                                    closestPoint.distance(thisPoints[i]));
            }
        }

        // do the same for the other rectangle if it goes the opposite velocity <=> velocity * -1
        Point[] otherPoints = new Point[]{other.getUpperLeft(), other.getUpperRight(),
                                            other.getLowerLeft(), other.getLowerRight()};
        for (int i = 0; i < otherPoints.length; i++) {
            Line otherTrajectory = new Line(otherPoints[i], velocity.multiply(-1));
            Point closestPoint = Point.getClosetPoint(this.intersectionPoints(otherTrajectory), otherPoints[i]);
            if (closestPoint == null) {
                continue;
            }
            if (velocityToCollision == null || closestPoint.distance(otherPoints[i]) < velocityToCollision.getSize()) {
                /* even though we check the opposite velocity,
                   the velocityToCollision direction-angle must be according this rectangle */
                velocityToCollision = Velocity.fromAngleAndSpeed(velocity.getAngle(),
                                                                    closestPoint.distance(otherPoints[i]));
            }
        }

        return velocityToCollision;
    }

    /**
     * Get the closest rectangle from a list,
     * that if we apply the given velocity on THIS rectangle,
     * will be the first to intersect. returns null if there is no one.
     * rectangle isn't being compared with itself(if this rectangle is in the given list)
     *
     * @param others -list of others rectangle to check intersection with this rectangle
     * @param velocity -velocity to apply on this rectangle to check for intersection
     * @return Rectangle from the given list that is the closest one for inintersection. null if there isn't.
     */
    public Rectangle getClosestRectangle(List<Rectangle> others, Velocity velocity) {
        if (others == null || velocity == null) {
            return null;
        }

        // if there is no intersection the return closetRectangle/velocity is null
        Rectangle closetRectangle = null;
        Velocity velocityToCollision = null;

        // for each rect in the list check that isn't this rectangle
        for (Rectangle otherRect : others) {
            if (otherRect == this || otherRect == null) {
                continue;
            }
            // get the closest velocity from this rect to the curr otherRect in the iteration
            Velocity newV = this.velocityToCollision(otherRect, velocity);
            // if the newV is lower than velocityToCollision update the closetRectangle and velocityToCollision
            if (velocityToCollision == null || (newV != null && newV.getSize() < velocityToCollision.getSize())) {
                velocityToCollision = this.velocityToCollision(otherRect, velocity);
                closetRectangle = otherRect;
            }
        }
        return closetRectangle;
    }

}