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

    private static <T extends Comparable<T>> void introSort(T[] array, final int left, int right, final int depthLimit) {
        while (right - left > INSERTION_SORT_THRESHOLD) {
            if (depthLimit == 0) {
                heapSort(array, left, right);
                return;
            }
            final int pivotIndex = partition(array, left, right);
            introSort(array, pivotIndex + 1, right, depthLimit - 1);
            right = pivotIndex - 1;
        }
        insertionSort(array, left, right);
    }

    private static <T extends Comparable<T>> int partition(T[] array, final int left, final int right) {
        final int randomIndex = left + (int) (Math.random() * (right - left + 1));
        SortUtils.swap(array, randomIndex, right);
        final T pivot = array[right];
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (array[j].compareTo(pivot) <= 0) {
                i++;
                SortUtils.swap(array, i, j);
            }
        }
        SortUtils.swap(array, i + 1, right);
        return i + 1;
    }

    private static <T extends Comparable<T>> void insertionSort(T[] array, final int left, final int right) {
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

    private static <T extends Comparable<T>> void heapSort(T[] array, final int left, final int right) {
        final int heapSize = right - left + 1;
        for (int i = (heapSize / 2) - 1; i >= 0; i--) {
            heapify(array, i, heapSize, left);
        }
        for (int i = right; i > left; i--) {
            SortUtils.swap(array, left, i);
            heapify(array, 0, i - left, left);
        }
    }

    private static <T extends Comparable<T>> void heapify(T[] array, final int index, final int heapSize, final int offset) {
        final int leftChild = 2 * index + 1;
        final int rightChild = 2 * index + 2;
        int largest = index;

        if (leftChild < heapSize && array[offset + leftChild].compareTo(array[offset + largest]) > 0) {
            largest = leftChild;
        }
        if (rightChild < heapSize && array[offset + rightChild].compareTo(array[offset + largest]) > 0) {
            largest = rightChild;
        }
        if (largest != index) {
            SortUtils.swap(array, offset + index, offset + largest);
            heapify(array, largest, heapSize, offset);
        }
    }
}