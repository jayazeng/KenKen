package kenken;

import java.util.ArrayList;
import java.awt.geom.Point2D;

public class Cage {

	public int total;
	public String op;
	public ArrayList<Point2D>locales = new ArrayList<Point2D>(15);
	
	public Cage() {
		
	}
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int x) {
		
		total = x;
		
	}

	public String getOp() {
		return op;
	}

	public void setOp(String y) {
		
		op = y;
		
	}

	public void setPoint(int i, int j) {
		Point2D e=null; 
		e.setLocation(i, i);
		locales.add(e);
		
	}


}
