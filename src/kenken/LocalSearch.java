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
import java.awt.geom.Point2D;
public class LocalSearch {
	private int[][] finalSolution; //2D int array to hold the solution
	int n; //length and width of array
	private boolean completed;
	private int iterations; //number of nodes accessed
	private InputFile input; //input file that will have all the data 
	private Point2D p; //place holder point that's only used to access values 
	
	public LocalSearch(InputFile file) {
		completed = false;
		iterations = 0;
		input = file;
		n = file.n;
	}
	
	
/*Brainstorm on creating LocalSearch Loop
 *  
 *  Loop inside a loop:
 *  Inner Loop:
 *  Find number of violations on the randomly assigned board
 *  	Lets say # of violations is 10. 
 *  	If # violation is > 0
 *  		Then change the values on the board 
 *  				i.e. random cell if flip-flopped
 *  			After the flop, if new number of violation is less than previous number, loop end
 *  			if not, it re enters the loop and does another flip flop until it finds less violations.   
 *  End of iteration - Print # of violations 
 *  If current # violations < previous # violations, then exit loop and one iteration is done. 
 *  
 */
	
//LocalSearch Method
	public boolean LocalSearch(InputFile input) {
		if(completed) {
			return true;
		}
		for (int iteration = 0; iteration < 1000000; iteration++) { //Runs 1000000 iterations before stopping
			//inner loop goes here
			
		}
		return false;
	}
	
	
	
//Printing Solution from LocalSearch
	public void printSolution(int[][] solution) {
		for(int x = 0; x < solution.length; x++) {
			for(int y = 0; y < solution.length; y++) {
				System.out.print(finalSolution[x][y]+ " ");
			}
			System.out.println();
		}
	}
	
	
//Constraint Method (should be same as BackTrack) - VIOLATIONS OF CONSTRAINTS 
	protected boolean checkConstraints(int value, int x, int y) {
		if (checkRow(x,y,value) && checkColumn(x,y,value) && checkOperations(x,y,value)) {
			return true;
		}
		return false;
	}
	
	//CONSTRAINT 1 - check each row for no repeated number
	private boolean checkRow(int x, int y, int value) {
		for (int col = 0; col < y ; col ++) {
			if(finalSolution[x][col] == value) {
				return false;
			}
		}
		return true;
	}
	//CONSTRAINT 2 - check each column for now repeated number
	private boolean checkColumn(int x, int y, int value) {
		for (int row = 0; row < x; row++) {
			if(finalSolution[row][y] == value){
				return false;
			}
		}
		return true;
	}
	//CONSTRAINT 3 - check each partition to see if it satisfies mathematical expression 
	private boolean checkOperations(int x, int y, int value) {
		//hashtable by op
		p.setLocation(x,y);
		//get cage
		String lookup = input.cageLookup.get(p);
		Cage cage = input.cages.get(lookup);
		//get operation total and set the actual total variable to the test value
		int opTotal = cage.getTotal();
		int actualTotal = value;
		for (int index = 0; index < cage.locales.size(); index++) { //test all points of the cage
			int otherX = (int) cage.locales.get(index).getX(); //get x for final array
			int otherY = (int) cage.locales.get(index).getY(); //get y for final array
			int val = finalSolution[otherX][otherY]; //val of point listed in cage
			if (val != 0) { //if value has been declared in solution run the code, otherwise it doesn't matter
				if (cage.getOp().contentEquals("+")) {//if addition
					actualTotal += val;
				}else if (cage.getOp().contentEquals("*")) {
					actualTotal *= val;
				}else if (cage.getOp().contentEquals("/")) {
					actualTotal /= val;
				}else if (cage.getOp().contentEquals("-")) {
					actualTotal -= val;
				}
			}
		}
		if (actualTotal > opTotal) { //check if actual total will be greater than expected total
			return false;
		}
		return true; //this will return true if actual total is less than or equal to expected total
	}
	
	
public int getIterations() {
	return iterations;
}
	
	
	
	


	
	
	

}

