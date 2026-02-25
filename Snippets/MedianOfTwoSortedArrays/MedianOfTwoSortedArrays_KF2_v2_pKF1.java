package com.thealgorithms.divideandconquer;

public final class MedianOfTwoSortedArrays {

    private MedianOfTwoSortedArrays() {
    }

    public static double findMedianSortedArrays(int[] smallerArray, int[] largerArray) {
        if (smallerArray.length > largerArray.length) {
            return findMedianSortedArrays(largerArray, smallerArray);
        }

        int smallerLength = smallerArray.length;
        int largerLength = largerArray.length;
        int startIndex = 0;
        int endIndex = smallerLength;

        while (startIndex <= endIndex) {
            int partitionSmaller = (startIndex + endIndex) / 2;
            int partitionLarger = (smallerLength + largerLength + 1) / 2 - partitionSmaller;

            int maxLeftSmaller =
                (partitionSmaller == 0) ? Integer.MIN_VALUE : smallerArray[partitionSmaller - 1];
            int minRightSmaller =
                (partitionSmaller == smallerLength) ? Integer.MAX_VALUE : smallerArray[partitionSmaller];

            int maxLeftLarger =
                (partitionLarger == 0) ? Integer.MIN_VALUE : largerArray[partitionLarger - 1];
            int minRightLarger =
                (partitionLarger == largerLength) ? Integer.MAX_VALUE : largerArray[partitionLarger];

            boolean isValidPartition =
                maxLeftSmaller <= minRightLarger && maxLeftLarger <= minRightSmaller;

            if (isValidPartition) {
                boolean isCombinedLengthOdd = ((smallerLength + largerLength) & 1) == 1;
                if (isCombinedLengthOdd) {
                    return Math.max(maxLeftSmaller, maxLeftLarger);
                } else {
                    int maxOfLeft = Math.max(maxLeftSmaller, maxLeftLarger);
                    int minOfRight = Math.min(minRightSmaller, minRightLarger);
                    return (maxOfLeft + minOfRight) / 2.0;
                }
            } else if (maxLeftSmaller > minRightLarger) {
                endIndex = partitionSmaller - 1;
            } else {
                startIndex = partitionSmaller + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted");
    }
}