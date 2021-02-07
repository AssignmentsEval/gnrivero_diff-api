package com.waes.diffapi.integration;

import com.waes.diffapi.domain.dto.DiffRequest;
import com.waes.diffapi.domain.dto.DiffResponse;
import com.waes.diffapi.helper.DiffRequestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootTest
@AutoConfigureWebTestClient
public class DiffIntegrationTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    @DisplayName("When valid left diff element is created must return 201 created")
    void when_leftDiffElementIsCreated_mustReturn201Created() {

        DiffRequest request = DiffRequestHelper.getDiffRequest();

        webClient.post()
                .uri("/v1/diff/{id}/left", "1")
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isCreated();

    }

    @Test
    @DisplayName("When valid right diff element is created must return 201 created")
    void when_rightDiffElementIsCreated_mustReturn201Created() {

        DiffRequest request = DiffRequestHelper.getDiffRequest();

        webClient.post()
                .uri("/v1/diff/{id}/right", "1")
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isCreated();

    }


    @Test
    @DisplayName("When a Diff with the specified id exists should return 200 OK")
    void when_diffIsFound_mustReturn200Ok() {

        DiffResponse diffResponse = DiffResponse.builder().insights(List.of("Elements are equal")).build();
        Mono<DiffResponse> diffResponseMono = Mono.just(diffResponse);

        //Mockito.when(diffService.getDiffById("1")).thenReturn(diffResponseMono);

        webClient.get()
                .uri("/v1/diff/{id}","1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.insights").isArray();

    }

    @Test
    @DisplayName("When a Diff with the specified id doesn't exist should return 404 Not Found")
    void when_diffElementIsNotFound_mustReturn404NotFound() {

        //Mockito.when(diffService.getDiffById("99")).thenReturn(Mono.empty());

        webClient.get()
                .uri("/v1/diff/{id}","99")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isNotFound();

    }

}
