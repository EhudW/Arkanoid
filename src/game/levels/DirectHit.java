package game.levels;

import java.util.List;
import java.awt.Color;
import game.axes.Rectangle;
import game.sprites.Background;
import game.sprites.Block;
import game.sprites.Sprite;
import game.sprites.Sight;

/** The class for level 'Direct Hit' - one hit to win the level. */
public class DirectHit extends AbstractLevel {
    /** Num of balls should be at each turn in this level. */
    private static final int NUM_OF_BALLS = 1;
    /** The given area to put the block within. */
    private Rectangle blocksArea;

    /**
     * Constructor. Get area rectangle to put the elements within.
     *
     * @param blocksArea where the level area is
     */
    public DirectHit(Rectangle blocksArea) {
        super(NUM_OF_BALLS, AbstractLevel.HIGH_PADDLE_SPEED, AbstractLevel.SHORT_PADDLE_WIDTH, "Direct Hit");
        this.blocksArea = blocksArea;
    }

    @Override
    public Sprite getBackground() {
        // return background of sight which its middle is the (only) middle of the one block of this level
        return new Background(Color.BLACK, new Sight(this.blocks().get(0).getCollisionRectangle().getMiddlePoint()));
    }

    @Override
    public List<Block> blocks() {
        // Only one block in color red
        String[] levelBlocks = new String[] {"---------------------",
                                             "----------R----------",
                                             "---------------------",
                                             "---------------------",
                                             "---------------------",
                                             "---------------------",
                                             "---------------------",
                                             "---------------------",
                                             "---------------------",
                                             "---------------------",
                                             "---------------------",
                                             "---------------------",
                                             "---------------------",
                                             "---------------------" };
        return AbstractLevel.getBlocksFromStrings(levelBlocks, blocksArea);
    }

}