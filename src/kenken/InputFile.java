package kenken;

import java.io.File;

import java.util.Scanner;

import java.io.FileNotFoundException;

import java.util.Hashtable;

public class InputFile {
	
	File text;
	int n;
	String [][] boardNumbers;
	
	
	public InputFile(String file) throws FileNotFoundException {
		
		
		text = new File("datainput.txt");
		
		
		Scanner scnr = new Scanner(text);
		
		int lineNumber = 0;
		int i = 0;
		int j = 0;
		int beginIndex;
		int endIndex;
		int lineLength;
		
		String cageLetter;
		int cageTotal;
		String cageOp;
		
		Hashtable<String,Cage> cages = new Hashtable<String,Cage>();
		
		Cage eachCage = new Cage();
		
		
		while (scnr.hasNextLine()) {
			
			String line = scnr.nextLine();
			
			if(lineNumber == 0) n = Integer.parseInt(line); makeArray(n);
			if(lineNumber >0 && lineNumber < n) {
				
					for(;j<=n;j++) {
						
						boardNumbers[i][j] = line.substring(j, j+1);
						
					}
				
					i =+ 1;
					
				}
			
			if( lineNumber >= n) {
			
				lineLength = line.length();
				
				eachCage.setOp(line.substring(lineLength-1));
				
				eachCage.setTotal(Integer.parseInt(line.substring(3,lineLength-2)));
				
				cages.put(line.substring(0,1), eachCage);
					
			}
			
			lineNumber =+ 1;
			
			
			}
			
			
		}
	
	
	public String[][] makeArray(int n){
		
		boardNumbers = new String[n][n];
		
		return boardNumbers;
	}
	
	
	 

}
