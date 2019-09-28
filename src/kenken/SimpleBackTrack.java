package kenken;

public class SimpleBackTrack {
	int n; // length and width of array
	private InputFile input; // input file that will have all the data

	public SearchTree tree;
	public Node currentNode; // node used to traverse through the tree
	protected int nodesCreated; // number of nodes created

	public SimpleBackTrack(InputFile file) {
		input = file;
		n = file.n;
		tree = new SearchTree(n);
		currentNode = tree.getRoot();
	}

	// using search tree to search
	public void trySearch() {
		/*
		 * use current node and test for the next cell if you find a successful value
		 * for the next cell, add it into the tree and update the current node to the
		 * next cell
		 * 
		 */
		while (tree.getDepth() <= n * n) { // keep it running until we get a solution
			if (backtrack() != 0) { // check if there is a possible value
				currentNode.addChild(backtrack()); // add the value into the tree
				nodesCreated++; // update how many nodes were created
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
			if (checkConstraints(test) && checkPreviousValues(test)) { // checks to see if the value will work
				return test;
			}
			test++; // try with another test value
		}
		return 0; // else return 0 which will trigger the else statement in trySearch()
	}

	// this will check the parent's other children to see if you've tested the value
	// before or not
	public boolean checkPreviousValues(int test) {
		if (currentNode == null) {
			return false;
		}
		for (Node child : currentNode.getChildren()) {
			if (child.getValue() == test) {
				return false;
			}
		}
		return true;
	}

	// print solution in matrix form
	public void printSolution() {
		System.out.println();
		Node traverse = tree.getRoot();
		// traverses the tree
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				traverse = traverse.getLastChild();
				System.out.print(traverse.getValue() + " ");
			}
			System.out.println();
		}
		// print the number of nodes created
		System.out.println(nodesCreated);
	}

	// get x coordinate of cell
	public int getX(Node n) {
		int depth = tree.getDepthOfNode(n) + 1; // have to plus one because we're looking for the next cell
		if (depth > 0) {
			int x = (int) Math.floor(depth / this.n);
			if (depth % this.n != 0) {
				return x + 1;
			}
			return x;
		}
		return 0;
	}

	// get y coordinate of cell
	public int getY(Node n) {
		int depth = tree.getDepthOfNode(n) + 1; // have to plus one because we're looking for the next cell
		if (depth > 0) {
			int y = (int) depth % this.n;
			if (y == 0) {
				return this.n;
			}
			return y;
		}
		return 0;
	}

	// Constraints method
	protected boolean checkConstraints(int value) { // checks all the constraints
		if (checkRow(value)) {
			if (checkColumn(value)) {
				if (checkOperation(value)) {
					return true;
				}
			}
		}
		return false;
	}

	// check the row for the same number
	public boolean checkRow(int value) {
		Node traverse = currentNode;
		for (int i = 1; i < getY(currentNode); i++) { // this will make the node only go back to the first cell of the
														// row
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
	public boolean checkColumn(int value) {
		int depth = tree.getDepthOfNode(currentNode) + 1;
		depth -= n;
		while (depth > 0) { // keep subtracting n from depth until it becomes a negative number to get all
							// cells in column
			if (tree.getNodeAtDepth(depth).getValue() == value) {
				return false;
			}
			depth -= n;
		}
		return true;
	}

	// check to see if values + op get the total needed once all areas are filled
	public boolean checkOperation(int value) {
		// get partition of cell
		String point = "(" + getX(currentNode) + "," + getY(currentNode) + ")";
		String lookup = input.cageLookup.get(point);
		Cage cage = input.cages.get(lookup);

		// get operation total and set the actual total variable to the test value
		int opTotal = cage.getTotal();
		int actualTotal = value;

		// Single cells don't need to look further in the cage
		if (cage.getOp().equals("=")) {
			if (opTotal == actualTotal) {
				return true;
			} else {
				return false;
			}
		}

		// get the depth of the cell you are looking for
		int depth = tree.getDepthOfNode(currentNode) + 1;

		// test all the cells in the partition
		for (int index = 0; index < cage.locales.size(); index++) {

			// get x and y coordinates of the cell in the partition
			int otherX = cage.getLocalesX(index);
			int otherY = cage.getLocalesY(index);

			// this will get the depth of the cell based off the x and y
			int otherDepth = otherY + (otherX - 1) * n;

			// since the only cells that have values are the ones before, any cell
			// that needs to be checked will have a lower depth
			if (otherDepth >= depth) {
				if (index == 0) { //
					return true;
				}
				break;
			}
			// value of cell listed in partition
			int val = tree.getNodeAtDepth(otherDepth).getValue();

			// Division and Subtraction only have two cells in the partition
			// so this would check if the second cell of the partition works
			if (cage.getOp().equals("/")) { // if division
				if (actualTotal / val == opTotal) {
					return true;
				}
				if (val / actualTotal == opTotal) {
					return true;
				}
				return false;
			}
			if (cage.getOp().equals("-")) { // if subtraction
				if (Math.abs(actualTotal - val) == opTotal) {
					return true;
				}
				return false;
			}
			// if addition
			if (cage.getOp().equals("+")) {
				actualTotal += val;
			}
			// if multiplication
			if (cage.getOp().equals("*")) {
				actualTotal *= val;
			}

			// if you are checking the last cell in the partition
			// the values should match up, otherwise you're wrong
			if (index == cage.locales.size() - 1) {
				if (actualTotal != opTotal) {
					return false;
				}
				return true;
			}
		}

		// after iterating through all the solved cells in the partition without
		// completing the partition
		if (actualTotal > opTotal) { // check if actual total will be greater than expected total
			return false;
		}
		return true; // this will return true if actual total is less than or equal expected total
	}

}
