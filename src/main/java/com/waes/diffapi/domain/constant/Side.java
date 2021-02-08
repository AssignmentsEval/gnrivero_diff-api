package com.waes.diffapi.domain.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum to represent a Side in Diff object.
 *
 * It becomes helpful in the value assignment for a specified side of the diff.
 *
 */
public enum Side {
    LEFT("left"),
    RIGHT("right");

    private static class Holder {
        private static final Map<String, Side> SIDE_BY_LITERAL = new HashMap<>();
    }

    private final String code;

    Side(String code) {
        this.code = code;
        Holder.SIDE_BY_LITERAL.put(code, this);
    }

    public static Side from(String code) {
        return Holder.SIDE_BY_LITERAL.get(code);
    }
}
