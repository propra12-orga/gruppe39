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
			this.Cur_Gamescreen.addEntityToScreen("unbreak" + i, e);
					
			Entity e2 = new UnbreakableEntity("Tile_UnBreakable",i * 32, 32 * 12);
			this.spielfeld[i][12] = e2;
			this.Cur_Gamescreen.addEntityToScreen("unbreak" + i + 15, e2);
		}
				
		for (int i = 1; i < 13; i++)
		{
			Entity e = new UnbreakableEntity("Tile_UnBreakable", 0, 32 * i);
			this.Cur_Gamescreen.addEntityToScreen("unbreak" + 30 + i, e);
			this.spielfeld[0][i] = e;
					
			Entity e2 = new UnbreakableEntity("Tile_UnBreakable", 32 * 14, 32 * i);
			this.Cur_Gamescreen.addEntityToScreen("unbreak" + 30 + i + 13, e2);
			this.spielfeld[14][i] = e2;
		}
		
		// bomberman
		Entity e = new BombermanEntity("Bomberman", 32 * 1, 32 * 1);
		this.Cur_Gamescreen.addEntityToScreen("bomberman0", e);
		this.spielfeld[1][1] = e;
		
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
				if (this.spielfeld[bomberman.getTileX()][bomberman.getTileY() + 1] == null)
				{
					this.spielfeld[bomberman.getTileX()][bomberman.getTileY() + 1] = this.spielfeld[bomberman.getTileX()][bomberman.getTileY()];
					this.spielfeld[bomberman.getTileX()][bomberman.getTileY()] = null;
					this.Cur_Gamescreen.getEntity("bomberman0").moveTileY(1, 3);
				}
			}
		}
			
		if (KeyboardInput.getActionState(0, KeyboardInput.ACTION.MOVE_DOWN) == true)
		{
			BombermanEntity bomberman = (BombermanEntity) this.Cur_Gamescreen.getEntity("bomberman0");
			
			if (bomberman.isMoving() == false)
			{
				if (this.spielfeld[bomberman.getTileX()][bomberman.getTileY() - 1] == null)
				{
					this.spielfeld[bomberman.getTileX()][bomberman.getTileY() - 1] = this.spielfeld[bomberman.getTileX()][bomberman.getTileY()];
					this.spielfeld[bomberman.getTileX()][bomberman.getTileY()] = null;
					this.Cur_Gamescreen.getEntity("bomberman0").moveTileY(-1, 3);
				}
			}
		}
		if (KeyboardInput.getActionState(0, KeyboardInput.ACTION.MOVE_LEFT) == true)
		{
			BombermanEntity bomberman = (BombermanEntity) this.Cur_Gamescreen.getEntity("bomberman0");
			
			if (bomberman.isMoving() == false)
			{
				if (this.spielfeld[bomberman.getTileX() - 1][bomberman.getTileY()] == null)
				{
					this.spielfeld[bomberman.getTileX() - 1][bomberman.getTileY()] = this.spielfeld[bomberman.getTileX()][bomberman.getTileY()];
					this.spielfeld[bomberman.getTileX()][bomberman.getTileY()] = null;
					this.Cur_Gamescreen.getEntity("bomberman0").moveTileX(-1, 3);
				}
			}
		}
			
		if (KeyboardInput.getActionState(0, KeyboardInput.ACTION.MOVE_RIGHT) == true)
		{
			BombermanEntity bomberman = (BombermanEntity) this.Cur_Gamescreen.getEntity("bomberman0");
			
			if (bomberman.isMoving() == false)
			{
				if (this.spielfeld[bomberman.getTileX() + 1][bomberman.getTileY()] == null)
				{
					this.spielfeld[bomberman.getTileX() + 1][bomberman.getTileY()] = this.spielfeld[bomberman.getTileX()][bomberman.getTileY()];
					this.spielfeld[bomberman.getTileX()][bomberman.getTileY()] = null;
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
