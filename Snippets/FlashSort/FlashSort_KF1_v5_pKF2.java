package com.thealgorithms.sorts;

/**
 * Bucket sort implementation using a configurable classification ratio.
 *
 * <p>Algorithm outline:
 * <ol>
 *     <li>Find minimum and maximum elements</li>
 *     <li>Determine bucket count from {@code classificationRatio}</li>
 *     <li>Count how many elements fall into each bucket</li>
 *     <li>Convert counts to bucket boundaries (prefix sums)</li>
 *     <li>Reorder elements in-place so that each bucket occupies a contiguous range</li>
 *     <li>Run insertion sort for final ordering</li>
 * </ol>
 */
public class Class1 implements SortAlgorithm {

    /**
     * Ratio used to determine the number of buckets.
     * Must be in the range (0, 1).
     */
    private double classificationRatio = 0.45;

    public Class1() {}

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
            throw new IllegalArgumentException(
                "Classification ratio must be between 0 and 1 (exclusive)."
            );
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
     * Bucket sort driver.
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
        final int[] bucketBoundaries = new int[bucketCount];

        // Scale factor that maps value distance from min to a bucket index
        final double bucketScale =
            (double) (bucketCount - 1) / array[maxIndex].compareTo(min);

        countBucketSizes(array, bucketBoundaries, bucketScale, min);
        accumulateBucketBoundaries(bucketBoundaries);
        reorderIntoBuckets(array, bucketBoundaries, bucketScale, min, array.length, bucketCount);
        insertionSort(array);
    }

    /**
     * Returns the minimum element in the array.
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
     * Returns the index of the maximum element in the array.
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
     *
     * @param array         the input array
     * @param bucketCounts  array where each entry is incremented for elements in that bucket
     * @param bucketScale   factor to map value distance from {@code min} to a bucket index
     * @param min           minimum element in {@code array}
     */
    private <T extends Comparable<? super T>> void countBucketSizes(
        final T[] array,
        final int[] bucketCounts,
        final double bucketScale,
        final T min
    ) {
        for (T value : array) {
            int bucketIndex = (int) (bucketScale * value.compareTo(min));
            bucketCounts[bucketIndex]++;
        }
    }

    /**
     * Converts bucket counts into prefix sums so that each entry represents
     * the exclusive upper bound index of that bucket.
     *
     * @param bucketBoundaries array of bucket counts, converted in-place to boundaries
     */
    private void accumulateBucketBoundaries(final int[] bucketBoundaries) {
        for (int i = 1; i < bucketBoundaries.length; i++) {
            bucketBoundaries[i] += bucketBoundaries[i - 1];
        }
    }

    /**
     * Reorders elements in-place so that all elements belonging to the same
     * bucket occupy a contiguous range defined by {@code bucketBoundaries}.
     *
     * @param array            the array to reorder
     * @param bucketBoundaries exclusive upper bounds for each bucket (modified in-place)
     * @param bucketScale      factor to map value distance from {@code min} to a bucket index
     * @param min              minimum element in {@code array}
     * @param length           length of {@code array}
     * @param bucketCount      number of buckets
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
            // Advance to the next index that still belongs to currentBucket
            while (currentIndex > bucketBoundaries[currentBucket] - 1) {
                currentIndex++;
                currentBucket = (int) (bucketScale * array[currentIndex].compareTo(min));
            }

            T currentValue = array[currentIndex];

            // Cycle currentValue into its correct bucket position
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
     * Final insertion sort pass to ensure full ordering within buckets.
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