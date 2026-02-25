package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.List;

public class MergeSort {

    private final List<Integer> values;

    public MergeSort(List<Integer> values) {
        this.values = new ArrayList<>(values);
    }

    public List<Integer> sort() {
        return mergeSort(new ArrayList<>(values));
    }

    private static List<Integer> mergeSort(List<Integer> list) {
        int size = list.size();
        if (size <= 1) {
            return list;
        }

        int middle = size / 2;

        List<Integer> left = new ArrayList<>(list.subList(0, middle));
        List<Integer> right = new ArrayList<>(list.subList(middle, size));

        List<Integer> sortedLeft = mergeSort(left);
        List<Integer> sortedRight = mergeSort(right);

        return merge(sortedLeft, sortedRight);
    }

    private static List<Integer> merge(List<Integer> left, List<Integer> right) {
        List<Integer> merged = new ArrayList<>(left.size() + right.size());
        int leftIndex = 0;
        int rightIndex = 0;
        int leftSize = left.size();
        int rightSize = right.size();

        while (leftIndex < leftSize && rightIndex < rightSize) {
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

        while (leftIndex < leftSize) {
            merged.add(left.get(leftIndex++));
        }

        while (rightIndex < rightSize) {
            merged.add(right.get(rightIndex++));
        }

        return merged;
    }
}