from pymongo import MongoClient
from bson.objectid import ObjectId
from pprint import pprint

class QuantRMA(object):
    """ CRUD operations for rma collection in DAD220 MongoDB """

    def __init__(self, username, password):
        # Initializing the MongoClient. This helps to 
        # access the MongoDB databases and collections.
        # This is hard-wired to use the aac database, the 
        # animals collection, and the aac user.
        # Definitions of the connection string variables are
        # unique to the individual Apporto environment.
        #
        # You must edit the connection variables below to reflect
        # your own instance of MongoDB!
        #
        # Connection Variables
        #
        USER = username 
        PASS = password 
        HOST =  '127.0.0.1' 
        PORT =  27017       
        DB = 'DAD220-CS499'       
        COL = 'rma'         
        #
        # Initialize Connection
        #
        # self.client = MongoClient('mongodb://%s:%s@%s:%d' % (USER,PASS,HOST,PORT))
        self.client = MongoClient('mongodb://%s:%d' % (HOST,PORT))
        self.database = self.client['%s' % (DB)]
        self.collection = self.database['%s' % (COL)]

# Create method C in CRUD
    def create(self, data):
        result = False
        if data is not None:
            try:
                answer = self.collection.insert_one(data)  # data should be dictionary
                result = answer.acknowledged
            except TypeError:
                print("Data parameter has to be a dictionary")
        else:
            raise Exception("Nothing to save, because data parameter is empty")
        return result

# Read method R in CRUD.
    def read(self, data):
        result = []
        if data is not None:
            try:
                result = list(self.collection.find(data))  # data should be dictionary
                # print(result)
            except TypeError:
                print("Data parameter has to be a dictionary")
        else:
            raise Exception("Nothing to read, because data parameter is empty")
        return result

# Update method U in CRUD.
    def update(self, data_filter, data_match):
        if ((data_filter is not None) and (data_match is not None)):
            try:
                result = self.collection.update_many(data_filter, data_match)  # data should be dictionary
            except TypeError:
                print("Data parameter has to be a dictionary")
        else:
            raise Exception("Nothing to update, because data parameter is empty")
        return result.modified_count

# Delete method D in CRUD.
    def delete(self, data):
        if data is not None:
            try:
                result = self.collection.delete_many(data)  # data should be dictionary
            except TypeError:
                print("Data parameter has to be a dictionary")
        else:
            raise Exception("Nothing to delete, because data parameter is empty")
        return result.deleted_count
        
# Extra aggregate method for future use.
    def agg(self, data):
        if data is not None:
            try:
                result = self.collection.aggregate(data)  # data should be dictionary
            except TypeError:
                print("Data parameter has to be a dictionary")
        else:
            raise Exception("Nothing to aggregate, because data parameter is empty")
        return result
