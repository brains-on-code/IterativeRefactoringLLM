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
     * Returns a sorted copy of the list provided at construction time.
     *
     * @return a new sorted list of integers
     */
    public List<Integer> sort() {
        return mergeSort(new ArrayList<>(values));
    }

    /**
     * Recursively sorts the given list using merge sort.
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

        List<Integer> leftHalf = new ArrayList<>(list.subList(0, mid));
        List<Integer> rightHalf = new ArrayList<>(list.subList(mid, size));

        List<Integer> sortedLeft = mergeSort(leftHalf);
        List<Integer> sortedRight = mergeSort(rightHalf);

        return merge(sortedLeft, sortedRight);
    }

    /**
     * Merges two sorted lists into a single sorted list.
     *
     * @param left  the first sorted list
     * @param right the second sorted list
     * @return a new merged and sorted list
     */
    private static List<Integer> merge(List<Integer> left, List<Integer> right) {
        int leftSize = left.size();
        int rightSize = right.size();

        List<Integer> merged = new ArrayList<>(leftSize + rightSize);

        int leftIndex = 0;
        int rightIndex = 0;

        // Merge elements from both lists while both have remaining items
        while (leftIndex < leftSize && rightIndex < rightSize) {
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

        // Append any remaining elements from the left list
        while (leftIndex < leftSize) {
            merged.add(left.get(leftIndex));
            leftIndex++;
        }

        // Append any remaining elements from the right list
        while (rightIndex < rightSize) {
            merged.add(right.get(rightIndex));
            rightIndex++;
        }

        return merged;
    }
}