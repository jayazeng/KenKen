package kenken;

import java.util.ArrayList;
import java.awt.Point;

public class Cage {

	public int total;
	public String op;
	public ArrayList<Point>locales = new ArrayList<Point>(81);
	
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


}
