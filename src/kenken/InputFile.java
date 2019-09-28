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
 *  REMEMBER DIVISION AND MINUS CAN ONLY BY TWO SQUARES
 * 
 *  NEED TO CHANGE THE INPUT FUNCTION TO ACCEPT THE COLON INSTEAD OF THE SPOTS
 * 
 * 
 */

package kenken;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public class InputFile {

	File text;

	int n; // the size of the rows, columns and domain

	String[][] boardNumbers; // this creates the matrix n x n

	String e; // the (i,j) coordinate

	Hashtable<String, Cage> cages;

	Hashtable<String, String> cageLookup;

	Cage eachCage;

	// constructor that reads the file and creates the board and fills in the object
	public InputFile(String file) {

		try {

			ReadDataFile dFile = new ReadDataFile(file);

			String[] textLines = dFile.OpenFile();

			int lineNumber = 0; // starting line number

			int i = 1; // starting row number

			int j = 1; // starting column number

			int lineLength; // measures the length of row for constraint input

			cages = new Hashtable<String, Cage>(); // hashtable which captures the letter in position one and an object
													// in the second position

			cageLookup = new Hashtable<String, String>(); // Need a Cage by point lookup table

			do { // using do since it has to run atleast one time

				String line = textLines[i - 1].trim(); // looks for i line

				j = 1;

				if (lineNumber == 0) { // reads the first value and sets to N & creates Array

					n = Integer.parseInt(line);

					makeArray(n);

				} else if (lineNumber <= n) { // must be between line 1 and line n to be the board

					for (; j <= n; j++) { // loop through the cols for each row

						e = ("(" + (i - 2) + "," + (j - 1) + ")"); // create point - might be redundant but using for
																	// now

						this.eachCage = new Cage(); // This is one group of cells that have a common constraint

						boardNumbers[i - 2][j - 1] = line.substring(j - 1, j); // grabs the individual cell and assigns
																				// it

						this.cageLookup.put(("(" + (i - 1) + "," + (j) + ")"), boardNumbers[i - 2][j - 1]); // put the
																											// point
																											// (i,j) as
																											// key and
																											// cage Name
																											// (ie A, B,
																											// C) as
																											// value

						if (!cages.containsKey(boardNumbers[i - 2][j - 1])) { // if [i][j] is not in hashtable then set
																				// point to particular cage

							this.eachCage.locales.add("(" + (i - 1) + "," + (j) + ")"); // set (i,j) into the obect's
																						// locale

							this.cages.put(boardNumbers[i - 2][j - 1], this.eachCage); // put (i,j)'s value=key (ie A,
																						// B,C) & put eachCage as value
																						// into hashtable

						} else {

							this.eachCage = this.cages.get(boardNumbers[i - 2][j - 1]); // grabs individual cage by key
																						// value in boardNumbers[i][j]

							this.eachCage.locales.add("(" + (i - 1) + "," + (j) + ")"); // adds new point (i,j) into the
																						// locale ArrayList of existing
																						// points

							this.cages.replace(boardNumbers[i - 2][j - 1], this.eachCage); // replaces old cage with new
																							// cage using key (A,B,C)

						}

					}

				}

				if (lineNumber > n) { // when line n+1 is reached, the data changes

					setTotalOps(line);

				}

				i++; // sets i for the next row

				lineNumber++; // increases the line number by 1

			} while (i <= textLines.length); // ends when scnr has no more lines left

		}

		catch (IOException e) {

			System.out.println(e.getMessage());

		}
		System.out.println("File Imported");
	} // end of constructor

	private String[][] makeArray(int n) { // makes the n ArrayList by initializing ArrayList with n

		boardNumbers = new String[n][n]; // this will be characters like A,B,C,$,&

		return boardNumbers;
	}

	private void setTotalOps(String line) {

		int lineLength = line.length(); // need to check the line length to know where to find the operator

		String op = parseOp(lineLength, line, "op"); // line.substring(lineLength-1) finds the operator

		int total = Integer.parseInt(parseOp(lineLength, line, "total")); // finds the total value

		cages.get(line.substring(0, 1)).op = op; // inserts eachCage into hashtable(cages) if its not there

		cages.get(line.substring(0, 1)).total = total;

	}

	private String parseOp(int lineLength, String line, String type) {

		if (type == "op" && !Character.isDigit(line.charAt(lineLength - 1))) {

			return line.substring(lineLength - 1);
		} else if (type == "op" && Character.isDigit(line.charAt(lineLength - 1))) {

			return "=";
		}

		if (type == "total" && !Character.isDigit(line.charAt(lineLength - 1))) {

			return line.substring(2, lineLength - 1);
		}

		return line.substring(2);
	}

}
