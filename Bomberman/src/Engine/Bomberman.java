package Engine;
import Gamestates.StateGameMode;
import Gamestates.StateGameModeClient;
import Gamestates.StateGameModeServer;
import Gamestates.StateMainMenu;
import Gamestates.StateNoState;

// MAIN TODO for engine:
// - sprite animations with seperate threads


// =======================================================================
// Bomberman
// desc: our main class/engine and program entry point
// =======================================================================
public class Bomberman
{
	// #######################################################################
	// Variables
	// #######################################################################
	// variable indicating, we want to stay in our mainloop
	private boolean game_running;
	// holding timestamp of previous gameloop to calculate update time delta
	private long last_gameloop_time;
	
	// our main rendering window
	private RenderWindow Render_Window;
	// current screen getting displayed
	private Gamescreen Cur_Gamescreen;

	// our current gamestate for the main gameloop
	private Gamestate Cur_Gamestate;
	// and previous one
	private Gamestate Prev_Gamestate;
	
	// reference to our current screen getting executed
	private InterfaceState Screen_State;
	
	// not good =(
	public static int port;
	public static String address;
	public static String level;
	
	// #######################################################################
	// Public functions
	// #######################################################################

	/*************************************************************************
	* main
	* desc: program entry point
	* @param: void
	**************************************************************************/
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


	/*************************************************************************
	* init
	* desc: 	initializing important parts of the engine
	* @param: 	void
	* @return: 	void
	**************************************************************************/
	public void init()
	{
		DebugConsole.Print("Initializing...");
		
		// create our main rendering window
		this.Render_Window = new RenderWindow("Bomberman", 480, 480);
		
		// set initial gamestates
		// TODO: for later, set this to the first screen which has to appear
		this.Cur_Gamestate = new Gamestate(Gamestate.STATE.MAIN_MENU);
		this.Prev_Gamestate = new Gamestate(Gamestate.STATE.NO_STATE);
		
		// init screen state
		this.Screen_State = new StateNoState();
		
		// set game running
		this.game_running = true;
		
		DebugConsole.Print("Initializing DONE");
	}
	
	
	/**************************************************************************
	* gameLoop
	* desc: 	looping through all the important parts for the game to run
	* @param: 	void
	* @return: 	void
	***************************************************************************/
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
				this.Cur_Gamescreen = new Gamescreen(this.Render_Window);
				
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
						this.Cur_Gamescreen = new GamescreenGameMode(this.Render_Window);
						this.Screen_State = new StateGameMode(this.Cur_Gamestate, (GamescreenGameMode) this.Cur_Gamescreen);
						break;
					}
					case GAME_MODE_CLIENT:
					{
						DebugConsole.Print("New gamestate is GAME_MODE_CLIENT");
						this.Cur_Gamescreen = new GamescreenGameMode(this.Render_Window);
						this.Screen_State = new StateGameModeClient(this.Cur_Gamestate, (GamescreenGameMode) this.Cur_Gamescreen);						
						break;
					}
					case GAME_MODE_SERVER:
					{
						DebugConsole.Print("New gamestate is GAME_MODE_SERVER");
						this.Cur_Gamescreen = new GamescreenGameMode(this.Render_Window);
						this.Screen_State = new StateGameModeServer(this.Cur_Gamestate, (GamescreenGameMode) this.Cur_Gamescreen);						
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
				
				DebugConsole.Print("Creating new state successful, running now");
			}	
			
			this.Prev_Gamestate.set(this.Cur_Gamestate.get());

			// execute main of our current state
			this.Screen_State.main(delta);
			
			// update graphics
			if (this.Cur_Gamestate.get() == Gamestate.STATE.GAME_MODE || 
				this.Cur_Gamestate.get() == Gamestate.STATE.GAME_MODE_CLIENT ||
				this.Cur_Gamestate.get() == Gamestate.STATE.GAME_MODE_SERVER)
				this.Render_Window.updateRenderWindow();

			// finally pause for a bit. Note: this should run us at about
			// 100 fps but on windows this might vary each loop due to
			// a bad implementation of timer
			try { Thread.sleep(10); } catch (Exception e) {}
		}
	}
	
	
	/************************************************************************
	* shutdown
	* desc: 	cleaning up stuff, if there is any
	* @param: 	void
	* @return: 	void
	*************************************************************************/	
	public void shutdown()
	{
		DebugConsole.Print("Shutting down...");
		
		DebugConsole.Print("Shutting down DONE");
	}
	
	
	
	// #######################################################################
	// Private functions
	// #######################################################################
	
	// nothing here
}
