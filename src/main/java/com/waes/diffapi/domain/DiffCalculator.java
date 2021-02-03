package com.waes.diffapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class DiffCalculator {

    private Base64.Decoder decoder;

    public DiffCalculator() {
        this.decoder = Base64.getDecoder();
    }

    @Getter
    @AllArgsConstructor
    private enum Result {
        EQUAL("Inputs are equal"),
        NOT_EQUAL_SIZE("Input sizes are not equal"),
        EQUAL_SIZE_DIFFERENT_CONTENT("");

        String message;
    }

    public String process(final Diff diff) {

        byte[] left = decoder.decode(diff.getLeftElement());
        byte[] right = decoder.decode(diff.getRightElement());

        if (left.length != right.length) {
            return Result.NOT_EQUAL_SIZE.getMessage();
        }

        int diffLength = 0;
        boolean isNewDiff = true;
        StringBuffer diffInsightResult = new StringBuffer();
        for (int i = 0; i < left.length; i++) {
            if (left[i] != right[i]) {
                if (isNewDiff) {
                    diffInsightResult.append("Offset: ").append(i + 1).append(" ");
                }
                diffLength++;
                isNewDiff = false;
            } else {
                if(!isNewDiff) {
                    diffInsightResult.append("Length: ").append(diffLength).append(". ");
                    diffLength = 0;
                    isNewDiff = true;
                }
            }
        }

        if (diffInsightResult.length() > 0) {
            return diffInsightResult.toString();
        }

        return Result.EQUAL.getMessage();
    }
    
}
