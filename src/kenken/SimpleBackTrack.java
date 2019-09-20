package kenken;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class SimpleBackTrack {
	private int[][] finalSolution; //2d int array to hold the solution
	int n; // length and width of array
	private boolean completed;
	private int iterations; // number of nodes accessed
	private InputFile input; // inputfile that will have all the data
	
	private SearchTree tree;
	private Node currentNode;

	public SimpleBackTrack(InputFile file) {
		completed = false;
		iterations = 0;
		input = file;
		n = file.n;
		finalSolution = new int[n][n];
		tree = new SearchTree(n);
		currentNode = tree.getRoot();
	}

	// backtrack method
	public boolean solve(int x, int y, int test) {
		if (completed) {
			return true;
		}
		while (test <= n) {
			if(checkConstraints(test,x,y)) { // checks to see if the value will work
				iterations++; // update iterations
				finalSolution[x][y] = test; // set value equal
				if (x == n-1 && y == n-1) { // if reached end return true
					completed = true;
					return true;
				}
				if (y < n - 1 && solve(x,y+1, test)) { // check next cell
					return true;
				} 
				else if (y == n-1 && x < n && solve(x+1,0, test)){ // check next cell if have to switch rows
					return true;
				}
				else {
					finalSolution[x][y] = 0; // else change everything to 0
				}
			}
			test++; // try with another test value
		}
		return false;
	}

	// using search tree to search
	public void trySearch() {
		/*
		 * use current node and add child if the value meets the constraints for the cell
		 * if a value of 0 is returned by the backtrack method, make the parent of the current node the current
		 * 
		 * DON'T KNOW HOW TO INCORPORATE XY VALUES -> how to check constraints without x and y?
		 * maybe do something with adding the nodes to the cages????
		 * or maybe add x and y field to node or associate it with the 
		 * 
		 */
		
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				
			}
		}
	}
	
	public int backtrack(int x, int y) {
		int test = 1;
		while (test <= n) {
			if(checkConstraints(test,x,y)) { // checks to see if the value will work
				return test;
			}
			test++; // try with another test value
		}
		return 0;
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
		//get cage
		String point = "("+ x+ "," + y + ")";
		String lookup = input.cageLookup.get(point);
		Cage cage = input.cages.get(lookup);
		//get operation total and set the actualtotal variable to the test value
		int opTotal = cage.getTotal();
		int actualTotal = value;
		for (int index =0; index < cage.locales.size(); index++ ) { //test all the points in the cage

			//			int otherX = (int) cage.locales.get(index).getX(); //get x for final array
			int otherX =  cage.getLocalesX(index); //get x for final array **** Ced's Version

			//			int otherY = (int) cage.locales.get(index).getY(); // get y for final array
			int otherY =  cage.getLocalesY(index); // get y for final array **** Ced's Version

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
