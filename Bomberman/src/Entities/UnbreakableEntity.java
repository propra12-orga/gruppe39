package Entities;




public class UnbreakableEntity extends Entity{
	
	public static enum STATE
	{
		NO_STATE
	};
	
	private STATE state;
	
	public UnbreakableEntity(String sprite_name, int x, int y)
	{
		super(sprite_name, x, y);
	}
	
	public UnbreakableEntity(String sprite_name)
	{
		super(sprite_name);
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
