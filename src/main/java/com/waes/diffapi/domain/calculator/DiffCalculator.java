package com.waes.diffapi.domain.calculator;

import com.waes.diffapi.domain.Diff;
import java.util.List;

public interface DiffCalculator {

    List<String> calculate(Diff diff);

}
