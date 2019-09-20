package kenken;

import java.util.ArrayList;
import java.util.Arrays;

public class Node {
	private int value; // value chosen that successfully will pass constraints test for cell
	public Node parent;
	private ArrayList<Node> children; // all the possible values that can be checked for the next cell
	private int n;
	private int x; // tentative x and y fields which should be the same for each level of the tree???
	private int y;
	private boolean[] possibles;
	
	public Node(int n) {
		children = new ArrayList<Node>(n);
		this.n = n;
		possibles = new boolean[n];
		Arrays.fill(possibles, Boolean.TRUE);
	}
	// if passes constraints, add a child node
	public void addChild(Node child, int index) {
		if (index < n) {
			child.parent = this;
			this.children.set(index, child);
		}
	}
	// get all children
	public ArrayList<Node> getChildren() {
		return this.children;
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

	public boolean getPossibles(int index) {
		return possibles[index];
	}

	public void setPossiblesFalse(int index) {
		this.possibles[index] = false;
	}
	
	
}
