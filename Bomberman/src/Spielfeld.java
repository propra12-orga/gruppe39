import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

public class Spielfeld extends Entity
{
	public static enum STATE
	{
		NO_STATE,
		GAME_RUNNING,
		GAME_PAUSED,
		GAME_OVER
	};
	
	private STATE state = STATE.NO_STATE;
	
	private final int[][] spielfeld = new int[32 * 15][32 * 13];
	
	// spielfeld inhalt
	private Vector<BombermanEntity> bomberman_entites = new Vector<BombermanEntity>();
	private Vector<BombeEntity> bombe_entities = new Vector <BombeEntity>();
	private Vector<ExplosionEntity> explosion_entities = new Vector <ExplosionEntity>();
	private Vector<UnbreakableEntity> unbreakable_entities = new Vector <UnbreakableEntity>();
	private Vector <BreakableEntity> breakable_entities = new Vector <BreakableEntity>();
	
	
	
	
	
	public Spielfeld(int sx, int sy)
	{
		super("Background", sx, sy);
	}
	
    public static void main(String... args)
    {
    	new Spielfeld(0,0); 
    }

	@Override
	public void collidedWith(Entity other) 
	{
		// nothing
	}
   
	/*
	public void draw(Graphics graphic_context)
	{
		super.draw(graphic_context);
		// TODO
	}
	*/
	
	public void addEntity(Entity entity)
	{
	public void instance of BombermanEntity;
	public void instance of BombeEntity;
	public void instance of ExplosionEntity;
	public void instance of UnbreakableEntity;
	public void instance of BreakableEntity;
	
	
	}
	
	public void deleteEntity(Entity entity)
	{
	public void instance of BombermanEntity;
	public void instance of BombeEntity;
	public void instance of ExplosionEntity;
	public void instance of UnbreakableEntity;
	public void instance of BreakableEntity;
	}
	
	public void moveX(long delta)
	{
		return;
	}
	
	public void moveY(long delta)
	{
		return;
	}
    
}
