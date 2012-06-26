package Engine;

/***************************************************************************
* Gamestate
* desc: 	simple class keeping track of our gamestate (either current
*			or previous) with practical enum
*			wont add any further description, because everything 
*			explains itself
***************************************************************************/
public class Gamestate 
{
	public static enum STATE 
	{
		NO_STATE,
		MAIN_MENU,
		START_NEW_GAME,
		GAME_MODE,
		GAME_MODE_SERVER,
		GAME_MODE_CLIENT,
		RESULT_SCREEN
	}
	
	private STATE state = STATE.NO_STATE;
	
	public Gamestate()
	{
		
	}
	
	public Gamestate(STATE gameMode)
	{
		this.state = gameMode;
	}

	public void set(STATE state)
	{
		this.state = state;
	}
	
	public STATE get()
	{
		return this.state; 
	}
}
