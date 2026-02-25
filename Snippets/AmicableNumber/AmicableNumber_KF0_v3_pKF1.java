package com.thealgorithms.maths;

import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Amicable numbers are two different natural numbers that the sum of the
 * proper divisors of each is equal to the other number.
 * (A proper divisor of a number is a positive factor of that number other than the number itself.
 * For example, the proper divisors of 6 are 1, 2, and 3.)
 * A pair of amicable numbers constitutes an aliquot sequence of period 2.
 * It is unknown if there are infinitely many pairs of amicable numbers.
 *
 * <p>
 * link: https://en.wikipedia.org/wiki/Amicable_numbers
 * <p>
 * Simple Example: (220, 284)
 * 220 is divisible by {1,2,4,5,10,11,20,22,44,55,110} <-SUM = 284
 * 284 is divisible by {1,2,4,71,142} <-SUM = 220.
 */
public final class AmicableNumber {

    private AmicableNumber() {
    }

    /**
     * Finds all the amicable numbers in a given range.
     *
     * @param rangeStart range start value
     * @param rangeEnd   range end value (inclusive)
     * @return set with amicable number pairs found in given range.
     */
    public static Set<Pair<Integer, Integer>> findAllInRange(int rangeStart, int rangeEnd) {
        if (rangeStart <= 0 || rangeEnd <= 0 || rangeEnd < rangeStart) {
            throw new IllegalArgumentException("Given range of values is invalid!");
        }

        Set<Pair<Integer, Integer>> amicablePairs = new LinkedHashSet<>();

        for (int firstNumber = rangeStart; firstNumber < rangeEnd; firstNumber++) {
            for (int secondNumber = firstNumber + 1; secondNumber <= rangeEnd; secondNumber++) {
                if (isAmicablePair(firstNumber, secondNumber)) {
                    amicablePairs.add(Pair.of(firstNumber, secondNumber));
                }
            }
        }
        return amicablePairs;
    }

    /**
     * Checks whether 2 numbers form an amicable pair.
     */
    public static boolean isAmicablePair(int firstNumber, int secondNumber) {
        if (firstNumber <= 0 || secondNumber <= 0) {
            throw new IllegalArgumentException("Input numbers must be natural!");
        }
        return sumOfProperDivisors(firstNumber, firstNumber) == secondNumber
            && sumOfProperDivisors(secondNumber, secondNumber) == firstNumber;
    }

    /**
     * Recursively calculates the sum of all proper divisors for a given number
     * excluding the number itself.
     */
    private static int sumOfProperDivisors(int number, int divisorCandidate) {
        if (divisorCandidate == 1) {
            return 0;
        }

        int nextDivisorCandidate = divisorCandidate - 1;

        if (number % nextDivisorCandidate == 0) {
            return sumOfProperDivisors(number, nextDivisorCandidate) + nextDivisorCandidate;
        } else {
            return sumOfProperDivisors(number, nextDivisorCandidate);
        }
    }
}