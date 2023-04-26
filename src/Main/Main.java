package Main;

import logic.Control;

public class Main
{
    // Fields (Static) below...

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
    }

    /* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
    public static void update(Control ctrl)
    {
        // TODO: This is where you can code!
    }
}