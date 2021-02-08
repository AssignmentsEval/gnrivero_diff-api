package com.waes.diffapi.service;

import com.waes.diffapi.domain.Diff;
import com.waes.diffapi.domain.calculator.DiffCalculator;
import com.waes.diffapi.domain.constant.Side;
import com.waes.diffapi.domain.dto.DiffRequest;

import com.waes.diffapi.domain.dto.DiffResponse;
import com.waes.diffapi.repository.DiffReactiveRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


/**
 * Diff Service implementation.
 *
 * Creation or Update of Diff objects
 *
 * This class is responsible for creating/updating a Diff object/document. Diff is computed
 * at creation time before being saved. If a Diff doesn't exist a method returns a new Diff
 * object to be computed. If it does the selected side value is set and then the diff over
 * both sides is computed. Finally the object is saved.
 *
 *
 * Retrieving a Diff result
 *
 * This class is also responsible for calling repository methods to retrieve a Diff object
 * from the database, convert it to DiffResponse object and return it.
 *
* */
@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ReactiveDiffService implements DiffService {

    private DiffReactiveRepository diffRepository;
    private DiffCalculator diffCalculator;

    @Override
    public Mono<Diff> createOrUpdateDiff(final String id, final DiffRequest diffRequest, final Side side) {
        return diffRepository.findById(id)
                .defaultIfEmpty(diffRequest.convertToDiff(id, side))
                .flatMap(d -> Mono.just(diffRequest.convertToDiff(id, side, d.toBuilder())))
                .flatMap(d -> Mono.just(d.toBuilder().insights(diffCalculator.calculate(d)).build()))
                .flatMap(diffRepository::save);
    }

    @Override
    public Mono<DiffResponse> getDiffById(final String id) {
        return diffRepository.findById(id)
                .flatMap(d -> Mono.just(DiffResponse.builder()
                        .insights(d.getInsights())
                        .build()));
    }

}
