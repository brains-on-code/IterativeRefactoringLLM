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

        int middleIndex = size / 2;

        List<Integer> leftHalf = new ArrayList<>(list.subList(0, middleIndex));
        List<Integer> rightHalf = new ArrayList<>(list.subList(middleIndex, size));

        List<Integer> sortedLeft = mergeSort(leftHalf);
        List<Integer> sortedRight = mergeSort(rightHalf);

        return merge(sortedLeft, sortedRight);
    }

    private static List<Integer> merge(List<Integer> left, List<Integer> right) {
        int leftSize = left.size();
        int rightSize = right.size();

        List<Integer> merged = new ArrayList<>(leftSize + rightSize);

        int leftIndex = 0;
        int rightIndex = 0;

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

        appendRemaining(left, leftIndex, merged);
        appendRemaining(right, rightIndex, merged);

        return merged;
    }

    private static void appendRemaining(List<Integer> source, int startIndex, List<Integer> target) {
        for (int i = startIndex; i < source.size(); i++) {
            target.add(source.get(i));
        }
    }
}