// MAIN TODO:
// - sprite animations with seperate threads
// - doc
// - 


public class Bomberman
{
	private boolean game_running;
	private long last_gameloop_time;
	
	private RenderWindow Render_Window;
	private Gamescreen Cur_Gamescreen;

	
	private Gamestate Cur_Gamestate;
	private Gamestate Prev_Gamestate;
	
	private InterfaceState Screen_State;
	
	

	public static void main(String[] args)
	{
		// DEBUG: set your level of verbose here, to see debug output
		// on console, check the class for details
		DebugConsole.setVerboseLevel(2);
		
		DebugConsole.Print("Entering Main, args: ");
		for (int i = 0; i < args.length; i++)
			DebugConsole.Print("arg " + i + ": " + args[i]);
		
		Bomberman game = new Bomberman();		
		game.init();
		// game.init(args);	if we are going to use args from console
		game.gameLoop();
		game.shutdown();
	}
	
	public Bomberman()
	{
		// nothing here
	}
	
	public void gameLoop()
	{		
		DebugConsole.Print("Starting Gameloop");
		
		while (game_running) 
		{
			// work out how long its been since the last update, this
			// will be used to calculate how far the entities should
			// move this loop
			long delta = System.currentTimeMillis() - last_gameloop_time;
			last_gameloop_time = System.currentTimeMillis();
			
			// if we have changed our state, we have to cleanup the old
			// screen and init the new one
			if (this.Prev_Gamestate.get() != this.Cur_Gamestate.get())
			{
				DebugConsole.Print("Gamestate has changed");
				DebugConsole.Print("Shutting down old state and screen, cleaning up");
				
				// cleanup old one first
				this.Screen_State.shutdown();
				this.Screen_State = null;
				this.Cur_Gamescreen = null;
				
				// create a new empty Gamescreen
				this.Cur_Gamescreen = new Gamescreen();
				
				// init new one according to next state saved in Cur_Gamestate
				switch (this.Cur_Gamestate.get())
				{
					case NO_STATE:
					{
						DebugConsole.Print("New gamestate is NO_STATE");
						this.Screen_State = new StateNoState();
						break;
					}
					case MAIN_MENU:
					{
						DebugConsole.Print("New gamestate is MAIN_MENU");
						this.Screen_State = new StateMainMenu(this.Cur_Gamestate, this.Cur_Gamescreen);
						break;
					}
					case GAME_MODE:
					{
						DebugConsole.Print("New gamestate is GAME_MODE");
						this.Cur_Gamescreen = new Gamescreen();
						this.Screen_State = new StateGameMode(this.Cur_Gamestate, this.Cur_Gamescreen);
						break;
					}
					default:
					{
						DebugConsole.Print("Unknown gamestate");
						this.Screen_State = new StateNoState();
						break;
					}
				}
				
				// init new screen state
				this.Screen_State.init();
				
				// apply the newly generated gamescreen to the rendering window
				this.Render_Window.setCurrentScreen(this.Cur_Gamescreen);
				
				// we are done, indicate this
				this.Prev_Gamestate = this.Cur_Gamestate;
				
				DebugConsole.Print("Creating new state successful, running now");
			}			

			// execute main of our current state
			this.Screen_State.main();
			
			// update graphics
			this.Render_Window.updateRenderWindow();

			// finally pause for a bit. Note: this should run us at about
			// 100 fps but on windows this might vary each loop due to
			// a bad implementation of timer
			try { Thread.sleep(10); } catch (Exception e) {}
		}
	}
	
	public void init()
	{
		DebugConsole.Print("Initializing...");
		
		// create our main rendering window
		this.Render_Window = new RenderWindow("Bomberman", 640, 480);
		
		// set initial gamestates
		// TODO: for later, set this to the first screen which has to appear
		this.Cur_Gamestate = new Gamestate(Gamestate.STATE.GAME_MODE);
		this.Prev_Gamestate = new Gamestate(Gamestate.STATE.NO_STATE);
		
		// init screen state
		this.Screen_State = new StateNoState();
		
		// set game running
		this.game_running = true;
		
		DebugConsole.Print("Initializing DONE");
	}
	
	
	public void shutdown()
	{
		DebugConsole.Print("Shutting down...");
		
		DebugConsole.Print("Shutting down DONE");
	}
}
