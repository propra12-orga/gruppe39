


public class BombeEntity extends Entity{
	
	public static enum STATE
	{
		TICKING,
		EXPLODING,
		EXPLODED
	};
	 
	private STATE state;
	
	private int time_exploding = 0;

	public BombeEntity(String sprite_name, int x, int y)
	{
		super(sprite_name, x, y);
		this.state = STATE.TICKING;
	}
	
	public void activate(int time)
	{
		this.state = STATE.TICKING;
		this.time_exploding = time;
	}
	
	public void updateTicking(float elapsed_time)
	{
		// TODO
	}

	@Override
	public void collidedWith(Entity other)
	{
		// passiert nichts
		// TODO: if else abfrage
		
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
