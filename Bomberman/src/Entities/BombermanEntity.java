package Entities;


public class BombermanEntity extends Entity
{
	public static enum STATE
	{
		ALIVE,
		DEAD
	};
	
	private STATE state;
	private int player_number;
	
	public BombermanEntity(String sprite_name, int x, int y, int player_number)
	{
		super(sprite_name, x, y);
		this.state = STATE.ALIVE;
		this.player_number = player_number;
	}
	
	public BombermanEntity(String sprite_name, int player_number)
	{		
		super(sprite_name, 0, 0);
		this.state = STATE.ALIVE;
		this.player_number = player_number;
	}
	
	public int getPlayerNumber()
	{
		return this.player_number;
	}

	@Override
	public void collidedWith(Entity other)
	{
		if (other instanceof UnbreakableEntity)
		{
			setHorizontalSpeedMovement(0);
			setVerticalSpeedMovement(0);
		}
		else if (other instanceof BreakableEntity)
		{
			setHorizontalSpeedMovement(0);
			setVerticalSpeedMovement(0);
		}
		else if ( other instanceof BombeEntity)
		{
			setHorizontalSpeedMovement(0);
			setVerticalSpeedMovement(0);
		}
		else if (other instanceof ExplosionEntity)
		{
			// TODO...andere Sachen m�glich; z.B.: vom Spielfeld l�schen
			this.state = STATE.DEAD; 
			
		}
		else
		{
			this.setHorizontalSpeedMovement(1);
			this.setVerticalSpeedMovement(1);
		}
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
