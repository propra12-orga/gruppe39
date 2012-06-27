package Engine;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;


public class Parser 
{
	public static void main(String args[])
	{
		Parser level = new Parser("test.txt");
		
		for (int i = 0; i < level.getBombermans().size(); i++)
		{
			System.out.println("Bomberman " + i + ": " + level.getBombermans().get(i).toString());
		}
		
		for (int i = 0; i < level.getBreakable().size(); i++)
		{
			System.out.println("Breakable " + i + ": " + level.getBreakable().get(i).toString());
		}
		
		for (int i = 0; i < level.getUnbreakable().size(); i++)
		{
			System.out.println("getUnbreakable " + i + ": " + level.getUnbreakable().get(i).toString());
		}
		
		for (int i = 0; i < level.getExit().size(); i++)
		{
			System.out.println("Exit " + i + ": " + level.getExit().get(i).toString());
		}
	}
	
	
	private Vector<Point> Bombermans = new Vector<Point>();
	private Vector<Point> Breakables = new Vector<Point>();
	private Vector<Point> Unbreakables = new Vector<Point>();
	private Vector<Point> Exit = new Vector<Point>();
	
	
	/**
	 * Die Dimension des FEldes ist 15 x 13 .
	 * Das string array ist also auch 15 x 13
	 *  => 13 String, jede mit 15 Chars ; jede Cahr entspricht ein Element im Feld 
	 */
	String[] configStr = new String[13];
	/** Konstrukter und datei einlesen 
	 * 
	 */
	String file_name;
	public Parser(String file_name)
	{
		this.file_name = file_name;
		int ret = parseConfigFile(file_name);
		if ( ret != 1)
		{
			this.configStr    = null;
			this.Bombermans   = null;
			this.Breakables   = null;
			this.Unbreakables = null;
			this.Exit         = null;
		}
			/** befuellung falls alles ok is
		 * 
		 */
		else
		{
			fillVectors();
		}
		

	}
	
	private void fillVectors()
	{
		/** Typ umwandlung von char zum int
		 * 
		 */
		int i, j, type;
		char[] arr = new char[15];
		for (i=0; i<13; i++)
		{
			arr = configStr[i].toCharArray();
			for (j=0; j<15; j++)
			{
				type = (int) arr[j] - 48;
				
				switch (type)
				{   // falls j mit i vertauscht sind bitte umdrehen
				/** Alle Vektoren werden hier mit Koordinaten der Elemente befuellt
				 * 
				 */
					case 0:
						break;
					case 1:
						Bombermans.addElement(new Point(j,i));
						break;
					case 2:
						Unbreakables.addElement(new Point(j,i));
						break;
					case 3:
						Breakables.addElement(new Point(j,i));
						break;
					case 4:
						Exit.addElement(new Point(j,i));
						break;
					default:
						System.out.println("ERROR Das sollte nicht passieren !!");
						break;
				}
				
		
			}
		}
	}
   /** parsing
    * 
    * @param file_name
    * @return
    */
	private int parseConfigFile(String file_name)
	{
		try{
			FileInputStream fstream = new FileInputStream(file_name);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			char[] arr;
			int i , j= 0;
			int lines = 0;
			
			while ((line = br.readLine()) != null) 
			{
				System.out.println (line);
				arr = line.toCharArray();
				
				// eine zeile darf genau 15 Zeichen haben
				if (arr.length != 15)
				{
					System.err.println("Eine Zeile der Spielfeld darf genau 15 Elementen haben");
					System.err.println("Die Zeile '"+line+ "' hat aber "+arr.length+ " Elemnte");
					return -1;
				}
				// werte von 1-4 ( char-ascii)
				lines ++;
				for (i=0; i<arr.length; i++)
				{
					if ( ! ( (arr[i] > 47 ) && (arr[i] < 53) ) )
					{
						System.err.println("Char '"+ arr[i] + "' ist nicht valid !!");
						System.err.println(" unterbrechen  ");
						return -1;
					}
				}
				
				configStr[j++] = line; 
			}
			
			if (lines != 13)
			{
				System.err.println("Das Spielfeld darf genau 13 Zeilen haben !!");
				System.err.println("Die Datei '"+file_name +"' hat aber "+lines+ " Zeile");
				return -1;
			}
			in.close();
			
		}
		catch (Exception e)
		{
			  System.err.println("Error: " + e.getMessage());
		}
		return 1;
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
