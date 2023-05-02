package Main;

import Data.*;
import Data.Frame;
import Input.*;
import logic.Control;
import particles.*;
import scriptingEngine.*;
import sounds.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class Main
{
    // Fields (Static) below...
    public static Sound sfx = new Sound("Sound/funny_death.wav");
    public static Sound music = new Sound("Sound/persephone_farewell.wav");

    public static Sprite canvas;
    public static ButterFly butterFly;
    public static Sprite particleSprite;

    public static HashMap<String, BufferedImage> sprites = new HashMap<>();
    public static final String[] tags32 = {"menu", "pause", "play", "close"};
    public static final String[] tags64 = {"feedButton","cursor","sugarButton","Pause64","carrotButton","Play64","cornButton","cakeButton","cowFoodButton","menu64","chickenFoodButton","sheepFoodButton","butterfly2","butterfly3","butterfly1","close64","butterfly4","wheatButton","butterfly5"};
    public static final String[] tags128 = {"corn","wheat","carrot","crop","chicken","sheep","cow","sugarcane","sugarcaneButton","strawberryButton","strawberry","box"};
    public static final String[] tags256 = {"my_hud"};
    public static final String[] tags512 = {"factory","barn"};
    public static final String[] tag1024 = {"conveyor"};

    private static Interpreter interpreter;
    public static AnimatedText animatedText;
    private static final int STEP = 15;
    // End Static fields...

    public static void main(String[] args)
    {
        Control ctrl = new Control();                // Do NOT remove!
        ctrl.gameLoop();// Do NOT remove!
    }

    /* This is your access to things BEFORE the game loop starts */
    public static void start(Control ctrl)
    {
        // TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite or drawString)
        //
        // song.setLoop();
        ctrl.hideDefaultCursor();
        interpreter = new Interpreter();

        generateSpriteFromSpriteSheet(ctrl);
        animatedText = new AnimatedText("Hello World, this is a test", 100);
        butterFly = new ButterFly(-50, 0, 1970, 1080, 25, 60, 30);
    }

    /* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
    public static void update(Control ctrl)
    {
        interpreter.processCommands(ctrl);
        Point p = Mouse.getMouseCoordinates();
        ctrl.addSpriteToOverlayBuffer(p.x, p.y, new Sprite(p.x, p.y, sprites.get("cursor"), "cursor"));
        //ctrl.addSpriteToFrontBuffer(new Sprite(200,200, sprites.get("my_hud"), "mu_hud"));
        //ctrl.addSpriteToFrontBuffer(new Sprite(0,500, sprites.get("conveyor"), "conveyor"));

        String text = animatedText.getCurrentStr();
        //ctrl.drawString(100,100, text, Color.WHITE);

    }

    private static void generateSpriteFromSpriteSheet(Control ctrl)
    {
        BufferedImage spriteSheet = ctrl.getSpriteFromBackBuffer("spriteSheet").getSprite();
        getSpritesFromSpriteSheet(tags32, spriteSheet, 0, 5,32,32);
        getSpritesFromSpriteSheet(tags64, spriteSheet, 50, 6,64,64);
        getSpritesFromSpriteSheet(tags128, spriteSheet, 150, 7,128,128);
        getSpritesFromSpriteSheet(tags256, spriteSheet, 300, 8,256,256);
        getSpritesFromSpriteSheet(tags512, spriteSheet, 600, 9, 512, 512);
        getSpritesFromSpriteSheet(tag1024, spriteSheet, 1150, 10, 1024,512);
    }
    private static void getSpritesFromSpriteSheet(String[] tags, BufferedImage spriteSheet, int yCoordinate, int exponent, int width, int height)
    {
        for(int i = 0; i < tags.length; i++)
        {
            int x = i << exponent;
            BufferedImage image = spriteSheet.getSubimage(x, yCoordinate, width, height);
            sprites.put(tags[i], image);
        }
    }
}
