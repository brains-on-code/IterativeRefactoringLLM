package com.thealgorithms.maths;

import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

public final class AmicableNumbers {

    private AmicableNumbers() {
    }

    public static Set<Pair<Integer, Integer>> findAmicablePairsInRange(int rangeStartInclusive, int rangeEndInclusive) {
        if (rangeStartInclusive <= 0 || rangeEndInclusive <= 0 || rangeEndInclusive < rangeStartInclusive) {
            throw new IllegalArgumentException("Given range of values is invalid!");
        }

        Set<Pair<Integer, Integer>> amicablePairs = new LinkedHashSet<>();

        for (int firstNumber = rangeStartInclusive; firstNumber < rangeEndInclusive; firstNumber++) {
            for (int secondNumber = firstNumber + 1; secondNumber <= rangeEndInclusive; secondNumber++) {
                if (areAmicable(firstNumber, secondNumber)) {
                    amicablePairs.add(Pair.of(firstNumber, secondNumber));
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

    private static int sumOfProperDivisorsRecursive(int number, int currentDivisorCandidate) {
        if (currentDivisorCandidate == 1) {
            return 0;
        }
        int nextDivisorCandidate = currentDivisorCandidate - 1;
        if (number % nextDivisorCandidate == 0) {
            return sumOfProperDivisorsRecursive(number, nextDivisorCandidate) + nextDivisorCandidate;
        } else {
            return sumOfProperDivisorsRecursive(number, nextDivisorCandidate);
        }
    }
}