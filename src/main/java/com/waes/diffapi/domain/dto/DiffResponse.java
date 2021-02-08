package com.waes.diffapi.domain.dto;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.List;


/**
 * A class to represent the response given to the client when
 * queries for Diff results
 */
@Value
@Builder
public class DiffResponse implements Serializable {
    private static final long serialVersionUID = 5885659661395926957L;

    List<String> insights;

}
