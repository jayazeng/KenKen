package kenken;

public class BackTrack {
	/* PseudoCode for Back Track
	 * 
	 * 1) Set up cage
	 * 2) Create a binary tree of cells
	 * 3) Depth First
	 * 
	 * 
	 */
	public Cell c;
	public int[][] finalAnswers;
	
	
	//SUPER SIMPLE METHODS THAT PROBABLY WON'T WORK
	// check the rows for the same number
	public boolean checkRow( ) {
		if (/* some hashtable*/.contains(c.getValue())) {
			return false;
		}
		return true;
	}
	
	// check the column for the same number
	public boolean checkColumn( ) {
		if (/* some hashtable column*/.contains(c.getValue())) {
			return false;
		}
		return true;
	}
	
	// check to see if values + op get the total needed once all areas are filled
	public boolean checkOperation( ) {
		//hashtable by op
		String op = c.getOperation();
		int opTotal = Integer.parseInt(op.substring(0,op.length()-1));
		int actualTotal = 0;
		if (actualTotal != opTotal) {
			return false;
		}
		return true;
	}
}
