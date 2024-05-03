/**
 * @author [Ehud Wasserman] [ID *********]
 */

package game.axes;

import java.util.List;

/**
 * The class represents a line in Coordinate system, immutable object.
*/
public class Line {

    /** The start point of the line, the start-end order is important. */
    private Point start;
    /** The end point of the line, the start-end order is important.  */
    private Point end;

    /**
     * Constructor which get start + end points of the line,
     * the start-end order is important.
     *
     * @param start ,start point value.
     * @param end ,end point value.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructor which get 4 Coordinates of the line,
     * the start-end order is important.
     *
     * @param x1 ,the x coordinate of the start point.
     * @param y1 ,the y coordinate of the start point.
     * @param x2 ,the x coordinate of the end point.
     * @param y2 ,the y coordinate of the end point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * Constructor which get point and velocity,
     * and returns the line that start at the point,
     * and ends after appplying the velocity.
     *
     * @param pt the start point.
     * @param v the velocity to apply on the start point, in order to find the end of the new line.
     */
    public Line(Point pt, Velocity v) {
        this(pt, v.applyToPoint(pt));
    }

    /**
     * Check if 2 lines are equal, the start-end order is important.
     *
     * @param other -line to be compared with.
     * @return true if the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        return (other.start().equals(this.start) && other.end().equals(this.end));
    }

    /**
     * Get the start point(it is immutable object).
     *
     * @return the start point of the line
     */
    public Point start() {
        return start;
    }

    /**
     * Get the end point(it is immutable object).
     *
     * @return the end point of the line
     */
    public Point end() {
        return end;
    }

    /**
     * Calculate and get the middle point(it is immutable object).
     *
     * @return the middle point of the line
     */
    public Point middle() {
        double middleX = (start.getX() + end.getX()) / 2;
        double middleY = (start.getY() + end.getY()) / 2;
        return new Point(middleX, middleY);
    }

    /**
     * Calculate and get the length of the line.
     *
     * @return the length of the line.(non-negative value)
     */
    public double length() {
        return start.distance(end);
    }

    /**
     * Check if x value is within the range of x values the line,
     * without meanings to start-end points order of the line.
     *
     * @param x value to be checked
     * @return true if min(start\end x values)<= x <= max(start\end x values)
     */
    public boolean isXInRange(double x) {
        boolean case1 =  MathUtil.isApproximatelyInRange(x, this.start().getX(), this.end().getX());
        boolean case2 =  MathUtil.isApproximatelyInRange(x, this.end().getX(), this.start().getX());
        return case1 || case2;
    }

    /**
     * Check if x value of the point is within the range of x values the line,
     * without meanings to start-end points order of the line.
     *
     * @param pt point that its x value to be checked
     * @return true if min(start\end x values)<= x <= max(start\end x values)
     */
    public boolean isXInRange(Point pt) {
        return isXInRange(pt.getX());
    }

    /**
     * Check if x values other line is within the range of x values the this line,
     * without meanings to start-end points order of the line.
     *
     * @param other line to be checked
     * @return true if min(start\end x values)<= at least one x from the other line <= max(start\end x values)
     */
    public boolean isXInRange(Line other) {
        return isXInRange(other.start()) || isXInRange(other.end());
    }

    /**
     * Check if y value is within the range of y values the line,
     * without meanings to start-end points order of the line.
     *
     * @param y value to be checked
     * @return true if min(start\end y values)<= y <= max(start\end y values)
     */
    public boolean isYInRange(double y) {
        boolean case1 =  MathUtil.isApproximatelyInRange(y, this.start().getY(), this.end().getY());
        boolean case2 =  MathUtil.isApproximatelyInRange(y, this.end().getY(), this.start().getY());
        return case1 || case2;
    }

    /**
     * Check if y value of the point is within the range of y values the line,
     * without meanings to start-end points order of the line.
     *
     * @param pt point that its y value to be checked
     * @return true if min(start\end y values)<= y <= max(start\end y values)
     */
    public boolean isYInRange(Point pt) {
        return isYInRange(pt.getY());
    }

    /**
     * Check if y values other line is within the range of y values the this line,
     * without meanings to start-end points order of the line.
     *
     * @param other line to be checked
     * @return true if min(start\end y values)<= at least one y from the other line <= max(start\end y values)
     */
    public boolean isYInRange(Line other) {
        return isYInRange(other.start()) || isYInRange(other.end());
    }

    /**
     * Calculate and get the gradient of the line,
     * the m in the math formula y = m * x + n .
     * Edge cases : the gradient is +-infinity, or NaN(if the line is a point).
     *
     * @return gradient of the line
     */
    public double getGradient() {
        //Even though in method equals() there is meaning to the start-end order, here it doesn't matter.
        return (start.getY() - end.getY()) / (start.getX() - end.getX());
    }

    /**
     * Check if the line is vertical <=> its math formula is x=constant.
     *
     * @return true if the line is vertical to the x axis
     */
    public boolean isVertical() {
        return Math.abs(this.getGradient()) == Double.POSITIVE_INFINITY;
    }

    /**
     * Calculate and get the n Constant Term of the line,
     * the n in the math formula y = m * x + n .
     * Edge cases : the line is vertical, or the line is a point.
     *
     * @return math-formula n Constant Term
     */
    public double nConstantTerm() {
        return this.start().getY() - this.getGradient() * this.start.getX();
    }

    /**
     * Check if point is on line.
     *
     * @param point to be checked with
     * @return true if the point is on line, false otherwise
     */
    public boolean isIntersecting(Point point) {
        return this.intersectionWith(point) != null;
    }

    /**
     * Check if two lines intersect- and if they intersect in ONE point.
     *
     * @param other line to be checked with
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        return this.intersectionWith(other) != null;
    }

    /**
     * Check if point is on line.
     *
     * @param point to be checked with
     * @return the point if the point is on line, null otherwise
     */
    public Point intersectionWith(Point point) {
        boolean isPtOnTheLine;
        // if this line is just a point, check if the 2 points are equal
        if (this.end().equals(this.start())) {
            isPtOnTheLine = this.end().equals(point);
            return isPtOnTheLine ? point : null;
        }
        // if this line is vertical check if the point is on it <=> same x value and in the range of the y valus
        if (this.isVertical()) {
            isPtOnTheLine =  MathUtil.isApproximatelyEqual(point.getX(), this.start().getX())
                             && this.isYInRange(point.getY());
            return isPtOnTheLine ? point : null;
        }
        /* otherwise, check if the point is on the line <=>
           Fulfill the equation y = m * x + n && in the range of this line  */
        double n = this.nConstantTerm();
        double m = this.getGradient();
        double x = point.getX();
        double y = point.getY();
        //if (y = m * x + n) && this.isXInRange(x) we can know for sure that this.isYInRange(y) == true
        isPtOnTheLine = MathUtil.isApproximatelyEqual(y, m * x + n) && this.isXInRange(x);
        return isPtOnTheLine ? point : null;
    }

    /**
     * Check if two lines intersect- and if they intersect in ONE point,
     * return that point, null otherwise.
     *
     * @param other line to be checked with
     * @return the point of the intersection IF there is, AND it's unic, null otherwise
     */
    public Point intersectionWith(Line other) {
        // I assume line is not considered being intersecting with itself[ even if this.start().equals(this.end()) ]
        if (this == other) {
            return null;
        }
        //if at least one of the line is point (start=end) check if it on the other line
        if (this.end().equals(this.start())) {
            return other.intersectionWith(this.start());
        }
        if (other.end().equals(other.start())) {
            return this.intersectionWith(other.start());
        }
        //if the 2 lines have the same gradient, check if they meet ONLY in 1 point- start/end
        if ((this.isVertical() && other.isVertical())
            || MathUtil.isApproximatelyEqual(this.getGradient(), other.getGradient())) {
            // case1 - this.start == other.start && other.end() not on 'this' line && this.end() not on 'other' line
            boolean case1 = this.start().equals(other.start()) && !this.isIntersecting(other.end())
                                                               && !other.isIntersecting(this.end());
            // case2 - this.start == other.end && other.start() not on 'this' line && this.end() not on 'other' line
            boolean case2 = this.start().equals(other.end()) && !this.isIntersecting(other.start())
                                                             && !other.isIntersecting(this.end());
            // case1 - this.end == other.start && other.end() not on 'this' line && this.start() not on 'other' line
            boolean case3 = this.end().equals(other.start()) && !this.isIntersecting(other.end())
                                                             && !other.isIntersecting(this.start());
            // case1 - this.end == other.end && other.start() not on 'this' line && this.start() not on 'other' line
            boolean case4 = this.end().equals(other.end()) && !this.isIntersecting(other.start())
                                                           && !other.isIntersecting(this.start());
            if (case1 || case2) {
                return this.start();
            }
            if (case3 || case4) {
                return this.end();
            }
            // if there is no one, and ONLY one point of intersection return null;
            return null;
        }
        /* if the (and we get here so only) the other line is vertical -
           call the method again in replacing other<->this just to short in code */
        if (other.isVertical()) {
            return other.intersectionWith(this);
        }
        //if only this line is vertical (formula x=constant) check if its x is on the other line and in the same range
        if (this.isVertical()) {
            double n = other.nConstantTerm();
            double m = other.getGradient();
            double x = this.start().getX();
            double intersectionY = m * x + n;
            /* check x value and y is in range [we know for sure that this.isXInRange(x) == true]
               and if other.isXInRange(x) than also other.isYInRange(intersectionY) */
            boolean isInRange = other.isXInRange(x) && this.isYInRange(intersectionY);
            return isInRange ? new Point(x, intersectionY) : null;
        }
        /* otherwise, check if the point that is on both lines <=>
           Fulfill the equation y = m * x + n , is in the range of the lines  */
        double n1 = this.nConstantTerm();
        double m1 = this.getGradient();
        double n2 = other.nConstantTerm();
        double m2 = other.getGradient();
        //math formula
        double intersectionX = (n1 - n2) / (m2 - m1);
        // y = f(x) = m * x + n  ==> f(x) = m1 * x + n1 = m2 * x +n2
        double intersectionY = m1 * intersectionX + n1;
        // check x value if it in the range ,and if yes -it means also that y value is in the range
        boolean isInRange = other.isXInRange(intersectionX) && this.isXInRange(intersectionX);
        return isInRange ? new Point(intersectionX, intersectionY) : null;
    }

    /**
     * Get the closest intersection point to the start of this line.
     * If this line does not intersect with the rectangle, returns null.
     *
     * @param rect Rectangle to be checked for intersection with
     * @return Point that is the closest to ther start of this line. null if there isn't.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> intersectionList = rect.intersectionPoints(this);
        return Point.getClosetPoint(intersectionList, this.start());
    }
}