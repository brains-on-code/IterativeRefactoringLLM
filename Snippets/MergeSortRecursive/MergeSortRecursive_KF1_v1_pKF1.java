package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.List;

public class MergeSort {

    private final List<Integer> inputList;

    public MergeSort(List<Integer> inputList) {
        this.inputList = inputList;
    }

    public List<Integer> sort() {
        return mergeSort(inputList);
    }

    private static List<Integer> mergeSort(List<Integer> list) {
        if (list.size() <= 1) {
            return list;
        }

        int size = list.size();
        int middleIndex = size / 2;
        List<Integer> left = list.subList(0, middleIndex);
        List<Integer> right = list.subList(middleIndex, size);

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
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
        if (left.get(0) <= right.get(0)) {
            List<Integer> merged = new ArrayList<Integer>() {
                {
                    add(left.get(0));
                }
            };
            merged.addAll(merge(left.subList(1, left.size()), right));
            return merged;
        } else {
            List<Integer> merged = new ArrayList<Integer>() {
                {
                    add(right.get(0));
                }
            };
            merged.addAll(merge(left, right.subList(1, right.size())));
            return merged;
        }
    }
}