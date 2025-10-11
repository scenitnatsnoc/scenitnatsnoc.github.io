package assignment_1;

import java.util.TreeMap;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class Main {
	
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
    
    // function to bubble sort by the capitals
    public static void bubbleSortCapsFunc(String myArray[][]) {
    	for (int j = 0; j < myArray.length - 1; j++ ) {
    		for (int i = 0; i < myArray.length - 1 - j; i++ ) {
    			// sorting by first letter
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
    }
    // function to bubble sort by the states
    public static void bubbleSortStatesFunc(String myArray[][]) {
    	for (int j = 0; j <  myArray.length - 1; j++ ) {
    		for (int i = 0; i < myArray.length - 1 - j; i++ ) {
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
    }
    
    // function to print array of states and capitals
    public static void printArray(String[][] myArray) {
    	for (int j = 0; j < Cap_States.length; j++ ) {
    		System.out.println(Cap_States[j][0] + ", " + Cap_States[j][1]);
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
    public static void compareArrs(String[][] badArray) {
    	int badCount = 0;
    	for (int j = 0; j < Cap_States_Good_Sorted.length; j++ ) {
    		if (!Cap_States_Good_Sorted[j][0].equals(badArray[j][0])) {
    			badCount++;
    			System.out.println("Wrong: " + Cap_States_Good_Sorted[j][0] + " and " + badArray[j][0]);
    		}
    	}
    	String result = (badCount > 0) ? "Got wrong: " + badCount + " times!" : "All is good brother!";
    	System.out.println(result);
    }
    
    // function to output the results of U.S. State Capitals Trivia 
    public static void printResults(int result) {
    	if (result == Cap_States_Good_Sorted.length) {
			System.out.println("Congrats, you got all questions right!");
		} else if (result == 1) {
			System.out.println("Well, you got only one right!");
		} else if (result == 0){
			System.out.println("All answers were wrong! Please try again later!");
		} else {
			System.out.println("Not too bad! You got " + result + " states right!");
		}
    }
    
	public static void main(String[] args) {

		// Part I
		
		int goodCount = 0;
		Scanner scnr = new Scanner(System.in);
		
		System.out.println("<---------------------------------------------------------------------------->");
		System.out.println("				  Part I");
		System.out.println("<---------------------------------------------------------------------------->");

		System.out.println("<---------------------------------------------------------------------------->");
		System.out.println("	Please see our unsorted array of the states and capitals below:");
		System.out.println("<---------------------------------------------------------------------------->");
		printArray(Cap_States);
		System.out.println("<---------------------------------------------------------------------------->");
		System.out.println("	Please see our sorted array by the capitals below,\n		by using bubble sort method:");
		System.out.println("<---------------------------------------------------------------------------->");
		bubbleSortCapsFunc(Cap_States);
    	printArray(Cap_States);
    	
		System.out.println("<---------------------------------------------------------------------------->");
		System.out.println("	Let's play U.S. State Capitals Trivia");
		System.out.println("<---------------------------------------------------------------------------->");
		for (int i = 0; i < Cap_States_Good_Sorted.length; i++) {
			System.out.println("What is the capital of " + Cap_States_Good_Sorted[i][0]);
			String tempCapital = scnr.nextLine();
			if (tempCapital.equalsIgnoreCase(Cap_States_Good_Sorted[i][1])) {
				goodCount++;
			}
		}

		// let's display our results
		System.out.println("<---------------------------------------------------------------------------->");
		printResults(goodCount);
    	
		// Part II
		// let's add our states and capitals to our new HashMap
    	// the result will be unordered as HashMap does not guarantee
    	// the order
		System.out.println("<---------------------------------------------------------------------------->");
		System.out.println("				  Part II");
		System.out.println("<---------------------------------------------------------------------------->");
		Map<String, String> StatesCapitals = new HashMap<String, String>();
		for (int i = 0; i < Cap_States.length; i++) {
			StatesCapitals.put(Cap_States_Good_Sorted[i][0], Cap_States_Good_Sorted[i][1]);
		}
		
		// let's print our HashMap
		System.out.println("Our unsorted Map(the keys and values are not ordered) printed below ");
		System.out.println("<---------------------------------------------------------------------------->");
		printMap(StatesCapitals);
		
		// let's create our TreeMap to sort by states
		TreeMap<String, String> myCapsStates = new TreeMap<String, String>();
		for (Map.Entry<String, String> entry : StatesCapitals.entrySet()) {
			myCapsStates.put(entry.getKey(), entry.getValue());
		}
		
		// and let's print our sorted TreeMap
		System.out.println("<---------------------------------------------------------------------------->");
		System.out.println("And our sorted Map by states(as a key) by using TreeMap natural sorting below");
		System.out.println("<---------------------------------------------------------------------------->");
		printTreeMap(myCapsStates);
		System.out.println("<---------------------------------------------------------------------------->");
		
		System.out.println("Please enter the State: ");
		String tempState = scnr.nextLine();
		if (myCapsStates.containsKey(tempState)) {
			System.out.println("The capital of " + tempState + " is " + myCapsStates.get(tempState));
		} else {
			System.out.println("State does not exists!");
		}
		
	}
}