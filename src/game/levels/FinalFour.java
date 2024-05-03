package game.levels;

import java.util.List;
import java.awt.Color;
import game.axes.Rectangle;
import game.sprites.Background;
import game.sprites.Block;
import game.sprites.Sprite;
import game.sprites.Cloud;

/** The class for level 'Final Four' - level with 6 rows of blocks. */
public class FinalFour extends AbstractLevel {
    /** Num of balls should be at each turn in this level. */
    private static final int NUM_OF_BALLS = 3;
    /** The given area to put the block within. */
    private Rectangle blocksArea;

    /**
     * Constructor. Get area rectangle to put the elements within.
     *
     * @param blocksArea where the level area is
     */
    public FinalFour(Rectangle blocksArea) {
        super(NUM_OF_BALLS, AbstractLevel.HIGH_PADDLE_SPEED, AbstractLevel.SHORT_PADDLE_WIDTH, "Final Four");
        this.blocksArea = blocksArea;
    }

    @Override
    public Sprite getBackground() {
        Color delicateBlueColor = new Color(80, 160, 255);
        // return background with 2 (raining) clouds
        return new Background(delicateBlueColor, new Cloud(4));
    }

    @Override
    public List<Block> blocks() {
        String[] levelBlocks = new String[] {"---------------",
                                             "---------------",
                                             "---------------",
                                             "ggggggggggggggg",
                                             "RRRRRRRRRRRRRRR",
                                             "YYYYYYYYYYYYYYY",
                                             "GGGGGGGGGGGGGGG",
                                             "WWWWWWWWWWWWWWW",
                                             "PPPPPPPPPPPPPPP",
                                             "CCCCCCCCCCCCCCC",
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
                                             "---------------",
                                             "---------------",
                                             "---------------",
                                             "---------------" };
        return AbstractLevel.getBlocksFromStrings(levelBlocks, blocksArea);
    }

}