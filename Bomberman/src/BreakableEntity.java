
public class BreakableEntity extends Entity
{
	public static enum STATE
	{
		UNBROKEN,
		BROKEN
	};

	public BreakableEntity(String sprite_name, int x, int y)
	{
		super(sprite_name, x, y);
	}

	@Override
	public void collidedWith(Entity other) {
		// TODO Auto-generated method stub
		
	}
}
