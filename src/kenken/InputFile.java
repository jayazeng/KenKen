/* This class creates the following: 
 * -> One hashtable that holds key:Letter (A,B,C,D,etc) & cage objects that contain Total (ie 120,6,240), 
 *    Operator (*,x,/,+,-) and grouping points [(0,0),(0,1),(0,2)] 
 *    {This hashtable is used to associate the cages with all the points to the Total and operator}
 * 
 * -> One hashMap that holds Point (i,j) as the key & value:Letter for reference.  We used a hashMap because it accepts NULL values. 
 *    {used to pull the corresponding letter when you only know the Point} 
 * 
 * -> An individual cage or group of points is called eachCage.  It's an object created in the cage class.
 *    It consist of a Numerical Total (total), opeartor (op), and an ArrayList of Point2D Coordinates (locales)
 * 
 * -> Also need to create a count of how many cage letters were present on the inputed board.  Anotherwords
 *    how many A or B or C points are respectively needed to complete the cage.
 * 
 */

package kenken;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import java.util.HashMap;
import java.util.Hashtable;

public class InputFile {
	
	File text;
	
	Scanner scnr;
	
	int n; // the size of the rows, columns and domain
	
	String [][] boardNumbers;  // this creates the matrix n x n
	
	Point2D e;  // the (i,j) coordinate
	
	Hashtable<String,Cage> cages;
	
	HashMap<Point2D,String> cageLookup;
	
	Cage eachCage;
	
	// constructor that reads the file and creates the board and fills in the object
	public InputFile(String file) {
		
		
		try {
			
			ReadDataFile dFile = new ReadDataFile(file);
			String[] textLines = dFile.OpenFile();

			e = new Point2D.Double();
			int lineNumber = 0;  // starting line number
			
			int h = 0;
			
			int i = -1;  // starting row number
			
			int j = 0;  // starting column number
	
			int lineLength;  // measures the length of row for constraint input
			
			cages = new Hashtable<String,Cage>();  //hashtable which captures the letter in position one and an object in the second position  
			
			cageLookup = new HashMap<Point2D,String>();  // Need a Cage by point lookup table
			
			eachCage = new Cage();  // This is one group of cells that have a common constraint
	
		
			do { // using do since it has to run atleast one time
				
				String line = textLines[h];  //looks for i line
				j=0;
				if(lineNumber == 0) { //reads the first value and sets to N & creates Array
					n = Integer.parseInt(line); 
					makeArray(n); 
				} else if(lineNumber <= n) { //must be between line 1 and line n to be the board
					
						for(;j<n;j++) { //loop through the cols for each row
							
							boardNumbers[i][j] = line.substring(j, j+1);  // grabs the individual cell and assigns it 
								
							e.setLocation(i, j);  // create point - might be redundant but using for now
							
							cageLookup.put(e,boardNumbers[i][j]); //put the point (i,j) as key and cage Name (ie A, B, C) as value
								
							if(!cages.containsKey(boardNumbers[i][j])){  // if [i][j] is not in hashtable then set point to particular cage
							
								eachCage.setPoint(i, j);	// set (i,j) into the obect's locale
								
								cages.put(boardNumbers[i][j], eachCage);  // put (i,j)'s value=key (ie A, B,C) & put eachCage as value into hashtable
								
							} else {
								
								eachCage = cages.get(boardNumbers[i][j]);  // grabs individual cage by key value in boardNumbers[i][j]
								
								e.setLocation(i,j); // sets variable equal to the point (i,j)
								
								eachCage.locales.add(e);  // adds new point (i,j) into the locale ArrayList of existing points
								
								cages.replace(boardNumbers[i][j], eachCage); // replaces old cage with new cage using key (A,B,C)
								
							}
							
						}
					
						
						
					}
				
				else if( lineNumber > n) {  // when line n+1 is reached, the data changes
				
					lineLength = line.length(); // need to check the line length to know where to find the operator
					
					eachCage.setOp(line.substring(lineLength-1)); // finds the operator
					//String what = line.substring(2,lineLength-1);
					eachCage.setTotal(Integer.parseInt(line.substring(2,lineLength-1))); // finds the total value
					
					cages.putIfAbsent(line.substring(0,1), eachCage); // inserts eachCage into hashtable(cages) if its not there
					
					
						
				}
				
				h++;
				i++;  // sets i for the next row
				lineNumber++;  // increases the line number by 1 
				
				
				} while(h < textLines.length); // ends when scnr has no more lines left
		
			System.out.print("Stop Here");
			
			System.out.print("Done");
		}
		
		catch (IOException e) {
			
			System.out.println(e.getMessage());
			
		}			
			
		}  // end of constructor
	
	
	
	private String[][] makeArray(int n){ //makes the n ArrayList by initializing ArrayList with n
		
		boardNumbers = new String[n][n];  // this will be characters like A,B,C,$,&
		
		return boardNumbers;
	}
	
	
	 

}
