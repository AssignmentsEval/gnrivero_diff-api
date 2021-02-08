package com.waes.diffapi.controller;

import com.waes.diffapi.domain.constant.Side;
import com.waes.diffapi.domain.dto.DiffRequest;

import com.waes.diffapi.domain.dto.DiffResponse;
import com.waes.diffapi.domain.dto.ErrorResponseBody;
import com.waes.diffapi.domain.exception.InvalidDataValueException;
import com.waes.diffapi.service.DiffService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


import java.net.URI;
import java.util.Optional;


/**
 * Diff Controller
 *
 * It's the entrypoint which the application communicates with other APIs.
 *
 * upsertDiffData method validates data input preventing empty data to be added
 * then calls the service method to save and process diff operations
 *
 * As json input is a restriction for this application I specified the type of data
 * the application consumes and produces.
 *
 * Create Input methods : Returns "201 created" if creation is successful, otherwise returns "400 bad request".
 * Get Diff: Returns 200 OK if diff is found, otherwise returns "404 not found".
 *
 */
@RestController
@RequestMapping("/v1/diff")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DiffController {

    private DiffService diffService;

    @PostMapping(value = "/{id}/{diffSide:left|right}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> upsertDiffData(
            @PathVariable final String id,
            @PathVariable final String diffSide,
            @RequestBody final DiffRequest diffRequest) {

        return Mono.just(diffRequest)
                .flatMap( dr -> {
                        Optional<String> value = Optional.ofNullable(dr.getValue());
                        if(value.isPresent() && !value.get().isEmpty()) {
                            return Mono.just(dr);
                        }
                        throw new InvalidDataValueException();
                })
                .flatMap( dr -> diffService.createOrUpdateDiff(id, dr, Side.from(diffSide)))
                .flatMap(d -> Mono.just(ResponseEntity
                        .created(URI.create(String.format("/v1/diff/%s", id)))
                        .build()))
                .onErrorResume(InvalidDataValueException.class, e -> Mono.just(
                        ResponseEntity.badRequest().body(ErrorResponseBody.builder()
                                .errorDetail(e.getMessage())
                                .build())));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DiffResponse>> getDiff(@PathVariable String id) {
        return diffService.getDiffById(id)
                .flatMap(dr -> Mono.just(ResponseEntity.ok(dr)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

}
