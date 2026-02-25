package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.List;

/**
 * Recursive implementation of merge sort for a list of integers.
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
     * Sorts the list provided at construction time using merge sort.
     *
     * @return a new sorted list
     */
    public List<Integer> mergeSort() {
        return mergeSort(arr);
    }

    /**
     * Recursively sorts the given list using merge sort.
     *
     * @param arr the list to sort
     * @return a new sorted list
     */
    private static List<Integer> mergeSort(List<Integer> arr) {
        if (arr.size() <= 1) {
            return new ArrayList<>(arr);
        }

        int mid = arr.size() / 2;
        List<Integer> left = new ArrayList<>(arr.subList(0, mid));
        List<Integer> right = new ArrayList<>(arr.subList(mid, arr.size()));

        List<Integer> sortedLeft = mergeSort(left);
        List<Integer> sortedRight = mergeSort(right);

        return merge(sortedLeft, sortedRight);
    }

    /**
     * Merges two sorted lists into a single sorted list.
     *
     * @param left  the first sorted list
     * @param right the second sorted list
     * @return a new list containing all elements from {@code left} and {@code right}, sorted
     */
    private static List<Integer> merge(List<Integer> left, List<Integer> right) {
        List<Integer> merged = new ArrayList<>(left.size() + right.size());
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            int leftValue = left.get(leftIndex);
            int rightValue = right.get(rightIndex);

            if (leftValue <= rightValue) {
                merged.add(leftValue);
                leftIndex++;
            } else {
                merged.add(rightValue);
                rightIndex++;
            }
        }

        while (leftIndex < left.size()) {
            merged.add(left.get(leftIndex++));
        }

        while (rightIndex < right.size()) {
            merged.add(right.get(rightIndex++));
        }

        return merged;
    }
}