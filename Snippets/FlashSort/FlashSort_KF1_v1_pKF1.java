package com.thealgorithms.sorts;

public class BucketSort implements SortAlgorithm {
    private double bucketRatio = 0.45;

    public BucketSort() {}

    public BucketSort(double bucketRatio) {
        if (bucketRatio <= 0 || bucketRatio >= 1) {
            throw new IllegalArgumentException("Classification ratio must be between 0 and 1 (exclusive).");
        }
        this.bucketRatio = bucketRatio;
    }

    public double getBucketRatio() {
        return bucketRatio;
    }

    public void setBucketRatio(double bucketRatio) {
        if (bucketRatio <= 0 || bucketRatio >= 1) {
            throw new IllegalArgumentException("Classification ratio must be between 0 and 1 (exclusive).");
        }
        this.bucketRatio = bucketRatio;
    }

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        bucketSort(array);
        return array;
    }

    private <T extends Comparable<? super T>> void bucketSort(T[] array) {
        if (array.length == 0) {
            return;
        }

        final T minValue = findMin(array);
        final int maxIndex = findMaxIndex(array);

        if (array[maxIndex].compareTo(minValue) == 0) {
            return;
        }

        final int bucketCount = (int) (bucketRatio * array.length);
        final int[] bucketSizes = new int[bucketCount];
        final double scalingFactor = (double) (bucketCount - 1) / array[maxIndex].compareTo(minValue);

        countBucketSizes(array, bucketSizes, scalingFactor, minValue);
        accumulateBucketSizes(bucketSizes);
        redistributeToBuckets(array, bucketSizes, scalingFactor, minValue, array.length, bucketCount);
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

    private <T extends Comparable<? super T>> void countBucketSizes(
            final T[] array, final int[] bucketSizes, final double scalingFactor, final T minValue) {
        for (T value : array) {
            int bucketIndex = (int) (scalingFactor * value.compareTo(minValue));
            bucketSizes[bucketIndex]++;
        }
    }

    private void accumulateBucketSizes(final int[] bucketSizes) {
        for (int i = 1; i < bucketSizes.length; i++) {
            bucketSizes[i] += bucketSizes[i - 1];
        }
    }

    private <T extends Comparable<? super T>> void redistributeToBuckets(
            final T[] array,
            final int[] bucketSizes,
            final double scalingFactor,
            T minValue,
            int length,
            int bucketCount) {

        int placedCount = 0;
        int currentIndex = 0;
        int currentBucket = bucketCount - 1;
        T currentValue;

        while (placedCount < length - 1) {
            while (currentIndex > bucketSizes[currentBucket] - 1) {
                currentIndex++;
                currentBucket = (int) (scalingFactor * array[currentIndex].compareTo(minValue));
            }
            currentValue = array[currentIndex];
            while (currentIndex != bucketSizes[currentBucket]) {
                currentBucket = (int) (scalingFactor * currentValue.compareTo(minValue));
                T temp = array[bucketSizes[currentBucket] - 1];
                array[bucketSizes[currentBucket] - 1] = currentValue;
                currentValue = temp;
                bucketSizes[currentBucket]--;
                placedCount++;
            }
        }
    }

    private <T extends Comparable<? super T>> void insertionSort(final T[] array) {
        int length = array.length;
        for (int i = 1; i < length; i++) {
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