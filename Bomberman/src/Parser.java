import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class Parser 
{
	/*
	 * Die Dimension des FEldes ist 15 x 13 .
	 * Das string array ist also auch 15 x 13
	 *  => 13 String, jede mit 15 Chars ; jede Cahr entspricht ein Element im Feld 
	 */
	String[] configStr = new String[13];
	
	String file_name;
	public String[] Parser(String file_name)
	{
		this.file_name = file_name;
		int ret = parseConfigFile(file_name);
		if ( ret != 1)
			configStr = null;

		return configStr;
	}
	
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
				
				lines ++;
				for (i=0; i<arr.length; i++)
				{
					if ( ! ( (arr[i] > 48 ) && (arr[i] < 53) ) )
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

}
