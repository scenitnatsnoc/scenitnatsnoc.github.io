/*
CS499 Example code for code review
We were tasked with developing an application in Java that 
sorts an unsorted list of States and Capitals in natural 
order (alphabetical), either by State or by Capital. 
Additionally, we needed to create a HashMap (to demonstrate that it does not guarantee order) 
and a TreeMap (to show that it sorts in natural order by default) of States and capitals. 
A Trivia Game to test knowledge of states and their capitals also needed to be implemented.
Programmer: Konstantin Dobikov
Last Date updated: 01/10/2024
Artifact 2 Category 2 in Algorithms and Data Structure
No known errors
Enhancement Plan:
1. Optimize bubbleSortStatesFunc and bubbleSortCapsFunc functions so they stop iterating the array if it is already sorted.
2. Adding the menu for the user to pick one of the options:
     1. Play a trivia game with the ability to keep the highest score and the player's name in a separate file.
     2. Print a sorted list of States and their Capitals, either by State or by Capitals.
     3. Play Q/A game to practice the knowledge of the States and their Capitals
     4. Ability to exit 

*/

package BubbleSortAndTrivia;

import java.util.TreeMap;

// to get random number
import java.util.Random;

import java.util.Scanner;
import java.util.Map;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

//Write into a File
//using FileWriterClass
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;

// to read
import java.io.FileReader;

public class BubbleSortAndTrivia {
    //pattern for digits only
    private static final String DIGIT_PATTERN = "-?\\d+?";
    
    // scoreboard file
    private static final String scoreBoardFile = ".\\ScoreBoard.txt";
    
	// unsorted array
    public static String[][] Cap_States  = {
    	{"Alabama", "Montgomery"},
    	{"Alaska", "Juneau"},
    	{"Arizona", "Phoenix"},
    	{"Wisconsin","Madison"},
    	{"Arkansas","Little Rock"},
    	{"California","Sacramento"},
    	{"West Virginia","Charleston"},
    	{"Colorado","Denver"},
    	{"Connecticut","Hartford"},
    	{"Delaware","Dover"},
    	{"Hawaii","Honolulu"},
    	{"Florida","Tallahassee"},
    	{"Georgia","Atlanta"},
    	{"Idaho","Boise"},
    	{"Virginia","Richmond"},
    	{"Illinois","Springfield"},
    	{"Washington","Olympia"},
    	{"Indiana","Indianapolis"},
    	{"Iowa","Des Moines"},
    	{"Kansas","Topeka"},
    	{"Kentucky","Frankfort"},
    	{"Louisiana","Baton Rouge"},
    	{"Maine","Augusta"},
    	{"South Dakota","Pierre"},
    	{"Pennsylvania","Harrisburg"},
    	{"Maryland","Annapolis"},
    	{"Vermont","Montpelier"},
    	{"Massachusetts","Boston"},
    	{"Michigan","Lansing"},
    	{"Minnesota", "St. Paul"},
    	{"Mississippi","Jackson"},
    	{"Missouri","Jefferson City"},
    	{"Montana","Helena"},
    	{"Nebraska","Lincoln"},
    	{"Nevada","Carson City"},
    	{"New Hampshire","Concord"},
    	{"New Jersey","Trenton"},
    	{"New Mexico","Santa Fe"},
    	{"North Carolina","Raleigh"},
    	{"North Dakota","Bismarck"},
    	{"New York","Albany"},
    	{"Ohio","Columbus"},
    	{"Oklahoma","Oklahoma City"},
    	{"Oregon","Salem"},
    	{"Rhode Island","Providence"},
    	{"South Carolina","Columbia"},
    	{"Tennessee","Nashville"},
    	{"Texas","Austin"},
    	{"Utah","Salt Lake City"},
    	{"Wyoming","Cheyenne"}
    };
    
    // nice sorted array of US sates and the capitals
    public static String[][] Cap_States_Good_Sorted  = {
        	{"Alabama", "Montgomery"},
        	{"Alaska", "Juneau"},
        	{"Arizona", "Phoenix"},
        	{"Arkansas","Little Rock"},
        	{"California","Sacramento"},
        	{"Colorado","Denver"},
        	{"Connecticut","Hartford"},
        	{"Delaware","Dover"},
        	{"Florida","Tallahassee"},
        	{"Georgia","Atlanta"},
        	{"Hawaii","Honolulu"},
        	{"Idaho","Boise"},
        	{"Illinois","Springfield"},
        	{"Indiana","Indianapolis"},
        	{"Iowa","Des Moines"},
        	{"Kansas","Topeka"},
        	{"Kentucky","Frankfort"},
        	{"Louisiana","Baton Rouge"},
        	{"Maine","Augusta"},
        	{"Maryland","Annapolis"},
        	{"Massachusetts","Boston"},
        	{"Michigan","Lansing"},
        	{"Minnesota", "St. Paul"},
        	{"Mississippi","Jackson"},
        	{"Missouri","Jefferson City"},
        	{"Montana","Helena"},
        	{"Nebraska","Lincoln"},
        	{"Nevada","Carson City"},
        	{"New Hampshire","Concord"},
        	{"New Jersey","Trenton"},
        	{"New Mexico","Santa Fe"},
        	{"New York","Albany"},
        	{"North Carolina","Raleigh"},
        	{"North Dakota","Bismarck"},
        	{"Ohio","Columbus"},
        	{"Oklahoma","Oklahoma City"},
        	{"Oregon","Salem"},
        	{"Pennsylvania","Harrisburg"},
        	{"Rhode Island","Providence"},
        	{"South Carolina","Columbia"},
        	{"South Dakota","Pierre"},
        	{"Tennessee","Nashville"},
        	{"Texas","Austin"},
        	{"Utah","Salt Lake City"},
        	{"Vermont","Montpelier"},
        	{"Virginia","Richmond"},
        	{"Washington","Olympia"},
        	{"West Virginia","Charleston"},
        	{"Wisconsin","Madison"},
        	{"Wyoming","Cheyenne"}
        };
    // method to check to get the right answer
    public static String checkAnswer(Scanner scanner) {
    	String answer;
    	boolean rightAnswer = true;
    	answer = scanner.nextLine();
    	do {
    		if(answer.matches(DIGIT_PATTERN)){
    			rightAnswer = false;
    		} else {
    			System.out.print("Not allowed, please use numbers only: \n");
    			answer = scanner.nextLine();
         }
    	} while(rightAnswer);
    	return answer;
    }
    // function to bubble sort by the capitals
    // returns the amount of iterations through all
    // existing loops
    public static long bubbleSortCapsFunc(String myArray[][]) {
    	// define our counters to count the amount of iterations in each of 
    	// our three loops
    	int counter1 = 0;
    	int counter2 = 0;
    	int counter3 = 0;
    	for (int j = 0; j < myArray.length - 1; j++ ) {
    		counter1++;
    		for (int i = 0; i < myArray.length - 1 - j; i++ ) {
    			// sorting by first letter
    			counter2++;
    			if ((int)myArray[i][1].toCharArray()[0] > (int)myArray[i + 1][1].toCharArray()[0]) {
    				String cap = myArray[i][1];
    				String state = myArray[i][0];
    				myArray[i][1] = myArray[i + 1][1];
    				myArray[i][0] = myArray[i + 1][0];
    				myArray[i + 1][1] = cap;
    				myArray[i + 1][0] = state;
    			}
    			
    			// continue sorting by checking next letter 
    			// of capitals start with the same letter
    			if (((int)myArray[i][1].toCharArray()[0] 
    					== (int)myArray[i + 1][1].toCharArray()[0])) {
	    				for (int k = 1; 
	    						k < Math.min((int)myArray[i][1].toCharArray().length, 
	    								(int)myArray[i + 1][1].toCharArray().length); 
	    						k++ ) {
	    					counter3++;
	    					if ((int)myArray[i][1].toCharArray()[k] > (int)myArray[i + 1][1].toCharArray()[k]) {
								String cap = myArray[i][1];
			    				String state = myArray[i][0];
			    				myArray[i][1] = myArray[i + 1][1];
			    				myArray[i][0] = myArray[i + 1][0];
			    				myArray[i + 1][1] = cap;
			    				myArray[i + 1][0] = state;
			    				break;		
	    					} else if ((int)myArray[i][1].toCharArray()[k] == (int)myArray[i + 1][1].toCharArray()[k]) {
	    						continue;
	    					} else {
	    						break;
	    					}
	    				}
    				    
    				}
    		}
    	}
    	// System.out.println("Final SUM of all iterations: " + counter1 + " * " + counter2 + " * " + counter3 + " = " + counter1*counter2*counter3);
		return counter1*counter2*counter3;
    }
    // function to bubble sort by the states
    // returns the amount of iterations through all
    // existing loops
    public static long bubbleSortStatesFunc(String myArray[][]) {
    	// define our counters to count the amount of iterations in each of 
    	// our three loops
    	int counter1 = 0;
    	int counter2 = 0;
    	int counter3 = 0;
    	for (int j = 0; j <  myArray.length - 1; j++ ) {
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
    			}
    			
    			// continue sorting by checking next letter 
    			// of states start with the same letter
    			if (((int)myArray[i][0].toCharArray()[0] 
    					== (int)myArray[i + 1][0].toCharArray()[0])) {
	    				for (int k = 1; 
	    						k < Math.min((int)myArray[i][0].toCharArray().length, 
	    								(int)myArray[i + 1][0].toCharArray().length); 
	    						k++ ) {
	    					counter3++;
	    					if ((int)myArray[i][0].toCharArray()[k] > (int)myArray[i + 1][0].toCharArray()[k]) {
								String cap = myArray[i][1];
			    				String state = myArray[i][0];
			    				myArray[i][1] = myArray[i + 1][1];
			    				myArray[i][0] = myArray[i + 1][0];
			    				myArray[i + 1][1] = cap;
			    				myArray[i + 1][0] = state;
			    				break;		
	    					} else if ((int)myArray[i][0].toCharArray()[k] == (int)myArray[i + 1][0].toCharArray()[k]) {
	    						continue;
	    					} else {
	    						break;
	    					}
	    					
	    				}
    				    
    				}
    		}
    	}
    	// System.out.println("Final SUM of all iterations: " + counter1 + " * " + counter2 + " * " + counter3 + " = " + counter1*counter2*counter3);
		return counter1*counter2*counter3;
    }
    
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
    
    // function to bubble sort our score board
    public static void bubbleSortScoresFuncOptimized(String myArray[][]) {
    	boolean isSorted = true;
    	for (int j = 0; j <  myArray.length - 1; j++ ) {
    		isSorted = true;
    		for (int i = 0; i < myArray.length - 1 - j; i++ ) {
    			// sorting by first first element which is an integer
    			if (Integer.parseInt(myArray[i][0]) < Integer.parseInt(myArray[i + 1][0])) {
    				String cap = myArray[i][1];
    				String state = myArray[i][0];
    				myArray[i][1] = myArray[i + 1][1];
    				myArray[i][0] = myArray[i + 1][0];
    				myArray[i + 1][1] = cap;
    				myArray[i + 1][0] = state;
    				isSorted = false;
    			}
    		}
    		if(isSorted == true) {
    			break;
    		}
    	}
    }
    
    // function to bubble sort by the capitals
    public static long bubbleSortCapsFuncOptimized(String myArray[][]) {
    	int counter1 = 0;
    	int counter2 = 0;
    	int counter3 = 0;
    	boolean isSorted = true;
    	for (int j = 0; j < myArray.length - 1; j++ ) {
    		counter1++;
    		for (int i = 0; i < myArray.length - 1 - j; i++ ) {
    			// sorting by first letter
    			counter2++;
    			if ((int)myArray[i][1].toCharArray()[0] > (int)myArray[i + 1][1].toCharArray()[0]) {
    				String cap = myArray[i][1];
    				String state = myArray[i][0];
    				myArray[i][1] = myArray[i + 1][1];
    				myArray[i][0] = myArray[i + 1][0];
    				myArray[i + 1][1] = cap;
    				myArray[i + 1][0] = state;
    				isSorted = false;
    			}
    			
    			// continue sorting by checking next letter 
    			// of capitals start with the same letter
    			if (((int)myArray[i][1].toCharArray()[0] == (int)myArray[i + 1][1].toCharArray()[0])) {
    				for (int k = 1; k < Math.min((int)myArray[i][1].toCharArray().length, (int)myArray[i + 1][1].toCharArray().length); k++ ) {
    					counter3++;
    					if ((int)myArray[i][1].toCharArray()[k] < (int)myArray[i + 1][1].toCharArray()[k]) {
							String cap = myArray[i][1];
		    				String state = myArray[i][0];
		    				myArray[i][1] = myArray[i + 1][1];
		    				myArray[i][0] = myArray[i + 1][0];
		    				myArray[i + 1][1] = cap;
		    				myArray[i + 1][0] = state;
		    				isSorted = false;
		    				break;		
    					} else if ((int)myArray[i][1].toCharArray()[k] == (int)myArray[i + 1][1].toCharArray()[k]) {
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
    
    // function to print array of states and capitals
    public static void printArray(String arrayToPrint[][]) {
    	for (int j = 0; j < arrayToPrint.length; j++ ) {
    		System.out.println(arrayToPrint[j][0] + ", " + arrayToPrint[j][1]);
    	}
    }
    
    // printing our map
    public static void printMap(Map<String, String> myMap) {
    	for (Map.Entry<String, String> entry : myMap.entrySet()) {
    	      System.out.println(entry.getValue() + " is the state capital of " + entry.getKey());
    	    }
    }
    
    // printing our TreeMap
    public static void printTreeMap(TreeMap<String, String> myTree) {
    	for (Map.Entry<String, String> entry : myTree.entrySet()) {
    	      System.out.println(entry.getValue() + " is the state capital of " + entry.getKey());
    	    }
    }
    
    // function to check if the sorting went well without errors
    public static boolean compareArrays(String badArray[][], String choice) {
    	String Cap_States_Good_Sorted_clone[][] = getCloneOf2D(Cap_States_Good_Sorted);
    	int badCount = 0;
    	boolean result = true;
    	if(choice.equals("ByState")) {
	    	for (int j = 0; j < Cap_States_Good_Sorted_clone.length; j++ ) {
	    		if (!Cap_States_Good_Sorted_clone[j][0].equals(badArray[j][0])) {
	    			badCount++;
	    			result = false;
	    			System.out.println("Wrong: " + Cap_States_Good_Sorted_clone[j][0] + " and " + badArray[j][0]);
	    		}
	    	}
    	} 
    	else if(choice.equals("ByCapitals")) {
    		bubbleSortCapsFuncOptimized(Cap_States_Good_Sorted_clone);
	    	for (int j = 0; j < Cap_States_Good_Sorted_clone.length; j++ ) {
	    		if (!Cap_States_Good_Sorted_clone[j][0].equals(badArray[j][0])) {
	    			badCount++;
	    			result = false;
	    			System.out.println("Wrong: " + Cap_States_Good_Sorted_clone[j][0] + " and " + badArray[j][0]);
	    		}
	    	}
    	}
    	// String outcome = (badCount > 0) ? "Got wrong: " + badCount + " times!" : "All is good brother!";
    	// System.out.println(outcome);
    	return result;
    }
    
    // method to get an unsorted array for testing purposes
    public static String[][] getUnsortedArrayOfStatesAndCaps() {
    	int maxLength = Cap_States_Good_Sorted.length;
    	String Cap_States_Good_Sorted_Cloned[][] = getCloneOf2D(Cap_States_Good_Sorted);
        String Cap_States_Random[][] = new String[maxLength][2];
    	int k = 0;
    	while (maxLength > 0) {
    		String[][] New_Cap_States = new String[maxLength][2];
            Random random = new Random();
            int randomNumber = random.nextInt(maxLength);
            for (int j = 0, n = 0; j < maxLength; j++) {
            	if (j == randomNumber) {
                	Cap_States_Random[k][0] = Cap_States_Good_Sorted_Cloned[randomNumber][0];
                	Cap_States_Random[k][1] = Cap_States_Good_Sorted_Cloned[randomNumber][1];
            		continue;
            	}
            	New_Cap_States[n++] = Cap_States_Good_Sorted_Cloned[j];
            }
            maxLength--;
            k++;
            Cap_States_Good_Sorted_Cloned = getCloneOf2D(New_Cap_States);
    	}
    	return Cap_States_Random;
    }
    
    // function to output the results of U.S. State Capitals Trivia 
    public static void printResults(int result, String userName) {
    	if (result == Cap_States_Good_Sorted.length) {
			System.out.println("Congrats " + userName + ", you got all questions right!");
		} else if (result == 1) {
			System.out.println("Well " + userName + ", you got only one right!");
		} else if (result == 0){
			System.out.println("Well, well, well " + userName + "! All answers were wrong! Please try again later!");
		} else {
			System.out.println("Not too bad " + userName + "! You got " + result + " states right!");
		}
    }
    
    
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
    
    // method to get a clone of 2-D array
    public static String[][] getCloneOf2D(String myArray[][]) {
        String[][] arrayClone = new String[50][2];
        
        // Copy each row using Arrays.copyOf() method
        for (int i = 0; i < myArray.length; i++) {
        	arrayClone[i] = Arrays.copyOf(myArray[i], myArray[i].length);
        }
        return arrayClone;
    }
    
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
    
    // Trivia Game function
    public static void playTrivia(Scanner scanner) {
    	int goodCount = 0;
		System.out.println("<---------------------------------------------------------------------------->");
		System.out.println("	Let's play U.S. State Capitals Trivia");
		System.out.println("<---------------------------------------------------------------------------->");
		System.out.println("Please enter your name for our scoreboard: ");
		String userName = scanner.nextLine();
		System.out.println("<---------------------------------------------------------------------------->");
		System.out.println("	Thank you " + userName + "! Let's play!");
		System.out.println("<---------------------------------------------------------------------------->");
		for (int i = 0; i < Cap_States_Good_Sorted.length; i++) {
			System.out.println("What is the capital of " + Cap_States_Good_Sorted[i][0]);
			String tempCapital = scanner.nextLine();
			if (tempCapital.equalsIgnoreCase(Cap_States_Good_Sorted[i][1])) {
				goodCount++;
			}
			// below there are some cheat codes I created
			// to easily test the functionality
			if (tempCapital.equals("I want to exit")) {
				System.out.println("<---------------------------------------------------------------------------->");
				System.out.println("Your score is: " + ((double) goodCount / Cap_States_Good_Sorted.length) * 100 + " so far! Sorry to see you going!\n Score is saved anyway!");
				System.out.println("<---------------------------------------------------------------------------->");
				addScore(((double) goodCount / Cap_States_Good_Sorted.length ) * 100, userName);
				return;
			}
			if (tempCapital.equals("Get 40")) {
				goodCount = 20;
				System.out.println("<---------------------------------------------------------------------------->");
				System.out.println("Your score is: " + ((double) goodCount / Cap_States_Good_Sorted.length) * 100 + " so far! Sorry to see you going!\n Score is saved anyway!");
				System.out.println("<---------------------------------------------------------------------------->");
				addScore(((double) goodCount / Cap_States_Good_Sorted.length ) * 100, userName);
				return;
			}
			if (tempCapital.equals("Get 60")) {
				goodCount = 35;
				System.out.println("<---------------------------------------------------------------------------->");
				System.out.println("Your score is: " + ((double) goodCount / Cap_States_Good_Sorted.length) * 100 + " so far! Sorry to see you going!\n Score is saved anyway!");
				System.out.println("<---------------------------------------------------------------------------->");
				addScore(((double) goodCount / Cap_States_Good_Sorted.length ) * 100, userName);
				return;
			}
			if (tempCapital.equals("Get 100")) {
				goodCount = 50;
				System.out.println("<---------------------------------------------------------------------------->");
				System.out.println("Your score is: " + ((double) goodCount / Cap_States_Good_Sorted.length) * 100 + " so far! Sorry to see you going!\n Score is saved anyway!");
				System.out.println("<---------------------------------------------------------------------------->");
				addScore(((double) goodCount / Cap_States_Good_Sorted.length ) * 100, userName);
				return;
			}
		}
	
		// let's display our results
		int myScore = (goodCount / Cap_States_Good_Sorted.length) * 100;
		System.out.println("<---------------------------------------------------------------------------->");
		// print the results to the console
		printResults(goodCount, userName);
		// let's add our score to the score board
		addScore(myScore, userName);
    }
    
    // Single Q/A game
    public static void playQA(Scanner scanner) {
    	// let's create HashMap of States and Capitals from Cap_States array
    	Map<String, String> StatesCapitals = new HashMap<String, String>();
		for (int i = 0; i < Cap_States.length; i++) {
			StatesCapitals.put(Cap_States_Good_Sorted[i][0], Cap_States_Good_Sorted[i][1]);
		}
		// let's create our TreeMap to sort by states
		TreeMap<String, String> myCapsStates = new TreeMap<String, String>();
		for (Map.Entry<String, String> entry : StatesCapitals.entrySet()) {
			myCapsStates.put(entry.getKey(), entry.getValue());
		}
    	System.out.println("Please enter the State: ");
		String tempState = scanner.nextLine();
		if (myCapsStates.containsKey(tempState)) {
			System.out.println("The capital of " + tempState + " is " + myCapsStates.get(tempState));
		} else {
			System.out.println("State does not exists!");
		}
    }
    
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
    
    
	public static void main(String[] args) {
		// Part I
		String[][] unsortedArray = getUnsortedArrayOfStatesAndCaps();
		String[][] unsortedArrayClone = getCloneOf2D(unsortedArray);
		int goodCount = 0;
		int myIterations = 0;
		Scanner scnr = new Scanner(System.in);
		
	    // Loop that displays the menu, accepts the users input
	    // and takes the appropriate action.		
	    String inputText;
	    displayMenu();
	    do {
	    	inputText = scnr.nextLine();
	        switch (inputText) {
	        case "1":
	    		System.out.println("<---------------------------------------------------------------------------->");
	    		System.out.println("	Please see our unsorted array of the states and capitals below:");
	    		System.out.println("<---------------------------------------------------------------------------->");
	        	printArray(unsortedArray);
	        	System.out.println("<---------------------------------------------------------------------------->");
	            break;
	        case "2":
	    		System.out.println("<---------------------------------------------------------------------------->");
	    		System.out.println("	Please see our sorted array by states of the states and capitals below:");
	    		System.out.println("<---------------------------------------------------------------------------->");
	        	bubbleSortStatesFuncOptimized(unsortedArray);
	        	printArray(unsortedArray);
	        	System.out.println("<---------------------------------------------------------------------------->");
	            break;
	        case "3":
	    		System.out.println("<---------------------------------------------------------------------------->");
	    		System.out.println("	Please see our sorted array by capitals of the states and capitals below:");
	    		System.out.println("<---------------------------------------------------------------------------->");
	        	bubbleSortCapsFuncOptimized(unsortedArray);
	        	printArray(unsortedArray);
	    		System.out.println("<---------------------------------------------------------------------------->");
	            break;
	        case "4": 
	        	playTrivia(scnr);
	            break;
	        case "5":
	        	playQA(scnr);
	            break;
	        case "6":
	        	System.out.println("Please enter an integer: ");
	        	// casting string to integer, as we already know we are getting an integer
	        	// that follows our pattern
	        	myIterations = Integer.parseInt(checkAnswer(scnr));
	        	getAverageOptimization(myIterations);
	            break;
	        case "7":
	        	showScoreBoard();
	        	break;
	        case "q":
	        	System.out.println("\nThank you! I hope to see you soon!");
	        	return;
	        default:
	        	System.out.println("Please use numbers from 1 to 7, or q to exit");
	            break;
	        }
	    	displayMenu();
	    } while(true);
	}
	
}
