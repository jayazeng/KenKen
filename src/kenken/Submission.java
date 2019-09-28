package kenken;

import java.io.IOException;

public class Submission {

	public static void main(String[] args) throws IOException {

		// Please input name of input file
		String data_file = "./src/kenken9x9.txt";

		InputFile game = new InputFile(data_file);

		SimpleBackTrack simple = new SimpleBackTrack(game);
		AdvancedBackTrack adv = new AdvancedBackTrack(game);
		LocalSearch local = new LocalSearch(game);
		
		simple.trySearch();
		adv.trySearch();		
		local.trySearch();
		
		simple.printSolution();
		System.out.println();
		simple.printNumNodes();
		adv.printNumNodes();
		local.printNumNodes();
		

	}

}
