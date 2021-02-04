package com.waes.diffapi.domain;

import org.springframework.stereotype.Component;
import com.waes.diffapi.domain.Diff.Result;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiffCalculator {

    private Base64.Decoder decoder;

    public DiffCalculator() {
        this.decoder = Base64.getDecoder();
    }

    public List<String> process(final Diff diff) {

        byte[] left = decoder.decode(diff.getLeftElement());
        byte[] right = decoder.decode(diff.getRightElement());

        if (left.length != right.length) {
            return List.of(Result.NOT_EQUAL_SIZE.getMessage());
        }

        int diffLength = 0;
        boolean isNewDiff = true;
        boolean isDifferent = false;
        StringBuffer diffInsightResult = new StringBuffer();
        for (int i = 0; i < left.length; i++) {
            if (left[i] != right[i]) {
                if (isNewDiff) {
                    diffInsightResult.append("Offset: ").append(i + 1).append(". ");
                }
                diffLength++;
                isNewDiff = false;
                isDifferent = true;
            } else {
                if(!isNewDiff) {
                    diffInsightResult.append("Length: ").append(diffLength).append(";");
                    diffLength = 0;
                    isNewDiff = true;
                }
            }
        }

        if (isDifferent) {
            return Arrays.stream(diffInsightResult.toString().split(";"))
                    .collect(Collectors.toUnmodifiableList());
        }

        return List.of(Result.EQUAL.getMessage());
    }
    
}
