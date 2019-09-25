package com.user;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

public class MongoDBReadWriteExample {
    public static void main(String[] args) {

        MongoClient mongoClient = new MongoClient("localhost", 27017);

        DB database = mongoClient.getDB("dev");
       // MongoDatabase database = mongoClient.getDatabase("dev");

        // print existing database names
        
        System.out.println("The database names");
        
       // MongoIterable<String> listDB = mongoClient.listDatabaseNames()
        		 
       mongoClient.getDatabaseNames().forEach(System.out::println);

        database.createCollection("customers", null);

        System.out.println("The collections in dev db");
        // print all collections in customers database
        database.getCollectionNames().forEach(System.out::println);

        // create new collection nad add data
        DBCollection collection = database.getCollection("customers");
        BasicDBObject document = new BasicDBObject();
        document.put("name", "Shubham");
        document.put("company", "Oryo");
        collection.insert(document);

        System.out.println("The read and update in dev db");
        // update data
        BasicDBObject query = new BasicDBObject();
        query.put("name", "Shubham");
        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("name", "John");
        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", newDocument);
        collection.update(query, updateObject);

        System.out.println("The read updated in dev db");
        // read data
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("name", "John");
        DBCursor cursor = collection.find(searchQuery);
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }

       
        // delete data
        BasicDBObject deleteQuery = new BasicDBObject();
        deleteQuery.put("name", "John");
        WriteResult res = collection.remove(deleteQuery);
        System.out.println("The delete in dev db "+res);
    }
}
