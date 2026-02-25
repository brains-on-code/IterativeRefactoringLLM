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
            final int depthLimit
    ) {
        while (endIndex - startIndex > INSERTION_SORT_THRESHOLD) {
            if (depthLimit == 0) {
                heapSort(array, startIndex, endIndex);
                return;
            }
            final int pivotIndex = partition(array, startIndex, endIndex);
            introSort(array, pivotIndex + 1, endIndex, depthLimit - 1);
            endIndex = pivotIndex - 1;
        }
        insertionSort(array, startIndex, endIndex);
    }

    private static <T extends Comparable<T>> int partition(
            T[] array,
            final int startIndex,
            final int endIndex
    ) {
        final int randomIndex = startIndex + (int) (Math.random() * (endIndex - startIndex + 1));
        SortUtils.swap(array, randomIndex, endIndex);
        final T pivotValue = array[endIndex];

        int smallerElementBoundary = startIndex - 1;
        for (int currentIndex = startIndex; currentIndex < endIndex; currentIndex++) {
            if (array[currentIndex].compareTo(pivotValue) <= 0) {
                smallerElementBoundary++;
                SortUtils.swap(array, smallerElementBoundary, currentIndex);
            }
        }
        SortUtils.swap(array, smallerElementBoundary + 1, endIndex);
        return smallerElementBoundary + 1;
    }

    private static <T extends Comparable<T>> void insertionSort(
            T[] array,
            final int startIndex,
            final int endIndex
    ) {
        for (int currentIndex = startIndex + 1; currentIndex <= endIndex; currentIndex++) {
            final T currentValue = array[currentIndex];
            int previousIndex = currentIndex - 1;

            while (previousIndex >= startIndex && array[previousIndex].compareTo(currentValue) > 0) {
                array[previousIndex + 1] = array[previousIndex];
                previousIndex--;
            }
            array[previousIndex + 1] = currentValue;
        }
    }

    private static <T extends Comparable<T>> void heapSort(
            T[] array,
            final int startIndex,
            final int endIndex
    ) {
        final int heapSize = endIndex - startIndex + 1;

        for (int heapIndex = (heapSize / 2) - 1; heapIndex >= 0; heapIndex--) {
            heapify(array, heapIndex, heapSize, startIndex);
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
            final int offset
    ) {
        final int leftChildIndex = 2 * rootIndex + 1;
        final int rightChildIndex = 2 * rootIndex + 2;
        int largestIndex = rootIndex;

        if (leftChildIndex < heapSize
                && array[offset + leftChildIndex].compareTo(array[offset + largestIndex]) > 0) {
            largestIndex = leftChildIndex;
        }

        if (rightChildIndex < heapSize
                && array[offset + rightChildIndex].compareTo(array[offset + largestIndex]) > 0) {
            largestIndex = rightChildIndex;
        }

        if (largestIndex != rootIndex) {
            SortUtils.swap(array, offset + rootIndex, offset + largestIndex);
            heapify(array, largestIndex, heapSize, offset);
        }
    }
}