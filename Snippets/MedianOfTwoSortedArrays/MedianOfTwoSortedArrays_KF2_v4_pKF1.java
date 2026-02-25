package com.thealgorithms.divideandconquer;

public final class MedianOfTwoSortedArrays {

    private MedianOfTwoSortedArrays() {
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int length1 = nums1.length;
        int length2 = nums2.length;
        int left = 0;
        int right = length1;

        while (left <= right) {
            int partition1 = (left + right) / 2;
            int partition2 = (length1 + length2 + 1) / 2 - partition1;

            int maxLeft1 = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
            int minRight1 = (partition1 == length1) ? Integer.MAX_VALUE : nums1[partition1];

            int maxLeft2 = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
            int minRight2 = (partition2 == length2) ? Integer.MAX_VALUE : nums2[partition2];

            boolean isCorrectPartition = maxLeft1 <= minRight2 && maxLeft2 <= minRight1;

            if (isCorrectPartition) {
                boolean isOddTotalLength = ((length1 + length2) & 1) == 1;
                if (isOddTotalLength) {
                    return Math.max(maxLeft1, maxLeft2);
                } else {
                    int maxOfLeft = Math.max(maxLeft1, maxLeft2);
                    int minOfRight = Math.min(minRight1, minRight2);
                    return (maxOfLeft + minOfRight) / 2.0;
                }
            } else if (maxLeft1 > minRight2) {
                right = partition1 - 1;
            } else {
                left = partition1 + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted");
    }
}