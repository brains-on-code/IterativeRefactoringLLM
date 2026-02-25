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
        List<T> input = new ArrayList<>(Arrays.asList(array));
        List<T> sorted = patienceSort(input);
        return sorted.toArray(array);
    }

    private static <T extends Comparable<? super T>> List<T> patienceSort(List<T> input) {
        if (input.size() <= 1) {
            return new ArrayList<>(input);
        }

        List<T> sorted = new ArrayList<>();

        while (!input.isEmpty()) {
            List<T> pile = buildNextPile(input);
            sorted = merge(sorted, pile);
        }

        return sorted;
    }

    private static <T extends Comparable<? super T>> List<T> buildNextPile(List<T> input) {
        List<T> pile = new ArrayList<>();
        pile.add(input.remove(0));

        int index = 0;
        while (index < input.size()) {
            T lastInPile = pile.get(pile.size() - 1);
            T current = input.get(index);

            if (lastInPile.compareTo(current) <= 0) {
                pile.add(input.remove(index));
            } else {
                index++;
            }
        }

        return pile;
    }

    private static <T extends Comparable<? super T>> List<T> merge(List<T> left, List<T> right) {
        List<T> merged = new ArrayList<>(left.size() + right.size());
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            T leftElement = left.get(leftIndex);
            T rightElement = right.get(rightIndex);

            if (leftElement.compareTo(rightElement) <= 0) {
                merged.add(leftElement);
                leftIndex++;
            } else {
                merged.add(rightElement);
                rightIndex++;
            }
        }

        if (leftIndex < left.size()) {
            merged.addAll(left.subList(leftIndex, left.size()));
        }

        if (rightIndex < right.size()) {
            merged.addAll(right.subList(rightIndex, right.size()));
        }

        return merged;
    }
}