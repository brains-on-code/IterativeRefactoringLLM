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

    private <T extends Comparable<T>> boolean circleSortRecursive(final T[] array, final int startIndex, final int endIndex) {
        boolean didSwap = false;

        if (startIndex == endIndex) {
            return false;
        }

        int leftIndex = startIndex;
        int rightIndex = endIndex;

        while (leftIndex < rightIndex) {
            if (array[leftIndex].compareTo(array[rightIndex]) > 0) {
                SortUtils.swap(array, leftIndex, rightIndex);
                didSwap = true;
            }
            leftIndex++;
            rightIndex--;
        }

        if (leftIndex == rightIndex && array[leftIndex].compareTo(array[rightIndex + 1]) > 0) {
            SortUtils.swap(array, leftIndex, rightIndex + 1);
            didSwap = true;
        }

        final int middleIndex = startIndex + (endIndex - startIndex) / 2;
        final boolean didSwapLeftHalf = circleSortRecursive(array, startIndex, middleIndex);
        final boolean didSwapRightHalf = circleSortRecursive(array, middleIndex + 1, endIndex);

        return didSwap || didSwapLeftHalf || didSwapRightHalf;
    }
}