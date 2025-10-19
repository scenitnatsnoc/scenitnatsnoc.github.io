# Software design and engineering

### Description 

For my Artifact in the Software Engineering and Design category, I chose to enhance one of the applications I developed in Java during my previous course, IT-145: Java Programming. I was tasked with developing a Java software application for the fictitious company Grazioso Salvare to help track search-and-rescue animals. These search-and-rescue animals are obtained and trained by the company to rescue humans from life-threatening situations. The required functionality of the application was to intake animals, reserve animals, and print available animals to the user's console. My enhancements for this artifact include porting it to Python, adding a SQLite database for persistent storage, and implementing login functionality with secure credential storage.

[Original Code on GitHub](https://github.com/scenitnatsnoc/scenitnatsnoc.github.io/tree/main/artifact_one/original_code)

### Justification

I selected this artifact for my ePortfolio to demonstrate a comprehensive understanding of programming languages and concepts, including object-oriented design, characterized by modularity, reusability, scalability, and maintainability. I also want to demonstrate my security mindset by creating software that is secure by design. Initially, I followed my enhancement plan by manually porting each class, including the database class and its associated services, to perform CRUD actions and other methods. However, I soon discovered SQLAlchemy, a Python SQL toolkit and Object-Relational Mapper, which already included all the functionality I needed. This was achieved through a declarative mapping that defines both the Python object model and its database metadata, describing the actual SQL tables in our database. I used single-table inheritance for the RescueAnimal parent class and its children, Dog and Monkey, making my queries more efficient because only one table is involved rather than complex joins across multiple tables, as shown below:
```python
...
class RescueAnimal(Base):
    __tablename__ = "RescueAnimal"
    id: Mapped[int] = mapped_column(primary_key=True)
    name: Mapped[str]
    animalType: Mapped[str]
    gender: Mapped[str]
    age: Mapped[str]
    weight: Mapped[str]
    acquisitionDate: Mapped[str]
    acquisitionCountry: Mapped[str]
    trainingStatus: Mapped[str]
    reserved: Mapped[bool]
    inServiceCountry: Mapped[str]

    __mapper_args__ = {
        "polymorphic_on": "animalType",
        "polymorphic_identity": "RescueAnimal",
    }


class Dog(RescueAnimal):
    __mapper_args__ = {
        "polymorphic_identity": "dog",
    }
    breed: Mapped[Optional[str]]

class Monkey(RescueAnimal):
    __mapper_args__ = {
        "polymorphic_identity": "monkey",
    }
    species: Mapped[Optional[str]]
    height: Mapped[Optional[str]]
    bodyLength: Mapped[Optional[str]]
    tailLength: Mapped[Optional[str]]
...
```
Additionally, a new class, User, was added, which was necessary for implementing login functionality and is mapped to the Users table:
```python
...
# Users table to hold users
class User(Base):
    __tablename__ = "Users"

    id: Mapped[int] = mapped_column(primary_key=True)
    name: Mapped[str] = mapped_column(String(30))
    fullname: Mapped[Optional[str]]
    saltedhash: Mapped[str] = mapped_column(String(32))

    def __repr__(self) -> str:
        return f"User(id={self.id!r}, name={self.name!r}, saltedhash={self.saltedhash!r})"
...
```
All database methods were decoupled and implemented in the DataBaseServices class, and the program’s main logic was implemented in a separate Driver class as well. To secure login credentials, I implemented PBKDF2 hashing to store users' passwords securely in our database, and added brute-force protection to the login functionality:
```python
...
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
...
 if (wrongUserCounter > 3 or wrongPasswordCounter > 3):
                # fix me
                # add 1 minutes wait time before trying again
                # store time in the database
                print("Too many attempts. Try again later in 1 minute!")
                time.sleep(60)
                wrongUserCounter = 0
                wrongPasswordCounter = 0
...
```

By implementing my enhancements, I achieved both of my planned outcomes, demonstrating my programming skills and ability to use the latest tools in computer science, as well as my security mindset in developing applications that ensure the privacy and enhanced security of data.

[Enhanced Code on GitHub](https://github.com/scenitnatsnoc/scenitnatsnoc.github.io/tree/main/artifact_one/enhanced_code)

### Reflection

During the process of enhancing and modifying the artifact, I learned new tools and libraries, such as SQLAlchemy, which gives application developers the full power and flexibility of SQL. I learned how to obtain and securely store credentials using PBKDF2 (Password-Based Key Derivation Function 2) with the help of another powerful library, hashlib.  Porting was challenging at first due to the differences between the two languages, but once I found the right tools, it became clearer, and the final solution became achievable.
