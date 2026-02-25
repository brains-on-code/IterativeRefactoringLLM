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
     * @param left       the left boundary of the current segment being sorted
     * @param right      the right boundary of the current segment being sorted
     * @param <T>        the type of elements in the array, which must be comparable
     * @return true if any elements were swapped during the sort; false otherwise
     */
    private <T extends Comparable<T>> boolean circleSort(final T[] array, final int left, final int right) {
        boolean hasSwapped = false;

        if (left == right) {
            return false;
        }

        int leftPointer = left;
        int rightPointer = right;

        while (leftPointer < rightPointer) {
            if (array[leftPointer].compareTo(array[rightPointer]) > 0) {
                SortUtils.swap(array, leftPointer, rightPointer);
                hasSwapped = true;
            }
            leftPointer++;
            rightPointer--;
        }

        if (leftPointer == rightPointer && array[leftPointer].compareTo(array[rightPointer + 1]) > 0) {
            SortUtils.swap(array, leftPointer, rightPointer + 1);
            hasSwapped = true;
        }

        final int middle = left + (right - left) / 2;
        final boolean hasSwappedLeftSegment = circleSort(array, left, middle);
        final boolean hasSwappedRightSegment = circleSort(array, middle + 1, right);

        return hasSwapped || hasSwappedLeftSegment || hasSwappedRightSegment;
    }
}