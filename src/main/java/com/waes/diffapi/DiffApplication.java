package com.waes.diffapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class DiffApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiffApplication.class, args);
	}

}
