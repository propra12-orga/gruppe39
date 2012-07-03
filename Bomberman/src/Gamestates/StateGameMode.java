package Gamestates;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import Engine.Bomberman;
import Engine.GamescreenGameMode;
import Engine.Gamestate;
import Engine.InterfaceState;
import Engine.KeyboardInput;
import Engine.RenderWindow;
import Engine.Gamestate.STATE;
import Engine.KeyboardInput.ACTION;
import Entities.BombermanEntity;
import Entities.Entity;
import Entities.ExplosionEntity;
import Network.BombermanClient;
import Network.BombermanServer;



/*********************************************************************
* StateGameMode
* desc: 	this state resembles our current running game, aka where 
*			a player can actually blow stuff up
*			for more information on details, see the corresponding interface
**************************************************************************/
public class StateGameMode implements InterfaceState
{
	public static enum STATE
	{
		NO_STATE,
		GAME_RUNNING,
		GAME_PAUSED,
		GAME_OVER
	};
	
	private STATE state = STATE.NO_STATE;
	
	private Gamestate Cur_Gamestate;
	private GamescreenGameMode Cur_Gamescreen;
	private volatile boolean game_over = false;
	
	public StateGameMode(Gamestate Cur_State, GamescreenGameMode Cur_Screen)
	{
		// important: these are references
		this.Cur_Gamestate =  Cur_State;
		this.Cur_Gamescreen = Cur_Screen;
	}
	
	private static boolean exit = false;

	@Override
	public void init() 
	{
		// init inputs for one player
		KeyboardInput.init(2);
				
		KeyboardInput.configSetActionToKey(0, KeyEvent.VK_ENTER, KeyboardInput.ACTION.BOMB);
		
		KeyboardInput.configSetActionToKey(1, KeyEvent.VK_W, KeyboardInput.ACTION.MOVE_UP);
		KeyboardInput.configSetActionToKey(1, KeyEvent.VK_S, KeyboardInput.ACTION.MOVE_DOWN);
		KeyboardInput.configSetActionToKey(1, KeyEvent.VK_A, KeyboardInput.ACTION.MOVE_LEFT);
		KeyboardInput.configSetActionToKey(1, KeyEvent.VK_D, KeyboardInput.ACTION.MOVE_RIGHT);
		KeyboardInput.configSetActionToKey(1, KeyEvent.VK_B, KeyboardInput.ACTION.BOMB);

		this.Cur_Gamescreen.loadArena(Bomberman.level);
	}


	


	@Override
	public void main(long elapsed_time) 
	{
		// TODO
		// - input abfrage und verarbeitung
		// - kollisionsabfrage und neue position(en) berechnen, evtl. zustandsabfrage
		// - zustandsabfrage und verarbeitung der zustaende der entities

		
		
		
		
		
		
		
		// TODO: abfrage, ob bomberman figuren noch am leben sind und entsprechendes machen			
		
		// statt dead abfrage, abfrage ob entity aus spielfeld null ist
		
		// List of Bombermans active
		/*
		BombermanEntity[] Bomberman = new BombermanEntity[this.Cur_Gamescreen.getNumberActivePlayers()];
		
		for (int i = 0; i < Bomberman.length; i++)
		{
			// TODO: put code for a single player here and loop through for every player
		}
		*/
		
		
		for (int player = 0; player < this.Cur_Gamescreen.getNumberActivePlayers(); player++)
		{
			BombermanEntity Bomberman = this.Cur_Gamescreen.getBomberman(player);
			
			if (KeyboardInput.getActionState(player, KeyboardInput.ACTION.MOVE_UP) == true)
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
				
			if (KeyboardInput.getActionState(player, KeyboardInput.ACTION.MOVE_DOWN) == true)
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
			
			if (KeyboardInput.getActionState(player, KeyboardInput.ACTION.MOVE_LEFT) == true)
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
				
			if (KeyboardInput.getActionState(player, KeyboardInput.ACTION.MOVE_RIGHT) == true)
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
			
			if (KeyboardInput.getActionState(player, KeyboardInput.ACTION.BOMB) == true)
			{
				if (Bomberman.isMoving() == false)
				{
					synchronized (this.Cur_Gamescreen)
					{
						// TODO: check, ob bereits eine bombe auf aktuellem feld liegt. mehrfaches legen auf gleichem feld sollte nicht sein
						this.Cur_Gamescreen.addBomb(Bomberman.getTileX(), Bomberman.getTileY(), 500, 0, 50, 2, 250, 50);
					}
				}
			}
		}
		
			
		// check on deaths
		int death_count = 0;
		for (int player = 0; player < this.Cur_Gamescreen.getNumberActivePlayers(); player++)
		{
			BombermanEntity Bomberman = this.Cur_Gamescreen.getBomberman(player);
			
			if (Bomberman.getState() == BombermanEntity.STATE.DEAD)
			{
				if (game_over == false)
				{
					game_over = true;
					
					death_count++;
					if (player == 0)
					{
						MsgBox1 box = new MsgBox1();
						box.start();						
					}
					else
					{
						MsgBox2 box = new MsgBox2();
						box.start();

					}
				}
			}	
		}	
		
		if (exit == true)
			this.Cur_Gamestate.set(Gamestate.STATE.MAIN_MENU);
	}
	
	private class MsgBox1 extends Thread
	{
		public void run() 
		{
			JOptionPane.showMessageDialog(Cur_Gamescreen.getRenderWindow().Frame, "Spieler 2 hat gewonnen!");
			StateGameMode.exit = true;
		}
	}

	private class MsgBox2 extends Thread
	{
		public void run() 
		{
			JOptionPane.showMessageDialog(Cur_Gamescreen.getRenderWindow().Frame, "Spieler 1 hat gewonnen!");
			StateGameMode.exit = true;
		}
	}
	

	@Override
	public void shutdown() 
	{
		exit = false;
		// TODO Auto-generated method stub
		RenderWindow.Frame.dispose();
	}
}
