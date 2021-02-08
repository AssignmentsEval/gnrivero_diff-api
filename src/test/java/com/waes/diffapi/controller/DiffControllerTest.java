package com.waes.diffapi.controller;

import com.waes.diffapi.domain.Diff;
import com.waes.diffapi.domain.constant.Side;
import com.waes.diffapi.domain.dto.DiffRequest;
import com.waes.diffapi.helper.DiffHelper;
import com.waes.diffapi.helper.DiffRequestHelper;
import com.waes.diffapi.helper.DiffResponseHelper;
import com.waes.diffapi.service.DiffService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;

@ExtendWith(MockitoExtension.class)
public class DiffControllerTest {

    @Mock
    private DiffService diffService;
    @InjectMocks
    DiffController diffController;

    private final DiffRequest validRequest = DiffRequestHelper.getDiffRequest();

    @Test
    @DisplayName("When left diff request is provided method must return 201 created")
    void when_validLeftDiffRequestIsPassed_mustComplete() {

        Diff leftDiff = DiffHelper.getLeftDiff();

        Mockito.when(diffService.createOrUpdateDiff("1", validRequest, Side.LEFT))
                .thenReturn(Mono.just(leftDiff));

        ResponseEntity<Object> expectedResponse = ResponseEntity.created(URI.create("/v1/diff/1")).build();

        StepVerifier.create(diffController.upsertDiffData("1", "left", validRequest))
            .expectSubscription()
            .expectNext(expectedResponse)
            .verifyComplete();

    }

    @Test
    @DisplayName("When right diff request is provided method must return 201 created")
    void when_validRightDiffRequestIsPassed_mustComplete() {

        Diff rightDiff = DiffHelper.getRightDiff();

        Mockito.when(diffService.createOrUpdateDiff("1", validRequest, Side.RIGHT))
                .thenReturn(Mono.just(rightDiff));

        ResponseEntity<Object> expectedResponse = ResponseEntity.created(URI.create("/v1/diff/1")).build();

        StepVerifier.create(diffController.upsertDiffData("1", "right", validRequest))
                .expectSubscription()
                .expectNext(expectedResponse)
                .verifyComplete();

    }

    @Test
    @DisplayName("When requested diff exists must return 200 OK")
    void when_requestedDiffExists_mustComplete() {

        Mockito.when(diffService.getDiffById("1"))
                .thenReturn(Mono.just(DiffResponseHelper.getEqualDiffResponse()));

        StepVerifier.create(diffController.getDiff("1"))
                .expectSubscription()
                .expectNext(ResponseEntity.ok(DiffResponseHelper.getEqualDiffResponse()))
                .verifyComplete();

    }

    @Test
    @DisplayName("When requested diff does not exists must return not found")
    void when_requestedDiffDoesNotExist_mustComplete() {

        Mockito.when(diffService.getDiffById("1"))
                .thenReturn(Mono.empty());

        StepVerifier.create(diffController.getDiff("1"))
                .expectSubscription()
                .expectNext(ResponseEntity.notFound().build())
                .verifyComplete();

    }

}
