


/**
 * @author Rene
 *
 */
public class BombeEntity extends Entity{
	
	public static enum STATE
	{
		TICKING,
		EXPLODING,
		EXPLODED
	};
	 
	private STATE state;
	
	private int time_exploding = 0;

	/**
	 * @param sprite_name
	 * @param x
	 * @param y
	 */
	public BombeEntity(String sprite_name, int x, int y)
	{
		super(sprite_name, x, y);
		this.state = STATE.TICKING;
	}
	
	/**
	 * @param time
	 */
	public void activate(int time)
	{
		this.state = STATE.TICKING;
		this.time_exploding = time;
	}
	
	/**
	 * @param elapsed_time
	 */
	public void updateTicking(float elapsed_time)
	{
		// TODO
	}

	/* (non-Javadoc)
	 * @see Entity#collidedWith(Entity)
	 */
	@Override
	public void collidedWith(Entity other)
	{
		// passiert nichts
		// TODO: if else abfrage
		
	}
	/**
	 * @return
	 */
	public STATE getState()
	{
		return this.state;
	}
	
	/**
	 * @param state
	 */
	public void setState(STATE state)
	{
		this.state = state;
	}
}
