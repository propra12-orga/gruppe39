


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
	
	private enum DIRECTION
	{
		UP,
		DOWN,
		LEFT,
		RIGHT
	};
	 
	private STATE state;
	private GamescreenGameMode Screen;
	
	private int time_exploding = 0;
	private int explosion_speed = 500;
	private int explosion_radius = 3;

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
	
	public BombeEntity(String sprite_name)
	{
		super(sprite_name);
		this.state = STATE.IDLE;
	}
	
	/**
	 * @param time
	 */
	public void activate(int time, int explosion_speed, int explosion_radius, GamescreenGameMode screen)
	{
		this.state = STATE.TICKING;
		
		this.Screen = screen;
		
		// set bomb parameters
		// TODO: recalculate the speed value to a wait value
		this.time_exploding = time;
		this.explosion_speed = explosion_speed;
		this.explosion_radius = explosion_radius;
		
		// start thread and bomb
		new Thread(this).start();
	}
	
	
	
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
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// counter for number of explosions in all directions
		int counter = 0;
		// keep track, if we hit something we arnt able to burst (for example: unbreakable walls)
		boolean[]direction_end = new boolean[4];
		for (int i = 0; i < 4; i++)
		{
			direction_end[i] = false;
		}
		// keep track of number of explosions in each direction
		int[] explosion_count = new int[4];
		
		// loop until we hit borders in every direction or until
		// we hit the max of the explosion radius
		while ((direction_end[0] == false || direction_end[1] == false ||
				direction_end[2] == false || direction_end[3] == false)
				&& counter < this.explosion_radius)
		{
			synchronized(this.Screen)
			{
				// loop through all directions
				for (int i = 0; i < 4; i++)
				{
					int direction_x = 0;
					int direction_y = 0;
					
					// set parameters for direction
					switch (i)
					{
						// UP
						case 0:
							direction_x = 0;
							direction_y = counter;
							break;
						// DOWN
						case 1:
							direction_x = 0;
							direction_y = - counter;
							break;
						// LEFT
						case 2:
							direction_x = - counter;
							direction_y = 0;
							break;
						// RIGHT
						case 3:
							direction_x = counter;
							direction_y = 0;
							break;
						default:
							break;
					}
					
					if (counter == 0)
					{
						this.setSprite("Explosion");
					}
					else
					{
						Entity e = this.Screen.getEntity(this.getTileX() + direction_x, 
														 this.getTileY() + direction_y);
						
						// check if we are still allowed to go on
						if (direction_end[i] == false)
						{
							// next space empty
							if (e == null)
							{
								this.Screen.addEntity(new ExplosionEntity("Explosion", 0, 0), 
														this.getTileX() + direction_x, 
														this.getTileY() + direction_y);	
								
								explosion_count[i]++;
							}
							// something is blocking
							else
							{
								if (e instanceof BombermanEntity)
								{
									// set bomberman dead and remove him from the arena
									((BombermanEntity) e).setState(BombermanEntity.STATE.DEAD);
									this.Screen.removePlayerFromArena(e.getLocationX(), e.getLocationY());
									
									// replace position with explosion
									this.Screen.addEntity((Entity) new ExplosionEntity("Explosion", 0, 0), 
											this.getTileX() + direction_x, 
											this.getTileY() + direction_y);
									
									explosion_count[i]++;
								}
								else if (e instanceof BreakableEntity)
								{
									// "destroy" breakable entity
									this.Screen.removeEntity(e.getTileX(), e.getTileY());
									
									// stop flames
									direction_end[i] = true;
								}
								else if (e instanceof UnbreakableEntity)
								{
									// we cant break that, stop flames
									direction_end[i] = true;
								}
								else if (e instanceof BombeEntity)
								{
									// TODO
									// make seperate tick function somehow, or possible to interrupt
									// the ticking within a thread, so we can trigger the 
									// the explosion here
								}
								/*
								else if (e instanceof ExitEntity)
								{
									// TODO
								}
								*/
							}
						}	
					}
				}	// for loop
				
				// update counter
				counter++;
			}	// synchronized
			
			// wait a bit until next explosion
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	// while
		
		// cleanup the mess
		// TODO: remove explosions with animation, remove first explosion (center)
		// first, then go to the outside with small pauses
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j <= explosion_count[i]; j++)
			{
				// center explosion
				this.Screen.removeEntity(this.getTileX(), this.getTileY());
				// UP
				this.Screen.removeEntity(this.getTileX(), this.getTileY() + j);
				// DOWN
				this.Screen.removeEntity(this.getTileX(), this.getTileY() - j);
				// LEFT
				this.Screen.removeEntity(this.getTileX() - j, this.getTileY());
				// RIGHT
				this.Screen.removeEntity(this.getTileX() + j, this.getTileY());
			}
		}
		
	}	// run
}
