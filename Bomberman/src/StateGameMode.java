
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
		// TODO Auto-generated method stub
		this.Cur_Gamescreen.addEntityToScreen("E1", new ExampleEntity("Bomberman", 0, 0));
		
		this.Cur_Gamescreen.getEntity("E1").setHorizontalSpeedMovement(5);
		this.Cur_Gamescreen.getEntity("E1").setVerticalSpeedMovement(5);
	}

	@Override
	public void main() 
	{
		this.Cur_Gamescreen.getEntity("E1").moveX(100);
	}

	@Override
	public void shutdown() 
	{
		// TODO Auto-generated method stub
		
	}
}
