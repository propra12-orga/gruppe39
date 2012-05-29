
public class BombermanEntity extends Entity
{
	public static enum STATE
	{
		ALIVE,
		DEAD
	};
	
	private STATE state;
	
	public BombermanEntity(String sprite_name, int x, int y)
	{
		super(sprite_name, x, y);
		this.state = STATE.ALIVE;
	}

	@Override
	public void collidedWith(Entity other)
	{
		if (other instanceof UnbreakableEntity)
		{
			setHorizontalSpeedMovement(0);
			setVerticalSpeedMovement(0);
		}
		else if (other instanceof BreakableEntity)
		{
			setHorizontalSpeedMovement(0);
			setVerticalSpeedMovement(0);
		}
		else if ( other instanceof BombeEntity)
		{
			setHorizontalSpeedMovement(0);
			setVerticalSpeedMovement(0);
		}
		else if (other instanceof ExplosionEntity)
		{
			// TODO...andere Sachen mšglich; z.B.: vom Spielfeld lšschen
			this.state = STATE.DEAD; 
			
		}
		else
		{
			this.setHorizontalSpeedMovement(1);
			this.setVerticalSpeedMovement(1);
		}
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
