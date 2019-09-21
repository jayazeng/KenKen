package kenken;

import java.util.ArrayList;

public class Node {
	private int value; // value chosen that successfully will pass constraints test for cell
	private Node parent;
	private ArrayList<Node> children; // all the possible values that can be checked for the next cell
	private int n;
	private int x; // tentative x and y fields which should be the same for each level of the tree???
	private int y;

	public Node(int n) {
		children = new ArrayList<Node>(n);
		this.n = n;
	}
	// if passes constraints, add a child node
	public void addChild(Node child) {
		child.parent = this;
		this.children.add(child);
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public Node getParent() {
		return this.parent;
	}
	// get all children
	public ArrayList<Node> getChildren() {
		return this.children;
	}

	// this will return the node that worked
	public Node getLastChild() {
		if (this.children.isEmpty()) {
			return null;
		}
		return this.children.get(this.children.size() - 1);
	}

	// will return node that is n cells before it in the puzzle or returns null to signify there isn't one
	public Node colUp() {
		Node traverse = this;
		for (int i = 0; i < n; i++) {
			if (traverse.parent == null) {
				return null;
			}
			traverse = traverse.parent;
		}
		return traverse;
	}
	// return the value of node (useful for printing out solution)
	public int getValue() {
		return value;
	}

	// set the node value
	public void setValue(int value) {
		this.value = value;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
