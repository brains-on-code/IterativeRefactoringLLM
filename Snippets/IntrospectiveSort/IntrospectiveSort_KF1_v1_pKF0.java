package com.thealgorithms.sorts;

/**
 * Introspective sort implementation (introsort).
 *
 * Uses quicksort and switches to heapsort when recursion depth exceeds a
 * limit, and to insertion sort for small partitions.
 */
public class Class1 implements SortAlgorithm {

    /** Threshold below which insertion sort is used. */
    private static final int INSERTION_SORT_THRESHOLD = 16;

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        int maxDepth = 2 * (int) (Math.log(array.length) / Math.log(2));
        introsort(array, 0, array.length - 1, maxDepth);
        return array;
    }

    /**
     * Introsort main routine: quicksort with depth limit, then heapsort.
     */
    private static <T extends Comparable<T>> void introsort(
            T[] array, int left, int right, int depthLimit) {

        while (right - left > INSERTION_SORT_THRESHOLD) {
            if (depthLimit == 0) {
                heapsort(array, left, right);
                return;
            }

            int pivotIndex = partition(array, left, right);
            introsort(array, pivotIndex + 1, right, depthLimit - 1);
            right = pivotIndex - 1;
        }

        insertionSort(array, left, right);
    }

    /**
     * Lomuto partition with random pivot.
     */
    private static <T extends Comparable<T>> int partition(T[] array, int left, int right) {
        int pivotIndex = left + (int) (Math.random() * (right - left + 1));
        SortUtils.swap(array, pivotIndex, right);

        T pivot = array[right];
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

    /**
     * Insertion sort on subarray [left, right].
     */
    private static <T extends Comparable<T>> void insertionSort(T[] array, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            T key = array[i];
            int j = i - 1;

            while (j >= left && array[j].compareTo(key) > 0) {
                array[j + 1] = array[j];
                j--;
            }

            array[j + 1] = key;
        }
    }

    /**
     * Heapsort on subarray [left, right].
     */
    private static <T extends Comparable<T>> void heapsort(T[] array, int left, int right) {
        int length = right - left + 1;

        // Build max heap
        for (int i = (length / 2) - 1; i >= 0; i--) {
            heapify(array, i, length, left);
        }

        // Extract elements from heap
        for (int i = right; i > left; i--) {
            SortUtils.swap(array, left, i);
            heapify(array, 0, i - left, left);
        }
    }

    /**
     * Heapify subtree rooted at index root within a heap of size heapSize,
     * offset by base index 'left' in the original array.
     */
    private static <T extends Comparable<T>> void heapify(
            T[] array, int root, int heapSize, int left) {

        int largest = root;
        int leftChild = 2 * root + 1;
        int rightChild = 2 * root + 2;

        if (leftChild < heapSize
                && array[left + leftChild].compareTo(array[left + largest]) > 0) {
            largest = leftChild;
        }

        if (rightChild < heapSize
                && array[left + rightChild].compareTo(array[left + largest]) > 0) {
            largest = rightChild;
        }

        if (largest != root) {
            SortUtils.swap(array, left + root, left + largest);
            heapify(array, largest, heapSize, left);
        }
    }
}