package kenken;

import java.awt.geom.Point2D;
import java.io.File;

import java.util.Scanner;

import java.io.FileNotFoundException;

import java.util.Hashtable;

public class InputFile {
	
	File text;
	int n;
	String [][] boardNumbers;
	Point2D e;
	
	
	
	public InputFile(String file) throws FileNotFoundException {
		
		
		text = new File("datainput.txt");
		
		
		Scanner scnr = new Scanner(text);
		
		int lineNumber = 0;
		int i = 0;
		int j = 0;

		int lineLength;
		
		
		Hashtable<String,Cage> cages = new Hashtable<String,Cage>();
		
		Cage eachCage = new Cage();
		
		
		while (scnr.hasNextLine()) {
			
			String line = scnr.nextLine();
			
			if(lineNumber == 0) n = Integer.parseInt(line); makeArray(n);
			
			if(lineNumber >0 && lineNumber < n) {
				
					for(;j<=n;j++) {
						
						boardNumbers[i][j] = line.substring(j, j+1);
						
						//if [i][j] is in Hashtable, skip, if not add it as a key and null value
						
						if(!cages.containsKey(boardNumbers[i][j])){
							
							eachCage.setPoint(i, j);
							
							cages.put(boardNumbers[i][j], eachCage);
							
						} else {
							
							eachCage = cages.get(boardNumbers[i][j]);
							
							e.setLocation(i,j);
							
							eachCage.locales.add(e);
							
							cages.replace(boardNumbers[i][j], eachCage);
							
						}
						
					}
				
					i =+ 1;
					
				}
			
			if( lineNumber >= n) {
			
				lineLength = line.length();
				
				eachCage.setOp(line.substring(lineLength-1));
				
				eachCage.setTotal(Integer.parseInt(line.substring(3,lineLength-2)));
				
				cages.putIfAbsent(line.substring(0,1), eachCage);
				
				
					
			}
			
			lineNumber =+ 1;
			
			
			}
			
			
		}
	
	
	public String[][] makeArray(int n){
		
		boardNumbers = new String[n][n];
		
		return boardNumbers;
	}
	
	
	 

}
