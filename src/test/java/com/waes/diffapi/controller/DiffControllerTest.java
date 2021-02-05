package com.waes.diffapi.controller;

import com.waes.diffapi.domain.dto.DiffRequest;
import com.waes.diffapi.domain.dto.DiffResponse;
import com.waes.diffapi.service.DiffService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.List;

@AutoConfigureDataMongo
@WebFluxTest(DiffController.class)
public class DiffControllerTest {

    @MockBean
    private DiffService diffService;

    @Autowired
    private WebTestClient webClient;

    @Test
    @DisplayName("Given valid left diff element must return 201 created")
    void createLeftDiffElementMustReturn201Created() {

        DiffRequest request = new DiffRequest("dGVzdA==");

        webClient.post()
                .uri("/v1/diff/{id}/left", "1")
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isCreated();

    }

    @Test
    @DisplayName("Given valid right diff element must return 201 created")
    void createRightDiffElementMustReturn201Created() {

        DiffRequest request = new DiffRequest("dGVzdA==");

        webClient.post()
                .uri("/v1/diff/{id}/right", "1")
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isCreated();

    }


    @Test
    @DisplayName("Given an existing Diff with the specified id should return 200 OK")
    void mustReturn200Ok() {

        DiffResponse diffResponse = DiffResponse.builder().insights(List.of("Elements are equal")).build();
        Mono<DiffResponse> diffResponseMono = Mono.just(diffResponse);

        Mockito.when(diffService.getDiffById("1")).thenReturn(diffResponseMono);

        webClient.get()
                .uri("/v1/diff/{id}","1")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.insights").isArray();

    }

    @Test
    @DisplayName("Given a non-existent Diff with the specified id should return 404 Not Found")
    void mustReturn404NotFound() {

        Mockito.when(diffService.getDiffById("99")).thenReturn(Mono.empty());

        webClient.get()
                .uri("/v1/diff/{id}","99")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isNotFound();

    }

}
