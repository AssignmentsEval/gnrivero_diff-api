package com.waes.diffapi.domain.calculator;

import com.waes.diffapi.domain.Diff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Base64DiffCalculator implements DiffCalculator {

    private Base64.Decoder decoder;

    public Base64DiffCalculator() {
        this.decoder = Base64.getDecoder();
    }

    @Getter
    @AllArgsConstructor
    public enum Result {
        EQUAL("Inputs are equal"),
        NOT_EQUAL_SIZE("Input sizes are not equal"),
        EQUAL_SIZE_DIFFERENT_CONTENT("Result: "),
        ONE_SIDE_MISSING("Both sides are required to calculate diff");

        String insight;
    }

    @Override
    public List<String> calculate(final Diff diff) {

        if(diff.getLeftElement() == null || diff.getRightElement() == null) {
            return List.of(Result.ONE_SIDE_MISSING.getInsight());
        }

        byte[] left = decoder.decode(diff.getLeftElement());
        byte[] right = decoder.decode(diff.getRightElement());

        if (left.length != right.length) {
            return List.of(Result.NOT_EQUAL_SIZE.getInsight());
        }

        int diffLength = 0;
        boolean isNewDiff = true;
        boolean isDifferent = false;
        StringBuffer diffInsight = new StringBuffer();
        for (int i = 0; i < left.length; i++) {
            if (left[i] != right[i]) {
                if (isNewDiff) {
                    diffInsight.append("Offset: ").append(i + 1).append(". ");
                }
                diffLength++;
                isNewDiff = false;
                isDifferent = true;
            } else {
                if(!isNewDiff) {
                    diffInsight.append("Length: ").append(diffLength).append(";");
                    diffLength = 0;
                    isNewDiff = true;
                }
            }
        }

        if (isDifferent) {
            return Arrays.stream(diffInsight.toString().split(";"))
                    .collect(Collectors.toUnmodifiableList());
        }

        return List.of(Result.EQUAL.getInsight());
    }
    
}
