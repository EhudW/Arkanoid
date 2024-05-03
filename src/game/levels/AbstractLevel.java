package game.levels;

import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import game.axes.Point;
import game.axes.Rectangle;
import game.axes.Velocity;
import game.sprites.Block;
import game.sprites.Sprite;

/** The abstract class of LevelInformation to make level quick and easy. */
public abstract class AbstractLevel implements LevelInformation {

    /** Const of high paddle speed. */
    public static final int HIGH_PADDLE_SPEED = 12;
    /** Const of low paddle speed. */
    public static final int LOW_PADDLE_SPEED = 3;

    /** Const of long paddle width. */
    public static final int LONG_PADDLE_WIDTH = 500;
    /** Const of short paddle width. */
    public static final int SHORT_PADDLE_WIDTH = 100;

    /** How much balls should be in the level. */
    private int ballsNum;
    /** How fast should the paddle be. */
    private int paddleSpeed;
    /** How long should the paddle be. */
    private int paddleWidth;
    /** What is the level 'name'. */
    private String levelName;

    /**
     * Constructor with the 4 basic proprties of the level.
     * (There are more, but the aren't basic).
     *
     * @param ballsNum How much balls should be in the level.
     * @param paddleSpeed How fast should the paddle be.
     * @param paddleWidth How long should the paddle be.
     * @param levelName What is the level 'name' String.
     */
    public AbstractLevel(int ballsNum, int paddleSpeed, int paddleWidth, String levelName) {
        this.ballsNum = ballsNum;
        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
        this.levelName = levelName;
    }

    @Override
    public int numberOfBalls() {
        return this.ballsNum;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        // default method - divide the arc to the angle of the balls velocities
        List<Velocity> velocities = new ArrayList<Velocity>();
        // default 'normal' speed of ball = 5
        int ballSpeedSize = 5;
        double angleFromLeftToRight = (Velocity.RIGHT_ANGLE - Velocity.LEFT_ANGLE + 360) % 360;
        // divide to numberOfBalls() + 1 because in this way the arc dividing will be symmetrical
        double anglePerBall = angleFromLeftToRight / (this.numberOfBalls() + 1);
        // start from left direction and for each ball go a little to the right degree
        double currVelocityAngle = Velocity.LEFT_ANGLE;
        for (int i = 1; i <= this.numberOfBalls(); i++) {
            currVelocityAngle += anglePerBall;
            velocities.add(Velocity.fromAngleAndSpeed(currVelocityAngle, ballSpeedSize));
        }
        return velocities;
    }

     @Override
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    @Override
    public int paddleWidth() {
        return this.paddleWidth;
    }

    @Override
    public String levelName() {
        return this.levelName;
    }


    // the sub class should implement:
    @Override
    public abstract Sprite getBackground();

    // the sub class should implement:
    @Override
    public abstract List<Block> blocks();

    @Override
    public int numberOfBlocksToRemove() {
        // not efficient!!!!!!!!!!!!!!
        return this.blocks().size();
    }

    /**
     * Useful method which get String array and Rectangle of area,
     * and returns List of blocks according to the String array in the given rectangle.
     * Each char in each String represnt block color :  B=BLUE C=CYAN G=GREEN
     *                          g=gray O=ORANGE  P=PINK R=RED W=WHITE Y=YELLOW
     * If another char is given it won't cause block to be there, but it will occupied the place, for space,
     * Meaning {"Y","OxY", ""} will divide the area rectangle height to 3 equal part,
     *      in the first row it put YELLOW block from side to side,
     *      in the second row it will put in the left third ORANGE block,
     *                                    in the right third YELLOW block,
     *                                    and in the middle there will be space (third of the rectangle width)
     *      in the third row nothing will be, but if we didn't put it in the array, then the sum of the two
     *                                    previous rows will be the rectangle height, while now they only 2/3
     * [Block width/height may have inaccurte values]
     *
     * @param strArr represetion of the blocks, colors, and spaces
     * @param blocksArea where to put the blocks and to divide the blocksArea according to the given strArr
     * @return List of blocks according to the strArr & blocksArea
     */
    protected static List<Block> getBlocksFromStrings(String[] strArr, Rectangle blocksArea) {
        return getBlocksFromStrings(strArr, blocksArea.getUpperLeft(), blocksArea.getWidth(), blocksArea.getHeight());
    }

    /**
     * Useful method which get String array and Rectangle of area(startPoint, totalWidth x totalHeight),
     * and returns List of blocks according to the String array in the given rectangle.
     * [Block width/height may have inaccurte values]
     *
     * @param strArr represetion of the blocks, colors, and spaces
     * @param startPoint start point where to put the blocks according to the given strArr
     * @param totalWidth to put the blocks according to the given strArr from the startPoint
     * @param totalHeight to put the blocks according to the given strArr from the startPoint
     * @return List of blocks according to the strArr & blocksArea
     */
    private static List<Block> getBlocksFromStrings(String[] strArr, Point startPoint,
                                                           double totalWidth, double totalHeight) {
        List<Block> list = new ArrayList<Block>();
        if (strArr == null) {
            return list;
        }
        // round down
        double heightPerBlock = (int) (totalHeight / strArr.length);
        // get the reminder and give it to the first row
        double firstBlockHeight = heightPerBlock + totalHeight - heightPerBlock * strArr.length;
        Point currPoint = startPoint;
        // add the blocks for each row we will divide the areaRectangle (according to strArr.length)
        for (int i = 0; i < strArr.length; i++) {
            double heightForCurrBlock = i == 0 ? firstBlockHeight : heightPerBlock;
            // add the blocks in the curr row
            list.addAll(getBlocksFromString(strArr[i], currPoint, totalWidth, heightForCurrBlock));
            currPoint = new Point(currPoint.getX(), currPoint.getY() + heightForCurrBlock);
        }
        return list;
    }

    /**
     * Useful method which get String ,startPoint, totalWidth , heightPerBlock,
     * and returns List of blocks according to the String which represnts row.
     * [Block width may have inaccurte values]
     *
     * @param str represetion of the blocks, colors, and spaces
     * @param startPoint start point where to put the blocks according to the given str
     * @param totalWidth to put the blocks according to the given str from the startPoint
     * @param heightPerBlock height of each block in the row
     * @return List of blocks according to the str & startPoint & totalWidth
     */
    private static List<Block> getBlocksFromString(String str, Point startPoint,
                                                        double totalWidth, double heightPerBlock) {
        List<Block> list = new ArrayList<Block>();
        if (str == null) {
            return list;
        }
        // round down
        double widthPerBlock = (int) (totalWidth / str.length());
        // get the reminder and give it to the first(left) block (/space)
        double firstBlockWidth = widthPerBlock + totalWidth - widthPerBlock * str.length();
        Point currPoint = startPoint;
        // go over the columns in the row and add the blocks/ spaces
        for (int i = 0; i < str.length(); i++) {
            double widthForCurrBlock = i == 0 ? firstBlockWidth : widthPerBlock;
            Block block = getBlockFromChar(str.charAt(i), currPoint, widthForCurrBlock, heightPerBlock);
            if (block != null) {
                list.add(block);
            }
            // even if we didn't add null ('space block') we still go to the next column and leave space behind
            currPoint = new Point(currPoint.getX() + widthForCurrBlock, currPoint.getY());
        }
        return list;
    }

    /** Get block in a given upper left point & width & height,
     *  in the given color represetion in the charColor.
     *  B=BLUE C=CYAN G=GREEN g=gray O=ORANGE P=PINK R=RED W=WHITE Y=YELLOW
     *  If another char is given null will be returned.
     *
     * @param charColor char which represents Color of block
     * @param pt the upper left of the new block
     * @param width the width of the new block
     * @param height the height of the new block
     * @return new Block according to charColor, pt, width, height;
     *         OR null if the there isn't such color represetion in char [not in "BCGgOPRWY"]
     */
    private static Block getBlockFromChar(char charColor, Point pt, double width, double height) {
        Color color = null;
        switch (charColor) {
            case 'B':
                    color = Color.BLUE;
                    break;
            case 'C':
                    color = Color.CYAN;
                    break;
            case 'G':
                    color = Color.GREEN;
                    break;
            case 'g':
                    color = Color.GRAY;
                    break;
            case 'O':
                    color = Color.ORANGE;
                    break;
            case 'P':
                    color = Color.PINK;
                    break;
            case 'R':
                    color = Color.RED;
                    break;
            case 'W':
                    color = Color.WHITE;
                    break;
            case 'Y':
                    color = Color.YELLOW;
                    break;
            default :
                    return null;
        }
        Rectangle rect = new Rectangle(pt, width, height);
        return new Block(rect, color);
    }
}