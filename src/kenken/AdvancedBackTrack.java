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

	int cageTotal;  // total the cage is trying to equal to

	String nextCoord; // for the next coordinate used for 

	ArrayList<Object> tmp; // temporary array

	Hashtable<String,ArrayList<Object>> cageDomain = new Hashtable<String,ArrayList<Object>>(); //Collection - coordinate as key and set of numbers or Domain

	int [][] solution,tempSol;  // this creates the matrix n x n

	int n; // size


	//Constructor that takes InputFile
	public AdvancedBackTrack(InputFile file) {
		super(file);

		input = file;  // grabs the parsed dataFile

		n = input.n; //assigns the dimension to n

		solution  = new int[n+1][n+1];  // creates a solution array
		tempSol = new int[n+1][n+1];  // creates a temporary solution array

		preCheck(input);  // assigns domain values based on the total and the operator 

		findSingle();  // finds the single cages with ='s

		//Hashtable<String,ArrayList<Object>> tempDomain = cageDomain;  // creates hashtable to store temporary cageDomains

		//nextCoord = findNextLowest(tempDomain);  // finds the most constrained cell

		//forwardCheck(nextCoord, tempDomain); // forward checks 

	}

	// Here's the method for finding the cage op
	public void findSingle() {
		for (Cage cage : input.cages.values()) {
			if (cage.getOp().equals("=")) {

				int x  = cage.getLocalesX(0);

				int y = cage.getLocalesY(0);

				// put into solutions array
				solution[x][y] = cage.getTotal();
				tempSol[x][y] = solution[x][y];
				// change cage operator from '=' into '$' to ignore when ran again
				cage.setOp("$");

				String coord = "(" + x + "," + y + ")";

				cellReduction(coord,cageDomain);
			} 	

		}

	}


	// returns the coordinates for the smallest domain, other than single cells
	public String findNextLowest(Hashtable<String,ArrayList<Object>> table) {  

		int i;
		int j;
		String tmpCoord;
		int mcv = n; 
		int size = n;
		String mcvCoord = null;

		for(i=n;i >0 ;i--) {

			for(j= n; j >0; j--) {

				tmpCoord  = "("+i+","+j+")";  // assigns a temp coordinate

				size = table.get(tmpCoord).size();  // checks the size of the coordinate

				//				String letter = input.cageLookup.get(tmpCoord);

				//				String op = input.cages.get(letter).op;

				if(mcv >=  size && mcv >= 1 && tempSol[i][j]==0) {  // ensures the value is lower than n, greater than 1 and not a size of 1

					mcv = size;

					mcvCoord = tmpCoord;

				}

			}

		}

		System.out.println("Next lowest mcv is "+ mcvCoord + " at " + mcv + " coordinates");

		if(mcvCoord == null) {

			solution = tempSol;

			return null;
		}


		return mcvCoord;
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




	} //end of preCheck


	// input successful coordinate
	public String cellReduction(String coord, Hashtable<String,ArrayList<Object>> table) {

		//get row and set row
		int row = Integer.parseInt(coord.substring(1,2));

		//get col  and set col
		int col= Integer.parseInt(coord.substring(3,4));

		// need to check if there is a number there
		int value = (int) table.get(coord).get(0);

		int index;

		//remove value from col's domain
		for(int r= 1;r<=n;r++) {

			String key = createCoord(r,col);

			if(!key.equals(coord) && tempSol[r][col]==0) {

				index = table.get(key).indexOf(value);

				if(index != -1) {

					table.get(key).remove(index);

					System.out.println("Removed " + value + " from domain at " + key);
				}

			}


		}
		//remove value from row's domain
		for(int c= 1;c<=n;c++) {

			String key = createCoord(row,c);

			if(!key.equals(coord) && tempSol[row][c]==0) {

				index = table.get(key).indexOf(value);

				if(index != -1) {

					table.get(key).remove(index);

					System.out.println("Removed " + value + " from domain at " + key);

				}

			}

		}

		return null;

	}

	// creates coordinate based on x and y
	public String createCoord(int x, int y) {

		String ans = "("+x+","+ y + ")";

		return ans;
	}

	// parses x from coordinate
	public int getX(String coord) {

		int x = Integer.parseInt(coord.substring(1,2));


		return x;
	}

	// parses y from coordinate
	public int getY(String coord) {

		int y = Integer.parseInt(coord.substring(3, 4));

		return y;
	}


	// takes an integer and creates a coordinate
	public String reverseCoord(int k) {

		int q,r;

		q = k/input.n;

		r = k%input.n;

		return createCoord(q,r);	

	}

	// forward checks which reduces values from other cells by row and column  
	public boolean forwardCheck(String coord, Hashtable<String,ArrayList<Object>> tempDomain ) {

		if(coord == null) {

			return false;
		}

		if(coord != null) {

			int x = getX(coord);

			int y = getY(coord); 

			int indexSize = tempDomain.get(coord).size();
			
			for(int v = 0;v<indexSize;v++) {

				if(tempDomain.get(coord).get(v) != null) {

					tempSol[x][y] = (int) tempDomain.get(coord).get(v);

					// eliminate rows and cols
					cellReduction(coord, tempDomain);

					// input the next lowest cell urecursively till exhausted or failed
//					if(forwardCheck(findNextLowest(tempDomain),tempDomain)) {
//
//						return true;
//					}




				}

			}

		}

		//  need to start again right here.

		System.out.println("Finished");

		return true;
	}

	// 
	public boolean checkFormula() {


		return false;

	}


	public void trySearch() {
		/*
		 * use current node and test for the next cell
		 * if you find a successful value for the next cell, 
		 * add it into the tree and update the current node to the next cell
		 * 
		 */
		while (tree.getDepth() <= n*n) { // keep it running until we get a solution
			String coor = createCoord(getX(currentNode), getY(currentNode));
			//ArrayList<Object> possibles = cageDomain.get(coor);
			int value = advBacktrack();
			if (value != 0) { // check if there is a possible value
				currentNode.addChild(value); // add the value into the tree
				nodesCreated++; // update how many nodes were created
				currentNode = currentNode.getLastChild(); // switch the node and start looking for next value
			} else { // otherwise go back up and start from the previous node
				currentNode = currentNode.getParent();
			}
			tree.printTree();
		}

	}

	public int advBacktrack() {
		String coor = createCoord(getX(currentNode), getY(currentNode));
		ArrayList<Object> values = cageDomain.get(coor);
		//forwardCheck(coor, temp);
		int test = 1;
		while (test <= n) {
			if(values.contains(test) && checkConstraints(test) && checkPreviousValues(test)) { // checks to see if the value will work
				return test;
			}
			test++; // try with another test value
		}
		return 0;
	}

		/*
	// Janelle's ForwardCheck
	// method uses the cageDomain and tmp variables to test if a testValue placed in a certain cell 
	// will cause another cell further in the puzzle to not have a value
	// if it does, then the method returns false and if it doesn't the method returns true
	@SuppressWarnings("unchecked")
	public boolean forwardCheck2(int testValue) {


		// create a temporary array lists to list all possible values that can be updated in the method only
		ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
		for (Object ob:cageDomain.values()) {
			temp.add((ArrayList<Integer>) ob);
		}

		// traverse through the created search tree and add the previous values if there are any 
		Node traverse = tree.getRoot(); 

		// set index
		int index = 0;


		while (traverse != currentNode) {
			temp.set(index, traverse.getLastChild().getValue());  //why are you changing the temp cageDomain which has less possible numbers?
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
		int rowNum = (int) Math.floor(cellNum/n);
		int rowStart = rowNum * n + 1;
		for (int i = rowStart; i < rowStart + n; i++) {
			temp.get(i).remove(testValue);
			if (temp.get(i).isEmpty()) {
				return false;
			}
		}


		// go through and remove the possible values based on column
		// use the fact that the col index should be a difference of n from the index

		int colStart = cellNum - rowStart + 1;
		for (int i = colStart; i < n*n; i += n) {
			temp.get(i).remove(testValue);
			if (temp.get(i).isEmpty()) {
				return false;
			}
		}

		// no need to go through and remove the possible values based on operation


		// check to see if any list is empty, and if it is return false
		// else return true

		return true;
	}
		 */

}  // end of class
