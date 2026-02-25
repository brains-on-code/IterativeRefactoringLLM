package com.thealgorithms.sorts;

/**
 * Introspective Sort Algorithm Implementation
 *
 * @see <a href="https://en.wikipedia.org/wiki/Introsort">IntroSort Algorithm</a>
 */
public class IntrospectiveSort implements SortAlgorithm {

    private static final int INSERTION_SORT_THRESHOLD = 16;

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        int maxDepth = 2 * (int) (Math.log(array.length) / Math.log(2));
        introspectiveSort(array, 0, array.length - 1, maxDepth);
        return array;
    }

    private static <T extends Comparable<T>> void introspectiveSort(
        T[] array,
        int low,
        int high,
        int depth
    ) {
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

    private static <T extends Comparable<T>> int partition(
        T[] array,
        int low,
        int high
    ) {
        int pivotIndex = low + (int) (Math.random() * (high - low + 1));
        SortUtils.swap(array, pivotIndex, high);

        T pivot = array[high];
        int smallerElementBoundary = low - 1;

        for (int current = low; current < high; current++) {
            if (array[current].compareTo(pivot) <= 0) {
                smallerElementBoundary++;
                SortUtils.swap(array, smallerElementBoundary, current);
            }
        }

        int finalPivotIndex = smallerElementBoundary + 1;
        SortUtils.swap(array, finalPivotIndex, high);
        return finalPivotIndex;
    }

    private static <T extends Comparable<T>> void insertionSort(
        T[] array,
        int low,
        int high
    ) {
        for (int i = low + 1; i <= high; i++) {
            T key = array[i];
            int j = i - 1;

            while (j >= low && array[j].compareTo(key) > 0) {
                array[j + 1] = array[j];
                j--;
            }

            array[j + 1] = key;
        }
    }

    private static <T extends Comparable<T>> void heapSort(
        T[] array,
        int low,
        int high
    ) {
        int length = high - low + 1;

        for (int i = (length / 2) - 1; i >= 0; i--) {
            heapify(array, i, length, low);
        }

        for (int i = high; i > low; i--) {
            SortUtils.swap(array, low, i);
            heapify(array, 0, i - low, low);
        }
    }

    private static <T extends Comparable<T>> void heapify(
        T[] array,
        int index,
        int heapSize,
        int low
    ) {
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        int largest = index;

        int largestIndex = low + largest;
        int leftIndex = low + leftChild;
        int rightIndex = low + rightChild;

        if (leftChild < heapSize && array[leftIndex].compareTo(array[largestIndex]) > 0) {
            largest = leftChild;
            largestIndex = leftIndex;
        }

        if (rightChild < heapSize && array[rightIndex].compareTo(array[largestIndex]) > 0) {
            largest = rightChild;
            largestIndex = rightIndex;
        }

        if (largest != index) {
            SortUtils.swap(array, low + index, largestIndex);
            heapify(array, largest, heapSize, low);
        }
    }
}