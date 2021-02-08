package com.waes.diffapi.domain.calculator;

import com.waes.diffapi.domain.Diff;
import java.util.List;

/**
 * Diff Calculator Interface
 *
 * Is an abstraction of a diff calculator that computes a Diff object which has
 * two elements (left and right) to be compared and return a result
 *
 * Using an interface allows me to change implementation easily in case
 * that i.e.: instead of comparing base64 encoded data, I now need to compare
 * plain text or numbers.
 *
 */
public interface DiffCalculator {

    List<String> calculate(Diff diff);

}
