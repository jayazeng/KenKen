/* Local Search Method
 * 	How it works
 * 		1. Iterate using proposed solution and improving -- BOARD SHOULD ALREADY BE POPULATED
 * 		2. Start with initial (random (?)) assignment of variables - most likely violate constraints
 * 		3. Iterations guided by objective function --> moving towards goal solution value (no violation of constraints)
 * 		4. Each iteration, choose any conflicted variable and reassign value to reduce violated constraint
 */

/* PseudoCode/Brainstorming for Local Search
 * 	1. Set up cage
 * 	2. Random numbers (1-n) assigned for N*N
 * 		
 *  ----START OF LOOP----
 *  3. Constraint violations detected
 *  		# of constraints (3):
 *  			1. Each row has one unique value
 *  			2. Each column has one unique value
 *  			3. Each partition satisfies the mathematical expression
 *  					There should be an order in which it focuses on solving certain constraints first(?)
 *  						1. Start from detecting violation from each partition (list out from A --> O)
 *  						2. Attempt to resolve violation in box A first and then moves down the list
 *  4. Iterations run to re-assign values (Looking for less violations in each run)
 *  5. Check for constraint violations
 *  6. Back to step 4 until it comes back with no violations in step 5 - If no more constraint violations, moves to step 7
 *  		Limit number of iterations so solution terminates --> can do this by not letting the loop go backwards - more violations of constraints
 *  ----END OF LOOP----
 *  
 *  7. Print the solution with no violations
 */

package kenken;

import java.util.Hashtable;

public class LocalSearch {
	private int[][] finalSolution; // 2D int array to hold the solution
	int n; // length and width of array
	private int iterations; // number of nodes accessed
	private InputFile input; // input file that will have all the data

	public LocalSearch(InputFile file) {
		iterations = 0;
		input = file;
		n = file.n;
		finalSolution = new int[n + 1][n + 1];
	}

	/*
	 * Brainstorm on creating LocalSearch Loop
	 * 
	 * Loop inside a loop: Inner Loop: Find number of violations on the randomly
	 * assigned board Lets say # of violations is 10. If # violation is > 0 Then
	 * change the values on the board i.e. random cell if flip-flopped After the
	 * flop, if new number of violation is less than previous number, loop end if
	 * not, it re enters the loop and does another flip flop until it finds less
	 * violations. End of iteration - Print # of violations If current # violations
	 * < previous # violations, then exit loop and one iteration is done.
	 * 
	 */

	public int getIterations() {
		return iterations;
	}

	// LocalSearch Method
	public boolean trySearch() {
		initial();
		for (iterations = 0; iterations < 10000; iterations++) { // Runs 1000000 iterations before stopping

			// get random 2 cells and swap them
			int x1 = getRandom();
			int y1 = getRandom();

			int x2 = getRandom();
			int y2 = getRandom();

			int originalViolations = checkConstraints(finalSolution[x1][y1], x1, y1)
					+ checkConstraints(finalSolution[x2][y2], x2, y2);
			int newViolations = checkConstraints(finalSolution[x2][y2], x1, y1)
					+ checkConstraints(finalSolution[x1][y1], x2, y2);
			if (newViolations < originalViolations) {
				int swap = finalSolution[x1][y1];
				finalSolution[x1][y1] = finalSolution[x2][y2];
				finalSolution[x2][y2] = swap;

			}
			if (checkTotalConstraints() == 0) {
				return true;
			}
			// Random restarts
			if (Math.random() < 0.05 && checkTotalConstraints() > n * n) {
				initial();
			}
		}
		return false;
	}

	// create a random integer in the range 1-n
	public int getRandom() {
		int random = (int) (Math.random() * ((n))) + 1;
		return random;
	}

	// populating solution with random integers
	public void initial() {
		// create a table to track how many times the a value is put into puzzle
		Hashtable<Integer, Integer> appearances = new Hashtable<Integer, Integer>(n);
		for (int val = 1; val <= n; val++) {
			appearances.put(val, 0);
		}
		// place a random value (1-n) into the array
		for (int x = 1; x <= n; x++) {
			for (int y = 1; y <= n; y++) {
				while (finalSolution[x][y] == 0) { // make sure to put the correct number of numbers
					int value = getRandom();
					int times = appearances.get(value);
					if (appearances.get(value) < n) {
						finalSolution[x][y] = value;
						appearances.replace(value, times + 1); // update appearances of values
					}
				}
			}
		}
	}

	// Printing Solution from LocalSearch
	public void printSolution() {
		for (int x = 1; x <= n; x++) {
			for (int y = 1; y <= n; y++) {
				System.out.print(finalSolution[x][y] + " ");
			}
			System.out.println();
		}
		System.out.println(iterations);
	}

	// Check to see if puzzle is completed
	public boolean completed() {
		for (int x = 1; x <= n; x++) {
			for (int y = 1; y <= n; y++) {
				if (checkConstraints(finalSolution[x][y], x, y) != 0) {
					return false;
				}
			}
		}
		return true;
	}

	// Constraint Method (should be same as BackTrack) - VIOLATIONS OF CONSTRAINTS
	protected int checkConstraints(int value, int x, int y) {
		int violations = 0;
		violations += checkRow(x, y, value);
		violations += checkColumn(x, y, value);
		violations += checkOperations(x, y, value);
		return violations;
	}

	// Constraint Method (checks total constraints in array)
	protected int checkTotalConstraints() {
		int violations = 0;
		for (int x = 1; x <= n; x++) {
			for (int y = 1; y <= n; y++) {
				int value = finalSolution[x][y];
				violations += checkRow(x, y, value);
				violations += checkColumn(x, y, value);
				violations += checkOperations(x, y, value);
			}
		}
		return violations;
	}

	// CONSTRAINT 1 - check each row for no repeated number
	private int checkRow(int x, int y, int value) {
		int toReturn = 0;
		for (int col = 1; col <= y; col++) {
			if (finalSolution[x][col] == value) {
				toReturn++;
			}
		}
		return toReturn;
	}

	// CONSTRAINT 2 - check each column for now repeated number
	private int checkColumn(int x, int y, int value) {
		int toReturn = 0;
		for (int row = 1; row <= x; row++) {
			if (finalSolution[row][y] == value) {
				toReturn++;
			}
		}
		return toReturn;
	}

	// CONSTRAINT 3 - check each partition to see if it satisfies mathematical
	// expression
	private int checkOperations(int x, int y, int value) {
		int toReturn = 0;
		// get cage
		String point = "(" + x + "," + y + ")";
		String lookup = input.cageLookup.get(point);
		Cage cage = input.cages.get(lookup);
		// get operation total and set the actual total variable to the test value
		int opTotal = cage.getTotal();
		int actualTotal = value;
		if (cage.getOp().equals("=")) { // for single cells
			if (opTotal == actualTotal) {
				return 0;
			} else {
				return toReturn++;
			}
		}
		for (int index = 0; index < cage.locales.size(); index++) { // test all points of the cage

			int otherX = (int) cage.getLocalesX(index); // get x for final array *** Ced's Version
			int otherY = (int) cage.getLocalesY(index); // get y for final array **** Ced's Version

			int val = finalSolution[otherX][otherY]; // val of point listed in cage
			if (val != 0) { // if value has been declared in solution run the code, otherwise it doesn't
							// matter
				if (cage.getOp().equals("+")) { // if addition
					actualTotal += val;
				} else if (cage.getOp().equals("*")) { // if multiplication
					actualTotal *= val;
				} else if (cage.getOp().equals("/")) { // if division
					if (actualTotal % val != 0) {
						if (val % actualTotal != 0) {
							return toReturn++;
						}
					}
				} else if (cage.getOp().equals("-")) { // if subtraction
					if (Math.abs(actualTotal - val) != opTotal) {
						return toReturn++;
					}
				}
			}
		}
		if (actualTotal > opTotal) { // check if actual total will be greater than expected total and update
										// violations
			toReturn++;
		}
		return toReturn; // this will return number of violations
	}
}
