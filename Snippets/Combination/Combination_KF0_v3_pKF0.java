package com.thealgorithms.backtracking;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * Finds all combinations of a given array.
 * @author Alan Piao (<a href="https://github.com/cpiao3">git-Alan Piao</a>)
 */
public final class Combination {

    private Combination() {
        // Utility class; prevent instantiation.
    }

    /**
     * Find all combinations of given array using backtracking.
     *
     * @param arr the array
     * @param n   length of each combination
     * @param <T> the type of elements in the array
     * @return a list of all combinations of length n; if n == 0, returns an empty list
     * @throws IllegalArgumentException if n is negative
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

        List<TreeSet<T>> combinations = new LinkedList<>();
        backtrack(sortedArray, n, 0, new TreeSet<>(), combinations);
        return combinations;
    }

    /**
     * Backtracks to generate all possible combinations of a given array.
     *
     * @param arr        the sorted array
     * @param targetSize desired combination length
     * @param startIndex current starting index in the array
     * @param current    set tracking the current combination
     * @param result     list collecting all combinations
     * @param <T>        the type of elements in the array
     */
    private static <T> void backtrack(
        T[] arr,
        int targetSize,
        int startIndex,
        TreeSet<T> current,
        List<TreeSet<T>> result
    ) {
        int remainingNeeded = targetSize - current.size();
        int remainingAvailable = arr.length - startIndex;

        if (remainingNeeded > remainingAvailable) {
            return;
        }

        if (current.size() == targetSize - 1) {
            addFinalElements(arr, startIndex, current, result);
            return;
        }

        for (int i = startIndex; i < arr.length; i++) {
            T element = arr[i];
            current.add(element);
            backtrack(arr, targetSize, i + 1, current, result);
            current.remove(element);
        }
    }

    private static <T> void addFinalElements(
        T[] arr,
        int startIndex,
        TreeSet<T> current,
        List<TreeSet<T>> result
    ) {
        for (int i = startIndex; i < arr.length; i++) {
            T element = arr[i];
            current.add(element);
            result.add(new TreeSet<>(current));
            current.remove(element);
        }
    }
}