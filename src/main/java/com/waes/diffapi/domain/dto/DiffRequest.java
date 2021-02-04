package com.waes.diffapi.domain.dto;

import com.waes.diffapi.domain.Diff;
import com.waes.diffapi.domain.constant.Side;
import lombok.Data;
import java.io.Serializable;

@Data
public class DiffRequest implements Serializable {
    private static final long serialVersionUID = 1729391026156757787L;

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
