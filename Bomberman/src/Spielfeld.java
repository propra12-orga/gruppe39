import java.util.LinkedList;

public class Spielfeld {

	private final int[][] spielfeld;
	
	//Bomberman koordinaten
	private int mx = 0;
	private int my = 0;
	
	/**
	 * Liste die alle Typen die passierbar sind gespeichert hat.
	 * Aber nur Zahlen wie 0,1,2... die fuer die Typen stehen.
	 * Ist Anfang noch leer.
	 */
	private LinkedList<Integer> passables = new LinkedList<Integer>();
	
	/**
	 * Ueberprueft ob das aktuelle Feld des bombermans passierbar ist.
	 * @return true wenn passierbar, false wenn nicht.
	 */
	public boolean canPass()
	{
		return (passables.contains(spielfeld[mx][my]));
	}
	
	public Spielfeld(int sx, int sy)
	{
		spielfeld = new int[sx][sy]; //Spielfeld wird in der richtigen Größe angelegt
		passables.add(0); //0=Leeres Feld wird passierbar gemacht
	}
	
    public static void main(String... args)
    {
    	new Spielfeld(15,13); //15x13 Spielfeld erstellen.
    }
    
    /**
     * Bomberman nach oben bewegen ein Feld
     */
    public void moveUp()
    {
    	if(my>0) //An der oberen Grenze soll er nicht mehr nach oben koennen.
    	{
    		my--;
    		if(canPass()) //Schaut ob der Bomberman jetzt auf einem gueltigen Feld steht.
    		{
    			//Wenn ja
    			System.out.println("Bewegt!");
    		}
    		else
    		{
    			//Wenn ja
    			System.out.println("Leider ein Hindernis im Weg.");
    			my++;//Bombermanzurueckbewegen
    		}
    	}
    }
}
