package com.waes.diffapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;


/**
 * Diff Application
 *
 *
 * This application is implemented using Spring Boot and MongoDB.
 *
 * The main features included (with scalability in mind) are:
 *
 * WebFlux: The Spring reactive web framework that makes a more efficient use of threads and memory,
 * improving requests processing and enhancing application scalability. WebFlux offers a non-blocking web
 * stack and handles concurrency in background using Reactor library. Avoiding the use of one thread for an entire
 * request (from controller to repository layer operations) but instead using different ones for different processing
 * stages allows the application to process more requests in a very efficient way.
 *
 * Embedded MongoDB: To keep the advantage gained with WebFlux and Spring's reactive stack, I decided to use
 * a embedded MongoDB no-SQL database. As object's relationship wasn't the main focus of this application
 * and SQL databases work in a blocking/transactional way I thought that a reactive MongoDB would be a much better fit.
 * Disclaimer: This is not a production implementation, if so, test and productive database config should be in
 * different configuration (properties or yml) files and or Spring profiles to distinguish both.
 *
 * Mongo Reactive Repositories: instead of a MongoTemplate I decided to use the ReactiveRepository
 * approach. The main reasons are ease of use, implementation and requires less configuration.
 * Besides, functions provided by ReactiveRepository were enough for the functionalities I needed, customization
 * in search by certain fields is supported.
 *
 * Utils:
 * ----
 *
 * Jacoco: To keep track of the application code coverage.
 * Lombok: To avoid boilerplate code and endless getters and setters. Another important feature is the
 * Builder/Value annotations that ensure object immutability and a clearer and concise object construction
 * syntax.
 *
 * Suggestions for improvement:
 *
 * Endpoint signatures. You can ask the clients for an object containing all necessary info like id, side, and value.
 * You can this way use a POST method to create a new Diff Side resource. To update it the API can apply a patch method
 * to update only value field given a certain Id.
 *
 *
 *
 *
 */
@SpringBootApplication
@EnableReactiveMongoRepositories
public class DiffApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiffApplication.class, args);
	}

}
