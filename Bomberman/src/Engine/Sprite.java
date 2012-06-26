package Engine;
import java.awt.Graphics;
import java.awt.Image;

/***************************************************************************
* Sprite
* desc: 	class wrapping around the standard image class with some
*			additional stuff, to make handling for images easier
***************************************************************************/
public class Sprite 
{
	// #######################################################################
	// Variables
	// #######################################################################
	// the image for our sprite
	private Image image;
	
	
	
	// #######################################################################
	// Public functions
	// #######################################################################

	/***************************************************************************
	* Sprite
	* desc: 	create a new sprite based on a provided image
	* @param: 	Image image: image for the sprite
	***************************************************************************/
	public Sprite(Image image) 
	{
		this.image = image;
	}
	
	
	/***************************************************************************
	* getWidth
	* desc: 	get width of the sprite
	* @param: 	void
	* @return:		width of the sprite
	***************************************************************************/
	public int getWidth() 
	{
		return image.getWidth(null);
	}

	
	/***************************************************************************
	* getHeight
	* desc: 	get height of the sprite
	* @param: 	void
	* @return:		height of the sprite
	***************************************************************************/
	public int getHeight() 
	{
		return image.getHeight(null);
	}
	
	
	/***************************************************************************
	* draw
	* desc: 	draw the sprite onto the provided graphics context at a
	*			specified position
	* @param: 	Graphics g: the context to draw the sprite to
	*			int x: x location to draw the sprite to
	*			int y: y location to draw the sprite to
	* @return:		void
	***************************************************************************/
	public void draw(Graphics g, int x, int y) 
	{
		g.drawImage(image, x, y, null);
	}
	
	
	
	// #######################################################################
	// Private functions
	// #######################################################################
	
	// nothing here
}