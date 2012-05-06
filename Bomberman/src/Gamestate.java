
public class Gamestate 
{
	public static enum STATE 
	{
		NO_STATE,
		MAIN_MENU,
		START_NEW_GAME,
		GAME_MODE,
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
