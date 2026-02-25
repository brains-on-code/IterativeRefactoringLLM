package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public final class StrandSort implements SortAlgorithm {


    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        List<T> unsortedList = new ArrayList<>(Arrays.asList(array));
        List<T> sortedList = strandSort(unsortedList);
        return sortedList.toArray(array);
    }


    private static <T extends Comparable<? super T>> List<T> strandSort(List<T> list) {
        if (list.size() <= 1) {
            return list;
        }

        List<T> result = new ArrayList<>();
        while (!list.isEmpty()) {
            final List<T> sorted = new ArrayList<>();
            sorted.add(list.removeFirst());
            for (int i = 0; i < list.size();) {
                if (sorted.getLast().compareTo(list.get(i)) <= 0) {
                    sorted.add(list.remove(i));
                } else {
                    i++;
                }
            }
            result = merge(result, sorted);
        }
        return result;
    }


    private static <T extends Comparable<? super T>> List<T> merge(List<T> left, List<T> right) {
        List<T> result = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i).compareTo(right.get(j)) <= 0) {
                result.add(left.get(i));
                i++;
            } else {
                result.add(right.get(j));
                j++;
            }
        }
        result.addAll(left.subList(i, left.size()));
        result.addAll(right.subList(j, right.size()));
        return result;
    }
}
