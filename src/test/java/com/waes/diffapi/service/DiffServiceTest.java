package com.waes.diffapi.service;

import com.waes.diffapi.domain.Diff;
import com.waes.diffapi.domain.calculator.DiffCalculator;
import com.waes.diffapi.domain.constant.Side;
import com.waes.diffapi.domain.dto.DiffRequest;
import com.waes.diffapi.repository.DiffReactiveRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class DiffServiceTest {

    @Mock
    private DiffReactiveRepository diffRepository;
    @Mock
    private DiffCalculator diffCalculator;

    @InjectMocks
    private ReactiveDiffService diffService;

    @Test
    @DisplayName("When diff does not exist expect findById empty and call calculate diff then save")
    void when_DiffDoesNotExist_expect_emptyReturnFromFindByIdCalculateDiff() {

        when(diffRepository.findById("1"))
                .thenReturn(Mono.empty());

        Diff diffBeforeCalc = Diff.builder()
                .id("1")
                .rightElement("dGVzdA==")
                .build();

        when(diffCalculator.calculate(diffBeforeCalc))
                .thenReturn(List.of("Both sides are required to calculate diff"));

        Diff diffToBeSaved = Diff.builder()
                .id("1")
                .rightElement("dGVzdA==")
                .insights(List.of("Both sides are required to calculate diff"))
                .build();

        when(diffRepository.save(diffToBeSaved))
                .thenReturn(Mono.just(diffToBeSaved));

        final DiffRequest request = Mockito.spy(new DiffRequest("dGVzdA=="));

        diffService.createOrUpdateDiff("1", request, Side.RIGHT);

        verify(diffCalculator, times(1))
                .calculate(diffBeforeCalc);
        verify(diffRepository, times(1))
                .save(diffToBeSaved);

    }


    @Test
    @DisplayName("When diff exists expect findById not empty and call calculate diff then save")
    void when_DiffExists_expect_nonEmptyReturnFromFindByIdCalculateDiff() {

        Diff foundDiff = Diff.builder()
                .id("1")
                .rightElement("dGVzdA==")
                .build();

        when(diffRepository.findById("1"))
                .thenReturn(Mono.just(foundDiff));

        Diff diffBeforeCalc = Diff.builder()
                .id("1")
                .leftElement("dGVzdA==")
                .rightElement("dGVzdA==")
                .build();

        when(diffCalculator.calculate(diffBeforeCalc))
                .thenReturn(List.of("Inputs are equal"));

        Diff diffToBeSaved = Diff.builder()
                .id("1")
                .leftElement("dGVzdA==")
                .rightElement("dGVzdA==")
                .insights(List.of("Inputs are equal"))
                .build();

        when(diffRepository.save(diffToBeSaved))
                .thenReturn(Mono.just(diffToBeSaved));

        final DiffRequest request = Mockito.spy(new DiffRequest("dGVzdA=="));

        diffService.createOrUpdateDiff("1", request, Side.LEFT);

        verify(diffCalculator, times(1))
                .calculate(diffBeforeCalc);
        verify(diffRepository, times(1))
                .save(diffToBeSaved);

    }

}
