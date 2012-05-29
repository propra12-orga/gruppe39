import java.awt.Graphics;
import java.awt.Rectangle;


//=======================================================================
// Entity
// desc: 	an entity resembles an object in the game. it has its own 
//			sprite/picture, its own radius for collision and few other
//			attributes. its an abstract class, so for a special entity
//			in your game, you have to create a separate class which
//			extends all the attributes of this one and add a the 
//			characteristic ones
//=======================================================================
public abstract class Entity 
{
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
	
	// TODO: mabye?
	// private String name = new String();
	
	
	// #######################################################################
	// Public functions
	// #######################################################################
	
	// =======================================================================
	// Entity
	// desc: 	construct a entity based on a sprite image and a location.
	// param: 	String sprite_name: name of the sprite loaded into our storage
	//			int x: initial x cord of the entity
	//			int y: initial y cord of the entity
	// =======================================================================
	public Entity(String sprite_name, int x, int y) 
	{
		this.sprite = SpriteStorage.get().getSprite(sprite_name);
		this.x = x;
		this.y = y;
	}
	
	
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	
	// =======================================================================
	// moveX
	// desc: 	move the entity based on a certain amount of time passing
	// param: 	long delta: amount of time passed in milliseconds
	// ret:		void
	// =======================================================================
	public void moveX(long delta) 
	{
		// update the location of the entity based on move speeds
		x += (delta * dx) / 1000;
	}
	
	public void moveY(long delta) 
	{
		// update the location of the entity based on move speeds
		y += (delta * dy) / 1000;
	}
	
	
	// =======================================================================
	// setHorizontalSpeedMovement
	// desc: 	obvious
	// param: 	double dx: horizontal speed in (pixels/sec)
	// ret:		void
	// =======================================================================
	public void setHorizontalSpeedMovement(double dx) 
	{
		this.dx = dx;
	}

	
	// =======================================================================
	// setVerticalSpeedMovement
	// desc: 	obvious
	// param: 	double dx: vertical speed in (pixels/sec)
	// ret:		void
	// =======================================================================
	public void setVerticalSpeedMovement(double dy) 
	{
		this.dy = dy;
	}
	
	
	// =======================================================================
	// getHorizontalSpeedMovement
	// desc: 	obvious
	// param: 	void
	// ret:		return horizontal speed in (pixels/sec)
	// =======================================================================
	public double getHorizontalSpeedMovement() 
	{
		return dx;
	}

	
	// =======================================================================
	// getVerticalSpeedMovement
	// desc: 	obvious
	// param: 	void
	// ret:		return vertical speed in (pixels/sec)
	// =======================================================================
	public double getVerticalSpeedMovement() 
	{
		return dy;
	}
	
	
	// =======================================================================
	// draw
	// desc: 	draw entity to provided graphics context
	// param: 	Graphics graphics_context: the context to draw the entity to
	// ret:		void
	// =======================================================================
	public void draw(Graphics graphics_context) 
	{
		sprite.draw(graphics_context, (int) x, (int) y);
	}
	
	
	// =======================================================================
	// doLogic
	// desc: 	do the logic associated with this entity. this method
	//			will be called periodically based on game events
	// param: 	void
	// ret:		void
	// =======================================================================
	public void doLogic() 
	{
		// nothing here
	}
	
	
	// =======================================================================
	// getLocationX
	// desc: 	obvious
	// param: 	void
	// ret:		return current x location
	// =======================================================================
	public int getLocationX() 
	{
		return (int) x;
	}

	
	// =======================================================================
	// getLocationY
	// desc: 	obvious
	// param: 	void
	// ret:		return current y location
	// =======================================================================
	public int getLocationY() 
	{
		return (int) y;
	}
	
	
	// =======================================================================
	// collidesWith
	// desc: 	check if this entity colides with another
	// param: 	Entity other: the other entity to check the collision against
	// ret:		true if we collide with provided entity, false if not
	// =======================================================================
	public boolean collidesWith(Entity other) 
	{
		me.setBounds((int) x,(int) y,sprite.getWidth(),sprite.getHeight());
		him.setBounds((int) other.x,(int) other.y,other.sprite.getWidth(),other.sprite.getHeight());

		return me.intersects(him);
	}
	
	
	// =======================================================================
	// collidedWith
	// desc: 	notification that this entity collided with another.
	// param: 	Entity other: the other entity with which this one collided
	// ret:		true if we collide with provided entity, false if not
	// =======================================================================
	public abstract void collidedWith(Entity other);
	
	
	
	// #######################################################################
	// Private functions
	// #######################################################################
	
	// nothing here
}