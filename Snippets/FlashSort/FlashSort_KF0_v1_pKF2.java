package com.thealgorithms.sorts;

/**
 * Flash Sort implementation.
 *
 * <p>Flash Sort is a distribution-based sorting algorithm. It:
 * <ol>
 *     <li>Finds the minimum and maximum values.</li>
 *     <li>Distributes elements into classes (buckets).</li>
 *     <li>Transforms class counts into starting indices.</li>
 *     <li>Permutes elements into near-sorted order.</li>
 *     <li>Finishes with insertion sort.</li>
 * </ol>
 */
public class FlashSort implements SortAlgorithm {

    /** Ratio of classes to array length (0, 1). */
    private double classificationRatio = 0.45;

    public FlashSort() {}

    public FlashSort(double classificationRatio) {
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
        flashSort(array);
        return array;
    }

    private <T extends Comparable<? super T>> void flashSort(T[] arr) {
        if (arr.length == 0) {
            return;
        }

        final T min = findMin(arr);
        final int maxIndex = findMaxIndex(arr);

        // All elements equal
        if (arr[maxIndex].compareTo(min) == 0) {
            return;
        }

        final int m = (int) (classificationRatio * arr.length);
        final int[] classCounts = new int[m];

        // Normalization factor for mapping values to classes
        final double c1 = (double) (m - 1) / arr[maxIndex].compareTo(min);

        classify(arr, classCounts, c1, min);
        prefixSum(classCounts);
        permute(arr, classCounts, c1, min, arr.length, m);
        insertionSort(arr);
    }

    private <T extends Comparable<? super T>> T findMin(final T[] arr) {
        T min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].compareTo(min) < 0) {
                min = arr[i];
            }
        }
        return min;
    }

    private <T extends Comparable<? super T>> int findMaxIndex(final T[] arr) {
        int maxIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].compareTo(arr[maxIndex]) > 0) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    /**
     * Count how many elements fall into each class.
     */
    private <T extends Comparable<? super T>> void classify(
            final T[] arr,
            final int[] classCounts,
            final double c1,
            final T min
    ) {
        for (T value : arr) {
            int k = (int) (c1 * value.compareTo(min));
            classCounts[k]++;
        }
    }

    /**
     * Convert counts into prefix sums so each entry holds the end index of its class.
     */
    private void prefixSum(final int[] classCounts) {
        for (int i = 1; i < classCounts.length; i++) {
            classCounts[i] += classCounts[i - 1];
        }
    }

    /**
     * Move elements into their target classes using cycle leader permutation.
     */
    private <T extends Comparable<? super T>> void permute(
            final T[] arr,
            final int[] classCounts,
            final double c1,
            final T min,
            final int n,
            final int m
    ) {
        int moved = 0;
        int currentIndex = 0;
        int currentClass = m - 1;

        while (moved < n - 1) {
            while (currentIndex > classCounts[currentClass] - 1) {
                currentIndex++;
                currentClass = (int) (c1 * arr[currentIndex].compareTo(min));
            }

            T flash = arr[currentIndex];

            while (currentIndex != classCounts[currentClass]) {
                currentClass = (int) (c1 * flash.compareTo(min));
                int targetIndex = classCounts[currentClass] - 1;

                T temp = arr[targetIndex];
                arr[targetIndex] = flash;
                flash = temp;

                classCounts[currentClass]--;
                moved++;
            }
        }
    }

    private <T extends Comparable<? super T>> void insertionSort(final T[] arr) {
        for (int i = 1; i < arr.length; i++) {
            T key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j].compareTo(key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }
}