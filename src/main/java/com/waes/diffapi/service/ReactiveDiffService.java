package com.waes.diffapi.service;

import com.waes.diffapi.domain.DiffCalculator;
import com.waes.diffapi.domain.Side;
import com.waes.diffapi.domain.dto.DiffRequest;

import com.waes.diffapi.domain.dto.DiffResponse;
import com.waes.diffapi.repository.DiffReactiveRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ReactiveDiffService implements DiffService {

    private DiffReactiveRepository diffRepository;
    private DiffCalculator calculator;

    @Override
    public void createOrUpdateDiff(final String id, final DiffRequest diffRequest, final Side side) {
        diffRepository.findById(id)
                .defaultIfEmpty(diffRequest.convertToDiff(id, side))
                .flatMap(d -> Mono.just(diffRequest.convertToDiff(id, side, d.toBuilder())))
                .flatMap(diffRepository::save)
                .filter(d -> d.getLeftElement() != null && d.getRightElement() != null)
                .flatMap(d -> Mono.just(d.toBuilder().result(calculator.process(d)).build()))
                .flatMap(diffRepository::save)
                .subscribe();
    }

    @Override
    public Mono<DiffResponse> getDiffById(String id) {
        return diffRepository.findById(id)
                .flatMap(d -> Mono.just(DiffResponse.builder()
                        .id(d.getId())
                        .left(d.getLeftElement())
                        .right(d.getRightElement())
                        .result(d.getResult())
                        .build()) );
    }

}
