package com.thealgorithms.maths;

import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

public final class AmicableNumbers {

    private AmicableNumbers() {
    }

    public static Set<Pair<Integer, Integer>> findAmicablePairsInRange(int startInclusive, int endInclusive) {
        if (startInclusive <= 0 || endInclusive <= 0 || endInclusive < startInclusive) {
            throw new IllegalArgumentException("Given range of values is invalid!");
        }

        Set<Pair<Integer, Integer>> amicablePairs = new LinkedHashSet<>();

        for (int first = startInclusive; first < endInclusive; first++) {
            for (int second = first + 1; second <= endInclusive; second++) {
                if (areAmicable(first, second)) {
                    amicablePairs.add(Pair.of(first, second));
                }
            }
        }
        return amicablePairs;
    }

    public static boolean areAmicable(int firstNumber, int secondNumber) {
        if (firstNumber <= 0 || secondNumber <= 0) {
            throw new IllegalArgumentException("Input numbers must be natural!");
        }
        return sumOfProperDivisors(firstNumber) == secondNumber
            && sumOfProperDivisors(secondNumber) == firstNumber;
    }

    private static int sumOfProperDivisors(int number) {
        return sumOfProperDivisorsRecursive(number, number);
    }

    private static int sumOfProperDivisorsRecursive(int number, int currentCandidateDivisor) {
        if (currentCandidateDivisor == 1) {
            return 0;
        }
        int nextCandidateDivisor = currentCandidateDivisor - 1;
        if (number % nextCandidateDivisor == 0) {
            return sumOfProperDivisorsRecursive(number, nextCandidateDivisor) + nextCandidateDivisor;
        } else {
            return sumOfProperDivisorsRecursive(number, nextCandidateDivisor);
        }
    }
}