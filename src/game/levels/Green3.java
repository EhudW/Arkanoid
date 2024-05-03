package game.levels;

import java.util.List;
import java.awt.Color;
import game.axes.Rectangle;
import game.sprites.Background;
import game.sprites.Block;
import game.sprites.Sprite;
import game.sprites.Tower;

/** The class for level 'Green 3' - level with background of tower. */
public class Green3 extends AbstractLevel {
    /** Num of balls should be at each turn in this level. */
    private static final int NUM_OF_BALLS = 2;
    /** The given area to put the block within. */
    private Rectangle blocksArea;

    /**
     * Constructor. Get area rectangle to put the elements within.
     *
     * @param blocksArea where the level area is
     */
    public Green3(Rectangle blocksArea) {
        super(NUM_OF_BALLS, AbstractLevel.HIGH_PADDLE_SPEED, AbstractLevel.SHORT_PADDLE_WIDTH, "Green 3");
        this.blocksArea = blocksArea;
    }

    @Override
    public Sprite getBackground() {
        // put background of Green and tower in front
        return new Background(Color.GREEN.darker().darker(), new Tower());
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
                                             "----ggggggggggg",
                                             "-----RRRRRRRRRR",
                                             "------YYYYYYYYY",
                                             "-------BBBBBBBB",
                                             "--------WWWWWWW",
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