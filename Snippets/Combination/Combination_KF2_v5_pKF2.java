package com.thealgorithms.backtracking;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public final class Combination {

    private Combination() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns all combinations of length {@code n} from the given array.
     *
     * @param <T> the element type
     * @param arr the input array
     * @param n   the desired combination length
     * @return a list of combinations, each as a {@link TreeSet}
     * @throws IllegalArgumentException if {@code n} is negative
     */
    public static <T> List<TreeSet<T>> combination(T[] arr, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Combination length cannot be negative.");
        }
        if (n == 0) {
            return Collections.emptyList();
        }

        T[] sortedArray = arr.clone();
        Arrays.sort(sortedArray);

        List<TreeSet<T>> result = new LinkedList<>();
        buildCombinations(sortedArray, n, 0, new TreeSet<>(), result);
        return result;
    }

    /**
     * Backtracking helper that builds combinations of a fixed length.
     *
     * @param <T>       the element type
     * @param arr       sorted input array
     * @param targetLen required size of each combination
     * @param startIdx  next index in {@code arr} to consider
     * @param current   current partial combination
     * @param result    list collecting completed combinations
     */
    private static <T> void buildCombinations(
        T[] arr,
        int targetLen,
        int startIdx,
        TreeSet<T> current,
        List<TreeSet<T>> result
    ) {
        int remainingSlots = targetLen - current.size();
        int remainingElements = arr.length - startIdx;

        // Not enough elements left to complete a combination
        if (remainingElements < remainingSlots) {
            return;
        }

        // Only one more element needed: add each possible tail element directly
        if (remainingSlots == 1) {
            for (int i = startIdx; i < arr.length; i++) {
                current.add(arr[i]);
                result.add(new TreeSet<>(current));
                current.remove(arr[i]);
            }
            return;
        }

        // General case: choose each possible next element and recurse
        for (int i = startIdx; i < arr.length; i++) {
            current.add(arr[i]);
            buildCombinations(arr, targetLen, i + 1, current, result);
            current.remove(arr[i]);
        }
    }
}