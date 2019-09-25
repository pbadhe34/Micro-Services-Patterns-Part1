package com.user.tagging;

import static dev.morphia.aggregation.Group.grouping;
import static dev.morphia.aggregation.Group.push;
 
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.user.morphia.domain.Author;
import com.user.morphia.domain.Book;
import com.user.morphia.domain.Publisher;

import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.query.Query;
import dev.morphia.query.UpdateOperations;

public class MorphiaClient {

	 private static Datastore datastore;
	    private static ObjectId id = new ObjectId();

	public static void main(String[] args) {
		Morphia morphia = new Morphia();
		//package scanning for entity and other annotations
        morphia.mapPackage("com.user.morphia");
        datastore = morphia.createDatastore(new MongoClient(), "library");
        datastore.ensureIndexes();
        
        //add records
        Publisher publisher = new Publisher(id, "Tata Publisher");
        Book book = new Book("981565927186", "Learning Java", "Yash Kanitkar", 2.33, publisher);
        Book companionBook = new Book("9789332575103", "Java Performance", "Baba Kadam", 1.95, publisher);
        book.addCompanionBooks(companionBook);
        datastore.save(companionBook);
        datastore.save(book);
        
        List<Book> books = datastore.createQuery(Book.class)
                .field("title")
                .contains("Learning Java")
                .find()
                .toList();
        System.out.println("The books added "+books.size());
        System.out.println("The books cost is ");
        
        
        
        Iterator<Book> bookIter = books.iterator();
        
        while(bookIter.hasNext()) {
        	System.out.println(bookIter.next().getCost());
        	 
        	
        }
        //Search Query
        Query<Book> query = datastore.createQuery(Book.class)
                .field("title")
                .contains("Learning Java");
            UpdateOperations<Book> updates = datastore.createUpdateOperations(Book.class)
                .inc("price", 1.2);
            //update operation
            datastore.update(query, updates);
            List<Book> booksUpdated = datastore.createQuery(Book.class)
                .field("title")
                .contains("Learning Java")
                .find()
                .toList();
            
            System.out.println("The books  updated are "+booksUpdated.size());
            
            //verify updates done           
            bookIter = booksUpdated.iterator();
            
            while(bookIter.hasNext()) {
            	System.out.println(bookIter.next().getCost());
            	 
            	
            }
            //Delete operation
            
            query = datastore.createQuery(Book.class)
                    .field("title")
                    .contains("Learning Java");
            
            WriteResult res = datastore.delete(query);
           
            System.out.println("Books deleted result is  "+res);
            
            //Aggregration and grouping
            
            publisher = new Publisher(id, "Awsome Publisher");
            //create and svae books
            datastore.save(new Book("9781565927186", "Learning Java", "Tom Kirkman", 3.95, publisher));
            datastore.save(new Book("9781449313142", "Learning Perl", "Mark Pence", 2.95, publisher));
            datastore.save(new Book("9787564100476", "Learning Python", "Mark Pence", 5.95, publisher));
            datastore.save(new Book("9781449368814", "Learning Scala", "Mark Pence", 6.95, publisher));
            datastore.save(new Book("9781784392338", "Learning Go", "Jonathan Sawyer", 8.95, publisher));

            Iterator<Author> authors = datastore.createAggregation(Book.class)
                .group("author", grouping("books", push("title")))
                .out(Author.class);

            System.out.println("Authors list group by");
            while(authors.hasNext()) {
            	System.out.println(authors.next().getName());           	 
            	
            }
            
            //Projection with Title only
            
            publisher = new Publisher(id, "Awsome Publisher");
            book = new Book("9781565927186", "Learning Java", "Tom Kirkman", 3.95, publisher);
            datastore.save(book);
            
            List<Book> bookList = datastore.createQuery(Book.class)
                .field("title")
                .contains("Learning Java")
                .project("title", true)
                //.project("author", true)
                .find()
                .toList();
            
            System.out.println("The books with title projection are "+bookList.size());
             
            
            System.out.println("The books with title projection title value is  "+bookList.get(0).getTitle());
            
            //Author is NOT projected,hence null 
            System.out.println("The books with title projection author value is  "+bookList.get(0).getAuthor());
            
	}

}
