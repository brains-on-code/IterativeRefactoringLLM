package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.List;

/**
 * Merge sort implementation for a list of integers.
 */
public class MergeSort {

    private final List<Integer> values;

    public MergeSort(List<Integer> values) {
        this.values = values;
    }

    /**
     * Sorts the list provided at construction time using merge sort.
     *
     * @return a new sorted list of integers
     */
    public List<Integer> sort() {
        return mergeSort(new ArrayList<>(values));
    }

    /**
     * Recursively splits the list into halves, sorts each half, and merges them.
     *
     * @param list the list to sort
     * @return a new sorted list
     */
    private static List<Integer> mergeSort(List<Integer> list) {
        int size = list.size();
        if (size <= 1) {
            return list;
        }

        int mid = size / 2;

        List<Integer> left = new ArrayList<>(list.subList(0, mid));
        List<Integer> right = new ArrayList<>(list.subList(mid, size));

        return merge(mergeSort(left), mergeSort(right));
    }

    /**
     * Merges two sorted lists into a single sorted list.
     *
     * @param left  the first sorted list
     * @param right the second sorted list
     * @return a new merged and sorted list
     */
    private static List<Integer> merge(List<Integer> left, List<Integer> right) {
        List<Integer> merged = new ArrayList<>(left.size() + right.size());

        int leftIndex = 0;
        int rightIndex = 0;
        int leftSize = left.size();
        int rightSize = right.size();

        while (leftIndex < leftSize && rightIndex < rightSize) {
            if (left.get(leftIndex) <= right.get(rightIndex)) {
                merged.add(left.get(leftIndex++));
            } else {
                merged.add(right.get(rightIndex++));
            }
        }

        while (leftIndex < leftSize) {
            merged.add(left.get(leftIndex++));
        }

        while (rightIndex < rightSize) {
            merged.add(right.get(rightIndex++));
        }

        return merged;
    }
}