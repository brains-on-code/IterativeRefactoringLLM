package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.List;

/**
 * Merge sort implementation for a list of integers.
 */
public class Class1 {

    private final List<Integer> values;

    public Class1(List<Integer> values) {
        this.values = values;
    }

    /**
     * Sorts the list provided at construction time using merge sort.
     *
     * @return a sorted list of integers
     */
    public List<Integer> sort() {
        return mergeSort(values);
    }

    /**
     * Recursively splits the list into halves, sorts each half, and merges them.
     *
     * @param list the list to sort
     * @return a sorted list
     */
    private static List<Integer> mergeSort(List<Integer> list) {
        if (list.size() <= 1) {
            return list;
        }

        int size = list.size();
        int mid = size / 2;

        List<Integer> left = mergeSort(list.subList(0, mid));
        List<Integer> right = mergeSort(list.subList(mid, size));

        return merge(left, right);
    }

    /**
     * Merges two sorted lists into a single sorted list.
     *
     * @param left  the first sorted list
     * @param right the second sorted list
     * @return a merged and sorted list
     */
    private static List<Integer> merge(List<Integer> left, List<Integer> right) {
        if (left.isEmpty() && right.isEmpty()) {
            return new ArrayList<>();
        }
        if (left.isEmpty()) {
            return new ArrayList<>(right);
        }
        if (right.isEmpty()) {
            return new ArrayList<>(left);
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