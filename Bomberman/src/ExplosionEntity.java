


public class ExplosionEntity extends Entity{
	
	private static enum STATE
	{
		NO_STATE,
		EXPLODING
	};
	
	private STATE state;

	public ExplosionEntity(String sprite_name, int x, int y)
	{
		super(sprite_name, x, y);
		this.state = STATE.NO_STATE;
	}

	@Override
	public void collidedWith(Entity other) {
		// TODO Auto-generated method stub
		
	}
	public STATE getState()
	{
		return this.state;
	}
	
	public void setState(STATE state)
	{
		this.state = state;
		if (state == STATE.EXPLODING)
		{
			
		}
	}
}
