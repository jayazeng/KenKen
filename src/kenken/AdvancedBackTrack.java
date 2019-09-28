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

	String op; // short for operator

	String letter; // cage letter from game board

	int cageTotal; // total the cage is trying to equal to

	String nextCoord; // for the next coordinate used for

	ArrayList<Object> tmp; // temporary array

	Hashtable<String, ArrayList<Object>> cageDomain = new Hashtable<String, ArrayList<Object>>(); // Collection -
																									// coordinate as key
																									// and set of
																									// numbers or Domain

	int[][] solution, tempSol; // this creates the matrix n x n

	int n; // size

	// Constructor that takes InputFile
	public AdvancedBackTrack(InputFile file) {
		super(file);

		input = file; // grabs the parsed dataFile

		n = input.n; // assigns the dimension to n

		solution = new int[n + 1][n + 1]; // creates a solution array

		tempSol = new int[n + 1][n + 1]; // creates a temporary solution array

		preCheck(input); // assigns domain values based on the total and the operator

		findSingle(); // finds the single cages with ='s

	}

	// Here's the method for finding the cage op
	public void findSingle() {
		for (Cage cage : input.cages.values()) {
			if (cage.getOp().equals("=")) {

				int x = cage.getLocalesX(0);

				int y = cage.getLocalesY(0);

				// put into solutions array
				solution[x][y] = cage.getTotal();
				tempSol[x][y] = solution[x][y];
				// change cage operator from '=' into '$' to ignore when ran again
				cage.setOp("$");

				String coord = "(" + x + "," + y + ")";

				cellReduction(coord, cageDomain);
			}

		}

	}

	// returns the coordinates for the smallest domain, other than single cells
	public String findNextLowest(Hashtable<String, ArrayList<Object>> table) {

		int i;
		int j;
		String tmpCoord;
		int mcv = n;
		int size = n;
		String mcvCoord = null;

		for (i = n; i > 0; i--) {

			for (j = n; j > 0; j--) {

				tmpCoord = "(" + i + "," + j + ")"; // assigns a temp coordinate

				size = table.get(tmpCoord).size(); // checks the size of the coordinate

				if (mcv >= size && mcv >= 1 && tempSol[i][j] == 0) { // ensures the value is lower than n, greater than
																		// 1 and not a size of 1

					mcv = size;

					mcvCoord = tmpCoord;

				}

			}

		}

		if (mcvCoord == null) {

			solution = tempSol;

			return null;
		}

		return mcvCoord;
	}

	// method takes the input which consist of the all the hashtables and game
	// information
	// from the InputFile class
	public void preCheck(InputFile input) {

		String key;
		String letter;

		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {

				tmp = new ArrayList<Object>(15); // size of array, shouldn't be more than 15 in size

				key = "(" + i + "," + j + ")"; // coordinate of cell starting at (1,1)

				letter = input.cageLookup.get(key); // cage letter

				cageTotal = input.cages.get(letter).getTotal(); // number we want to achieve

				op = input.cages.get(letter).getOp();

				// Add
				if (op.equals("+")) {

					// loop thru and only get numbers < total

					for (int q = 1; q <= n; q++) {

						tmp.add(q);

					}

				}
				// Subtract
				if (op.equals("-")) {

					for (int q = 1; q <= n; q++) {

						// loop thru and grab number pairs that equal total
						// numbers could be higher and lower than total

						tmp.add(q);

					}

				}
				// Multiply
				if (op.equals("*")) {

					for (int q = 1; q <= n; q++) {

						// loop thru and grab number pairs that have a mod of zero of total

						if (cageTotal % q == 0) {

							tmp.add(q);

						}

					}

				}
				// Division
				if (op.equals("/")) {

					for (int q = 1; q <= n; q++) {

						// loop thru and grab number pairs that have a mod of zero of total
						if (cageTotal * q <= n || cageTotal % q == 0 || q % cageTotal == 0) {

							tmp.add(q);

						}

					}

				}
				// Equals
				if (op.equals("=")) {

					tmp.add(cageTotal);

				}

				cageDomain.put(key, tmp);

			}
		} // end of i & j loop

	} // end of preCheck

	// input successful coordinate
	public String cellReduction(String coord, Hashtable<String, ArrayList<Object>> table) {

		// get row and set row
		int row = Integer.parseInt(coord.substring(1, 2));

		// get col and set col
		int col = Integer.parseInt(coord.substring(3, 4));

		// need to check if there is a number there
		int value = (int) table.get(coord).get(0);

		int index;

		// remove value from col's domain
		for (int r = 1; r <= n; r++) {

			String key = createCoord(r, col);

			if (!key.equals(coord) && tempSol[r][col] == 0) {

				index = table.get(key).indexOf(value);

				if (index != -1) {

					table.get(key).remove(index);

				}

			}

		}
		// remove value from row's domain
		for (int c = 1; c <= n; c++) {

			String key = createCoord(row, c);

			if (!key.equals(coord) && tempSol[row][c] == 0) {

				index = table.get(key).indexOf(value);

				if (index != -1) {

					table.get(key).remove(index);

				}

			}

		}

		return null;

	}

	// creates coordinate based on x and y
	public String createCoord(int x, int y) {

		String ans = "(" + x + "," + y + ")";

		return ans;
	}

	// parses x from coordinate
	public int getX(String coord) {

		int x = Integer.parseInt(coord.substring(1, 2));

		return x;
	}

	// parses y from coordinate
	public int getY(String coord) {

		int y = Integer.parseInt(coord.substring(3, 4));

		return y;
	}

	// takes an integer and creates a coordinate
	public String reverseCoord(int k) {

		int q, r;

		q = k / input.n;

		r = k % input.n;

		return createCoord(q, r);

	}

	// forward checks which reduces values from other cells by row and column
	public boolean forwardCheck(String coord, Hashtable<String, ArrayList<Object>> tempDomain) {

		if (coord == null) {

			return false;
		}

		if (coord != null) {

			int x = getX(coord);

			int y = getY(coord);

			int indexSize = tempDomain.get(coord).size();

			for (int v = 0; v < indexSize; v++) {

				if (tempDomain.get(coord).get(v) != null) {

					tempSol[x][y] = (int) tempDomain.get(coord).get(v);

					// eliminate rows and cols
					cellReduction(coord, tempDomain);

				}

			}

		}

		// need to start again right here.

		return true;
	}

	//
	public boolean checkFormula() {

		return false;

	}

	public void trySearch() {
		/*
		 * use current node and test for the next cell if you find a successful value
		 * for the next cell, add it into the tree and update the current node to the
		 * next cell
		 * 
		 */
		while (tree.getDepth() <= n * n) { // keep it running until we get a solution
			int value = advBacktrack();
			if (value != 0) { // check if there is a possible value
				currentNode.addChild(value); // add the value into the tree
				nodesCreated++; // update how many nodes were created
				currentNode = currentNode.getLastChild(); // switch the node and start looking for next value
			} else { // otherwise go back up and start from the previous node
				currentNode = currentNode.getParent();
			}
		}

	}

	public int advBacktrack() {
		String coor = createCoord(getX(currentNode), getY(currentNode));
		ArrayList<Object> values = cageDomain.get(coor);
		// forwardCheck(coor, temp);
		int test = 1;
		while (test <= n) {
			if (values.contains(test) && checkConstraints(test) && checkPreviousValues(test)) { // checks to see if the
																								// value will work
				return test;
			}
			test++; // try with another test value
		}
		return 0;
	}

} // end of class
