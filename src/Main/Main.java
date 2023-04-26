package Main;

import logic.Control;
import sounds.*;

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
    public static void start()
    {
        // TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite or drawString)
        song.setLoop();
    }

    /* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
    public static void update(Control ctrl)
    {
        ctrl.addSpriteToFrontBuffer(0,0,"firefly");
        // TODO: This is where you can code!
    }
}
