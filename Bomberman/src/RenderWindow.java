import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class RenderWindow extends Canvas
{
	private static final long serialVersionUID = 1L;
	
	private Gamescreen Current_Screen;
	private BufferStrategy Buffer_Strategy;
	
	private int window_width;
	private int window_height;
	
	
	public RenderWindow(String title, int width, int height)
	{
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
	}

	public void setCurrentScreen(Gamescreen New_Screen)	 
	{
		this.Current_Screen = New_Screen;
	}
	
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
	
	
}
