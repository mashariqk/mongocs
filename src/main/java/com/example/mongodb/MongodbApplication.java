package com.example.mongodb;

import com.example.mongodb.subscriber.PrintDocumentSubscriber;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.FullDocument;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class MongodbApplication implements CommandLineRunner {

	@Value("${watch.cursor.month:January}")
	private String birthDayMonth;

	public static void main(String[] args) {
		SpringApplication.run(MongodbApplication.class, args);
	}

	/**
	 * Callback used to run the bean.
	 *
	 * @param args incoming main method arguments
	 * @throws Exception on error
	 */
	@Override
	public void run(String... args) throws Exception {
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		MongoDatabase database = mongoClient.getDatabase("test");
		MongoCollection<Document> collection = database.getCollection("customers");
		List<Bson> pipeline = Collections.singletonList(
				Aggregates.match(
						Filters.and(Document.parse(String.format("{'fullDocument.birthDayMonth':'%s'}",birthDayMonth)),
								Filters.in("operationType",Arrays.asList("insert", "update", "replace", "delete")))
				)
		);
		collection.watch(pipeline).fullDocument(FullDocument.UPDATE_LOOKUP).subscribe(new PrintDocumentSubscriber());
	}
}
