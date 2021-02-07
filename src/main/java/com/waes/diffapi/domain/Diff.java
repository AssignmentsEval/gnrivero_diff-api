package com.waes.diffapi.domain;

import lombok.Builder;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * The class that represents contains all the diff
 * information: id, each side value and the result of the comparison itself.
 *
* */


@Value
@Builder(toBuilder = true)
@Document
public class Diff {

    @Id
    String id;
    String leftElement;
    String rightElement;
    List<String> insights;

}
