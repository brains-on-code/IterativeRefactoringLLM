package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.List;

public class MergeSortRecursive {

    private final List<Integer> values;

    public MergeSortRecursive(List<Integer> values) {
        this.values = values;
    }

    public List<Integer> sort() {
        return mergeSort(values);
    }

    private static List<Integer> mergeSort(List<Integer> elements) {
        if (elements.size() <= 1) {
            return elements;
        }

        int size = elements.size();
        int middleIndex = size / 2;

        List<Integer> leftHalf = elements.subList(0, middleIndex);
        List<Integer> rightHalf = elements.subList(middleIndex, size);

        List<Integer> sortedLeftHalf = mergeSort(leftHalf);
        List<Integer> sortedRightHalf = mergeSort(rightHalf);

        return merge(sortedLeftHalf, sortedRightHalf);
    }

    private static List<Integer> merge(List<Integer> left, List<Integer> right) {
        if (left.isEmpty() && right.isEmpty()) {
            return new ArrayList<>();
        }
        if (left.isEmpty()) {
            return right;
        }
        if (right.isEmpty()) {
            return left;
        }

        List<Integer> merged = new ArrayList<>();
        if (left.get(0) <= right.get(0)) {
            merged.add(left.get(0));
            merged.addAll(merge(left.subList(1, left.size()), right));
        } else {
            merged.add(right.get(0));
            merged.addAll(merge(left, right.subList(1, right.size())));
        }
        return merged;
    }
}