package kenken;

public class SimpleBackTrack {
	int n; // length and width of array
	private InputFile input; // inputfile that will have all the data

	private SearchTree tree;
	private Node currentNode;

	public SimpleBackTrack(InputFile file) {
		input = file;
		n = file.n;
		tree = new SearchTree(n);
		currentNode = tree.getRoot();
	}

	/*
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
	}*/

	// using search tree to search
	public void trySearch() {
		/*
		 * use current node and test for the next cell
		 * if you find a successful value for the next cell, 
		 * add it into the tree and update the current node to the next cell
		 * 
		 */
		while (tree.getDepth() <= n*n) { // keep it running until we get a solution
			if (backtrack() != 0) { // check if there is a possible value
				tree.addNewNode(currentNode, backtrack()); // add the value into the tree
				currentNode = currentNode.getLastChild(); // switch the node and start looking for next value
			} else { // otherwise go back up and start from the previous node
				currentNode = currentNode.getParent();
			}
		}

	}

	// backtrack method
	public int backtrack() {
		int test = 1;
		while (test <= n) {
			if(checkConstraints(test) && checkPreviousValues(test)) { // checks to see if the value will work
				return test;
			}
			test++; // try with another test value
		}
		return 0;
	}
	
	// this will check the parent's other children to see if you've tested the value before or not
	public boolean checkPreviousValues(int test) {
		for (Node child: currentNode.getChildren()) {
			if (child.getValue() == test) {
				return false;
			}
		}
		return true;
	}


	public void printSolution() { //print solution in matrix form 
		Node traverse = tree.getRoot();
		for (int x =0; x < n; x++) {
			for (int y =0; y < n; y++) {
				traverse = traverse.getLastChild();
				System.out.print(traverse.getValue()+" ");
			}
			System.out.println();
		}
	}

	// get x coordinate of cell
	public int getX(Node n) {
		int depth = tree.getDepthOfNode(n) + 1; //have to plus one because we're looking for the next node
		if (depth > 0) {
			return (int) Math.floor(depth / this.n);
		}
		return 0;
	}

	// get y coordinate of cell
	public int getY(Node n) {
		int depth = tree.getDepthOfNode(n) + 1; // have to plus one because we're looking for the next node
		if (depth > 0) {
			return (int) depth % this.n;
		}
		return 0;
	}

	//Constraints method
	protected boolean checkConstraints(int value) { //checks all the constraints
		if (checkRow(value) && checkColumn(value) && checkOperation(value)) {
			return true;
		}
		return false;
	}
	
	// check the row for the same number
	private boolean checkRow(int value) {
		Node traverse = currentNode;
		for (int i = 0; i < getY(currentNode); i++) { // this will make the node only go back to the beginning of the row
			if (traverse.getValue() == value) {
				return false;
			}
			if (traverse.getParent() == null) {
				break;
			}
			traverse = traverse.getParent();

		}
		return true;
	}

	// check the column for the same number
	private boolean checkColumn(int value) {
		Node traverse = currentNode;
		while (traverse.colUp() != null) { // use colUp to get the node that is n cells before it, thus a column up
			traverse = traverse.colUp();
			if (traverse.getValue() == value) {
				return false;
			}
		}
		return true;
	}

	// check to see if values + op get the total needed once all areas are filled
	private boolean checkOperation(int value) {
		//get cage
		String point = "("+ getX(currentNode)+ "," + getY(currentNode) + ")";
		String lookup = input.cageLookup.get(point);
		Cage cage = input.cages.get(lookup);
		//get operation total and set the actualtotal variable to the test value
		int opTotal = cage.getTotal();
		int actualTotal = value;
		for (int index =0; index < cage.locales.size(); index++ ) { //test all the points in the cage

			int otherX =  cage.getLocalesX(index); //get x for final array **** Ced's Version

			int otherY =  cage.getLocalesY(index); // get y for final array **** Ced's Version

			int val = tree.getNodeAtDepth(otherX*n + otherY).getValue(); // val of point listed in cage
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

}
