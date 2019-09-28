# KenKen
Project for COMP 560: Artificial Intelligence

Task: Implement algorithims (backtracking and local search) to solve KenKen puzzles using Java

Additional Notes: 

Input for puzzle will give the size of the puzzle, the partitioning of the puzzle in row major order   using letters and the equations associated with each partitions. So the input for a puzzle will look like:
        6
        ABBCDD
        AEECFD
        GGHHFD
        GGIJKK
        LLIJJM
        NNNOOM
        A:11+
        B:2/
        C:20*
        D:6*
        E:3-
        F:3+
        G:240*
        H:6*
        I:6*
        J:7+
        K:30*
        L:6*
        M:9+
        N:8+
        O:2/ 

Output for puzzle should be the solutions and the number of nodes/iterations for the each of the solutions.

How to run code: 
Please run the code using the main method in submission. Change the data_file string to the path of the input file of the puzzle. There should already be an InputFile called game which will take the information from the file and convert it for our code's purpose. The three algorithms should also be created with the separate classes: SimpleBackTrack, AdvancedBackTrack and LocalSearch. Each class has three methods that should be used. trySearch() will test the alorithm, printSolution() will print the solution the algorithm comes to, and printNumNodes() will print the number of nodes/iterations the algorithm uses to get to final solution or fail (in the case of local search)
