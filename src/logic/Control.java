/* This holds the boilerplate stuff that students shouldn't have to worry about . 
 * Copyright 2020 Matthew W. Phillips */
package logic;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

import Data.*;
import FileIO.EZFileRead;
import Graphics.Graphic;
import Graphics.Sprites;
import Input.*;
import Main.KeyProcessor;
import Main.Main;
import gameloop.gameLoop;

public class Control{
	// Fields
	private Graphic graphic;
	private Sprites backbuffer;
	private Sprites frontbuffer;
	private Sprites overlaybuffer;
	private Sprites hudBuffer;
	private ArrayList<gameString> gs;
	private ArrayList<gameString> hudText;
	private Keyb kb;
	private Mouse mouse;
	private gameLoop gl;
	private Font font;
	public static boolean isMouseCoordsDisplayed;
	public static Click mouseInput;

	// Constructor
	public Control(){
		graphic = new Graphic();                // Place screen into Graphics mode (1280 x 720) so we can use full screen exclusive graphic routines!
		backbuffer = new Sprites();                // Create place to store game ALL game sprites needed for project (images)
		frontbuffer = new Sprites();            // This is the "front buffer" that goes to the renderer every frame
		overlaybuffer = new Sprites();            // Allows things above the frontbuffer (overlays or draws on top of...things like custom mouse cursors, etc.)
		hudBuffer = new Sprites();
		gs = new ArrayList<>();                    // Set up for our gamestrings (graphical text printing; System.out.println doesn't work in graphics mode!)
		hudText = new ArrayList<>();
		setupFont();                            // Set up our program to use a font custom (stored in "Font" subfolder)
		loadArtIntoBackBuffer();                // Lods the art referenced in "Art.txt" into the backbuffer of sprites
		kb = new Keyb();// Initialize the keyboard handler
		mouse = new Mouse();
		gl = new gameLoop(graphic, gs, frontbuffer, hudText, hudBuffer, overlaybuffer);        // Sets up our render loop
		graphic.setKeyListener(kb);// Sets our graphics handler up to listen for keyboard input (Asynchronous!)
		graphic.setMouseListener(mouse);
		Main.start(this);
	}

	// Methods
	// WARNING! DO NOT MODIFY THE CODE IN HERE! THIS IS HERE TO GET THE GAME LIBRARY TO WORK!
	public void gameLoop(){
		char key = kb.getInputCodeX();
		/* "Infinite" loop here...*/
		while(true){
			KeyProcessor.processKey(key);                                        // Check for keyboard key press
			Main.update(this);                                                    // This is where the student codes inside of
			if(isMouseCoordsDisplayed)
				drawString(1110, 20, getMouseCoords(), Color.white);
			gl.run();                                                             // Render the graphical data for the frame!
			frontbuffer.clearSprites();
			hudBuffer.clearSprites();// Clear the front sprite buffer for the next frame
			overlaybuffer.clearSprites();                                         // Clears the overlay buffer for the next frame
			gs.clear();// Clears all of the gameStrings for that frame
			hudText.clear();
			key = kb.getInputCodeX();// Get keyboard input for next pass
			mouseInput = mouse.pollCLick();
		}
	}

	// WARNING! DO NOT MODIFY THE CODE HERE! THIS IS HERE TO GET THE GAME LIBRARY TO WORK!
	public void drawString(int x, int y, String text, Color c){
		gs.add(new gameString(c, x, y, text, font, 1.0f));
	}

	// WARNING! DO NOT MODIFY THE CODE HERE! THIS IS HERE TO GET THE GAME LIBRARY TO WORK!
	public void addSpriteToFrontBuffer(int x, int y, String spriteTag){
		Sprite s = backbuffer.getSpriteByTag(spriteTag);
		s.moveXAbsolute(x);
		s.moveYAbsolute(y);
		frontbuffer.addSprite(s);
	}

	public void addSpriteToHudBuffer(int x, int y, String spriteTag)
	{
		Sprite s = backbuffer.getSpriteByTag(spriteTag);
		s.moveXAbsolute(x);
		s.moveYAbsolute(y);
		hudBuffer.addSprite(s);
	}

	public void addSpriteToHudBuffer(Sprite s)
	{
		hudBuffer.addSprite(s);
	}

	public void drawHudString(int x, int y, String text, Color c)
	{
		hudText.add(new gameString(c,x,y,text,font,1.0f));
	}

	public Sprite getSpriteFromBackBuffer(String tag)
	{
		return backbuffer.getSpriteByTag(tag);
	}

	public static Click getMouseInput()
	{
		return mouseInput;
	}

	public void addSpriteToFrontBuffer(Sprite sprite)
	{
		frontbuffer.addSprite(sprite);
	}

	public void addSpriteToOverlayBuffer(int x, int y, Sprite sprite)
	{
		overlaybuffer.addSprite(sprite);
	}

	public void hideDefaultCursor()
	{
		graphic.hideCursor();
	}

	// WARNING! DO NOT MODIFY THE CODE HERE! THIS IS HERE TO GET THE GAME LIBRARY TO WORK!
	private void loadArtIntoBackBuffer(){
		EZFileRead ezr = new EZFileRead("Art.txt");
		for(int i = 0; i < ezr.getNumLines(); i++){
			String raw = ezr.getLine(i);
			StringTokenizer st = new StringTokenizer(raw, "*");
			String file = st.nextToken();
			String tag = st.nextToken();
			backbuffer.addSprite(new Sprite(0, 0, file, tag));
		}
	}

	// WARNING! DO NOT MODIFY THE CODE HERE! THIS IS HERE TO GET THE GAME LIBRARY TO WORK!
	private void setupFont(){
		String fontFile = "Font/Jipatha-Regular.ttf";
		float fontSize = 32f;
		font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File(fontFile)).deriveFont(fontSize);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(fontFile)));
		}catch (Exception e) {e.printStackTrace();}
	}

	// WARNING! DO NOT MODIFY THE CODE HERE! THIS IS HERE TO GET THE GAME LIBRARY TO WORK!
	private static String getMouseCoords(){
		Point p = MouseInfo.getPointerInfo().getLocation();
		int x1 = (int) p.getX();
		int y1 = (int) p.getY();
		return "X: " + x1 + " , Y: " + y1;
	}
}
