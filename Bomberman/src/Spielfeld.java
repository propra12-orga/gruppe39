
import java.util.Vector;


public class Spielfeld 
{
	public Entity[][] Arena;
	
	
	
	// spielfeld inhalt ::: 
	private Vector<BombermanEntity> EBomberman = new Vector<BombermanEntity>();
	private Vector<BombeEntity> EBomb = new Vector <BombeEntity>();
	private Vector<ExplosionEntity> EExplosion = new Vector <ExplosionEntity>();
	private Vector<UnbreakableEntity> EUnbreakable = new Vector <UnbreakableEntity>();
	private Vector<BreakableEntity> EBreakable = new Vector <BreakableEntity>();

	public Spielfeld()
	{
		// TODO
		// - level aus xml datei auslesen -> eigene xml parser klasse
		// - xml parser klasse schreibt werte, anzahl, positionen der einzelnen objekte in eine klasse
		// - Arena initialisieren: klasse mit werten aus tabelle an funktion hier uebergeben
	}

	
	public void addInitialArenaToScreen(Gamescreen cur_gamescreen)
	{
		for (int i = 0; i < this.EUnbreakable.size(); i++)
		{
			cur_gamescreen.addEntityToScreen("unbreakable" + i, this.EBreakable.get(i));
		}
		
		for (int i = 0; i < this.EBreakable.size(); i++)
		{
			cur_gamescreen.addEntityToScreen("breakable" + i, this.EBreakable.get(i));
		}
		
		for (int i = 0; i < this.EBomberman.size(); i++)
		{
			cur_gamescreen.addEntityToScreen("bomberman" + i, this.EBomberman.get(i));
		}
	}

	
	public void addBomb(int x, int y)
	{
		// TODO: 
		// koordinaten pruefen (nicht ausserhalb vom spielfeld)
		// bombenobjekt erzeugen
		// spielfeld an bombe uebergeben, damit bombe nach beenden des tickvorgangs die explosionen erzeugen kann
		// synchronisation fuer zugriff auf das spielfeld (wichtig!!!)		
	}
	
	public void fillArena(/* parameter */)
	{
		// TODO 
		// arbeite alle kategorien hier ab und fuelle das spielfeld
		// entsprechend der werte, die aus der datei ausgelesen wurden
		// fuelle hierzu nicht nur Arena, sondern auch die vektoren
	}
	
	public void clearArena()
	{
		for (int i = 0; i < this.Arena.length; i++)
		{
			for (int j = 0; j < this.Arena[i].length; j++)
			{
				this.Arena[i][j] = null;
			}
		}
	}
    
}
