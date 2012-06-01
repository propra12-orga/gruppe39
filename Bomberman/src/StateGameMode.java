import java.awt.Graphics2D;


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
	
	//private Spielfeld spielfeld;
	private final Spielfeld spielfeld  =  new Spielfeld();


	@Override
	public void init() 
	{
		// init inputs for one player
		KeyboardInput.init(1);
		
		// TODO Auto-generated method stub
		spielfeld.addEntitiesToScreen(this.Cur_Gamescreen);	
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
			BombermanEntity bomberman = (BombermanEntity) this.Cur_Gamescreen.getEntity("bomberman0");
			
			if (bomberman.isMoving() == false)
			{
				if (this.spielfeld.spielfeld[bomberman.getTileX()][bomberman.getTileY() + 1] == null)
				{
					this.Cur_Gamescreen.getEntity("bomberman0").moveTileY(1, 3);
				}
			}
		}
			
		if (KeyboardInput.getActionState(0, KeyboardInput.ACTION.MOVE_DOWN) == true)
		{
			BombermanEntity bomberman = (BombermanEntity) this.Cur_Gamescreen.getEntity("bomberman0");
			
			if (bomberman.isMoving() == false)
			{
				if (this.spielfeld.spielfeld[bomberman.getTileX()][bomberman.getTileY() - 1] == null)
				{
					this.Cur_Gamescreen.getEntity("bomberman0").moveTileY(-1, 3);
				}
			}
		}
		if (KeyboardInput.getActionState(0, KeyboardInput.ACTION.MOVE_LEFT) == true)
		{
			BombermanEntity bomberman = (BombermanEntity) this.Cur_Gamescreen.getEntity("bomberman0");
			
			if (bomberman.isMoving() == false)
			{
				if (this.spielfeld.spielfeld[bomberman.getTileX() - 1][bomberman.getTileY()] == null)
				{
					this.Cur_Gamescreen.getEntity("bomberman0").moveTileX(-1, 3);
				}
			}
		}
			
		if (KeyboardInput.getActionState(0, KeyboardInput.ACTION.MOVE_RIGHT) == true)
		{
			BombermanEntity bomberman = (BombermanEntity) this.Cur_Gamescreen.getEntity("bomberman0");
			
			if (bomberman.isMoving() == false)
			{
				if (this.spielfeld.spielfeld[bomberman.getTileX() + 1][bomberman.getTileY()] == null)
				{
					this.Cur_Gamescreen.getEntity("bomberman0").moveTileX(1, 3);
				}
			}
			
		}
		
			

		// kollisionsabfrage
		
	}

	@Override
	public void shutdown() 
	{
		// TODO Auto-generated method stub
		
	}
}
