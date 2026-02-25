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
     * @param array the array to be sorted
     * @param leftIndex  the left boundary of the current segment being sorted
     * @param rightIndex the right boundary of the current segment being sorted
     * @param <T>   the type of elements in the array, which must be comparable
     * @return true if any elements were swapped during the sort; false otherwise
     */
    private <T extends Comparable<T>> boolean circleSort(final T[] array, final int leftIndex, final int rightIndex) {
        boolean hasSwapped = false;

        if (leftIndex == rightIndex) {
            return false;
        }

        int leftPointer = leftIndex;
        int rightPointer = rightIndex;

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

        final int middleIndex = leftIndex + (rightIndex - leftIndex) / 2;
        final boolean leftSegmentSwapped = circleSort(array, leftIndex, middleIndex);
        final boolean rightSegmentSwapped = circleSort(array, middleIndex + 1, rightIndex);

        return hasSwapped || leftSegmentSwapped || rightSegmentSwapped;
    }
}