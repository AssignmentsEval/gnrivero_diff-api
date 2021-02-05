package com.waes.diffapi.domain.dto;

import com.waes.diffapi.domain.Diff;
import com.waes.diffapi.domain.constant.Side;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DiffRequestTest {

    @Test
    void givenNewLeftDiffElementMustReturnValidDiff() {

        Diff expectedDiff = Diff.builder().id("1").leftElement("dGVzdA==").build();

        final DiffRequest diffRequest = new DiffRequest("dGVzdA==");
        Diff resultDiffObject = diffRequest.convertToDiff("1", Side.LEFT);

        Assertions.assertEquals(expectedDiff, resultDiffObject);
    }

    @Test
    void givenNewRightDiffElementMustReturnValidDiff() {

        Diff expectedDiff = Diff.builder().id("1").rightElement("dGVzdA==").build();

        final DiffRequest diffRequest = new DiffRequest("dGVzdA==");
        Diff resultDiffObject = diffRequest.convertToDiff("1", Side.RIGHT);

        Assertions.assertEquals(expectedDiff, resultDiffObject);
    }

    @Test
    void givenExistingLeftDiffElementMustReturnValidDiff() {

        Diff expectedDiff = Diff.builder().id("1").leftElement("dGVzdA==").rightElement("dGVzdA==").build();

        Diff existingDiff = Diff.builder().id("1").leftElement("dGVzdA==").build();
        final DiffRequest diffRequest = new DiffRequest("dGVzdA==");
        Diff resultDiffObject = diffRequest.convertToDiff("1", Side.RIGHT, existingDiff.toBuilder());

        Assertions.assertEquals(expectedDiff, resultDiffObject);
    }

    @Test
    void givenExistingRightDiffElementMustReturnValidDiff() {

        Diff expectedDiff = Diff.builder().id("1").leftElement("dGVzdA==").rightElement("dGVzdA==").build();

        Diff existingDiff = Diff.builder().id("1").rightElement("dGVzdA==").build();
        final DiffRequest diffRequest = new DiffRequest("dGVzdA==");
        Diff resultDiffObject = diffRequest.convertToDiff("1", Side.LEFT, existingDiff.toBuilder());

        Assertions.assertEquals(expectedDiff, resultDiffObject);
    }

}
