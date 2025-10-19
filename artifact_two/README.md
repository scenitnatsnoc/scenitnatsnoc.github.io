# Algorithms and data structures

### Description

In one of the previous assignments at Study.com during the Computer Science 201 - Data Structures & Algorithms course, we were tasked with developing a Java application that sorts an unsorted list of States and Capitals in natural order (alphabetical), either by State or by Capital. Additionally, we needed to create a HashMap (to demonstrate that it does not guarantee the order) and a TreeMap (to show that it sorts in natural order by default) of States and capitals. Finally, a Trivia Game to test knowledge of states and their capitals needed to be implemented, along with a single question/answer module to determine the capital of a particular state.

[Original Code on GitHub](https://github.com/scenitnatsnoc/scenitnatsnoc.github.io/tree/main/artifact_two/original_code)

### Justification

I selected this artifact to demonstrate my knowledge and understanding of data structures and algorithms, as well as my ability to develop creative solutions for various problems in the computer science field. From arrays, hash maps, and treemaps to the bubble sort algorithm and its optimizations, my artifact encompasses a wide range of topics in algorithms and data structures, utilizing Java as the primary programming language. The enhancements I implemented include optimizing the bubble sort functions bubbleSortCapsFunc(myArray) and bubbleSortStatesFunc(myArray) to sort a 2D array of capitals and their corresponding states, either by state or by capital, preventing the code from continuing to iterate through the array even after it was already sorted with the help of a boolean isSorted variable:
```java
...
// function to bubble sort by the states
    // returns the amount of iterations through all
    // existing loops
    public static long bubbleSortStatesFuncOptimized(String myArray[][]) {
    	// define our counters to count the amount of iterations in each of 
    	// our three loops
    	int counter1 = 0;
    	int counter2 = 0;
    	int counter3 = 0;
    	boolean isSorted = true;
    	for (int j = 0; j <  myArray.length - 1; j++ ) {
    		isSorted = true;
    		counter1++;
    		for (int i = 0; i < myArray.length - 1 - j; i++ ) {
    			counter2++;
    			// sorting by first letter
    			if ((int)myArray[i][0].toCharArray()[0] > (int)myArray[i + 1][0].toCharArray()[0]) {
    				String cap = myArray[i][1];
    				String state = myArray[i][0];
    				myArray[i][1] = myArray[i + 1][1];
    				myArray[i][0] = myArray[i + 1][0];
    				myArray[i + 1][1] = cap;
    				myArray[i + 1][0] = state;
    				isSorted = false;
    			}
    			
    			// continue sorting by checking next letter 
    			// of states start with the same letter
    			if (((int)myArray[i][0].toCharArray()[0] == (int)myArray[i + 1][0].toCharArray()[0])) {
    				
    				for (int k = 1; k < Math.min((int)myArray[i][0].toCharArray().length, (int)myArray[i + 1][0].toCharArray().length); k++ ) {
    					counter3++;
    					if ((int)myArray[i][0].toCharArray()[k] > (int)myArray[i + 1][0].toCharArray()[k]) {
							String cap = myArray[i][1];
		    				String state = myArray[i][0];
		    				myArray[i][1] = myArray[i + 1][1];
		    				myArray[i][0] = myArray[i + 1][0];
		    				myArray[i + 1][1] = cap;
		    				myArray[i + 1][0] = state;
		    				isSorted = false;
		    				break;		
    					} else if ((int)myArray[i][0].toCharArray()[k] == (int)myArray[i + 1][0].toCharArray()[k]) {
    						continue;
    					} else {
    						break;
    					}
    					
    				}
    				    
    			}
    		}
    		if(isSorted == true) {
    			break;
    		}
    	
    	}
    	// System.out.println("Optimized final SUM of all iterations: " + counter1 + " * " + counter2 + " * " + counter3 + " = " + counter1*counter2*counter3);
		return counter1*counter2*counter3;
    }
...
```
The optimized versions are, on average, 20% more efficient because they require around 20% fewer iterations. This was demonstrated with the newly developed method getAverageOptimization(iterations), which creates two identical arrays, sorts both the optimized and non-optimized versions using a bubble sort, given the number of iterations, and calculates the average improvement as a percentage:
```java
...
// get average optimization in percentage
    public static double getAverageOptimization(int iterations) {
		double sum = 0;
		double finalResult;
		for (int j = 0; j < iterations; j++ ) {
			double currResult;
			String unsortedArrayOfStatesAndCaps[][] = getUnsortedArrayOfStatesAndCaps();
			String unsortedArrayOfStatesAndCapsClone[][] = getCloneOf2D(unsortedArrayOfStatesAndCaps);
			long opt = bubbleSortStatesFuncOptimized(unsortedArrayOfStatesAndCaps);
			if (!compareArrays(unsortedArrayOfStatesAndCaps, "ByState")) {
				System.out.println("Sorting Not Good Enogh");
				return 0;
			}
			long nonopt = bubbleSortStatesFunc(unsortedArrayOfStatesAndCapsClone);
			compareArrays(unsortedArrayOfStatesAndCapsClone, "ByState");
			currResult = 100 - ((double)opt/nonopt) * 100;
			sum = sum + currResult;
			System.out.printf("Optimization is: %.2f%%%n", currResult);
		}
		finalResult = (100 - (sum/iterations));
		System.out.printf("Average optimization is: %.2f%%%n", 100 - finalResult);
		return sum;
    }
...
```
Another improvement was to add save-score functionality for the Trivia Game of States and Capitals, where I created an algorithm to save the user’s name and scores to a file and display the scoreboard, sorted from highest to lowest, using the bubble sort algorithm:
```java
...
// method to add scores to the score board
    public static void addScore(double myScore, String myUsername) {
        boolean notFound = true;
        // counter to fill up our array of scores
        int i = 0;
    	try {
    		BufferedReader reader = new BufferedReader(new FileReader(scoreBoardFile));
            String line = reader.readLine();
            String[][] myScoreArray;
			if (line == null) {
            	line = "0: ";
            }
			int arrSize = Integer.parseInt(line.split(": ")[0]);
			// defining the size of the array in case we need to 
			// add another element
            myScoreArray = new String[arrSize + 1][2];
            while ((line = reader.readLine()) != null) {
                // Process each line here
        		String[] currLine = line.split(": ");
                if (currLine[0].equals(myUsername)) {
                	if (Integer.parseInt(currLine[1]) < (int)myScore) {
                    	// updating the users score as it is higher
                    	currLine[1] = Integer.toString((int)myScore); 
                	}
                	// to ensure the users score is there
                	// if not we need to add it later
                	notFound = false;
                }
                //System.out.println(line);
            	myScoreArray[i][0] = currLine[1];
            	myScoreArray[i][1] = currLine[0];
            	i++;
        	} 
            // users name is not on the score board, let's add it
            if (notFound) {
            	myScoreArray[i][0] = Integer.toString((int)myScore);
            	myScoreArray[i][1] = myUsername;
            } else {
            	// removing last element which is null,null
            	myScoreArray = Arrays.copyOf(myScoreArray, myScoreArray.length - 1);
            }
    		// create FileWriter Object
    		FileWriter fWriter = new FileWriter(scoreBoardFile);
    		
    		// Writing into file
    		String whatToWrite = myScoreArray.length + ": \n";
    		for (int k = 0; k < myScoreArray.length; k++) {
    			whatToWrite = whatToWrite + myScoreArray[k][1] + ": " + myScoreArray[k][0] + "\n";
    		}
            fWriter.write(whatToWrite);
            // Printing the contents of a file for debugging purposes
            // System.out.println(whatToWrite);

            // Closing the file writing connection
            fWriter.close();
    	}
    	
    	// Catch block to handle any exceptions
    	catch (IOException e) {
    		// Print the exception
    		System.out.println(e.getMessage());
    	}
    	
    }
    
    // method to show our score board
    public static void showScoreBoard() {
    	System.out.println("<---------------------------------------------------------------------------->");
		System.out.println("	Our Score Board for Trivia Game!");
		System.out.println("<---------------------------------------------------------------------------->");
    	// creating our file object
    	File myFile = new File(scoreBoardFile);
    	try {
    		// checking if file exists
    		// if it is not fill with random scores
    		if (myFile.createNewFile()) {
    			addScore(34, "Alex");
    			addScore(45, "Niko");
    			addScore(84, "Ale");
    			addScore(20, "Kostya");
        	} 
    		BufferedReader reader = new BufferedReader(new FileReader(scoreBoardFile));
            String line = reader.readLine();
            String[][] myScoreArray;
            if (line == null) {
            	line = "0: ";
            }
            myScoreArray = new String[Integer.parseInt(line.split(": ")[0])][2];
            
            int i = 0;
            while ((line = reader.readLine()) != null) {
            	
                // Process each line here
            	String[] currLine = line.split(": ");
            	if (currLine.length != 1) {
	                //System.out.println(line);
	            	myScoreArray[i][0] = currLine[1];
	            	myScoreArray[i][1] = currLine[0];
	            	i++;
            	}
            }
            bubbleSortScoresFuncOptimized(myScoreArray);
            printArray(myScoreArray);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    	System.out.println("<---------------------------------------------------------------------------->");
    	}
    
...
```
Finally, a main menu was added for users to pick one of the following options: print unsorted array of states and capitals, sort by capitals by using the optimized bubble sort method, sort by states by using the optimized bubble sort method, play Trivia Game,  a single Q/A Game to practice knowledge of states and their capitals, print optimization in percentage based on interations, print our Score Board for Trivia Game, and quit application:
```java
...
// This method prints the menu options
    public static void displayMenu() {
        System.out.println("\n\n");
        System.out.println("\t\t\t\tBubble sort and trivia main menu");
        System.out.println("[1] Print Unsorted Array of States and Capitals");
        System.out.println("[2] Sort by Capitals by using the optimized bubble sort method");
        System.out.println("[3] Sort by States by using the optimized bubble sort method");
        System.out.println("[4] Play Trivia");
        System.out.println("[5] Single Q/A Game to practice knowledge of States and their capitals");
        System.out.println("[6] Print optimization in percentage based on interations");
        System.out.println("[7] Print our Score Board for Trivia Game");
        System.out.println("[q] Quit application");
        System.out.println();
        System.out.println("Enter a menu selection");
    }
...
```

By implementing my enhancements, I achieved both of my planned outcomes, which demonstrates my ability to design and evaluate computing solutions that solve a given problem using algorithmic principles and computer science practices and standards appropriate to its solution, while managing the trade-offs involved in design choices, as well as demonstrate an ability to use well-founded and innovative techniques, skills, and tools in computing practices for the purpose of implementing computer solutions that deliver value and accomplish industry-specific goals.

[Enhanced Code on GitHub](https://github.com/scenitnatsnoc/scenitnatsnoc.github.io/tree/main/artifact_two/enhanced_code)

### Reflection

I found myself stuck a few times while going through my enhancement process. The first challenge was related to arrays, as I had to refresh my knowledge on how Java initializes them, their available methods, and how to clone them properly. I had to create an additional method, getCloneOf2D(myArray), that returns a manually cloned 2D array, since the included methods clone() and copyOfArray() create only shallow copies, which essentially create a reference to the original. The second one was creating an algorithm that saves users’ names and scores in a file. I had to add a first line to the file that includes the number of users in the scoreboard and update it every time a user is added, so I can create an array of that dimension to perform the bubble sort algorithm and display the results from highest to lowest in the user's console using the showScoreBoard() method. I learned several valuable techniques along the way that helped me become a better software engineer.



