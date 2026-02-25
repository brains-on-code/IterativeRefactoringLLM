package com.thealgorithms.maths;

import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Utility class for working with amicable numbers.
 *
 * <p>Example:
 * 220 has proper divisors {1,2,4,5,10,11,20,22,44,55,110} which sum to 284.
 * 284 has proper divisors {1,2,4,71,142} which sum to 220.
 * Therefore, (220, 284) is an amicable pair.
 */
public final class AmicableNumbers {

    private AmicableNumbers() {
        // Utility class; prevent instantiation
    }

    /**
     * Finds all amicable pairs (a, b) such that:
     * - lowerBound ≤ a < b ≤ upperBound
     * - sumOfProperDivisors(a) = b
     * - sumOfProperDivisors(b) = a
     *
     * @param lowerBound inclusive lower bound of the search range (must be > 0)
     * @param upperBound inclusive upper bound of the search range (must be ≥ lowerBound)
     * @return a set of amicable pairs within the given range
     * @throws IllegalArgumentException if the range is invalid
     */
    public static Set<Pair<Integer, Integer>> findAmicablePairs(int lowerBound, int upperBound) {
        validateRange(lowerBound, upperBound);

        Set<Pair<Integer, Integer>> amicablePairs = new LinkedHashSet<>();

        for (int a = lowerBound; a < upperBound; a++) {
            int sumA = sumOfProperDivisors(a);
            if (sumA <= a || sumA > upperBound) {
                continue;
            }
            if (sumOfProperDivisors(sumA) == a) {
                amicablePairs.add(Pair.of(a, sumA));
            }
        }

        return amicablePairs;
    }

    /**
     * Checks whether two natural numbers form an amicable pair.
     *
     * @param first  first natural number
     * @param second second natural number
     * @return true if the numbers are amicable, false otherwise
     * @throws IllegalArgumentException if any input is not a natural number (> 0)
     */
    public static boolean areAmicable(int first, int second) {
        validateNatural(first, second);
        return sumOfProperDivisors(first) == second
            && sumOfProperDivisors(second) == first;
    }

    /**
     * Computes the sum of proper divisors of a number.
     *
     * @param number the number whose proper divisors are being summed
     * @return the sum of all proper divisors of {@code number}
     */
    private static int sumOfProperDivisors(int number) {
        int sum = 1; // 1 is a proper divisor of all numbers > 1
        if (number == 1) {
            return 0;
        }

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

    private static void validateRange(int lowerBound, int upperBound) {
        if (lowerBound <= 0 || upperBound <= 0 || upperBound < lowerBound) {
            throw new IllegalArgumentException("Given range of values is invalid!");
        }
    }

    private static void validateNatural(int... numbers) {
        for (int number : numbers) {
            if (number <= 0) {
                throw new IllegalArgumentException("Input numbers must be natural!");
            }
        }
    }
}