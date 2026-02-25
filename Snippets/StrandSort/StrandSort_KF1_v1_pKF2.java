package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sorts an array using a natural merge sort based on runs (non-decreasing subsequences).
 */
public final class Class1 implements SortAlgorithm {

    /**
     * Sorts the given array using natural merge sort.
     *
     * @param array the array to be sorted
     * @param <T>   the type of elements, must be {@link Comparable}
     * @return the sorted array (same instance as the input)
     */
    @Override
    public <T extends Comparable<T>> T[] method1(T[] array) {
        List<T> list = new ArrayList<>(Arrays.asList(array));
        List<T> sorted = splitIntoRunsAndMerge(list);
        return sorted.toArray(array);
    }

    /**
     * Splits the list into non-decreasing runs and repeatedly merges them
     * until a single sorted list remains.
     *
     * @param list the list to be processed
     * @param <T>  the type of elements, must be {@link Comparable}
     * @return a sorted list containing all elements of {@code list}
     */
    private static <T extends Comparable<? super T>> List<T> splitIntoRunsAndMerge(List<T> list) {
        if (list.size() <= 1) {
            return list;
        }

        List<T> result = new ArrayList<>();

        while (!list.isEmpty()) {
            // Build one non-decreasing run starting from the first element
            List<T> run = new ArrayList<>();
            run.add(list.removeFirst());

            for (int i = 0; i < list.size(); ) {
                if (run.getLast().compareTo(list.get(i)) <= 0) {
                    run.add(list.remove(i));
                } else {
                    i++;
                }
            }

            // Merge the new run into the accumulated result
            result = mergeSortedLists(result, run);
        }

        return result;
    }

    /**
     * Merges two sorted lists into a single sorted list.
     *
     * @param left  the first sorted list
     * @param right the second sorted list
     * @param <T>   the type of elements, must be {@link Comparable}
     * @return a new list containing all elements from {@code left} and {@code right}, sorted
     */
    private static <T extends Comparable<? super T>> List<T> mergeSortedLists(List<T> left, List<T> right) {
        List<T> merged = new ArrayList<>();
        int i = 0;
        int j = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).compareTo(right.get(j)) <= 0) {
                merged.add(left.get(i));
                i++;
            } else {
                merged.add(right.get(j));
                j++;
            }
        }

        merged.addAll(left.subList(i, left.size()));
        merged.addAll(right.subList(j, right.size()));

        return merged;
    }
}