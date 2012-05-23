
public class BombeEntity extends Entity{
	
	public static enum STATE
	{
		TICKING,
		EXPLODING,
		EXPLODED
	};

	public BombeEntity(String sprite_name, int x, int y)
	{
		super(sprite_name, x, y);
	}

	@Override
	public void collidedWith(Entity other) {
		// TODO Auto-generated method stub
		
	}
}
