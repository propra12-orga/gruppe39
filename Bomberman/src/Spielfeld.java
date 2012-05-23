import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

import quicktime.qd3d.math.Vector2D;

// Spielfeld darf nicht von Entity vererben, denn sie ist kein normales Entity !!
public class Spielfeld // extends Entity
{
	// hochwahrscheinlich werden wir kein STATE fŸr Spielfeld brauchen
	public static enum STATE
	{
		NO_STATE,
		GAME_RUNNING,
		GAME_PAUSED,
		GAME_OVER
	};
	
	private STATE state = STATE.NO_STATE;
	
	/*
	 * Ein KŠstchen ist 32p breit und 32p hoch . 
	 * => hat das Spielfeld insgesamt 15*13 KŠstchen
	 */
	private final int[][] spielfeld = new int[32 * 15][32 * 13];
	
	Entity e;
	
	
	// spielfeld inhalt ::: 
	private Vector<BombermanEntity> bomberman_entites = new Vector<BombermanEntity>();
	private Vector<BombeEntity> bombe_entities = new Vector <BombeEntity>();
	private Vector<ExplosionEntity> explosion_entities = new Vector <ExplosionEntity>();
	private Vector<UnbreakableEntity> unbreakable_entities = new Vector <UnbreakableEntity>();
	private Vector<BreakableEntity> breakable_entities = new Vector <BreakableEntity>();

	private Vector<BombermanEntity> bombe_entites;
	
	private void fillUnbreakable()
	{
		int i, j;
		for (i =1 ; i<15; i+=2)
		{
			for (j=1; j<13; j+=2)
			{
				e = new UnbreakableEntity("Unbreakable",i*32, j*32);
				unbreakable_entities.add((UnbreakableEntity) e);
			}
		}
	}
	
	private void makeBomberman()
	{
		e = new BombermanEntity("Bomberman", 0, 32);
		bomberman_entites.add((BombermanEntity) e);
	}
	
	private void makeBreakable()
	{
		e = new BreakableEntity("Breakable", 0, 32*4);
		breakable_entities.add((BreakableEntity) e);
	}
	
	public Spielfeld()
	{
		fillUnbreakable();
		makeBomberman();
		makeBreakable();
	}
/*	
	public Spielfeld(int sx, int sy)
	{
		super("Background", sx, sy);
	}
	*/


   
	/*
	public void draw(Graphics graphic_context)
	{
		super.draw(graphic_context);
		// TODO
	}
	*/
	
	public void addBomb(BombeEntity bomb)
	{
		bombe_entities.add(bomb);
	}
	
	public void addExplosion(ExplosionEntity expl)
	{
		explosion_entities.add(expl);
	}
	
	public void deleteEntity(Entity entity)
	{
		if (entity instanceof BombermanEntity)
		{
			// es gibt nur einen Bomberman
			bomberman_entites.clear();
		}
		else if (entity instanceof ExplosionEntity)
		{
			// man braucht also nur einen der Explosionen wegzumachen und alle anderen werden mitgelšscht.
			explosion_entities.clear();
		}
		else if (entity instanceof BreakableEntity)
		{
			BreakableEntity br_e = (BreakableEntity) entity; 
			
			for (int i =0; i<breakable_entities.size(); i++)
				if (       (breakable_entities.get(i).getLocationX() == br_e.getLocationX() )
						&& (breakable_entities.get(i).getLocationY() == br_e.getLocationY() )  )
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
    		// gucken ob bomberman : es gibt nur einen deswegen get(0)
    		if ( (bomberman_entites.get(0).getLocationX() == x) && (bomberman_entites.get(0).getLocationY() == y) )
    			return bomberman_entites.get(0);
    		// es gibt auch nur einen bomb ; spŠter vielleicht mit mehreren bombermen
    		else if ( (bombe_entites.get(0).getLocationX() == x) && (bombe_entites.get(0).getLocationY() == y) )
    			return bombe_entites.get(0);
    		
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
