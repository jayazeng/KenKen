package kenken;

import java.util.ArrayList;
import java.util.Hashtable;

public class AdvancedBackTrack extends SimpleBackTrack {

	//int n; // length and width of array
	
	private InputFile input; // inputfile that will have all the data

	Hashtable<String,ArrayList<Object>> cageTD ; //Cage Total & Domain
	
	public AdvancedBackTrack(InputFile file) {
		super(file);
		
		input = file;
		
		//setTotalOps(input.);
		
		
		System.out.print("hello");
		
	}

	
	
	// takes in current domain and reduces possibilities by what the total can be
	public void limitCageDomain() {
		
	
		
		
	}
	
	private void setTotalOps( String line) {
		
		int lineLength = line.length(); // need to check the line length to know where to find the operator
		
		String op = parseOp(lineLength,line,"op"); // line.substring(lineLength-1) finds the operator
		
		int total = Integer.parseInt(parseOp(lineLength,line,"total")); // finds the total value
		
		String letter = line.substring(0,1);
		
		input.cages.get(letter).op = op; // inserts eachCage into hashtable(cages) if its not there
		
		input.cages.get(letter).total = total;
		
		cageTD.put(letter, forwardChecking(letter,total,op,n));
		
	}
	
	private String parseOp(int lineLength, String line, String type) {
		
		
		if(type=="op" && !Character.isDigit(line.charAt(lineLength-1))) {
	
			
			return line.substring(lineLength-1);
		
		} else
		
			if(type=="op" && Character.isDigit(line.charAt(lineLength-1))) {
	
			
			return "=";
		}
		
		
		if(type=="total" && !Character.isDigit(line.charAt(lineLength-1))) {
			
			return line.substring(2,lineLength-1);
		} 
		
		return line.substring(2);
	}
	 
	
	public ArrayList<Object> forwardChecking(String letter, int x, String op,int n) {
		
		ArrayList<Object> tmp = new ArrayList<Object>();
		
		// Add
		if(op=="+") {
		
			// loop thru and only get numbers < total
			
			for(int q = 1; q < x;q++) {
				
				
				
			}
			
			return tmp;
		}
		// Subtract
		if(op=="-") {
			
			for(int q = 1;q<n;q++) {
				
				
			// loop thru and grab number pairs that equal total
				// numbers could be higher and lower than total
				
			}
			
			return tmp;
		}
		// Multiply
		if(op=="*") {
			
			for(int q = 1;q<=x;q++) {
				
				
			// loop thru and grab number pairs that have a mod of zero of total
				
				
			}
			
			return tmp;
		}
		// Division
		if(op=="/") {
			
			for(int q = 1;q<=n;q++) {
				
				
			// loop thru and grab number pairs that have a mod of zero of total
				
				
			}
			
			return tmp;
			
		}
		// Equals
		if(op=="=") {
			
			tmp.add(x);
			
		}
		return tmp;
	}
	
	
}
