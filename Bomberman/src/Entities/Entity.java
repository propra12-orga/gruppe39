package Entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import Engine.Sprite;
import Engine.SpriteStorage;

/***************************************************************************
 * Entity desc: an entity resembles an object in the game. it has its own
 * sprite/picture, its own radius for collision and few other attributes. its an
 * abstract class, so for a special entity in your game, you have to create a
 * separate class which extends all the attributes of this one and add a the
 * characteristic ones
 ***************************************************************************/
public abstract class Entity {
	// #######################################################################
	// Variables
	// #######################################################################
	// the current x location of this entity
	protected double x;
	// the current y location of this entity
	protected double y;
	// the sprite that represents this entity
	protected Sprite sprite;
	// the current speed of this entity horizontally (pixels/sec)
	protected double dx;
	// the current speed of this entity vertically (pixels/sec)
	protected double dy;
	// the rectangle used for this entity during collisions resolution
	private Rectangle me = new Rectangle();
	// the rectangle used for other entities during collision resolution
	private Rectangle him = new Rectangle();
	// for animating the movement
	// TODO: desc
	private boolean is_moving_x = false;
	private boolean is_moving_y = false;
	private int cur_movement_blocks_x = 0;
	private int cur_movement_blocks_y = 0;
	private long cur_movement_speed_x = 0;
	private long cur_movement_speed_y = 0;
	private int cur_movement_counter_x = 0;
	private int cur_movement_counter_y = 0;

	// TODO: mabye?
	// private String name = new String();

	// #######################################################################
	// Public functions
	// #######################################################################

	/***************************************************************************
	 * Entity desc: construct a entity based on a sprite image and a location.
	 * 
	 * @param: String sprite_name: name of the sprite loaded into our storage
	 *         int x: initial x cord of the entity (tile based coordinate, *32)
	 *         int y: initial y cord of the entity (tile based coordinate, *32)
	 ***************************************************************************/
	public Entity(String sprite_name, int x, int y) 
	{
		this.sprite = SpriteStorage.get().getSprite(sprite_name);
		this.x = x;
		this.y = y;
	}
	
	public Entity(String sprite_name)
	{
		this.sprite = SpriteStorage.get().getSprite(sprite_name);
		this.x = 0;
		this.y = 0;
	}
	
	public void setSprite(String sprite_name)
	{
		this.sprite = SpriteStorage.get().getSprite(sprite_name);
	}

	public void moveTileX(int blocks, long speed) 
	{
		if (this.is_moving_x == false) 
		{
			this.is_moving_x = true;
			this.cur_movement_blocks_x = blocks;
			
			if (speed > 6)
			{
				this.cur_movement_speed_x = 5;
				this.cur_movement_counter_x = Math.abs(blocks);
			}
			else if (speed < 1)
			{
				this.cur_movement_speed_x = 0;
				this.cur_movement_counter_x = Math.abs(32 * blocks);
			}
			else
			{
				this.cur_movement_speed_x = speed - 1;
				this.cur_movement_counter_x = Math.abs((32 * blocks) / (int) Math.pow(2, speed - 1));
			}
		}
	}

	private void updateTileMovementX() 
	{
		if (this.is_moving_x == true) 
		{
			this.x = this.x + 32 / ((this.cur_movement_blocks_x * 32)
					/ (int) Math.pow(2, this.cur_movement_speed_x));
			
			this.cur_movement_counter_x--;
			if (this.cur_movement_counter_x == 0)
				this.is_moving_x = false;
		}
	}

	public void moveTileY(int blocks, long speed) 
	{
		if (this.is_moving_y == false) 
		{
			this.is_moving_y = true;
			this.cur_movement_blocks_y = blocks;
			
			if (speed > 6)
			{
				this.cur_movement_speed_y = 5;
				this.cur_movement_counter_y = Math.abs(blocks);
			}
			else if (speed < 1)
			{
				this.cur_movement_speed_y = 0;
				this.cur_movement_counter_y = Math.abs(32 * blocks);
			}
			else
			{
				this.cur_movement_speed_y = speed - 1;
				this.cur_movement_counter_y = Math.abs((32 * blocks) / (int) Math.pow(2, speed - 1));
			}
		}
	}

	private void updateTileMovementY() 
	{
		if (this.is_moving_y == true) 
		{
			this.y = this.y + 32 / ((this.cur_movement_blocks_y * 32)
					/ (int) Math.pow(2, this.cur_movement_speed_y));
			
			this.cur_movement_counter_y--;
			if (this.cur_movement_counter_y == 0)
				this.is_moving_y = false;
		}
	}
	
	public void setTile(int tile_x, int tile_y)
	{
		this.setTileX(tile_x);
		this.setTileY(tile_y);
	}

	public void setTileX(int tile_x) 
	{
		if (this.is_moving_x == false)
			this.x = tile_x * 32;
	}

	public void setTileY(int tile_y) 
	{
		if (this.is_moving_y == false)
			this.y = tile_y * 32;
	}
	
	public int getTileX()
	{
			return ((int) this.x / 32);		
	}
	
	public int getTileY()
	{
			return ((int) this.y / 32);		
	}
	
	public boolean isMoving()
	{
		if (this.is_moving_x == true || this.is_moving_y == true)
			return true;
		else
			return false;
	}

	// TODO: javadoc

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	/***************************************************************************
	 * moveX desc: move the entity based on a certain amount of time passing
	 * 
	 * @param: long delta: amount of time passed in milliseconds
	 * @return: void
	 ***************************************************************************/
	public void moveX(long delta) {
		// update the location of the entity based on move speeds
		x += (delta * dx) / 1000;
	}

	public void moveY(long delta) {
		// update the location of the entity based on move speeds
		y += (delta * dy) / 1000;
	}

	/***************************************************************************
	 * setHorizontalSpeedMovement desc: obvious
	 * 
	 * @param: double dx: horizontal speed in (pixels/sec)
	 * @return: void
	 ***************************************************************************/
	public void setHorizontalSpeedMovement(double dx) {
		this.dx = dx;
	}

	/***************************************************************************
	 * setVerticalSpeedMovement desc: obvious
	 * 
	 * @param: double dx: vertical speed in (pixels/sec)
	 * @return: void
	 ***************************************************************************/
	public void setVerticalSpeedMovement(double dy) {
		this.dy = dy;
	}

	/***************************************************************************
	 * getHorizontalSpeedMovement desc: obvious
	 * 
	 * @param: void
	 * @return: return horizontal speed in (pixels/sec)
	 ***************************************************************************/
	public double getHorizontalSpeedMovement() {
		return dx;
	}

	/***************************************************************************
	 * getVerticalSpeedMovement desc: obvious
	 * 
	 * @param: void
	 * @return: return vertical speed in (pixels/sec)
	 ***************************************************************************/
	public double getVerticalSpeedMovement() {
		return dy;
	}

	/***************************************************************************
	 * draw desc: draw entity to provided graphics context
	 * 
	 * @param: Graphics graphics_context: the context to draw the entity to
	 * @return: void
	 ***************************************************************************/
	public void draw(Graphics graphics_context) 
	{
		this.updateTileMovementX();
		this.updateTileMovementY();
		sprite.draw(graphics_context, (int) this.x, (int) this.y);
	}

	/*****************************************************************************
	 * doLogic desc: do the logic associated with this entity. this method will
	 * be called periodically based on game events
	 * 
	 * @param: void
	 * @return: void
	 ***************************************************************************/
	public void doLogic() {
		// nothing here
	}

	/***************************************************************************
	 * getLocationX desc: obvious
	 * 
	 * @param: void
	 * @return: return current x location
	 ***************************************************************************/
	public int getLocationX() {
		return (int) x;
	}

	/***************************************************************************
	 * getLocationY desc: obvious
	 * 
	 * @param: void
	 * @return: return current y location
	 ***************************************************************************/
	public int getLocationY() {
		return (int) y;
	}

	/***************************************************************************
	 * collidesWith desc: check if this entity collides with another
	 * 
	 * @param: Entity other: the other entity to check the collision against
	 * @return: true if we collide with provided entity, false if not
	 ***************************************************************************/
	public boolean collidesWith(Entity other) {
		me.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
		him.setBounds((int) other.x, (int) other.y, other.sprite.getWidth(),
				other.sprite.getHeight());

		return me.intersects(him);
	}

	/***************************************************************************
	 * collidedWith desc: notification that this entity collided with another.
	 * 
	 * @param: Entity other: the other entity with which this one collided
	 * @return: true if we collide with provided entity, false if not
	 ***************************************************************************/
	public abstract void collidedWith(Entity other);

	// #######################################################################
	// Private functions
	// #######################################################################

	// nothing here
}
