package kenken;

public class Cell {
	// These are the variable fields for the cell

	/*
	 * This will contain 0-n boolean values where 0 will be if the cell has an
	 * answer or not and 1-n will correspond to value and if they are applicable for
	 * the cell
	 * 
	 * BUT maybe instead we could use a stack instead?
	 */
	private boolean[] possibleValues;

	// fields listed in google docs
	private int value;
	private char op;

	// This will be the group its associated with
	private String operation;

	// constructor
	public Cell(int size, String op) {
		possibleValues = new boolean[size];
		operation = op;

	}

	public Cell(char operation) {
		setOp(operation);
		setValue(0);
	}

	// Basic setter and getter methods for the fields
	public String getOperation() {
		return operation;
	}

	public void changeOp(String op) {
		operation = op;
	}

	public boolean[] getPossibles() {
		return possibleValues;
	}

	public void notPossible(int index) {
		possibleValues[index] = false;
	}

	public boolean foundAnswer() {
		return possibleValues[0];
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public char getOp() {
		return op;
	}

	public void setOp(char op) {
		this.op = op;
	}
}
