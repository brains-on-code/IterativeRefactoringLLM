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
        final int depthLimit = 2 * (int) (Math.log(array.length) / Math.log(2));
        introSort(array, 0, array.length - 1, depthLimit);
        return array;
    }

    private static <T extends Comparable<T>> void introSort(
            T[] array,
            final int left,
            int right,
            final int depthLimit
    ) {
        int currentRight = right;
        int currentDepthLimit = depthLimit;

        while (currentRight - left > INSERTION_SORT_THRESHOLD) {
            if (currentDepthLimit == 0) {
                heapSort(array, left, currentRight);
                return;
            }
            final int pivotIndex = partition(array, left, currentRight);
            introSort(array, pivotIndex + 1, currentRight, currentDepthLimit - 1);
            currentRight = pivotIndex - 1;
            currentDepthLimit--;
        }
        insertionSort(array, left, currentRight);
    }

    private static <T extends Comparable<T>> int partition(
            T[] array,
            final int left,
            final int right
    ) {
        final int randomIndex = left + (int) (Math.random() * (right - left + 1));
        SortUtils.swap(array, randomIndex, right);
        final T pivot = array[right];

        int partitionIndex = left - 1;
        for (int i = left; i < right; i++) {
            if (array[i].compareTo(pivot) <= 0) {
                partitionIndex++;
                SortUtils.swap(array, partitionIndex, i);
            }
        }
        SortUtils.swap(array, partitionIndex + 1, right);
        return partitionIndex + 1;
    }

    private static <T extends Comparable<T>> void insertionSort(
            T[] array,
            final int left,
            final int right
    ) {
        for (int i = left + 1; i <= right; i++) {
            final T key = array[i];
            int j = i - 1;

            while (j >= left && array[j].compareTo(key) > 0) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    private static <T extends Comparable<T>> void heapSort(
            T[] array,
            final int left,
            final int right
    ) {
        final int heapSize = right - left + 1;

        for (int i = (heapSize / 2) - 1; i >= 0; i--) {
            heapify(array, i, heapSize, left);
        }

        for (int i = right; i > left; i--) {
            SortUtils.swap(array, left, i);
            heapify(array, 0, i - left, left);
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