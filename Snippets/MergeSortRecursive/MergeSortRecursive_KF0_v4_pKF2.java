package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.List;

/**
 * Recursive merge sort implementation for a list of integers.
 */
public class MergeSortRecursive {

    private final List<Integer> arr;

    /**
     * Creates a new instance with the list to be sorted.
     *
     * @param arr the list of integers to sort
     */
    public MergeSortRecursive(List<Integer> arr) {
        this.arr = arr;
    }

    /**
     * Returns a new list containing the elements of {@code arr} in sorted order.
     *
     * @return a new sorted list
     */
    public List<Integer> mergeSort() {
        return mergeSortRecursive(arr);
    }

    /**
     * Recursively sorts the given list using merge sort.
     *
     * @param list the list to sort
     * @return a new sorted list
     */
    private static List<Integer> mergeSortRecursive(List<Integer> list) {
        if (list.size() <= 1) {
            return list;
        }

        int mid = list.size() / 2;

        List<Integer> left = mergeSortRecursive(new ArrayList<>(list.subList(0, mid)));
        List<Integer> right = mergeSortRecursive(new ArrayList<>(list.subList(mid, list.size())));

        return mergeSortedLists(left, right);
    }

    /**
     * Merges two sorted lists into a single sorted list.
     *
     * @param left  the first sorted list
     * @param right the second sorted list
     * @return a new list containing all elements from {@code left} and {@code right} in sorted order
     */
    private static List<Integer> mergeSortedLists(List<Integer> left, List<Integer> right) {
        List<Integer> merged = new ArrayList<>(left.size() + right.size());

        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            Integer leftValue = left.get(leftIndex);
            Integer rightValue = right.get(rightIndex);

            if (leftValue <= rightValue) {
                merged.add(leftValue);
                leftIndex++;
            } else {
                merged.add(rightValue);
                rightIndex++;
            }
        }

        while (leftIndex < left.size()) {
            merged.add(left.get(leftIndex));
            leftIndex++;
        }

        while (rightIndex < right.size()) {
            merged.add(right.get(rightIndex));
            rightIndex++;
        }

        return merged;
    }
}