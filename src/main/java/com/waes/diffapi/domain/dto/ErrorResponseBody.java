package com.waes.diffapi.domain.dto;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Value
@Builder
public class ErrorResponseBody implements Serializable {
    private static final long serialVersionUID = -1362718802183142004L;

    String errorDetail;

}
