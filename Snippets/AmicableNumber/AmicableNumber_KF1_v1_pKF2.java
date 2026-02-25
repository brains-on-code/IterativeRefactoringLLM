package com.thealgorithms.maths;

import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Utility class for finding amicable number pairs within a given range.
 *
 * <p>Two numbers are amicable if each is the sum of the proper divisors of the
 * other. For example, (220, 284) is an amicable pair:
 *
 * <ul>
 *   <li>Proper divisors of 220: {1, 2, 4, 5, 10, 11, 20, 22, 44, 55, 110}, sum = 284
 *   <li>Proper divisors of 284: {1, 2, 4, 71, 142}, sum = 220
 * </ul>
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Finds all amicable pairs (a, b) such that:
     * <ul>
     *   <li>var1 ≤ a &lt; b ≤ var2
     *   <li>a and b are amicable numbers
     * </ul>
     *
     * @param var1 lower bound of the search range (inclusive), must be &gt; 0
     * @param var2 upper bound of the search range (inclusive), must be ≥ var1
     * @return a set of ordered pairs (a, b) that are amicable within the range
     * @throws IllegalArgumentException if the range is invalid
     */
    public static Set<Pair<Integer, Integer>> method1(int var1, int var2) {
        if (var1 <= 0 || var2 <= 0 || var2 < var1) {
            throw new IllegalArgumentException("Given range of values is invalid!");
        }

        Set<Pair<Integer, Integer>> amicablePairs = new LinkedHashSet<>();

        for (int a = var1; a < var2; a++) {
            for (int b = a + 1; b <= var2; b++) {
                if (method2(a, b)) {
                    amicablePairs.add(Pair.of(a, b));
                }
            }
        }
        return amicablePairs;
    }

    /**
     * Checks whether two natural numbers form an amicable pair.
     *
     * @param var3 first number, must be &gt; 0
     * @param var4 second number, must be &gt; 0
     * @return {@code true} if var3 and var4 are amicable, {@code false} otherwise
     * @throws IllegalArgumentException if any input is not a natural number
     */
    public static boolean method2(int var3, int var4) {
        if (var3 <= 0 || var4 <= 0) {
            throw new IllegalArgumentException("Input numbers must be natural!");
        }
        return method3(var3, var3) == var4 && method3(var4, var4) == var3;
    }

    /**
     * Computes the sum of proper divisors of a number.
     *
     * <p>This is implemented recursively by iterating downwards from {@code var6 - 1}
     * to 1 and accumulating divisors of {@code var5}.
     *
     * @param var5 the number whose proper divisors are summed
     * @param var6 current divisor candidate (initially equal to var5)
     * @return the sum of all proper divisors of {@code var5}
     */
    private static int method3(int var5, int var6) {
        if (var6 == 1) {
            return 0;
        } else if (var5 % --var6 == 0) {
            return method3(var5, var6) + var6;
        } else {
            return method3(var5, var6);
        }
    }
}