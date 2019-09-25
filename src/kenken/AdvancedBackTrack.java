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
	
	int n; //size of the game board in one dimension
	
	int cageTotal;  // total the cage is trying to equal to
	
	ArrayList<Object> tmp; // temporary array
	
	Hashtable<String,ArrayList<Object>> cageDomain = new Hashtable<String,ArrayList<Object>>(); //Collection - coordinate as key and set of numbers or Domain
	
	public AdvancedBackTrack(InputFile file) {
		super(file);
		
		input = file;
		
		n = input.n;
		
		preCheck(input);
		
		
		System.out.print("hello");
		
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
			
		
		System.out.print("Cedric");
		
	} //end of preCheck
	
}  // end of class
