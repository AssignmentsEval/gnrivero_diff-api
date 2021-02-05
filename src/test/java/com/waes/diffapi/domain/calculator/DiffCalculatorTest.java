package com.waes.diffapi.domain.calculator;

import com.waes.diffapi.domain.Diff;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DiffCalculatorTest {

    private static DiffCalculator diffCalculator;

    @BeforeAll
    public static void setUp() {
        diffCalculator = new Base64DiffCalculator();
    }

    @Test
    @DisplayName("Given two equal inputs must return 'Inputs are equal'")
    void mustReturnEqual() {

        var diff = Diff.builder().id("1").leftElement("dGVzdA==").rightElement("dGVzdA==").build();

        List<String> result = diffCalculator.calculate(diff);

        Assertions.assertEquals(List.of("Inputs are equal"), result);

    }

    @Test
    @DisplayName("Given two inputs with different size must return 'Input sizes are not equal'")
    void mustReturnSizeNotEqual() {

        var diff = Diff.builder().id("1").leftElement("dGVzdHRlc3Q=").rightElement("dGVzdA==").build();

        List<String> result = diffCalculator.calculate(diff);

        Assertions.assertEquals(List.of("Input sizes are not equal"), result);

    }

    @Test
    @DisplayName("Given one input must return 'Both sides are required to calculate diff'")
    void mustReturnOneElementMissing() {

        var diff = Diff.builder().id("1").rightElement("dGVzdA==").build();

        List<String> result = diffCalculator.calculate(diff);

        Assertions.assertEquals(List.of("Both sides are required to calculate diff"), result);

    }

    @Test
    @DisplayName("Given two different inputs with same length must return Offset and Length")
    void mustReturnInsight() {

        var diff = Diff.builder().id("1")
                .leftElement("dGVzdHRlc3R0ZXN0dGVzdHRlc3R0ZXN0dGVzdHRlc3R0ZXN0dGVzdHRlc3R0ZXN0")
                .rightElement("dGVzdHRhYWFhZXN0dGVzdHRlc3R0ZWFhYWVzdHRlc3R0ZXN0dGVzdGFhc3R0ZXN0")
                .build();

        List<String> result = diffCalculator.calculate(diff);
        List<String> expected = List.of("Offset: 6. Length: 4", "Offset: 23. Length: 3", "Offset: 41. Length: 2");

        Assertions.assertEquals(expected, result);

    }

}
