package com.thealgorithms.sorts;

/**
 * Introspective sort implementation combining quicksort, heapsort, and insertion sort.
 */
public class IntroSort implements SortAlgorithm {

    private static final int INSERTION_SORT_THRESHOLD = 16;

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }
        final int maxDepth = 2 * (int) (Math.log(array.length) / Math.log(2));
        introSort(array, 0, array.length - 1, maxDepth);
        return array;
    }

    private static <T extends Comparable<T>> void introSort(
            T[] array,
            final int startIndex,
            int endIndex,
            final int maxDepth
    ) {
        int currentEndIndex = endIndex;
        int currentDepth = maxDepth;

        while (currentEndIndex - startIndex > INSERTION_SORT_THRESHOLD) {
            if (currentDepth == 0) {
                heapSort(array, startIndex, currentEndIndex);
                return;
            }
            final int pivotIndex = partition(array, startIndex, currentEndIndex);
            introSort(array, pivotIndex + 1, currentEndIndex, currentDepth - 1);
            currentEndIndex = pivotIndex - 1;
            currentDepth--;
        }
        insertionSort(array, startIndex, currentEndIndex);
    }

    private static <T extends Comparable<T>> int partition(
            T[] array,
            final int startIndex,
            final int endIndex
    ) {
        final int randomIndex = startIndex + (int) (Math.random() * (endIndex - startIndex + 1));
        SortUtils.swap(array, randomIndex, endIndex);
        final T pivotValue = array[endIndex];

        int partitionBoundary = startIndex - 1;
        for (int currentIndex = startIndex; currentIndex < endIndex; currentIndex++) {
            if (array[currentIndex].compareTo(pivotValue) <= 0) {
                partitionBoundary++;
                SortUtils.swap(array, partitionBoundary, currentIndex);
            }
        }
        SortUtils.swap(array, partitionBoundary + 1, endIndex);
        return partitionBoundary + 1;
    }

    private static <T extends Comparable<T>> void insertionSort(
            T[] array,
            final int startIndex,
            final int endIndex
    ) {
        for (int currentIndex = startIndex + 1; currentIndex <= endIndex; currentIndex++) {
            final T currentValue = array[currentIndex];
            int sortedIndex = currentIndex - 1;

            while (sortedIndex >= startIndex && array[sortedIndex].compareTo(currentValue) > 0) {
                array[sortedIndex + 1] = array[sortedIndex];
                sortedIndex--;
            }
            array[sortedIndex + 1] = currentValue;
        }
    }

    private static <T extends Comparable<T>> void heapSort(
            T[] array,
            final int startIndex,
            final int endIndex
    ) {
        final int heapSize = endIndex - startIndex + 1;

        for (int rootIndex = (heapSize / 2) - 1; rootIndex >= 0; rootIndex--) {
            heapify(array, rootIndex, heapSize, startIndex);
        }

        for (int currentIndex = endIndex; currentIndex > startIndex; currentIndex--) {
            SortUtils.swap(array, startIndex, currentIndex);
            heapify(array, 0, currentIndex - startIndex, startIndex);
        }
    }

    private static <T extends Comparable<T>> void heapify(
            T[] array,
            final int rootIndex,
            final int heapSize,
            final int baseIndex
    ) {
        final int leftChildIndex = 2 * rootIndex + 1;
        final int rightChildIndex = 2 * rootIndex + 2;
        int largestIndex = rootIndex;

        if (leftChildIndex < heapSize
                && array[baseIndex + leftChildIndex].compareTo(array[baseIndex + largestIndex]) > 0) {
            largestIndex = leftChildIndex;
        }

        if (rightChildIndex < heapSize
                && array[baseIndex + rightChildIndex].compareTo(array[baseIndex + largestIndex]) > 0) {
            largestIndex = rightChildIndex;
        }

        if (largestIndex != rootIndex) {
            SortUtils.swap(array, baseIndex + rootIndex, baseIndex + largestIndex);
            heapify(array, largestIndex, heapSize, baseIndex);
        }
    }
}