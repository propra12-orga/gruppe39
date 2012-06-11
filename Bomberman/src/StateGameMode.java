import java.awt.Graphics2D;



/*********************************************************************
* StateGameMode
* desc: 	this state resembles our current running game, aka where 
*			a player can actually blow stuff up
*			for more information on details, see the corresponding interface
**************************************************************************/
public class StateGameMode implements InterfaceState
{
	public static enum STATE
	{
		NO_STATE,
		GAME_RUNNING,
		GAME_PAUSED,
		GAME_OVER
	};
	
	private STATE state = STATE.NO_STATE;
	
	private Gamestate Cur_Gamestate;
	private Gamescreen Cur_Gamescreen;
	
	public StateGameMode(Gamestate Cur_State, Gamescreen Cur_Screen)
	{
		// important: these are references
		this.Cur_Gamestate = Cur_State;
		this.Cur_Gamescreen = Cur_Screen;
	}
	
	//private Spielfeld spielfeld;
	public final Entity[][] spielfeld = new Entity[15][13];

	
	// TODO: remove, only for testing
	private BombermanEntity bomberman;

	@Override
	public void init() 
	{
		// init inputs for one player
		KeyboardInput.init(1);
		
		// ring of unbreakables
		for (int i = 0; i < 15; i++)
		{
			Entity e = new UnbreakableEntity("Tile_UnBreakable",i * 32, 0);
			this.spielfeld[i][0] = e;
			this.Cur_Gamescreen.addEntityToScreen(e);
					
			Entity e2 = new UnbreakableEntity("Tile_UnBreakable",i * 32, 32 * 12);
			this.spielfeld[i][12] = e2;
			this.Cur_Gamescreen.addEntityToScreen(e2);
		}
				
		for (int i = 1; i < 13; i++)
		{
			Entity e = new UnbreakableEntity("Tile_UnBreakable", 0, 32 * i);
			this.Cur_Gamescreen.addEntityToScreen(e);
			this.spielfeld[0][i] = e;
					
			Entity e2 = new UnbreakableEntity("Tile_UnBreakable", 32 * 14, 32 * i);
			this.Cur_Gamescreen.addEntityToScreen(e2);
			this.spielfeld[14][i] = e2;
		}
		
		// bomberman
		Entity e = new BombermanEntity("Bomberman", 32 * 1, 32 * 1);
		this.Cur_Gamescreen.addEntityToScreen(e);
		this.spielfeld[1][1] = e;
		this.bomberman = (BombermanEntity) e;
		
	}

	@Override
	public void main(long elapsed_time) 
	{
		// TODO
		// - input abfrage und verarbeitung
		// - kollisionsabfrage und neue position(en) berechnen, evtl. zustandsabfrage
		// - zustandsabfrage und verarbeitung der zustaende der entities

	
		// TODO: abfrage, ob bomberman figuren noch am leben sind und entsprechendes machen			
		
		// statt dead abfrage, abfrage ob entity aus spielfeld null ist
		if (this.bomberman.getState() == BombermanEntity.STATE.DEAD)
		{
			// TODO
			return;
		}
		
		if (KeyboardInput.getActionState(0, KeyboardInput.ACTION.MOVE_UP) == true)
		{			
			if (bomberman.isMoving() == false)
			{
				synchronized (this.spielfeld)
				{
					if (this.spielfeld[bomberman.getTileX()][bomberman.getTileY() + 1] == null)
					{
						this.spielfeld[bomberman.getTileX()][bomberman.getTileY() + 1] = this.spielfeld[bomberman.getTileX()][bomberman.getTileY()];
						this.spielfeld[bomberman.getTileX()][bomberman.getTileY()] = null;
						this.bomberman.moveTileY(1, 3);
					}
				}
			}
		}
			
		if (KeyboardInput.getActionState(0, KeyboardInput.ACTION.MOVE_DOWN) == true)
		{	
			if (bomberman.isMoving() == false)
			{
				synchronized (this.spielfeld)
				{
					if (this.spielfeld[bomberman.getTileX()][bomberman.getTileY() - 1] == null)
					{
						this.spielfeld[bomberman.getTileX()][bomberman.getTileY() - 1] = this.spielfeld[bomberman.getTileX()][bomberman.getTileY()];
						this.spielfeld[bomberman.getTileX()][bomberman.getTileY()] = null;
						this.bomberman.moveTileY(-1, 3);
					}
				}
			}
		}
		if (KeyboardInput.getActionState(0, KeyboardInput.ACTION.MOVE_LEFT) == true)
		{
			if (bomberman.isMoving() == false)
			{
				synchronized (this.spielfeld)
				{
					if (this.spielfeld[bomberman.getTileX() - 1][bomberman.getTileY()] == null)
					{
						this.spielfeld[bomberman.getTileX() - 1][bomberman.getTileY()] = this.spielfeld[bomberman.getTileX()][bomberman.getTileY()];
						this.spielfeld[bomberman.getTileX()][bomberman.getTileY()] = null;
						this.bomberman.moveTileX(-1, 3);
					}
				}
			}
		}
			
		if (KeyboardInput.getActionState(0, KeyboardInput.ACTION.MOVE_RIGHT) == true)
		{
			if (bomberman.isMoving() == false)
			{
				synchronized (this.spielfeld)
				{
					if (this.spielfeld[bomberman.getTileX() + 1][bomberman.getTileY()] == null)
					{
						this.spielfeld[bomberman.getTileX() + 1][bomberman.getTileY()] = this.spielfeld[bomberman.getTileX()][bomberman.getTileY()];
						this.spielfeld[bomberman.getTileX()][bomberman.getTileY()] = null;
						this.bomberman.moveTileX(1, 3);
					}
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
