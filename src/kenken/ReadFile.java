/*  This starts the whole kenKen process (main file).  This is the ReadFile class which uses FileReader to read in the KenKen puzzle.
 *  We pass this information into the InputFile which inturn builds the KenKen puzzle.
 *
 * 
 * 
 * 
 */

package kenken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ReadFile {

	private String path;
	
	public ReadFile(String file_path) {
		path=file_path;
	}
		
	public String[] OpenFile() throws IOException{
		
		FileReader fr = new FileReader(path);
		
		BufferedReader textReader = new BufferedReader(fr);
		
		int numberOfLines = readLines();
		String[] textData = new String[numberOfLines];
		
		int i;
		
		for(i=0; i<numberOfLines; i++) {
			textData[i] = textReader.readLine();
		}
		
		
		textReader.close();
		return textData;
		
	}
		
	int readLines() throws IOException{
		
		FileReader file_to_read = new FileReader(path);
		BufferedReader bf = new BufferedReader(file_to_read);
		
		String aLine;
		int numberOfLines = 0;
		
		while ((aLine = bf.readLine()) != null) {
			
			numberOfLines++;
			
		}
		
		bf.close();
		
		return numberOfLines;
	}




	public static void main(String[] args) throws IOException {
		
		String file_name = "./src/kenken.txt";
		
		System.out.print("Hello Cedric");
		
		
		try {
			
			ReadFile file = new ReadFile(file_name);
			String[] aryLines = file.OpenFile();
			
			int i;
			for(i=0; i < aryLines.length; i++) {
				
				System.out.print(aryLines[i]);
				
			}
			
		}
		catch (IOException e) {
			
			System.out.println(e.getMessage());
			
		}
	}
	
}
