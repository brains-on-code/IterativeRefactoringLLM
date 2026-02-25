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

    /**
     * Ratio of classes to array length (must be in (0, 1)).
     * A typical value is around 0.4–0.5.
     */
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

        // If all elements are equal, the array is already sorted.
        if (arr[maxIndex].compareTo(min) == 0) {
            return;
        }

        final int numberOfClasses = (int) (classificationRatio * arr.length);
        final int[] classCounts = new int[numberOfClasses];

        // Normalization factor for mapping values to class indices.
        final double normalizationFactor = (double) (numberOfClasses - 1) / arr[maxIndex].compareTo(min);

        countElementsPerClass(arr, classCounts, normalizationFactor, min);
        convertCountsToPrefixSums(classCounts);
        permuteIntoClasses(arr, classCounts, normalizationFactor, min, arr.length, numberOfClasses);
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
     * Counts how many elements fall into each class.
     */
    private <T extends Comparable<? super T>> void countElementsPerClass(
            final T[] arr,
            final int[] classCounts,
            final double normalizationFactor,
            final T min
    ) {
        for (T value : arr) {
            int classIndex = (int) (normalizationFactor * value.compareTo(min));
            classCounts[classIndex]++;
        }
    }

    /**
     * Converts class counts into prefix sums so each entry holds
     * the end index (exclusive) of its class in the permuted array.
     */
    private void convertCountsToPrefixSums(final int[] classCounts) {
        for (int i = 1; i < classCounts.length; i++) {
            classCounts[i] += classCounts[i - 1];
        }
    }

    /**
     * Moves elements into their target classes using cycle leader permutation.
     */
    private <T extends Comparable<? super T>> void permuteIntoClasses(
            final T[] arr,
            final int[] classCounts,
            final double normalizationFactor,
            final T min,
            final int length,
            final int numberOfClasses
    ) {
        int moved = 0;
        int currentIndex = 0;
        int currentClass = numberOfClasses - 1;

        while (moved < length - 1) {
            while (currentIndex > classCounts[currentClass] - 1) {
                currentIndex++;
                currentClass = (int) (normalizationFactor * arr[currentIndex].compareTo(min));
            }

            T flash = arr[currentIndex];

            while (currentIndex != classCounts[currentClass]) {
                currentClass = (int) (normalizationFactor * flash.compareTo(min));
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