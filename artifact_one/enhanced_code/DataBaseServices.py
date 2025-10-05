# importing DataBase class from DataBaseClasses as database
from DataBaseClasses import DataBase as database
# importing User, Dog, Monkey, RescueAnimal classes from DataBaseClasses
from DataBaseClasses import User, Dog, Monkey, RescueAnimal
# imporing session from sqlalchemy
from sqlalchemy.orm import Session
# importing select from sqlalchemy
from sqlalchemy import select
# os library for salt generation
import os
# hashlib library for hashing
import hashlib


class DataBaseServices:
    def __init__(self, file):
        self.database_object = database(file)

    # get a secure hash for our password
    def getPasswordSaltedHash(self, password):
        #  generating out salt 
        salt = os.urandom(16)
        iterations = 100_000
        hash_value = hashlib.pbkdf2_hmac(
            "sha256",
            password.encode('utf-8'),
            salt,
            iterations
        )
        password_hash = salt + hash_value
        return password_hash

    # get pbkdf2_hmac
    def getPBKDF2_hmac(self, hash_algo, password, salt, iterations):
        return hashlib.pbkdf2_hmac(
            hash_algo,
            password.encode('utf-8'),
            salt,
            iterations
        )

    # add user to Users table
    def addUser(self, username, password):
        password_hash = self.getPasswordSaltedHash(password)
        with Session(self.database_object.getEngine()) as session:
            user = User(
            name = username,
            saltedhash = password_hash
            )
            session.add_all([user])
            session.commit()
            
    # check if any users in Users table
    def checkUsersTableEmpty(self):
        with Session(self.database_object.getEngine()) as session:
            stmt = select(User)
            results = session.scalars(stmt).all()
        if (results):
            return True
        else:
            return False

    # check if data exists in RescueAnimal table
    def checkRescueAnimalsTableEmpty(self):
        with Session(self.database_object.getEngine()) as session:
            stmt = select(RescueAnimal)
            results = session.scalars(stmt).all()
        if (results):
            return True
        else:
            return False

    # function to add several monkeys and dogs into our database
    def initializeData(self):
        with Session(self.database_object.getEngine()) as session:
            dog1 = Dog(
            name = "Spot",
            breed = "German Shepherd",
            gender = "male",
            age = "1",
            weight = "25.6",
            acquisitionDate = "05-12-2019",
            acquisitionCountry = "United States",
            trainingStatus = "intake",
            reserved = False,
            inServiceCountry = "United States")

            dog2 = Dog(
            name = "Rex",
            breed = "Great Dane",
            gender = "male",
            age = "3",
            weight = "35.2",
            acquisitionDate = "02-03-2020",
            acquisitionCountry = "United States",
            trainingStatus = "Phase I",
            reserved = False,
            inServiceCountry = "United States")

            dog3 = Dog(
            name = "Bella",
            breed = "Chihuahua",
            gender = "female",
            age = "4",
            weight = "25.6",
            acquisitionDate = "12-12-2019",
            acquisitionCountry = "Canada",
            trainingStatus = "in-service",
            reserved = False,
            inServiceCountry = "Canada")

            dog4 = Dog(
            name = "BellaClone",
            breed = "Chihuahua",
            gender = "female",
            age = "4",
            weight = "25.6",
            acquisitionDate = "12-12-2019",
            acquisitionCountry = "Canada",
            trainingStatus = "in-service",
            reserved = False,
            inServiceCountry = "Canada")

            monkey1 = Monkey(
            name = "Coin",
            species = "Capuchin",
            gender = "male",
            age = "1",
            weight = "25.6",
            acquisitionDate = "05-12-2019",
            acquisitionCountry = "United States",
            trainingStatus = "intake",
            reserved = False,
            inServiceCountry = "United States",
            height = "2.0",
            bodyLength = "1.5",
            tailLength = "3.0")

            monkey2 = Monkey(
            name = "Eth",
            species = "Guenon",
            gender = "male",
            age = "3",
            weight = "35.2",
            acquisitionDate = "02-03-2020",
            acquisitionCountry = "United States",
            trainingStatus = "Phase I",
            reserved = False,
            inServiceCountry = "United States",
            height = "3.0",
            bodyLength = "2.5",
            tailLength = "4.0")

            monkey3 = Monkey(
            name = "Bitcoin",
            species = "Macaque",
            gender = "female",
            age = "4",
            weight = "25.6",
            acquisitionDate = "12-12-2019",
            acquisitionCountry = "Canada",
            trainingStatus = "in-service",
            reserved = False,
            inServiceCountry = "Canada",
            height = "4.0",
            bodyLength = "3.5",
            tailLength = "5.0")

            session.add_all([dog1, dog2, dog3, dog4, monkey1, monkey2, monkey3])
            session.commit()

    # function to print nicely formated table of animals by given type
    def printRescueAnimalsByType(self, animalType):
        with Session(self.database_object.getEngine()) as session:
            stmt = select(RescueAnimal).where(RescueAnimal.animalType.in_([animalType]))
        for animal in session.scalars(stmt):
            print(animal.name + (10 - len(animal.name))*" ", animal.trainingStatus + (len("Training Status") - len(animal.trainingStatus))*" ", animal.acquisitionCountry + ((len("Location"  + (10 - len("Type"))*" ") - len(animal.acquisitionCountry))) * " ", animal.reserved, sep="  |  ")

    # function to print nicely formated table of all animals that are
    # both have in-service training status and not reserved
    def printReservedReady(self):
        counter = 0
        with Session(self.database_object.getEngine()) as session:
            stmt = select(RescueAnimal)
        for animal in session.scalars(stmt):
            if ((animal.trainingStatus == "in-service") and (not animal.reserved)):
                print(animal.name + (10 - len(animal.name))*" ", animal.animalType + (10 - len(animal.animalType))*" ", animal.trainingStatus + (len("Training Status") - len(animal.trainingStatus))*" ", animal.acquisitionCountry + (len("Location") - len(animal.acquisitionCountry))*" ", animal.reserved, sep="  |  ")
                counter = counter + 1
        print("--------------------------------------------------------------------------------\n")
        print()
        if (counter == 0):
            print("No animals available to reserve! Comeback later!")

    # check if animal with the given name and type exists
    def checkAnimalByNameType(self, animalName, animalType):
        with Session(self.database_object.getEngine()) as session:
            stmt = select(RescueAnimal).where(RescueAnimal.name.in_([animalName])).where(RescueAnimal.animalType.in_([animalType]))
        if (session.scalars(stmt)):
            return False
        else:
            return True

    # get user by username from Users table
    def getUserByName(self, username):
        with Session(self.database_object.getEngine()) as session:
            stmt = select(User).where(User.name.in_([username]))
            results = session.scalars(stmt).all()
        return results

    # method to add one dog to our database
    def addOneDogToDatabase(self, dogName, dogBreed, dogGender, dogAge, dogWeight, dogAcquisitionDate, dogAcquisitionCountry, dogTrainingStatus, dogReserved, dogInServiceCountry):
        with Session(self.database_object.getEngine()) as session:
            dog = Dog(
            name = dogName,
            breed = dogBreed,
            gender = dogGender,
            age = dogAge,
            weight = dogWeight,
            acquisitionDate = dogAcquisitionDate,
            acquisitionCountry = dogAcquisitionCountry,
            trainingStatus = dogTrainingStatus,
            reserved = dogReserved,
            inServiceCountry = dogInServiceCountry)

            session.add_all([dog])
            session.commit()

    # method to add one monkey to our database
    def addOneMonkeyToDatabase(self, monkeyName, monkeySpecies, monkeyGender, monkeyAge, monkeyWeight, monkeyAcquisitionDate, monkeyAcquisitionCountry, monkeyTrainingStatus, monkeyReserved, monkeyInServiceCountry, monkeyHeight, monkeyBodyLength, monkeyTailLength):
        with Session(self.database_object.getEngine()) as session:
            monkey = Monkey(
            name = monkeyName,
            species = monkeySpecies,
            gender = monkeyGender,
            age = monkeyAge,
            weight = monkeyWeight,
            acquisitionDate = monkeyAcquisitionDate,
            acquisitionCountry = monkeyAcquisitionCountry,
            trainingStatus = monkeyTrainingStatus,
            reserved = monkeyReserved,
            inServiceCountry = monkeyInServiceCountry,
            height = monkeyHeight,
            bodyLength = monkeyBodyLength,
            tailLength = monkeyTailLength)

            session.add_all([monkey])
            session.commit()

    # function to get animals which are in-service and not reserved
    # and set the first one's reserved status to reserved if exists
    # and return animal's name otherwise return -1
    def reserveAnimalPerCountryAndType(self, country, type):
        with Session(self.database_object.getEngine()) as session:
            stmt = select(RescueAnimal).where(RescueAnimal.trainingStatus.in_(["in-service"])).where(RescueAnimal.animalType.in_([type])).where(RescueAnimal.reserved.in_([False])).where(RescueAnimal.inServiceCountry.in_([country]))
            # getting first result
            result = session.scalars(stmt).first()
            if (result):
                result.reserved = True
                session.commit()
                return result.name
            else:
                return -1
