package Engine;
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
	public static BufferStrategy Buffer_Strategy;
	
	public static JFrame Frame;
	
	// our window width (obviously)
	private int window_width;
	// our window height (obviously)
	private int window_height;
	
	private String title;
	
	
	// #######################################################################
	// public
	// #######################################################################
	
	/***************************************************************************
	* RenderWindow
	* desc: 	initialises our render window with provided parameters and 
	*			applies some common settings, we dont need to change
	* @param: 	String title: title for our rendering window
	* 			int width: window width
	*			int height: window height
	***************************************************************************/
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
		this.title = title;
		
		// create a frame to contain our game
		RenderWindow.Frame = new JFrame(title);
				
		// get hold the content of the frame and set up the 
		// resolution of the game
		JPanel panel = (JPanel) RenderWindow.Frame.getContentPane();
		panel.setPreferredSize(new Dimension(this.window_width, this.window_height));
		
		panel.setLayout(null);

		// finally make the window visible 
		RenderWindow.Frame.pack();
		RenderWindow.Frame.setResizable(false);
		RenderWindow.Frame.toFront();	// FIXME: get focus of window doesnt work like that, why?
	
		DebugConsole.Print("Init RenderWindow DONE");
	}

	
	public void initGameRenderWindow()
	{
		// create a frame to contain our game
		RenderWindow.Frame = new JFrame(this.title);
		
		// get hold the content of the frame and set up the 
		// resolution of the game
		JPanel panel = (JPanel) RenderWindow.Frame.getContentPane();
		panel.setPreferredSize(new Dimension(this.window_width, this.window_height));
				
		panel.setLayout(null);

		// finally make the window visible 
		RenderWindow.Frame.pack();
		RenderWindow.Frame.setResizable(false);
		RenderWindow.Frame.setVisible(true);
		RenderWindow.Frame.toFront();	// FIXME: get focus of window doesnt work like that, why?
				
		// setup our canvas size and put it into the content of the frame
		this.setBounds(0, 0, this.window_width, this.window_height);
		RenderWindow.Frame.getContentPane().add(this);
		
		// Tell AWT not to bother repainting our canvas since we're
		// going to do that our self in accelerated mode
		this.setIgnoreRepaint(true);

		// create the buffering strategy which will allow AWT
		// to manage our accelerated graphics
		this.createBufferStrategy(2);
		Buffer_Strategy = getBufferStrategy();
		
		// by default, an AWT Frame doesn't do anything when you click
        // the close button; this bit of code will terminate the program when
        // the window is asked to close
		RenderWindow.Frame.addWindowListener(new WindowAdapter() 
		{
            public void windowClosing(WindowEvent e) 
            {
                System.exit(0);
            }
        });
		
		// attach our input class to the window, so we get our actions states
		this.addKeyListener(new KeyboardInput());	// keyboard
													// future: joystick?
	}
	
	/***************************************************************************
	* setCurrentScreen
	* desc: 	set the current game screen, which has get rendered
	* @param: 	Gamescreen New_Screen: the screen, which has get rendered
	* @return:		void
	***************************************************************************/
	public void setCurrentScreen(Gamescreen New_Screen)	 
	{
		DebugConsole.Print("Setting new Gamescreen");
		
		// error check
		if (New_Screen == null)
			DebugConsole.PrintError("Invalid new Gamescreen");
		
		this.Current_Screen = New_Screen;
	}
	
	
	/***************************************************************************
	* updateRenderWindow
	* desc: 	updates our render window with our previously set game screen
	* @param: 	void
	* @return:		void
	***************************************************************************/
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
