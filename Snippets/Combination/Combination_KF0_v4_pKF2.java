package com.thealgorithms.backtracking;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * Utility class for generating combinations of elements from an array.
 */
public final class Combination {

    private Combination() {
        // Utility class; prevent instantiation
    }

    /**
     * Generates all combinations of length {@code n} from the given array using backtracking.
     *
     * @param arr the input array
     * @param n   the desired combination length
     * @param <T> the type of elements in the array
     * @return a list of all combinations of length {@code n};
     *         if {@code n == 0}, returns an empty list
     * @throws IllegalArgumentException if {@code n} is negative
     */
    public static <T> List<TreeSet<T>> combination(T[] arr, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The combination length cannot be negative.");
        }
        if (n == 0) {
            return Collections.emptyList();
        }

        T[] array = arr.clone();
        Arrays.sort(array);

        List<TreeSet<T>> result = new LinkedList<>();
        generateCombinations(array, n, 0, new TreeSet<>(), result);
        return result;
    }

    /**
     * Backtracking helper that builds all combinations of length {@code n} from {@code arr}.
     *
     * @param arr     the sorted input array
     * @param n       the desired combination length
     * @param index   the current starting index in {@code arr}
     * @param current the current partial combination
     * @param result  the list collecting all complete combinations
     * @param <T>     the type of elements in the array
     */
    private static <T> void generateCombinations(
        T[] arr,
        int n,
        int index,
        TreeSet<T> current,
        List<TreeSet<T>> result
    ) {
        int remainingNeeded = n - current.size();
        int remainingAvailable = arr.length - index;

        if (remainingAvailable < remainingNeeded) {
            return;
        }

        if (remainingNeeded == 1) {
            for (int i = index; i < arr.length; i++) {
                current.add(arr[i]);
                result.add(new TreeSet<>(current));
                current.remove(arr[i]);
            }
            return;
        }

        for (int i = index; i < arr.length; i++) {
            current.add(arr[i]);
            generateCombinations(arr, n, i + 1, current, result);
            current.remove(arr[i]);
        }
    }
}