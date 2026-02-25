package com.thealgorithms.sorts;

/**
 * Implementation of the Proxmap Sort algorithm.
 *
 * <p>Proxmap sort is a distribution-based sorting algorithm that:
 * <ul>
 *     <li>Finds the minimum and maximum elements</li>
 *     <li>Maps elements into "proxmap" buckets using a mapping function</li>
 *     <li>Computes prefix sums over the bucket counts</li>
 *     <li>Rearranges elements in-place based on the bucket boundaries</li>
 *     <li>Finishes with an insertion sort to fully order the array</li>
 * </ul>
 */
public class ProxmapSort implements SortAlgorithm {

    /** Ratio used to determine the number of buckets (proxmap size). Must be in (0, 1). */
    private double classificationRatio = 0.45;

    public ProxmapSort() {}

    public ProxmapSort(double classificationRatio) {
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

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        proxmapSort(array);
        return array;
    }

    private <T extends Comparable<? super T>> void proxmapSort(T[] array) {
        if (array.length == 0) {
            return;
        }

        final T min = findMin(array);
        final int maxIndex = findMaxIndex(array);
        final T max = array[maxIndex];

        if (max.compareTo(min) == 0) {
            return; // All elements are equal; already sorted.
        }

        final int bucketCount = (int) (classificationRatio * array.length);
        final int[] bucketCounts = new int[bucketCount];

        final double mappingFactor = (double) (bucketCount - 1) / max.compareTo(min);

        buildBucketCounts(array, bucketCounts, mappingFactor, min);
        prefixSum(bucketCounts);
        rearrangeByBuckets(array, bucketCounts, mappingFactor, min, array.length, bucketCount);
        insertionSort(array);
    }

    private <T extends Comparable<? super T>> T findMin(final T[] array) {
        T min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(min) < 0) {
                min = array[i];
            }
        }
        return min;
    }

    private <T extends Comparable<? super T>> int findMaxIndex(final T[] array) {
        int maxIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(array[maxIndex]) > 0) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private <T extends Comparable<? super T>> void buildBucketCounts(
            final T[] array, final int[] bucketCounts, final double mappingFactor, final T min) {

        for (T value : array) {
            int bucketIndex = (int) (mappingFactor * value.compareTo(min));
            bucketCounts[bucketIndex]++;
        }
    }

    private void prefixSum(final int[] bucketCounts) {
        for (int i = 1; i < bucketCounts.length; i++) {
            bucketCounts[i] += bucketCounts[i - 1];
        }
    }

    private <T extends Comparable<? super T>> void rearrangeByBuckets(
            final T[] array,
            final int[] bucketCounts,
            final double mappingFactor,
            final T min,
            int length,
            int bucketCount) {

        int placedCount = 0;
        int currentIndex = 0;
        int currentBucket = bucketCount - 1;

        while (placedCount < length - 1) {
            while (currentIndex > bucketCounts[currentBucket] - 1) {
                currentIndex++;
                currentBucket = (int) (mappingFactor * array[currentIndex].compareTo(min));
            }

            T valueToPlace = array[currentIndex];

            while (currentIndex != bucketCounts[currentBucket]) {
                currentBucket = (int) (mappingFactor * valueToPlace.compareTo(min));
                int targetIndex = bucketCounts[currentBucket] - 1;

                T temp = array[targetIndex];
                array[targetIndex] = valueToPlace;
                valueToPlace = temp;

                bucketCounts[currentBucket]--;
                placedCount++;
            }
        }
    }

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