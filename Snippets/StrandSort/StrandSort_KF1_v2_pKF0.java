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
        List<T> inputList = new ArrayList<>(Arrays.asList(array));
        List<T> sortedList = patienceSort(inputList);
        return sortedList.toArray(array);
    }

    private static <T extends Comparable<? super T>> List<T> patienceSort(List<T> input) {
        if (input.size() <= 1) {
            return new ArrayList<>(input);
        }

        List<T> sortedResult = new ArrayList<>();

        while (!input.isEmpty()) {
            List<T> currentPile = new ArrayList<>();
            currentPile.add(input.remove(0));

            int index = 0;
            while (index < input.size()) {
                T lastInPile = currentPile.get(currentPile.size() - 1);
                T currentElement = input.get(index);

                if (lastInPile.compareTo(currentElement) <= 0) {
                    currentPile.add(input.remove(index));
                } else {
                    index++;
                }
            }

            sortedResult = merge(sortedResult, currentPile);
        }

        return sortedResult;
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