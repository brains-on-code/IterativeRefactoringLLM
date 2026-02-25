package com.thealgorithms.sorts;

public class CircleSort implements SortAlgorithm {

    /**
     * Sorts the given array using the circle sort algorithm.
     *
     * @param array the array to be sorted
     * @param <T>   the type of elements in the array, which must be comparable
     * @return the sorted array
     */
    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array.length == 0) {
            return array;
        }
        while (circleSort(array, 0, array.length - 1)) {
            // keep sorting until no swaps occur
        }
        return array;
    }

    /**
     * Recursively sorts the array in a circular manner by comparing elements
     * from the start and end of the current segment.
     *
     * @param array      the array to be sorted
     * @param startIndex the left boundary of the current segment being sorted
     * @param endIndex   the right boundary of the current segment being sorted
     * @param <T>        the type of elements in the array, which must be comparable
     * @return true if any elements were swapped during the sort; false otherwise
     */
    private <T extends Comparable<T>> boolean circleSort(final T[] array, final int startIndex, final int endIndex) {
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
        final boolean didSwapLeftSegment = circleSort(array, startIndex, middleIndex);
        final boolean didSwapRightSegment = circleSort(array, middleIndex + 1, endIndex);

        return didSwap || didSwapLeftSegment || didSwapRightSegment;
    }
}