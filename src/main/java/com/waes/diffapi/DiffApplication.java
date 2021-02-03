package com.waes.diffapi;

import com.waes.diffapi.domain.Diff;
import com.waes.diffapi.repository.DiffReactiveRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.core.publisher.Flux;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class DiffApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiffApplication.class, args);
	}

	@Bean
	ApplicationRunner init(DiffReactiveRepository repository) {

		Object[][] data = {
				{"1", "dGVzdGZvcm15d2Flc2ZyaWVuZHM=", null },
				{"2", "dGVzdGZvcm15d2Flc2ZyaWVuZHM=","dGVzdGZvcm15d2Flc2ZyaWVuZHM="},
				{"3", "dGVzdGZvcm15d2Flc2ZyaWVuZHM=", null}
		};

		return args -> {
			repository
					.deleteAll()
					.thenMany(
							Flux
									.just(data)
									.map(array -> {
										return Diff.builder().id(((String) array[0])).leftElement((String) array[1]).rightElement((String) array[2]).build();
									})
									.flatMap(repository::save)
					)
					.thenMany(repository.findAll())
					.subscribe(kayak -> System.out.println("saving " + kayak.toString()));
		};
	}

}
