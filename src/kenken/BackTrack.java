package kenken;

import java.awt.geom.Point2D;

public class BackTrack {
	/* PseudoCode for Back Track
	 * 
	 * 1) Set up cage
	 * 2) Create a binary tree of cells
	 * 3) Depth First
	 * 
	 * 
	 */
	public int[][] finalAnswers;
	int n;
	int xcoor;
	int ycoor;
	boolean completed;
	int iterations;
	InputFile input;
	Point2D p;
	
	public BackTrack(InputFile file) {
		completed = false;
		iterations = 0; xcoor = 0; ycoor = 0;
		input = file;
	}
	
	// backtrack method
	public boolean backtrack(InputFile input) {
		if (completed) {
			return true;
		}
		for (int x = 0; x < n; x++) {
			int testValue = 1;
			for (int y = 0; y < n; y++) {
				if (checkConstraints(testValue, x, y, input)) {
					finalAnswers[x][y] = testValue;
					if (x == n-1 && y == n-1) {
						completed = true;
					}
					return true;
				}
				finalAnswers[x][y] = 0;
			}
		}
		return false;
	}
	
	public void printSolution(int[][] solution) {
		for (int x =0; x < solution.length; x++) {
			for (int y =0; y < solution.length; y++) {
			}
		}
	}
	//Constraints method
	public boolean checkConstraints(int value, int x, int y, InputFile file) {
		p.setLocation(x, y);
		int hashcode = p.hashCode();
		//file.
		if (checkRow(value, file) && checkColumn(value, file) && checkOperation(x,y, file)) {
			return true;
		}
		return false;
	}
	// check the rows for the same number
	public boolean checkRow(int value, InputFile file) {
		if (/* some point hashtable*/.contains(value)) {
			return false;
		}
		return true;
	}
	
	// check the column for the same number
	public boolean checkColumn(int value, InputFile file) {
		if (/* some point hashtable column*/.contains(value)) {
			return false;
		}
		return true;
	}
	
	// check to see if values + op get the total needed once all areas are filled
	public boolean checkOperation(int x, int y, InputFile file) {
		//hashtable by op
		//get cage
		//access point array and check for values
		String op = c.getOperation();
		int opTotal = Integer.parseInt(op.substring(0,op.length()-1));
		int actualTotal = 0;
		if (actualTotal != opTotal) {
			return false;
		}
		return true;
	}
}
