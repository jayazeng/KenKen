package kenken;

import java.util.ArrayList;

public class Node {
	private int value; // value chosen that successfully will pass constraints test for cell
	private Node parent;
	private ArrayList<Node> children; // all the possible values that can be checked for the next cell
	private int n;

	public Node(int n) {
		children = new ArrayList<Node>(n);
		this.n = n;
	}
	// if passes constraints, add a child node
	public void addChild(int value) {
		Node child = new Node(this.n);
		child.setValue(value);
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

	// return the value of node (useful for printing out solution)
	public int getValue() {
		return value;
	}

	// set the node value
	public void setValue(int value) {
		this.value = value;
	}
}
