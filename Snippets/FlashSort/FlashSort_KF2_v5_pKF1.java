package com.thealgorithms.sorts;

public class FlashSort implements SortAlgorithm {
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

    private <T extends Comparable<? super T>> void flashSort(T[] array) {
        if (array.length == 0) {
            return;
        }

        final T minValue = findMinValue(array);
        final int maxIndex = findMaxIndex(array);

        if (array[maxIndex].compareTo(minValue) == 0) {
            return;
        }

        final int classCount = (int) (classificationRatio * array.length);
        final int[] classCounts = new int[classCount];

        final double classIndexScale = (double) (classCount - 1) / array[maxIndex].compareTo(minValue);

        countElementsPerClass(array, classCounts, classIndexScale, minValue);
        accumulateClassCounts(classCounts);
        distributeElementsToClasses(array, classCounts, classIndexScale, minValue, array.length, classCount);
        insertionSort(array);
    }

    private <T extends Comparable<? super T>> T findMinValue(final T[] array) {
        T minValue = array[0];
        for (int index = 1; index < array.length; index++) {
            if (array[index].compareTo(minValue) < 0) {
                minValue = array[index];
            }
        }
        return minValue;
    }

    private <T extends Comparable<? super T>> int findMaxIndex(final T[] array) {
        int maxIndex = 0;
        for (int index = 1; index < array.length; index++) {
            if (array[index].compareTo(array[maxIndex]) > 0) {
                maxIndex = index;
            }
        }
        return maxIndex;
    }

    private <T extends Comparable<? super T>> void countElementsPerClass(
            final T[] array,
            final int[] classCounts,
            final double classIndexScale,
            final T minValue
    ) {
        for (T element : array) {
            int classIndex = (int) (classIndexScale * element.compareTo(minValue));
            classCounts[classIndex]++;
        }
    }

    private void accumulateClassCounts(final int[] classCounts) {
        for (int index = 1; index < classCounts.length; index++) {
            classCounts[index] += classCounts[index - 1];
        }
    }

    private <T extends Comparable<? super T>> void distributeElementsToClasses(
            final T[] array,
            final int[] classCounts,
            final double classIndexScale,
            final T minValue,
            int arrayLength,
            int classCount
    ) {
        int movedElementCount = 0;
        int currentIndex = 0;
        int currentClassIndex = classCount - 1;
        T currentValue;

        while (movedElementCount < arrayLength - 1) {
            while (currentIndex > classCounts[currentClassIndex] - 1) {
                currentIndex++;
                currentClassIndex = (int) (classIndexScale * array[currentIndex].compareTo(minValue));
            }
            currentValue = array[currentIndex];
            while (currentIndex != classCounts[currentClassIndex]) {
                currentClassIndex = (int) (classIndexScale * currentValue.compareTo(minValue));
                int targetIndex = classCounts[currentClassIndex] - 1;
                T temp = array[targetIndex];
                array[targetIndex] = currentValue;
                currentValue = temp;
                classCounts[currentClassIndex]--;
                movedElementCount++;
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