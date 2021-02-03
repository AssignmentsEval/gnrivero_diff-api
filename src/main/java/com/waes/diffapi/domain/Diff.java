package com.waes.diffapi.domain;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Builder(toBuilder = true)
@Document
public class Diff {

    @Id
    String id;
    String leftElement;
    String rightElement;
    String result;

}
