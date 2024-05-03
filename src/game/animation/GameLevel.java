/**
 * @author [Ehud Wasserman] [ID *********]
 */
package game.animation;

import java.util.List;
import java.awt.Color;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import game.levels.LevelInformation;

import game.axes.Counter;
import game.axes.Point;
import game.axes.Rectangle;
import game.axes.Velocity;

import game.listeners.BallRemover;
import game.listeners.BlockRemover;
import game.listeners.ScoreTrackingListener;

import game.sprites.Ball;
import game.sprites.Block;
import game.sprites.Collidable;
import game.sprites.GameEnvironment;
import game.sprites.Paddle;
import game.sprites.ScoreIndicator;
import game.sprites.Sprite;
import game.sprites.SpriteCollection;

/**
 * The class for making new instance of an arkanoid game. should be called initialize() before playOneTurn().
 * shouldStop() & doOneFrame() for inner use only.(use only playOneTurn() outside this class)
 */
public class GameLevel implements Animation {

    /** The AnimationRunner which is important in order to remain in the same framesPerSeconds. */
    private AnimationRunner runner;
    /** Flag wheter the animation yet need to be run. */
    private boolean running;

    /** Points that are added to user score for each hit Block. */
    public static final int SCORE_PER_BLOCK = ScoreTrackingListener.SCORE_PER_BLOCK;
    /** Point that are added to user score after pass the level. */
    public static final int SCORE_PER_LEVEL = 100;

    /** Width of the gui window. */
    private int winWidth;
    /** Height of the gui window. */
    private int winHeight;
    /** The gui of this game level. */
    private GUI gui;
    /** The KeyboardSensor of the gui. */
    private KeyboardSensor keyboard;

    /** The thick of the border blocks. */
    private int borderThick;
    /** The paddle height in this game level. */
    private int paddleHeight;
    /** The reference for the current paddle. */
    private Paddle paddle;

    /** The level information which determines the properties of this game level. */
    private LevelInformation levelInfo;

    /** The collection of the sprites in the game level. */
    private SpriteCollection sprites;
    /** The collection of the collidables in the game level. */
    private GameEnvironment environment;
    /** The counter of the remaing Vulnerable(not border blocks etc.) Blocks. */
    private Counter remainedVulnerableBlocks;
    /** The counter of the remaing Balls in the game level. */
    private Counter remainedBalls;

    /** The user score. should passed by constructor and continues all over the game.(all the games level). */
    private Counter score;
    /** The user lives. should passed by constructor and continues all over the game.(all the games level). */
    private Counter lives;

    /** Property if the game is '3D'. */
    private boolean is3D;

    /**
     * Constructor with adjustable values.
     *
     * @param runner The AnimationRunner which runs this game level, and can also run other things.
     * @param gui which this game level occurs within.
     * @param levelInfo the information to initialize the game level
     * @param score the current score of the user in the WHOLE GAME
     * @param lives the current 'lives' of the user in the WHOLE GAME
     * @param borderThick The thick of the border blocks.
     * @param is3D wheter the game is 3D
     */
    public GameLevel(AnimationRunner runner, GUI gui, LevelInformation levelInfo, Counter score,
                                                    Counter lives, int borderThick, boolean is3D) {
        this.runner = runner;
        this.gui = gui;
        this.levelInfo = levelInfo;
        this.score = score;
        this.lives = lives;
        this.borderThick = borderThick;
        this.is3D = is3D;

        this.keyboard = this.gui.getKeyboardSensor();
        this.winWidth = gui.getDrawSurface().getWidth();
        this.winHeight = gui.getDrawSurface().getHeight();
        // default value
        this.paddleHeight = 15;
    }

    /**
     * Get the gameLevel gui.
     *
     * @return GUI of this GameLevel
     */
    public GUI getGUI() {
        return gui;
    }

    /**
     * Get the collection of the sprites in the game level.
     *
     * @return SpriteCollection of this game level
     */
    public SpriteCollection getSpirteCollection() {
        return sprites;
    }

    /**
     * Get the environment(collidables collection) of this game level.
     *
     * @return GameEnvironment of this game level
     */
    public GameEnvironment getEnvironment() {
        return environment;
    }

    /**
     * Add sprite to this game level sprite collection.
     * The sprite isn't be added to something else(like collidable collection).
     *
     * @param s sprite to be added
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Add collidable to this game level collidable collection.
     * The collidable isn't be added to something else(like sprite collection).
     *
     * @param c collidable to be added
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Remove sprite from this game level sprite collection.
     * The sprite isn't be removed from something else(like collidable collection).
     *
     * @param s sprite to be be removed from this game level
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Remove collidable from this game level collidable collection.
     * The collidable isn't be removed something else(like sprite collection).
     *
     * @param c collidable to be removed from the game level
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Initialize a new game level: create the Blocks, and add them to the game level,
     * (add to gameEnvironment, spriteCollection, add listeners to them)
     * according to the given gui & levelInfo given in the constructor.
     * Paddle & balls will be added at playOneTurn() -> createBallsAndPaddle()
     */
    public void initialize() {

        this.remainedBalls = new Counter();
        this.remainedVulnerableBlocks = new Counter(levelInfo.numberOfBlocksToRemove());

        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();

        // the first sprite will be drawn at back, so add the background of the levelInfo
        levelInfo.getBackground().addToGameLevel(this);

        // add sign of the current score & lives & level name
        // However, we will add it to game at last in order it will be drawn at the top
        // scoreIndicator.addToGameLevel(this); ---> at the end of this method
        Rectangle scoreIndicatorRect = new Rectangle(new Point(0, 0), this.winWidth, this.borderThick);
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.lives, this.score, this.levelInfo.levelName(),
                                                                                            scoreIndicatorRect);

        // add the border blocks which thickness is according to this.borderThick
        Rectangle leftBorderBlockRect = new Rectangle(new Point(0, 0), this.borderThick, this.winHeight);
        Rectangle rightBorderBlockRect = new Rectangle(new Point(this.winWidth - this.borderThick, 0),
                                                                        this.borderThick, this.winHeight);
        // upBorderBlockRect is double thickness because the upper half will be for scoreIndicator
        Rectangle upBorderBlockRect = new Rectangle(new Point(0, 0), this.winWidth, this.borderThick * 2);
        /* downBorderBlockRect start at this.winHeight because we want user think the ball is 'fall' from the screen,
           and won't see the bottom block ('region death') */
        Rectangle downBorderBlockRect = new Rectangle(new Point(0, this.winHeight), this.winWidth, this.borderThick);

        Rectangle[] borderBlocksRects = new Rectangle[]{leftBorderBlockRect, rightBorderBlockRect,
                                                    upBorderBlockRect, downBorderBlockRect};
        // add the border block to the this game level
        for (int i = 0; i < borderBlocksRects.length; i++) {
            Block block = new Block(borderBlocksRects[i], Color.GRAY);
            // if it's the last rect, the bottom block, add BallRemover as listener
            if (i == borderBlocksRects.length - 1) {
                block.addHitListener(new BallRemover(this, this.remainedBalls));
            }
            block.set3D(this.is3D);
            block.addToGameLevel(this);
        }

        // create new listener which will remove block from this game and update the counter of remained blocks
        BlockRemover blockRemover = new BlockRemover(this, this.remainedVulnerableBlocks);
        // create new listener which will track and update the user score
        ScoreTrackingListener scoreTracking = new ScoreTrackingListener(this.score);

        // add to the VulnerableBlocks the listeners
        List<Block> blockList = this.levelInfo.blocks();
        for (Block block : blockList) {
            block.set3D(this.is3D);
                block.addToGameLevel(this);
                // add Score Tracking Listener as listener to that block
                block.addHitListener(scoreTracking);
                // add block remover as listener to that block
                block.addHitListener(blockRemover);
        }

        // we are adding to the the game at last the scoreIndicator because we want it will be draw at the top layer
        scoreIndicator.addToGameLevel(this);
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.sprites.drawAllOn(d);
        // be prepare for the next frame[/move]
        this.sprites.notifyAllTimePassed();

        // if the user press 'p' pause the game until he will press space bar
        if (this.keyboard.isPressed("p") || this.keyboard.isPressed("P")) {
            Animation animation = new PauseScreen();
            animation = new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY, animation);
            this.runner.run(animation);
        }

        // if all the blocks removed by hitting, add SCORE_PER_LEVEL(=100) points to the score and stop the level
        if (this.remainedVulnerableBlocks.getValue() == 0) {
            this.score.increase(SCORE_PER_LEVEL);
            this.running = false;
        }

        // end the program when all the block was removed OR all balls where removed  and stop the level
        if (this.remainedBalls.getValue() == 0) {
            this.lives.decrease(1);
            this.running = false;
        }
    }

    /**
     * Method for playOneTurn. Each turn will end if the lives will be 0,
     * or there will be no more block. Therefor, who calls playOneTurn()
     * should check the lives counter and this.remainedBlocks()
     * Turn = new amount of balls according the level info that was given in the constructor.
     */
    public void playOneTurn() {
        // create paddle at the middle at the bottom, and new balls
        this.createBallsAndPaddle();
        this.running = true;
        // show countdown animation (2 seconds count from 3)
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        // use our runner to run the current animation -- which is one turn of
        // the game.
        this.runner.run(this);
    }

    /**
     * Add new paddle to this game level, instead of the old one,
     * in the bottom of the screen in the middle.
     */
    private void replacePaddle() {
        // remove the old paddle from this game level
        if (this.paddle != null) {
            this.paddle.removeFromGameLevel(this);
        }
        int middleOfScreenX = this.winWidth / 2;
        // leave space (5 pixel) between the paddle and the border block
        double paddleY = this.winHeight - this.paddleHeight - 5;
        // add paddle to the game at the middle of the width, and bottom of height
        Point paddleStartPt = new Point(middleOfScreenX - this.levelInfo.paddleWidth() / 2, paddleY);
        Rectangle paddleRect = new Rectangle(paddleStartPt, this.levelInfo.paddleWidth(), this.paddleHeight);
        this.paddle =  new Paddle(paddleRect , Color.ORANGE, this.keyboard, this.levelInfo.paddleSpeed());
        paddle.set3D(this.is3D);
        paddle.addToGameLevel(this);
    }

    /**
     * Create new balls, according to the amount & velocities from levelInfo,
     * and replace the old paddle with new at the bottom at the middle of the gui.
     */
    public void createBallsAndPaddle() {
        //replace the current paddle with another paddle which will be at bottom at middle
        replacePaddle();

        // put the balls just little higher than the paddle
        Point center = this.paddle.getCollisionRectangle().getUpperLine().middle();
        center = new Velocity(0, -3).applyToPoint(center);

        // add the balls to this game level with their velocities from the levelInfo
        int ballSize = 5;
        Color ballColor = Color.WHITE;
        for (int i = 0; i < this.levelInfo.numberOfBalls(); i++) {
            new Ball(center, ballSize, ballColor, this.levelInfo.initialBallVelocities().get(i)).addToGameLevel(this);
            this.remainedBalls.increase(1);
        }
    }

    /**
     * Get how much blocks the user should hit to 'win' this level game.
     *
     * @return int of how much blocks should be hit before the user pass this level game.
     */
    public int remainedBlocks() {
        return this.remainedVulnerableBlocks.getValue();
    }

}