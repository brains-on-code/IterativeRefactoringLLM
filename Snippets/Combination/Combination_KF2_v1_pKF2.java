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
     * Generates all combinations of length {@code n} from the given array.
     *
     * @param <T>  the type of elements in the array
     * @param arr  the input array
     * @param n    the desired combination length
     * @return     a list of combinations, each represented as a {@link TreeSet}
     * @throws IllegalArgumentException if {@code n} is negative
     */
    public static <T> List<TreeSet<T>> combination(T[] arr, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The combination length cannot be negative.");
        }
        if (n == 0) {
            return Collections.emptyList();
        }

        T[] sortedArray = arr.clone();
        Arrays.sort(sortedArray);

        List<TreeSet<T>> result = new LinkedList<>();
        backtrack(sortedArray, n, 0, new TreeSet<>(), result);
        return result;
    }

    /**
     * Recursive backtracking helper to build combinations.
     *
     * @param <T>       the type of elements
     * @param arr       sorted input array
     * @param targetLen desired combination length
     * @param startIdx  current index in {@code arr} to consider
     * @param current   current partial combination
     * @param result    accumulator for completed combinations
     */
    private static <T> void backtrack(
        T[] arr,
        int targetLen,
        int startIdx,
        TreeSet<T> current,
        List<TreeSet<T>> result
    ) {
        // Prune: not enough elements left to reach target length
        if (startIdx + targetLen - current.size() > arr.length) {
            return;
        }

        // If we need only one more element, add each possible remaining element directly
        if (current.size() == targetLen - 1) {
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
            backtrack(arr, targetLen, i + 1, current, result);
            current.remove(arr[i]);
        }
    }
}