#import database servicies
import DataBaseServices
# time library to protect from bruteforcing
# username and password
import time
# regex library to filter user input
import re
# to hide the inputted password with maskpass()
import maskpass

# pattern for digits only
DIGIT_PATTERN = "-?\\d+(\\.\\d+)?"

# pattern for date format only
DATE_PATTERN = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/((19|2[0-9])[0-9]{2})$"

# List of possible trainingStatus
trainingStatusAllowList = ["intake", "farm", "in-service", "farm", "Phase I", "Phase II", "Phase III", "Phase IV", "Phase V"]

# List of allowed Monkeys
allowedMonkeysSpeciesList = ["Capuchin", "Guenon", "Macaque", "Marmoset", "Squirrel monkey", "Tamarin"]

class Driver:
    # default contructor
    def __init__(self, database_file):
        self.dataBaseServices = DataBaseServices.DataBaseServices(database_file)
    
    # method to check answer based on the given pattern
    def checkAnswer(self, pattern):
        rightAnswer = True
        answer = input()
        while(rightAnswer):
            if(re.search(pattern, answer)):
                rightAnswer = False
            else:
                if (pattern == DIGIT_PATTERN):
                    print("Not allowed, please use numbers only: ")
                elif (pattern == DATE_PATTERN):
                    print("Not allowed, please use the right format dd/mm/yyyy: ")
                answer = input()
        return answer

    # intakeNewDog method
    def intakeNewDog(self):
        name = input("What is the dog's name?\n");
        if(self.dataBaseServices.checkAnimalByNameType(name, "dog")):
            print("\n\nThis dog is already in our system\n\n")
            return # returns to menu

        breed = input("What is the dog's breed?\n")
        gender = input("What is the dog's gender?\n")
        print("What is the dog's age?")
        age = self.checkAnswer(DIGIT_PATTERN);
        print("What is the dog's weight in lbs?")
        weight = self.checkAnswer(DIGIT_PATTERN)
        print("What is the dog's acquisition date? In format: dd/mm/yyyy")
        acquisitionDate = self.checkAnswer(DATE_PATTERN)
        acquisitionCountry = input("What is the dog's acquisition country?\n")
        print("What is the dog's training status?\n"
                + "Possible answers: intake, farm, in-service, farm, Phase (I, II, III, IV, V)")
        # getting training status of a dog
        trainingStatus = input()
        # need it to stop the loop when we get the correct training status
        choice = True
        while(choice):
            if (trainingStatus not in trainingStatusAllowList):
                print("Whrong status! Please use the correct status!")
                trainingStatus = input()
            else:
                print("Thank you!\nAdding the dog in our database...")
                choice = False
        # default not reserved
        reserved = False
        # default acquisition country is equal to service country
        inServiceCountry = acquisitionCountry
        # adding a dog to our database
        self.dataBaseServices.addOneDogToDatabase(name, breed, gender, age, weight, acquisitionDate, acquisitionCountry, trainingStatus, reserved, inServiceCountry)
        print("Dog was added to our database.\nReturning to the main menu...");
        return


    # intakeNewMonkey
    def intakeNewMonkey(self):
        name = input("What is the monkey's name?\n")
        if(self.dataBaseServices.checkAnimalByNameType(name, "monkey")):
            print("\n\nThis dog is already in our system\n\n")
            return # returns to menu
        print("What is the monkey's species?\n"
                + "Currently supported species: Capuchin, Guenon, Macaque, Marmoset, Squirrel monkey, Tamarin")
        species = input()
        if (species not in allowedMonkeysSpeciesList):
            print("\n\nThis monkey species is not allowed\n\n")
            return
        gender = input("What is the monkey's gender?\n")
        print("What is the monkey's age?")
        age = self.checkAnswer(DIGIT_PATTERN)
        print("What is the monkey's weight in lbs?")
        weight = self.checkAnswer(DIGIT_PATTERN)
        print("What is the monkey's acquisition date? In format: dd/mm/yyyy")
        acquisitionDate = self.checkAnswer(DATE_PATTERN)
        acquisitionCountry = input("What is the monkey's acquisition country?\n")
        # default status is intake
        trainingStatus = "intake"
        # default is not reserved
        reserved = False
        # default acquisition country is equal to service country
        inServiceCountry = acquisitionCountry
        # continue asking for the rest of the required information such as monkeys height, bodyLength, tailLength
        print("What is the monkey's height in inches?")
        height = self.checkAnswer(DIGIT_PATTERN)

        print("What is the monkey's body length inches?")
        bodyLength = self.checkAnswer(DIGIT_PATTERN)

        print("What is the monkey's tail length inches?")
        tailLength = self.checkAnswer(DIGIT_PATTERN)

        # adding monkey to our database
        self.dataBaseServices.addOneMonkeyToDatabase(name, species, gender, age, weight, acquisitionDate, acquisitionCountry, trainingStatus, reserved, inServiceCountry, height, bodyLength, tailLength)
        print("Monkey was added. Returning to the main menu.");
        return


    # reserveAnimal method
    # We will need to find the animal by animal type and in service country
    def reserveAnimal(self):
        print("Please enter animal's type to be reserved?");
        type = input()
        choiceAnimal = True
        while(choiceAnimal):
            if ( (type != "dog") and (type != "monkey") ):
                print("Whrong type!\nPossible types: dog, monkey")
                type = input()
            else:
                choiceAnimal = False

        print("Please enter animal's country to be reserved?")
        inServiceCountry = input()
        result = self.dataBaseServices.reserveAnimalPerCountryAndType(inServiceCountry, type)
        if (result == -1):
            print("No animal to reserve in this country! Come back later!")
            return
        else:
            print(result + " is reserved!")
            return


    # method printAnimals
    # Includes the animal name, status, acquisition country and if the animal is reserved.
    # This method connects to three different menu items.
    # The printAnimals() method has three different outputs
    # based on the listType parameter
    # dog - prints the list of dogs
    # monkey - prints the list of monkeys
    # available - prints a combined list of all animals that are
    # fully trained ("in service") but not reserved
    def printAnimals(self, animalType):
        if (animalType == "monkey"):
            print("-------------------------------------------------------------------")
            print("Name" + (10 - len("Name"))*" ", "Training Status" + (15 - len("Training Status"))*" ", "Location"  + (10 - len("Type"))*" ", "Reserved", sep="  |  ")
            print("-------------------------------------------------------------------")
            self.dataBaseServices.printRescueAnimalsByType("monkey")
            print("-------------------------------------------------------------------\n")

        elif (animalType == "dog"):
            print("-------------------------------------------------------------------")
            print("Name" + (10 - len("Name"))*" ", "Training Status" + (15 - len("Training Status"))*" ", "Location"  + (10 - len("Type"))*" ", "Reserved", sep="  |  ")
            print("-------------------------------------------------------------------")
            self.dataBaseServices.printRescueAnimalsByType("dog")
            print("-------------------------------------------------------------------\n")

        else:
            print("--------------------------------------------------------------------------------")
            print("Name" + (10 - len("Name"))*" ", "Type" + (10 - len("Type"))*" ", "Training Status", "Location", "Reserved", sep="  |  ")
            print("--------------------------------------------------------------------------------")
            self.dataBaseServices.printReservedReady()

    # This method prints the menu options
    def displayMenu(self):
        print("\n\n");
        print("\t\t\t\tRescue Animal System Menu")
        print("[1] Intake a new dog")
        print("[2] Intake a new monkey")
        print("[3] Reserve an animal")
        print("[4] Print a table of all dogs")
        print("[5] Print a table of all monkeys")
        print("[6] Print a table of all animals that are ready to be reserved")
        print("[q] Quit application")
        print()
        print("Enter a menu selection")

    # main loop of choices
    def mainLoop(self):
        while(True):
            inputText = input("")
            match inputText:
                case "1":
                    self.intakeNewDog()
                case "2":
                    self.intakeNewMonkey()
                case "3":
                    self.reserveAnimal()
                case "4":
                    self.printAnimals("dog")
                case "5":
                    self.printAnimals("monkey")
                case "6":
                    self.printAnimals("")
                case "q":
                    print("\nSee you later! Data was preserved for future use")
                    return
                case _:
                    print("Please use numbers from 1 to 6, or q to exit")
            self.displayMenu()


    # our main function that checks username and password
    # if successfull we display our menu to perform further actions
    # if no user exists new one is created
    def startMain(self):
        # counters for bruteforce protection
        wrongUserCounter = 0
        wrongPasswordCounter = 0
        loggedOut = True
        while(loggedOut):
            print("----------Login Menu----------\n")
            username = input("Enter your username: ")
            results = self.dataBaseServices.getUserByName(username)
            if(results):
                password = maskpass.askpass("Enter your password: ")
                hashed_password = self.dataBaseServices.getPasswordSaltedHash(password)
                salt, key = results[0].saltedhash[:16], results[0].saltedhash[16:]
                hash_algo = "sha256"
                iterations = 100_000

                # get a hash from user entered password
                password_hash_check = self.dataBaseServices.getPBKDF2_hmac(hash_algo, password, salt, iterations)
                if (key == password_hash_check):
                    loggedOut = False
                    self.displayMenu()
                    self.mainLoop()
                else:
                    wrongPasswordCounter += 1
                    print("Wrong password!")
                    pass
            else:
                wrongUserCounter += 1
                print("User does not exist!")
            if (wrongUserCounter > 3 or wrongPasswordCounter > 3):
                # fix me
                # add 1 minutes wait time before trying again
                # store time in the database
                print("Too many attempts. Try again later in 1 minute!")
                time.sleep(60)
                wrongUserCounter = 0
                wrongPasswordCounter = 0
                # break

# Our Main Logic starts here
driver = Driver("RescueAnimal.db")

# import some data if our database is empty
if (driver.dataBaseServices.checkRescueAnimalsTableEmpty()):
    print("RescueAnimal is not empty, no need to initialize data.")
else:
    print("RescueAnimal is empty.\nInitializing data")
    driver.dataBaseServices.initializeData()
    print("Done")

# check if at least one user exists
# else create one
if (not driver.dataBaseServices.checkUsersTableEmpty()):
    print("User table is empty\nLet's create an Admin user\nMake sure you write it down somewhere and store it securely")
    username = input("Enter username: ")
    password = maskpass.askpass("Enter your password: ")
    driver.dataBaseServices.addUser(username, password)

# starting our Main function
driver.startMain()
