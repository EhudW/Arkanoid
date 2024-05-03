package game.levels;

import java.util.List;
import java.awt.Color;
import game.axes.Rectangle;
import game.sprites.Background;
import game.sprites.Block;
import game.sprites.Sprite;
import game.sprites.Sun;

/** The class for level 'Wide Easy' - level that suppose to be easy, but no so much. */
public class WideEasy extends AbstractLevel {
    /** Num of balls should be at each turn in this level. */
    private static final int NUM_OF_BALLS = 10;
    /** The given area to put the block within. */
    private Rectangle blocksArea;

    /**
     * Constructor. Get area rectangle to put the elements within.
     *
     * @param blocksArea where the level area is
     */
    public WideEasy(Rectangle blocksArea) {
        super(NUM_OF_BALLS, AbstractLevel.LOW_PADDLE_SPEED, AbstractLevel.LONG_PADDLE_WIDTH, "Wide Easy");
        this.blocksArea = blocksArea;
    }

    @Override
    public Sprite getBackground() {
        // put sun with its rays at the background
        return new Background(Color.WHITE, new Sun());
    }

    @Override
    public List<Block> blocks() {
        String[] levelBlocks = new String[] {"---------------",
                                             "---------------",
                                             "---------------",
                                             "---------------",
                                             "---------------",
                                             "---------------",
                                             "---------------",
                                             "---------------",
                                             "---------------",
                                             "---------------",
                                             "RROOYYGGGBBPPCC",
                                             "---------------",
                                             "---------------",
                                             "---------------",
                                             "---------------",
                                             "---------------",
                                             "---------------",
                                             "---------------",
                                             "---------------",
                                             "---------------",
                                             "---------------",
                                             "---------------",
                                             "---------------",
                                             "---------------" };
        return AbstractLevel.getBlocksFromStrings(levelBlocks, blocksArea);
    }

}