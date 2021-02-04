package com.waes.diffapi.service;

import com.waes.diffapi.domain.calculator.DiffCalculator;
import com.waes.diffapi.domain.constant.Side;
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
    private DiffCalculator diffCalculator;

    @Override
    public void createOrUpdateDiff(final String id, final DiffRequest diffRequest, final Side side) {
        diffRepository.findById(id)
                .defaultIfEmpty(diffRequest.convertToDiff(id, side))
                .flatMap(d -> Mono.just(diffRequest.convertToDiff(id, side, d.toBuilder())))
                .flatMap(d -> Mono.just(d.toBuilder().insights(diffCalculator.calculate(d)).build()))
                .flatMap(diffRepository::save)
                .subscribe();
    }

    @Override
    public Mono<DiffResponse> getDiffById(final String id) {
        return diffRepository.findById(id)
                .flatMap(d -> Mono.just(DiffResponse.builder()
                        .insights(d.getInsights())
                        .build()));
    }

}
