package Engine;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// TODO: put the action enum and a few other things, which
// can be used into a seperate class (if we are going to have joysticks
// or any other input device supported)
/***************************************************************************
* KeyboardInput
* desc: 	this class handles all the keyboard input and provides
*			an interface to access a fixed list of action states, which
*			are used to write the gameflow
***************************************************************************/
public class KeyboardInput extends KeyAdapter
{	
	// #######################################################################
	// Variables
	// #######################################################################
	// keeps track of our current number of actions
	private static final int NUMBER_ACTIONS = 7;
	// enum for actions, makes stuff easier
	public enum ACTION
	{
		NONE,
		MOVE_UP,
		MOVE_DOWN,
		MOVE_LEFT,
		MOVE_RIGHT,
		BOMB,
		PAUSE
	};
	// number of players we want to keep track of
	private static int number_players = 0;
	// array holding our keyboard configuration for each player
	private static int[][] Keycode_Config;
	// array holding our action states for each player
	private static boolean[][][] action;
	
	
	
	// #######################################################################
	// Public functions
	// #######################################################################

	/***************************************************************************
	* init
	* desc: 	this has to be called, else we wont have anything available
	* @param: 	int number_players: create interfaces for a number of players (at least 1)
	* @return:		void
	***************************************************************************/
	public static void init(int number_players)
	{
		DebugConsole.Print("Initalizing Keyboardinput, number of players: " + number_players);
		
		// TODO: add limit
		// error check
		if (number_players < 1)
		{
			DebugConsole.PrintError("Number of Players must be at least 1");
			return;
		}
		
		KeyboardInput.number_players = number_players;
		
		// create as many arrays as players we have
		KeyboardInput.Keycode_Config = new int[KeyboardInput.number_players][KeyboardInput.NUMBER_ACTIONS];
		KeyboardInput.action = new boolean[KeyboardInput.number_players][KeyboardInput.NUMBER_ACTIONS][2];

		
		// init all actions as false and set default keyconfig for player 1 and none
		// for all other players (to prevent errors)
		KeyboardInput.setDefaultKeyConfig(0);	//player 1
		for (int i = 0; i < KeyboardInput.number_players; i++)
		{
			if (i < 0)	// all other players
				KeyboardInput.setKeyConfigNone(i);
			
			for (int j = 0; j < KeyboardInput.NUMBER_ACTIONS; j++)
			{
				KeyboardInput.action[i][j][0] = false;
				KeyboardInput.action[i][j][1] = false;
			}
		}
	}
	
	public static int getNumberPlayers()
	{
		return KeyboardInput.number_players;
	}
	
	
	
	/***************************************************************************
	* configSetActionToKey
	* desc: 	assign a key to an action ("keyboard settings")
	* @param: 	int player: configuration of which player (starting with 0 for player 1)
	*			int key_code: key, which has to be assign to the action a
	*			ACTION a: which action has to get the key_code
	* @return:		void
	***************************************************************************/
	public static void configSetActionToKey(int player, int key_code, ACTION a)
	{
		// error check
		if (player < 0 || player + 1 > KeyboardInput.number_players)
		{
			DebugConsole.PrintError("Invalid player number: " + player);
			return;
		}
		
		// used to configure inputs by the user
		// map key event e to action a
		KeyboardInput.Keycode_Config[player][a.ordinal()] = key_code;
	}
	
	
	/***************************************************************************
	* setDefaultKeyConfig
	* desc: 	set a default key configuration for a player
	* @param: 	int player: player, which recvices the default config
	*			starting with player 1 as 0
	* @return:		void
	***************************************************************************/
	public static void setDefaultKeyConfig(int player)
	{
		// error check
		if (player < 0 || player + 1 > KeyboardInput.number_players)
		{
			DebugConsole.PrintError("Invalid player number: " + player);
			return;
		}
		
		// default configuration for our keyboard input
		KeyboardInput.Keycode_Config[player][ACTION.MOVE_UP.ordinal()] = KeyEvent.VK_UP;
		KeyboardInput.Keycode_Config[player][ACTION.MOVE_DOWN.ordinal()] = KeyEvent.VK_DOWN; 
		KeyboardInput.Keycode_Config[player][ACTION.MOVE_LEFT.ordinal()] = KeyEvent.VK_LEFT;
		KeyboardInput.Keycode_Config[player][ACTION.MOVE_RIGHT.ordinal()] = KeyEvent.VK_RIGHT;
		KeyboardInput.Keycode_Config[player][ACTION.PAUSE.ordinal()] = KeyEvent.VK_ENTER;
		KeyboardInput.Keycode_Config[player][ACTION.BOMB.ordinal()] = KeyEvent.VK_SPACE;
		
		DebugConsole.Print("Default keyboard config set for player " + player);
	}
	
	
	/***************************************************************************
	* setKeyConfigNone
	* desc: 	delete a complete key config of a player
	* @param: 	int player: delete the config of this player (starting with 0)
	* @return:		void
	***************************************************************************/
	public static void setKeyConfigNone(int player)
	{
		// error check
		if (player < 0 || player + 1 > KeyboardInput.number_players)
		{
			DebugConsole.PrintError("Invalid player number: " + player);
			return;
		}
		
		// set all "keys" to none
		for (int i = 0; i < KeyboardInput.NUMBER_ACTIONS; i++)
		{
			KeyboardInput.Keycode_Config[player][i] = ACTION.NONE.ordinal();
		}
		
		DebugConsole.Print("Keyboard config NONE set for player " + player);
	}
	
	
	/***************************************************************************
	* getActionState
	* desc: 	returns if a specified action of a player was taken
	* @param: 	int player: specify player
	*			ACTION a: action to check on
	* @return:		true, if action was taken, false if not
	***************************************************************************/
	public static boolean getActionState(int player, ACTION a)
	{
		// error check
		if (player < 0 || player + 1 > KeyboardInput.number_players)
		{
			DebugConsole.PrintError("Invalid player number: " + player);
			return false;
		}
		
		if (KeyboardInput.action[player][a.ordinal()][0] == true && KeyboardInput.action[player][a.ordinal()][1] == true)
		{
			KeyboardInput.action[player][a.ordinal()][0] = false;
			KeyboardInput.action[player][a.ordinal()][1] = false;
			return true;
		}
		else 
			return false;
	}
	
	
	/***************************************************************************
	* keyPressed
	* desc: 	notification from AWT that a key has been pressed. Note that
	*  		a key being pressed is equal to being pushed down but *NOT*
	* 			released. Thats where keyTyped() comes in.
	* @param: 	KeyEvent e: the details of the key that was pressed 
	* @return:		void
	***************************************************************************/
	public void keyPressed(KeyEvent e) 
	{
		// if we havnt initalized  anything, skip
		if (KeyboardInput.number_players < 1)
			return;
		
		// loop through all players we have
		for (int i = 0; i < KeyboardInput.number_players; i++)
		{
			for (int j = 0; j < KeyboardInput.NUMBER_ACTIONS; j++)
			{
				if (e.getKeyCode() == KeyboardInput.Keycode_Config[i][j])
				{
					KeyboardInput.action[i][j][0] = true;
					// FIXME: insert this to have single key presses AND releases (types) only
					//KeyboardInput.action[i][j][1] = false;
				}
				else
					KeyboardInput.action[i][j][0] = false;
			}
		}
	}
	
	
	/*************************************************************************** 
	* keyReleased
	* desc: 	notification from AWT that a key has been released.
	* @param: 	KeyEvent e: the details of the key that was pressed 
	* @return:		void
	***************************************************************************/
	public void keyReleased(KeyEvent e) 
	{
		// if we havnt initalized  anything, skip
		if (KeyboardInput.number_players < 0)
			return;
		
		// loop through all players we have
		for (int i = 0; i < KeyboardInput.number_players; i++)
		{
			for (int j = 0; j < KeyboardInput.NUMBER_ACTIONS; j++)
			{
				if (e.getKeyCode() == KeyboardInput.Keycode_Config[i][j])
				{
					KeyboardInput.action[i][j][1] = true;
				}
				else
					KeyboardInput.action[i][j][1] = false;
			}
		}
	}

	
	/***************************************************************************
	* keyTyped
	* desc: 	notification from AWT that a key has been typed. Note that
	*		 	typing a key means to both press and then release it.
	* @param: 	KeyEvent e: the details of the key that was pressed 
	* @return:		void
	***************************************************************************/
	public void keyTyped(KeyEvent e) 
	{/*
		System.out.println("key typed: " + e.toString());
		
		// loop through all players we have
		for (int i = 0; i < KeyboardInput.number_players; i++)
		{
			for (int j = 0; j < KeyboardInput.NUMBER_ACTIONS; j++)
			{
				if (e.getKeyCode() == KeyboardInput.Keycode_Config[i][j])
				{
					KeyboardInput.action[i][j] = true;
				}
			}
		}
*/
		// TODO: ?
		/*

		// if we're waiting for a "any key" type then
		// check if we've recieved any recently. We may
		// have had a keyType() event from the user releasing
		// the shoot or move keys, hence the use of the "pressCount"
		// counter.
		if (waitingForKeyPress) {
			if (pressCount == 1) {
				// since we've now recieved our key typed
				// event we can mark it as such and start 
				// our new game
				waitingForKeyPress = false;
				startGame();
				pressCount = 0;
			} else {
				pressCount++;
			}
		}
		
		// if we hit escape, then quit the game
		if (e.getKeyChar() == 27) {
			System.exit(0);
		}
		*/
	}
}