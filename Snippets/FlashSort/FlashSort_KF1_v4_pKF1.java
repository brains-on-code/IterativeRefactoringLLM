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

        final T minElement = findMinElement(array);
        final int maxElementIndex = findMaxElementIndex(array);

        if (array[maxElementIndex].compareTo(minElement) == 0) {
            return;
        }

        final int bucketCount = (int) (bucketRatio * array.length);
        final int[] bucketBoundaries = new int[bucketCount];
        final double scalingFactor =
                (double) (bucketCount - 1) / array[maxElementIndex].compareTo(minElement);

        countBucketSizes(array, bucketBoundaries, scalingFactor, minElement);
        accumulateBucketBoundaries(bucketBoundaries);
        redistributeElementsToBuckets(array, bucketBoundaries, scalingFactor, minElement);
        insertionSort(array);
    }

    private <T extends Comparable<? super T>> T findMinElement(final T[] array) {
        T minElement = array[0];
        for (int index = 1; index < array.length; index++) {
            if (array[index].compareTo(minElement) < 0) {
                minElement = array[index];
            }
        }
        return minElement;
    }

    private <T extends Comparable<? super T>> int findMaxElementIndex(final T[] array) {
        int maxElementIndex = 0;
        for (int index = 1; index < array.length; index++) {
            if (array[index].compareTo(array[maxElementIndex]) > 0) {
                maxElementIndex = index;
            }
        }
        return maxElementIndex;
    }

    private <T extends Comparable<? super T>> void countBucketSizes(
            final T[] array,
            final int[] bucketBoundaries,
            final double scalingFactor,
            final T minElement) {

        for (T element : array) {
            int bucketIndex = (int) (scalingFactor * element.compareTo(minElement));
            bucketBoundaries[bucketIndex]++;
        }
    }

    private void accumulateBucketBoundaries(final int[] bucketBoundaries) {
        for (int index = 1; index < bucketBoundaries.length; index++) {
            bucketBoundaries[index] += bucketBoundaries[index - 1];
        }
    }

    private <T extends Comparable<? super T>> void redistributeElementsToBuckets(
            final T[] array,
            final int[] bucketBoundaries,
            final double scalingFactor,
            final T minElement) {

        final int arrayLength = array.length;
        final int bucketCount = bucketBoundaries.length;

        int placedElementCount = 0;
        int currentIndex = 0;
        int currentBucketIndex = bucketCount - 1;
        T currentElement;

        while (placedElementCount < arrayLength - 1) {
            while (currentIndex > bucketBoundaries[currentBucketIndex] - 1) {
                currentIndex++;
                currentBucketIndex =
                        (int) (scalingFactor * array[currentIndex].compareTo(minElement));
            }
            currentElement = array[currentIndex];
            while (currentIndex != bucketBoundaries[currentBucketIndex]) {
                currentBucketIndex =
                        (int) (scalingFactor * currentElement.compareTo(minElement));
                T tempElement = array[bucketBoundaries[currentBucketIndex] - 1];
                array[bucketBoundaries[currentBucketIndex] - 1] = currentElement;
                currentElement = tempElement;
                bucketBoundaries[currentBucketIndex]--;
                placedElementCount++;
            }
        }
    }

    private <T extends Comparable<? super T>> void insertionSort(final T[] array) {
        int arrayLength = array.length;
        for (int index = 1; index < arrayLength; index++) {
            T keyElement = array[index];
            int sortedIndex = index - 1;
            while (sortedIndex >= 0 && array[sortedIndex].compareTo(keyElement) > 0) {
                array[sortedIndex + 1] = array[sortedIndex];
                sortedIndex--;
            }
            array[sortedIndex + 1] = keyElement;
        }
    }
}