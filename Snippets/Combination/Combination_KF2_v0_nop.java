package com.thealgorithms.backtracking;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;


public final class Combination {
    private Combination() {
    }


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
        backtracking(array, n, 0, new TreeSet<T>(), result);
        return result;
    }


    private static <T> void backtracking(T[] arr, int n, int index, TreeSet<T> currSet, List<TreeSet<T>> result) {
        if (index + n - currSet.size() > arr.length) {
            return;
        }
        if (currSet.size() == n - 1) {
            for (int i = index; i < arr.length; i++) {
                currSet.add(arr[i]);
                result.add(new TreeSet<>(currSet));
                currSet.remove(arr[i]);
            }
            return;
        }
        for (int i = index; i < arr.length; i++) {
            currSet.add(arr[i]);
            backtracking(arr, n, i + 1, currSet, result);
            currSet.remove(arr[i]);
        }
    }
}
