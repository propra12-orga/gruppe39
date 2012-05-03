import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class RenderWindow 
{
	private Gamescreen Current_Screen;
	
	
	public RenderWindow(String title, int height, int width)
	{
        Frame frame = new Frame(title);
        frame.setSize(height, width);
        frame.setVisible(true);
        
        // by default, an AWT Frame doesn't do anything when you click
        // the close button; this bit of code will terminate the program when
        // the window is asked to close
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
	}
	
	// TODO: interface for graphical object
	// every game object has to extend this in order to get drawn
	public void setCurrentScreen(Gamescreen New_Screen)	 
	{
		this.Current_Screen = New_Screen;
	}
	
	public void updateRenderWindow()
	{
		Current_Screen.updateGraphics();
	}
	
	
}
