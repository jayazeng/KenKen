package kenken;
/*
 * This class creates a n-ary tree that stores nodes
 * 
 */
public class SearchTree {
	private Node root;
	private int n;
	
	public SearchTree(int n) {
		this.n = n;
		root = new Node(n);
		root.parent = null;
	}
	// adds a new node to a parent... maybe used for some kind of forward tracking method
	public void addNewNode(Node parent, int childValue, int index) {
		Node child = new Node(n);
		child.setValue(childValue);
		parent.addChild(child, index);		
	}
	
	// returns number of total nodes traversed by tree
	public int getNumOfNodes(Node root) {
		int count = 0;
		if (root.getChildren().size() != 0) {
			for (Node child: root.getChildren()) {
				count += getNumOfNodes(child);
			}
		}
		return count;
	}
	//get height of search tree and when that is equal to n*n, we've found all the values
	public int getDepth(Node root) {
		if (root == null) {
			return 0;
		} else {
			int maxDepth = 0;
			for(Node child: root.getChildren()) {
				maxDepth = Math.max(maxDepth, getDepth(child));
			}
			return maxDepth + 1;
		}
		
	}
	
	public Node getRoot() {
		return this.root;
	}
	
}
