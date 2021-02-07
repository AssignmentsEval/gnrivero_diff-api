package com.waes.diffapi.service;

import com.waes.diffapi.domain.Diff;
import com.waes.diffapi.domain.constant.Side;
import com.waes.diffapi.domain.dto.DiffRequest;
import com.waes.diffapi.domain.dto.DiffResponse;
import reactor.core.publisher.Mono;

public interface DiffService {

    Mono<Diff> createOrUpdateDiff(String id, DiffRequest diffRequest, Side side);

    Mono<DiffResponse> getDiffById(String id);

}
