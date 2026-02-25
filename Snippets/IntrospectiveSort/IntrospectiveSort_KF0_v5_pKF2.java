package com.thealgorithms.sorts;

/**
 * Introspective Sort (Introsort) implementation.
 *
 * <p>Starts with quicksort and:
 * <ul>
 *   <li>switches to heapsort when recursion depth is exhausted, and</li>
 *   <li>uses insertion sort for sufficiently small partitions.</li>
 * </ul>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Introsort">Introsort Algorithm</a>
 */
public class IntrospectiveSort implements SortAlgorithm {

    /** Maximum partition size for which insertion sort is used. */
    private static final int INSERTION_SORT_THRESHOLD = 16;

    /**
     * Sorts the given array using introspective sort.
     *
     * @param array the array to be sorted
     * @param <T>   the type of elements in the array, which must be comparable
     * @return the sorted array
     */
    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        int maxDepth = 2 * (int) (Math.log(array.length) / Math.log(2));
        introspectiveSort(array, 0, array.length - 1, maxDepth);
        return array;
    }

    /**
     * Sorts the subarray {@code array[low..high]} using introsort.
     *
     * <p>Algorithm:
     * <ol>
     *   <li>While the current partition is larger than
     *       {@link #INSERTION_SORT_THRESHOLD}:
     *       <ul>
     *         <li>If {@code depth == 0}, fall back to heapsort on this partition.</li>
     *         <li>Otherwise, partition around a pivot and recurse on the right
     *             partition, then continue iteratively on the left.</li>
     *       </ul>
     *   </li>
     *   <li>For small partitions, finish with insertion sort.</li>
     * </ol>
     */
    private static <T extends Comparable<T>> void introspectiveSort(
            T[] array, int low, int high, int depth) {

        while (high - low > INSERTION_SORT_THRESHOLD) {
            if (depth == 0) {
                heapSort(array, low, high);
                return;
            }

            int pivotIndex = partition(array, low, high);
            introspectiveSort(array, pivotIndex + 1, high, depth - 1);
            high = pivotIndex - 1;
            depth--;
        }

        insertionSort(array, low, high);
    }

    /**
     * Partitions the subarray {@code array[low..high]} around a randomly chosen pivot.
     *
     * @param array the array containing the subarray to partition
     * @param low   lower bound (inclusive) of the subarray
     * @param high  upper bound (inclusive) of the subarray
     * @param <T>   element type
     * @return the final index of the pivot
     */
    private static <T extends Comparable<T>> int partition(T[] array, int low, int high) {
        int pivotIndex = low + (int) (Math.random() * (high - low + 1));
        SortUtils.swap(array, pivotIndex, high);

        T pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (array[j].compareTo(pivot) <= 0) {
                i++;
                SortUtils.swap(array, i, j);
            }
        }

        SortUtils.swap(array, i + 1, high);
        return i + 1;
    }

    /**
     * Sorts the subarray {@code array[low..high]} using insertion sort.
     *
     * @param array the array containing the subarray to sort
     * @param low   lower bound (inclusive) of the subarray
     * @param high  upper bound (inclusive) of the subarray
     * @param <T>   element type
     */
    private static <T extends Comparable<T>> void insertionSort(T[] array, int low, int high) {
        for (int currentIndex = low + 1; currentIndex <= high; currentIndex++) {
            T key = array[currentIndex];
            int previousIndex = currentIndex - 1;

            while (previousIndex >= low && array[previousIndex].compareTo(key) > 0) {
                array[previousIndex + 1] = array[previousIndex];
                previousIndex--;
            }

            array[previousIndex + 1] = key;
        }
    }

    /**
     * Sorts the subarray {@code array[low..high]} using heapsort.
     *
     * <p>The heap is built and maintained over the logical range
     * {@code array[low..high]}.</p>
     *
     * @param array the array containing the subarray to sort
     * @param low   lower bound (inclusive) of the subarray
     * @param high  upper bound (inclusive) of the subarray
     * @param <T>   element type
     */
    private static <T extends Comparable<T>> void heapSort(T[] array, int low, int high) {
        int heapSize = high - low + 1;

        // Build max heap on array[low..high]
        for (int i = (heapSize / 2) - 1; i >= 0; i--) {
            heapify(array, i, heapSize, low);
        }

        // Extract elements from heap one by one
        for (int i = high; i > low; i--) {
            SortUtils.swap(array, low, i);
            heapify(array, 0, i - low, low);
        }
    }

    /**
     * Restores the max-heap property in the heap represented by
     * {@code array[low..low + heapSize - 1]}, assuming subtrees are already heaps.
     *
     * @param array    the array containing the heap
     * @param index    index within the heap (0-based relative to {@code low})
     * @param heapSize current heap size
     * @param low      starting index of the heap in the original array
     * @param <T>      element type
     */
    private static <T extends Comparable<T>> void heapify(
            T[] array, int index, int heapSize, int low) {

        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;
        int largestIndex = index;

        if (leftChildIndex < heapSize
                && array[low + leftChildIndex].compareTo(array[low + largestIndex]) > 0) {
            largestIndex = leftChildIndex;
        }
        if (rightChildIndex < heapSize
                && array[low + rightChildIndex].compareTo(array[low + largestIndex]) > 0) {
            largestIndex = rightChildIndex;
        }

        if (largestIndex != index) {
            SortUtils.swap(array, low + index, low + largestIndex);
            heapify(array, largestIndex, heapSize, low);
        }
    }
}