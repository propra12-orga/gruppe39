import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;


public class SpriteStorage 
{
	// we only need a single instance of this class
	private static SpriteStorage single = new SpriteStorage();
	
	// cached sprite map, from reference to sprite instance
	// used to avoid loading sprites more than one time
	private HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
	
	
	
	public static SpriteStorage get() 
	{
		return single;
	}
	
	
	
	/**
	 * Retrieve a sprite from the store
	 * 
	 * @param ref The reference to the image to use for the sprite
	 * @return A sprite instance containing an accelerate image of the request reference
	 */
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
			// TODO: debug output here
			System.out.println("Failed to load: "+ sprite_name);
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

}