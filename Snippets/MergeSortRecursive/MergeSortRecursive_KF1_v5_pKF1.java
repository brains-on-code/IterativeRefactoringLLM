package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.List;

public class MergeSort {

    private final List<Integer> values;

    public MergeSort(List<Integer> values) {
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
        int middle = size / 2;

        List<Integer> leftSublist = new ArrayList<>(list.subList(0, middle));
        List<Integer> rightSublist = new ArrayList<>(list.subList(middle, size));

        List<Integer> sortedLeft = mergeSort(leftSublist);
        List<Integer> sortedRight = mergeSort(rightSublist);

        return merge(sortedLeft, sortedRight);
    }

    private static List<Integer> merge(List<Integer> left, List<Integer> right) {
        List<Integer> merged = new ArrayList<>();

        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (left.get(leftIndex) <= right.get(rightIndex)) {
                merged.add(left.get(leftIndex));
                leftIndex++;
            } else {
                merged.add(right.get(rightIndex));
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