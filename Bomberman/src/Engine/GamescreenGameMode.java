package Engine;
import java.awt.Point;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import javax.swing.JOptionPane;

import Entities.BombeEntity;
import Entities.BombermanEntity;
import Entities.BreakableEntity;
import Entities.Entity;
import Entities.ExplosionEntity;
import Entities.UnbreakableEntity;


public class GamescreenGameMode extends Gamescreen
{
	public Vector<Entity>[][] Arena;
	
	// spielfeld inhalt ::: 

	private HashMap<Integer, BombermanEntity> EBomberman = new HashMap<Integer, BombermanEntity>();
	//private Vector<BombermanEntity> EBomberman = new Vector<BombermanEntity>();
	/*	private Vector<BombeEntity> EBomb = new Vector <BombeEntity>();
	private Vector<ExplosionEntity> EExplosion = new Vector <ExplosionEntity>();
	private Vector<UnbreakableEntity> EUnbreakable = new Vector <UnbreakableEntity>();
	private Vector<BreakableEntity> EBreakable = new Vector <BreakableEntity>();
	*/
	public GamescreenGameMode(RenderWindow Render_Window)
	{
		super(Render_Window);
		
		// debug only
		this.Arena = (Vector<Entity>[][]) new Vector[15][13];
		
		for (int i = 0; i < this.Arena.length; i++)
		{
			for (int j = 0; j < this.Arena[i].length; j++)
			{
				this.Arena[i][j] = new Vector<Entity>();
			}
		}
		// TODO
		// - level aus xml datei auslesen -> eigene xml parser klasse
		// - xml parser klasse schreibt werte, anzahl, positionen der einzelnen objekte in eine klasse
		// - Arena initialisieren: klasse mit werten aus tabelle an funktion hier uebergeben
	}
	
	// ===================================
	// GET
	// ===================================
	public Entity[] getEntities(int x, int y)
	{
		if (this.checkCoordinates(x, y) == false)
		{
			DebugConsole.PrintError("getting entity failed, out of bounds");
			return null;
		}
		
		Entity[] array = new Entity[0];
		
		return this.Arena[x][y].toArray(array);
	}
	
	public Entity getEntity(int x, int y, int z)
	{
		if (this.checkCoordinates(x, y) == false)
		{
			DebugConsole.PrintError("getting entity failed, out of bounds");
			return null;
		}
		
		return this.Arena[x][y].get(z);
	}
	
	
	public BombermanEntity getBomberman(int player)
	{
		BombermanEntity E = this.EBomberman.get(new Integer(player));
		
		if (E == null)
			DebugConsole.PrintError("Invalid player id, not available in hash map");
		
		return E;		
	}
	
	
	public int getNumberActivePlayers()
	{
		return this.EBomberman.size();
	}
	
	// ===================================
	// REMOVE
	// ===================================
	
	// COMPLETELY remove/delete entity
	public void removeEntity(int x, int y, int z)
	{
		Entity e = this.Arena[x][y].get(z);
		
		// check if available and remove
		if (this.isEntityOnScreen(e))
			this.removeEntityFromScreen(e);
		
		// if we have a bomberman entity, remove it from the hash map
		if (e instanceof BombermanEntity)
			this.EBomberman.remove(((BombermanEntity) e).getPlayerNumber());
	
		// from arena		
		this.Arena[x][y].remove(z);
	}
	
	// use this if a player dies in the arena, so we still have the
	// datastructure available for scoring and stuff
	public void removePlayerFromArena(int x, int y, int z)
	{
		if (this.checkCoordinates(x, y) == false)
		{
			DebugConsole.PrintError("removing player from arena failed, invalid coordinates");
			return;
		}
		
		Entity e = this.Arena[x][y].get(z);
				
		if (e instanceof BombermanEntity)
		{
			// remove from screen
			this.removeEntityFromScreen(e);
			
			// from arena		
			this.Arena[x][y].remove(z);
		}	
		else
		{
			DebugConsole.PrintError("entity not an instance of BombermanEntity");
		}
	}
	
	public void removePlayerFromArena(int player)
	{
		if (this.EBomberman.containsKey(player) == false)
		{
			DebugConsole.PrintError("invalid player number");
			return;
		}
		
		BombermanEntity E = this.EBomberman.get(player);

		this.removeEntityFromScreen(E);
		this.Arena[E.getTileX()][E.getTileY()].remove(E);
	}
	
	// use this, if above function was called before.
	// this removes the bomberman entity from the hash table, so its gone forever
	public void removePlayerFromManagement(int player)
	{
		BombermanEntity E = this.EBomberman.get(player);
	
		if (E == null)
			DebugConsole.PrintError("no such player does exist, id: " + player);
	}
	
	
	public void clearArena()
	{
		// clear arena and remove entities from screen
		for (int i = 0; i < this.Arena.length; i++)
		{
			for (int j = 0; j < this.Arena[i].length; j++)
			{
				for (int k = 0; k < this.Arena[i][j].size(); k++)
				{
					this.removeEntityFromScreen(this.getEntity(i, j, k));
					this.Arena[i][j].remove(k);
				}
				
			}
		}
		
		// clear hash map of the bomberman management
		this.EBomberman.clear();		
	}
	
	// ===================================
	// ADD
	// ===================================
	
	public void addBomberman(int x, int y, int player_number)
	{
		if (this.checkCoordinates(x, y) == false)
		{
			DebugConsole.PrintError("Adding new bomberman to arena failed, coordinates out of bounds");
			return;
		}

		// TODO: acording to player_number, different colour
		
		BombermanEntity e = new BombermanEntity("Bomberman", player_number);
		e.setTile(x, y);
		this.addEntityToScreen(e);
		
		// add to arena 
		this.Arena[x][y].add(e);
	
		// and to management system
		this.EBomberman.put(new Integer(player_number), e);
	}
	

	public void addBomb(int x, int y, int time_exploding, int delay_ticking, int explosion_speed, int explosion_radius, int delay_flames, int removing_speed)
	{
		if (this.checkCoordinates(x, y) == false)
		{
			DebugConsole.PrintError("Adding new bomb to arena failed, coordinates out of bounds");
			return;
		}
		
		BombeEntity E = new BombeEntity("Bomb");
		E.setTile(x, y);
		this.Arena[x][y].add(E);
		this.addEntityToScreen(E);
		
		// set parameters
		E.setParamters(time_exploding, delay_ticking, explosion_speed, explosion_radius, delay_flames, removing_speed);
		
		// activate bomb
		E.activate(this);
	}
	
	public void addUnbreakable(int x, int y)
	{
		if (this.checkCoordinates(x, y) == false)
		{
			DebugConsole.PrintError("Adding new unbreakable to arena failed, coordinates out of bounds");
			return;
		}
		
		UnbreakableEntity E = new UnbreakableEntity("Tile_UnBreakable");
		E.setTile(x, y);
		
		this.Arena[x][y].add(E);
		this.addEntityToScreen(E);
	}
	
	public void addBreakable(int x, int y)
	{
		if (this.checkCoordinates(x, y) == false)
		{
			DebugConsole.PrintError("Adding new breakable to arena failed, coordinates out of bounds");
			return;
		}
		
		BreakableEntity E = new BreakableEntity("Tile_Breakable");
		E.setTile(x, y);
		
		this.Arena[x][y].add(E);
		this.addEntityToScreen(E);
	}
	
	public void addExit(int x, int y)
	{
		// TODO
	}
	
	public void addExplosion(int x, int y)
	{
		if (this.checkCoordinates(x, y) == false)
		{
			DebugConsole.PrintError("Adding new explosion to arena failed, coordinates out of bounds");
			return;
		}
		
		ExplosionEntity E = new ExplosionEntity("Explosion");
		E.setTile(x, y);
		
		this.Arena[x][y].add(E);
		this.addEntityToScreen(E);
	}
	
	
	
	public void loadArena(String filename)
	{
		if (filename == null)
		{
			this.loadNetworkArena();
		}
		else if (filename == "map.ran")
		{
			this.loadRandomArena();
		}
		else if (filename != null)
		{
			Parser LevelParser = new Parser(filename);
			
			Vector<Point> Bombermans = LevelParser.getBombermans();
			Vector<Point> Breakables = LevelParser.getBreakable();
			Vector<Point> Unbreakables = LevelParser.getUnbreakable();
			Vector<Point> Exit = LevelParser.getExit();
			
			if (Bombermans == null || Breakables == null || Unbreakables == null || Exit == null)
			{
				DebugConsole.PrintError("level file error");
				JOptionPane.showMessageDialog(super.getRenderWindow(), "Fehler in Leveldatei");
				while (true)
				{}
			}
			
			for (int i = 0; i < Bombermans.size(); i++)
			{
				this.addBomberman(Bombermans.get(i).x, Bombermans.get(i).y, i);
			}
			
			for (int i = 0; i < Breakables.size(); i++)
			{
				this.addBreakable(Breakables.get(i).x, Breakables.get(i).y);
			}
			
			for (int i = 0; i < Unbreakables.size(); i++)
			{
				this.addUnbreakable(Unbreakables.get(i).x, Unbreakables.get(i).y);
			}
			
			for (int i = 0; i < Exit.size(); i++)
			{
				this.addExit(Exit.get(i).x, Exit.get(i).y);
			}
		}
		else
		{
			this.loadRandomArena();
		}
	}
	
	public void loadNetworkArena()
	{
		this.addBomberman(1, 1, 0);
		this.addBomberman(13, 11, 1);
		
		this.generateUnbreakablesField();
		this.generateUnbreakablesFrame();
	}
	
	public void loadRandomArena()
	{
		// bomberman
		this.addBomberman(1, 1, 0);
		this.addBomberman(13, 11, 1);
	
		this.generateUnbreakablesFrame();
		this.generateUnbreakablesField();
		this.generateRandomBreakables();		
	}
	
	private void generateRandomBreakables()
	{
		// add random breakables
		// second row
		for (int i = 3; i < 12; i++)
		{
			if (this.randomNumber(0, 1) == 1)
			{
				this.addBreakable(i, 1);
			}
		}
		
		// third row
		for (int i = 3; i <= 13; i += 2)
		{
			if (this.randomNumber(0, 1) == 1)
			{
				this.addBreakable(i, 2);
			}
		}
		
		// fourth to tenth row
		for (int i = 3; i <= 9; i++)
		{
			for (int j = 1; j <= 13; j += 2)
			{
				if (this.randomNumber(0, 1) == 1)
				{
					this.addBreakable(j, i);
				}
			}
		}
		
		// eleventh row
		for (int i = 1; i <= 11; i += 2)
		{
			if (this.randomNumber(0, 1) == 1)
			{
				this.addBreakable(i, 10);
			}
		}

		// last row
		for (int i = 1; i <= 11; i++)
		{
			if (this.randomNumber(0, 1) == 1)
			{
				this.addBreakable(i, 11);
			}
		}
	}
	
	private void generateUnbreakablesFrame()
	{
		// temp static arena
		// ring of unbreakables
		for (int i = 0; i < 15; i++)
		{
			this.addUnbreakable(i, 0);
			this.addUnbreakable(i, 12);
		}
						
		for (int i = 1; i < 13; i++)
		{
			this.addUnbreakable(0, i);
			this.addUnbreakable(14, i);
		}			
	}
	
	private void generateUnbreakablesField()
	{	
		// 15 * 13
		for (int i = 2; i <= 12; i += 2)
		{
			for (int j = 2; j <= 12; j += 2)
			{
				this.addUnbreakable(i, j);
			}
		}
	}
	
	private int randomNumber(int min, int max) 
	{
		max += 3;
		int tmp = min + (new Random()).nextInt(max-min);
		if (tmp == 0)
			return 0;
		else 
			return 1;
	}
	
	public boolean checkCoordinates(int x, int y)
	{
		if (x >= 0 && y >= 0 && x < this.Arena.length && y < this.Arena[x].length)
			return true;
		else
			return false;
	}
	
}
