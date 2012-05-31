
/***************************************************************************
* StateGameMode
* desc: 	main menu of the whole game.
*			for more information on details, see the corresponding interface
***************************************************************************/
public class StateMainMenu implements InterfaceState
{
	private Gamestate Cur_Gamestate;
	private Gamescreen Cur_Gamescreen;
	
	public StateMainMenu(Gamestate Cur_State, Gamescreen Cur_Screen)
	{
		// important: these are references
		this.Cur_Gamestate = Cur_State;
		this.Cur_Gamescreen = Cur_Screen;
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void main(long elapsed_time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

}
