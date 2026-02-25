package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.List;

public class MergeSortRecursive {

    private final List<Integer> input;

    public MergeSortRecursive(List<Integer> input) {
        this.input = new ArrayList<>(input);
    }

    public List<Integer> mergeSort() {
        return mergeSortRecursive(new ArrayList<>(input));
    }

    private static List<Integer> mergeSortRecursive(List<Integer> list) {
        int size = list.size();
        if (size <= 1) {
            return list;
        }

        int mid = size / 2;
        List<Integer> leftHalf = new ArrayList<>(list.subList(0, mid));
        List<Integer> rightHalf = new ArrayList<>(list.subList(mid, size));

        List<Integer> sortedLeft = mergeSortRecursive(leftHalf);
        List<Integer> sortedRight = mergeSortRecursive(rightHalf);

        return mergeSortedLists(sortedLeft, sortedRight);
    }

    private static List<Integer> mergeSortedLists(List<Integer> left, List<Integer> right) {
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