package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.List;

public class MergeSortRecursive {

    private final List<Integer> arr;

    public MergeSortRecursive(List<Integer> arr) {
        this.arr = arr;
    }

    public List<Integer> mergeSort() {
        return merge(arr);
    }

    private static List<Integer> merge(List<Integer> arr) {
        if (arr.size() <= 1) {
            return new ArrayList<>(arr);
        }

        int mid = arr.size() / 2;
        List<Integer> left = new ArrayList<>(arr.subList(0, mid));
        List<Integer> right = new ArrayList<>(arr.subList(mid, arr.size()));

        List<Integer> sortedLeft = merge(left);
        List<Integer> sortedRight = merge(right);

        return mergeSortedLists(sortedLeft, sortedRight);
    }

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
            merged.add(left.get(leftIndex++));
        }

        while (rightIndex < right.size()) {
            merged.add(right.get(rightIndex++));
        }

        return merged;
    }
}