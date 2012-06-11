


/**
 * @author Rene
 *
 */
public class BombeEntity extends Entity implements Runnable{
	
	public static enum STATE
	{
		IDLE,
		TICKING,
		EXPLODING,
		EXPLODED
	};
	 
	private STATE state;
	private Spielfeld SPFeld;
	
	private int time_exploding = 0;

	/**
	 * @param sprite_name
	 * @param x
	 * @param y
	 */
	public BombeEntity(String sprite_name, int x, int y)
	{
		super(sprite_name, x, y);
		this.state = STATE.IDLE;
	}
	
	/**
	 * @param time
	 */
	public void activate(int time, Spielfeld SPFeld)
	{
		this.state = STATE.TICKING;
		
		this.SPFeld = SPFeld;
		
		// start thread and bomb
		new Thread(this);
		// TODO
		//this.time_exploding = time;
	}
	
	
	
	/**
	 * @param elapsed_time
	 */
	/*public void updateTicking(float elapsed_time)
{
		// TODO
	}*/

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

	@Override
	public void run() 
	{
		for (int i = 3; i > 0; i--)
		{
			this.setSprite("Bombtick_" + i);
			
			try {
				this.wait(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		boolean done = false;
		int counter = 0;
		while (!done)
		{
			synchronized(this.SPFeld)
			{
				if (counter == 0)
				{
					this.setSprite("Explosion");
				}
				else
				{
					Entity e = this.SPFeld.Arena[this.getLocationX()][this.getLocationY() + counter];
					
					// up
					if (e == null)
					{
						this.SPFeld.addEntity(new ExplosionEntity("Explosion", 0, 0), 
												"Exp_" + this.x + "_" + this.y,
												this.getLocationX(), 
												this.getLocationY() + counter);	
					}
					else
					{
						if (e instanceof BombermanEntity)
						{
							((BombermanEntity) e).setState(BombermanEntity.STATE.DEAD);
							//this.SPFeld.removePlayerFromArena(Entity_Name)
							
							
							this.SPFeld.addEntity((Entity) new ExplosionEntity("Explosion", 0, 0), 
									("Explosion_up" + counter + "_" + this.x + "_" + this.y),
									this.getLocationX(), 
									this.getLocationY() + counter);
						}
					}
				}
				
				
			}
			
			try {
				this.wait(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		// aufraeumen von explosionen und leeren von spielfeld
		
		
		
		
	}
}
