import java.awt.Point;

// this class converts our 32x32 pixel block coordinates to real pixel coordinates
// or the other way round.
// it makes things easier with positioning entities, so we dont have to think about
// calculating their positions with pixel coordinates.
public interface InterfaceScreenCoordManager 
{
	// calculate the pixel coordinates of the HUD coordinates x and y (resembled as point)
	// class point is very simple to return x and y coordinates
	public Point convertHUDCordToRealCoord(Point p);
	
	// calculate the pixel coordinates of the playground coordinates x and y (resembled as point)
	// class point is very simple to return x and y coordinates
	public Point convertPlaygroundCordToRealCoord(Point p);
	
	// calculate the hud coordinates of the pixel coordinates 
	public Point convertRealCordToHUDCoord(Point p);
	
	// calculate the playground coordinates of the pixel coordinates
	public Point convertRealCordToPlaygroundCoord(Point p);
}
