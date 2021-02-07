package com.waes.diffapi.controller;

import com.waes.diffapi.domain.Diff;
import com.waes.diffapi.domain.constant.Side;
import com.waes.diffapi.domain.dto.DiffRequest;

import com.waes.diffapi.domain.dto.DiffResponse;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/diff")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DiffController {

    private DiffService diffService;

    @PostMapping(value = "/{id}/left", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<Diff> createLeftInput(@PathVariable String id, @RequestBody DiffRequest diffRequest) {
        return diffService.createOrUpdateDiff(id, diffRequest, Side.LEFT);
    }

    @PostMapping(value = "/{id}/right", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<Diff> createRightInput(@PathVariable String id, @RequestBody DiffRequest diffRequest) {
        return diffService.createOrUpdateDiff(id, diffRequest, Side.RIGHT);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DiffResponse>> getDiff(@PathVariable String id) {
        return diffService.getDiffById(id)
                .flatMap(dr -> Mono.just(ResponseEntity.ok(dr)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

}
