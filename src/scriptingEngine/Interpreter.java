package scriptingEngine;

import Data.*;
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

import static Main.Main.*;

public class Interpreter
{
    private List<Command> commands;
    private EZFileRead reader;

    private List<RECT> dayRECTAndButton;
    private static List<RECT> factoryRECTAndButton;
    private static List<RECT> barnRECTAndButton;
    private static List<RECT> button;

    private static List<SpriteInfo> dayImage;
    private static List<SpriteInfo> factoryImage;
    private static List<SpriteInfo> barnImage;

    private boolean switchScene  = false;
    private boolean field1ReadyForPlant;
    private boolean field2ReadyForPlant;
    private boolean field3ReadyForPlant;
    private boolean field4ReadyForPlant;
    private List<String> backgroundTagList = new ArrayList<>();

    private static int sceneNumber = Integer.MIN_VALUE;
    private static RECT r;
    private static final int dropShadow = 2;
    private static Animation loopingAnimation;

    public Interpreter()
    {
        //rs = new ArrayList<>();
        reader = new EZFileRead("script.txt");
        commands = new ArrayList<>();

        dayImage = new ArrayList<>();
        factoryImage = new ArrayList<>();
        barnImage = new ArrayList<>();
        field1ReadyForPlant = true;
        field2ReadyForPlant = true;
        field3ReadyForPlant = true;
        field4ReadyForPlant = true;
        setupInterpreter();
    }

    public void processCommands(Control ctrl)
    {
        for (Command command : commands)
        {
            if (command.isCommand("bg") && command.getNumberOfParameters() == 1 && command.getParametersByIndex(0).equalsIgnoreCase("day"))
            {
                if(!switchScene)
                {
                    String tag = backgroundTagList.get(0);
                    ParticleSystem particleSystem2 = butterFly.getParticleSystem();

                    Iterator<Frame> iterator2 = particleSystem2.getParticles();
                    while (iterator2.hasNext())
                    {
                        Frame particleFrame = iterator2.next();
                        BufferedImage butterFly = Main.sprites.get(particleFrame.getSpriteTag());
                        particleSprite = new Sprite(particleFrame.getX(), particleFrame.getY(), butterFly, particleFrame.getSpriteTag());
                        ctrl.addSpriteToFrontBuffer(particleSprite);
                    }
                }

                for (SpriteInfo info : dayImage)
                {
                    ctrl.addSpriteToFrontBuffer(new Sprite(info.x, info.y, sprites.get("tag"), info.tag));
                }
            }

            if(command.isCommand("Animation") && command.getNumberOfParameters() == 5)
            {
                if(sceneNumber == Integer.MIN_VALUE)
                {
                    Frame frame = loopingAnimation.getCurrentFrame();
                    if (frame != null)
                    {
                        ctrl.addSpriteToFrontBuffer(new Sprite(frame.getX(), frame.getY(), sprites.get("box"), "box"));
                    }
                }
            }
        }
        /*if (command.isCommand("add_sprite") && command.getNumberOfParameters() == 3)
        {
            int x1 = Integer.parseInt(command.getParametersByIndex(0));
            int y1 = Integer.parseInt(command.getParametersByIndex(1));
            String tag = command.getParametersByIndex(2);
            ctrl.addSpriteToFrontBuffer(x1, y1, tag);
        } else if (command.isCommand("text_hover") && command.getNumberOfParameters() == 6)
        {
            ctrl.addSpriteToFrontBuffer(0, 0, "f0");

            if (Control.getMouseInput() != null)
            {
                for (int i = 0; i < rs.size(); i++)
                {
                    if (rs.get(i).isClicked(Control.getMouseInput(), Click.LEFT_BUTTON))
                    {
                        string2 = i == 1 ? "" : rs.get(i).getTag() + " is clicked";
                        break;
                    }
                }
            }

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
            ctrl.drawString(20, 150, string2, Color.YELLOW);
            ctrl.drawString(x, (y - 2), string, Color.BLACK);
            ctrl.drawString(x - dropShadow, ((y - dropShadow) - 2), string, Color.YELLOW);
        }
    }*/
}

    private void createClickableRect(Command command, List<RECT> list)
    {
        int x1 = Integer.parseInt(command.getParametersByIndex(0));
        int y1 = Integer.parseInt(command.getParametersByIndex(1));
        int x2 = Integer.parseInt(command.getParametersByIndex(2));
        int y2 = Integer.parseInt(command.getParametersByIndex(3));
        String tag = command.getParametersByIndex(4);
        String hoverLabel = command.getParametersByIndex(5);
        r = new RECT(x1, y1, x2, y2, tag, hoverLabel);
        RECT screen = new RECT(0,0,1920,1080, "Whole screen");

    }
    private void renderBackground(Control ctrl)
    {
        if(switchScene)
        {
            String tag = "";
            if (sceneNumber == 0)
            {
                tag = backgroundTagList.get(sceneNumber);
                ctrl.addSpriteToFrontBuffer(0,0, tag);
            }
            else if (sceneNumber == 1)
            {
                tag = backgroundTagList.get(sceneNumber);
                ctrl.addSpriteToFrontBuffer(0,0, tag);
            }

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
                    }
                    else if(command.isCommand("show") && command.getNumberOfParameters() == 4)
                    {
                        addImageToList(command);
                    }
                    else if(command.isCommand("playMusic") && command.getNumberOfParameters() == 1)
                    {
                        Sound song = new Sound("Sound/" + command.getParametersByIndex(0));
                        song.setLoop();
                    }
                    else if (command.isCommand("animation") && command.getNumberOfParameters() == 5)
                    {
                        loopingAnimation = new Animation(Integer.parseInt(command.getParametersByIndex(0)),Boolean.parseBoolean(command.getParametersByIndex(1)));
                        int screen_width = Integer.parseInt(command.getParametersByIndex(2));
                        int step = Integer.parseInt(command.getParametersByIndex(3));
                        int yCoordinate = Integer.parseInt(command.getParametersByIndex(4));
                        for (int x = 0; x < screen_width; x += step)
                        {
                            loopingAnimation.addFrame(new Frame(x, yCoordinate, "cursor"));
                        }
                    }
                    else if (command.isCommand("text_hover"))
                    {
                        //createClickableRect(command);
                    }
                    commands.add(command);
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
        }
        else if (command.getParametersByIndex(1).equalsIgnoreCase("factory"))
        {
            factoryImage.add(spriteInfo);
        }
        else if (command.getParametersByIndex(1).equalsIgnoreCase(""))
        {
            barnImage.add(spriteInfo);
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
        public int getX()
        {
            return x;
        }

        public int getY()
        {
            return y;
        }

        public String getTag()
        {
            return tag;
        }
    }

    class buttonInfo
    {

    }



}

