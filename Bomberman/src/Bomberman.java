
public class Bomberman
{
	private static final long serialVersionUID = 1L;
	
	private boolean game_running;
	private long last_gameloop_time;
	
	private RenderWindow Render_Window;
	private Gamescreen Current_Gamescreen;

	/* for later
	private static int gamestate = 0;
	private enum GAMESTATE 
	{
		NO_STATE,
		MAIN_MENU,
		START_NEW_GAME,
		GAME_MODE,
		RESULT_SCREEN
	}
	*/
	

	public static void main(String[] args)
	{
		Bomberman game = new Bomberman();		
		game.init();
		game.gameLoop();
		game.shutdown();
	}
	
	public Bomberman()
	{
		
	}
	
	public void gameLoop()
	{		
		while (game_running) 
		{
			// work out how long its been since the last update, this
			// will be used to calculate how far the entities should
			// move this loop
			long delta = System.currentTimeMillis() - last_gameloop_time;
			last_gameloop_time = System.currentTimeMillis();
					
			this.Render_Window.updateRenderWindow();

			// finally pause for a bit. Note: this should run us at about
			// 100 fps but on windows this might vary each loop due to
			// a bad implementation of timer
			try { Thread.sleep(10); } catch (Exception e) {}
		}
	}
	
	public void init()
	{
		// create our main rendering window
		this.Render_Window = new RenderWindow("Bomberman", 640, 480);
		
		// setup initial game screen
		// create our initial gamescreen with entities
		this.Current_Gamescreen = new Gamescreen();
		
		// TODO: game states, now we only have one, maybe fork this into another function
		this.Current_Gamescreen.addEntityToScreen("E1", new Entity("Bomberman", 0, 0));
		this.Render_Window.setCurrentScreen(this.Current_Gamescreen);
		
		// we are done
		this.game_running = true;
	}
	
	
	public void shutdown()
	{
		
	}
	
	
	/*
	
	
	
	
	private static void gameLoop()
	{
		/*
		// TODO
		// concept
		boolean exit = false;
		
		while (!exit)
		{
			// state machine
			switch (gamestate)
			{
				case "main menu": // function call main menu 
				case "start game menu": // etc.
				case "game screen"
				case "result screen"
				default: 
			}
		}
	
	*/
	/*}
	
	private static void shutdown()
	{
		// TODO
		// cleanup and shit
	}
	*/
}
