package com.thealgorithms.maths;

import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Utility class for finding amicable number pairs within a given range.
 *
 * <p>Two numbers are amicable if each is the sum of the proper divisors of the
 * other. For example, (220, 284) is an amicable pair:
 *
 * <ul>
 *   <li>Proper divisors of 220: {1, 2, 4, 5, 10, 11, 20, 22, 44, 55, 110}, sum = 284
 *   <li>Proper divisors of 284: {1, 2, 4, 71, 142}, sum = 220
 * </ul>
 */
public final class AmicableNumbers {

    private AmicableNumbers() {
        // Prevent instantiation
    }

    /**
     * Finds all amicable pairs (a, b) such that:
     * <ul>
     *   <li>lowerBound ≤ a &lt; b ≤ upperBound
     *   <li>a and b are amicable numbers
     * </ul>
     *
     * @param lowerBound lower bound of the search range (inclusive), must be &gt; 0
     * @param upperBound upper bound of the search range (inclusive), must be ≥ lowerBound
     * @return a set of ordered pairs (a, b) that are amicable within the range
     * @throws IllegalArgumentException if the range is invalid
     */
    public static Set<Pair<Integer, Integer>> findAmicablePairs(int lowerBound, int upperBound) {
        if (lowerBound <= 0 || upperBound <= 0 || upperBound < lowerBound) {
            throw new IllegalArgumentException("Given range of values is invalid!");
        }

        Set<Pair<Integer, Integer>> amicablePairs = new LinkedHashSet<>();

        for (int a = lowerBound; a < upperBound; a++) {
            for (int b = a + 1; b <= upperBound; b++) {
                if (areAmicable(a, b)) {
                    amicablePairs.add(Pair.of(a, b));
                }
            }
        }
        return amicablePairs;
    }

    /**
     * Checks whether two natural numbers form an amicable pair.
     *
     * @param first first number, must be &gt; 0
     * @param second second number, must be &gt; 0
     * @return {@code true} if first and second are amicable, {@code false} otherwise
     * @throws IllegalArgumentException if any input is not a natural number
     */
    public static boolean areAmicable(int first, int second) {
        if (first <= 0 || second <= 0) {
            throw new IllegalArgumentException("Input numbers must be natural!");
        }
        return sumOfProperDivisors(first, first) == second
                && sumOfProperDivisors(second, second) == first;
    }

    /**
     * Computes the sum of proper divisors of a number.
     *
     * <p>This is implemented recursively by iterating downwards from {@code current - 1}
     * to 1 and accumulating divisors of {@code number}.
     *
     * @param number the number whose proper divisors are summed
     * @param current current divisor candidate (initially equal to number)
     * @return the sum of all proper divisors of {@code number}
     */
    private static int sumOfProperDivisors(int number, int current) {
        if (current == 1) {
            return 0;
        }

        current--;

        if (number % current == 0) {
            return sumOfProperDivisors(number, current) + current;
        } else {
            return sumOfProperDivisors(number, current);
        }
    }
}