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
        int smallerElementBoundary = left - 1;

        for (int current = left; current < right; current++) {
            if (array[current].compareTo(pivot) <= 0) {
                smallerElementBoundary++;
                SortUtils.swap(array, smallerElementBoundary, current);
            }
        }

        int finalPivotIndex = smallerElementBoundary + 1;
        SortUtils.swap(array, finalPivotIndex, right);
        return finalPivotIndex;
    }

    /**
     * Insertion sort on subarray [left, right].
     */
    private static <T extends Comparable<T>> void insertionSort(T[] array, int left, int right) {
        for (int index = left + 1; index <= right; index++) {
            T key = array[index];
            int position = index - 1;

            while (position >= left && array[position].compareTo(key) > 0) {
                array[position + 1] = array[position];
                position--;
            }

            array[position + 1] = key;
        }
    }

    /**
     * Heapsort on subarray [left, right].
     */
    private static <T extends Comparable<T>> void heapsort(T[] array, int left, int right) {
        int length = right - left + 1;

        // Build max heap
        for (int root = (length / 2) - 1; root >= 0; root--) {
            heapify(array, root, length, left);
        }

        // Extract elements from heap
        for (int end = right; end > left; end--) {
            SortUtils.swap(array, left, end);
            heapify(array, 0, end - left, left);
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

        int leftChildIndex = left + leftChild;
        int rightChildIndex = left + rightChild;
        int largestIndex = left + largest;

        if (leftChild < heapSize && array[leftChildIndex].compareTo(array[largestIndex]) > 0) {
            largest = leftChild;
            largestIndex = leftChildIndex;
        }

        if (rightChild < heapSize && array[rightChildIndex].compareTo(array[largestIndex]) > 0) {
            largest = rightChild;
            largestIndex = rightChildIndex;
        }

        if (largest != root) {
            SortUtils.swap(array, left + root, largestIndex);
            heapify(array, largest, heapSize, left);
        }
    }
}