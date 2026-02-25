package com.thealgorithms.maths;

import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

public final class AmicableNumber {

    private AmicableNumber() {
    }

    public static Set<Pair<Integer, Integer>> findAllInRange(int startInclusive, int endInclusive) {
        if (startInclusive <= 0 || endInclusive <= 0 || endInclusive < startInclusive) {
            throw new IllegalArgumentException("Given range of values is invalid!");
        }

        Set<Pair<Integer, Integer>> amicablePairs = new LinkedHashSet<>();

        for (int firstCandidate = startInclusive; firstCandidate < endInclusive; firstCandidate++) {
            for (int secondCandidate = firstCandidate + 1; secondCandidate <= endInclusive; secondCandidate++) {
                if (isAmicablePair(firstCandidate, secondCandidate)) {
                    amicablePairs.add(Pair.of(firstCandidate, secondCandidate));
                }
            }
        }
        return amicablePairs;
    }

    public static boolean isAmicablePair(int firstNumber, int secondNumber) {
        if (firstNumber <= 0 || secondNumber <= 0) {
            throw new IllegalArgumentException("Input numbers must be natural!");
        }
        return sumOfProperDivisors(firstNumber, firstNumber) == secondNumber
            && sumOfProperDivisors(secondNumber, secondNumber) == firstNumber;
    }

    private static int sumOfProperDivisors(int number, int currentDivisor) {
        if (currentDivisor == 1) {
            return 0;
        }

        int nextDivisor = currentDivisor - 1;

        if (number % nextDivisor == 0) {
            return sumOfProperDivisors(number, nextDivisor) + nextDivisor;
        } else {
            return sumOfProperDivisors(number, nextDivisor);
        }
    }
}