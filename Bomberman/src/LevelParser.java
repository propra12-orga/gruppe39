import java.awt.Point;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.text.html.parser.Entity;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class LevelParser 
{
	private Vector<Point> Bombermans = new Vector<Point>();
	private Vector<Point> Breakables = new Vector<Point>();
	private Vector<Point> Unbreakables = new Vector<Point>();
	private Vector<Point> Exit = new Vector<Point>();
	
	
	public static void main(String args)
	{
		
	}
	
	private void parseXmlFile(String file_name)
	{
		// get the factory
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		  try 
		  {
			  // using factory get an instance of document builder
			  DocumentBuilder db = dbf.newDocumentBuilder();
			  
			  // parse using buider to get DOM representation of XML file
			  Document dom = db.parse("test.xml");
			  
			  
		  }	  
		  catch (ParserConfigurationException pce) {
			  
			  pce.printStackTrace();
		  }	  
		  catch (SAXException se) {
			  
			  se.printStackTrace();
		  }	  
		  catch (IOException ioe){
			  
			  ioe.printStackTrace();
			  
		  }
	}
	
	  private void parseDocument(Document dom)
	  {
		  // get the root element
		  Element docEle = dom.getDocumentElement();
		  
		  // get a nodelist of elements
		  
		  NodeList nl = docEle.getElementsByTagName ("Entity");
		  if (nl !=null && nl.getLength()>0 )	  {
	  {
		  for (int i = 0; i < nl.getLength(); i++)
		  {
			  // get the entity element
			  Element el = (Element)nl.item(i);
			  
			  // get the Entity object
			  Entity e = getEntity1(el);
			  
			  // add it to list
			  Vector<Point> myEnt;
			myEnt.add(e);
		  }
	  	}
	 }
}
	
			
		/** 	 
		 * I take an Entity element and read the values in, 
		 * create an Entity object and return it
		 */
	  private Entity getEntity1(Element entEl) 
	  {
		  // for each <Entity> element get text or int values of
		  //player, x, y. 
		  String player = getTextValue(entEl, "Player");
		  int x = getIntValue(entEl, "X");
		  int y = getIntValue(entEl, "Y");
		  
		  String type = entEl.getAttribute("type");
		  
		  // create a new Entity with the value read from the xml node
		  Entity e = new Entity(player,x,y,type);
		  
		  return e;
	  }
	  
	  /** 
	   * I take a xml element and teh tag name, look for the tag and get 
	   * the text content
	   */
	  
	  private String getTextValue(Element ele, String tagName)
	  {
		  String textval = null;
		  NodeList nl = ele.getElementsByTagName(tagName);
		  if (nl != null && nl.getLength() > 0)
		  {
			  Element el = (Element)nl.item(0);
			  textval = el.getFirstChild().getNodeValue();
		  }
		  
		  return textval;
	  }
	  
	  /**
	   * calls getTextValue and returns a int value
	   */
	  
	  private int getIntValue(Element ele, String tagName)
	  {
		  // in production application you would catch the exception
		  return Integer.parseInt(getTextValue(ele,tagName));
		  
	  }
	  
	  // printing
	  
	  private void printData(Vector<Point> myEnt)
	  {
		  System.out.println("Entity"+ myEnt.size() + "'.");
		  Iterator it = myEnt.iterator();
		  while(it.hasNext())
		  {
			  System.out.println(it.next().toString());
		  }
	  }
			
			
			
	


	private Entity getEntity(Element el) {
		return null;
	}

	public Vector getBombermans()
	{
		return this.Bombermans;
	}
	public Vector getBreakable()
	{
		return this.Breakables;
	}
	public Vector getUnbreakable()
	{
		return this.Unbreakables;
	}
	public Vector getExit()
	{
		return this.Exit;
	}
	
		
	
	
}
