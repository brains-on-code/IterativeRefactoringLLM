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

        List<TreeSet<T>> result = new LinkedList<>();
        generateCombinations(sortedArray, n, 0, new TreeSet<>(), result);
        return result;
    }

    /**
     * Backtracks to generate all possible combinations of a given array.
     *
     * @param arr      the sorted array
     * @param target   desired combination length
     * @param startIdx current starting index in the array
     * @param current  set tracking the current combination
     * @param result   list collecting all combinations
     * @param <T>      the type of elements in the array
     */
    private static <T> void generateCombinations(
        T[] arr,
        int target,
        int startIdx,
        TreeSet<T> current,
        List<TreeSet<T>> result
    ) {
        int remainingNeeded = target - current.size();
        int remainingAvailable = arr.length - startIdx;

        if (remainingNeeded > remainingAvailable) {
            return;
        }

        if (current.size() == target - 1) {
            addLastElements(arr, startIdx, current, result);
            return;
        }

        for (int i = startIdx; i < arr.length; i++) {
            T element = arr[i];
            current.add(element);
            generateCombinations(arr, target, i + 1, current, result);
            current.remove(element);
        }
    }

    private static <T> void addLastElements(
        T[] arr,
        int startIdx,
        TreeSet<T> current,
        List<TreeSet<T>> result
    ) {
        for (int i = startIdx; i < arr.length; i++) {
            T element = arr[i];
            current.add(element);
            result.add(new TreeSet<>(current));
            current.remove(element);
        }
    }
}