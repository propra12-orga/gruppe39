
public class BombermanEntity extends Entity
{
	public static enum STATE
	{
		NO_STATE,
		ALIVE,
		DEAD
	};
	
	private STATE state = STATE.NO_STATE;
	
	public BombermanEntity(String sprite_name, int x, int y)
	{
		super(sprite_name, x, y);
		this.setState(state);
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
		//else if ()
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
