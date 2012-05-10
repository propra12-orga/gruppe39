import java.awt.Point;


// use this to implement BlockFrame for easier collision detection
// use the BlockFrame Class to create a grid for the playground 
// and maybe also the hud (later).
// with this, we dont have to loop through all the entities, which
// are placed on our screen. just check for the next block the entity 
// is moving towards. if we have an entity there, use it to detect collisions.
// also dont forget, initialize the 
public interface InterfaceBlockFrame
{
	// private array: Block_Grid[][]
	
	// constructor
	// int x, y: initial size of the block frame
	// public BlockFrame(int x, int y)
	
	// adds an entity to our playground array.
	// dont forget, that x and y of the point are the grid coordinates and not pixel coordinates
	// also dont forget about error checks (array out of bounds)
	public void setEntity(Entity E, Point p);
	
	// gets an entity from the specified coordinates
	// dont forget, that x and y of the point are the grid coordinates and not pixel coordinates
	// also dont forget about error checks (array out of bounds)
	public Entity getEntity(Point p);
	
}
