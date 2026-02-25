package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.List;

/**
 * Recursive implementation of merge sort for a list of integers.
 */
public class MergeSortRecursive {

    private final List<Integer> arr;

    /**
     * Constructs a MergeSortRecursive with the given list.
     *
     * @param arr the list of integers to be sorted
     */
    public MergeSortRecursive(List<Integer> arr) {
        this.arr = arr;
    }

    /**
     * Sorts the list using merge sort and returns a new sorted list.
     *
     * @return a new list containing the sorted elements
     */
    public List<Integer> mergeSort() {
        return mergeSortRecursive(arr);
    }

    /**
     * Recursively splits the list into halves, sorts each half, and merges them.
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
     * @return a new list containing all elements from left and right in sorted order
     */
    private static List<Integer> mergeSortedLists(List<Integer> left, List<Integer> right) {
        List<Integer> merged = new ArrayList<>(left.size() + right.size());

        int leftIndex = 0;
        int rightIndex = 0;

        // Merge elements from both lists in sorted order
        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (left.get(leftIndex) <= right.get(rightIndex)) {
                merged.add(left.get(leftIndex));
                leftIndex++;
            } else {
                merged.add(right.get(rightIndex));
                rightIndex++;
            }
        }

        // Append remaining elements from the left list, if any
        while (leftIndex < left.size()) {
            merged.add(left.get(leftIndex));
            leftIndex++;
        }

        // Append remaining elements from the right list, if any
        while (rightIndex < right.size()) {
            merged.add(right.get(rightIndex));
            rightIndex++;
        }

        return merged;
    }
}