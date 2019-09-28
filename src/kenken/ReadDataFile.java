/*  This starts the whole kenKen process (main file).  This is the ReadFile class which uses FileReader to read in the KenKen puzzle.
 *  We pass this information into the InputFile which inturn builds the KenKen puzzle.
 *
 * 
 * 
 * 
 */

package kenken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadDataFile {

	private String dataPath;

	public ReadDataFile(String dataFilePath) {

		dataPath = dataFilePath;
	}

	public String[] OpenFile() throws IOException {

		FileReader fr = new FileReader(dataPath);

		BufferedReader textReader = new BufferedReader(fr);

		int numOfLines = readLines();

		String[] textData = new String[numOfLines];

		int i;

		for (i = 0; i < numOfLines; i++) {

			textData[i] = textReader.readLine();
		}

		textReader.close();

		return textData;

	}

	int readLines() throws IOException {

		FileReader file_to_read = new FileReader(dataPath);

		BufferedReader buffFile = new BufferedReader(file_to_read);

		String dataFileLine;

		int numOfLines = 0;

		while ((dataFileLine = buffFile.readLine()) != null) {

			numOfLines++;

		}

		buffFile.close();

		return numOfLines;

	}
}
