package scriptingEngine;

import Data.*;
import Data.AbstractClass.*;
import Data.Animal.*;
import Data.AnimalFood.*;
import Data.Crops.*;
import Data.Factory.*;
import Data.Frame;
import FileIO.*;
import Input.*;
import Main.*;
import logic.*;
import particles.*;
import sounds.*;

import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.util.List;

import static Data.Factory.Factory.currentProduct;
import static Main.Main.*;

public class Interpreter
{
    private static final int dropShadow = 2;
    public static int sceneNumber = Integer.MIN_VALUE;
    public static Sound song;
    public static String string = "";
    private static Factory factory;
    private static Timer field1Timer;
    private static Timer field2Timer;
    private static List<RECT> dayRECTAndButton;
    private static List<RECT> factoryRECTAndButton;
    private static List<RECT> barnRECTAndButton;
    private static List<RECT> allRECTButton;
    private static List<SpriteInfo> dayImage;
    private static List<SpriteInfo> factoryImage;
    private static List<SpriteInfo> barnImage;
    private static List<SpriteInfo> all;
    private static boolean field1ReadyForPlant;
    private static boolean field2ReadyForPlant;
    private static RECT r;
    private static Animation loopingAnimation;
    private static String tag;
    private static String animalTag;
    private static Cow cows = new Cow();
    private static Chicken chickens = new Chicken();
    private static Sheep sheeps = new Sheep();
    private EZFileRead reader;
    private HashMap<String, SpriteInfo> animal;
    private List<String> backgroundTagList = new ArrayList<>();

    public Interpreter()
    {
        reader = new EZFileRead("script.txt");
        factory = new Factory();

        dayRECTAndButton = new ArrayList<>();
        factoryRECTAndButton = new ArrayList<>();
        barnRECTAndButton = new ArrayList<>();
        allRECTButton = new ArrayList<>();

        dayImage = new ArrayList<>();
        factoryImage = new ArrayList<>();
        barnImage = new ArrayList<>();
        animal = new HashMap<>();
        all = new ArrayList<>();

        field1ReadyForPlant = true;
        field2ReadyForPlant = true;
        setupInterpreter();
    }

    public void processCommands(Control ctrl)
    {
        renderBackgroundImage(ctrl);
        renderAnimation(ctrl);
        factory.Production();
        cows.checkTime();
        chickens.checkTime();
        sheeps.checkTime();
    }

    private void renderBackgroundImage(Control ctrl)
    {
        if (sceneNumber == Integer.MIN_VALUE)
        {
            processDayButton(ctrl);
            processTextHover(dayRECTAndButton.get(0), ctrl);
            processTextHover(dayRECTAndButton.get(1), ctrl);
            processTextHover(dayRECTAndButton.get(2), ctrl);
            processTextHover(dayRECTAndButton.get(3), ctrl);
            ctrl.addSpriteToFrontBuffer(0, 0, "day");
            ctrl.addSpriteToFrontBuffer(new Sprite(630, 900, sprites.get(tag), tag));
            for (SpriteInfo info : dayImage)
            {
                ctrl.addSpriteToFrontBuffer(new Sprite(info.x, info.y, sprites.get(info.tag), info.tag));
            }
        } else if (sceneNumber == 0)
        {
            String tag = backgroundTagList.get(0);
            ctrl.addSpriteToFrontBuffer(0, 0, tag);
            ctrl.drawString(577, 316, "Number of item in the production queue: " + factory.getProductionQueueSize(), Color.YELLOW);
            ctrl.drawString(577, 366, "currently producing: " + currentProduct, Color.YELLOW);
            processFactoryButton();
            processTextHover(factoryRECTAndButton.get(0), ctrl);
            processTextHover(factoryRECTAndButton.get(1), ctrl);
            for (SpriteInfo info : factoryImage)
            {
                ctrl.addSpriteToFrontBuffer(new Sprite(info.x, info.y, sprites.get(info.tag), info.tag));
            }
        } else if (sceneNumber == 1)
        {
            String tag = backgroundTagList.get(1);
            ctrl.addSpriteToFrontBuffer(0, 0, tag);
            processBarnButton(ctrl);
            processTextHover(barnRECTAndButton.get(0), ctrl);
            for (SpriteInfo info : barnImage)
            {
                if (!(info.tag.equalsIgnoreCase("cow") || info.tag.equalsIgnoreCase("chicken") || info.tag.equalsIgnoreCase("sheep")))
                {
                    ctrl.addSpriteToFrontBuffer(new Sprite(info.x, info.y, sprites.get(info.tag), info.tag));
                }
            }
            SpriteInfo info = animal.get(animalTag);
            int x = info.x;
            int y = info.y;
            for (int i = 0; i < 5; i++)
            {
                if (i != 0)
                {
                    x += 138;
                }
                ctrl.addSpriteToFrontBuffer(new Sprite(x, y, sprites.get(info.tag), info.tag));
            }
        }

        processAllButton();
        processGraphicalHover(allRECTButton.get(0), ctrl);
        processGraphicalHover(allRECTButton.get(1), ctrl);
        processGraphicalHover(allRECTButton.get(2), ctrl);

        for (int i = 0; i < 3; i++)
        {
            SpriteInfo info = all.get(i);
            ctrl.addSpriteToFrontBuffer(new Sprite(info.x, info.y, sprites.get(info.tag), info.tag));
        }
    }

    private void renderAnimation(Control ctrl)
    {
        if (sceneNumber == 0)
        {
            Frame frame = loopingAnimation.getCurrentFrame();
            if (frame != null)
            {
                ctrl.addSpriteToFrontBuffer(new Sprite(frame.getX(), frame.getY(), sprites.get("box"), "box"));
            }
        }
    }

    public void processDayButton(Control ctrl)
    {
        Point p = Mouse.getMouseCoordinates();
        int x = (int) p.getX();
        int y = (int) p.getY();
        for (RECT button : dayRECTAndButton)
        {
            if (Control.getMouseInput() != null)
            {
                if (button.isClicked(Control.getMouseInput(), Click.LEFT_BUTTON))
                {
                    if (button.getTag().equalsIgnoreCase("factory"))
                    {
                        sceneNumber = 0;
                    } else if (button.getTag().equalsIgnoreCase("cow") || button.getTag().equalsIgnoreCase("chicken") || button.getTag().equalsIgnoreCase("sheep"))
                    {
                        sceneNumber = 1;
                        switch (button.getTag())
                        {
                            case "cow":
                                animalTag = "cow";
                                break;
                            case "chicken":
                                animalTag = "chicken";
                                break;
                            case "sheep":
                                animalTag = "sheep";
                                break;
                        }
                    } else
                    {
                        String temp = button.getTag();

                        if (temp.equalsIgnoreCase("corn") && field1ReadyForPlant)
                        {
                            /*field1ReadyForPlant = false;
                            Crop crop = new Corn();
                            {
                            }*/
                            tag = "corn";
                        } else if (temp.equalsIgnoreCase("wheat") && field1ReadyForPlant)
                        {
                            tag = "wheat";
                        } else if (temp.equalsIgnoreCase("carrot") && field1ReadyForPlant)
                        {
                            tag = "carrot";
                        } else if (temp.equalsIgnoreCase("sugarcane") && field2ReadyForPlant)
                        {
                            tag = "sugarcane";
                        } else if (temp.equalsIgnoreCase("strawberry") && field2ReadyForPlant)
                        {
                            tag = "strawberry";
                        }
                    }
                }
            }
        }
    }

    private void processBarnButton(Control ctrl)
    {
        for (RECT button : barnRECTAndButton)
        {
            if (Control.getMouseInput() != null)
            {
                if (button.isClicked(Control.getMouseInput(), Click.LEFT_BUTTON))
                {
                    if (button.getTag().equalsIgnoreCase("exit"))
                    {
                        sceneNumber = Integer.MIN_VALUE;
                    }

                    else if (button.getTag().equalsIgnoreCase("feed"))
                    {
                        if (animalTag.equalsIgnoreCase("cow"))
                        {
                            if (inventory.productInInventory("cowFood"))
                            {
                                cows.feedAnimal();
                                inventory.decreaseProductQuantity("cowFood", 1);
                            }
                        }

                        else if (animalTag.equalsIgnoreCase("chicken"))
                        {
                            if (inventory.productInInventory("chickenFood"))
                            {
                                chickens.feedAnimal();
                                inventory.decreaseProductQuantity("chickenFood", 1);
                            }
                        }

                        else if (animalTag.equalsIgnoreCase("sheep"))
                        {
                            if (inventory.productInInventory("sheepFood"))
                            {
                                sheeps.feedAnimal();
                                inventory.decreaseProductQuantity("sheepFood", 1);
                            }
                        }
                    }
                }
            }
        }
    }

    private void processFactoryButton()
    {
        for (RECT button : factoryRECTAndButton)
        {
            if (Control.getMouseInput() != null)
            {
                if (button.isClicked(Control.getMouseInput(), Click.LEFT_BUTTON))
                {
                    if (button.getTag().equalsIgnoreCase("exit"))
                    {
                        sceneNumber = Integer.MIN_VALUE;
                    }

                    else if (button.getTag().equalsIgnoreCase("cowFood"))
                    {
                        Item cowFood = new CowFood();
                        factory.addProductToQueue(cowFood);
                    }

                    else if (button.getTag().equalsIgnoreCase("chickenFood"))
                    {
                        Item chickenFood = new ChickenFood();
                        factory.addProductToQueue(chickenFood);
                    }

                    else if (button.getTag().equalsIgnoreCase("sheepFood"))
                    {
                        Item sheepFood = new SheepFood();
                        factory.addProductToQueue(sheepFood);
                    }

                    else if (button.getTag().equalsIgnoreCase("reset"))
                    {
                        cowFoodReceipt.resetAnimation();
                        chickenFoodReceipt.resetAnimation();
                        sheepFoodReceipt.resetAnimation();
                    }
                }
            }
        }
    }

    private void processAllButton()
    {
        for (RECT button : allRECTButton)
        {
            if (Control.getMouseInput() != null)
            {
                if (button.isClicked(Control.getMouseInput(), Click.LEFT_BUTTON))
                {
                    if (button.getTag().equalsIgnoreCase("playButton"))
                    {
                        if (!song.isPlaying())
                        {
                            song.setLoop();
                        }
                    } else if (button.getTag().equalsIgnoreCase("pauseButton"))
                    {
                        if (song.isPlaying())
                        {
                            song.pauseWAV();
                        }
                    } else if (button.getTag().equalsIgnoreCase("closeButton"))
                    {
                        System.exit(0);
                    }
                }
            }
        }
    }

    private void createRECT(Command command)
    {
        int x1 = Integer.parseInt(command.getParametersByIndex(0));
        int y1 = Integer.parseInt(command.getParametersByIndex(1));
        int x2 = Integer.parseInt(command.getParametersByIndex(2));
        int y2 = Integer.parseInt(command.getParametersByIndex(3));
        String tag = command.getParametersByIndex(4);
        if (command.isCommand("createClickableRECT") && command.getNumberOfParameters() == 6)
        {
            r = new RECT(x1, y1, x2, y2, tag);
            String string = command.getParametersByIndex(5);
            addRECtToList(string);
        } else if (command.isCommand("createRECTText") && command.getNumberOfParameters() == 7)
        {
            String hoverLabel = command.getParametersByIndex(5);
            r = new RECT(x1, y1, x2, y2, tag, hoverLabel);
            String string = command.getParametersByIndex(6);
            addRECtToList(string);
        } else if (command.isCommand("createRECTGraphical") && command.getNumberOfParameters() == 8)
        {
            int frameComponent1 = Integer.parseInt(command.getParametersByIndex(5));
            int frameComponent2 = Integer.parseInt(command.getParametersByIndex(6));
            String frameComponent3 = command.getParametersByIndex(7);
            r = new RECT(x1, y1, x2, y2, tag, new Frame(frameComponent1, frameComponent2, frameComponent3));
            allRECTButton.add(r);
        }
    }

    private void addRECtToList(String string)
    {
        switch (string)
        {
            case "day":
                dayRECTAndButton.add(r);
                break;
            case "factory":
                factoryRECTAndButton.add(r);
                break;
            case "barn":
                barnRECTAndButton.add(r);
                break;
        }
    }

    private void setupInterpreter()
    {
        for (int i = 0; i < reader.getNumLines(); i++)
        {
            String raw = reader.getLine(i);
            if (!raw.trim().equals(""))
            {
                if (!(raw.charAt(0) == '#'))
                {
                    Command command = new Command(raw);
                    if (command.isCommand("bg") && (!command.getParametersByIndex(0).equalsIgnoreCase("day")))
                    {
                        backgroundTagList.add(command.getParametersByIndex(0));
                    } else if (command.isCommand("show") && command.getNumberOfParameters() == 4)
                    {
                        addImageToList(command);
                    } else if (command.isCommand("playMusic") && command.getNumberOfParameters() == 1)
                    {
                        song = new Sound("Sound/" + command.getParametersByIndex(0));
                        song.setLoop();
                    } else if (command.isCommand("animation") && command.getNumberOfParameters() == 4)
                    {
                        loopingAnimation = new Animation(Integer.parseInt(command.getParametersByIndex(0)), Boolean.parseBoolean(command.getParametersByIndex(1)));
                        int step = Integer.parseInt(command.getParametersByIndex(2));
                        int yCoordinate = Integer.parseInt(command.getParametersByIndex(3));
                        for (int x = 1028; x > 473; x -= step)
                        {
                            loopingAnimation.addFrame(new Frame(x, yCoordinate, "cursor"));
                        }
                    } else if ((command.isCommand("createRECTGraphical") || command.isCommand("createRECTText") || command.isCommand("createClickableRECT")))
                    {
                        createRECT(command);
                    }
                }
            }
        }
    }

    private void addImageToList(Command command)
    {
        int x = Integer.parseInt(command.getParametersByIndex(1));
        int y = Integer.parseInt(command.getParametersByIndex(2));
        SpriteInfo spriteInfo = new SpriteInfo(x, y, command.getParametersByIndex(3));
        if (command.getParametersByIndex(0).equalsIgnoreCase("day"))
        {
            dayImage.add(spriteInfo);
        } else if (command.getParametersByIndex(0).equalsIgnoreCase("factory"))
        {
            factoryImage.add(spriteInfo);
        } else if (command.getParametersByIndex(0).equalsIgnoreCase("barn"))
        {
            if (spriteInfo.tag.equalsIgnoreCase("cow") || spriteInfo.tag.equalsIgnoreCase("sheep") || spriteInfo.tag.equalsIgnoreCase("chicken"))
            {
                animal.put(spriteInfo.tag, spriteInfo);
            }
            barnImage.add(spriteInfo);
        } else if (command.getParametersByIndex(0).equalsIgnoreCase("all"))
        {
            all.add(spriteInfo);
        }
    }

    private void processTextHover(RECT r, Control ctrl)
    {
        Point p = Mouse.getMouseCoordinates();
        int x = (int) p.getX();
        int y = (int) p.getY();
        if (r.isCollision(x, y))
        {
            string = r.getHoverLabel();
        } else
        {
            string = "";
        }
        ctrl.drawString(x, (y - 2), string, Color.BLACK);
        ctrl.drawString(x - dropShadow, ((y - dropShadow) - 2), string, Color.YELLOW);
    }

    private void processGraphicalHover(RECT r, Control ctrl)
    {
        Point p = Mouse.getMouseCoordinates();
        int x = (int) p.getX();
        int y = (int) p.getY();
        Sprite sprite = new Sprite(r.getGraphicalHover().getX(), r.getGraphicalHover().getY(), sprites.get(r.getGraphicalHover().getSpriteTag()), r.getGraphicalHover().getSpriteTag());
        if (r.isCollision(x, y))
        {
            ctrl.addSpriteToHudBuffer(sprite);
        }
    }

    static class SpriteInfo
    {
        private int x;
        private int y;
        private String tag;

        public SpriteInfo(int x, int y, String tag)
        {
            this.x = x;
            this.y = y;
            this.tag = tag;
        }
    }
}