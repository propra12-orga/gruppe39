package Entities;
import Entities.BreakableEntity.STATE;




public class ExplosionEntity extends Entity{
	
	private static enum STATE
	{
		NO_STATE,
		EXPLODING,
		EXPLODED
	};
	
	private STATE state;

	public ExplosionEntity(String sprite_name, int x, int y)
	{
		super(sprite_name, x, y);
		this.state = STATE.NO_STATE;
	}
	
	public ExplosionEntity(String sprite_name)
	{
		super(sprite_name);
		this.state = STATE.NO_STATE;
	}

	@Override
	public void collidedWith(Entity other)
	{
		if (other instanceof BreakableEntity)
		{
			BreakableEntity bre_e = (BreakableEntity) other;
			// hier sollte diese BreakableEntity von Spielfeld gel�scht werden. Das kann in zwei M�glichkeiten gemacht werden:
			//  1- ein globales Objekt / Variable f�r Spielfeld damit man auf sie hier auch zugreiffen kann.
			//  2- ein Thread, das st�ndig l�uft und �berpr�ft: falls STATE == BROKEN dann l�schen
			bre_e.setState(BreakableEntity.STATE.BROKEN);
		}
		// TODO: Bomberman und bombe ---> done!
		if (other instanceof BombermanEntity)
		{
			// TODO...andere Sachen m�glich; z.B.: vom Spielfeld l�schen
			BombermanEntity bom_e = (BombermanEntity) other;
			bom_e.setState(BombermanEntity.STATE.DEAD);
		}
		if (other instanceof BombeEntity)
		{ 
			BombeEntity bomb_e = (BombeEntity) other;
			bomb_e.setState(BombeEntity.STATE.EXPLODING);
		}
		
		// else do nothing
	}
	public STATE getState()
	{
		return this.state;
	}
	
	public void setState(STATE state)
	{
		int x, y;
		this.state = state;
		if (state == STATE.EXPLODING)
		{
			x = this.getLocationX();
			y = this.getLocationY();
			
			//TODO!!  hier werden die Explosionsfelder hinzugef�gt, daf�r wird aber das Spielfeld gebraucht.
		}
	}
}
