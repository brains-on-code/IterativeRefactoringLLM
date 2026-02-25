package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Natural merge sort implementation based on non-decreasing runs.
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
     * Repeatedly extracts non-decreasing runs from the list and merges them
     * into a single sorted list.
     *
     * @param list the list to be sorted
     * @param <T>  the type of elements, must be {@link Comparable}
     * @return a sorted list containing all elements of {@code list}
     */
    private static <T extends Comparable<? super T>> List<T> splitIntoRunsAndMerge(List<T> list) {
        if (list.size() <= 1) {
            return list;
        }

        List<T> result = new ArrayList<>();

        while (!list.isEmpty()) {
            List<T> run = new ArrayList<>();
            run.add(list.remove(0));

            int index = 0;
            while (index < list.size()) {
                T lastInRun = run.get(run.size() - 1);
                T current = list.get(index);

                if (lastInRun.compareTo(current) <= 0) {
                    run.add(list.remove(index));
                } else {
                    index++;
                }
            }

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
        List<T> merged = new ArrayList<>(left.size() + right.size());
        int i = 0;
        int j = 0;

        while (i < left.size() && j < right.size()) {
            T leftElement = left.get(i);
            T rightElement = right.get(j);

            if (leftElement.compareTo(rightElement) <= 0) {
                merged.add(leftElement);
                i++;
            } else {
                merged.add(rightElement);
                j++;
            }
        }

        merged.addAll(left.subList(i, left.size()));
        merged.addAll(right.subList(j, right.size()));

        return merged;
    }
}