package com.thealgorithms.maths;

import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

public final class AmicableNumbers {

    private AmicableNumbers() {
    }

    public static Set<Pair<Integer, Integer>> findAmicablePairsInRange(int rangeStart, int rangeEnd) {
        if (rangeStart <= 0 || rangeEnd <= 0 || rangeEnd < rangeStart) {
            throw new IllegalArgumentException("Given range of values is invalid!");
        }

        Set<Pair<Integer, Integer>> amicablePairs = new LinkedHashSet<>();

        for (int firstNumber = rangeStart; firstNumber < rangeEnd; firstNumber++) {
            for (int secondNumber = firstNumber + 1; secondNumber <= rangeEnd; secondNumber++) {
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

    private static int sumOfProperDivisorsRecursive(int number, int currentDivisor) {
        if (currentDivisor == 1) {
            return 0;
        }
        int nextDivisor = currentDivisor - 1;
        if (number % nextDivisor == 0) {
            return sumOfProperDivisorsRecursive(number, nextDivisor) + nextDivisor;
        } else {
            return sumOfProperDivisorsRecursive(number, nextDivisor);
        }
    }
}