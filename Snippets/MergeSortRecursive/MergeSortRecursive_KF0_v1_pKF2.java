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
            return arr;
        }

        int mid = arr.size() / 2;
        List<Integer> left = merge(new ArrayList<>(arr.subList(0, mid)));
        List<Integer> right = merge(new ArrayList<>(arr.subList(mid, arr.size())));

        return mergeSortedLists(left, right);
    }

    private static List<Integer> mergeSortedLists(List<Integer> left, List<Integer> right) {
        List<Integer> merged = new ArrayList<>(left.size() + right.size());
        int i = 0;
        int j = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i) <= right.get(j)) {
                merged.add(left.get(i));
                i++;
            } else {
                merged.add(right.get(j));
                j++;
            }
        }

        while (i < left.size()) {
            merged.add(left.get(i));
            i++;
        }

        while (j < right.size()) {
            merged.add(right.get(j));
            j++;
        }

        return merged;
    }
}