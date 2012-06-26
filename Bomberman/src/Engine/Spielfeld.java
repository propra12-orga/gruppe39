package Engine;
// TODO TODO TODO
// delete, got replaced by GamescreenGameMode


import java.util.Vector;

import Entities.BombeEntity;
import Entities.BombermanEntity;
import Entities.BreakableEntity;
import Entities.Entity;
import Entities.ExplosionEntity;
import Entities.UnbreakableEntity;


public class Spielfeld 
{
	public Entity[][] Arena;
	
	private Gamescreen Cur_Gamescreen;
	
	// spielfeld inhalt ::: 
	private Vector<BombermanEntity> EBomberman = new Vector<BombermanEntity>();
	private Vector<BombeEntity> EBomb = new Vector <BombeEntity>();
	private Vector<ExplosionEntity> EExplosion = new Vector <ExplosionEntity>();
	private Vector<UnbreakableEntity> EUnbreakable = new Vector <UnbreakableEntity>();
	private Vector<BreakableEntity> EBreakable = new Vector <BreakableEntity>();

	public Spielfeld(Gamescreen Cur_Gamescreen)
	{
		this.Cur_Gamescreen = Cur_Gamescreen;
		
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

	public void addEntity(Entity e, String Name_Entity, int x, int y)
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
		this.Cur_Gamescreen.addEntityToScreen(e);
	}
	
	public void removeEntity(int x, int y)
	{
		Entity e = this.Arena[x][y];
		
		// check if available and remove
		if (this.Cur_Gamescreen.isEntityOnScreen(e))
			this.Cur_Gamescreen.removeEntityFromScreen(e);
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
			this.Cur_Gamescreen.removeEntityFromScreen(e);
			
			// from arena		
			this.Arena[x][y] = null;
		}		
	}
	
	public void removePlayerFromManagement(Entity e)
	{
		this.EBomberman.set(this.EBomberman.indexOf(e), null);
	}
	
	public void addBomb(int x, int y)
	{
		// benutze addEntity hier
		// TODO: 
		// koordinaten pruefen (nicht ausserhalb vom spielfeld)
		// bombenobjekt erzeugen
		// spielfeld an bombe uebergeben, damit bombe nach beenden des tickvorgangs die explosionen erzeugen kann
		// synchronisation fuer zugriff auf das spielfeld (wichtig!!!)		
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
