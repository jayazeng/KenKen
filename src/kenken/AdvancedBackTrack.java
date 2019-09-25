/* This class controls the Advanced Back Track algorithm which is a subclass or extension of SimpleBackTrack.
 * First we establish a hashtable to store a cage coordinate <key> and a set of potential numbers which fit the total 
 * and operator constraint.  This is done before the algorithm solves the puzzle.  We call this preCheck.
 * 
 * After preCheck, the puzzle will start at single cell cages and reduce the set of numbers in the domain, making
 * the domain even smaller.
 * 
 * 
 */

package kenken;

import java.util.ArrayList;
import java.util.Hashtable;

public class AdvancedBackTrack extends SimpleBackTrack {

	private InputFile input; // inputfile that will have all the data

	String op;  //short for operator

	String letter;  // cage letter from game board

	int n; //size of the game board in one dimension

	int cageTotal;  // total the cage is trying to equal to

	ArrayList<Object> tmp; // temporary array

	Hashtable<String,ArrayList<Object>> cageDomain = new Hashtable<String,ArrayList<Object>>(); //Collection - coordinate as key and set of numbers or Domain

	SearchTree tree;
	Node currentNode;
	
	// Here's the method for finding the cage op
	public void findSingle() {
		for (Cage cage : input.cages.values()) {
			if (cage.getOp().equals("=")) {
				int x  = cage.getLocalesX(0);
				int y = cage.getLocalesY(0);
				//solution[x][y] = cage.getTotal();
			}
		}
	}
	
	public AdvancedBackTrack(InputFile file) {
		super(file);

		input = file;

		n = input.n;

		preCheck(input);


		System.out.print("hello");

	}

	// method uses the cageDomain and tmp variables to test if a testValue placed in a certain cell 
	// will cause another cell further in the puzzle to not have a value
	// if it does, then the method returns false and if it doesn't the method returns true
	public boolean forwardCheck(int testValue) {

		// create a temporary array lists to list all possible values that can be updated in the method only
		ArrayList<Object> temp = new ArrayList<Object>(cageDomain.values());

		// traverse through the created search tree and add the previous values if there are any 
		Node traverse = tree.getRoot(); 
		int index = 0;
		while (traverse != currentNode) {
			temp.set(index, traverse.getLastChild().getValue());
			traverse = traverse.getLastChild();
			index++;
		}

		// this will get the cell number of the cell we are looking at
		int cellNum = this.tree.getDepthOfNode(currentNode) + 1; 
		
		// set the testValue in the temp list and correct index, but since it's an arraylist and starts at 0, subtract 1 from the cellNum
		temp.set(cellNum-1, testValue);

		// now the temp array should contain single values to represent the cell values already chosen
		// and list of possible values for cells not initialized yet

		// go through and remove the possible values based on row
		// can use the subList(int fromIndex, int toIndex) to get just the row

		// go through and remove the possible values based on column
		// use the fact that the col index should be a difference of n from the index

		// no need to go through and remove the possible values based on operation

		// check to see if any list is empty, and if it is return false
		// else return true
		return true;
	}



	// method takes the input which consist of the all the hashtables and game information
	// from the InputFile class
	public void preCheck(InputFile input) {

		String key;
		String letter;




		for(int i = 1; i <= n;i++) {
			for(int j = 1;j<=n;j++) {

				tmp = new ArrayList<Object>(15);  // size of array, shouldn't be more than 15 in size

				key = "("+i+","+j+")";  // coordinate of cell starting at (1,1)

				letter = input.cageLookup.get(key); // cage letter

				cageTotal = input.cages.get(letter).getTotal(); // number we want to achieve

				op = input.cages.get(letter).getOp();

				// Add
				if(op.equals("+")) {

					// loop thru and only get numbers < total

					for(int q = 1; q <= n;q++) {

						tmp.add(q);

					}




				}
				// Subtract
				if(op.equals("-")) {

					for(int q = 1;q<=n;q++) {


						// loop thru and grab number pairs that equal total
						// numbers could be higher and lower than total

						tmp.add(q);

					}


				}
				// Multiply
				if(op.equals("*")) {

					for(int q = 1;q<=n;q++) {


						// loop thru and grab number pairs that have a mod of zero of total


						if(cageTotal % q == 0) {

							tmp.add(q);

						}


					}


				}
				// Division
				if(op.equals("/")) {

					for(int q = 1;q<=n;q++) {


						// loop thru and grab number pairs that have a mod of zero of total
						if(cageTotal * q <= n || cageTotal % q ==0 || q % cageTotal == 0) {

							tmp.add(q);

						}

					}



				}
				// Equals
				if(op.equals("=")) {

					tmp.add(cageTotal);

				}

				cageDomain.put(key, tmp);

			}}  //end of i & j loop


		System.out.print("Cedric");

	} //end of preCheck

}  // end of class
