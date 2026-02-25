package com.thealgorithms.sorts;

/**
 * Introspective sort (Introsort) implementation.
 *
 * <p>Introsort is a hybrid sorting algorithm that begins with quicksort and
 * switches to heapsort when the recursion depth exceeds a level based on the
 * logarithm of the number of elements being sorted. For small partitions, it
 * uses insertion sort.</p>
 */
public class Class1 implements SortAlgorithm {

    /** Threshold below which insertion sort is used instead of quicksort. */
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
     * Introsort main routine: quicksort with depth limit, then heapsort fallback,
     * and insertion sort for small partitions.
     */
    private static <T extends Comparable<T>> void introsort(
        T[] array,
        int left,
        int right,
        int depthLimit
    ) {
        int hi = right;
        int lo = left;

        while (hi - lo > INSERTION_SORT_THRESHOLD) {
            if (depthLimit == 0) {
                heapsort(array, lo, hi);
                return;
            }

            int pivotIndex = partition(array, lo, hi);
            introsort(array, pivotIndex + 1, hi, depthLimit - 1);
            hi = pivotIndex - 1;
        }

        insertionSort(array, lo, hi);
    }

    /**
     * Lomuto-style partition with a random pivot.
     *
     * @return index of the pivot after partitioning
     */
    private static <T extends Comparable<T>> int partition(
        T[] array,
        int left,
        int right
    ) {
        int randomIndex = left + (int) (Math.random() * (right - left + 1));
        SortUtils.swap(array, randomIndex, right);

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

    /** Insertion sort on the subarray [left, right]. */
    private static <T extends Comparable<T>> void insertionSort(
        T[] array,
        int left,
        int right
    ) {
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

    /** Heapsort on the subarray [left, right]. */
    private static <T extends Comparable<T>> void heapsort(
        T[] array,
        int left,
        int right
    ) {
        int length = right - left + 1;

        for (int i = (length / 2) - 1; i >= 0; i--) {
            heapify(array, i, length, left);
        }

        for (int i = right; i > left; i--) {
            SortUtils.swap(array, left, i);
            heapify(array, 0, i - left, left);
        }
    }

    /**
     * Heapify subtree rooted at index root within a heap of given size,
     * offset by 'offset' in the original array.
     */
    private static <T extends Comparable<T>> void heapify(
        T[] array,
        int root,
        int heapSize,
        int offset
    ) {
        int leftChild = 2 * root + 1;
        int rightChild = 2 * root + 2;
        int largest = root;

        if (leftChild < heapSize
            && array[offset + leftChild].compareTo(array[offset + largest]) > 0) {
            largest = leftChild;
        }

        if (rightChild < heapSize
            && array[offset + rightChild].compareTo(array[offset + largest]) > 0) {
            largest = rightChild;
        }

        if (largest != root) {
            SortUtils.swap(array, offset + root, offset + largest);
            heapify(array, largest, heapSize, offset);
        }
    }
}