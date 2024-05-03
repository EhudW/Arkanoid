/**
 * @author [Ehud Wasserman] [ID *********]
 */


import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import biuoop.GUI;
import game.GameFlow;
import game.axes.Counter;
import game.axes.Point;
import game.axes.Rectangle;
import game.levels.LevelInformation;
import game.levels.DirectHit;
import game.levels.FinalFour;
import game.levels.Green3;
import game.levels.WideEasy;

/**
 * The class create new game of arkanoid.
 */

public class Arkanoid {

    /**
     * Method to launch the game.
     * If there are command-line arguments ,
     * these are the levels to run in the accepted order or other settings.
     * levels that aren't in the range of levels [1-4] are ignored.
     * if NO VALID[OR NOT AT ALL] arguments sent, levels 1,2,3,4 will be run.
     * The game start with 7 lives by default.
     * run with arg --help to full setting option.
     *
     * /@param args - batch args which will set the levels to run[in the given order] and other settings.
     */
    public static void main(String[] args) {
        // parse users cmd line args with default values, null=arg not mentioned(off)
        Map<String, String> userChoices = new HashMap<String, String>();
        userChoices.put("live", "7");   // key: lives or live    val: int
        userChoices.put("3d", null);    // key: 3d               val: null will be treated as false, otherwise true
        userChoices.put("lvl", "1234"); // key: levels or lvl    val: string
        userChoices.put("h", null);     // key: help or h or ?   val: null will be treated as false, otherwise true
        for (String str : args) {
            // remove leading char and turn to lower
            str = str.toLowerCase().replace("-", "").replace("/", "");
            String[] keyValue = str.split("=");
            String key = keyValue[0];
            // aliases
            key = key.replace("level", "lvl").replace("lvls", "lvl")
                    .replace("lives", "live").replace("?", "help").replace("help", "h");
            // parse "$key=$val" => value=$val, parse "$key" => value=$key
            String value = keyValue[keyValue.length - 1];
            userChoices.put(key, value);
        }
        if (userChoices.get("h") != null) {
            System.out.println("Arkanoid game");
            System.out.println("    made by Ehud Wasserman as part of oop course, Bar-Ilan University");
            System.out.println("    with adjustments later");
            System.out.println("    'biuoop-1.4.jar', 'build.xml' is not mine and might has copyrights to Bar-Ilan");
            System.out.println("    which therefore also affect 'Arkanoid.jar'");
            System.out.println("How to play");
            System.out.println("    hit the blocks with the balls until the level is clear");
            System.out.println("    move the paddle with ARROWS or 'WASD' to avoid the balls from falling down");
            System.out.println("    hit 'P' to pause, and SPACE to continue");
            System.out.println("    each level start with only enabling left/right but you can enable up/down with 'U'");
            System.out.println("Using ant with given 'build.xml'");
            System.out.println("    ant clean");
            System.out.println("    ant compile");
            System.out.println("    ant run [-Dargs=\"[--help] [--levels=(1|2|3|4)*] [--lives=7] [--3d]\"]");
            System.out.println("Running command:");
            System.out.println("    java -jar Arkanoid.jar [--help] [--levels=(1|2|3|4)*] [--lives=7] [--3d]");
            System.out.println("${args}:");
            System.out.println("    --help  --h  --?              show this msg");
            System.out.println("    --levels=12  --lvl=334        sequence of levels to be played [there are 1-4 lvls]");
            System.out.println("    --lives=10   --live=7         lives for the game, positive integer");
            System.out.println("    --3d                          3D view");
            return;
        }
        int borderThick = 30;
        int winWidth = 800;
        int winHeight = 600;
        int lives = Integer.parseInt(userChoices.get("live"));
        boolean is3D = userChoices.get("3d") != null;
        // create new gui
        GUI gui = new GUI("Arkanoid", winWidth, winHeight);
        GameFlow gameFlow = new GameFlow(gui, new Counter(), new Counter(lives), borderThick, is3D);

        // the blocks should be within the follow rectangle, because outside it there will be the border Blocks
        Rectangle rect = new Rectangle(new Point(borderThick, 2 * borderThick),
                                        winWidth - 2 * borderThick, winHeight - 2 * borderThick);

        // parse the args[] to decide which levels to run
        List<LevelInformation> lvlsInfo = new ArrayList<LevelInformation>();

        for (String lvl : userChoices.get("lvl").split("")) {
            switch (lvl) {
                case "1":
                        lvlsInfo.add(new DirectHit(rect));
                        break;
                case "2":
                        lvlsInfo.add(new WideEasy(rect));
                        break;
                case "3":
                        lvlsInfo.add(new Green3(rect));
                        break;
                case "4":
                        lvlsInfo.add(new FinalFour(rect));
                        break;
                // if it isn't "1"/"2"/"3"/"4" ignore the lvl
                default:
                        break;
            }
        }

        // if no valid arg OR no args at all, run 1-4 levels
        if (lvlsInfo.size() == 0) {
            lvlsInfo.add(new DirectHit(rect));
            lvlsInfo.add(new WideEasy(rect));
            lvlsInfo.add(new Green3(rect));
            lvlsInfo.add(new FinalFour(rect));
        }

        gameFlow.runLevels(lvlsInfo);
        // close the gui afterwards
        gui.close();
    }
}