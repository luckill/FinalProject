package Main;

import Input.*;
import logic.Control;
import sounds.*;

import java.awt.*;

public class Main
{
    // Fields (Static) below...
    public static Sound song = new Sound("Sound/persephone_farewell.wav");
    public static Sound sfx = new Sound("Sound/funny_death.wav");
    // End Static fields...

    public static void main(String[] args)
    {
        Control ctrl = new Control();                // Do NOT remove!
        ctrl.gameLoop();                            // Do NOT remove!
    }

    /* This is your access to things BEFORE the game loop starts */
    public static void start(Control ctrl)
    {
        // TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite or drawString)
        song.setLoop();
        ctrl.hideDefaultCursor();
    }

    /* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
    public static void update(Control ctrl)
    {
        Point p = Mouse.getMouseCoordinates();
        ctrl.addSpriteToOverlayBuffer(p.x, p.y, "cursor");
    }
}
