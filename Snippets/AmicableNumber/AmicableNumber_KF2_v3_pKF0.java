package com.thealgorithms.maths;

import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

public final class AmicableNumber {

    private AmicableNumber() {
        // Utility class; prevent instantiation
    }

    public static Set<Pair<Integer, Integer>> findAllInRange(int from, int to) {
        validateRange(from, to);

        Set<Pair<Integer, Integer>> amicablePairs = new LinkedHashSet<>();

        for (int first = from; first < to; first++) {
            for (int second = first + 1; second <= to; second++) {
                if (areAmicable(first, second)) {
                    amicablePairs.add(Pair.of(first, second));
                }
            }
        }

        return amicablePairs;
    }

    public static boolean areAmicable(int first, int second) {
        validatePositive(first, second);

        int sumFirst = sumOfProperDivisors(first);
        int sumSecond = sumOfProperDivisors(second);

        return sumFirst == second && sumSecond == first;
    }

    private static int sumOfProperDivisors(int number) {
        if (number <= 1) {
            return 0;
        }

        int sum = 1; // 1 is a proper divisor of any number > 1
        int sqrt = (int) Math.sqrt(number);

        for (int divisor = 2; divisor <= sqrt; divisor++) {
            if (number % divisor != 0) {
                continue;
            }

            sum += divisor;
            int pairedDivisor = number / divisor;

            if (pairedDivisor != divisor) {
                sum += pairedDivisor;
            }
        }

        return sum;
    }

    private static void validateRange(int from, int to) {
        if (from <= 0 || to <= 0 || to < from) {
            throw new IllegalArgumentException("Given range of values is invalid!");
        }
    }

    private static void validatePositive(int... numbers) {
        for (int number : numbers) {
            if (number <= 0) {
                throw new IllegalArgumentException("Input numbers must be natural!");
            }
        }
    }
}