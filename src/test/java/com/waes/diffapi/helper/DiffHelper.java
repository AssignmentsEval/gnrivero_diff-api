package com.waes.diffapi.helper;

import com.waes.diffapi.domain.Diff;

public final class DiffHelper {

    public static Diff getLeftDiff() {
        return Diff.builder().id("1").leftElement("dGVzdA==").build();
    }

    public static Diff getRightDiff() {
        return Diff.builder().id("1").rightElement("dGVzdA==").build();
    }

    public static Diff getLeftAndRightDiff() {
        return Diff.builder().id("1").leftElement("dGVzdA==").rightElement("dGVzdA==").build();
    }
}
