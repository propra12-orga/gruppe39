package Entities;




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
	
	public BreakableEntity(String sprite_name)
	{
		super(sprite_name);
		this.state = STATE.UNBROKEN;
	}

	@Override
	public void collidedWith(Entity other)
	{
		
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
