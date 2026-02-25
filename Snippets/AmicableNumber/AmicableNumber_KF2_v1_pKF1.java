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

        for (int firstNumber = startInclusive; firstNumber < endInclusive; firstNumber++) {
            for (int secondNumber = firstNumber + 1; secondNumber <= endInclusive; secondNumber++) {
                if (isAmicablePair(firstNumber, secondNumber)) {
                    amicablePairs.add(Pair.of(firstNumber, secondNumber));
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

        currentDivisor--;

        if (number % currentDivisor == 0) {
            return sumOfProperDivisors(number, currentDivisor) + currentDivisor;
        } else {
            return sumOfProperDivisors(number, currentDivisor);
        }
    }
}