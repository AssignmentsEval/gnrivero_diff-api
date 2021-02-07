package com.waes.diffapi.helper;

import com.waes.diffapi.domain.dto.DiffRequest;

public final class DiffRequestHelper {

    public static DiffRequest getDiffRequest() {
        return new DiffRequest("dGVzdA==");
    }

    public static DiffRequest getDiffRequestWithCustomValue(String value) {
        return new DiffRequest(value);
    }

}
