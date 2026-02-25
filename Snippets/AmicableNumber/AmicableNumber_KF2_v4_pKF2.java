package com.thealgorithms.maths;

import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

public final class AmicableNumber {

    private AmicableNumber() {
        // Prevent instantiation
    }

    /**
     * Finds all amicable number pairs (a, b) in the inclusive range [from, to].
     *
     * A pair (a, b) is amicable if:
     * <ul>
     *   <li>from &lt;= a &lt; b &lt;= to</li>
     *   <li>sum of proper divisors of a equals b</li>
     *   <li>sum of proper divisors of b equals a</li>
     * </ul>
     *
     * @param from lower bound of the search range (inclusive), must be &gt; 0
     * @param to   upper bound of the search range (inclusive), must be &gt;= from
     * @return a set of amicable pairs within the given range
     * @throws IllegalArgumentException if the range is invalid
     */
    public static Set<Pair<Integer, Integer>> findAllInRange(int from, int to) {
        validateRange(from, to);

        Set<Pair<Integer, Integer>> amicablePairs = new LinkedHashSet<>();

        for (int a = from; a < to; a++) {
            for (int b = a + 1; b <= to; b++) {
                if (isAmicableNumber(a, b)) {
                    amicablePairs.add(Pair.of(a, b));
                }
            }
        }

        return amicablePairs;
    }

    /**
     * Checks whether two positive integers form an amicable pair.
     *
     * @param a first number, must be &gt; 0
     * @param b second number, must be &gt; 0
     * @return {@code true} if a and b are amicable, {@code false} otherwise
     * @throws IllegalArgumentException if any input is not positive
     */
    public static boolean isAmicableNumber(int a, int b) {
        validatePositive(a, b);
        return sumOfProperDivisors(a) == b && sumOfProperDivisors(b) == a;
    }

    /**
     * Computes the sum of all proper divisors of the given number.
     * Proper divisors are positive divisors less than the number itself.
     *
     * @param number the number whose proper divisors are to be summed
     * @return the sum of proper divisors of {@code number}
     */
    private static int sumOfProperDivisors(int number) {
        if (number == 1) {
            return 0;
        }

        int sum = 1;
        int sqrt = (int) Math.sqrt(number);

        for (int divisor = 2; divisor <= sqrt; divisor++) {
            if (number % divisor == 0) {
                int complement = number / divisor;
                sum += divisor;
                if (complement != divisor) {
                    sum += complement;
                }
            }
        }

        return sum;
    }

    /**
     * Validates that the given range is positive and well-ordered.
     *
     * @param from lower bound (inclusive)
     * @param to   upper bound (inclusive)
     * @throws IllegalArgumentException if the range is invalid
     */
    private static void validateRange(int from, int to) {
        if (from <= 0 || to <= 0 || to < from) {
            throw new IllegalArgumentException("Given range of values is invalid!");
        }
    }

    /**
     * Validates that all provided numbers are positive.
     *
     * @param numbers numbers to validate
     * @throws IllegalArgumentException if any number is not positive
     */
    private static void validatePositive(int... numbers) {
        for (int number : numbers) {
            if (number <= 0) {
                throw new IllegalArgumentException("Input numbers must be natural!");
            }
        }
    }
}