package com.thealgorithms.sorts;

public class CircleSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array.length == 0) {
            return array;
        }
        while (circleSort(array, 0, array.length - 1)) {
            // repeat until no swaps occur
        }
        return array;
    }

    private <T extends Comparable<T>> boolean circleSort(final T[] array, final int startIndex, final int endIndex) {
        if (startIndex == endIndex) {
            return false;
        }

        boolean didSwap = false;
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
        final boolean didSwapLeftHalf = circleSort(array, startIndex, middleIndex);
        final boolean didSwapRightHalf = circleSort(array, middleIndex + 1, endIndex);

        return didSwap || didSwapLeftHalf || didSwapRightHalf;
    }
}