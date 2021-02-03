package com.waes.diffapi.domain.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DiffResponse {

    String id;
    String left;
    String right;
    String result;

}
