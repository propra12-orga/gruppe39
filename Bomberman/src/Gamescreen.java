import java.awt.Graphics;
import java.util.HashMap;


//=======================================================================
// Gamescreen
// desc: 	keeps track of all entities on one screen. simply add all your
// 			entities, you want to have to appear on your screen to this.
//			everything gets updated within the mainloop automatically,
//			so you dont have to keep track of that.
//=======================================================================
public class Gamescreen 
{
	// #######################################################################
	// Variables
	// #######################################################################
	// "list" containing all entities for that particular screen
	private HashMap<String, Entity> Entity_List;

	
	
	// #######################################################################
	// Public functions
	// #######################################################################
	
	// =======================================================================
	// Gamescreen
	// desc: nothing important to say
	// param: void
	// =======================================================================
	public Gamescreen()
	{		
		// create our entity "list"
		this.Entity_List = new HashMap<String, Entity>();
	}
	
	
	// =======================================================================
	// addEntityToScreen
	// desc: 	add a single entity with an identification string to our screen
	// param: 	String Name_Entity: identification name for our entity
	// 			Entity E: our entity we want to add
	// ret:		void
	// =======================================================================
	public void addEntityToScreen(String Name_Entity, Entity E)
	{
		// error check
		if (Name_Entity == null || E == null)
		{
			DebugConsole.PrintError("Invalid(null) Name_Entity or Entity");
			return;
		}
			
		// valid element, add entity to list
		this.Entity_List.put(Name_Entity, E);
	}
	
	
	// =======================================================================
	// addEntitiesToScreen
	// desc: 	adds a list of entities with identification string to our screen 
	// param: 	String[] Name_Entity: list with strings as names for every 
	//			single entity (same length as E!)
	// 			Entity[] E: list with entities (same length as Name_Entity!)
	// ret: 	void
	// =======================================================================
	public void addEntitiesToScreen(String[] Name_Entity, Entity[] E)
	{
		// error check
		if (Name_Entity == null || E == null)
		{
			DebugConsole.PrintError("Invalid(null) Name_Entity Array or Entity Array");
			return;
		}
		
		// add all our enties with their respective names to our list
		for (int i = 0; i < Name_Entity.length; i++)
		{
			// error check on every element
			if (Name_Entity[i] == null)
			{
				DebugConsole.PrintError("Adding entity to screen, invalid(null) name");
				break;
			}
			
			// error check on every element
			if (E[i] == null)
			{
				DebugConsole.PrintError("Adding entity " + Name_Entity[i] + " to screen, invalid(null) entity");
				break;
			}
			
			// valid element, add to list
			this.Entity_List.put(Name_Entity[i], E[i]);		
		}
	}
	
	// TODO TODO
	public void removeEntityFromScreen(String name)
	{
		this.Entity_List.remove(name);
	}
	
	
	// =======================================================================
	// getEntity
	// desc: 	returns an entity identified by its name
	// param: 	String Name_Entity: name of the entity we want to have
	// ret: 	returns the entity if its in our list, else null
	// =======================================================================
	public Entity getEntity(String Name_Entity)
	{
		// error checks
		if (Name_Entity == null)
		{
			DebugConsole.PrintError("Invalid(null) Name_Entity");
			return null;
		}
		
		// check if entity is our list, if not return null
		if (!this.Entity_List.containsKey(Name_Entity))
			return null;
		else
			return this.Entity_List.get(Name_Entity);
	}
	
	
	// =======================================================================
	// getAllEntites
	// desc: 	returns an array with all entities in our list
	// param:	void
	// ret:		array containing all entities of our private list
	// =======================================================================
	public Entity[] getAllEntites()
	{
		// get all the entities from the hashmap and put them into a handy array
		Entity[] array = new Entity[1];
		array = this.Entity_List.values().toArray(array);
		
		return array;
	}
	
	
	// =======================================================================
	// drawEntitesToScreen
	// dsec: 	draws all entities of our list to the graphical context provided
	// param: 	Graphics Graphics_Context: the graphical context to draw everything to
	// ret: 	void
	// =======================================================================	
	public void drawEntitesToScreen(Graphics Graphics_Context)
	{
		// error check
		if (Graphics_Context == null)
		{
			DebugConsole.PrintError("Invalid(null) graphical context");
			return;
		}
		
		// get all our entities
		Entity[] Entities = this.getAllEntites();
		
		// draw them
		for (int i = 0; i < Entities.length; i++)
		{
			Entities[i].draw(Graphics_Context);
		}
	}
	
	
	
	// #######################################################################
	// Private functions
	// #######################################################################
	
	// nothing here
}
