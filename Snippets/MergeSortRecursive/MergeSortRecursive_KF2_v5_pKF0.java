package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.List;

public class MergeSortRecursive {

    private final List<Integer> input;

    public MergeSortRecursive(List<Integer> input) {
        this.input = input;
    }

    public List<Integer> mergeSort() {
        return mergeSort(new ArrayList<>(input));
    }

    private static List<Integer> mergeSort(List<Integer> list) {
        if (list.size() <= 1) {
            return list;
        }

        int middleIndex = list.size() / 2;
        List<Integer> leftHalf = new ArrayList<>(list.subList(0, middleIndex));
        List<Integer> rightHalf = new ArrayList<>(list.subList(middleIndex, list.size()));

        List<Integer> sortedLeft = mergeSort(leftHalf);
        List<Integer> sortedRight = mergeSort(rightHalf);

        return merge(sortedLeft, sortedRight);
    }

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