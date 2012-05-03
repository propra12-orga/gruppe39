

public class Bomberman 
{
	public enum GAMESTATE 
	{
		NO_STATE,
		MAIN_MENU,
		START_NEW_GAME,
		GAME_MODE,
		RESULT_SCREEN
	}
	
	// TODO set correct state, public is probably wrong, 
	// because not everyone is allowed to change it
	public static int gamestate = 0;
	
	
	
	public static void main(String[] args)
	{
		Bomberman.init();
		// Bomberman.init(args)? falls es was zu parsen gibt
		Bomberman.gameLoop();
		Bomberman.shutdown();
	}
	
	private static void init()
	{
		// TODO
		// init environment stuff
		// set environment variables, gamestate, program state etc.
		// create seperate threads and related stuff
		// init network stuff (maybe later?)
		// init input, start input thread
		
		// put render window in seperate thread
		RenderWindow window = new RenderWindow("Testwindow", 640, 480);
	}
	
	private static void gameLoop()
	{
		// TODO
		// concept
		/*
		boolean exit = false
		
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
	}
	
	private static void shutdown()
	{
		// TODO
		// cleanup and shit
	}
}
