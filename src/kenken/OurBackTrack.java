package kenken;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class OurBackTrack {
	private Cell[][] finalSolution; //2d Cell array to hold the solution
	int n; // length and width of array
	private boolean completed;
	private int iterations; // number of nodes accessed
	private InputFile input; // inputfile that will have all the data
	private Point2D p; //place holder point that's only used to access values

	public OurBackTrack(InputFile file) {
		completed = false;
		iterations = 0;
		input = file;
		n = file.n;
		finalSolution = new Cell[n][n];
	}
	// this method should put possible values into each cell
	private ArrayList<Integer> possibilities(Cage c) {
		ArrayList<Integer> possible = new ArrayList<Integer>(n);
		int total = c.getTotal();
		String op = c.getOp();
		for (int value = 1; value <= n; value++) {
			if (op.equals("*")) {
				if(total % value == 0){ // this will mean the total is divisible by the possible value
					possible.add(value);
				}
			} else if (op.equals("+")) {
				if (total - value > 0) { // this will mean that subtracting the possible value from the total will be greater than 0
					possible.add(value); 
				}
			} else if (op.equals("-")) {// this will mean that adding the possible value to the total will be less than or equal to n
				if (total + value <= n) {
					possible.add(value); 
				}
			} else if (op.equals("/")) {// this will mean that multiplying the possible value to the total will be less than or equal to n
				if (total*value <= n) {
					possible.add(value); 
				}
			}
		}
		return possible;
	}
	
	
	
	// backtrack method
	public boolean backtrack() {
		if (completed) {
			return true;
		}
		for (int x = 0; x < n; x++) {
			int testValue = 1; // test value that will be incremented if it doesn't meet constraints
			for (int y = 0; y < n; y++) {
				p.setLocation(x, y);
				//get cage
				//String lookup = input.cageLookup.get(p);
				Cage cage = input.cages.get(input.cageLookup.get(p));
				ArrayList<Integer> testValues = possibilities(cage);
				iterations++; // updates number of nodes
				for (int i = 0; i < testValues.size(); i++) {
					if (checkConstraints(testValues.get(i), x, y)) { // will only update solution if constraints are made
						finalSolution[x][y].setValue(testValue);
						if (x == n-1 && y == n-1) { // if at the end of the solution array, you've found it all
							completed = true;
						}
						return true;
					}
				}
			}
			
		}
		return false;
	}

	public void printSolution() { //print solution in matrix form (tentatively)
		for (int x =0; x < n; x++) {
			for (int y =0; y < n; y++) {
				System.out.print(finalSolution[x][y].getValue() +" ");
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
			if (finalSolution[x][col].getValue() == value) {
				return false;
			}
		}
		return true;
	}

	// check the column for the same number
	private boolean checkColumn(int x, int y, int value) {
		for (int row = 0; row < x; row++) {
			if (finalSolution[row][y].getValue() == value) {
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
			int val = finalSolution[otherX][otherY].getValue(); // val of point listed in cage
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
	
	//Put in more constraints
}
