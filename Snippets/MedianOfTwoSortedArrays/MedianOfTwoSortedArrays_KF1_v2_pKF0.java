package com.thealgorithms.divideandconquer;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Finds the median of two sorted integer arrays using a binary search approach.
     *
     * @param nums1 first sorted array
     * @param nums2 second sorted array
     * @return median value as a double
     * @throws IllegalArgumentException if input arrays are not sorted in non-decreasing order
     */
    public static double method1(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null) {
            throw new IllegalArgumentException("Input arrays must not be null");
        }

        // Ensure nums1 is the smaller array to keep the binary search efficient
        if (nums1.length > nums2.length) {
            return method1(nums2, nums1);
        }

        int length1 = nums1.length;
        int length2 = nums2.length;
        int totalLength = length1 + length2;
        boolean isTotalLengthOdd = (totalLength & 1) == 1;

        int left = 0;
        int right = length1;

        while (left <= right) {
            int partition1 = (left + right) >>> 1;
            int partition2 = (totalLength + 1) / 2 - partition1;

            int maxLeft1 = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
            int minRight1 = (partition1 == length1) ? Integer.MAX_VALUE : nums1[partition1];

            int maxLeft2 = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
            int minRight2 = (partition2 == length2) ? Integer.MAX_VALUE : nums2[partition2];

            boolean isCorrectPartition = maxLeft1 <= minRight2 && maxLeft2 <= minRight1;
            if (isCorrectPartition) {
                int maxOfLeft = Math.max(maxLeft1, maxLeft2);
                if (isTotalLengthOdd) {
                    return maxOfLeft;
                }
                int minOfRight = Math.min(minRight1, minRight2);
                return (maxOfLeft + minOfRight) / 2.0;
            }

            if (maxLeft1 > minRight2) {
                right = partition1 - 1;
            } else {
                left = partition1 + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted in non-decreasing order");
    }
}