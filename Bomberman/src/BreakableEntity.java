


public class BreakableEntity extends Entity
{
	public static enum STATE
	{
		UNBROKEN,
		BROKEN
	};
	
	private STATE state;

	public BreakableEntity(String sprite_name, int x, int y)
	{
		super(sprite_name, x, y);
		this.state = STATE.UNBROKEN;
	}

	@Override
	public void collidedWith(Entity other)
	{
		if (other instanceof ExplosionEntity)
		{
			// hier sollte diese BreakableEntity von Spielfeld gel�scht werden. Das kann in zwei M�glichkeiten gemacht werden:
			//  1- ein globales Objekt / Variable f�r Spielfeld damit man auf sie hier auch zugreiffen kann.
			//  2- ein Thread, das st�ndig l�uft und �berpr�ft: falls STATE == BROKEN dann l�schen
			this.state = STATE.BROKEN;
		}
		
		// else do nothing
	}
	public STATE getState()
	{
		return this.state;
	}
	
	public void setState(STATE state)
	{
		this.state = state;
	}
}
