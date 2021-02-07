package com.waes.diffapi.helper;

import com.waes.diffapi.domain.dto.DiffResponse;

import java.util.List;

public final class DiffResponseHelper {
    
    public static DiffResponse getEqualDiffResponse() {
        return DiffResponse.builder()
                .insights(List.of("Inputs are equal"))
                .build();
    }
    
}
