package Main;

import Data.*;
import Data.AbstractClass.*;
import Data.AnimalFood.*;
import Data.Factory.*;
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

import static scriptingEngine.Interpreter.sceneNumber;

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
    public static final String[] tags64 = {"feedButton","cursor","sugarButton","pause64","carrotButton","play64","cornButton","cakeButton","cowFoodButton","menu64","chickenFoodButton","sheepFoodButton","butterfly2","butterfly3","butterfly1","close64","butterfly4","wheatButton","butterfly5"};
    public static final String[] tags128 = {"corn","wheat","carrot","crop","cow","sheep","chicken","sugarcane","sugarcaneButton","strawberryButton","strawberry","box"};
    public static final String[] tags256 = {"my_hud"};
    public static final String[] tags512 = {"factory","barn"};
    public static final String[] tag1024 = {"conveyor"};

    public static Inventory inventory;

    private static Interpreter interpreter;
    public static AnimatedText animatedText;

    public static AnimatedText cowFoodReceipt;
    public static AnimatedText chickenFoodReceipt;
    public static AnimatedText sheepFoodReceipt;
    private static Balance balance;


    private static final int STEP = 15;
    // End Static fields...

    public static void main(String[] args)
    {
        //inventory = new Inventory();
        //inventory.addProductToInventory("wheat", 6);
        //inventory.addProductToInventory("carrot", 3);
        //inventory.addProductToInventory("strawberry", 4);
        //Factory factory = new Factory();
        //factory.addProductToQueue(new CowFood());
        Control ctrl = new Control();                // Do NOT remove!
        ctrl.gameLoop();// Do NOT remove!
    }

    /* This is your access to things BEFORE the game loop starts */
    public static void start(Control ctrl)
    {
        Item cowFood = new CowFood();
        Item chickenFood = new ChickenFood();
        Item sheepFood = new SheepFood();
        // ctrl.hideDefaultCursor();
        interpreter = new Interpreter();
        inventory = new Inventory();
        balance = new Balance();
        inventory.addProductToInventory("wheat", 10);
        inventory.addProductToInventory("carrot", 10);
        inventory.addProductToInventory("corn", 10);
        generateSpriteFromSpriteSheet(ctrl);
        String cowFoodStr = cowFood.getReceipt().getIngredients().toString();
        String chickenFoodStr = chickenFood.getReceipt().getIngredients().toString();
        String sheepFoodStr = sheepFood.getReceipt().getIngredients().toString();

        cowFoodReceipt = new AnimatedText("cow food - material needed: " + cowFoodStr.substring(1, cowFoodStr.length() - 1), 100);
        chickenFoodReceipt = new AnimatedText("chicken food - material needed: " + chickenFoodStr.substring(1, chickenFoodStr.length() - 1), 100);
        sheepFoodReceipt = new AnimatedText("sheep food - material needed: " + sheepFoodStr.substring(1,sheepFoodStr.length() - 1), 100);

        butterFly = new ButterFly(-50, 0, 1970, 1080, 25, 60, 30);
        Factory factory = new Factory();
    }

    /* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
    public static void update(Control ctrl)
    {
        interpreter.processCommands(ctrl);
        //Point p = Mouse.getMouseCoordinates();
        //ctrl.addSpriteToOverlayBuffer(p.x, p.y, new Sprite(p.x, p.y, sprites.get("cursor"), "cursor"));
        ctrl.addSpriteToHudBuffer(new Sprite(20,20, sprites.get("my_hud"), "mu_hud"));
        ctrl.drawHudString(40, 50, "Balance: " + balance.toString(), Color.YELLOW);
        ctrl.drawString(650,150, inventory.toString(), Color.YELLOW);
        String text = cowFoodReceipt.getCurrentStr();
        String text1 = chickenFoodReceipt.getCurrentStr();
        String text2 = sheepFoodReceipt.getCurrentStr();
        if (sceneNumber == 0)
        {
            ctrl.drawString(577,216, text, Color.YELLOW);
            ctrl.drawString(577, 266, text1, Color.YELLOW);
            ctrl.drawString(577, 316, text2, Color.YELLOW);
        }

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