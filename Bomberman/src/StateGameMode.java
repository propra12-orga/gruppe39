
//=======================================================================
// StateGameMode
// desc: 	this state resembles our current running game, aka where 
//			a player can actually blow stuff up
//			for more information on details, see the corresponding interface
//=======================================================================
public class StateGameMode implements InterfaceState
{
	private Gamestate Cur_Gamestate;
	private Gamescreen Cur_Gamescreen;
	
	public StateGameMode(Gamestate Cur_State, Gamescreen Cur_Screen)
	{
		// important: these are references
		this.Cur_Gamestate = Cur_State;
		this.Cur_Gamescreen = Cur_Screen;
	}
	
	@Override
	public void init() 
	{
		// init inputs for one player
		KeyboardInput.init(1);
		
		// TODO Auto-generated method stub
		this.Cur_Gamescreen.addEntityToScreen("E1", new ExampleEntity("Bomberman", 0, 0));
		
		this.Cur_Gamescreen.getEntity("E1").setHorizontalSpeedMovement(5);
		this.Cur_Gamescreen.getEntity("E1").setVerticalSpeedMovement(5);
	}

	@Override
	public void main(long elapsed_time) 
	{
		if (KeyboardInput.getActionState(0, KeyboardInput.ACTION.MOVE_UP) == true)
			this.Cur_Gamescreen.getEntity("E1").moveY(50 * elapsed_time);
		if (KeyboardInput.getActionState(0, KeyboardInput.ACTION.MOVE_DOWN) == true)
			this.Cur_Gamescreen.getEntity("E1").moveY(-50 * elapsed_time);
		if (KeyboardInput.getActionState(0, KeyboardInput.ACTION.MOVE_LEFT) == true)
			this.Cur_Gamescreen.getEntity("E1").moveX(-50 * elapsed_time);
		if (KeyboardInput.getActionState(0, KeyboardInput.ACTION.MOVE_RIGHT) == true)
			this.Cur_Gamescreen.getEntity("E1").moveX(50 * elapsed_time);
		
		// kollisionsabfrage
	}

	@Override
	public void shutdown() 
	{
		// TODO Auto-generated method stub
		
	}
}
