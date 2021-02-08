package com.waes.diffapi.service;

import com.waes.diffapi.domain.Diff;
import com.waes.diffapi.domain.constant.Side;
import com.waes.diffapi.domain.dto.DiffRequest;
import com.waes.diffapi.domain.dto.DiffResponse;
import reactor.core.publisher.Mono;

/**
 * Diff Service abstraction to make use of spring dependency injection.
 *
 * For now this service has only one implementation, but this interface
 * helps in SOLID principles application.
 *
 * */
public interface DiffService {

    Mono<Diff> createOrUpdateDiff(String id, DiffRequest diffRequest, Side side);

    Mono<DiffResponse> getDiffById(String id);

}
