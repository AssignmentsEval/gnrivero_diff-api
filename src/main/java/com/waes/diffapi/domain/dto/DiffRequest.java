package com.waes.diffapi.domain.dto;

import com.waes.diffapi.domain.Diff;
import com.waes.diffapi.domain.Side;
import lombok.Data;
import java.io.Serializable;

@Data
public class DiffRequest implements Serializable {
    private static final long serialVersionUID = 1429063558898305883L;
    String value;

    public Diff convertToDiff(String id, Side side) {
        return convertToDiff(id, side, Diff.builder());
    }

    public Diff convertToDiff(String id, Side side, Diff.DiffBuilder diffBuilder){

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
