package com.thealgorithms.maths;

import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

public final class Class1 {

    private Class1() {
    }

    public static Set<Pair<Integer, Integer>> findAmicablePairsInRange(int start, int end) {
        if (start <= 0 || end <= 0 || end < start) {
            throw new IllegalArgumentException("Given range of values is invalid!");
        }

        Set<Pair<Integer, Integer>> amicablePairs = new LinkedHashSet<>();

        for (int first = start; first < end; first++) {
            for (int second = first + 1; second <= end; second++) {
                if (areAmicable(first, second)) {
                    amicablePairs.add(Pair.of(first, second));
                }
            }
        }
        return amicablePairs;
    }

    public static boolean areAmicable(int a, int b) {
        if (a <= 0 || b <= 0) {
            throw new IllegalArgumentException("Input numbers must be natural!");
        }
        return sumOfProperDivisors(a) == b && sumOfProperDivisors(b) == a;
    }

    private static int sumOfProperDivisors(int number) {
        return sumOfProperDivisorsRecursive(number, number);
    }

    private static int sumOfProperDivisorsRecursive(int number, int divisor) {
        if (divisor == 1) {
            return 0;
        }
        int nextDivisor = divisor - 1;
        if (number % nextDivisor == 0) {
            return sumOfProperDivisorsRecursive(number, nextDivisor) + nextDivisor;
        } else {
            return sumOfProperDivisorsRecursive(number, nextDivisor);
        }
    }
}