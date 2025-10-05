/*
CS499 Example code for code review
Course IT-145
I was tasked with developing a software application 
in Java for the fictitious company Grazioso Salvare, 
which helps track search and rescue animals. 
These search and rescue animals are obtained and trained 
by the company for the purpose of rescuing humans from 
life-threatening situations.
Programmer: Konstantin Dobikov
Last Date updated: 06/03/2023
Artifact 1 Category 1 in Software Design and Engineering
No known errors
Enhancement Plan:
1.	Migrate the application to Python by porting Dog, Driver, Monkey, 
	and RescueAnimal classes in addition to Database, Users, and RescueAnimalDatabaseServices, 
	UsersDatabaseServices new classes.
2.	Add SQLite database for persistent storage
3.	Add login functionality
4.	Implement secure storage of hashed credentials.

*/

package grazioso;

import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    private static ArrayList<Dog> dogList = new ArrayList<Dog>();
    // Instance variables (if needed)
    private static ArrayList<Monkey> monkeyList = new ArrayList<Monkey>();
    //pattern for digits only
    private static final String DIGIT_PATTERN = "-?\\d+(\\.\\d+)?";
    //pattern for date format only
    private static final String DATE_PATTERN =
            "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/((19|2[0-9])[0-9]{2})$";
    
    public static void main(String[] args) {
    	
    	Scanner scnr = new Scanner(System.in);
    	
        initializeDogList();
        initializeMonkeyList();
        
        // Loop that displays the menu, accepts the users input
        // and takes the appropriate action.
	
        String inputText;
        displayMenu();
        do {
        	inputText = scnr.nextLine();
            switch (inputText) {
            case "1":
            	intakeNewDog(scnr);
                break;
            case "2":
            	intakeNewMonkey(scnr);
                break;
            case "3":
            	reserveAnimal(scnr);
                break;
            case "4": 
            	printAnimals("dog");
                break;
            case "5":
            	printAnimals("monkey");
                break;
            case "6":
            	printAnimals("");
                break;
            case "q":
            	System.out.println("\nSee you later! Oops, saving data module is disabled!");
            	return;
            default:
            	System.out.println("Please use numbers from 1 to 6, or q to exit");
                break;
            }
        	displayMenu();
        } while(true);
    }

    // This method prints the menu options
    public static void displayMenu() {
        System.out.println("\n\n");
        System.out.println("\t\t\t\tRescue Animal System Menu");
        System.out.println("[1] Intake a new dog");
        System.out.println("[2] Intake a new monkey");
        System.out.println("[3] Reserve an animal");
        System.out.println("[4] Print a list of all dogs");
        System.out.println("[5] Print a list of all monkeys");
        System.out.println("[6] Print a list of all animals that are not reserved");
        System.out.println("[q] Quit application");
        System.out.println();
        System.out.println("Enter a menu selection");
    }


    // Adds dogs to a list for testing
    public static void initializeDogList() {
        Dog dog1 = new Dog("Spot", "German Shepherd", "male", "1", "25.6", "05-12-2019", "United States", "intake", false, "United States");
        Dog dog2 = new Dog("Rex", "Great Dane", "male", "3", "35.2", "02-03-2020", "United States", "Phase I", false, "United States");
        Dog dog3 = new Dog("Bella", "Chihuahua", "female", "4", "25.6", "12-12-2019", "Canada", "in-service", false, "Canada");
        Dog dog4 = new Dog("BellaClone", "Chihuahua", "female", "4", "25.6", "12-12-2019", "Canada", "in-service", false, "Canada");

        dogList.add(dog1);
        dogList.add(dog2);
        dogList.add(dog3);
        dogList.add(dog4);
        
    }


    // Adds monkeys to a list for testing
    //Optional for testing

    public static void initializeMonkeyList() {
        Monkey monkey1 = new Monkey("Coin", "Capuchin", "male", "1", "25.6", "05-12-2019", "United States", "intake", false, "United States", "2.0", "1.5", "3.0");
        Monkey monkey2 = new Monkey("Eth", "Guenon", "male", "3", "35.2", "02-03-2020", "United States", "Phase I", false, "United States", "3.0", "2.5", "4.0");
        Monkey monkey3 = new Monkey("Bitcoin", "Macaque", "female", "4", "25.6", "12-12-2019", "Canada", "in-service", false, "Canada", "4.0", "3.5", "5.0");

        monkeyList.add(monkey1);
        monkeyList.add(monkey2);
        monkeyList.add(monkey3);
    }


    // intakeNewDog method
    // Instantiate and add the new dog to the appropriate list with input validation
    
    public static void intakeNewDog(Scanner scanner) {
        System.out.println("What is the dog's name?");
        String name = scanner.nextLine();
        for(Dog dog: dogList) {
            if(dog.getName().equalsIgnoreCase(name)) {
                System.out.println("\n\nThis dog is already in our system\n\n");
                return; //returns to menu
            }
        }

        // Add the code to instantiate a new dog and add it to the appropriate list
        System.out.println("What is the dog's breed?");
        String breed = scanner.nextLine();
        System.out.println("What is the dog's gender?");
        String gender = scanner.nextLine();
        System.out.println("What is the dog's age?");
        String age = checkAnswer(scanner, DIGIT_PATTERN);
        System.out.println("What is the dog's weight in lbs?");
        String weight = checkAnswer(scanner, DIGIT_PATTERN);
        System.out.println("What is the dog's acquisition date? In format: dd/mm/yyyy");
        String acquisitionDate = checkAnswer(scanner, DATE_PATTERN);
        System.out.println("What is the dog's acquisition country?");
        String acquisitionCountry = scanner.nextLine();
        System.out.println("What is the dog's training status?\n"
        		+ "Possible answers: intake, farm, in-service, farm, Phase (I, II, III, IV, V)");
        // getting training status of a dog
        String trainingStatus = scanner.nextLine();
        boolean choice; //need it to stop the loop when we get the correct training status 
        do {
        	choice = true;
        	if (!trainingStatus.equals("intake") 
        			&& !trainingStatus.equals("in-service") 
        			&& !trainingStatus.equals("farm")
        			&& !trainingStatus.equals("Phase I")
        			&& !trainingStatus.equals("Phase II")
        			&& !trainingStatus.equals("Phase III")
        			&& !trainingStatus.equals("Phase IV")
        			&& !trainingStatus.equals("Phase V")) {
        		System.out.println("Whrong status! Please use the correct status!");
        		trainingStatus = scanner.nextLine();
        	}
        	else {
        		System.out.println("Thank you!\nAdding the dog in our database...");
        		choice = false;
        	}
        	
        } while(choice);
        // default not reserved
        boolean reserved = false;
        // default acquisition country is equal to service country
        String inServiceCountry = acquisitionCountry;
        // create instance of Dog and add to the ArrayList dogList
        Dog dog = new Dog(name, breed, gender, age,
        	weight, acquisitionDate, acquisitionCountry,
        	trainingStatus, reserved, inServiceCountry);
        dogList.add(dog);
        System.out.println("Dog was added.\nReturning to the main menu...");
        return;
    }


        // intakeNewMonkey
		// Instantiate and add the new monkey to the appropriate list with input validation

        public static void intakeNewMonkey(Scanner scanner) {
        	System.out.println("What is the monkey's name?");
            String name = scanner.nextLine();
            for(Monkey monkey: monkeyList) {
                if(monkey.getName().equalsIgnoreCase(name)) {
                    System.out.println("\n\nThis monkey is already in our system\n\n");
                    return; //returns to menu
                }
            }
            System.out.println("What is the monkey's species?");
            String species = scanner.nextLine();
            if (!species.equalsIgnoreCase("Capuchin") 
            		&& !species.equalsIgnoreCase("Guenon") 
            		&& !species.equalsIgnoreCase("Macaque") 
            		&& !species.equalsIgnoreCase("Marmoset") 
            		&& !species.equalsIgnoreCase("Squirrel monkey") 
            		&& !species.equalsIgnoreCase("Tamarin")) {
                System.out.println("\n\nThis monkey species is not allowed\n\n");
                return; 
            }
            System.out.println("What is the monkey's gender?");
            String gender = scanner.nextLine();
           
            System.out.println("What is the monkey's age?");
            String age = checkAnswer(scanner, DIGIT_PATTERN);
            System.out.println("What is the monkey's weight in lbs?");
            String weight = checkAnswer(scanner, DIGIT_PATTERN);
            System.out.println("What is the monkey's acquisition date? In format: dd/mm/yyyy");
            String acquisitionDate = checkAnswer(scanner, DATE_PATTERN);
            System.out.println("What is the monkey's acquisition country?");
            String acquisitionCountry = scanner.nextLine();
            //default status is intake
            String trainingStatus = "intake";
            //default is not reserved
            boolean reserved = false;
            //default acquisition country is equal to service country
            String inServiceCountry = acquisitionCountry;
            //height, bodyLength, tailLength
            System.out.println("What is the monkey's height in inches?");
            String height = checkAnswer(scanner, DIGIT_PATTERN);
            
            System.out.println("What is the monkey's body length inches?");
            String bodyLength = checkAnswer(scanner, DIGIT_PATTERN);
            
            System.out.println("What is the monkey's tail length inches?");
            String tailLength = checkAnswer(scanner, DIGIT_PATTERN);

            //create instance of Monkey and add to the ArrayList monkeyList
            Monkey monkey = new Monkey(name, species, gender, age,
            	weight, acquisitionDate, acquisitionCountry,
            	trainingStatus, reserved, inServiceCountry,
            	height, bodyLength, tailLength);
            monkeyList.add(monkey);
            System.out.println("Monkey was added. Returning to the main menu.");
            return;
        }

        // reserveAnimal method
        // We will need to find the animal by animal type and in service country
        public static void reserveAnimal(Scanner scanner) {
        	System.out.println("Please enter animal's type to be reserved?");
            String type = scanner.nextLine();
            boolean choiceAnimal;
            do {
            	choiceAnimal = true;
            	if (!type.equalsIgnoreCase("dog") 
            			&& !type.equalsIgnoreCase("monkey")) {
            		System.out.println("Whrong type!\nPossible types: dog, monkey");
            		type = scanner.nextLine();
            	}
            	else {
            		choiceAnimal = false;
            	}
            	
            } while(choiceAnimal);
        	System.out.println("Please enter animal's country to be reserved?");
            String inServiceCountry = scanner.nextLine();
            if (type.equalsIgnoreCase("dog")) {
            	for(Dog dog: dogList) {
            		if (dog.getInServiceLocation().equalsIgnoreCase(inServiceCountry) 
            				&& dog.getTrainingStatus().equals("in-service") 
            				&& !dog.getReserved()) {
            				dog.setReserved(true);
            				System.out.println(dog.getName() + " is reserved!");
            				return;
            	    }
            	}
            	noAvail();
            } 
            else if (type.equalsIgnoreCase("monkey")) {
            	for(Monkey monkey: monkeyList) {
            		if (monkey.getInServiceLocation().equalsIgnoreCase(inServiceCountry) 
            				&& monkey.getTrainingStatus().equals("in-service") 
            				&& !monkey.getReserved()) {
            				monkey.setReserved(true);
            				System.out.println(monkey.getName() + " is reserved!");
            				return;
            		} 
            	}
            	noAvail();
            }
            else {
            	noAvail();
            }
        }
        public static void noAvail() {
        	System.out.println("No animal to reserve in this country! Come back later!");
        	return;
        }
        // test method to reserve a dog
        public static void dogReserve(ArrayList<Dog> dogList, String inServiceCountry) {
        	for(Dog dog: dogList) {
        		if (dog.getInServiceLocation().equals(inServiceCountry) && dog.getTrainingStatus().equals("in-service") && !dog.getReserved()) {
        				dog.setReserved(true);
        				System.out.println(dog.getName() + " is reserved!");
        				return;
        	    }
        	}
        	noAvail();
        }
        // test method to reserve a monkey
        public static void monkeyReserve(ArrayList<Monkey> monkeyList, String inServiceCountry) {
        	for(Monkey monkey: monkeyList) {
        		if (monkey.getInServiceLocation().equals(inServiceCountry) && monkey.getTrainingStatus().equals("in-service") && !monkey.getReserved()) {
        				monkey.setReserved(true);
        				System.out.println(monkey.getName() + " is reserved!");
        				return;
        		} 
        	}
        	noAvail();
        }
        // method to check to get the right answer
        public static String checkAnswer(Scanner scanner, String pattern) {
        	String answer;
        	boolean rightAnswer = true;
        	answer = scanner.nextLine();
        	do {
        		if(answer.matches(pattern)){
        			rightAnswer = false;
        	}
        	else {
        		if (pattern == DIGIT_PATTERN) {
        			System.out.print("Not allowed, please use numbers only: ");
        		} 
        		else if (pattern == DATE_PATTERN) {
        			System.out.print("Not allowed, please use the right format dd/mm/yyyy: ");
        		}
            	answer = scanner.nextLine();
             }
        	} while(rightAnswer);
        	return answer;
        }
        
        // method printAnimals
        // Includes the animal name, status, acquisition country and if the animal is reserved.
        // This method connects to three different menu items.
        // The printAnimals() method has three different outputs
        // based on the listType parameter
        // dog - prints the list of dogs
        // monkey - prints the list of monkeys
        // available - prints a combined list of all animals that are
        // fully trained ("in service") but not reserved 
        public static void printAnimals(String listType) {
        	if (listType.equals("monkey")) {
        		System.out.printf("-------------------------------------------------------------------\n");
        		System.out.printf("| %-10s | %18s | %16s | %10s |\n", "Name", "Training Status", "Location", "Reserved");
        		System.out.printf("-------------------------------------------------------------------\n");
        		for(Monkey monkey: monkeyList) {
        			System.out.printf("| %-10s | %18s | %16s | %10s |\n", monkey.getName(), monkey.getTrainingStatus(), monkey.getAcquisitionLocation(), monkey.getReserved());           
        		}
        		System.out.printf("-------------------------------------------------------------------\n");
        	}
        	else if (listType.equals("dog")) {
        		System.out.printf("-------------------------------------------------------------------\n");
        		System.out.printf("| %-10s | %18s | %16s | %10s |\n", "Name", "Training Status", "Location", "Reserved");
        		System.out.printf("-------------------------------------------------------------------\n");
        		for(Dog dog: dogList) {
        			System.out.printf("| %-10s | %18s | %16s | %10s |\n", dog.getName(), dog.getTrainingStatus(), dog.getAcquisitionLocation(), dog.getReserved());
        		}
        		System.out.printf("-------------------------------------------------------------------\n");
        	}        		
        	else {
                int i = 0; //count of reserved animals
                System.out.printf("--------------------------------------------------------------------------------\n");
        		System.out.printf("| %-10s | %10s | %18s | %16s | %10s |\n", "Name", "Type", "Training Status", "Location", "Reserved");
        		System.out.printf("--------------------------------------------------------------------------------\n");
                for(Dog dog: dogList) {
             	   if (dog.getTrainingStatus().equals("in-service") && !dog.getReserved()) {
             		  System.out.printf("| %-10s | %10s | %18s | %16s | %10s |\n", dog.getName(), "Dog", dog.getTrainingStatus(), dog.getAcquisitionLocation(), dog.getReserved());
                        ++i;
             	   }
            		}
                for(Monkey monkey: monkeyList) {
             	   if (monkey.getTrainingStatus().equals("in-service") && !monkey.getReserved()) {
             		  System.out.printf("| %-10s | %10s | %18s | %16s | %10s |\n", monkey.getName(), "Monkey", monkey.getTrainingStatus(), monkey.getAcquisitionLocation(), monkey.getReserved());
                        ++i;
             	   }
                }
                System.out.printf("--------------------------------------------------------------------------------\n");
                System.out.println();
                if (i == 0) {
             	   System.out.println("No animals available to reserve! Comeback later!");
                }
        	}
        }

}

