package com.thealgorithms.sorts;

public class FlashSort implements SortAlgorithm {

    private static final double DEFAULT_CLASSIFICATION_RATIO = 0.45;
    private static final double MIN_RATIO = 0.0;
    private static final double MAX_RATIO = 1.0;

    private double classificationRatio = DEFAULT_CLASSIFICATION_RATIO;

    public FlashSort() {}

    public FlashSort(double classificationRatio) {
        setClassificationRatio(classificationRatio);
    }

    public double getClassificationRatio() {
        return classificationRatio;
    }

    public void setClassificationRatio(double classificationRatio) {
        validateClassificationRatio(classificationRatio);
        this.classificationRatio = classificationRatio;
    }

    private void validateClassificationRatio(double ratio) {
        if (ratio <= MIN_RATIO || ratio >= MAX_RATIO) {
            throw new IllegalArgumentException(
                "Classification ratio must be between 0 and 1 (exclusive)."
            );
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

        final T min = findMin(array);
        final int maxIndex = findMaxIndex(array);

        if (array[maxIndex].compareTo(min) == 0) {
            return;
        }

        final int bucketCount = (int) (classificationRatio * array.length);
        final int[] bucketSizes = new int[bucketCount];

        final double scale = (double) (bucketCount - 1) / array[maxIndex].compareTo(min);

        classifyToBuckets(array, bucketSizes, scale, min);
        accumulateBucketSizes(bucketSizes);
        permuteIntoBuckets(array, bucketSizes, scale, min, array.length, bucketCount);
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

    private <T extends Comparable<? super T>> void classifyToBuckets(
        final T[] array,
        final int[] bucketSizes,
        final double scale,
        final T min
    ) {
        for (T value : array) {
            int bucketIndex = (int) (scale * value.compareTo(min));
            bucketSizes[bucketIndex]++;
        }
    }

    private void accumulateBucketSizes(final int[] bucketSizes) {
        for (int i = 1; i < bucketSizes.length; i++) {
            bucketSizes[i] += bucketSizes[i - 1];
        }
    }

    private <T extends Comparable<? super T>> void permuteIntoBuckets(
        final T[] array,
        final int[] bucketBoundaries,
        final double scale,
        final T min,
        int length,
        int bucketCount
    ) {
        int moved = 0;
        int currentIndex = 0;
        int currentBucket = bucketCount - 1;

        while (moved < length - 1) {
            while (currentIndex > bucketBoundaries[currentBucket] - 1) {
                currentIndex++;
                currentBucket = (int) (scale * array[currentIndex].compareTo(min));
            }

            T flash = array[currentIndex];

            while (currentIndex != bucketBoundaries[currentBucket]) {
                currentBucket = (int) (scale * flash.compareTo(min));
                int targetIndex = bucketBoundaries[currentBucket] - 1;

                T temp = array[targetIndex];
                array[targetIndex] = flash;
                flash = temp;

                bucketBoundaries[currentBucket]--;
                moved++;
            }
        }
    }

    private <T extends Comparable<? super T>> void insertionSort(final T[] array) {
        for (int i = 1; i < array.length; i++) {
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