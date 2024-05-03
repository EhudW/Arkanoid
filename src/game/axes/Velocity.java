/**
 * @author [Ehud Wasserman] [ID *********]
 */

package game.axes;

/**
 * Velocity specifies the change in position on the `x` and the `y` axes.[immutable object].
 *  The class operations - constructor(x3), fromAngleAndSpeed,
 *                         getDx, getDy, getAngle, getSize,
 *                         plus, minus, multiply, lower, min,
 *                         applyToPoint, applyToRectangle, getLineFrom .
 */
public class Velocity {

    /** Constant reference for zero velocity. */
    public static final Velocity ZERO = new Velocity(0, 0);
    /** Constant angle for up direction in the gui. */
    public static final double UP_ANGLE = 0;
    /** Constant angle for right direction in the gui. */
    public static final double RIGHT_ANGLE = 90;
    /** Constant angle for down direction in the gui. */
    public static final double DOWN_ANGLE = 180;
    /** Constant angle for left direction in the gui. */
    public static final double LEFT_ANGLE = 270;

    /** The dx-component of the velocity. */
    private double dx;
    /** The dy-component of the velocity. */
    private double dy;

    /**
     * Constructor which create velocity instance[immutable] from dx & dy.
     * Small dx\dy will be avoid and count as 0 [dx\dy].
     *
     * @param dx ,the dx-component of the velocity.
     * @param dy ,the dy-component of the velocity.
     */
    public Velocity(double dx, double dy) {
        // avoid small dx/dy component
        this.dx = Math.abs(dx) > MathUtil.BIG_EPSILON ? dx : 0;
        this.dy = Math.abs(dy) > MathUtil.BIG_EPSILON ? dy : 0;
    }

    /**
     * Constructor which create velocity instance from start point to the end point.
     * Small dx\dy will be avoid and count as 0 [dx\dy].
     *
     * @param start ,the start point
     * @param end ,the end point that will be if we applyToPoint the returned velocity
     */
    public Velocity(Point start, Point end) {
        this(end.getX() - start.getX(), end.getY() - start.getY());
    }

    /**
     * Constructor which create velocity instance from start of the line to the end of the line.
     * Small dx\dy will be avoid and count as 0 [dx\dy].
     *
     * @param line which its start/end point is the size&angle of the velocity
     */
    public Velocity(Line line) {
        this(line.start(), line.end());
    }

    /**
     * Method(Constructor cannot overloaded with double,double),
     * which returns velocity instance[immutable] from angle and speed.
     * The angle is between negetaive y axe=>up and right direction in the screen is 45 degrees.
     *
     * @param angle ,the speed direction[in degrees].
     * @param speed ,the speed size.
     * @return suitable velocity from the speed & direction.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        // ignore the k * 360 addition(k natural number)
        angle = angle % 360;
        if (angle < 0) {
            angle += 360;
        }
        // calc the dx/dy component abs sizes
        double dx = Math.abs(Math.sin(Math.PI * angle / 180)) * speed;
        double dy = Math.abs(Math.cos(Math.PI * angle / 180)) * speed;
        // decide if they are negative
        if (angle >= 180) {
            dx = -dx;
        }
        if ((0 <= angle && angle <= 90) || (270 <= angle && angle <= 360)) {
            dy = -dy;
        }
        return new Velocity(dx, dy);
    }

    /**
     * Get the dx-component of the velocity.
     *
     * @return the dx-component of the velocity.
     */
    public double getDx() {
        return dx;
    }

    /**
     * Get the dy-component of the velocity.
     *
     * @return the dy-component of the velocity.
     */
    public double getDy() {
        return dy;
    }

    /**
     * Get the angle of the velocity.
     *
     * @return double the angle of the velocity
     */
    public double getAngle() {
        double angle =  -1 * Math.atan(dx / dy) * 180 / Math.PI;
        angle = dy < 0 ? angle : angle + 180;
        return (angle + 360) % 360;
    }

    /**
     * Get the size of the velocity.
     *
     * @return double size of the velocity
     */
    public double getSize() {
        return (new Point(0, 0)).distance(new Point(dx, dy));
    }

    /**
     * Get new velocity which is the addition of this velocity to other velocity.
     *
     * @param other velocity to be added
     * @return velocity of the addition
     */
    public Velocity plus(Velocity other) {
        return new Velocity(this.dx + other.dx, this.dy + other.dy);
    }

    /**
     * Get new velocity which is the subtraction of other velocity from this velocity.
     *
     * @param other velocity to sub from this velocity
     * @return velocity of the subtraction
     */
    public Velocity minus(Velocity other) {
        return new Velocity(this.dx - other.dx, this.dy - other.dy);
    }

    /**
     * Get new velocity which is the multiplication of scalar with this velocity.
     *
     * @param scalar to multuply this velocity (its size)
     * @return velocity of the multiplication
     */
    public Velocity multiply(double scalar) {
        return new Velocity(this.dx * scalar, this.dy * scalar);
    }

    /**
     * Get new velocity that is lower by a little bit from this velocity.
     *
     * @return velocity that a little bit lower
     */
    public Velocity lower() {
        double size = this.getSize();
        /* reduce the size by BIG_EPSILON which is for saving distance between sprites
           but make sure reducing BIG_EPSILON gives a positive size */
        size = Math.max(0, size - MathUtil.BIG_EPSILON);
        if (size == 0) {
            return Velocity.ZERO;
        }
        // return new velocity with the same angle but lower size
        double angle = this.getAngle();
        return Velocity.fromAngleAndSpeed(angle, size);
    }

    /**
     * Get the minimum between 2 velocity.
     * The minimum is defined by the size, not by velocity angle.
     * 'null' velocity as param will cause to get the other param.
     *
     * @param v1 first velocity to be checked
     * @param v2 second velocity to be checked
     * @return the velocity that have the minimum size
     */
    public static Velocity min(Velocity v1, Velocity v2) {
        if (v1 == null) {
            return v2;
        }
        if (v2 == null) {
            return v1;
        }
        if (v1.getSize() > v2.getSize()) {
            return v2;
        }
        return v1;
    }

    /**
     * Get the point after the velocity was operate on it.
     *
     * @param p point of the current place before adding the velocity
     * @return point of the next place.
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + dx, p.getY() + dy);
    }

    /**
     * Get the Rectangle after the velocity was operate on it.
     *
     * @param rect Rectangle in the current place before adding the velocity
     * @return Rectangle of the next place, after the velocity was applied on it
     */
    public Rectangle applyToRectangle(Rectangle rect) {
        return new Rectangle(this.applyToPoint(rect.getUpperLeft()), rect.getWidth(), rect.getHeight());
    }

    /**
     * Get line from start point to the point that will be,
     * after applying this velocity on the start point.
     *
     * @param start point to start the line
     * @return Line that we get after applying this velocity
     */
    public Line getLineFrom(Point start) {
        return new Line(start, this.applyToPoint(start));
    }
}