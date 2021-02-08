package com.waes.diffapi.domain.calculator;

import com.waes.diffapi.domain.Diff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Diff Calculator Implementation
 *
 * This particular implementation decodes base64 information and converts it to
 * byte array (binary and original data).
 *
 * After comparison, returns a result with details about the computation.
 *
 * All data received is supposed to be valid, so no validation is made here.
 * Only check for null values to avoid NPE.
 *
 * Possible results:
 *
 * "Equal": Left and right are equal in size and content.
 *
 * "Different Size": Left and right are present but differ in length.
 *
 * "Both Sides Required": Indicates that one of the sides is null or empty. I don't considered null
 * as a sizeable element, so "Different Size" doesn't apply here. Besides it's more descriptive of the
 * real situation.
 *
 * "Equal Size, Different Content": When both sides are present, have the same length but differ in content.
 * Along with this state a report including Offset where the difference is and Length of the difference is return.
 *
 */
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
        DIFFERENT_SIZE("Input sizes are not equal"),
        EQUAL_SIZE_DIFFERENT_CONTENT("Result: "),
        BOTH_SIDES_REQUIRED("Both sides are required to calculate diff");

        String insight;
    }

    @Override
    public List<String> calculate(final Diff diff) {

        if(diff.getLeftElement() == null || diff.getRightElement() == null) {
            return List.of(Result.BOTH_SIDES_REQUIRED.getInsight());
        }

        byte[] left = decoder.decode(diff.getLeftElement());
        byte[] right = decoder.decode(diff.getRightElement());

        if (left.length != right.length) {
            return List.of(Result.DIFFERENT_SIZE.getInsight());
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
