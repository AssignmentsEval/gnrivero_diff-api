package com.waes.diffapi.domain.dto;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
@Builder
public class DiffResponse implements Serializable {

    List<String> insight;

}
