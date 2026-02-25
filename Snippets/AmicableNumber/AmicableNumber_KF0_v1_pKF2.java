package com.thealgorithms.maths;

import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Utility class for working with amicable numbers.
 *
 * <p>Amicable numbers are two different natural numbers such that the sum of the
 * proper divisors of each is equal to the other number.
 *
 * <p>Example: (220, 284)
 * <ul>
 *   <li>Proper divisors of 220: 1, 2, 4, 5, 10, 11, 20, 22, 44, 55, 110 (sum = 284)</li>
 *   <li>Proper divisors of 284: 1, 2, 4, 71, 142 (sum = 220)</li>
 * </ul>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Amicable_numbers">Amicable numbers (Wikipedia)</a>
 */
public final class AmicableNumber {

    private AmicableNumber() {
        // Utility class; prevent instantiation
    }

    /**
     * Finds all amicable number pairs in the given inclusive range.
     *
     * @param from start of the range (inclusive), must be > 0 and ≤ {@code to}
     * @param to   end of the range (inclusive), must be > 0 and ≥ {@code from}
     * @return a set of ordered pairs (a, b) where a and b are amicable and
     *         {@code from ≤ a < b ≤ to}
     * @throws IllegalArgumentException if the range is invalid
     */
    public static Set<Pair<Integer, Integer>> findAllInRange(int from, int to) {
        if (from <= 0 || to <= 0 || to < from) {
            throw new IllegalArgumentException("Given range of values is invalid!");
        }

        Set<Pair<Integer, Integer>> result = new LinkedHashSet<>();

        for (int i = from; i < to; i++) {
            for (int j = i + 1; j <= to; j++) {
                if (isAmicableNumber(i, j)) {
                    result.add(Pair.of(i, j));
                }
            }
        }

        return result;
    }

    /**
     * Checks whether two numbers form an amicable pair.
     *
     * @param a first natural number
     * @param b second natural number
     * @return {@code true} if {@code a} and {@code b} are amicable, {@code false} otherwise
     * @throws IllegalArgumentException if either number is not a positive integer
     */
    public static boolean isAmicableNumber(int a, int b) {
        if (a <= 0 || b <= 0) {
            throw new IllegalArgumentException("Input numbers must be natural!");
        }
        return sumOfProperDivisors(a) == b && sumOfProperDivisors(b) == a;
    }

    /**
     * Calculates the sum of all proper divisors of a number.
     * Proper divisors are positive divisors less than the number itself.
     *
     * @param number the number whose proper divisors are to be summed
     * @return the sum of all proper divisors of {@code number}
     */
    private static int sumOfProperDivisors(int number) {
        return sumOfDivisorsRecursive(number, number);
    }

    /**
     * Recursively calculates the sum of all divisors of {@code number} that are
     * less than {@code divisor}, effectively summing proper divisors when
     * initially called with {@code divisor == number}.
     *
     * @param number  the number whose divisors are being summed
     * @param divisor the current divisor candidate (exclusive upper bound)
     * @return the sum of all divisors of {@code number} that are less than {@code divisor}
     */
    private static int sumOfDivisorsRecursive(int number, int divisor) {
        if (divisor == 1) {
            return 0;
        }

        divisor--;

        if (number % divisor == 0) {
            return sumOfDivisorsRecursive(number, divisor) + divisor;
        } else {
            return sumOfDivisorsRecursive(number, divisor);
        }
    }
}