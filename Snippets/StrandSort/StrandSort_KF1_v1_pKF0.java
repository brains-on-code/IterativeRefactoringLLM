package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Patience sort implementation.
 */
public final class Class1 implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] method1(T[] array) {
        List<T> list = new ArrayList<>(Arrays.asList(array));
        List<T> sorted = patienceSort(list);
        return sorted.toArray(array);
    }

    private static <T extends Comparable<? super T>> List<T> patienceSort(List<T> input) {
        if (input.size() <= 1) {
            return input;
        }

        List<T> result = new ArrayList<>();
        while (!input.isEmpty()) {
            List<T> pile = new ArrayList<>();
            pile.add(input.removeFirst());

            for (int i = 0; i < input.size();) {
                if (pile.getLast().compareTo(input.get(i)) <= 0) {
                    pile.add(input.remove(i));
                } else {
                    i++;
                }
            }
            result = merge(result, pile);
        }
        return result;
    }

    private static <T extends Comparable<? super T>> List<T> merge(List<T> left, List<T> right) {
        List<T> merged = new ArrayList<>();
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (left.get(leftIndex).compareTo(right.get(rightIndex)) <= 0) {
                merged.add(left.get(leftIndex));
                leftIndex++;
            } else {
                merged.add(right.get(rightIndex));
                rightIndex++;
            }
        }

        merged.addAll(left.subList(leftIndex, left.size()));
        merged.addAll(right.subList(rightIndex, right.size()));
        return merged;
    }
}