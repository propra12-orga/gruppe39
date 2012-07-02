package Engine;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;


public class Generator 
{
	
    Random generator = new Random();

	private Vector<Point> Bombermans = new Vector<Point>();
	private Vector<Point> Breakables = new Vector<Point>();
	private Vector<Point> Unbreakables = new Vector<Point>();
	private Vector<Point> Exit = new Vector<Point>();
	
	
	/**
	 * Die Dimension des FEldes ist 15 x 13
	 */

	public Generator()
	{

			fillVectors();
	}
	/**  funktion fuer zufaellige Zahlen */
	
	private int randomNumber(int ceil)
	{
		return generator.nextInt(ceil);
	}
	
	private void fillVectors()
	{

		int i, j,random, bomberman;


		/* filling unbreakable */
		for (i=1; i<15; i += 2)
			for (j=1;j<13; j += 2)
				Unbreakables.addElement(new Point(i,j));
				
		for (i = 0; i<15; i+= 2)
			for (j=0; j<13; j+=2)
			{   
				
				// probabilit�t ca 66 dass es einen breakable gibt
				random = randomNumber(3);
				if (random != 0)
				{
					Breakables.addElement(new Point(i,j));
				}
			}
				
		/*Platz von Bomberman */
		bomberman = randomNumber(2);

		random = randomNumber(14);

		if (bomberman == 0)
		{   
			
			// Bomberman muss sich frei bomben k�nnen
			Breakables.remove(new Point(0,0));
			Breakables.remove(new Point(0,1));
			Breakables.remove(new Point(1,0));
			Breakables.remove(new Point(14,12));
			Breakables.remove(new Point(14,11));
			Breakables.remove(new Point(13,12));
			
			
			Bombermans.addElement(new Point(0,0));
			Bombermans.addElement(new Point(14,12));
            
			// prueft dass zwei Elemente nicht auf den gleichen Koordinaten liegen
			Breakables.remove(new Point(random, 13 - random));
			Unbreakables.remove(new Point(random, 13 - random));
			Exit.addElement(new Point(random, 13 - random));
			
			

		}
		else
		{   
			// Bomberman muss sich frei bomben k�nnen
			Breakables.remove(new Point(14,0));
			Breakables.remove(new Point(13,0));
			Breakables.remove(new Point(14,1));
			Breakables.remove(new Point(0,12));
			Breakables.remove(new Point(0,11));
			Breakables.remove(new Point(1,12));
			
			
			Bombermans.addElement(new Point(14,0));
			Bombermans.addElement(new Point(0,12));
			
			// prueft dass zwei Elemente nicht auf den gleichen Koordinaten liegen
			Breakables.remove(new Point(random +1 , random));
			Unbreakables.remove(new Point(random +1 , random));
			Exit.addElement(new Point(random +1 , random));
		}
	}
  
	public Vector<Point> getBombermans()
	{
		return this.Bombermans;
	}
	public Vector<Point> getBreakable()
	{
		return this.Breakables;
	}
	public Vector<Point> getUnbreakable()
	{
		return this.Unbreakables;
	}
	public Vector<Point> getExit()
	{
		return this.Exit;
	}

}
