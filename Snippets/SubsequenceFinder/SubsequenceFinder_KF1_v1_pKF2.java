package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating all subsets (the power set) of a given list.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation.
    }

    /**
     * Generates the power set of the given list.
     *
     * <p>The power set of a list is the set of all possible subsets,
     * including the empty set and the list itself.</p>
     *
     * @param <T>  the type of elements in the input list
     * @param list the input list
     * @return a list containing all subsets of the input list
     */
    public static <T> List<List<T>> method1(List<T> list) {
        List<List<T>> result = new ArrayList<>();
        if (list.isEmpty()) {
            result.add(new ArrayList<>());
            return result;
        }
        List<T> currentSubset = new ArrayList<>();
        method2(list, currentSubset, 0, result);
        return result;
    }

    /**
     * Recursive helper that builds all subsets of {@code list}.
     *
     * @param <T>           the type of elements in the list
     * @param list          the original input list
     * @param currentSubset the subset being built
     * @param index         the current index in {@code list}
     * @param result        the collection of all generated subsets
     */
    private static <T> void method2(List<T> list, List<T> currentSubset, final int index, List<List<T>> result) {
        assert index <= list.size();
        if (index == list.size()) {
            result.add(new ArrayList<>(currentSubset));
            return;
        }

        // Exclude current element
        method2(list, currentSubset, index + 1, result);

        // Include current element
        currentSubset.add(list.get(index));
        method2(list, currentSubset, index + 1, result);

        // Backtrack
        currentSubset.remove(currentSubset.size() - 1);
    }
}