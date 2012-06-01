
/*********************************************************************
* StateGameMode
* desc: 	this state resembles our current running game, aka where 
*			a player can actually blow stuff up
*			for more information on details, see the corresponding interface
**************************************************************************/
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
	
	private Spielfeld spielfeld;
	

	@Override
	public void init() 
	{
		// init inputs for one player
		KeyboardInput.init(1);
		
		// TODO Auto-generated method stub
		//this.spielfeld =  new Spielfeld();
		//spielfeld.addEntitiesToScreen(this.Cur_Gamescreen);
		
		this.Cur_Gamescreen.addEntityToScreen("bomberman0", new ExampleEntity("Bomberman", 0, 0));
		
		this.Cur_Gamescreen.getEntity("bomberman0").setHorizontalSpeedMovement(5);
		this.Cur_Gamescreen.getEntity("bomberman0").setVerticalSpeedMovement(5);
	
	}

	@Override
	public void main(long elapsed_time) 
	{
		// TODO
		// - input abfrage und verarbeitung
		// - kollisionsabfrage und neue position(en) berechnen, evtl. zustandsabfrage
		// - zustandsabfrage und verarbeitung der zustaende der entities
		
		
		// TODO: draft not working
		if (KeyboardInput.getActionState(0, KeyboardInput.ACTION.MOVE_UP) == true)
		{
			this.Cur_Gamescreen.getEntity("bomberman0").moveTileY(1, 0);
		}
			
		if (KeyboardInput.getActionState(0, KeyboardInput.ACTION.MOVE_DOWN) == true)
			this.Cur_Gamescreen.getEntity("bomberman0").moveTileY(-1, 0);
		if (KeyboardInput.getActionState(0, KeyboardInput.ACTION.MOVE_LEFT) == true)
			this.Cur_Gamescreen.getEntity("bomberman0").moveTileX(-1, 0);
		if (KeyboardInput.getActionState(0, KeyboardInput.ACTION.MOVE_RIGHT) == true)
		{
			this.Cur_Gamescreen.getEntity("bomberman0").moveTileX(1, 0);
			/*
			int old_cord = this.Cur_Gamescreen.getEntity("bomberman0").getLocationX();
			this.Cur_Gamescreen.getEntity("bomberman0").moveX(50 * elapsed_time);
			int new_cord = this.Cur_Gamescreen.getEntity("bomberman0").getLocationX();
			
			if (this.Cur_Gamescreen.getEntity("bomberman0").collidesWith(spielfeld.getEntity(new_cord, 
					(this.Cur_Gamescreen.getEntity("bomberman0").getLocationY()))))
			{
				this.Cur_Gamescreen.getEntity("bomberman0").setX(old_cord);
				System.out.println("ccc");
			}
			*/
		}
			
		
		// kollisionsabfrage
		
	}

	@Override
	public void shutdown() 
	{
		// TODO Auto-generated method stub
		
	}
}
