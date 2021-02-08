package com.waes.diffapi.integration;

import com.waes.diffapi.domain.dto.DiffRequest;
import com.waes.diffapi.helper.DiffRequestHelper;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiffIntegrationTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    @DisplayName("When left diff element is created must return 201 created")
    void when_leftDiffElementIsCreated_mustReturn201Created() {

        DiffRequest request = DiffRequestHelper.getDiffRequest();

        webClient.post()
                .uri("/v1/diff/{id}/left", "1")
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isCreated();

    }

    @Test
    @DisplayName("When right diff element is created must return 201 created")
    void when_rightDiffElementIsCreated_mustReturn201Created() {

        DiffRequest request = DiffRequestHelper.getDiffRequest();

        webClient.post()
                .uri("/v1/diff/{id}/right", "1")
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isCreated();

    }

    @Test
    @DisplayName("When right diff element is created must return 400 bad request")
    void when_rightDiffElementIsEmpty_mustReturn400BadRequest() {

        DiffRequest nullRequest = DiffRequestHelper.getDiffRequestWithCustomValue(null);

        webClient.post()
                .uri("/v1/diff/{id}/right", "1")
                .body(BodyInserters.fromValue(nullRequest))
                .exchange()
                .expectStatus().isBadRequest();

        DiffRequest emptyRequest = DiffRequestHelper.getDiffRequestWithCustomValue("");

        webClient.post()
                .uri("/v1/diff/{id}/right", "1")
                .body(BodyInserters.fromValue(emptyRequest))
                .exchange()
                .expectStatus().isBadRequest();

    }

    @Test
    @DisplayName("When left diff element is created must return 400 bad request")
    void when_leftDiffElementIsEmpty_mustReturn400BadRequest() {

        DiffRequest nullRequest = DiffRequestHelper.getDiffRequestWithCustomValue(null);

        webClient.post()
                .uri("/v1/diff/{id}/left", "1")
                .body(BodyInserters.fromValue(nullRequest))
                .exchange()
                .expectStatus().isBadRequest();

        DiffRequest emptyRequest = DiffRequestHelper.getDiffRequestWithCustomValue("");

        webClient.post()
                .uri("/v1/diff/{id}/left", "1")
                .body(BodyInserters.fromValue(emptyRequest))
                .exchange()
                .expectStatus().isBadRequest();

    }

    @Test
    @DisplayName("When a diff element exists must return 200 OK")
    void when_diffIsFound_mustReturn200Ok() {

        DiffRequest request = DiffRequestHelper.getDiffRequest();

        webClient.post()
                .uri("/v1/diff/{id}/right", "1")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody();

        webClient.get()
                .uri("/v1/diff/{id}","1")
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    @DisplayName("When a diff doesn't exist must return 404 Not Found")
    void when_diffElementIsNotFound_mustReturn404NotFound() {

        webClient.get()
                .uri("/v1/diff/{id}","99")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isNotFound();

    }

    @Test
    @DisplayName("When a diff exists and has only one side should return message")
    void when_diffHasOnlyOneSide_mustReturnMessageIndicatingSo() {

        DiffRequest request = DiffRequestHelper.getDiffRequest();

        webClient.post()
                .uri("/v1/diff/{id}/right", "1")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated();


        webClient.get()
                .uri("/v1/diff/{id}","1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.insights").isEqualTo("Both sides are required to calculate diff");
    }

    @Test
    @DisplayName("When a diff exists and elements are equal return message")
    void when_diffIsEqual_mustReturnMessageIndicatingSo() {

        DiffRequest request = DiffRequestHelper.getDiffRequest();

        webClient.post()
                .uri("/v1/diff/{id}/right", "1")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated();

        webClient.post()
                .uri("/v1/diff/{id}/left", "1")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated();

        webClient.get()
                .uri("/v1/diff/{id}","1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.insights").isEqualTo("Inputs are equal");
    }

    @Test
    @DisplayName("When a diff exists and has different size elements return message")
    void when_diffIsDifferentSize_mustReturnMessageIndicatingSo() {

        DiffRequest rightRequest = DiffRequestHelper.getDiffRequest();
        DiffRequest leftRequest = DiffRequestHelper.getDiffRequestWithCustomValue("dGVzdHRlc3Q=");

        webClient.post()
                .uri("/v1/diff/{id}/right", "1")
                .bodyValue(rightRequest)
                .exchange()
                .expectStatus().isCreated();

        webClient.post()
                .uri("/v1/diff/{id}/left", "1")
                .bodyValue(leftRequest)
                .exchange()
                .expectStatus().isCreated();

        webClient.get()
                .uri("/v1/diff/{id}","1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.insights").isEqualTo("Input sizes are not equal");
    }

    @Test
    @DisplayName("When diff exists and has elements with different content return offset and length")
    void when_diffHasDifferentContent_mustReturnMessageIndicatingSo() {

        DiffRequest rightRequest = DiffRequestHelper.getDiffRequestWithCustomValue("dGVzdHRlc3R0ZXN0dGVzdHRlc3Q=");
        DiffRequest leftRequest = DiffRequestHelper.getDiffRequestWithCustomValue("dGV4eHRlc3R4eHh0dGVzeHRlc3Q=");

        webClient.post()
                .uri("/v1/diff/{id}/right", "1")
                .bodyValue(rightRequest)
                .exchange()
                .expectStatus().isCreated();

        webClient.post()
                .uri("/v1/diff/{id}/left", "1")
                .bodyValue(leftRequest)
                .exchange()
                .expectStatus().isCreated();

        JSONArray expectedResponse = new JSONArray();
        expectedResponse.appendElement("Offset: 3. Length: 2")
                .appendElement("Offset: 9. Length: 3")
                .appendElement("Offset: 16. Length: 1");

        webClient.get()
                .uri("/v1/diff/{id}","1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.insights").isEqualTo(expectedResponse);
    }

}
