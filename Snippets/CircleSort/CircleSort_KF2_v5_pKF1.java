package com.thealgorithms.sorts;

public class CircleSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array.length == 0) {
            return array;
        }
        while (circleSortRecursive(array, 0, array.length - 1)) {
            // keep sorting until no swaps occur
        }
        return array;
    }

    private <T extends Comparable<T>> boolean circleSortRecursive(final T[] array, final int left, final int right) {
        boolean hasSwapped = false;

        if (left == right) {
            return false;
        }

        int leftIndex = left;
        int rightIndex = right;

        while (leftIndex < rightIndex) {
            if (array[leftIndex].compareTo(array[rightIndex]) > 0) {
                SortUtils.swap(array, leftIndex, rightIndex);
                hasSwapped = true;
            }
            leftIndex++;
            rightIndex--;
        }

        if (leftIndex == rightIndex && array[leftIndex].compareTo(array[rightIndex + 1]) > 0) {
            SortUtils.swap(array, leftIndex, rightIndex + 1);
            hasSwapped = true;
        }

        final int middle = left + (right - left) / 2;
        final boolean hasSwappedLeft = circleSortRecursive(array, left, middle);
        final boolean hasSwappedRight = circleSortRecursive(array, middle + 1, right);

        return hasSwapped || hasSwappedLeft || hasSwappedRight;
    }
}