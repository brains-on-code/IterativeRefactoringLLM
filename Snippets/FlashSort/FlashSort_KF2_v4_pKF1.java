package com.thealgorithms.sorts;

public class FlashSort implements SortAlgorithm {
    private double classRatio = 0.45;

    public FlashSort() {}

    public FlashSort(double classRatio) {
        validateClassRatio(classRatio);
        this.classRatio = classRatio;
    }

    public double getClassificationRatio() {
        return classRatio;
    }

    public void setClassificationRatio(double classRatio) {
        validateClassRatio(classRatio);
        this.classRatio = classRatio;
    }

    private void validateClassRatio(double ratio) {
        if (ratio <= 0 || ratio >= 1) {
            throw new IllegalArgumentException("Classification ratio must be between 0 and 1 (exclusive).");
        }
    }

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        flashSort(array);
        return array;
    }

    private <T extends Comparable<? super T>> void flashSort(T[] array) {
        if (array.length == 0) {
            return;
        }

        final T minElement = findMinElement(array);
        final int maxElementIndex = findMaxElementIndex(array);

        if (array[maxElementIndex].compareTo(minElement) == 0) {
            return;
        }

        final int bucketCount = (int) (classRatio * array.length);
        final int[] bucketSizes = new int[bucketCount];

        final double bucketScale = (double) (bucketCount - 1) / array[maxElementIndex].compareTo(minElement);

        countElementsPerBucket(array, bucketSizes, bucketScale, minElement);
        accumulateBucketSizes(bucketSizes);
        distributeElementsToBuckets(array, bucketSizes, bucketScale, minElement, array.length, bucketCount);
        insertionSort(array);
    }

    private <T extends Comparable<? super T>> T findMinElement(final T[] array) {
        T minElement = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(minElement) < 0) {
                minElement = array[i];
            }
        }
        return minElement;
    }

    private <T extends Comparable<? super T>> int findMaxElementIndex(final T[] array) {
        int maxIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(array[maxIndex]) > 0) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private <T extends Comparable<? super T>> void countElementsPerBucket(
            final T[] array,
            final int[] bucketSizes,
            final double bucketScale,
            final T minElement
    ) {
        for (T element : array) {
            int bucketIndex = (int) (bucketScale * element.compareTo(minElement));
            bucketSizes[bucketIndex]++;
        }
    }

    private void accumulateBucketSizes(final int[] bucketSizes) {
        for (int i = 1; i < bucketSizes.length; i++) {
            bucketSizes[i] += bucketSizes[i - 1];
        }
    }

    private <T extends Comparable<? super T>> void distributeElementsToBuckets(
            final T[] array,
            final int[] bucketSizes,
            final double bucketScale,
            final T minElement,
            int arrayLength,
            int bucketCount
    ) {
        int movedCount = 0;
        int currentIndex = 0;
        int currentBucketIndex = bucketCount - 1;
        T currentValue;

        while (movedCount < arrayLength - 1) {
            while (currentIndex > bucketSizes[currentBucketIndex] - 1) {
                currentIndex++;
                currentBucketIndex = (int) (bucketScale * array[currentIndex].compareTo(minElement));
            }
            currentValue = array[currentIndex];
            while (currentIndex != bucketSizes[currentBucketIndex]) {
                currentBucketIndex = (int) (bucketScale * currentValue.compareTo(minElement));
                int targetIndex = bucketSizes[currentBucketIndex] - 1;
                T temp = array[targetIndex];
                array[targetIndex] = currentValue;
                currentValue = temp;
                bucketSizes[currentBucketIndex]--;
                movedCount++;
            }
        }
    }

    private <T extends Comparable<? super T>> void insertionSort(final T[] array) {
        int length = array.length;
        for (int unsortedIndex = 1; unsortedIndex < length; unsortedIndex++) {
            T key = array[unsortedIndex];
            int sortedIndex = unsortedIndex - 1;
            while (sortedIndex >= 0 && array[sortedIndex].compareTo(key) > 0) {
                array[sortedIndex + 1] = array[sortedIndex];
                sortedIndex--;
            }
            array[sortedIndex + 1] = key;
        }
    }
}