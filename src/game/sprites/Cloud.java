package game.sprites;

import java.util.Random;
import java.awt.Color;
import biuoop.DrawSurface;
import game.animation.GameLevel;
import game.axes.Rectangle;
import game.axes.Point;

/** The class for draw cloud(s) on DrawSurface. Use only on one drawsurface.
 *  [meaning one defenitoin of hegiht x width]. */
public class Cloud implements Sprite {

    /** Number of parts['simple' cloud] each cloud contains. */
    private static final int CLOUD_PARTS_NUM = 7;
    /** Max size of parts['simple' cloud] each cloud contains. */
    private static final int CLOUD_PARTS_MAX_SIZE = 25;
    /** Min size of parts['simple' cloud] each cloud contains. */
    private static final int CLOUD_PARTS_MIN_SIZE = 17;
    /** The 'part' of cloud = the simple clouds. */
    private Ball[][] simpleClouds;
    /** The surface width.*/
    private int width;
    /** The surface height.*/
    private int height;

    /**
     * Constructor which get how many clouds to paint.
     *
     * @param numOfClouds num of clouds to draw
     */
    public Cloud(int numOfClouds) {
        this.simpleClouds = new Ball[numOfClouds][];
    }

    @Override
    public void addToGameLevel(GameLevel g) {
        g.addSprite(this);
    }

    @Override
    public void removeFromGameLevel(GameLevel g) {
        g.removeSprite(this);
    }

    @Override
    public Rectangle getSpriteRectangle() {
        return Sprite.UNREACHABLE_RECT;
    }

    @Override
    public void drawOn(DrawSurface surface) {
        // if this is the first time we draw the clouds, we need to
        // decide their locations, and set this.width & this.height
        if (simpleClouds.length > 0 && simpleClouds[0] == null) {
            this.height = surface.getHeight();
            this.width = surface.getWidth();
            int yBottomOfScreen = 5 * this.height / 6;
            // divide the width to regions
            int regions = 2 * simpleClouds.length;
            int widthPerRegion = this.width / regions;
            // start at the end of the first regions
            int currRegionX = widthPerRegion;

            // populate the each complex cloud in its region
            for (int i = 0; i < simpleClouds.length; i++) {

                // each complex cloud will include CLOUD_PARTS_NUM simple clouds
                this.simpleClouds[i] = new Ball[CLOUD_PARTS_NUM];
                // they will start from color white (and will become darker)
                Color cloudColor = Color.WHITE;
                // remember the previous radius of the simple cloud in the current complex cloud
                int prevRadius = 0;
                int sumOfPrevRadius = 0;

                // simpleClouds[i].length == CLOUD_PARTS_NUM
                for (int j = 0; j < CLOUD_PARTS_NUM; j++) {
                    // get random size for the simple cloud in range
                    // [CLOUD_PARTS_MIN_SIZE : CLOUD_PARTS_MAX_SIZE]
                    Random rand = new Random();
                    int radius = rand.nextInt(CLOUD_PARTS_MAX_SIZE - CLOUD_PARTS_MIN_SIZE + 1) + CLOUD_PARTS_MIN_SIZE;
                    // sum the radius till now
                    sumOfPrevRadius += prevRadius;
                    //if j even the simple cloud will be placed hight
                    int y = j % 2 == 0 ? yBottomOfScreen - radius / 2 : yBottomOfScreen + radius / 2;
                    prevRadius = radius;
                    Point center = new Point(currRegionX + sumOfPrevRadius / 2, y);

                    this.simpleClouds[i][j] = new Ball(center, radius, cloudColor);
                    this.simpleClouds[i][j].set3D(false);
                    // make color darker [white->gray->black] every 4 simple clouds
                    cloudColor = j % 4  == 3 ? cloudColor.darker() : cloudColor;
                }

                // go to the next complex cloud and the next region
                currRegionX += 2 * widthPerRegion;
            }
        }

        // draw rain for each simple cloud
        for (Ball[] simpleCloudArr : this.simpleClouds) {
            for (Ball simpleCloud : simpleCloudArr) {
                    surface.setColor(Color.WHITE);
                    int startRainX = (int) (simpleCloud.getX() - simpleCloud.getSize());
                    int endRainX = (int) (simpleCloud.getX() + simpleCloud.getSize());
                    int spaceBetweenRain = 6;
                    int y = (int) simpleCloud.getY();
                    // we want the rain the fall in angle of arctan(1.5/4) = 20
                    int xDiff = (int) (-1.5 * CLOUD_PARTS_MAX_SIZE);
                    int yDiff = 4 * CLOUD_PARTS_MAX_SIZE;
                    // draw some rain for each simpleCloud
                    for (int x = startRainX; x < endRainX; x += spaceBetweenRain) {
                        surface.drawLine(x, y, x + xDiff, y + yDiff);
                    }
            }
        }

        // draw all the simple clouds which will make the complex clouds
        // we draw them on the rain
        for (Ball[] simpleCloudArr : this.simpleClouds) {
            for (Ball simpleCloud : simpleCloudArr) {
                simpleCloud.drawOn(surface);
            }
        }
    }

    @Override
    public void timePassed() {
        // do nothing
    }

}