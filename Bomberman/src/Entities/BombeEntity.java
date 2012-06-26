package Entities;

import java.util.concurrent.atomic.AtomicBoolean;

import Engine.GamescreenGameMode;





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
	private GamescreenGameMode Screen;
	private Thread Thread_Bomb;
	public AtomicBoolean no_interrupt = new AtomicBoolean(false);
	
	
	private int time_exploding = 500;
	private int delay_ticking = 0;
	private int explosion_speed = 50;
	private int explosion_radius = 1;
	private int delay_flames = 250;
	private int removing_speed = 50;


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
	
	
	public void setParamters(int time_exploding, int delay_ticking, int explosion_speed, int explosion_radius, int delay_flames, int removing_speed)
	{
		this.time_exploding = time_exploding;
		this.delay_ticking = delay_ticking;
		this.explosion_speed = explosion_speed;
		this.explosion_radius = explosion_radius;
		this.delay_flames = delay_flames;
		this.removing_speed = removing_speed;		
	}
	
	public void activate(GamescreenGameMode screen)
	{
		this.state = STATE.TICKING;
		
		this.Screen = screen;
		
		// start thread and bomb
		this.Thread_Bomb = new Thread(this);
		this.Thread_Bomb.start();
	}
	
	public Thread getBombThread()
	{
		return this.Thread_Bomb;
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
		try 
		{
			this.startBombTick();
			this.bombTicking();
		} 
		catch (InterruptedException e) 
		{
			// do nothing, because we continue with the explosion
		}

		this.no_interrupt.set(true);
		this.bombExplode();		
	}	// run
	
	private void startBombTick() throws InterruptedException 
	{
		// delay of bomb tick
		try 
		{
			Thread.sleep(this.delay_ticking);
		} 
		catch (InterruptedException e) 
		{
			throw e;
		}
	}
	
	private void bombTicking() throws InterruptedException
	{
		// start bombtick
		for (int i = 3; i > 0; i--)
		{
			this.setSprite("Bombtick_" + i);
				
			try 
			{
				Thread.sleep(this.time_exploding);
			} 
			catch (InterruptedException e) 
			{
				throw e;
			}
		}
	}
	
	private void bombExplode()
	{
		// counter for number of explosions in all directions
		int counter = -1;
		// keep track, if we hit something we arnt able to burst (for example: unbreakable walls)
		boolean[]direction_end = new boolean[4];
		// keep track of number of explosions in each direction
		int[] explosion_count = new int[4];
		// init
		for (int i = 0; i < 4; i++)
		{
			explosion_count[i] = 0;
			direction_end[i] = false;
		}
				
				
		// loop until we hit borders in every direction or until
		// we hit the max of the explosion radius
		while ((direction_end[0] == false || direction_end[1] == false ||
				direction_end[2] == false || direction_end[3] == false)
				&& counter < this.explosion_radius)
		{
			counter++;
					
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
							direction_y = - counter;
							break;
						// DOWN
						case 1:
							direction_y = counter;
							break;
						// LEFT
						case 2:
							direction_x = - counter;
							break;
						// RIGHT
						case 3:
							direction_x = counter;
							break;
						default:
							break;
					}

					// a bit hacky: replace sprite only for bomb itself
					if (counter == 0 && i == 0)
					{			
						// remove bomb from arena
						this.Screen.Arena[this.getTileX()][this.getTileY()].remove(this);
						this.Screen.removeEntityFromScreen(this);
						
						// check if by accident, a bomberman stands within the bomb
						if (this.Screen.Arena[this.getTileX()][this.getTileY()].isEmpty() == false)
						{
							int size = this.Screen.Arena[this.getTileX()][this.getTileY()].size();
							for (int j = 0; j < size; j++)
							{
								Entity E = this.Screen.Arena[this.getTileX()][this.getTileY()].get(j);
								if (E instanceof BombermanEntity)
								{
									((BombermanEntity) E).setState(BombermanEntity.STATE.DEAD);
									this.Screen.removePlayerFromArena(this.getTileX(), this.getTileY(), j);
								}
							}
						}
						
						// and add explosion instead
						this.Screen.addExplosion(this.getTileX(), this.getTileY());	

						break;
					}
							
					// if we are out of bounds, skip
					if (this.Screen.checkCoordinates(this.getTileX() + direction_x, this.getTileY() + direction_y) == false)
						continue;
				
								
					// check if we are still allowed to go on
					if (direction_end[i] == false)
					{
						// no objects on the field
						if (this.Screen.Arena[this.getTileX() + direction_x][this.getTileY() + direction_y].isEmpty() == true)
						{
							this.Screen.addExplosion(this.getTileX() + direction_x, this.getTileY() + direction_y);
										
							explosion_count[i]++;
							continue;
						}
								
						// get next entity from one field
						for (int j = 0; j < this.Screen.Arena[this.getTileX() + direction_x][this.getTileY() + direction_y].size(); j++)
						{
							// get entity
							Entity e = this.Screen.getEntity(this.getTileX() + direction_x, 
									 this.getTileY() + direction_y, j);
									
							// check unbreakable first
							if (e instanceof UnbreakableEntity)
							{
								// we cant break that, stop flames
								direction_end[i] = true;
								break;
							}
							else if (e instanceof BreakableEntity)
							{
								// "destroy" breakable entity
								this.Screen.removeEntity(e.getTileX(), e.getTileY(), j);
												
								// stop flames
								direction_end[i] = true;
								break;
							}
							else if (e instanceof BombeEntity)
							{				
								// TODO
								// make seperate tick function somehow, or possible to interrupt
								// the ticking within a thread, so we can trigger the 
								// the explosion here
								if (((BombeEntity) e).no_interrupt.get() == false)
									((BombeEntity) e).getBombThread().interrupt();
								
								direction_end[i] = true;
								break;
							}
							else if (e instanceof ExplosionEntity)
							{						
								direction_end[i] = true;
								break;
							}
							else if (e instanceof BombermanEntity)
							{
								// set bomberman dead and remove him from the arena
								int number = ((BombermanEntity) e).getPlayerNumber();
								//System.out.println("number player: " + number);
								this.Screen.removePlayerFromArena(number);
								((BombermanEntity) e).setState(BombermanEntity.STATE.DEAD);
											
								// replace position with explosion
								this.Screen.addExplosion(this.getTileX() + direction_x, this.getTileY() + direction_y);
												
								explosion_count[i]++;
							}
							/*
							else if (e instanceof ExitEntity)
							{
								// TODO
							}
							*/			
						}	
					}
				}	// for loop
			}	// synchronized
					
			// wait a bit until next explosion
			try 
			{
				Thread.sleep(this.explosion_speed);
			} 
			catch (InterruptedException e) 
			{
				// do nothing
				//e.printStackTrace();
			}
		}	// while
				
				
		// leave the flames for a bit on the screen
		try {
			Thread.sleep(this.delay_flames);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
				
				
		// cleanup the mess
		// remove explosions in the same order we created them
		// with a specified speed (pause of thread)
				
		// FIXME:
		// actually we have to loop through all items here as well and
		// identify which one is an explosion.
		// but having the explosion as the stronges entity, killing everything
		// else, where its set, we dont have to do that, because its the only
		// item on that field
		// center explosion	
		for (int i = 0; i <= this.explosion_radius; i++)
		{
			synchronized (this.Screen)
			{
				// center flame (where bomb used to be)
				if (i == 0)
				{
					this.Screen.removeEntity(this.getTileX(), this.getTileY(), 0);
				}
				// everything else in all 4 directions
				else
				{				
					if (i <= explosion_count[0])
						this.Screen.removeEntity(this.getTileX(), this.getTileY() - i, 0);
					if (i <= explosion_count[1])
						this.Screen.removeEntity(this.getTileX(), this.getTileY() + i, 0);
					if (i <= explosion_count[2])
						this.Screen.removeEntity(this.getTileX() - i, this.getTileY(), 0);
					if (i <= explosion_count[3])
						this.Screen.removeEntity(this.getTileX() + i, this.getTileY(), 0);
				}
			}
					
			// wait a bit before removing next explosion
			try 
			{
				Thread.sleep(this.removing_speed);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
				
	}

}
