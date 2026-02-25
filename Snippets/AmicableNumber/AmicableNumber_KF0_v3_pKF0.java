package com.thealgorithms.maths;

import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Amicable numbers are two different natural numbers such that the sum of the
 * proper divisors of each is equal to the other number.
 *
 * <p>A proper divisor of a number is a positive factor of that number other than
 * the number itself. For example, the proper divisors of 6 are 1, 2, and 3.
 *
 * <p>Example: (220, 284)
 * 220 is divisible by {1,2,4,5,10,11,20,22,44,55,110} -> SUM = 284
 * 284 is divisible by {1,2,4,71,142} -> SUM = 220.
 *
 * <p>See: https://en.wikipedia.org/wiki/Amicable_numbers
 */
public final class AmicableNumber {

    private AmicableNumber() {
        // Utility class; prevent instantiation
    }

    /**
     * Finds all the amicable number pairs in a given inclusive range.
     *
     * @param from range start value (inclusive)
     * @param to   range end value (inclusive)
     * @return set of amicable number pairs found in the given range
     * @throws IllegalArgumentException if the range is invalid or non-positive
     */
    public static Set<Pair<Integer, Integer>> findAllInRange(int from, int to) {
        validateRange(from, to);

        Set<Pair<Integer, Integer>> amicablePairs = new LinkedHashSet<>();

        for (int first = from; first < to; first++) {
            for (int second = first + 1; second <= to; second++) {
                if (isAmicablePair(first, second)) {
                    amicablePairs.add(Pair.of(first, second));
                }
            }
        }

        return amicablePairs;
    }

    /**
     * Checks whether two numbers form an amicable pair.
     *
     * @param first  first number
     * @param second second number
     * @return true if first and second are amicable numbers, false otherwise
     * @throws IllegalArgumentException if any input is non-positive
     */
    public static boolean isAmicablePair(int first, int second) {
        validatePositive(first, second);

        int sumFirst = sumOfProperDivisors(first);
        if (sumFirst != second) {
            return false;
        }

        int sumSecond = sumOfProperDivisors(second);
        return sumSecond == first;
    }

    /**
     * Calculates the sum of all proper divisors of a given number.
     *
     * @param number the number whose proper divisors are to be summed
     * @return sum of proper divisors of {@code number}
     */
    private static int sumOfProperDivisors(int number) {
        if (number <= 1) {
            return 0;
        }

        int sum = 1; // 1 is a proper divisor of every number > 1
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

    private static void validateRange(int from, int to) {
        if (from <= 0 || to <= 0 || to < from) {
            throw new IllegalArgumentException("Given range of values is invalid!");
        }
    }

    private static void validatePositive(int... values) {
        for (int value : values) {
            if (value <= 0) {
                throw new IllegalArgumentException("Input numbers must be natural!");
            }
        }
    }
}