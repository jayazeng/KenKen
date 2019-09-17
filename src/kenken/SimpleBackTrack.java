package kenken;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class SimpleBackTrack {
	private int[][] finalSolution; //2d int array to hold the solution
	int n; // length and width of array
	private boolean completed;
	private int iterations; // number of nodes accessed
	private InputFile input; // inputfile that will have all the data
	private Point2D p; //place holder point that's only used to access values

	public SimpleBackTrack(InputFile file) {
		completed = false;
		iterations = 0;
		input = file;
		n = file.n;
		finalSolution = new int[n][n];
		p = new Point2D.Double();
	}

	// backtrack method
	public boolean backtrack() {
		if (completed) {
			return true;
		}
		for (int x = 0; x < n; x++) {
			int testValue = 1; // test value that will be incremented if it doesn't meet constraints
			for (int y = 0; y < n; y++) {
				iterations++; // updates number of nodes
				if (checkConstraints(testValue, x, y)) { // will only update solution if constraints are made
					finalSolution[x][y] = testValue;
					if (x == n-1 && y == n-1) { // if at the end of the solution array, you've found it all
						completed = true;
					}
					return true;
				} else {
					testValue++; // check with another test value
					checkConstraints(testValue,x,y);
				}
			}
			
		}
		return false;
	}

	public void printSolution() { //print solution in matrix form (tentatively)
		for (int x =0; x < n; x++) {
			for (int y =0; y < n; y++) {
				System.out.print(finalSolution[x][y]+" ");
			}
			System.out.println();
		}
	}
	
	//Constraints method
	protected boolean checkConstraints(int value, int x, int y) { //checks all the constraints
		if (checkRow(x,y,value) && checkColumn(x,y,value) && checkOperation(x,y, value)) {
			return true;
		}
		return false;
	}
	// check the rows for the same number
	private boolean checkRow(int x, int y, int value) {
		for (int col = 0; col < y; col++) {
			if (finalSolution[x][col] == value) {
				return false;
			}
		}
		return true;
	}

	// check the column for the same number
	private boolean checkColumn(int x, int y, int value) {
		for (int row = 0; row < x; row++) {
			if (finalSolution[row][y] == value) {
				return false;
			}
		}
		return true;
	}

	// check to see if values + op get the total needed once all areas are filled
	private boolean checkOperation(int x, int y, int value) {
		//hashtable by op
		p.setLocation(x, y);
		//get cage
		String lookup = input.cageLookup.get(p);
		Cage cage = input.cages.get(lookup);
		//get operation total and set the actualtotal variable to the test value
		int opTotal = cage.getTotal();
		int actualTotal = value;
		for (int index =0; index < cage.locales.size(); index++ ) { //test all the points in the cage
			int otherX = (int) cage.locales.get(index).getX(); //get x for final array
			int otherY = (int) cage.locales.get(index).getY(); // get y for final array
			int val = finalSolution[otherX][otherY]; // val of point listed in cage
			if (val != 0) { //if value has been declared in solution run the code otherwise it doesn't matter
				if (cage.getOp().contentEquals("+")) { // if addition
					actualTotal += val;
				} else if (cage.getOp().contentEquals("*")) { // if multiplication
					actualTotal *= val;
				} else if (cage.getOp().contentEquals("/")) { // if division
					actualTotal /= val;
				} else if (cage.getOp().contentEquals("-")) { // if subtraction
					actualTotal -= val;
				}

			}
		}
		if (actualTotal > opTotal) { // check if actual total will be greater than expected total
			return false;
		}
		return true; // this will return true if actual total is less than or equal expected total
	}

	public int getIterations() {
		return iterations;
	}
}
