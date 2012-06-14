import java.util.Vector;


public class GamescreenGameMode extends Gamescreen
{
	private Entity[][] Arena;
	
	// spielfeld inhalt ::: 
	private Vector<BombermanEntity> EBomberman = new Vector<BombermanEntity>();
	private Vector<BombeEntity> EBomb = new Vector <BombeEntity>();
	private Vector<ExplosionEntity> EExplosion = new Vector <ExplosionEntity>();
	private Vector<UnbreakableEntity> EUnbreakable = new Vector <UnbreakableEntity>();
	private Vector<BreakableEntity> EBreakable = new Vector <BreakableEntity>();
	
	public GamescreenGameMode()
	{
		super();
		
		// debug only
		this.Arena = new Entity[15][13];
		// TODO
		// - level aus xml datei auslesen -> eigene xml parser klasse
		// - xml parser klasse schreibt werte, anzahl, positionen der einzelnen objekte in eine klasse
		// - Arena initialisieren: klasse mit werten aus tabelle an funktion hier uebergeben
	}
	
	
	public void addInitialArenaToScreen()
	{
		/*
		for (int i = 0; i < this.EUnbreakable.size(); i++)
		{
			this.Cur_Gamescreen.addEntityToScreen(this.EBreakable.get(i));
		}
		
		for (int i = 0; i < this.EBreakable.size(); i++)
		{
			this.Cur_Gamescreen.addEntityToScreen("breakable" + i, this.EBreakable.get(i));
		}
		
		for (int i = 0; i < this.EBomberman.size(); i++)
		{
			this.Cur_Gamescreen.addEntityToScreen("bomberman" + i, this.EBomberman.get(i));
		}
		*/
	}
	
	public Entity getEntity(int x, int y)
	{
		// TODO: check x and y if we are within boundaries
		return this.Arena[x][y];
	}

	public void addEntity(Entity e, int x, int y)
	{
		if (x > this.Arena.length || y > this.Arena[x].length)
		{
			// TODO errorhandling
			return;
		}
		
		// add to vector
		if (e instanceof BombermanEntity)
			this.EBomberman.add((BombermanEntity) e);
		else if (e instanceof BombeEntity)
			this.EBomb.add((BombeEntity) e);
		else if (e instanceof BreakableEntity)
			this.EBreakable.add((BreakableEntity) e);
		else if (e instanceof UnbreakableEntity)
			this.EUnbreakable.add((UnbreakableEntity) e);
	
		// to arena		
		this.Arena[x][y] = e;
		
		// set correct coordinates
		e.setTileX(x);
		e.setTileY(y);
		
		// add to screen/engine for drawing
		this.addEntityToScreen(e);
	}
	
	public void removeEntity(int x, int y)
	{
		Entity e = this.Arena[x][y];
		
		// check if available and remove
		if (this.isEntityOnScreen(e))
			this.removeEntityFromScreen(e);
		else
			return;
		
		// remove from vector
		if (e instanceof BombermanEntity)
			this.EBomberman.remove((BombermanEntity) e);
		else if (e instanceof BombeEntity)
			this.EBomb.remove((BombeEntity) e);
		else if (e instanceof BreakableEntity)
			this.EBreakable.remove((BreakableEntity) e);
		else if (e instanceof UnbreakableEntity)
			this.EUnbreakable.remove((UnbreakableEntity) e);
	
		// from arena		
		this.Arena[x][y] = null;
	}
	
	public void removePlayerFromArena(int x, int y)
	{
		Entity e = this.Arena[x][y];
				
		if (e instanceof BombermanEntity)
		{
			// remove from screen
			this.removeEntityFromScreen(e);
			
			// from arena		
			this.Arena[x][y] = null;
		}		
	}
	
	public void removePlayerFromManagement(Entity e)
	{
		this.EBomberman.set(this.EBomberman.indexOf(e), null);
	}
	
	public void addBomb(int x, int y, int explosion_radius, int explosion_speed)
	{
		if (this.checkCoordinates(x, y) == false)
		{
			DebugConsole.PrintError("Adding new bomb to arena failed, coordinates out of bounds");
			return;
		}
		
		BombeEntity E = new BombeEntity("Bomb");
		E.setTile(x, y);
		this.Arena[x][y] = E;
		this.addEntityToScreen(E);
		
		// TODO
		E.activate(5, explosion_speed, explosion_radius, this);
			
		
		
		// TODO 
		// benutze addEntity hier
		// TODO: 
		// koordinaten pruefen (nicht ausserhalb vom spielfeld)
		// bombenobjekt erzeugen
		// spielfeld an bombe uebergeben, damit bombe nach beenden des tickvorgangs die explosionen erzeugen kann
		// synchronisation fuer zugriff auf das spielfeld (wichtig!!!)		
	}
	
	private boolean checkCoordinates(int x, int y)
	{
		if (x > this.Arena.length || y > this.Arena.length)
			return false;
		else
			return true;
	}
	
	public void fillArena(/* parameter */)
	{
		// TODO 
		// arbeite alle kategorien hier ab und fuelle das spielfeld
		// entsprechend der werte, die aus der datei ausgelesen wurden
		// fuelle hierzu nicht nur Arena, sondern auch die vektoren
	}
	
	public void clearArena()
	{
		for (int i = 0; i < this.Arena.length; i++)
		{
			for (int j = 0; j < this.Arena[i].length; j++)
			{
				this.Arena[i][j] = null;
			}
		}
	}
	
}
