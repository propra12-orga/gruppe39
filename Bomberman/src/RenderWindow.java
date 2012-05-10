import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class RenderWindow extends Canvas
{
	// #######################################################################
	// Variables
	// #######################################################################
	// not used by the class, but needed because of extending the class Canvas
	private static final long serialVersionUID = 1L;
	// holds our current screen we have to render to our window
	private Gamescreen Current_Screen;
	// used to manage accelerated graphics
	private BufferStrategy Buffer_Strategy;
	
	// our window width (obviously)
	private int window_width;
	// our window height (obviously)
	private int window_height;
	
	
	
	// #######################################################################
	// public
	// #######################################################################
	
	// =======================================================================
	// RenderWindow
	// desc: 	initialises our render window with provided parameters and 
	//			applies some common settings, we dont need to change
	// param: 	String title: title for our rendering window
	// 			int width: window width
	//			int height: window height
	// =======================================================================
	public RenderWindow(String title, int width, int height)
	{
		DebugConsole.Print("Init RenderWindow...");
		
		// error checks
		if (width <= 0 || height <= 0)
			DebugConsole.PrintError("Screen width or height <= 0");
		
		if (title == null)
			DebugConsole.PrintError("Invalid Windowtitle");
		
		
		// apply to private variables
		this.window_height = height;
		this.window_width = width;
		
		// create a frame to contain our game
		JFrame container = new JFrame(title);
				
		// get hold the content of the frame and set up the 
		// resolution of the game
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(this.window_width, this.window_height));
		panel.setLayout(null);
				
		// setup our canvas size and put it into the content of the frame
		this.setBounds(0, 0, width, height);
		panel.add(this);
		
		// Tell AWT not to bother repainting our canvas since we're
		// going to do that our self in accelerated mode
		this.setIgnoreRepaint(true);
				
		// finally make the window visible 
		container.pack();
		container.setResizable(false);
		container.setVisible(true);
		container.toFront();	// FIXME: get focus of window doesnt work like that, why?
		
		// create the buffering strategy which will allow AWT
		// to manage our accelerated graphics
		this.createBufferStrategy(2);
		Buffer_Strategy = getBufferStrategy();
		
		// by default, an AWT Frame doesn't do anything when you click
        // the close button; this bit of code will terminate the program when
        // the window is asked to close
		container.addWindowListener(new WindowAdapter() 
		{
            public void windowClosing(WindowEvent e) 
            {
                System.exit(0);
            }
        });
		
		// attach our input class to the window, so we get our actions states
		this.addKeyListener(new KeyboardInput());	// keyboard
													// future: joystick?
		
		DebugConsole.Print("Init RenderWindow DONE");
	}

	
	// =======================================================================
	// setCurrentScreen
	// desc: 	set the current game screen, which has get rendered
	// param: 	Gamescreen New_Screen: the screen, which has get rendered
	// ret:		void
	// =======================================================================
	public void setCurrentScreen(Gamescreen New_Screen)	 
	{
		DebugConsole.Print("Setting new Gamescreen");
		
		// error check
		if (New_Screen == null)
			DebugConsole.PrintError("Invalid new Gamescreen");
		
		this.Current_Screen = New_Screen;
	}
	
	
	// =======================================================================
	// updateRenderWindow
	// desc: 	updates our render window with our previously set game screen
	// param: 	void
	// ret:		void
	// =======================================================================
	public void updateRenderWindow()
	{
		// Get hold of a graphics context for the accelerated 
		// surface and blank it out
		Graphics2D graphics_context = (Graphics2D) Buffer_Strategy.getDrawGraphics();
		graphics_context.setColor(Color.black);
		graphics_context.fillRect(0, 0, this.window_width, this.window_height);
		
		// TODO: update all sprites of a game screen here
		//SpriteStorage.get().getSprite("1").draw(graphics_context, 0, 0);
		// draw all entities of our current screen to the render window
		this.Current_Screen.drawEntitesToScreen(graphics_context);
		
		// finally, we've completed drawing so clear up the graphics
		// and flip the buffer over
		graphics_context.dispose();
		Buffer_Strategy.show();
	}
	
	
	
	// #######################################################################
	// public
	// #######################################################################
	
	// nothing here	
}
