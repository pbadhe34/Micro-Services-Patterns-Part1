package com.user;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MongoBsonFilterQuery
{
   public static void main(String[] args)
   {
      //
      // 4.1 Connect to cluster (default is localhost:27017)
      //

      MongoClient mongoClient = MongoClients.create();
      MongoDatabase database = mongoClient.getDatabase("dev");
      //Create or get into collection
      MongoCollection<Document> collection = database.getCollection("employees");
     System.out.println("The collection is "+collection);
      
      // 4.2 Insert new document     

      Document employee = new Document()
                             .append("first_name", "Tre")
                             .append("last_name", "Bgst")
                             .append("title", "Java Programmer")
                             .append("years_of_service", 3)
                             .append("skills", Arrays.asList("java", "spring", "mongodb"))
                             .append("manager", new Document()
                                                   .append("first_name", "Sally")
                                                   .append("last_name", "Johanson"));
      collection.insertOne(employee);

      System.out.println("The doc inserted "+employee);
      
      //Find documents
      List results = new ArrayList<>();
      
      collection.find().into(results);

      System.out.println("Number of records are  "+results.size());     
      

      Document query = new Document("last_name", "Bava");
      results = new ArrayList<>();
      collection.find(query).into(results);
      
      System.out.println("The first query result is "+results.size());

      query =
         new Document("$or", Arrays.asList(
            new Document("last_name", "Bava"),
            new Document("first_name", "Ching2")));//case sensitive
      
      results = new ArrayList<>();
      
      collection.find(query).into(results);
      
      System.out.println("The second query result is "+results.size());
     
      //Find and Update document     

      query = new Document(
         "skills",
         new Document(
            "$elemMatch",
            new Document("$eq", "spring")));
      
      Document update = new Document(
         "$push",
         new Document("skills", "cyber-security"));
      
      UpdateResult res = collection.updateMany(query, update);
      
      System.out.println("The query update result is "+res);

      ///find and update KK with 5
     query = new Document("last_name", "KK");
    	      
     update = new Document(
    		 "$set",
    		 new Document("years_of_service", 5));
    	      
   res = collection.updateMany(query, update);
    	      
   System.out.println("The second  update result is "+res);
      
      //
      
      //Find  documents    having  years_of_service = 3
      
      query = new Document("years_of_service", 1);
      
      results = new ArrayList<>();
      
      collection.find(query).into(results);
      
      System.out.println("The docs with specified value are "+results.size());
      
      
    //Find  documents  having  years_of_service > 2
      
      query = new Document(
    	         "years_of_service",    	          
    	            new Document("$eq", 5));   
       
      
      results = new ArrayList<>();
      
      Document found = collection.find(query).first();
      System.out.println("The document found with  value is "+found.getString("last_name"));
      
       collection.find(query).into(results);
      
      System.out.println("The docs with conditional value are "+results.size());
      
      //Find and Delete documents     
      query = new Document("last_name", "Choutu");
      DeleteResult resd= collection.deleteMany(query);     
 
      
      System.out.println("The query delete result is "+resd.getDeletedCount());

   }
}
