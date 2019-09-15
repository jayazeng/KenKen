package kenken;

import java.awt.geom.Point2D;

public class BackTrack {
	public int[][] finalSolution;
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
					finalSolution[x][y] = testValue;
					if (x == n-1 && y == n-1) {
						completed = true;
					}
					return true;
				}
				finalSolution[x][y] = 0;
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
		if (checkRow(x,y,value) && checkColumn(x,y,value) && checkOperation(x,y, file, value)) {
			return true;
		}
		return false;
	}
	// check the rows for the same number
	public boolean checkRow(int x, int y, int value) {
		for (int col = 0; col < y; col++) {
			if (finalSolution[x][col] == value) {
				return false;
			}
		}
		return true;
	}

	// check the column for the same number
	public boolean checkColumn(int x, int y, int value) {
		for (int row = 0; row < x; row++) {
			if (finalSolution[row][y] == value) {
				return false;
			}
		}
		return true;
	}

	// check to see if values + op get the total needed once all areas are filled
	public boolean checkOperation(int x, int y, InputFile file, int value) {
		//hashtable by op
		p.setLocation(x, y);
		//get cage
		String lookup = file.cageLookup.get(p);
		Cage cage = file.cages.get(lookup);
		//access point array and check for values
		int opTotal = cage.getTotal();
		int actualTotal = value;
		for (int index =0; index < cage.locales.size(); index++ ) {
			int otherX = (int) cage.locales.get(index).getX();
			int otherY = (int) cage.locales.get(index).getY();
			int val = finalSolution[otherX][otherY];
			if (val != 0) {
				if (cage.getOp().contentEquals("+")) {
					actualTotal += val;
				} else if (cage.getOp().contentEquals("*")) {
					actualTotal *= val;
				} else if (cage.getOp().contentEquals("/")) {
					actualTotal /= val;
				} else if (cage.getOp().contentEquals("-")) {
					actualTotal -= val;
				}

			}
		}
		cage.getOp();
		if (actualTotal > opTotal) {
			return false;
		}
		return true;
	}
}
