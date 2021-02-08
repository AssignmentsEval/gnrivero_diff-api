package com.waes.diffapi.domain.dto;

import com.waes.diffapi.domain.Diff;
import com.waes.diffapi.domain.constant.Side;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Represents an abstraction and encapsulates information
 * received by DiffController and passed to Service layer
 *
 * This class has a method to get a Diff from its data.
 * It's a class responsible to transport data from controller layer to
 * service layer and has the knowledge to know how to create a Diff object
 * from its data.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiffRequest implements Serializable {
    private static final long serialVersionUID = 1729391026156757787L;

    String value;

    public Diff convertToDiff(String id, Side side) {
        return convertToDiff(id, side, Diff.builder());
    }

    public Diff convertToDiff(String id, Side side, Diff.DiffBuilder diffBuilder) {

        diffBuilder.id(id);

        if (Side.LEFT.equals(side)) {
            diffBuilder.leftElement(this.value);
        }

        if (Side.RIGHT.equals(side)) {
            diffBuilder.rightElement(this.value);
        }

        return diffBuilder.build();
    }
}
