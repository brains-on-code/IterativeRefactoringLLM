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
        List<T> elements = new ArrayList<>(Arrays.asList(array));
        List<T> sorted = mergeAllRuns(elements);
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
    private static <T extends Comparable<? super T>> List<T> mergeAllRuns(List<T> list) {
        if (list.size() <= 1) {
            return list;
        }

        List<T> mergedRuns = new ArrayList<>();

        while (!list.isEmpty()) {
            List<T> nextRun = extractNextRun(list);
            mergedRuns = mergeSortedLists(mergedRuns, nextRun);
        }

        return mergedRuns;
    }

    /**
     * Extracts the next non-decreasing run from the front of the list.
     * Elements that belong to the run are removed from {@code list}.
     *
     * @param list the source list
     * @param <T>  the type of elements, must be {@link Comparable}
     * @return a list representing the next non-decreasing run
     */
    private static <T extends Comparable<? super T>> List<T> extractNextRun(List<T> list) {
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

        return run;
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
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            T leftElement = left.get(leftIndex);
            T rightElement = right.get(rightIndex);

            if (leftElement.compareTo(rightElement) <= 0) {
                merged.add(leftElement);
                leftIndex++;
            } else {
                merged.add(rightElement);
                rightIndex++;
            }
        }

        merged.addAll(left.subList(leftIndex, left.size()));
        merged.addAll(right.subList(rightIndex, right.size()));

        return merged;
    }
}