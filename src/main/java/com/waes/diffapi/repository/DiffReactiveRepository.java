package com.waes.diffapi.repository;

import com.waes.diffapi.domain.Diff;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiffReactiveRepository extends ReactiveMongoRepository<Diff, String> {

}
