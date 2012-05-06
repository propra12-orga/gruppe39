import java.awt.Graphics;
import java.util.HashMap;


// TODO: only concept, extend this for every screen
public class Gamescreen 
{
	private HashMap<String, Entity> Entity_List;

	
	// private variable containing all graphics for that screen
	// private variable holding input states
	public Gamescreen()
	{		
		this.Entity_List = new HashMap<String, Entity>();
	}
	
	public void addEntityToScreen(String Name_Entity, Entity E)
	{
		// TODO: add debug output console
		if (Name_Entity == null || E == null)
			return;
		
		this.Entity_List.put(Name_Entity, E);
	}
	
	public void drawEntitesToScreen(Graphics Graphics_Context)
	{
		Entity[] Entities = this.getAllEntites();
		
		for (int i = 0; i < Entities.length; i++)
		{
			Entities[i].draw(Graphics_Context);
		}
	}
	
	public Entity getEntity(String Name_Entity)
	{
		// TODO: add debug console output
		if (!this.Entity_List.containsKey(Name_Entity))
			return null;
		else
			return this.Entity_List.get(Name_Entity);
	}
	
	public Entity[] getAllEntites()
	{
		// get all the entities from the hashmap and put them into a handy array
		Entity[] array = new Entity[1];
		array = this.Entity_List.values().toArray(array);
		
		return array;
	}
	
	
	

}
