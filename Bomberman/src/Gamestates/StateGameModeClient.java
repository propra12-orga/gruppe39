package Gamestates;

import java.awt.event.KeyEvent;

import Engine.Bomberman;
import Engine.Gamescreen;
import Engine.GamescreenGameMode;
import Engine.Gamestate;
import Engine.InterfaceState;
import Engine.KeyboardInput;
import Engine.RenderWindow;
import Entities.BombermanEntity;
import Entities.Entity;
import Entities.ExplosionEntity;
import Network.BombermanClient;
import Network.BombermanServer;

public class StateGameModeClient implements InterfaceState
{
	private Gamestate Cur_Gamestate;
	private GamescreenGameMode Cur_Gamescreen;
	
	private BombermanClient Client;
	
	public StateGameModeClient(Gamestate Cur_State, GamescreenGameMode Cur_Screen)
	{
		// important: these are references
		this.Cur_Gamestate = Cur_State;
		this.Cur_Gamescreen = Cur_Screen;
	}
	
	@Override
	public void init() 
	{		
		KeyboardInput.init(1);
		
		KeyboardInput.configSetActionToKey(0, KeyEvent.VK_ENTER, KeyboardInput.ACTION.BOMB);
		
		if (Bomberman.level == null)
			this.Cur_Gamescreen.loadNetworkArena();
		else
			this.Cur_Gamescreen.loadArena(Bomberman.level);
		
		this.Client = new BombermanClient(Bomberman.address, Bomberman.port);
		this.Client.start();		
		
	}

	@Override
	public void main(long elapsed_time) 
	{
		boolean[][] input_states = new boolean[2][5];
		input_states[0] = BombermanServer.convertToBoolean(this.Client.getServerInputState());
		
		//if (this.Client.getServerInputState() != 0)
		//System.out.println("client: " + this.Client.getServerInputState());
		
		input_states[1][0] = KeyboardInput.getActionState(0, KeyboardInput.ACTION.MOVE_UP);
		input_states[1][1] = KeyboardInput.getActionState(0, KeyboardInput.ACTION.MOVE_DOWN);
		input_states[1][2] = KeyboardInput.getActionState(0, KeyboardInput.ACTION.MOVE_LEFT);
		input_states[1][3] = KeyboardInput.getActionState(0, KeyboardInput.ACTION.MOVE_RIGHT);
		input_states[1][4] = KeyboardInput.getActionState(0, KeyboardInput.ACTION.BOMB);
		
		this.Client.setClientInputState(BombermanServer.convertToPackage(	input_states[1][0], 
				input_states[1][1],
				input_states[1][2],
				input_states[1][3],
				input_states[1][4]));
		
		
		// for both players
		for (int player = 0; player < 2; player++)
		{
			BombermanEntity Bomberman = this.Cur_Gamescreen.getBomberman(player);
			
			// up
			if (input_states[player][0] == true)
			{
				if (Bomberman.isMoving() == false)
				{
					synchronized (this.Cur_Gamescreen)
					{
						Entity[] E_Next = this.Cur_Gamescreen.getEntities(Bomberman.getTileX(), Bomberman.getTileY() - 1);
						
						// no entities
						if (E_Next.length == 0)
						{
							this.Cur_Gamescreen.Arena[Bomberman.getTileX()][Bomberman.getTileY() - 1].add(Bomberman);
							this.Cur_Gamescreen.Arena[Bomberman.getTileX()][Bomberman.getTileY()].remove(Bomberman);
							Bomberman.moveTileY(-1, 4);
						}
						else
						{
							// loop through all entities
							for (int i = 0; i < E_Next.length; i++)
							{
								// check explosion first
								if (E_Next[i] instanceof ExplosionEntity)
								{
									Bomberman.setState(BombermanEntity.STATE.DEAD);
									this.Cur_Gamescreen.removePlayerFromArena(0);
									break;
								}
								// allow different bombermans to be on the same spot
								else if (E_Next[i] instanceof BombermanEntity)
								{
									this.Cur_Gamescreen.Arena[Bomberman.getTileX()][Bomberman.getTileY() - 1].add(Bomberman);
									this.Cur_Gamescreen.Arena[Bomberman.getTileX()][Bomberman.getTileY()].remove(Bomberman);
									Bomberman.moveTileY(-1, 4);
								}
								/*
								else if (E_Next[i] instanceof ExitEntity)
								{
									// TODO
								}
								*/
							}
						}
					}
				}
			}
			
			// down
			if (input_states[player][1] == true)
			{
				if (Bomberman.isMoving() == false)
				{
					synchronized (this.Cur_Gamescreen)
					{
						Entity[] E_Next = this.Cur_Gamescreen.getEntities(Bomberman.getTileX(), Bomberman.getTileY() + 1);
						
						// no entities
						if (E_Next.length == 0)
						{
							this.Cur_Gamescreen.Arena[Bomberman.getTileX()][Bomberman.getTileY() + 1].add(Bomberman);
							this.Cur_Gamescreen.Arena[Bomberman.getTileX()][Bomberman.getTileY()].remove(Bomberman);
							Bomberman.moveTileY(1, 4);
						}
						else
						{
							// loop through all entities
							for (int i = 0; i < E_Next.length; i++)
							{
								// check explosion first
								if (E_Next[i] instanceof ExplosionEntity)
								{
									Bomberman.setState(BombermanEntity.STATE.DEAD);
									this.Cur_Gamescreen.removePlayerFromArena(0);
									break;
								}
								// allow different bombermans to be on the same spot
								else if (E_Next[i] instanceof BombermanEntity)
								{
									this.Cur_Gamescreen.Arena[Bomberman.getTileX()][Bomberman.getTileY() + 1].add(Bomberman);
									this.Cur_Gamescreen.Arena[Bomberman.getTileX()][Bomberman.getTileY()].remove(Bomberman);
									Bomberman.moveTileY(1, 4);
								}
								/*
								else if (E_Next[i] instanceof ExitEntity)
								{
									// TODO
								}
								*/
							}
						}
					}
				}
			}
			
			// left
			if (input_states[player][2] == true)
			{
				if (Bomberman.isMoving() == false)
				{
					synchronized (this.Cur_Gamescreen)
					{
						Entity[] E_Next = this.Cur_Gamescreen.getEntities(Bomberman.getTileX() - 1, Bomberman.getTileY());
						
						// no entities
						if (E_Next.length == 0)
						{
							this.Cur_Gamescreen.Arena[Bomberman.getTileX() - 1][Bomberman.getTileY()].add(Bomberman);
							this.Cur_Gamescreen.Arena[Bomberman.getTileX()][Bomberman.getTileY()].remove(Bomberman);
							Bomberman.moveTileX(-1, 4);
						}
						else
						{
							// loop through all entities
							for (int i = 0; i < E_Next.length; i++)
							{
								// check explosion first
								if (E_Next[i] instanceof ExplosionEntity)
								{
									Bomberman.setState(BombermanEntity.STATE.DEAD);
									this.Cur_Gamescreen.removePlayerFromArena(0);
									break;
								}
								// allow different bombermans to be on the same spot
								else if (E_Next[i] instanceof BombermanEntity)
								{
									this.Cur_Gamescreen.Arena[Bomberman.getTileX() - 1][Bomberman.getTileY()].add(Bomberman);
									this.Cur_Gamescreen.Arena[Bomberman.getTileX()][Bomberman.getTileY()].remove(Bomberman);
									Bomberman.moveTileX(-1, 4);
								}
								/*
								else if (E_Next[i] instanceof ExitEntity)
								{
									// TODO
								}
								*/
							}
						}
					}
				}
			}
			
			// right
			if (input_states[player][3] == true)
			{
				if (Bomberman.isMoving() == false)
				{
					synchronized (this.Cur_Gamescreen)
					{
						Entity[] E_Next = this.Cur_Gamescreen.getEntities(Bomberman.getTileX() + 1, Bomberman.getTileY());
						
						// no entities
						if (E_Next.length == 0)
						{
							this.Cur_Gamescreen.Arena[Bomberman.getTileX() + 1][Bomberman.getTileY()].add(Bomberman);
							this.Cur_Gamescreen.Arena[Bomberman.getTileX()][Bomberman.getTileY()].remove(Bomberman);
							Bomberman.moveTileX(1, 4);
						}
						else
						{
							// loop through all entities
							for (int i = 0; i < E_Next.length; i++)
							{
								// check explosion first
								if (E_Next[i] instanceof ExplosionEntity)
								{
									Bomberman.setState(BombermanEntity.STATE.DEAD);
									this.Cur_Gamescreen.removePlayerFromArena(0);
									break;
								}
								// allow different bombermans to be on the same spot
								else if (E_Next[i] instanceof BombermanEntity)
								{
									this.Cur_Gamescreen.Arena[Bomberman.getTileX() + 1][Bomberman.getTileY()].add(Bomberman);
									this.Cur_Gamescreen.Arena[Bomberman.getTileX()][Bomberman.getTileY()].remove(Bomberman);
									Bomberman.moveTileX(1, 4);
								}
								/*
								else if (E_Next[i] instanceof ExitEntity)
								{
									// TODO
								}
								*/
							}
						}
					}
				}
			}
			
			// bomb
			if (input_states[player][4] == true)
			{
				if (Bomberman.isMoving() == false)
				{
					synchronized (this.Cur_Gamescreen)
					{
						// TODO: check, ob bereits eine bombe auf aktuellem feld liegt. mehrfaches legen auf gleichem feld sollte nicht sein
						this.Cur_Gamescreen.addBomb(Bomberman.getTileX(), Bomberman.getTileY(), 500, 0, 50, 5, 250, 50);
					}
				}
			}
		}
					
		
		
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
}
