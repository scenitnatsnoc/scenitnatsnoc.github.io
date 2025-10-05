from typing import List
from typing import Optional
from sqlalchemy import ForeignKey
from sqlalchemy import String
from sqlalchemy.orm import DeclarativeBase
from sqlalchemy.orm import Mapped
from sqlalchemy.orm import mapped_column
from sqlalchemy.orm import relationship
from datetime import datetime

# database engine
from sqlalchemy import create_engine

class Base(DeclarativeBase):
    pass

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

# Users table to hold users
class User(Base):
    __tablename__ = "Users"

    id: Mapped[int] = mapped_column(primary_key=True)
    name: Mapped[str] = mapped_column(String(30))
    fullname: Mapped[Optional[str]]
    saltedhash: Mapped[str] = mapped_column(String(32))

    def __repr__(self) -> str:
        return f"User(id={self.id!r}, name={self.name!r}, saltedhash={self.saltedhash!r})"

# our database class
class DataBase:
    def __init__(self, database_file):
        self.engine = create_engine("sqlite:///" + database_file)
        # create tables
        Base.metadata.create_all(self.engine)

    # get the engine
    def getEngine(self):
        return self.engine
