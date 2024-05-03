/**
 * @author [Ehud Wasserman] [ID *********]
 */

package game.sprites;

import java.util.Random;

import java.awt.Color;
import biuoop.DrawSurface;
import game.axes.Rectangle;

 /**
  * The class offers some useful functions deal with colors and color in the GUI.
  */
public class ColorsUtil {

    public static final int SHADOW_THICKNESS = 3;
    public static final int SIGNIFICANT_SEEN_RADIUS = 2;

    /**
     * The method check if two colors are similar and return true\false.
     *
     * @param color1 for the checking.
     * @param color2 for the checking.
     * @return if the 2 colors are similar.
     */
    public static boolean isSimilarColor(Color color1, Color color2) {
        //even if both colors are 'null' return they not similar since we didn't have something to compare
        if (color1 == null || color2 == null) {
            return false;
        }
        int smallDifference = 35;
        boolean redProblem = Math.abs(color1.getRed() - color2.getRed()) <= smallDifference;
        boolean greenProblem = Math.abs(color1.getGreen() - color2.getGreen()) <= smallDifference;
        boolean blueProblem = Math.abs(color1.getBlue() - color2.getBlue()) <= smallDifference;
        // only if all the components of the colors are similiar retrun the colors similar
        return redProblem && greenProblem && blueProblem;
    }

    /**
     * The method check if there is color in the array which similar to given color.
     * It doesn't check if within the array 2 colors are similar.
     *
     * @param colors array for the checking.
     * @param color to check if similar to 1 of the colors in the array.
     * @return if there is color in the array which similar to given color.
     */
    public static boolean isThereSimilarColor(Color[] colors, Color color) {
        if (colors == null) {
            return false;
        }
        for (int i = 0; i < colors.length; i++) {
            if (isSimilarColor(colors[i], color)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The method give random color.
     *
     * @return random color.
     */
    public static Color getRandomColor() {
        Random rand = new Random();
        return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    /**
     * The method give random color, which is not similar to the color within the sent array of colors.
     * In order to avoid infinty loop after several try in any case a random color will be sent.
     * [It doesn't check if within the array 2 colors are similar.]
     *
     * @param avoidColors array for avoid similarty in the random color checking.
     * @return random color and tries that it will be different from the colors in the colors array.
     */
    public static Color getRandomColor(Color[] avoidColors) {
        int enoughToTry = 1000;
        Color color = getRandomColor();
        for (int counter = 0; isThereSimilarColor(avoidColors, color) && counter < enoughToTry; counter++) {
            color = getRandomColor();
        }
        return color;
    }

    /**
     * The method draw(fill) flat rectangle on a given DrawSurface.
     *
     * @param surface to draw on it
     * @param rect Rectangle to draw
     * @param color of the rectangle
     * @param hasBorders if to add black borders
     */
    public static void drawFlatRectangle(DrawSurface surface, Rectangle rect, Color color, boolean hasBorders) {
        // check the given color isn't transparent
        if (color.getTransparency() != Color.OPAQUE) {
            return;
        }

        int startX = (int) rect.getUpperLeft().getX();
        int startY = (int) rect.getUpperLeft().getY();
        int width = (int) rect.getWidth();
        int height = (int) rect.getHeight();
        // fill the rectangle
        surface.setColor(color);
        surface.fillRectangle(startX, startY, width, height);
        //draw its borders
        if (hasBorders) {
            surface.setColor(Color.BLACK);
            surface.drawRectangle(startX, startY, width, height);
        }
    }

    /**
     * The method draw(fill) '3D' rectangle on a given DrawSurface.
     * If the Rectangle is to small for 3D it will be drawn as flat rectangle(with borders).
     *
     * @param surface to draw on it
     * @param rect Rectangle to draw
     * @param color of the rectangle
     * @param isLeftRightChopped if the left-right edges should have gradation of color (shadow)
     */
    public static void draw3dRectangle(DrawSurface surface, Rectangle rect, Color color, boolean isLeftRightChopped) {
        // check the given color isn't transparent
        if (color.getTransparency() != Color.OPAQUE) {
            return;
        }
        int startX = (int) rect.getUpperLeft().getX();
        int startY = (int) rect.getUpperLeft().getY();
        int width = (int) rect.getWidth();
        int height = (int) rect.getHeight();
        // check the rectangle is not to small, if it is, draw it like flat rectangle
        if (width < 3 * SHADOW_THICKNESS || height < 3 * SHADOW_THICKNESS) {
            drawFlatRectangle(surface, rect, color, true);
            return;
        }
        // if the given color is similar to black work with darker gray color that will look the same for '3d'
        color = isSimilarColor(Color.BLACK, color) ? Color.GRAY.darker().darker().darker() : color;

        // draw first layer of flat rectangle of low level shadow
        drawFlatRectangle(surface, rect, color.darker(), false);
        // decide if left-right edges should have gradation of color (shadow
        int xShadowShifting = isLeftRightChopped ? 0 : ColorsUtil.SHADOW_THICKNESS;
        // draw the high-level shadow at the right-up corner and remain the low-level at the left-down corner
        surface.setColor(color.darker().darker());
        surface.fillRectangle(startX + xShadowShifting, startY , width - xShadowShifting, height - SHADOW_THICKNESS);
        // draw the higher level of the rectangle as brighter
        surface.setColor(color.brighter());
        surface.fillRectangle(startX + xShadowShifting , startY + SHADOW_THICKNESS,
                                width - 2 * xShadowShifting, height - 2 * SHADOW_THICKNESS);
        // add black border
        surface.setColor(Color.BLACK);
        surface.drawRectangle(startX, startY, width, height);
    }

    /**
     * The method draw(fill) 'ball' : '3D' or flat(with borders), on a given DrawSurface.
     *
     * @param surface to draw on it
     * @param x -x value of the center of the ball
     * @param y -y value of the center of the ball
     * @param radius -the radius of the ball
     * @param color of the ball
     * @param is3D whether to draw the ball as '3D' or as flat
     */
    public static void drawBall(DrawSurface surface, double x, double y, double radius, Color color, boolean is3D) {
         if (!is3D) {
            // draw the [flat]ball
            surface.setColor(color);
            surface.fillCircle((int) x, (int) y, (int) radius);
            // draw border
            surface.setColor(Color.BLACK);
            surface.drawCircle((int) x, (int) y, (int) radius);
        } else {
            // draw the flat ball
            surface.setColor(color);
            surface.fillCircle((int) x, (int) y, (int) radius);
            // draw 'shadow' smaller ball whose center is just right-up
            surface.setColor(color.darker().darker());
            surface.fillCircle((int) (x + radius / 6), (int) (y  - radius / 6), (int) (0.75 * radius));
            // draw again the original color to cover the places we don't want as shadow
            surface.setColor(color);
            surface.fillCircle((int) x, (int) y, (int) (0.775 * radius));
            // draw white point down-left, according to the radius size, which give sensation of lighting
            surface.setColor(Color.WHITE);
            surface.fillCircle((int) (x - 0.5 * radius), (int) (y  + 0.5 * radius), (int) (radius / 20 + 1));
            // draw black border
            surface.setColor(Color.BLACK);
            surface.drawCircle((int) x, (int) y, (int) radius);
        }

    }

    /**
     * The method draw(fill) 'backgroundColor' on a given DrawSurface,
     * with the abillity to add 'effects' which are lines with point at the
     * end of them, looks like rain / falling star.
     *
     * @param surface to draw on it
     * @param backgroundColor to fill the surface
     * @param hasEffects whether to draw the 'effects' on the surface
     */
    public static void drawBackground(DrawSurface surface, Color backgroundColor, boolean hasEffects) {
        // fill the surface with the backgroundColor
        int width = surface.getWidth();
        int height = surface.getHeight();
        surface.setColor(backgroundColor);
        surface.fillRectangle(0, 0, width, height);
        if (!hasEffects) {
            return;
        }
        // set the color that will be seen good (white or gray if the backgroundColor similar to white)
        if (!ColorsUtil.isSimilarColor(Color.WHITE, backgroundColor)) {
            surface.setColor(Color.WHITE);
        } else {
            surface.setColor(Color.GRAY);
        }
        // how many effects=lines in a row / col
        int effectsInRowOrCol = 15;
        // what they width / height
        int widthPerEffect = width / effectsInRowOrCol;
        int heightPerEffect = height / effectsInRowOrCol;
        for (int i = 0; i < effectsInRowOrCol; i++) {
            for (int j = 0; j < effectsInRowOrCol; j++) {
                // if it is odd index make shifting / indention so the lines won't be continued in the next row
                int shiftingX = (i % 2 == 0) ? 0 : widthPerEffect / 2;
                //get the line start-end values according to the j[current col] and i[current row]
                int xStartOfTheLine = j * widthPerEffect + shiftingX;
                int yStartOfTheLine = i * heightPerEffect;
                int xEndOfTheLine = xStartOfTheLine + widthPerEffect;
                int yEndOfTheLine = yStartOfTheLine + heightPerEffect;
                // draw a line and point on its end
                surface.drawLine(xStartOfTheLine, yStartOfTheLine, xEndOfTheLine, yEndOfTheLine);
                surface.fillCircle(xEndOfTheLine, yEndOfTheLine, SIGNIFICANT_SEEN_RADIUS);
            }
        }
    }

}