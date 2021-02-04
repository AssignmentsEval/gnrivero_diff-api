package com.waes.diffapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Value
@Builder(toBuilder = true)
@Document
public class Diff {

    @Id
    String id;
    String leftElement;
    String rightElement;
    List<String> insight;

    @Getter
    @AllArgsConstructor
    public enum Result {
        EQUAL("Inputs are equal"),
        NOT_EQUAL_SIZE("Input sizes are not equal"),
        EQUAL_SIZE_DIFFERENT_CONTENT("Result: "),
        ONE_SIDE_MISSING("Both sides are required to calculate diff");

        String message;
    }

}
