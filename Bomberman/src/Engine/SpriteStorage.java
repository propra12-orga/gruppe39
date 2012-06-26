package Engine;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

/***************************************************************************
* SpriteStorage
* desc: 	this class provides a storage for all the sprites/images
* 			used in the whole game. we have to fill it on startup
*			and then we can access the sprites very easy by just using
*			the key/name we gave them when adding them to the storage
*			by that, we dont have to care about loading sprites anymore
*			during the mainloop
***************************************************************************/
public class SpriteStorage 
{
	// #######################################################################
	// Variables
	// #######################################################################
	// we only need a single instance of this class
	private static SpriteStorage single = new SpriteStorage();
	// cached sprite map, from reference to sprite instance
	// used to avoid loading sprites more than one time
	private HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
	
	
	
	// #######################################################################
	// Public functions
	// #######################################################################

	/***************************************************************************
	* get
	* desc: 	returns our single instance of the sprite storage
	* @param: 	void
	* @return:		our single instance
	***************************************************************************/
	public static SpriteStorage get() 
	{
		return single;
	}
	
	
	/***************************************************************************
	* get
	* desc: 	get a sprite from the storage
	* @param: 	String sprite_name: name of the sprite, which got its image in
	*			the folder "Images"
	* @return:		if the sprite was found, it is returned, else null
	***************************************************************************/
	public Sprite getSprite(String sprite_name) 
	{
		// if we've already got the sprite in the cache
		// then just return the existing version
		// avoid multiple loading
		if (sprites.get(sprite_name) != null) 
		{
			return (Sprite) sprites.get(sprite_name);
		}
		
		// otherwise, load the sprite
		BufferedImage source_image = null;
		
		try 
		{
			// open image from "Images" directory
			source_image = ImageIO.read(new File("Images/" + sprite_name + ".png"));
		} 
		catch (IOException e) 
		{
			DebugConsole.PrintError("Failed to get sprite " + sprite_name + "from sprite storage, no such sprite exists");
			return null;
		}
		
		// create an accelerated image of the right size to store our sprite in
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		Image image = gc.createCompatibleImage(source_image.getWidth(), source_image.getHeight(), Transparency.BITMASK);
		
		// draw our source image into the accelerated image
		image.getGraphics().drawImage(source_image, 0, 0, null);
		
		// create a sprite, add it the cache then return it
		Sprite sprite = new Sprite(image);
		sprites.put(sprite_name, sprite);
		
		return sprite;
	}
	
	
	
	// #######################################################################
	// Private functions
	// #######################################################################
	
	// nothing here

}