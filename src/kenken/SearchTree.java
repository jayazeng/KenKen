package kenken;

/*
 * This class creates a n-ary tree that stores nodes
 * 
 */
public class SearchTree {
	private Node root;

	public SearchTree(int n) {
		root = new Node(n);
		root.setParent(null);
	}

	// get height of search tree and when that is equal to n*n, we've found all the
	// values
	public int getDepth() {
		Node traverse = this.root;
		int depth = 0;
		while (traverse != null) {
			traverse = traverse.getLastChild();
			depth++;
		}
		return depth;
	}

	// find the depth of a node in the tree so as to know how much down it is
	public int getDepthOfNode(Node find) {
		if (find == null) {
			return 0;
		}
		Node traverse = this.root;
		int depth = 0;
		while (traverse != find) {
			traverse = traverse.getLastChild();
			depth++;
		}
		return depth;
	}

	// function used to get a specific node at a depth or say that it hasn't been
	// declared yet
	public Node getNodeAtDepth(int depth) {
		Node traverse = this.root;
		for (int i = 0; i < depth; i++) {
			if (traverse.getLastChild() == null) {
				return null;
			}
			traverse = traverse.getLastChild();
		}
		return traverse;
	}

	public void printTree() {
		Node traverse = this.root;
		while (traverse.getLastChild() != null) {
			System.out.print(traverse.getLastChild().getValue() + " ");
			traverse = traverse.getLastChild();
		}
		System.out.println();
	}

	public Node getRoot() {
		return this.root;
	}

}
