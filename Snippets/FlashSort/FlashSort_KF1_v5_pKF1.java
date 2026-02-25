package com.thealgorithms.sorts;

public class BucketSort implements SortAlgorithm {
    private double bucketRatio = 0.45;

    public BucketSort() {}

    public BucketSort(double bucketRatio) {
        validateBucketRatio(bucketRatio);
        this.bucketRatio = bucketRatio;
    }

    public double getBucketRatio() {
        return bucketRatio;
    }

    public void setBucketRatio(double bucketRatio) {
        validateBucketRatio(bucketRatio);
        this.bucketRatio = bucketRatio;
    }

    private void validateBucketRatio(double ratio) {
        if (ratio <= 0 || ratio >= 1) {
            throw new IllegalArgumentException("Bucket ratio must be between 0 and 1 (exclusive).");
        }
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

        final T minValue = findMinValue(array);
        final int maxValueIndex = findMaxValueIndex(array);

        if (array[maxValueIndex].compareTo(minValue) == 0) {
            return;
        }

        final int bucketCount = (int) (bucketRatio * array.length);
        final int[] bucketEndIndices = new int[bucketCount];
        final double scalingFactor =
                (double) (bucketCount - 1) / array[maxValueIndex].compareTo(minValue);

        countBucketSizes(array, bucketEndIndices, scalingFactor, minValue);
        accumulateBucketEndIndices(bucketEndIndices);
        redistributeElementsToBuckets(array, bucketEndIndices, scalingFactor, minValue);
        insertionSort(array);
    }

    private <T extends Comparable<? super T>> T findMinValue(final T[] array) {
        T minValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(minValue) < 0) {
                minValue = array[i];
            }
        }
        return minValue;
    }

    private <T extends Comparable<? super T>> int findMaxValueIndex(final T[] array) {
        int maxValueIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(array[maxValueIndex]) > 0) {
                maxValueIndex = i;
            }
        }
        return maxValueIndex;
    }

    private <T extends Comparable<? super T>> void countBucketSizes(
            final T[] array,
            final int[] bucketEndIndices,
            final double scalingFactor,
            final T minValue) {

        for (T element : array) {
            int bucketIndex = (int) (scalingFactor * element.compareTo(minValue));
            bucketEndIndices[bucketIndex]++;
        }
    }

    private void accumulateBucketEndIndices(final int[] bucketEndIndices) {
        for (int i = 1; i < bucketEndIndices.length; i++) {
            bucketEndIndices[i] += bucketEndIndices[i - 1];
        }
    }

    private <T extends Comparable<? super T>> void redistributeElementsToBuckets(
            final T[] array,
            final int[] bucketEndIndices,
            final double scalingFactor,
            final T minValue) {

        final int arrayLength = array.length;
        final int bucketCount = bucketEndIndices.length;

        int placedElementCount = 0;
        int currentIndex = 0;
        int currentBucketIndex = bucketCount - 1;
        T currentElement;

        while (placedElementCount < arrayLength - 1) {
            while (currentIndex > bucketEndIndices[currentBucketIndex] - 1) {
                currentIndex++;
                currentBucketIndex =
                        (int) (scalingFactor * array[currentIndex].compareTo(minValue));
            }
            currentElement = array[currentIndex];
            while (currentIndex != bucketEndIndices[currentBucketIndex]) {
                currentBucketIndex =
                        (int) (scalingFactor * currentElement.compareTo(minValue));
                T tempElement = array[bucketEndIndices[currentBucketIndex] - 1];
                array[bucketEndIndices[currentBucketIndex] - 1] = currentElement;
                currentElement = tempElement;
                bucketEndIndices[currentBucketIndex]--;
                placedElementCount++;
            }
        }
    }

    private <T extends Comparable<? super T>> void insertionSort(final T[] array) {
        int arrayLength = array.length;
        for (int i = 1; i < arrayLength; i++) {
            T keyElement = array[i];
            int sortedIndex = i - 1;
            while (sortedIndex >= 0 && array[sortedIndex].compareTo(keyElement) > 0) {
                array[sortedIndex + 1] = array[sortedIndex];
                sortedIndex--;
            }
            array[sortedIndex + 1] = keyElement;
        }
    }
}