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

    private static List<Integer> mergeSort(List<Integer> list) {
        if (list.size() <= 1) {
            return list;
        }

        int size = list.size();
        int midIndex = size / 2;

        List<Integer> leftHalf = list.subList(0, midIndex);
        List<Integer> rightHalf = list.subList(midIndex, size);

        List<Integer> sortedLeft = mergeSort(leftHalf);
        List<Integer> sortedRight = mergeSort(rightHalf);

        return merge(sortedLeft, sortedRight);
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