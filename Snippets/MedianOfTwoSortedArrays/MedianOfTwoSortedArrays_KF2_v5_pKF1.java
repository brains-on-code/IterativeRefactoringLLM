package com.thealgorithms.divideandconquer;

public final class MedianOfTwoSortedArrays {

    private MedianOfTwoSortedArrays() {
    }

    public static double findMedianSortedArrays(int[] firstArray, int[] secondArray) {
        if (firstArray.length > secondArray.length) {
            return findMedianSortedArrays(secondArray, firstArray);
        }

        int firstLength = firstArray.length;
        int secondLength = secondArray.length;
        int searchStart = 0;
        int searchEnd = firstLength;

        while (searchStart <= searchEnd) {
            int firstPartitionIndex = (searchStart + searchEnd) / 2;
            int secondPartitionIndex = (firstLength + secondLength + 1) / 2 - firstPartitionIndex;

            int maxLeftFirst = (firstPartitionIndex == 0)
                ? Integer.MIN_VALUE
                : firstArray[firstPartitionIndex - 1];
            int minRightFirst = (firstPartitionIndex == firstLength)
                ? Integer.MAX_VALUE
                : firstArray[firstPartitionIndex];

            int maxLeftSecond = (secondPartitionIndex == 0)
                ? Integer.MIN_VALUE
                : secondArray[secondPartitionIndex - 1];
            int minRightSecond = (secondPartitionIndex == secondLength)
                ? Integer.MAX_VALUE
                : secondArray[secondPartitionIndex];

            boolean partitionsAreValid =
                maxLeftFirst <= minRightSecond && maxLeftSecond <= minRightFirst;

            if (partitionsAreValid) {
                boolean totalLengthIsOdd = ((firstLength + secondLength) & 1) == 1;
                if (totalLengthIsOdd) {
                    return Math.max(maxLeftFirst, maxLeftSecond);
                } else {
                    int maxOfLeftPartition = Math.max(maxLeftFirst, maxLeftSecond);
                    int minOfRightPartition = Math.min(minRightFirst, minRightSecond);
                    return (maxOfLeftPartition + minOfRightPartition) / 2.0;
                }
            } else if (maxLeftFirst > minRightSecond) {
                searchEnd = firstPartitionIndex - 1;
            } else {
                searchStart = firstPartitionIndex + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted");
    }
}