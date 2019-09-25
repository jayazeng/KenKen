/* This class controls the Advanced Back Track algorithm which is a subclass or extension of SimpleBackTrack.
 * First we establish a hashtable to store a cage letter <key> and a set of potential numbers which fit the total 
 * and operator constraint.  This is done before the algorithm solves the puzzle.
 * 
 * We use an ARC Consistency to eliminate the domains prior to running the solve algorithm.
 * 
 * Our forward checking algorithm
 */

package kenken;

import java.util.ArrayList;
import java.util.Hashtable;

public class AdvancedBackTrack extends SimpleBackTrack {
	
	private InputFile input; // inputfile that will have all the data

	String op;
	
	String letter;
	
	int n;
	
	int cageTotal;
	
	ArrayList<Object> tmp; 
	
	Hashtable<String,ArrayList<Object>> cageDomain = new Hashtable<String,ArrayList<Object>>(); //Collection - letter as key and Cage Total & Domain
	
	public AdvancedBackTrack(InputFile file) {
		super(file);
		
		input = file;
		
		n = input.n;
		
		preCheck(input);
		
		
		System.out.print("hello");
		
	}


	

	 
	// Inputs a cell location , total, operator and dimension size (n) and determines 
	// possible values for each cage.
	public void preCheck(InputFile input) {
		
		String key;
		String letter;
		
		
		
		
		for(int i = 1; i <= n;i++) {
			for(int j = 1;j<=n;j++) {
	
				tmp = new ArrayList<Object>(15);
				
				key = "("+i+","+j+")";
				
				letter = input.cageLookup.get(key);
				
				cageTotal = input.cages.get(letter).getTotal();
				
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
