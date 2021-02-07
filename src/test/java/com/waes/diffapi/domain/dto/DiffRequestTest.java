package com.waes.diffapi.domain.dto;

import com.waes.diffapi.domain.Diff;
import com.waes.diffapi.domain.constant.Side;
import com.waes.diffapi.helper.DiffHelper;
import com.waes.diffapi.helper.DiffRequestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DiffRequestTest {

    @Test
    @DisplayName("When new left diff element is created and is new must set only left element")
    void when_newLeftDiffElement_mustReturnValidDiff() {

        Diff expectedDiff = DiffHelper.getLeftDiff();

        final DiffRequest diffRequest = DiffRequestHelper.getDiffRequest();
        Diff resultDiffObject = diffRequest.convertToDiff("1", Side.LEFT);

        Assertions.assertEquals(expectedDiff, resultDiffObject);
    }

    @Test
    @DisplayName("When new right diff element is created and is new must set only right element")
    void when_newRightDiffElement_mustReturnValidDiff() {

        Diff expectedDiff = DiffHelper.getRightDiff();

        final DiffRequest diffRequest = DiffRequestHelper.getDiffRequest();
        Diff resultDiffObject = diffRequest.convertToDiff("1", Side.RIGHT);

        Assertions.assertEquals(expectedDiff, resultDiffObject);
    }

    @Test
    @DisplayName("When diff exists and must update only left element")
    void when_existingLeftDiffElement_mustReturnValidDiff() {

        Diff expectedDiff = DiffHelper.getLeftAndRightDiff();

        Diff existingDiff = DiffHelper.getLeftDiff();
        final DiffRequest diffRequest = DiffRequestHelper.getDiffRequest();
        Diff resultDiffObject = diffRequest.convertToDiff("1", Side.RIGHT, existingDiff.toBuilder());

        Assertions.assertEquals(expectedDiff, resultDiffObject);
    }

    @Test
    @DisplayName("When diff exists and must update only right element")
    void when_existingRightDiffElement_mustReturnValidDiff() {

        Diff expectedDiff = DiffHelper.getLeftAndRightDiff();

        Diff existingDiff = DiffHelper.getRightDiff();
        final DiffRequest diffRequest = DiffRequestHelper.getDiffRequest();
        Diff resultDiffObject = diffRequest.convertToDiff("1", Side.LEFT, existingDiff.toBuilder());

        Assertions.assertEquals(expectedDiff, resultDiffObject);
    }

}
