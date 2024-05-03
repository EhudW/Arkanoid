package game.sprites;

import java.awt.Color;
import biuoop.DrawSurface;
import game.animation.GameLevel;
import game.axes.Rectangle;

/** The class for draw tower on DrawSurface. Use only on one drawsurface.
 *  [meaning one defenitoin of hegiht x width]. */
public class Tower implements Sprite {

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
        int width = surface.getWidth();
        int height = surface.getHeight();

        // draw building with its windows

        int xUpperLeftTower = 150;
        int yUpperLeftTower = height - 200;
        int towerWidth = 170;
        int towerHeight = 200;

        // draw the building
        surface.setColor(Color.BLACK);
        surface.fillRectangle(xUpperLeftTower, yUpperLeftTower, towerWidth, towerHeight);

        // draw the windows [we will have same num of row & cols]
        int windowsNum = 5;
        // the sum of the windows should be half of the tower width
        int windowWidth = towerWidth / (windowsNum * 2);
        // the sum of the windows should be 2/3 of the tower width
        int windowHeight = (int) (towerHeight / (windowsNum * 1.5));
        int heightSpaceBetweenWindows = (towerHeight - (windowsNum + 1) * windowHeight) / windowsNum;
        int widthSpaceBetweenWindows = (towerWidth - (windowsNum + 1) * windowWidth) / windowsNum;

        surface.setColor(Color.WHITE);
        //we have same num of row & cols
        for (int i = 1; i <= windowsNum; i++) {
            for (int j = 1; j <= windowsNum; j++) {
                int horizitionallIndent = j * widthSpaceBetweenWindows + (j - 1) * windowWidth;
                int verticalIndent = i * heightSpaceBetweenWindows + (i - 1) * windowHeight;
                int x = xUpperLeftTower + horizitionallIndent;
                int y = yUpperLeftTower + verticalIndent;
                surface.fillRectangle(x, y, windowWidth, windowHeight);
            }
        }

        int xMiddleOfTower = xUpperLeftTower + towerWidth / 2;

        // draw the antenna base
        int antennaBaseWidth = 30;
        int antennaBaseHeight = 50;
        int antennaBaseLeftX = xMiddleOfTower - antennaBaseWidth / 2;
        int antennaBaseUpY = yUpperLeftTower - antennaBaseHeight;
        surface.setColor(Color.GRAY.darker());
        surface.fillRectangle(antennaBaseLeftX, antennaBaseUpY, antennaBaseWidth, antennaBaseHeight);

        // draw the antenna
        int antennaWidth = 10;
        int antennaHeight = 150;
        int antennaLeftX = xMiddleOfTower - antennaWidth / 2;;
        int antennaUpY = antennaBaseUpY - antennaHeight;
        surface.setColor(Color.GRAY.darker());
        surface.fillRectangle(antennaLeftX, antennaUpY, antennaWidth, antennaHeight);

        // draw light at the edge of the antenna
        int outerLightSize = 10;
        int middleLightSize = 6;
        int innerLightSize = 2;
        surface.setColor(Color.ORANGE);
        surface.fillCircle(xMiddleOfTower, antennaUpY, outerLightSize);

        surface.setColor(Color.RED);
        surface.fillCircle(xMiddleOfTower, antennaUpY, middleLightSize);

        surface.setColor(Color.WHITE);
        surface.fillCircle(xMiddleOfTower, antennaUpY, innerLightSize);

    }

    @Override
    public void timePassed() {
        // do nothing
    }

}