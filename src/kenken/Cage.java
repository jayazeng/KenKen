/*-> An individual cage or group of points is called eachCage.  It's an object created in the cage class.
 *   It consist of a Numerical Total (total), opeartor (op), and an ArrayList of Point2D Coordinates (locales) 
 * 
 * 
 */

package kenken;

import java.util.ArrayList;

public class Cage {

	public int total; // numerical total from input file

	public String op; // operator needed to make total

	public ArrayList<String> locales = new ArrayList<String>(15); // ArrayList which holds Point2D for cage

	public int size = 0;

	public Cage() { // no default value as of yet

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

	public void setLocales(int i, int j) { // setter for Point (locales)

		String e = ("(" + (i - 1) + "," + j + ")");

		locales.add(e);

		size++;

	}

	public ArrayList<String> getLocales() { // getter for Point (locales)

		return locales;

	}

	public int getLocalesX(int i) { // input index and get back x coordinate as int

		String x = (locales.get(i));

		int indexComma = x.indexOf(",");

		return Integer.parseInt(x.substring(1, indexComma));
	}

	public int getLocalesY(int j) { // input index and get back y coordinate as int

		String y = (locales.get(j));

		int indexComma = y.indexOf(",");
		return Integer.parseInt(y.substring(indexComma + 1, y.length() - 1));
	}
}
