/*-> An individual cage or group of points is called eachCage.  It's an object created in the cage class.
 *   It consist of a Numerical Total (total), opeartor (op), and an ArrayList of Point2D Coordinates (locales) 
 * 
 * 
 */

package kenken;

import java.util.ArrayList;
import java.awt.geom.Point2D;

public class Cage {

	public int total;  // numerical total from input file
	public String op;  // operator needed to make total
	public ArrayList<Point2D>locales = new ArrayList<Point2D>(15); // ArrayList which holds Point2D for cage
	public Point2D e;
	public int size = 0;
	
	public Cage() { //no default value as of yet
		
	}
	
	public int getTotal() { // getter for total
		
		return total;
	
	}

	public void setTotal(int x) { // setter for total
		
		total = x;
		
	}

	public String getOp() { // getter for operator (op)
		
		return op;
	
	}

	public void setOp(String y) { // setter for operator (op)
		
		op = y;
		
	}

	public void setPoint(int i, int j) { // setter for Point (locales)
		
		e.setLocation(i, i);
		
		locales.add(e);
		
		size++;
		
	}

	public ArrayList<Point2D> getPoint() { // getter for Point (locales)
		
		return locales;
		
	}
	
	
	
}
