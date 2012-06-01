
import java.util.Vector;


public class Spielfeld 
{
	// hoechstwahrscheinlich werden wir kein STATE f�r Spielfeld brauchen
	public static enum STATE
	{
		NO_STATE,
		GAME_RUNNING,
		GAME_PAUSED,
		GAME_OVER
	};
	
	private STATE state = STATE.NO_STATE;
	
	/*
	 * Ein K�stchen ist 32p breit und 32p hoch . 
	 * => hat das Spielfeld insgesamt 15*13 K�stchen
	 */
	public final Entity[][] spielfeld = new Entity[15][13];
	
	
	
	// spielfeld inhalt ::: 
	private Vector<BombermanEntity> bomberman_entities = new Vector<BombermanEntity>();
	private Vector<BombeEntity> bombe_entities = new Vector <BombeEntity>();
	private Vector<ExplosionEntity> explosion_entities = new Vector <ExplosionEntity>();
	private Vector<UnbreakableEntity> unbreakable_entities = new Vector <UnbreakableEntity>();
	private Vector<BreakableEntity> breakable_entities = new Vector <BreakableEntity>();

	
	private void fillUnbreakable()
	{
		// ring of unbreakables
		for (int i = 0; i < 15; i++)
		{
			Entity e = new UnbreakableEntity("Tile_UnBreakable",i * 32, 0);
			unbreakable_entities.add((UnbreakableEntity) e);	// vector
			this.spielfeld[i][0] = e;
			
			Entity e2 = new UnbreakableEntity("Tile_UnBreakable",i * 32, 32 * 12);
			unbreakable_entities.add((UnbreakableEntity) e2);	// vector
			this.spielfeld[i][12] = e2;
		}
		
		for (int i = 1; i < 13; i++)
		{
			Entity e = new UnbreakableEntity("Tile_UnBreakable", 0, 32 * i);
			unbreakable_entities.add((UnbreakableEntity) e);	// vector
			this.spielfeld[0][i] = e;
			
			Entity e2 = new UnbreakableEntity("Tile_UnBreakable", 32 * 14, 32 * i);
			unbreakable_entities.add((UnbreakableEntity) e2);	// vector
			this.spielfeld[14][i] = e2;
		}
		
	/*	
		for (i =1 ; i<15; i+=2)
		{
			for (j=1; j<13; j+=2)
			{
				e = new UnbreakableEntity("Tile_UnBreakable",i*32, j*32);
				unbreakable_entities.add((UnbreakableEntity) e);	// vector
				this.spielfeld[i][j] = e;
				
			}
		}
		*/
	}
	
	private void makeBomberman()
	{
		Entity e = new BombermanEntity("Bomberman", 32 * 1, 32 * 1);
		bomberman_entities.add((BombermanEntity) e);
		this.spielfeld[0][0] = e;
	}
	
	private void makeBreakable()
	{

		int i,j;
	    for (i=0; i<15; i+=2)
	    {	
			for (j=0; j<13; j+=2)
	    	{
				Entity e = new BreakableEntity("Tile_Breakable", i*32, j*32);
				breakable_entities.add((BreakableEntity) e);
				this.spielfeld[i][j] = e;
	       }
	    } 	

	}    
	
	public Spielfeld()
	{
		fillUnbreakable();
		makeBomberman();
		//makeBreakable();
	}
	
	public void addEntitiesToScreen(Gamescreen cur_gamescreen)
	{
		for (int i = 0; i < this.unbreakable_entities.size(); i++)
		{
			cur_gamescreen.addEntityToScreen("unbreakable" + i, this.unbreakable_entities.get(i));
		}
		
		for (int i = 0; i < this.breakable_entities.size(); i++)
		{
			cur_gamescreen.addEntityToScreen("breakable" + i, this.breakable_entities.get(i));
		}
		
		for (int i = 0; i < this.bomberman_entities.size(); i++)
		{
			cur_gamescreen.addEntityToScreen("bomberman" + i, this.bomberman_entities.get(i));
		}

		// TODO: unbreakable
		
	}

	
	public void addBomb(int x, int y)
	{
		// TODO: bombe legen + status setzen + ticken lassen etc.
		// bombe erzeugen...
		//bombe_entities.add(bomb);
	}
	
	public void addExplosion(ExplosionEntity expl)
	{
		// TODO: explosionen erzeugt die bombe in eigenem privaten thread
		explosion_entities.add(expl);
	}
	
	public void deleteEntity(Entity entity)
	{
		if (entity instanceof BombermanEntity)
		{
			// es gibt nur einen Bomberman
			bomberman_entities.remove(entity);
		}
		else if (entity instanceof ExplosionEntity)
		{
			// man braucht also nur einen der Explosionen weg zu machen und alle anderen werden mitgel�scht.
			explosion_entities.clear();
		}
		else if (entity instanceof BreakableEntity)
		{
			BreakableEntity br_e = (BreakableEntity) entity; 
			
			for (int i =0; i<breakable_entities.size(); i++)
				if (breakable_entities.get(i) == br_e )
				{
					breakable_entities.remove(i);
					break;
				}
		}
		else
		{
			// do nothing 
		}
	}
	
    public Entity getEntity(int x, int y)
    {
    	// TODO:
    	// abfrage ob x und y außerhalb vom spielfeld -> error
    	// return this.spielfeld[x][y]
    	int i;
    	// wenn entity unbreakable
    	if  ( ( ( (x/32) % 2 ) == 1 )  && ( ( (y/32) % 2 ) == 1 ) )
    	{
    		for (i=0; i<unbreakable_entities.size(); i++)
    			if (	 (unbreakable_entities.get(i).getLocationX() == x)
    				  && (unbreakable_entities.get(i).getLocationY() == y) )
    				return unbreakable_entities.get(i);
    	}
    	else
    	{
    		Vector<BombermanEntity> bomberman_entities;
		// gucken ob bomberman : es gibt nur einen deswegen get(0)
    	for (i=0; i < this.bomberman_entities.size(); i++)	
    		if ( (this.bomberman_entities.get(0).getLocationX() == x) && (this.bomberman_entities.get(0).getLocationY() == y) )
    			return this.bomberman_entities.get(0);
    		// es gibt auch nur einen bomb ; sp�ter vielleicht mit mehreren bombermen
    	 for (i=0; i<this.bombe_entities.size(); i++)
    		 if ( (this.bombe_entities.get(0).getLocationX() == x) && (this.bombe_entities.get(0).getLocationY() == y) )
    			return this.bombe_entities.get(0);
    		
    		else
    		{
    			for (i=0; i<explosion_entities.size(); i++)
    				if (  ( explosion_entities.get(i).getLocationX() == x ) && ( explosion_entities.get(i).getLocationY() == y)  )
    					return explosion_entities.get(i);
    			
    			for (i=0; i<breakable_entities.size(); i++)
    				if (  ( breakable_entities.get(i).getLocationX() == x) && (breakable_entities.get(i).getLocationY() == y) )
    					return breakable_entities.get(i);
    		}
    		
    	}
    	
    	return null;
    }
    
}
