package com.thealgorithms.sorts;

/**
 * Bucket sort implementation using a configurable classification ratio.
 *
 * <p>Algorithm steps:
 * <ul>
 *     <li>Finds the minimum and maximum elements</li>
 *     <li>Distributes elements into buckets based on their value</li>
 *     <li>Reorders elements according to bucket boundaries</li>
 *     <li>Finishes with insertion sort for final ordering</li>
 * </ul>
 */
public class Class1 implements SortAlgorithm {

    /**
     * Ratio used to determine the number of buckets.
     * Must be in the range (0, 1).
     */
    private double classificationRatio = 0.45;

    public Class1() {
    }

    public Class1(double classificationRatio) {
        validateClassificationRatio(classificationRatio);
        this.classificationRatio = classificationRatio;
    }

    public double getClassificationRatio() {
        return classificationRatio;
    }

    public void setClassificationRatio(double classificationRatio) {
        validateClassificationRatio(classificationRatio);
        this.classificationRatio = classificationRatio;
    }

    private void validateClassificationRatio(double ratio) {
        if (ratio <= 0 || ratio >= 1) {
            throw new IllegalArgumentException("Classification ratio must be between 0 and 1 (exclusive).");
        }
    }

    /**
     * Sorts the given array using bucket sort.
     *
     * @param array the array to be sorted
     * @param <T>   the type of elements, must be comparable
     * @return the sorted array (same instance as input)
     */
    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        bucketSort(array);
        return array;
    }

    /**
     * Core bucket sort routine.
     *
     * @param array the array to sort
     * @param <T>   the type of elements, must be comparable
     */
    private <T extends Comparable<? super T>> void bucketSort(T[] array) {
        if (array.length == 0) {
            return;
        }

        final T min = findMin(array);
        final int maxIndex = findMaxIndex(array);

        // If all elements are equal, the array is already sorted
        if (array[maxIndex].compareTo(min) == 0) {
            return;
        }

        final int bucketCount = (int) (classificationRatio * array.length);
        final int[] bucketSizes = new int[bucketCount];

        final double bucketScale = (double) (bucketCount - 1) / array[maxIndex].compareTo(min);

        countBucketSizes(array, bucketSizes, bucketScale, min);
        accumulateBucketSizes(bucketSizes);
        reorderIntoBuckets(array, bucketSizes, bucketScale, min, array.length, bucketCount);
        insertionSort(array);
    }

    /**
     * Finds the minimum element in the array.
     */
    private <T extends Comparable<? super T>> T findMin(final T[] array) {
        T min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(min) < 0) {
                min = array[i];
            }
        }
        return min;
    }

    /**
     * Finds the index of the maximum element in the array.
     */
    private <T extends Comparable<? super T>> int findMaxIndex(final T[] array) {
        int maxIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(array[maxIndex]) > 0) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    /**
     * Counts how many elements fall into each bucket.
     */
    private <T extends Comparable<? super T>> void countBucketSizes(
            final T[] array,
            final int[] bucketSizes,
            final double bucketScale,
            final T min
    ) {
        for (T value : array) {
            int bucketIndex = (int) (bucketScale * value.compareTo(min));
            bucketSizes[bucketIndex]++;
        }
    }

    /**
     * Converts bucket sizes into prefix sums (bucket boundaries).
     */
    private void accumulateBucketSizes(final int[] bucketSizes) {
        for (int i = 1; i < bucketSizes.length; i++) {
            bucketSizes[i] += bucketSizes[i - 1];
        }
    }

    /**
     * Reorders elements into their appropriate buckets in-place.
     */
    private <T extends Comparable<? super T>> void reorderIntoBuckets(
            final T[] array,
            final int[] bucketBoundaries,
            final double bucketScale,
            final T min,
            final int length,
            final int bucketCount
    ) {
        int movedCount = 0;
        int currentIndex = 0;
        int currentBucket = bucketCount - 1;

        while (movedCount < length - 1) {
            // Move currentIndex to the first index that still belongs to currentBucket
            while (currentIndex > bucketBoundaries[currentBucket] - 1) {
                currentIndex++;
                currentBucket = (int) (bucketScale * array[currentIndex].compareTo(min));
            }

            T currentValue = array[currentIndex];

            // Place currentValue into its correct bucket position
            while (currentIndex != bucketBoundaries[currentBucket]) {
                currentBucket = (int) (bucketScale * currentValue.compareTo(min));
                int targetIndex = bucketBoundaries[currentBucket] - 1;

                T temp = array[targetIndex];
                array[targetIndex] = currentValue;
                currentValue = temp;

                bucketBoundaries[currentBucket]--;
                movedCount++;
            }
        }
    }

    /**
     * Final insertion sort pass to ensure full ordering.
     */
    private <T extends Comparable<? super T>> void insertionSort(final T[] array) {
        int n = array.length;
        for (int i = 1; i < n; i++) {
            T key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j].compareTo(key) > 0) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }
}