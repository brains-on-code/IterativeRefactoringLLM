package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class StrandSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        List<T> unsorted = new ArrayList<>(Arrays.asList(array));
        List<T> sorted = strandSort(unsorted);
        return sorted.toArray(array);
    }

    private static <T extends Comparable<? super T>> List<T> strandSort(List<T> input) {
        if (input.size() <= 1) {
            return new ArrayList<>(input);
        }

        List<T> sorted = new ArrayList<>();

        while (!input.isEmpty()) {
            List<T> strand = extractStrand(input);
            sorted = merge(sorted, strand);
        }

        return sorted;
    }

    private static <T extends Comparable<? super T>> List<T> extractStrand(List<T> input) {
        List<T> strand = new ArrayList<>();
        strand.add(input.remove(0));

        int currentIndex = 0;
        while (currentIndex < input.size()) {
            T lastStrandElement = strand.get(strand.size() - 1);
            T currentElement = input.get(currentIndex);

            if (lastStrandElement.compareTo(currentElement) <= 0) {
                strand.add(input.remove(currentIndex));
            } else {
                currentIndex++;
            }
        }

        return strand;
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