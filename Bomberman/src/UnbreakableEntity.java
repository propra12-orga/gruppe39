import BombermanEntity.STATE;


public class UnbreakableEntity extends Entity{
	
	public static enum STATE
	{
		NO_STATE
	};
	
	public UnbreakableEntity(String sprite_name, int x, int y)
	{
		super(sprite_name, x, y);
	}
  
	
	@Override
	public void collidedWith(Entity other)
	{
		// do nothing
		
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
